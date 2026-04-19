package com.workload.journalapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class health_check
{
    @GetMapping("/health")
    public String HealthCheckController()
    {
        return "Everything is OK!!!...";
    }
}
