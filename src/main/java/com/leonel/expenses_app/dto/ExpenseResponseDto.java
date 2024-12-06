package com.leonel.expenses_app.dto;

import com.leonel.expenses_app.model.Expense;
import lombok.Data;

@Data
public class ExpenseResponseDto {
    private Long id;
    private String name;
    private Double amount;
    private String category;

    // Constructor to map from Expense entity
    public ExpenseResponseDto(Expense expense) {
        this.id = expense.getId();
        this.name = expense.getName();
        this.amount = expense.getAmount();
        this.category = expense.getCategory();
    }
}
