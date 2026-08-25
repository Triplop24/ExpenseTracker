import java.math.BigDecimal;
import java.util.*;

public class ExpenseService {
    private Map<Integer, Expense> expenses = new HashMap<>();

    public void add(Expense expense) {
        if (expense == null){
            throw new IllegalArgumentException();
        }
        if (expenses.containsKey(expense.getId())){
            throw new IllegalArgumentException();
        }
        expenses.put(expense.getId(), expense);
    }

    public void remove(int id) {
        expenses.remove(id);
    }

    public List<Expense> getByCategory(String category){
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses.values()) {
            if (expense.getCategory().equals(category)) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }

    public BigDecimal getTotalAmount(){
        BigDecimal amount = BigDecimal.ZERO;
        for (Expense expense : expenses.values()){
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    public Map<Integer, Expense> getExpenses() {
        return Map.copyOf(expenses);
    }

    public Optional<Expense> getById(int id) {
        return Optional.ofNullable(expenses.get(id));
    }

    public BigDecimal getTotalAmountByCategory(String category) {
        List<Expense> filteredExpenses = getByCategory(category);
        BigDecimal amount = BigDecimal.ZERO;
        for (Expense expense : filteredExpenses) {
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    public void update(Expense expense) {
        if (expense == null){
            throw new IllegalArgumentException();
        }

        if (getById(expense.getId()).isPresent()){
            expenses.put(expense.getId(), expense);
            return;
        }
        throw new IllegalArgumentException();
    }

    public List<Expense> getByAmountRange(BigDecimal minAmount, BigDecimal maxAmount){
        if (minAmount.compareTo(maxAmount) > 0){
            throw new IllegalArgumentException();
        }
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses.values()){
            if (expense.getAmount().compareTo(minAmount) >= 0 && expense.getAmount().compareTo(maxAmount) <= 0){
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }
}
