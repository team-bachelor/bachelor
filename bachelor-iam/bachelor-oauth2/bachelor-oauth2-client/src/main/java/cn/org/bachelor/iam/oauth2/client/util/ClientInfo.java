package cn.org.bachelor.iam.oauth2.client.util;

import lombok.Data;

import javax.servlet.http.Cookie;

@Data
public class ClientInfo {

    private String url;
    private String code;
    private String phoneId;
    private String error;
    private String targetUrl;
    private Cookie[] cookies;

    public ClientInfo() {

    }
}
