package com.leonel.expenses_app.dto;

public class ExpenseRequestDto {
    private String name;
    private Double amount;
    private String category;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Optional: Override toString(), equals(), and hashCode() if needed
}
