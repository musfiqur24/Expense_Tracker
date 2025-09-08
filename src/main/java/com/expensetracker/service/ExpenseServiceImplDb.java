package com.expensetracker.service;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImplDb implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImplDb(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getExpenseByDay(String date) {
        return expenseRepository.findAll().stream().filter(
                expense -> expense.getDate().equalsIgnoreCase(date)).toList();

    }

    @Override
    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return expenseRepository.findAll().stream().filter(
                expense -> expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month)).toList();
    }

    @Override
    public List<String> getAllExpenseCategories() {
        return expenseRepository.findAll().stream().map(
                Expense::getCategory).distinct().toList();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public boolean updateExpense(Expense updatedExpense) {
        if (expenseRepository.existsById(updatedExpense.getId())) {
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
