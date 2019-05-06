package com.mybank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mybank.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {

}
