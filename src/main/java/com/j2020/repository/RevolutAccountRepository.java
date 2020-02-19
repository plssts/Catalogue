package com.j2020.repository;

import com.j2020.model.revolut.RevolutAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevolutAccountRepository extends JpaRepository<RevolutAccount, String> {
}
