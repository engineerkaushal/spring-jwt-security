package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/secJournal")
public class SecurityJournalController {
    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntryOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            Users userByUserName = userService.getUserByUserName(name);
            List<JournalEntry> entries = userByUserName.getEntries();
            if (!CollectionUtils.isEmpty(entries)) {
                return new ResponseEntity<>(entries, HttpStatus.OK);
            }
            return new ResponseEntity<>("Details not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            String saved = entryService.saveMenual(journalEntry, name);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Id/{myId}")
    public ResponseEntity<?> getJurnalEntryById(@PathVariable long myId) {
        JournalEntry journalEntryById = null;
         try {
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String name = authentication.getName();
             Users userByUserName = userService.getUserByUserName(name);
             List<JournalEntry> collect = userByUserName.getEntries()
                     .stream().filter(f -> f.getId() == myId).collect(Collectors.toList());
             if (!collect.isEmpty()) {
                 journalEntryById = entryService.getJournalEntryById(myId);
             }
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
            SecurityContextHolder.getContext().getAuthentication();
            String delete = entryService.delete(myId);
            return new ResponseEntity<>(delete, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("Id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@RequestBody JournalEntry journalEntry, @PathVariable Long myId) {
        JournalEntry journalEntryById = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            Users userByUserName = userService.getUserByUserName(name);
            List<JournalEntry> collect = userByUserName.getEntries()
                    .stream().filter(f -> f.getId() == myId).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                JournalEntry entry = collect.get(0);
                entry.setContent(!StringUtils.isEmpty(journalEntry.getContent()) ? journalEntry.getContent() : entry.getContent());
                entry.setTitle(!StringUtils.isEmpty(journalEntry.getTitle()) ? journalEntry.getTitle() : entry.getTitle());
                String msg = entryService.save(entry);
                return new ResponseEntity<>(msg, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
