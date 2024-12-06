package com.leonel.expenses_app.service;

import com.leonel.expenses_app.dto.ExpenseRequestDto;
import com.leonel.expenses_app.dto.ExpenseResponseDto;
import com.leonel.expenses_app.model.Expense;
import com.leonel.expenses_app.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddExpense() {
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setName("Verduras");
        request.setAmount(100.0);
        request.setCategory("Comida");

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setName("Verduras");
        expense.setAmount(100.0);
        expense.setCategory("Comida");

        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseResponseDto response = expenseService.addExpense(request);

        assertNotNull(response);
        assertEquals("Verduras", response.getName());
        assertEquals(100.0, response.getAmount());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testGetAllExpenses() {
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setName("Verduras");
        expense1.setAmount(100.0);
        expense1.setCategory("Comida");

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setName("Alquiler");
        expense2.setAmount(500.0);
        expense2.setCategory("Casa");

        when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses();

        assertNotNull(expenses);
        assertEquals(2, expenses.size());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void testUpdateExpense_Success() {
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setName("Updated Alquiler");
        request.setAmount(600.0);
        request.setCategory("Casa");

        Expense existingExpense = new Expense();
        existingExpense.setId(1L);
        existingExpense.setName("Alquiler");
        existingExpense.setAmount(500.0);
        existingExpense.setCategory("Casa");

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(existingExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(existingExpense);

        ExpenseResponseDto response = expenseService.updateExpense(1L, request);

        assertNotNull(response);
        assertEquals("Updated Alquiler", response.getName());
        assertEquals(600.0, response.getAmount());
        verify(expenseRepository, times(1)).findById(1L);
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdateExpense_NotFound() {
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setName("Updated Alquiler");
        request.setAmount(600.0);
        request.setCategory("Casa");

        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> expenseService.updateExpense(1L, request));
        verify(expenseRepository, times(1)).findById(1L);
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @Test
    void testDeleteExpense_Success() {
        when(expenseRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> expenseService.deleteExpense(1L));
        verify(expenseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteExpense_NotFound() {
        when(expenseRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> expenseService.deleteExpense(1L));
        verify(expenseRepository, never()).deleteById(any());
    }
}
