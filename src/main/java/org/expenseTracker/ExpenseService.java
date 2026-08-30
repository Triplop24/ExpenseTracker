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

    public void add(Expense expense) {
        if (expense == null){
            throw new IllegalArgumentException();
        }

        expenseRepository.save(expense);
    }

    public void deleteById(int id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getByCategory(String category){
        return expenseRepository.findByCategory(ExpenseCategory.valueOf(category));
    }

    public BigDecimal getTotalAmount(){
        BigDecimal amount = BigDecimal.ZERO;
        List<Expense> expensesList = getExpenses();

        for (Expense expense : expensesList){
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public BigDecimal getTotalAmountByCategory(String category) {
        List<Expense> filteredExpenses = getByCategory(category);
        BigDecimal amount = BigDecimal.ZERO;
        for (Expense expense : filteredExpenses) {
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    public Expense getById(int id) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isEmpty()) {
            return null;
        }

        return expense.get();
    }

    public Expense update(int id, Expense expense) {
        if (expenseRepository.findById(id).isEmpty()){
            throw new IllegalArgumentException();
        }
        Expense existing = expenseRepository.findById(id).get();

        existing.setAmount(expense.getAmount());
        existing.setCategory(expense.getCategory());
        existing.setDescription(expense.getDescription());

        return expenseRepository.save(existing);
    }

    public List<Expense> getByAmountRange(BigDecimal minAmount, BigDecimal maxAmount){
        if (minAmount.compareTo(maxAmount) > 0){
            throw new IllegalArgumentException();
        }
        return expenseRepository.findByAmountBetween(minAmount, maxAmount);
    }
}
