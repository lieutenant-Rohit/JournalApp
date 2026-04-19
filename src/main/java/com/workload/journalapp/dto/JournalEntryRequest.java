package com.workload.journalapp.dto;

import jakarta.validation.constraints.NotBlank;

public record JournalEntryRequest(
        @NotBlank(message = "Title is mandatory and cannot be empty")
        String title,

        String content
){}