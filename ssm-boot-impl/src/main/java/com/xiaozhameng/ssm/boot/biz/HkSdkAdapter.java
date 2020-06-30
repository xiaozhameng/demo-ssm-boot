package com.xiaozhameng.ssm.boot.biz;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.xiaozhameng.ssm.boot.exception.HkSdkException;
import com.xiaozhameng.ssm.boot.message.entity.Token;
import com.xiaozhameng.ssm.boot.message.enums.DeviceOptTypeEnum;
import com.xiaozhameng.ssm.boot.message.result.sdk.CaptureRes;
import com.xiaozhameng.ssm.boot.message.result.sdk.DownloadRes;
import com.xiaozhameng.ssm.boot.message.result.sdk.StateRes;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceOptRecord;
import com.xiaozhameng.ssm.boot.utils.DateUtils;
import com.xiaozhameng.ssm.boot.utils.TokenUtil;
import hksdk.HCNetSDK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 包装海康SDK直接提供的接口
 *
 * @author: xiaozhameng
 * @date: 2020/6/28 12:51 ????
 */
@Service
public class HkSdkAdapter {

    /**
     * 通道暂时默认，有需要时再扩展
     */
    private static final NativeLong DEFAULT_CHANNEL = new NativeLong(1);
    /**
     * SDK接口
     */
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    @Value("${sdk-config.temp-path}")
    private String tempPath;

    /**
     * 接口-设备初始化
     *
     * @return result result
     */
    public boolean sdkInit() throws HkSdkException {
        boolean res = hCNetSDK.NET_DVR_Init();
        checkException(res, "初始化失败");
        return res;
    }

    /**
     * 设备登录
     *
     * @return result
     */
    public long deviceLogin(DeviceInfo deviceInfo) throws HkSdkException {
        //设备ip地址
        String deviceInfoIp = deviceInfo.getIp();
        short iPort = (short) deviceInfo.getPort().intValue();
        String userName = deviceInfo.getLoginName();
        String passWord = deviceInfo.getLoginPwd();

        // 设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V30 deviceInfoV30 = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        NativeLong lUserId = hCNetSDK.NET_DVR_Login_V30(deviceInfoIp, iPort, userName, passWord, deviceInfoV30);
        long userId = lUserId.longValue();

        checkException((userId != -1), "设备登录失败");
        return userId;
    }

    /**
     * 设备状态检查
     * <p>
     * SDK提供了较为丰富的设备状态监测接口，参考文档 ： 《设备网络SDK编程指南（IPC）》
     *
     * @param token
     * @return result
     */
    public StateRes deviceState(Token token) {
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserId = new NativeLong(loginId);
        //调用接口获取设备工作状态
        HCNetSDK.NET_DVR_WORKSTATE_V30 state = new HCNetSDK.NET_DVR_WORKSTATE_V30();
        boolean res = hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserId, state);

        checkException(res, "设备状态检查失败");

