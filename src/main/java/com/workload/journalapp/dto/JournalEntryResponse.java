package com.workload.journalapp.dto;

import java.time.LocalDateTime;

public record JournalEntryResponse(
        String id,
        String title,
        String content,
        LocalDateTime date
) {}