package com.workload.journalapp.service;

import com.workload.journalapp.entity.User;
import com.workload.journalapp.repository.JournalEntryRepository;
import com.workload.journalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final PasswordEncoder passwordEncoder; // <--- INJECT THIS

    public boolean saveNewUser(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            return false;
        }

        // <--- HASH THE PASSWORD BEFORE SAVING --->
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return true;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
    public void deleteUser(String userName) {

        journalEntryRepository.deleteByUserName(userName);
        userRepository.deleteByUserName(userName);
    }
}