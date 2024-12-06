package com.leonel.expenses_app.service;

import com.leonel.expenses_app.dto.ExpenseRequestDto;
import com.leonel.expenses_app.dto.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto addExpense(ExpenseRequestDto request);
    List<ExpenseResponseDto> getAllExpenses();
    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto request);
    void deleteExpense(Long id);
}
