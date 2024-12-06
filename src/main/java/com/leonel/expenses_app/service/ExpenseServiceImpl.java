package com.leonel.expenses_app.service;

import com.leonel.expenses_app.dto.ExpenseRequestDto;
import com.leonel.expenses_app.dto.ExpenseResponseDto;
import com.leonel.expenses_app.model.Expense;
import com.leonel.expenses_app.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<ExpenseResponseDto> getAllExpenses() {
        logger.info("Fetching all expenses.");
        List<ExpenseResponseDto> expenses = expenseRepository.findAll().stream()
                .map(expense -> new ExpenseResponseDto(expense))
                .collect(Collectors.toList());
        logger.info("Found {} expenses.", expenses.size());
        return expenses;
    }

    @Override
    public ExpenseResponseDto addExpense(ExpenseRequestDto request) {
        logger.info("Adding new expense with name: {}", request.getName());
        Expense expense = new Expense();
        expense.setName(request.getName());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expenseRepository.save(expense);
        logger.info("Expense added with ID: {}", expense.getId());
        return new ExpenseResponseDto(expense);
    }

    @Override
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto request) {
        logger.info("Updating expense with ID: {}", id);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expense.setName(request.getName());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expenseRepository.save(expense);
        logger.info("Expense with ID: {} updated.", expense.getId());
        return new ExpenseResponseDto(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        logger.info("Deleting expense with ID: {}", id);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
        logger.info("Expense with ID: {} deleted.", id);
    }
}
