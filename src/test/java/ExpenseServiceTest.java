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
}