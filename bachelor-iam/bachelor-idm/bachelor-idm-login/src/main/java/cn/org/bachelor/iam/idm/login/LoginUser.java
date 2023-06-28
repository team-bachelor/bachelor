package cn.org.bachelor.iam.idm.login;

import cn.org.bachelor.context.IUser;
import cn.org.bachelor.iam.acm.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author lz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails, IUser {

    private User user;

    private String orgCode;

    private String deptName;

    private String orgName;

    private String accessToken;

    private String tenantId;

    private String areaId;

    private String areaName;

    private boolean isAdministrator;

    public LoginUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getId() {
        return user == null ? null : user.getId();
    }

    @Override
    public String getName() {
        return user == null ? null : user.getName();
    }

    @Override
    public String getCode() {
        return user == null ? null : user.getCode();
    }

    @Override
    public String getOrgId() {
        return user == null ? null : user.getOrgId();
    }

    @Override
    public String getDeptId() {
        return user == null ? null : user.getDeptId();
    }

    @Override
    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setIsAdministrator(boolean isAdministrator){
        this.isAdministrator = isAdministrator;
    }
}