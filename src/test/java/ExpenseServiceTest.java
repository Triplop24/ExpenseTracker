import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Optional;

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
        Optional<Expense> expenseOptional = expenseServices.getById(2);
        assertTrue(expenseOptional.isPresent());
        assertEquals(expense, expenseOptional.get());
    }

    @Test
    void getByIdUnknownExpense() {
        Optional<Expense> expenseOptional = expenseServices.getById(1000);
        assertTrue(expenseOptional.isEmpty());
    }

    @Test
    void getTotalAmountByCategory(){
        expense = new Expense(2, 700, "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        expense = new Expense(3, 900, "TRANSPORT", "Проездной на месяц");
        expenseServices.add(expense);
        assertEquals(1100, expenseServices.getTotalAmountByCategory("FOOD"));
        assertEquals(900, expenseServices.getTotalAmountByCategory("TRANSPORT"));
        assertEquals(0, expenseServices.getTotalAmountByCategory("UNKNOWN"));
    }

    @Test
    void illegalArgumentsExpenseConstructor(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Expense expense1 = new Expense(-1, -100, "FFF", "FFF");
            }
        });
    }

    @Test
    void addDuplicateId(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                expenseServices.add(new Expense(1, 10000, "TRANSPORT", "Test Description"));
            }
        });
    }

    @Test
    void updateByExistingId(){
        expenseServices.update(new Expense(1, 1000, "FOOD", "Покушал в рестике"));
        assertEquals(1000, expenseServices.getById(1).get().getAmount());
    }

    @Test
    void updateByNotExistingId(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                expenseServices.update(new Expense(3, 1000, "FOOD", "Покушал в рестике"));
            }
        });
    }

    @Test
    void updateArgumentNull(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                expenseServices.update(null);
            }
        });
    }

    @Test
    void getByExistingAmountRange(){
        expense = new Expense(2, 700, "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        expense = new Expense(3, 1120, "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        List<Expense> filteredEx1 = expenseServices.getByAmountRange(400, 700);
        List<Expense> filteredEx2 = expenseServices.getByAmountRange(700, 700);
        assertEquals(2, filteredEx1.size());
        assertEquals(1, filteredEx2.size());
    }

    @Test
    void getByNotExistingAmountRange(){
        expense = new Expense(2, 700, "FOOD", "Покушал в магазине");
        expenseServices.add(expense);
        List<Expense> filteredEx = expenseServices.getByAmountRange(900, 1700);

        assertEquals(0, filteredEx.size());
    }

    @Test
    void getByIllegalRange(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                expenseServices.getByAmountRange(1100, 700);
            }
        });
    }
}
