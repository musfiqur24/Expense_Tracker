package com.expensetracker.controller;

import com.expensetracker.model.AppUser;
import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getExpensesByDay(@PathVariable String date, Authentication authentication) {

        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        List<Expense> expenses = expenseService.getExpenseByDay(date, user.getId());
        return ResponseEntity.ok(expenses);

    }

    @GetMapping("/expenses/category/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndMonth(@PathVariable String category, @RequestParam String month, Authentication authentication) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        List<Expense> expenses = expenseService.getExpenseByCategoryAndMonth(category, month, user.getId());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getAllExpenseCategories(Authentication authentication) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        List<String> categories = expenseService.getAllExpenseCategories(user.getId());
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        return ResponseEntity.ok(expenseService.getExpenseById(id, user.getId()));
    }


    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Authentication authentication) {

        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        Expense newExpense = expenseService.addExpense(expense, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(newExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense, Authentication authentication) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        try {
            updatedExpense.setId(id);
            boolean updated = expenseService.updateExpense(updatedExpense, user.getId());
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
    public ResponseEntity<String> deleteExpense(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        try {
            boolean deleted = expenseService.deleteExpense(id, user.getId());
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
