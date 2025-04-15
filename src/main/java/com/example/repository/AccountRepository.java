package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    @Modifying
    @Query(value = "INSERT INTO account (username, password) VALUES (:username, :password)", nativeQuery = true)
    void InsertAccountIntoTable(@Param("username") String username, @Param("password") String password);

    Account findByUsername(String username);
    
    Account findByUsernameAndPassword(String username, String password);

    Account findByAccountId(Integer accountId);
}
