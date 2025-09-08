package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.utils.ExpenseDataLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getExpensesByDay(@PathVariable String date) {
        try {
            List<Expense> expenses = expenseService.getExpenseByDay(date);
            return ResponseEntity.ok(expenses);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/expenses/category/{category}/month")
    public List<Expense> getExpensesByCategoryAndMonth(@PathVariable String category, @RequestParam String month) {
        return ExpenseDataLoader.getExpenses().stream()
                .filter(expense -> expense.getCategory().equalsIgnoreCase(category)
                        && expense.getDate().startsWith(month))
                .collect(Collectors.toList());
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpense();
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense newExpense = expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.OK).body(newExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        try {
            updatedExpense.setId(id);
            boolean updated = expenseService.updateExpense(updatedExpense);
            if (updated) {
                return ResponseEntity.ok(updatedExpense);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        try {
            boolean deleted = expenseService.deleteExpense(id);
            if (deleted) {
                return ResponseEntity.ok("Expense with id " + id + "deleted sucesfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Expense with id " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting expense with id " + id);
        }
    }
}
