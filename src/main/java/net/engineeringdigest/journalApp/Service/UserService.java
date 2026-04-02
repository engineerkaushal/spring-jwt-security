package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;

import java.util.List;

public interface UserService {
    List<Users> getAllUserDetails();

    Users getUserById(Long id);

    Users getUserByUserName(String userName);

    String save(Users users);

    String saveUserDetails(Users users);

    String deleteUserById(Long id);

    String deleteUserByName(String userName);
}
