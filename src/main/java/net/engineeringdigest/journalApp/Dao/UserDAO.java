package net.engineeringdigest.journalApp.Dao;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;

import java.util.List;

public interface UserDAO {

    List<Users> getAllUserDetails();

    Users getUserById(Long id);

    Users getUserByUserName(String userName);

    String save(Users users);

    String deleteUserById(Long id);

    String deleteUserByUserName(Users user);
}
