public class Expense {
    private int id;
    private double amount;
    private String category;
    private String description;

    public Expense(int id, double amount, String category, String description) {
        if (id < 0 || amount <= 0){
            throw new IllegalArgumentException("Введено неверное значение");
        }
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
