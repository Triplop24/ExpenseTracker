import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();

    public void add(Expense expense) {
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

}
