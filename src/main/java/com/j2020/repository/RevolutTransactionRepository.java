/**
 * @author Paulius Staisiunas
 */

package com.j2020.repository;

import com.j2020.model.revolut.RevolutTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevolutTransactionRepository extends JpaRepository<RevolutTransaction, String> {
}
