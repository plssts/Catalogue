/**
 * @author Paulius Staisiunas
 */

package com.j2020.repository;

import com.j2020.model.deutsche.DeutscheTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeutscheTransactionRepository extends JpaRepository<DeutscheTransaction, String> {
}
