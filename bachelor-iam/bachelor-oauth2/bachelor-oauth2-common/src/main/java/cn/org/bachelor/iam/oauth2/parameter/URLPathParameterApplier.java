package cn.org.bachelor.iam.oauth2.parameter;


import cn.org.bachelor.iam.oauth2.request.OAuthRequest;
import cn.org.bachelor.iam.oauth2.utils.OAuthUtils;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class URLPathParameterApplier implements OAuthParameterApplier {
    public URLPathParameterApplier() {
    }

    public OAuthRequest applyOAuthParameters(OAuthRequest message, Map<String, Object> params) {
        String messageUrl = message.getLocationUri();
        if(messageUrl != null) {
            boolean containsQuestionMark = messageUrl.contains("?");
            StringBuffer url = new StringBuffer(messageUrl);
            StringBuffer query = new StringBuffer(OAuthUtils.buildQueryString(params.entrySet(), "UTF-8"));
            if(!OAuthUtils.isEmpty(query.toString())) {
                if(containsQuestionMark) {
                    url.append("&").append(query);
                } else {
                    url.append("?").append(query);
                }
            }

            message.setLocationUri(url.toString());
        }

        return message;
    }
}
