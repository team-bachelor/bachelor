package cn.org.bachelor.iam.dac.client;

public abstract class DacHelper {
    protected static final ThreadLocal<DacInfo> LOCAL_DAC = new ThreadLocal();

    private DacHelper() {
    }

    protected static void setLocalDacInfo(DacInfo dacInfo) {
        LOCAL_DAC.set(dacInfo);
    }

    public static DacInfo getLocalDacInfo() {
        return (DacInfo) LOCAL_DAC.get();
    }

    public static void enableDac() {
        getLocalDacInfo().setEnable(true);
    }

//    public static DacInfo enableDac(List<Class<?>> params) {
//        return getLocalDacInfo();
//    }

    public static void disableDac() {
        getLocalDacInfo().setEnable(false);
    }

    public static boolean isEnable() {
        return getLocalDacInfo().isEnable();
    }
}
