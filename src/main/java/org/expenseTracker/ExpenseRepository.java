package org.expenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findByCategory(ExpenseCategory category);

    List<Expense> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
}
