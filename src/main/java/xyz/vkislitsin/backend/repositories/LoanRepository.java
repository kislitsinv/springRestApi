package xyz.vkislitsin.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.vkislitsin.backend.entities.Loan;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByBorrowerId(Long userId);

    List<Loan> findAllByDebtorId(Long userId);

    @Query(value = "SELECT MAX(id) FROM Loan", nativeQuery = true)
    Long findMaxId();
}
