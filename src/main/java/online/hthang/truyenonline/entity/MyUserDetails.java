package online.hthang.truyenonline.entity;

import online.hthang.truyenonline.utils.ConstantsUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private List< GrantedAuthority > authorities;
    private User user;

    public MyUserDetails(User user, List< GrantedAuthority > authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUPass();
    }

    @Override
    public String getUsername() {
        return user.getUName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getUStatus().equals(ConstantsUtils.STATUS_ACTIVED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}