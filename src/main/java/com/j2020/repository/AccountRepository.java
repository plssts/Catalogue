package com.j2020.repository;

import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<GeneralAccount, String> {
    List<GeneralAccount> findByBank(Bank bank);
}
