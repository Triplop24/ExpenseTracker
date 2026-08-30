package org.expenseTracker;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.getExpenses();
    }

    @GetMapping("/{id}")
    public Expense getExpense(@PathVariable int id) {
        return expenseService.getById(id);
    }


    @PostMapping
    public void addExpense(@RequestBody Expense expense){
        expenseService.add(expense);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@RequestBody Expense expense, @PathVariable int id){
        return expenseService.update(id, expense);
    }

    @DeleteMapping("/{id}")
    public void removeExpense(@PathVariable int id){
        expenseService.deleteById(id);
    }

    @GetMapping("/total-expenses")
    public BigDecimal getTotalAmount(){
        return expenseService.getTotalAmount();
    }


    @GetMapping(value = "/total-expenses", params = "category")
    public BigDecimal getTotalAmountByCategory(@RequestParam String category){
        return expenseService.getTotalAmountByCategory(category);
    }

    @GetMapping(params = "category")
    public List<Expense> getByCategory(@RequestParam String category){
        return expenseService.getByCategory(category);
    }

    @GetMapping(value = "/total-expenses", params = {"minAmount","maxAmount"})
    public List<Expense> getByAmountRange(@RequestParam BigDecimal minAmount, @RequestParam BigDecimal maxAmount){
        return expenseService.getByAmountRange(minAmount, maxAmount);
    }

}
