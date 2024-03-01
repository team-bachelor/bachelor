package cn.org.bachelor.iam.oauth2.rs;

import lombok.Data;

@Data
public class RsRequest {
    private String accessToken;
    private String refreshToken;
    private String clientId;
    private String userId;
}
