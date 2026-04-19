package com.workload.journalapp.repository;

import com.workload.journalapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserName(String userName);
    void deleteByUserName(String userName);

}