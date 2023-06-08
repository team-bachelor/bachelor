package cn.org.bachelor.iam.idm.login.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        Object credentials = authentication.getCredentials();
        String password = credentials == null ? null : credentials.toString();
        UserDetails user = loadUserByUsername(username);
        if(!new BCryptPasswordEncoder().matches(password, user.getPassword())){
            throw new BadCredentialsException("yo!") ;
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword());
    }

    private UserDetails loadUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
