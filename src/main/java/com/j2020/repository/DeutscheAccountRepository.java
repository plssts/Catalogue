package com.j2020.repository;

import com.j2020.model.deutsche.DeutscheAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeutscheAccountRepository extends JpaRepository<DeutscheAccount, String> {
}
