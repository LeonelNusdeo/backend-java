package com.leonel.expenses_app.controller;

import com.leonel.expenses_app.dto.ExpenseRequestDto;
import com.leonel.expenses_app.model.Expense;
import com.leonel.expenses_app.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void testGetAllExpenses() throws Exception {
        Expense expense = new Expense();
        expense.setName("Verduras");
        expense.setAmount(100.0);
        expense.setCategory("Comida");
        expenseRepository.save(expense);

        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Verduras")));
    }

    @Test
    void testAddExpense() throws Exception {
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setName("Verduras");
        request.setAmount(100.0);
        request.setCategory("Comida");

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Verduras\", \"amount\": 100.0, \"category\": \"Comida\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Verduras")));
    }
}
