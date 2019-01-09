package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.MyUserDetails;
import online.hthang.truyenonline.entity.Role;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;


    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userService.getUserByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        List< GrantedAuthority > grantList = new ArrayList< GrantedAuthority >();
        if (user.getRoleList() != null) {
            for (Role urole : user.getRoleList()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(urole.getRName());
                grantList.add(authority);
            }
        }

        return new MyUserDetails(user, grantList);
    }

}