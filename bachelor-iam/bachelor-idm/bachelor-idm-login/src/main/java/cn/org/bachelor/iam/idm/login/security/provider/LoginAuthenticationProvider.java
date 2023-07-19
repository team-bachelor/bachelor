package cn.org.bachelor.iam.idm.login.security.provider;

import cn.org.bachelor.iam.idm.login.service.UserDetailsService;
import cn.org.bachelor.iam.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        Object credentials = authentication.getCredentials();
        String password = credentials == null ? null : credentials.toString();
        UserDetails user = loadUserByUsername(username);
        if (user == null || !PasswordUtil.getPasswordEncoder().matches(password, user.getPassword())) {
            return null;
        }
        //还可以填充其他信息
        return new UsernamePasswordAuthenticationToken(user, user.getPassword());
    }

    private UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
