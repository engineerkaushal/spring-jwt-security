package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud")
public class CRUDController {

    @Autowired
    private JournalEntryService service;


    @PostMapping("AllDetails")
    public List<JournalEntry> getAllDetails() {
        List<JournalEntry> details = service.getAlldetails();
        return details;
    }

    @PostMapping("DetailsById/{id}")
    public JournalEntry getDetailsById(@PathVariable Long id) {
        return service.getJournalEntryById(id);
    }

    @PostMapping("SaveDetails")
    public String saveDetails(@RequestBody JournalEntry entry) {
        return service.save(entry);
    }

    @PostMapping("DeleteRecordById/{id}")
    public String deleteRecordById(@PathVariable Long id) {
        return service.delete(id);
    }

}
