package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        try {
            List<Users> users = userService.getAllUserDetails();
            if (!CollectionUtils.isEmpty(users)) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
            return new ResponseEntity<>("Details not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable long myId) {
        try {
            Users userById = userService.getUserById(myId);
            if (userById != null) {
                return new ResponseEntity<>(userById, HttpStatus.OK);
            }
            return new ResponseEntity<>("Details not found with Id: "+myId+"", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("Id/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long myId) {
        try {
            String delete = userService.deleteUserById(myId);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("UserName/{userName}")
    public ResponseEntity<?> deleteUserByUserName(@PathVariable String userName) {
        try {
            String delete = userService.deleteUserByName(userName);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("Id/{myId}")
    public ResponseEntity<?> updateUserById(@RequestBody Users users, @PathVariable Long myId) {
        try {
            Users oldData = userService.getUserById(myId);
            if (oldData != null) {
                oldData.setUserName(users.getUserName());
                oldData.setPassword(users.getPassword());
            }
            String msg = userService.save(oldData);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
