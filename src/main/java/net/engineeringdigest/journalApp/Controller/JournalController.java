package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;

    /*@GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<JournalEntry> entries = entryService.getAlldetails();
            if (!CollectionUtils.isEmpty(entries)) {
                return new ResponseEntity<>(entries, HttpStatus.OK);
            }
            return new ResponseEntity<>("Details not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllEntryOfUser(@PathVariable String userName) {
        try {
            Users userByUserName = userService.getUserByUserName(userName);
            List<JournalEntry> entries = userByUserName.getEntries();
            if (!CollectionUtils.isEmpty(entries)) {
                return new ResponseEntity<>(entries, HttpStatus.OK);
            }
            return new ResponseEntity<>("Details not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            entryService.save(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try {
            String saved = entryService.saveMenual(journalEntry, userName);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Id/{myId}")
    public ResponseEntity<?> getJurnalEntryById(@PathVariable long myId) {
         try {
             JournalEntry journalEntryById = entryService.getJournalEntryById(myId);
             if (journalEntryById != null) {
                 return new ResponseEntity<>(journalEntryById, HttpStatus.OK);
             }
             return new ResponseEntity<>("Details not found with Id: "+myId+"", HttpStatus.NOT_FOUND);
         } catch (Exception e) {
             return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
         }
    }


    @DeleteMapping("Id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable Long myId) {
        try {
            String delete = entryService.delete(myId);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("Id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@RequestBody JournalEntry journalEntry, @PathVariable Long myId) {
        try {
            JournalEntry oldData = entryService.getJournalEntryById(myId);
            if (oldData != null) {
                oldData.setContent(!StringUtils.isEmpty(journalEntry.getContent()) ? journalEntry.getContent() : oldData.getContent());
                oldData.setTitle(!StringUtils.isEmpty(journalEntry.getTitle()) ? journalEntry.getTitle() : oldData.getTitle());
            }
            String msg = entryService.save(oldData);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
