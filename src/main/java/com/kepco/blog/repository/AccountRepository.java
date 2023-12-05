package com.kepco.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepco.blog.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    Account findByUserIdAndPassword(String userId, String password);
    Account findByUserId(String userId);
}
