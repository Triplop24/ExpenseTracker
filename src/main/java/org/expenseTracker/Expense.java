package org.expenseTracker;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;
    private String description;

    public Expense() {}

    public Expense(int id, BigDecimal amount, ExpenseCategory category, String description) {
        if ((id <= 0) || (amount == null) || (amount.compareTo(BigDecimal.ZERO) <= 0)){
            throw new IllegalArgumentException("Введено неверное значение");
        }

        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public Expense(BigDecimal amount, ExpenseCategory category, String description) {
        if ((amount == null) || (amount.compareTo(BigDecimal.ZERO) <= 0)){
            throw new IllegalArgumentException("Введено неверное значение");
        }

        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
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
