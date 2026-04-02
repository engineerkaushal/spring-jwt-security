package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("Id/{myId}")
    public JournalEntry getJurnalEntryById(@PathVariable long myId) {
        return journalEntries.get(myId);
    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntries.put(journalEntry.getId(), journalEntry);
        return "Data saved successfully";
    }

    @DeleteMapping("Id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId) {
        return journalEntries.remove(myId);
    }

    @PutMapping("Id/{myId}")
    public JournalEntry updateJournalEntryById(@RequestBody JournalEntry journalEntry, @PathVariable Long myId) {
        return journalEntries.put(myId, journalEntry);
    }
}
