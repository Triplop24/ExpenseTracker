import org.expenseTracker.Expense;
import org.expenseTracker.ExpenseRepository;
import org.expenseTracker.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    private ExpenseService expenseServices;
    private Expense expense;


    @BeforeEach
    void setUp() {
        expenseServices = new ExpenseService(new ExpenseRepository());
    }

    @Test
    void add() {
        expense = new Expense(new BigDecimal("800.20"), "TRANSPORT", "Покушал в рестике");

        assertTrue(expenseServices.add(expense) > 0);
    }

    @Test
    void getTotalAmount() {
        assertEquals(new BigDecimal("1200.20"), expenseServices.getTotalAmount());
    }

    @Test
    void remove(){
        expense = new Expense(new BigDecimal("700"), "FOOD", "Покушал в магазине");
        int id = expenseServices.add(expense);
        expenseServices.remove(id);
        assertNull(expenseServices.getById(id));
    }

    @Test
    void getByCategory(){
        expense = new Expense(new BigDecimal("700"), "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        expense = new Expense(new BigDecimal("1200"), "TRANSPORT", "Pass");
        expenseServices.add(expense);
        boolean flag = true;
        List<Expense> filtered = expenseServices.getByCategory("FOOD");
        for (Expense expense1: filtered){
            if (!expense1.getCategory().equals("FOOD")){
                flag = false;
                break;
            }
        }
        assertEquals(6, filtered.size());
        assertTrue(flag);
    }

    @Test
    void getByIdExistingExpense(){
        assertNotNull(expenseServices.getById(1));
    }

    @Test
    void getByIdUnknownExpense() {
        assertNull(expenseServices.getById(999));
    }

    @Test
    void getTotalAmountByCategory(){
        expense = new Expense(new BigDecimal("700"), "FOOD", "Покушал на яхте");
        expenseServices.add(expense);
        expense = new Expense(new BigDecimal("900"), "TRANSPORT", "Проездной на месяц");
        expenseServices.add(expense);
        assertEquals(new BigDecimal("1800.00"), expenseServices.getTotalAmountByCategory("FOOD"));
        assertEquals(new BigDecimal("2100.00"), expenseServices.getTotalAmountByCategory("TRANSPORT"));

    }

    @Test
    void illegalArgumentsExpenseConstructor(){
        assertThrows(IllegalArgumentException.class, () -> {
            Expense expense1 = new Expense(-1, new BigDecimal("-100"), "FFF", "FFF");
        });
    }

    @Test
    void updateByExistingId(){
        int result = 0;
        result = expenseServices.update(new Expense(1, new BigDecimal("800"), "FFF", "FFF"));
        assertEquals(1, result);
    }

    @Test
    void updateByNotExistingId() {
        int result = 0;
        result = expenseServices.update(new Expense(999, new BigDecimal("800"), "FFF", "FFF"));
        assertEquals(0, result);
    }
    @Test
    void updateArgumentNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            expenseServices.update(null);
        });
    }

    @Test
    void getByExistingAmountRange(){
        expense = new Expense(new BigDecimal("500"), "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        expense = new Expense(new BigDecimal("1120"), "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        List<Expense> filteredEx1 = expenseServices.getByAmountRange(new BigDecimal("400"), new BigDecimal("700"));
        List<Expense> filteredEx2 = expenseServices.getByAmountRange(new BigDecimal("700"), new BigDecimal("700"));
        assertEquals(3, filteredEx1.size());
        assertEquals(2, filteredEx2.size());
    }

    @Test
    void getByNotExistingAmountRange(){
        expense = new Expense(new BigDecimal("700"), "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        List<Expense> filteredEx = expenseServices.getByAmountRange(new BigDecimal("1300"), new BigDecimal("1700"));

        assertEquals(0, filteredEx.size());
    }

    @Test
    void getByIllegalRange(){
        assertThrows(IllegalArgumentException.class, () -> {
            expenseServices.getByAmountRange(new BigDecimal("1100"), new BigDecimal("700"));
        });
    }

    @Test
    void addNullExpense(){
        assertThrows(IllegalArgumentException.class, () -> expenseServices.add(null));
    }
}