package net.engineeringdigest.journalApp.Controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.JWTUtils.JwtUtils;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/All-Users")
    public ResponseEntity<?> getAllUsers() {
        List<Users> allUserDetails = userService.getAllUserDetails();
        if (!CollectionUtils.isEmpty(allUserDetails)) {
            return new ResponseEntity<>(allUserDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/Create-Users")
    public ResponseEntity<?> createUser(@RequestBody Users users) {
        String userDetails = userService.saveUserDetails(users);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @PostMapping("/Signup")
    public ResponseEntity<?> signup(@RequestBody Users users) {
        String userDetails = userService.saveUserDetails(users);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUserName(), users.getPassword()));
            UserDetails user = userDetailsService.loadUserByUsername(users.getUserName());
            String jwt = jwtUtils.generateToken(user.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e.getMessage ());
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
