//package com.xiaozhameng.ssm.boot.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sun.jna.NativeLong;
//import com.sun.jna.ptr.IntByReference;
//import hksdk.HCNetSDK;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Vector;
//
//
//@RestController
//@RequestMapping("/rest")
//public class HkSdkController {
//
//    @RequestMapping("api/sdkInit")
//    public String sdkInit() {
//        StringBuilder builder = new StringBuilder("测试结果\n");
//        // SDK初始化
//        boolean initSuc = hCNetSDK.NET_DVR_Init();
//        // TODO 设置超时时间
//        // hCNetSDK.NET_DVR_SetConnectTime();
//        return this.buildMessage("api/sdkInit", initSuc);
//    }
//
//    @RequestMapping("api/regist")
//    public String regist() {
//        StringBuilder builder = new StringBuilder("测试结果\n");
//
//        // 用户注册设备
//        String login = this.login();
//        return this.buildMessage("api/regist", login);
//    }
//
//    @RequestMapping("api/record/start")
//    public String startRecord() {
//        // 开始录像
//        boolean check = startManualRecord();
//        return this.buildMessage("api/record/start", check);
//    }
//
//    @RequestMapping("api/record/stop")
//    public String stopRecord() {
//        // 结束录像
//        boolean check = stopManualRecord();
//        return this.buildMessage("api/record/stop", check);
//    }
//
//    @RequestMapping("api/record/download")
//    public String downLoadRecord() {
//        // 下载录像 TODO 也可以做文件传输
//        String res = downLoad();
//        return this.buildMessage("api/record/download", res);
//    }
//
//    @RequestMapping("api/record/capture")
//    public String recordCapture() {
//        HCNetSDK.NET_DVR_JPEGPARA jpegpara = new HCNetSDK.NET_DVR_JPEGPARA();
//        jpegpara.wPicSize = 2;
//        jpegpara.wPicQuality = 1;
//        long times = System.currentTimeMillis();
//        String path = "C:\\Picture\\myDemo\\" + times + ".jpeg";
//        boolean res = hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, new NativeLong(1L), jpegpara, path);
//        System.out.println("抓图结果：" + res);
//        return this.buildMessage("api/record/capture", path);
//    }
//
//    @RequestMapping("api/device/check")
//    public String checkDevice() {
//        HCNetSDK.NET_DVR_WORKSTATE_V30 m_strWorkState = new HCNetSDK.NET_DVR_WORKSTATE_V30();//调用接口获取设备工作状态
//        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, m_strWorkState);
//        return this.buildMessage("api/device/check",getDVRConfigSuc);
//    }
//
//    // ********************** 如下信息为测试组装信息 *********************
//
//    private String buildMessage(String api, Object result) {
//        JSONObject item = new JSONObject();
//        item.put("接口地址", api);
//        item.put("result", result);
//
//        JSONObject res = new JSONObject();
//        res.put("测试结果", item);
//        return res.toJSONString();
//    }
//
//    @RequestMapping("api/test")
//    public String test() {
//        StringBuilder builder = new StringBuilder("测试结果\n");
//        // SDK初始化
//        boolean initSuc = hCNetSDK.NET_DVR_Init();
//        append(builder, initSuc);
//        // TODO 设置超时时间
//        // hCNetSDK.NET_DVR_SetConnectTime();
//
//        // 用户注册设备
//        String login = this.login();
//        append(builder, login);
//
//        // 开始录像
//        startManualRecord();
//
//        // 下载录像 TODO 也可以做文件传输
//        // downLoad();
//
//        // 查找文件
//        // findFileList();
//
//        String str = builder.toString();
//        System.out.println(str);
//        return str;
//    }
//
//    /*************************************************
//     函数:      "查找"  按钮单击相应函数
//     函数描述:	根据搜索信息搜索录像文件
//     *************************************************/
//    private void findFileList()//GEN-FIRST:event_jButtonSearchActionPerformed
//    {//GEN-HEADEREND:event_jButtonSearchActionPerformed
//        HCNetSDK.NET_DVR_FILECOND m_strFilecond = new HCNetSDK.NET_DVR_FILECOND();
//        m_strFilecond.struStartTime = new HCNetSDK.NET_DVR_TIME();
//        m_strFilecond.struStopTime = new HCNetSDK.NET_DVR_TIME();
//        m_strFilecond.struStartTime.dwYear = Integer.parseInt("2020");//开始时间
//        m_strFilecond.struStartTime.dwMonth = Integer.parseInt("06");
//        m_strFilecond.struStartTime.dwDay = Integer.parseInt("01");
//        m_strFilecond.struStartTime.dwHour = Integer.parseInt("00");
//        m_strFilecond.struStartTime.dwMinute = Integer.parseInt("00");
//        m_strFilecond.struStartTime.dwSecond = Integer.parseInt("00");
//        m_strFilecond.struStopTime.dwYear = Integer.parseInt("2020");//结束时间
//        m_strFilecond.struStopTime.dwMonth = Integer.parseInt("06");
//        m_strFilecond.struStopTime.dwDay = Integer.parseInt("21");
//        m_strFilecond.struStopTime.dwHour = Integer.parseInt("00");
//        m_strFilecond.struStopTime.dwMinute = Integer.parseInt("00");
//        m_strFilecond.struStopTime.dwSecond = Integer.parseInt("00");
//        m_strFilecond.lChannel = new NativeLong(Integer.parseInt("1"));//通道号
//
//        m_strFilecond.dwIsLocked = 0xff;
//
//
//        NativeLong lFindFile = hCNetSDK.NET_DVR_FindFile_V30(lUserID, m_strFilecond);
//        HCNetSDK.NET_DVR_FINDDATA_V30 strFile = new HCNetSDK.NET_DVR_FINDDATA_V30();
//        long findFile = lFindFile.longValue();
//        if (findFile > -1) {
//            System.out.println("file" + findFile);
//        }
//        NativeLong lnext;
//        strFile = new HCNetSDK.NET_DVR_FINDDATA_V30();
//
//        while (true) {
//            lnext = hCNetSDK.NET_DVR_FindNextFile_V30(lFindFile, strFile);
//            if (lnext.longValue() == HCNetSDK.NET_DVR_FILE_SUCCESS) {
//                //搜索成功
//                Vector<String> newRow = new Vector<String>();
//
//                //添加文件名信息
//                String[] s = new String[2];
//                s = new String(strFile.sFileName).split("\0", 2);
//                newRow.add(new String(s[0]));
//
//                int iTemp;
//                String MyString;
//                if (strFile.dwFileSize < 1024 * 1024) {
//                    iTemp = (strFile.dwFileSize) / (1024);
//                    MyString = iTemp + "K";
//                } else {
//                    iTemp = (strFile.dwFileSize) / (1024 * 1024);
//                    MyString = iTemp + "M   ";
//                    iTemp = ((strFile.dwFileSize) % (1024 * 1024)) / (1204);
//                    MyString = MyString + iTemp + "K";
//                }
//                newRow.add(MyString);                            //添加文件大小信息
//                newRow.add(strFile.struStartTime.toStringTime());//添加开始时间信息
//                newRow.add(strFile.struStopTime.toStringTime()); //添加结束时间信息
//                System.out.println("文件信息： " + newRow);
//            } else {
//                if (lnext.longValue() == HCNetSDK.NET_DVR_ISFINDING) {//搜索中
//                    System.out.println("搜索中");
//                    continue;
//                } else {
//                    if (lnext.longValue() == HCNetSDK.NET_DVR_FILE_NOFIND) {
//                        System.out.println("没有搜到文件");
//                        return;
//                    } else {
//                        System.out.println("搜索文件结束");
//                        boolean flag = hCNetSDK.NET_DVR_FindClose_V30(lFindFile);
//                        if (flag == false) {
//                            System.out.println("结束搜索失败");
//                        }
//                        return;
//                    }
//                }
//            }
//        }
//    }//GEN-LAST:event_jButtonSearchActionPerformed
//
//    private String downLoad()//GEN-FIRST:event_jButtonDownloadActionPerformed
//    {//GEN-HEADEREND:event_jButtonDownloadActionPerformed
//        HCNetSDK.NET_DVR_TIME struStartTime;
//        HCNetSDK.NET_DVR_TIME struStopTime;
//
//        struStartTime = new HCNetSDK.NET_DVR_TIME();
//        struStopTime = new HCNetSDK.NET_DVR_TIME();
//        struStartTime.dwYear = Integer.parseInt("2020");//开始时间
//        struStartTime.dwMonth = Integer.parseInt("06");
//        struStartTime.dwDay = Integer.parseInt("24");
//        struStartTime.dwHour = Integer.parseInt("00");
//        struStartTime.dwMinute = Integer.parseInt("00");
//        struStartTime.dwSecond = Integer.parseInt("00");
//        struStopTime.dwYear = Integer.parseInt("2020");//结束时间
//        struStopTime.dwMonth = Integer.parseInt("06");
//        struStopTime.dwDay = Integer.parseInt("24");
//        struStopTime.dwHour = Integer.parseInt("12");
//        struStopTime.dwMinute = Integer.parseInt("59");
//        struStopTime.dwSecond = Integer.parseInt("59");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//
//        String sFileName = "c:\\DownLoad\\" + sdf.format(new Date()) + ".mp4";
//        System.out.println(sFileName);
//        m_lLoadHandle = hCNetSDK.NET_DVR_GetFileByTime(lUserID, new NativeLong(1), struStartTime, struStopTime, sFileName);
//        if (m_lLoadHandle.intValue() >= 0) {
//            hCNetSDK.NET_DVR_PlayBackControl(m_lLoadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, new IntByReference(0));
//            return sFileName;
//        } else {
//            System.out.println("按时间下载失败 laste error " + hCNetSDK.NET_DVR_GetLastError());
//            return "下载失败";
//        }
//    }//GEN-LAST:event_jButtonDownloadActionPerformed
//
//    /**
//     * 5.15.1 远程手动启动设备录像 NET_DVR_StartManualRecord
//     * 函 数： BOOL NET_DVR_StartManualRecord(LONG lUserID, LPNET_DVR_MANUAL_RECORD_PARA
//     * pRecordPara)
//     * 参 数： [in]lUserID
//     * [in]pRecordPara
//     * NET_DVR_Login_V40 的返回值
//     * 手动录像参数，包括通道号或流 ID、录像类型等
//     * 返回值： TRUE 表示成功，FALSE 表示失败。接口返回失败请调用 NET_DVR_GetLastError 获取错误码，通
//     * 过错误码判断出错原因。
//     *
//     * @return
//     */
//    private boolean startManualRecord() {
//        Boolean check = hCNetSDK.NET_DVR_StartDVRRecord(new NativeLong(0L), new NativeLong(1L), new NativeLong(1L));
//        System.out.println("开始录像 = " + check);
//        if (!check) {
//            System.out.println("录像失败，错误码 = " + hCNetSDK.NET_DVR_GetLastError());
//        }
//        return check;
//    }
//
//    private boolean stopManualRecord() {
//        Boolean check = hCNetSDK.NET_DVR_StopDVRRecord(new NativeLong(0L), new NativeLong(1L));
//        System.out.println("结束录像 = " + check);
//        if (!check) {
//            System.out.println("结束录像失败，错误码 = " + hCNetSDK.NET_DVR_GetLastError());
//        }
//        return check;
//    }
//
//    /**
//     * 这里的配置信息到时候可以放在数据库，然后提供操作界面
//     */
//    private String login() {
//        StringBuilder builder = new StringBuilder("登录信息-->");
//        //设备ip地址
//        m_sDeviceIP = "192.168.1.64";
//        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
//        short iPort = (short) Integer.parseInt("8000");
//        String userName = "admin";
//        String passWord = "Asdfg890";
//        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, iPort, userName, passWord, m_strDeviceInfo);
//        System.out.println("注册失败错误码：" + hCNetSDK.NET_DVR_GetLastError());
//        long userID = lUserID.longValue();
//        if (userID == -1) {
//            m_sDeviceIP = "";//登录未成功,IP置为空
//            System.out.println("未登录成功！");
//            builder.append("未登录成功！");
//        } else {
//            System.out.println("登录成功,UserId = " + userID);
//            builder.append("登录成功！");
//        }
//        return builder.toString();
//    }
//
//    private void append(StringBuilder res, Object result) {
//        res.append("\n").append(result);
//    }
//
//    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
//
//    // 设备信息
//    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
//    HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;//IP参数
//    HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//用户参数
//
//    NativeLong m_lLoadHandle;
//    String m_sDeviceIP;//已登录设备的IP地址
//    NativeLong lUserID;//用户句柄
//}
