package net.engineeringdigest.journalApp.Service.Impl;

import net.engineeringdigest.journalApp.Dao.JournalEntryDAO;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryDAO journalEntryDAO;

    @Autowired
    private UserService userService;
    @Override
    public List<JournalEntry> getAlldetails() {
        return journalEntryDAO.getAlldetails();
    }

    @Override
    public JournalEntry getJournalEntryById(Long id) {

        return journalEntryDAO.getJournalEntryById(id);
    }

    @Override
    public String save(JournalEntry entry) {
        return journalEntryDAO.save(entry);
    }

    @Override
    public String saveMenual(JournalEntry entry, String userName) {
        Users userByUserName = userService.getUserByUserName(userName);
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(entry.getTitle());
        journalEntry.setContent(entry.getContent());
        journalEntry.setUser(userByUserName);
        return journalEntryDAO.save(journalEntry);
//        userByUserName.getEntries().add(journalEntry);
//        return userService.save(userByUserName);
    }


    @Override
    public String delete(Long id) {
        return journalEntryDAO.delete(id);
    }

}
