package cn.org.bachelor.acm.dac;

import java.util.List;

public abstract class DacHelper {
    protected static final ThreadLocal<DacInfo> LOCAL_PAGE = new ThreadLocal();
    protected static boolean DEFAULT_COUNT = true;

    private DacHelper() {
    }

    protected static void setLocalDac(DacInfo dacInfo) {
        LOCAL_PAGE.set(dacInfo);
    }

    public static DacInfo getLocalDac() {
        return (DacInfo)LOCAL_PAGE.get();
    }

    public static void enableDac() {

    }
    public static DacInfo enableDac(List<Class<?>> params) {

        return getLocalDac();
    }

    public static void disableDac() {
    }

}
