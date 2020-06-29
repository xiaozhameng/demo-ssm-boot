package com.xiaozhameng.ssm.boot.controller;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceRecordOptReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import com.xiaozhameng.ssm.boot.biz.HkSdkAdapter;
import com.xiaozhameng.ssm.boot.message.enums.DeviceOptTypeEnum;
import com.xiaozhameng.ssm.boot.message.result.sdk.CaptureRes;
import com.xiaozhameng.ssm.boot.message.result.sdk.DownloadRes;
import com.xiaozhameng.ssm.boot.service.DeviceInfoExtendService;
import com.xiaozhameng.ssm.boot.service.DeviceInfoService;
import com.xiaozhameng.ssm.boot.service.DeviceOptRecordService;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceOptRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @author: xiaozhameng
 * @date: 2020/6/27 9:15 下午
 */
@Controller
public class HkSdkOptController {

    @Resource
    private DeviceInfoExtendService deviceInfoExtendService;
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private HkSdkAdapter hkSdkAdapter;
    @Resource
    private DeviceOptRecordService deviceOptRecordService;
    @Resource
    private SftpRemoteFileTemplate sftpRemoteFileTemplate;
    @Value("${sftp-config.absPath}")
    private String sftpAbsPath ;

    /**
     * 线程池
     */
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(1,3,1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(5), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    /**
     * 开始录像：返回是否录操作成功
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("/api/device/opt/v1/video/start")
    public Result<DeviceOptCommonRes<Boolean>> videoStart(@NotNull String token){
        boolean videoStart = hkSdkAdapter.videoStart(token);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(videoStart));
    }

    /**
     * 结束录像：返回是否录操作成功
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("/api/device/opt/v1/video/stop")
    public Result<DeviceOptCommonRes<Boolean>> videoStop(@NotNull String token){
        boolean videoStop = hkSdkAdapter.videoStop(token);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(videoStop));
    }

    /**
     * 抓图：返回是否操作成功
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("/api/device/opt/v1/video/capture")
    public Result<DeviceOptCommonRes<String>> videoCapture(@NotNull String token){
        CaptureRes captureRes = hkSdkAdapter.videoCapture(token);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(captureRes.getPicPath()));
    }

    /**
     * 文件上传：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("/api/device/data/v1/upload")
    public Result<DeviceOptCommonRes<Boolean>> dataUpload(@Valid @RequestBody DeviceRecordOptReq param){
        // 查询拍照记录
        DeviceOptRecord record = deviceOptRecordService.getByPrimaryKey(param.getRecordId());
        // 先执行文件的下载
        DownloadRes downloadRes = hkSdkAdapter.fileDownload(record, param.getToken());

        // 如果下载成功，操作sftp 上传，这里必须异步启动线程操作
        this.doFileUpload(downloadRes);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(downloadRes.isSuccess()));
    }

    /**
     * 文件上传进度查询：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     * <p>
     * 扩展字段返回上传进度
     */
    @RequestMapping("/api/device/data/v1/upload/check")
    public Result<DeviceOptCommonRes<Long>> dataUploadCheck(@Valid @RequestBody DeviceRecordOptReq param){
        // 查询下载记录
        DeviceOptRecord record = deviceOptRecordService.getByPrimaryKey(param.getRecordId());
        if (!DeviceOptTypeEnum.RECORD_DOWNLOAD.name().equals(record.getOptType())){
            return Result.of(DeviceOptCommonRes.getFailedInstance("未找到对应的下载记录"));
        }

        int progressRate = hkSdkAdapter.fileDownloadCheck(downloadRes.getDownLoadHandle());
        return null;
    }

    /**
     * 执行文件上传操作
     */
    private void doFileUpload(DownloadRes downloadRes){
        pool.submit(()->{
            try {
                // 轮询查询文件下载状态，如果下载成功，则执行异步上传
                while (true){
                    int progressRate = hkSdkAdapter.fileDownloadCheck(downloadRes.getDownLoadHandle());
                    if (progressRate == 100){
                        break;
                    }else {
                        TimeUnit.MINUTES.sleep(5);
                    }
                }
                fileUpload2Sftp(downloadRes.getFilePath());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * sftp 文件上传
     * @param tempFilePath
     */
    private void fileUpload2Sftp(String tempFilePath){
        File file = Paths.get(tempFilePath).toFile();
        if (file.exists()) {
            Message<File> message = MessageBuilder.withPayload(file).build();
            sftpRemoteFileTemplate.send(message, sftpAbsPath, FileExistsMode.REPLACE);
            // file.delete();
            // 本地临时文件先不删除
        }
    }
    
}
