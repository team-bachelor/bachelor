package cn.org.bachelor.iam.oauth2.rs;

public class RsClientHelper {

    protected static final ThreadLocal<RsRequest> LOCAL_REQ = new ThreadLocal();

    public static void start(RsRequest r) {
        LOCAL_REQ.set(r);
    }

    public static RsRequest getRsRequest() {
        return LOCAL_REQ.get() == null ? new RsRequest() : LOCAL_REQ.get();
    }

    public static void finish() {
        LOCAL_REQ.remove();
    }
}