        //设备的状态,0-正常,1-CPU占用率太高,超过85%,2-硬件错误,例如串口死掉
        int dwDeviceStatic = state.dwDeviceStatic;
        String code = "";
        String message = "";
        switch (dwDeviceStatic) {
            case 0:
                code = "0";
                message = "正常";
                break;
            case 1:
                code = "1";
                message = "CPU占用率太高";
                break;
            case 2:
                code = "2";
                message = "硬件错误";
                break;
            default:
                code = String.valueOf(dwDeviceStatic);
                message = "未知";
                break;
        }
        return StateRes.builder()
                .result(res)
                .code(code)
                .codeDes(message)
                .build();
    }

    /**
     * 开始录像
     *
     * @param token token
     * @return result 开始录像
     */
    public boolean videoStart(Token token) {
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserID = new NativeLong(loginId);
        // 开始录像
        boolean res = hCNetSDK.NET_DVR_StartDVRRecord(lUserID, DEFAULT_CHANNEL, new NativeLong(0));
        checkException(res, "录像操作失败");
        return res;
    }

    /**
     * 结束录像
     *
     * @param token token
     * @return result 录像结果
     */
    public boolean videoStop(Token token) {
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserID = new NativeLong(loginId);
        // 结束录像
        boolean res = hCNetSDK.NET_DVR_StopDVRRecord(lUserID, DEFAULT_CHANNEL);
        checkException(res, "停止录像失败");
        return res;
    }

    /**
     * 抓图
     *
     * @param token token 信息
     * @return result
     */
    public CaptureRes videoCapture(Token token) {
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserId = new NativeLong(loginId);
        // 抓图图像参数
        HCNetSDK.NET_DVR_JPEGPARA jpegpara = new HCNetSDK.NET_DVR_JPEGPARA();
        jpegpara.wPicSize = 2;
        jpegpara.wPicQuality = 1;

        // 基准目录 / 设备ID / pic /年/月/日_HHmmss.jpeg
        String format = "yyyy" + File.separator + "MM" + File.separator + "dd_HHmmss";
        String subPath = new SimpleDateFormat(format).format(new Date());

        String path = tempPath + File.separator
                + token.getDeviceId() + File.separator
                + "pic" + File.separator
                + subPath + ".jpeg";
        File pathFile = new File(path);
        if (!pathFile.exists()){
            pathFile.mkdirs();
        }
        boolean res = hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserId, DEFAULT_CHANNEL, jpegpara, path + ".jpeg");
        checkException(res, "抓图失败");
        return CaptureRes.builder()
                .picPath(path)
                .success(res)
                .build();
    }

    /**
     * 文件下载
     *
     * @return result
     */
    public DownloadRes fileDownload(DeviceOptRecord optRecord, Token token) {
        // 如果这不是一条录像对应的记录，则直接返回失败
        if (optRecord == null || !DeviceOptTypeEnum.MANUAL_RECORD.name().equals(optRecord.getOptType())) {
            throw new RuntimeException("未找到对应的操作记录");
        }
        HCNetSDK.NET_DVR_TIME startTime = new HCNetSDK.NET_DVR_TIME();
        HCNetSDK.NET_DVR_TIME stopTime = new HCNetSDK.NET_DVR_TIME();
        //开始时间
        LocalDateTime startDate = DateUtils.dateConvertToLocalDateTime(optRecord.getCreateTime());
        LocalDateTime endDate = DateUtils.dateConvertToLocalDateTime(optRecord.getUpdateTime());
        startTime.dwYear = startDate.getYear();
        startTime.dwMonth = startDate.getMonthValue();
        startTime.dwDay = startDate.getDayOfMonth();
        startTime.dwHour = startDate.getHour();
        startTime.dwMinute = startDate.getMinute();
        startTime.dwSecond = startDate.getSecond();
        //结束时间
        stopTime.dwYear = endDate.getYear();
        stopTime.dwMonth = endDate.getMonthValue();
        stopTime.dwDay = endDate.getDayOfMonth();
        stopTime.dwHour = endDate.getHour();
        stopTime.dwMinute = endDate.getMinute();
        stopTime.dwSecond = endDate.getSecond();

        // 基准目录 / 设备ID / video /年/月/日_HHmmss.mp4
        String format = "yyyy" + File.separator + "MM" + File.separator + "dd_HHmmss";
        String subPath = new SimpleDateFormat(format).format(new Date());

        String sFileName = tempPath + File.separator
                + optRecord.getDeviceId() + File.separator
                + "video" + File.separator
                + subPath + ".mp4";
        NativeLong lUserId = new NativeLong(token.getLoginId());
        NativeLong downLoadHandle = hCNetSDK.NET_DVR_GetFileByTime(lUserId, DEFAULT_CHANNEL, startTime, stopTime, sFileName);

        checkException((downLoadHandle.intValue() >= 0), "文件下载失败");
        // 执行文件的异步下载
        hCNetSDK.NET_DVR_PlayBackControl(downLoadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, new IntByReference(0));

        return DownloadRes.builder()
                .success(true)
                .downLoadHandle(downLoadHandle.longValue())
                .filePath(sFileName)
                .build();
    }

    /**
     * 文件下载状态检测
     *
     * @param fileHandle 下载文件句柄
     * @return result -1 表示失败;0~100 表示下载的进度;100 表示下载结束;正常范围 0-100，如返回 200 表明出
     */
    public int fileDownloadCheck(long fileHandle) {
        int res = hCNetSDK.NET_DVR_GetDownloadPos(new NativeLong(fileHandle));
        checkException(res != -1, "录像文件进度获取失败");
        return res;
    }


    /**
     * 结果检查，并抛出相应的异常
     */
    private void checkException(boolean expectRes, String error) {
        if (!expectRes) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            String message = hCNetSDK.NET_DVR_GetErrorMsg(new NativeLongByReference(new NativeLong(errorCode)));
            HkSdkException hkSdkException = new HkSdkException();
            hkSdkException.setCode(String.valueOf(errorCode));
            hkSdkException.setMessage(error + "SDK指引-->" + message);
            hkSdkException.setStackTrace(Thread.currentThread().getStackTrace());
            throw hkSdkException;
        }
    }
}
