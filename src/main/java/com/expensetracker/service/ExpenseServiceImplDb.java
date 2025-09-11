package com.expensetracker.service;

import com.expensetracker.model.AppUser;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImplDb implements ExpenseService {
    private final UserService userService;
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImplDb(UserService userService, ExpenseRepository expenseRepository) {
        this.userService = userService;
        this.expenseRepository = expenseRepository;
    }


    @Override
    public List<Expense> getAllUserExpenses(Long userId) {
        return new ArrayList<>(expenseRepository.findByUserIdOrderByDateDesc(userId));
    }

    @Override
    public List<Expense> getExpenseByDay(String date, long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(expense -> expense.getDate().equals(date)).toList();
    }

    @Override
    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(
                expense -> expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month)).toList();
    }

    @Override
    public List<String> getAllExpenseCategories(Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().map(
                Expense::getCategory).distinct().toList();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id, Long userId) {
        return expenseRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Expense addExpense(Expense expense, Long userId) {
        Optional<AppUser> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public boolean updateExpense(Expense updatedExpense, Long userId) {
        Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(updatedExpense.getId(), userId);
        if (existingExpense.isPresent()) {
            updatedExpense.setUser(existingExpense.get().getUser());
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id, Long userId) {
        Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(id, userId);
        if (existingExpense.isPresent()) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
