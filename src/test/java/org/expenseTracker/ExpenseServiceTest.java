package org.expenseTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void add() {
        Expense expense = new Expense(new BigDecimal("800.20"), ExpenseCategory.TRANSPORT, "Проездной");
        expenseService.add(expense);
        assertTrue(expense.getId() > 0);
        Expense saved = expenseRepository.findById(expense.getId()).orElse(null);
        assertNotNull(saved);
        assertEquals(new BigDecimal("800.20"), saved.getAmount());
        assertEquals(ExpenseCategory.TRANSPORT, saved.getCategory());
        assertEquals("Проездной", saved.getDescription());
    }

    @Test
    void addNullExpense() {
        assertThrows(IllegalArgumentException.class, () -> expenseService.add(null));
    }

    @Test
    void delete() {
        Expense expense = new Expense(new BigDecimal("700"), ExpenseCategory.FOOD, "Ужин");
        expenseService.add(expense);
        int id = expense.getId();
        assertNotNull(expenseRepository.findById(id).orElse(null));
        expenseService.deleteById(id);
        assertTrue(expenseRepository.findById(id).isEmpty());
    }

    @Test
    void getByIdExistingExpense() {
        Expense expense = new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед");
        expenseService.add(expense);
        Expense found = expenseService.getById(expense.getId());
        assertNotNull(found);
        assertEquals(expense.getId(), found.getId());
        assertEquals(new BigDecimal("500"), found.getAmount());
        assertEquals(ExpenseCategory.FOOD, found.getCategory());
        assertEquals("Обед", found.getDescription());
    }

    @Test
    void getByIdUnknownExpense() {
        Expense found = expenseService.getById(999999);
        assertNull(found);
    }

    @Test
    void getExpenses() {
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        expenseService.add(new Expense(new BigDecimal("800"), ExpenseCategory.TRANSPORT, "Автобус"));
        List<Expense> expenses = expenseService.getExpenses();
        assertEquals(2, expenses.size());
    }

    @Test
    void getByCategory() {
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        expenseService.add(new Expense(new BigDecimal("700"), ExpenseCategory.FOOD, "Ужин"));
        expenseService.add(new Expense(new BigDecimal("1000"), ExpenseCategory.TRANSPORT, "Проездной"));
        List<Expense> foodExpenses = expenseService.getByCategory("FOOD");
        assertEquals(2, foodExpenses.size());
        for (Expense expense : foodExpenses) {
            assertEquals(ExpenseCategory.FOOD, expense.getCategory());
        }
    }

    @Test
    void getTotalAmount() {
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        expenseService.add(new Expense(new BigDecimal("700"), ExpenseCategory.TRANSPORT, "Проездной"));
        expenseService.add(new Expense(new BigDecimal("1000"), ExpenseCategory.FOOD, "Ужин"));
        BigDecimal total = expenseService.getTotalAmount();
        assertEquals(new BigDecimal("2200"), total);
    }

    @Test
    void getTotalAmountWithNoExpenses() {
        assertEquals(BigDecimal.ZERO, expenseService.getTotalAmount());
    }

    @Test
    void getTotalAmountByCategory() {
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        expenseService.add(new Expense(new BigDecimal("700"), ExpenseCategory.FOOD, "Ужин"));
        expenseService.add(new Expense(new BigDecimal("1000"), ExpenseCategory.TRANSPORT, "Проездной"));
        assertEquals(new BigDecimal("1200"), expenseService.getTotalAmountByCategory("FOOD"));
        assertEquals(new BigDecimal("1000"), expenseService.getTotalAmountByCategory("TRANSPORT"));
    }

    @Test
    void getTotalAmountByCategoryWithNoExpenses() {
        assertEquals(BigDecimal.ZERO, expenseService.getTotalAmountByCategory("FOOD"));
    }

    @Test
    void getByAmountRange() {
        expenseService.add(new Expense(new BigDecimal("300"), ExpenseCategory.FOOD, "Кофе"));
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        expenseService.add(new Expense(new BigDecimal("800"), ExpenseCategory.TRANSPORT, "Такси"));
        expenseService.add(new Expense(new BigDecimal("1500"), ExpenseCategory.TRANSPORT, "Поезд"));
        List<Expense> expenses = expenseService.getByAmountRange(new BigDecimal("400"), new BigDecimal("900"));
        assertEquals(2, expenses.size());
        for (Expense expense : expenses) {
            assertTrue(expense.getAmount().compareTo(new BigDecimal("400")) >= 0);
            assertTrue(expense.getAmount().compareTo(new BigDecimal("900")) <= 0);
        }
    }

    @Test
    void getByAmountRangeIncludesBoundaries() {
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        expenseService.add(new Expense(new BigDecimal("700"), ExpenseCategory.FOOD, "Ужин"));
        List<Expense> expenses = expenseService.getByAmountRange(new BigDecimal("500"), new BigDecimal("700"));
        assertEquals(2, expenses.size());
    }

    @Test
    void getByNotExistingAmountRange() {
        expenseService.add(new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед"));
        List<Expense> expenses = expenseService.getByAmountRange(new BigDecimal("1000"), new BigDecimal("2000"));
        assertTrue(expenses.isEmpty());
    }

    @Test
    void getByIllegalRange() {
        assertThrows(IllegalArgumentException.class, () -> expenseService.getByAmountRange(new BigDecimal("1100"), new BigDecimal("700")));
    }

    @Test
    void updateByExistingId() {
        Expense expense = new Expense(new BigDecimal("500"), ExpenseCategory.FOOD, "Обед");
        expenseService.add(expense);
        int id = expense.getId();
        Expense updatedExpense = new Expense(new BigDecimal("900"), ExpenseCategory.TRANSPORT, "Такси");
        Expense result = expenseService.update(id, updatedExpense);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(new BigDecimal("900"), result.getAmount());
        assertEquals(ExpenseCategory.TRANSPORT, result.getCategory());
        assertEquals("Такси", result.getDescription());
        Expense fromDatabase = expenseRepository.findById(id).orElse(null);
        assertNotNull(fromDatabase);
        assertEquals(new BigDecimal("900"), fromDatabase.getAmount());
        assertEquals(ExpenseCategory.TRANSPORT, fromDatabase.getCategory());
        assertEquals("Такси", fromDatabase.getDescription());
    }

    @Test
    void updateByNotExistingId() {
        Expense expense = new Expense(new BigDecimal("800"), ExpenseCategory.FOOD, "Тест");
        assertThrows(IllegalArgumentException.class, () -> expenseService.update(999999, expense));
    }
}