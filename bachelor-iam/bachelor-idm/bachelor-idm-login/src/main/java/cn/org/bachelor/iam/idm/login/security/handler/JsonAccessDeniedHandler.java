package cn.org.bachelor.iam.idm.login.security.handler;

import cn.org.bachelor.iam.idm.login.util.WebUtils;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        JsonResponse r = new JsonResponse<AccessDeniedException>();
        r.setStatus(ResponseStatus.BIZ_ERR);
        r.setCode(HttpStatus.FORBIDDEN.name());
        r.setData(accessDeniedException);
        r.setMsg("无权限访问当前路径");
        WebUtils.setJson2Response(response, r);
    }
}
