package org.expenseTracker;

import java.math.BigDecimal;

public class Expense {
    private int id;
    private BigDecimal amount;
    private String category;
    private String description;

    public Expense(int id, BigDecimal amount, String category, String description) {
        if ((id <= 0) || (amount == null) || (amount.compareTo(BigDecimal.ZERO) <= 0)){
            throw new IllegalArgumentException("Введено неверное значение");
        }
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public Expense(BigDecimal amount, String category, String description){
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

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Expense)) {
            return false;
        }
        Expense expense = (Expense) o;
        return id == expense.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
