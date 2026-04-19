package com.workload.journalapp.service;

import com.workload.journalapp.dto.JournalEntryRequest;
import com.workload.journalapp.dto.JournalEntryResponse;
import com.workload.journalapp.entity.JournalEntry;
import com.workload.journalapp.entity.User;
import com.workload.journalapp.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    // FIX: Added 'String userName' to the parameters here
    public JournalEntryResponse saveEntry(JournalEntryRequest request, String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        JournalEntry entry = new JournalEntry();
        entry.setTitle(request.title());
        entry.setContent(request.content());
        entry.setDate(LocalDateTime.now());
        entry.setUserName(userName); // Now Java knows what this is

        JournalEntry savedEntry = journalEntryRepository.save(entry);
        return mapToResponse(savedEntry);
    }

    public List<JournalEntryResponse> getAllByUserName(String userName) {
        return journalEntryRepository.findByUserName(userName).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponse> getAll() {
        return journalEntryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<JournalEntryResponse> getById(String id) {
        return journalEntryRepository.findById(id)
                .map(this::mapToResponse);
    }

    public boolean deleteById(String id) {
        if (journalEntryRepository.existsById(id)) {
            journalEntryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<JournalEntryResponse> updateEntry(String id, JournalEntryRequest request) {
        Optional<JournalEntry> optionalEntry = journalEntryRepository.findById(id);

        if (optionalEntry.isPresent()) {
            JournalEntry existingEntry = optionalEntry.get();

            if (request.title() != null && !request.title().isEmpty()) {
                existingEntry.setTitle(request.title());
            }
            if (request.content() != null && !request.content().isEmpty()) {
                existingEntry.setContent(request.content());
            }

            JournalEntry updatedEntry = journalEntryRepository.save(existingEntry);
            return Optional.of(mapToResponse(updatedEntry));
        }

        return Optional.empty();
    }

    public List<JournalEntryResponse> searchByTitle(String keyword) {
        return journalEntryRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private JournalEntryResponse mapToResponse(JournalEntry entry) {
        return new JournalEntryResponse(
                entry.getId(),
                entry.getTitle(),
                entry.getContent(),
                entry.getDate()
        );
    }
}