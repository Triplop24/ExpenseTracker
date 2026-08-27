package org.expenseTracker;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public int add(Expense expense) {
        if (expense == null){
            throw new IllegalArgumentException();
        }

        return expenseRepository.addExpense(expense);
    }

    public void remove(int id) {
        expenseRepository.removeExpense(id);
    }

    public List<Expense> getByCategory(String category){
        return expenseRepository.getByCategory(category);
    }

    public BigDecimal getTotalAmount(){
        BigDecimal amount = BigDecimal.ZERO;
        List<Expense> expensesList = expenseRepository.getAllExpenses();

        for (Expense expense : expensesList){
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    public List<Expense> getExpenses() {
        return expenseRepository.getAllExpenses();
    }

    public BigDecimal getTotalAmountByCategory(String category) {
        List<Expense> filteredExpenses = getByCategory(category);
        BigDecimal amount = BigDecimal.ZERO;
        for (Expense expense : filteredExpenses) {
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    public Expense getById(int id){
        return expenseRepository.getById(id);
    }

    public int update(Expense expense) {
        if (expense == null){
            throw new IllegalArgumentException();
        }

        return expenseRepository.updateExpense(expense);
    }

    public List<Expense> getByAmountRange(BigDecimal minAmount, BigDecimal maxAmount){
        if (minAmount.compareTo(maxAmount) > 0){
            throw new IllegalArgumentException();
        }
        return expenseRepository.getByAmountRange(minAmount, maxAmount);
    }
}
