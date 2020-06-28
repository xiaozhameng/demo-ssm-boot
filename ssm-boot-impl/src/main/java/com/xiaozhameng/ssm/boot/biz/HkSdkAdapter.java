package com.xiaozhameng.ssm.boot.biz;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;
import com.xiaozhameng.ssm.boot.message.entity.Token;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import com.xiaozhameng.ssm.boot.utils.TokenUtil;
import hksdk.HCNetSDK;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 包装海康SDK直接提供的接口
 *
 * @author: xiaozhameng
 * @date: 2020/6/28 12:51 ????
 */
public class HkSdkAdapter {

    // TODO 通道暂时默认，有需要时再扩展
    private static final NativeLong DEFAULT_CHANNEL = new NativeLong(1);
    // SDK接口
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    // TODO 抓图图片存储路径
    // TODO 录像文件上传时的暂存路径，因为录像文件上传是一个先下载后传输的过程

    /**
     * 设备初始化
     *
     * @return
     */
    public boolean sdkInit() throws RuntimeException {
        return hCNetSDK.NET_DVR_Init();
    }

    /**
     * 设备登录
     *
     * @return
     */
    public long deviceLogin(DeviceInfo deviceInfo) throws RuntimeException {
        //设备ip地址
        String m_sDeviceIP = deviceInfo.getIp();
        short iPort = (short) deviceInfo.getPort().intValue();
        String userName = deviceInfo.getDUserName();
        String passWord = deviceInfo.getDPassword();

        // 设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        NativeLong lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, iPort, userName, passWord, m_strDeviceInfo);

        long userID = lUserID.longValue();
        if (userID == -1) {
            String message = String.format("设备登录异常, deviceId = %s, 错误码 = %s", deviceInfo.getId(), lUserID);
            throw new RuntimeException(message);
        } else {
            return userID;
        }
    }

    /**
     * 设备状态检查
     * <p>
     * SDK提供了较为丰富的设备状态监测接口，参考文档 ： 《设备网络SDK编程指南（IPC）》
     *
     * @param tokenStr
     * @return
     */
    public boolean deviceState(String tokenStr) {
        Token token = TokenUtil.tokenParse(tokenStr);
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserID = new NativeLong(loginId);
        //调用接口获取设备工作状态
        HCNetSDK.NET_DVR_WORKSTATE_V30 m_strWorkState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, m_strWorkState);
        return getDVRConfigSuc;
    }

    /**
     * 开始录像
     *
     * @param tokenStr
     * @return 开始录像
     */
    public boolean videoStart(String tokenStr) {
        Token token = TokenUtil.tokenParse(tokenStr);
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserID = new NativeLong(loginId);
        // 开始录像
        return hCNetSDK.NET_DVR_StartDVRRecord(lUserID, DEFAULT_CHANNEL, new NativeLong(0));
    }

    /**
     * 结束录像
     *
     * @param tokenStr tokenStr
     * @return 录像结果
     */
    public boolean videoStop(String tokenStr) {
        Token token = TokenUtil.tokenParse(tokenStr);
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserID = new NativeLong(loginId);
        // 结束录像
        return hCNetSDK.NET_DVR_StopDVRRecord(lUserID, DEFAULT_CHANNEL);
    }

    public boolean videoCapture(String tokenStr) {
        Token token = TokenUtil.tokenParse(tokenStr);
        long loginId = token.getLoginId();
        // 设备登录ID
        NativeLong lUserID = new NativeLong(loginId);
        // 抓图图像参数
        HCNetSDK.NET_DVR_JPEGPARA jpegpara = new HCNetSDK.NET_DVR_JPEGPARA();
        jpegpara.wPicSize = 2;
        jpegpara.wPicQuality = 1;
        long times = System.currentTimeMillis();
        // TODO 配置抓图地址
        String path = "C:\\Picture\\myDemo\\" + times + ".jpeg";
        return hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, new NativeLong(1L), jpegpara, path);
    }


    /**
     * 文件先下载，后上传
     *
     * @return
     */
    public String dataUpload() {
        HCNetSDK.NET_DVR_TIME struStartTime;
        HCNetSDK.NET_DVR_TIME struStopTime;

        struStartTime = new HCNetSDK.NET_DVR_TIME();
        struStopTime = new HCNetSDK.NET_DVR_TIME();
        struStartTime.dwYear = Integer.parseInt("2020");//开始时间
        struStartTime.dwMonth = Integer.parseInt("06");
        struStartTime.dwDay = Integer.parseInt("24");
        struStartTime.dwHour = Integer.parseInt("00");
        struStartTime.dwMinute = Integer.parseInt("00");
        struStartTime.dwSecond = Integer.parseInt("00");
        struStopTime.dwYear = Integer.parseInt("2020");//结束时间
        struStopTime.dwMonth = Integer.parseInt("06");
        struStopTime.dwDay = Integer.parseInt("24");
        struStopTime.dwHour = Integer.parseInt("12");
        struStopTime.dwMinute = Integer.parseInt("59");
        struStopTime.dwSecond = Integer.parseInt("59");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String sFileName = "c:\\DownLoad\\" + sdf.format(new Date()) + ".mp4";
        System.out.println(sFileName);
        NativeLong lUserID = null;
        NativeLong m_lLoadHandle = hCNetSDK.NET_DVR_GetFileByTime(lUserID, new NativeLong(1), struStartTime, struStopTime, sFileName);
        if (m_lLoadHandle.intValue() >= 0) {
            hCNetSDK.NET_DVR_PlayBackControl(m_lLoadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, new IntByReference(0));
            return sFileName;
        } else {
            System.out.println("按时间下载失败 laste error " + hCNetSDK.NET_DVR_GetLastError());
            return "下载失败";
        }
    }

    /**
     * 文件上传状态监测
     *
     * @return
     */
    public String dataUploadCheck() {
        return null;
    }
}
