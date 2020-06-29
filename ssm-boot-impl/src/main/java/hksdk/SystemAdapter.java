package hksdk;

/**
 * 系统环境适配类
 */
public class SystemAdapter {

    /**
     * 根据系统环境，获取系统资源文件
     */
    public static String autoSelectLibraryPath(){
        String os = System.getProperty("os.name");
        System.out.println("操作系统为： "+os);
        // String path = "..\\..\\hkwsExtend\\";
        String PATH = "C:\\workspace\\CH-HCNetSDKV6.1.4.42_build20200527_win64\\库文件\\";
        return PATH;
    }
}
