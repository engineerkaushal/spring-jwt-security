package net.engineeringdigest.journalApp.Service.Impl;

import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails details = null;
        try {
            Users userByUserName = userService.getUserByUserName(username);
            if (userByUserName != null) {
                details = User.builder()
                        .username(userByUserName.getUserName())
                        .password(userByUserName.getPassword())
                        .roles(userByUserName.getRoles())
                        .build();
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: "+ username);
        }
        return details;
    }
}
