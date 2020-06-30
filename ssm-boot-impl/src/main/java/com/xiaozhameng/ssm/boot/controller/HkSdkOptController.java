package com.xiaozhameng.ssm.boot.controller;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceOptReq;
import com.xiaozhameng.hk.api.message.req.DeviceRecordOptReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import com.xiaozhameng.ssm.boot.biz.HkSdkAdapter;
import com.xiaozhameng.ssm.boot.message.entity.Token;
import com.xiaozhameng.ssm.boot.message.enums.CommonStatus;
import com.xiaozhameng.ssm.boot.message.enums.DeviceOptTypeEnum;
import com.xiaozhameng.ssm.boot.message.result.sdk.CaptureRes;
import com.xiaozhameng.ssm.boot.message.result.sdk.DownloadRes;
import com.xiaozhameng.ssm.boot.service.DeviceInfoExtendService;
import com.xiaozhameng.ssm.boot.service.DeviceInfoService;
import com.xiaozhameng.ssm.boot.service.DeviceOptRecordService;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceOptRecord;
import com.xiaozhameng.ssm.boot.utils.TokenUtil;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@RestController
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

    /**
     * 线程池
     */
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 3, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 开始录像：返回是否录操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @PostMapping("/api/device/opt/v1/video/start")
    public Result<DeviceOptCommonRes<Boolean>> videoStart(@Valid @RequestBody DeviceOptReq param) {
        Token token = TokenUtil.tokenParse(param.getToken());
        boolean videoStart = hkSdkAdapter.videoStart(token);
        this.buildOptRecordAndSave(token, DeviceOptTypeEnum.MANUAL_RECORD, CommonStatus.SUCCESS.name(), "操作录像结果" + videoStart);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(videoStart));
    }

    /**
     * 结束录像：返回是否录操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @PostMapping("/api/device/opt/v1/video/stop")
    public Result<DeviceOptCommonRes<Boolean>> videoStop(@Valid @RequestBody DeviceOptReq param) {
        Token token = TokenUtil.tokenParse(param.getToken());
        boolean videoStop = hkSdkAdapter.videoStop(token);
        this.buildOptRecordAndSave(token, DeviceOptTypeEnum.MANUAL_RECORD_STOP, CommonStatus.SUCCESS.name(), "停止录像结果" + videoStop);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(videoStop));
    }

    /**
     * 抓图：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @PostMapping("/api/device/opt/v1/video/capture")
    public Result<DeviceOptCommonRes<String>> videoCapture(@Valid @RequestBody DeviceOptReq param) {
        Token token = TokenUtil.tokenParse(param.getToken());
        CaptureRes captureRes = hkSdkAdapter.videoCapture(token);
        this.buildOptRecordAndSave(token, DeviceOptTypeEnum.MANUAL_CAPTURE, CommonStatus.SUCCESS.name(), "抓图结果" + captureRes.isSuccess());
        return Result.of(DeviceOptCommonRes.getSuccessInstance(captureRes.getPicPath()));
    }

    /**
     * 文件上传：返回是否操作成功记录ID
     *
     * @param param 请求参数
     * @return vo
     */
    @PostMapping("/api/device/data/v1/upload")
    public Result<DeviceOptCommonRes<Long>> dataUpload(@Valid @RequestBody DeviceRecordOptReq param) {
        // 查询拍照记录
        DeviceOptRecord record = deviceOptRecordService.getByPrimaryKey(param.getRecordId());
        // 先执行文件的下载
        Token token = TokenUtil.tokenParse(param.getToken());
        DownloadRes downloadRes = hkSdkAdapter.fileDownload(record, token);

        // 如果下载成功，操作sftp 上传，这里必须异步启动线程操作
        this.doFileUpload(downloadRes);

        // 封装下载记录，并落库
        DeviceOptRecord downLoadRecord =
                this.buildOptRecordAndSave(token, DeviceOptTypeEnum.RECORD_DOWNLOAD, CommonStatus.SUCCESS.name(), String.valueOf(downloadRes.getDownLoadHandle()));
        return Result.of(DeviceOptCommonRes.getSuccessInstance(downLoadRecord.getId()));
    }

    /**
     * 文件上传进度查询：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     * <p>
     * 扩展字段返回上传进度
     */
    @PostMapping("/api/device/data/v1/upload/check")
    public Result<DeviceOptCommonRes<Long>> dataUploadCheck(@Valid @RequestBody DeviceRecordOptReq param) {
        // 查询下载记录
        DeviceOptRecord record = deviceOptRecordService.getByPrimaryKey(param.getRecordId());
        if (!DeviceOptTypeEnum.RECORD_DOWNLOAD.name().equals(record.getOptType())) {
            return Result.of(DeviceOptCommonRes.getFailedInstance("未找到对应的下载记录"));
        }
        // 下载记录存储的值是下载文件的句柄
        String optData = record.getOptData();
        long fileHandle = Long.parseLong(optData);
        int progressRate = hkSdkAdapter.fileDownloadCheck(fileHandle);
        return Result.of(DeviceOptCommonRes.getSuccessInstance((long) progressRate));
    }

    /**
     * 构建记录
     */
    private DeviceOptRecord buildOptRecordAndSave(Token token, DeviceOptTypeEnum optType, String state, String optData) {
        // 记录设备登录日志
        DeviceOptRecord optRecord = DeviceOptRecord.builder()
                .deviceId(token.getDeviceId())
                .jobNo(token.getJobNo())
                .optType(optType.name())
                .state(state)
                .optData(optData)
                .build();
        deviceOptRecordService.saveSelective(optRecord);
        return optRecord;
    }

    /**
     * 执行文件上传操作
     */
    private void doFileUpload(DownloadRes downloadRes) {
        pool.submit(() -> {
            try {
                // 轮询查询文件下载状态，如果下载成功，则执行异步上传
                while (true) {
                    int progressRate = hkSdkAdapter.fileDownloadCheck(downloadRes.getDownLoadHandle());
                    if (progressRate == 100) {
                        break;
                    } else {
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
     *
     * @param tempFilePath
     */
    private void fileUpload2Sftp(String tempFilePath) {
        File file = Paths.get(tempFilePath).toFile();
        if (file.exists()) {
            Message<File> message = MessageBuilder.withPayload(file).build();
            sftpRemoteFileTemplate.send(message, "file", FileExistsMode.REPLACE);
            // file.delete();
            // 本地临时文件先不删除
        }
    }

}
