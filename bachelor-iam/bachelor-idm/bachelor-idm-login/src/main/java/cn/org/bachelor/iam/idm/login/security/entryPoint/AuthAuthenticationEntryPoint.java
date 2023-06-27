package cn.org.bachelor.iam.idm.login.security.entryPoint;

import cn.org.bachelor.iam.idm.login.util.WebUtils;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录失败
 *
 * @Date: 2022-05-21 22:49
 * @since: 1.0
 */
@Slf4j
public class AuthAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("客户端信息认证失败: {}", authException.getMessage());
        JsonResponse r = new JsonResponse<AccessDeniedException>();
        r.setStatus(ResponseStatus.BIZ_ERR);
        r.setCode(String.valueOf(HttpStatus.OK.value()));
        r.setData(authException);
        r.setMsg("无权限访问当前路径");
        WebUtils.setJson2Response(response, r);
    }
}