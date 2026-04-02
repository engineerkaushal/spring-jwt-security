package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.JournalEntry;

import java.util.List;

public interface JournalEntryService {
    List<JournalEntry> getAlldetails();

    JournalEntry getJournalEntryById(Long id);

    String save(JournalEntry entry);

    String saveMenual(JournalEntry entry, String userName);

    String delete(Long id);

}
