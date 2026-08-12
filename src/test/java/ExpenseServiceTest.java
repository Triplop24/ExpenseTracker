import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    private ExpenseService expenseServices;
    private Expense expense;
    @BeforeEach
    void setUp() {
        expenseServices = new ExpenseService();
        expense = new Expense(1, 400, "FOOD", "Покушал в рестике");
        expenseServices.add(expense);
    }

    @Test
    void add() {
        assertEquals(1, expenseServices.getExpenses().size());
    }

    @Test
    void getTotalAmount() {
        expenseServices.add(new Expense(2, 800, "FOOD", "Покушал дома"));
        assertEquals(1200, expenseServices.getTotalAmount());
    }

    @Test
    void remove(){
        expenseServices.add(new Expense(2, 800, "FOOD", "Покушал дома"));
        assertEquals(2, expenseServices.getExpenses().size());
        expenseServices.remove(2);
        assertEquals(1, expenseServices.getExpenses().get(0).getId());
        assertEquals(1, expenseServices.getExpenses().size());
    }

    @Test
    void getByCategory(){
        expense = new Expense(2, 700, "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        expense = new Expense(3, 900, "TRANSPORT", "Проездной на месяц");
        expenseServices.add(expense);
        assertEquals(2, expenseServices.getByCategory("FOOD").size());
        assertEquals(1, expenseServices.getByCategory("TRANSPORT").size());
        assertEquals(0, expenseServices.getByCategory("UNKNOWN").size());
    }

    @Test
    void getByIdExistingExpense(){
        expense = new Expense(2, 700, "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        Expense foundExpense = expenseServices.getById(2);
        assertEquals(expense, foundExpense);

    }

    @Test
    void getByIdUnknownExpense() {
        Expense foundExpense = expenseServices.getById(100);
        assertNull(foundExpense);
    }
}
