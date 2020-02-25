package com.j2020.repository;

import com.j2020.model.Bank;
import com.j2020.model.GeneralAccount;
import com.j2020.model.GeneralTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<GeneralTransaction, String> {
    List<GeneralTransaction> findByBank(Bank bank);
}
