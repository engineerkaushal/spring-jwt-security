package net.engineeringdigest.journalApp.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Dao.UserDAO;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<Users> getAllUserDetails() {
        return userDAO.getAllUserDetails();
    }

    @Override
    public Users getUserById(Long id) {

        return userDAO.getUserById(id);
    }

    @Override
    public Users getUserByUserName(String userName) {

        return userDAO.getUserByUserName(userName);
    }

    @Override
    public String save(Users entry) {
        return userDAO.save(entry);
    }

    @Override
    public String saveUserDetails(Users entry) {
        String response = null;
        try {
            Users userName = userDAO.getUserByUserName(entry.getUserName());
            if (userName == null) {
                entry.setPassword(passwordEncoder.encode(entry.getPassword()));
                response = userDAO.save(entry);
            } else {
//                log.trace("trace");
//                log.debug("debug");
//                log.error("error");
//                log.warn("warn");
//                log.info("info");
                response = "User already present!";
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while saving {}, " + e.getMessage());
        }
        return response;
    }

    @Override
    public String deleteUserById(Long id) {
        return userDAO.deleteUserById(id);
    }

    @Override
    public String deleteUserByName(String userName) {
        Users user = getUserByUserName(userName);
        return userDAO.deleteUserByUserName(user);
    }
}
