import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();

    public void add(Expense expense) {
        for (Expense expense1 : expenses){
            if (expense.equals(expense1)){
                throw new IllegalArgumentException("Такой id уже существует, пожалуйста, проверьте корректность вводимых данных");
            }
        }
        expenses.add(expense);
    }

    public void remove(int id) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.remove(i);
                break;
            }
        }
    }

    public List<Expense> getByCategory(String category){
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses ;
    }

    public double getTotalAmount(){
        double amount = 0;
        for (Expense expense : expenses) {
            amount+=expense.getAmount();
        }
        return amount;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public Optional<Expense> getById(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return Optional.of(expense);
            }
        }
        return Optional.empty();
    }

    public double getTotalAmountByCategory(String category) {
        List<Expense> filteredExpenses = getByCategory(category);
        double amount = 0;
        for (Expense expense : filteredExpenses) {
            amount += expense.getAmount();
        }
        return amount;
    }

    public void update(Expense expense) {
        if (expense == null){
            throw new IllegalArgumentException();
        }
        Optional<Expense> foundExpenseOpt = getById(expense.getId());
        if (foundExpenseOpt.isPresent()){
            Expense foundExpense = foundExpenseOpt.get();
            remove(foundExpense.getId());
            add(expense);
            return;
        }
        throw new IllegalArgumentException();
    }
}
