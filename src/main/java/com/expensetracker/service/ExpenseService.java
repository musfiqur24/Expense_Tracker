package com.expensetracker.service;

import com.expensetracker.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    List<Expense> getExpenseByDay(String date);
    List<Expense> getAllExpense();
    List<Expense> getExpenseByCategoryAndMonth(String category, String month);
    List<String> getAllExpenseCategories();
    Optional<Expense> getExpenseById(Long id);
    Expense addExpense(Expense expense);
    boolean updateExpense (Expense expense);
    boolean deleteExpense (Long id);
}
