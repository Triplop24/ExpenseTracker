package org.expenseTracker;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseRepository {

    public List<Expense> getAllExpenses(){
        List<Expense> expenseList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses;");
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                BigDecimal amount = resultSet.getBigDecimal(2);
                String category = resultSet.getString(3);
                String description = resultSet.getString(4);

                Expense expense = new Expense(id, amount, category, description);
                expenseList.add(expense);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return expenseList;
    }

    public int addExpense(Expense expense){
        int id = -1;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO expenses (amount, category, description) VALUES (?, ?, ?);",
                     PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            BigDecimal amount = expense.getAmount();
            String description = expense.getDescription();
            String category = expense.getCategory();
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, description);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public int updateExpense(Expense expense) {
        int result = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE expenses SET amount = ?, category = ?, description = ? WHERE id = ?;");
        ) {
            preparedStatement.setBigDecimal(1, expense.getAmount());
            preparedStatement.setString(2, expense.getCategory());
            preparedStatement.setString(3, expense.getDescription());
            preparedStatement.setInt(4, expense.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void removeExpense(int id){
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM expenses WHERE id = ?");
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Expense> getByCategory(String categoryExpenses) {
        List<Expense> expenses = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM expenses WHERE category = ?");
        ) {
            preparedStatement.setString(1, categoryExpenses);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                BigDecimal amount = resultSet.getBigDecimal(2);
                String category = resultSet.getString(3);
                String description = resultSet.getString(4);
                expenses.add(new Expense(id, amount, category, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    public List<Expense> getByAmountRange(BigDecimal minAmount, BigDecimal maxAmount){
        List<Expense> filteredExpenses = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM expenses WHERE amount>= ? and amount<=?;");
        ) {
            preparedStatement.setBigDecimal(1, minAmount);
            preparedStatement.setBigDecimal(2, maxAmount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                BigDecimal amount = resultSet.getBigDecimal(2);
                String category = resultSet.getString(3);
                String description = resultSet.getString(4);
                filteredExpenses.add(new Expense(id, amount, category, description));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return filteredExpenses;
    }

    public Expense getById(int idExpense) {
        Expense expense = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM  expenses WHERE id = ?;");
        ) {
            preparedStatement.setInt(1, idExpense);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                BigDecimal amount = resultSet.getBigDecimal(2);
                String category = resultSet.getString(3);
                String description = resultSet.getString(4);
                expense = new Expense(id,amount, category, description);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return expense;
    }


}
