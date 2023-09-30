package app.moz.blogapp.DetailsService;

import app.moz.blogapp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TheUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public TheUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userService.getUserByEmail(username).
                orElseThrow(()-> new UsernameNotFoundException("Username " + username +" Not Found!"));
    }
}
