package cn.org.bachelor.iam.idm.login.security.filter;

import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.idm.login.LoginUser;
import cn.org.bachelor.iam.idm.login.config.IamLocalLoginConfig;
import cn.org.bachelor.iam.idm.login.util.UserUtil;
import cn.org.bachelor.iam.idm.service.UserIdentifyService;
import cn.org.bachelor.iam.token.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class UserIdentifyFilter extends OncePerRequestFilter {

    @Autowired
    private UserIdentifyService userIdentifyService;

    @Autowired
    private IamLocalLoginConfig iamLoginConfig;

    @Autowired
    private IamContext iamContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        LoginUser loginUser = null;
        if (iamLoginConfig.isEnableGateway()) {
            loginUser = UserUtil.vo2LoginUser(userIdentifyService.getAndSetUser2Context(request));
        } else {
            String token = request.getHeader(IamConstant.HTTP_HEADER_TOKEN_KEY);
            if(StringUtils.isEmpty(token)){
                filterChain.doFilter(request, response);
                return;
            }
            JwtToken jwt = JwtToken.decode(token);
            loginUser = UserUtil.jwt2LoginUser(jwt);
            iamContext.setLogonUser(UserUtil.loginUser2Vo(loginUser));
        }

        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        if (loginUser.getUser() == null || StringUtils.isEmpty(loginUser.getUser().getId())) {

        } else {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //放行
        }
        filterChain.doFilter(request, response);
    }
}

