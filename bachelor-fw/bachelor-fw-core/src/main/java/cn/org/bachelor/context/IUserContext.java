package cn.org.bachelor.context;
@InjectDeny
public interface IUserContext {
    IUser getUser();

    String getRemoteIP();
}
