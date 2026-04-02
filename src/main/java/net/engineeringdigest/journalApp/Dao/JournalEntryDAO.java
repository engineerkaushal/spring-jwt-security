package net.engineeringdigest.journalApp.Dao;

import net.engineeringdigest.journalApp.Entity.JournalEntry;

import java.util.List;

public interface JournalEntryDAO {

    List<JournalEntry> getAlldetails();

    JournalEntry getJournalEntryById(Long id);

    String save(JournalEntry entry);

    JournalEntry saveMenual(JournalEntry entry);

    String delete(Long id);
}
