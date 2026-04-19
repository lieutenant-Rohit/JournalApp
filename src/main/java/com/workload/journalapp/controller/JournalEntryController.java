package com.workload.journalapp.controller;

import com.workload.journalapp.dto.JournalEntryRequest;
import com.workload.journalapp.dto.JournalEntryResponse;
import com.workload.journalapp.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    // FIX 1: Added userName to the path and passed it to the service
    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntryResponse> createEntry(
            @PathVariable String userName,
            @Valid @RequestBody JournalEntryRequest request) {

        JournalEntryResponse response = journalEntryService.saveEntry(request, userName);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // FIX 2: Added the endpoint to fetch entries for a specific user
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<JournalEntryResponse>> getAllEntriesByUser(@PathVariable String userName) {
        List<JournalEntryResponse> entries = journalEntryService.getAllByUserName(userName);

        if (entries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    // --- Standard CRUD Methods below ---

    @GetMapping
    public ResponseEntity<List<JournalEntryResponse>> getAllEntries() {
        List<JournalEntryResponse> entries = journalEntryService.getAll();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntryResponse> getEntryById(@PathVariable String myId) {
        return journalEntryService.getById(myId)
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteEntryById(@PathVariable String myId) {
        boolean isDeleted = journalEntryService.deleteById(myId);

        if (isDeleted) {
            return new ResponseEntity<>("Entry Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Entry Not Found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntryResponse> updateEntryById(
            @PathVariable String myId,
            @Valid @RequestBody JournalEntryRequest request) {

        return journalEntryService.updateEntry(myId, request)
                .map(updatedEntry -> new ResponseEntity<>(updatedEntry, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<JournalEntryResponse>> searchEntriesByTitle(@RequestParam String title) {
        List<JournalEntryResponse> results = journalEntryService.searchByTitle(title);

        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}