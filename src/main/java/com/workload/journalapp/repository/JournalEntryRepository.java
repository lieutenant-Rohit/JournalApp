package com.workload.journalapp.repository;

import com.workload.journalapp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {
    List<JournalEntry> findByTitleContainingIgnoreCase(String Keyword);
    List<JournalEntry> findByUserName(String userName);
    void deleteByUserName(String userName);
}
