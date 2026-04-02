package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.POJO.WeatherResponse;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secUser")
public class SecureUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUserName() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            String delete = userService.deleteUserByName(name);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUserByUserName(@RequestBody Users users) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            Users oldData = userService.getUserByUserName(name);
            if (oldData != null) {
                oldData.setUserName(users.getUserName());
                oldData.setPassword(users.getPassword());
            }
            String msg = userService.saveUserDetails(oldData);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> greetingsTestforExternalApiCallWithRestTemplate() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            WeatherResponse records = weatherService.getRecords("Mumbai");
            return new ResponseEntity<>("Hi " + name +" Weather feels like : " + records.getCurrent().getFeelsLike(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
