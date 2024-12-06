package com.leonel.expenses_app.repository;

import com.leonel.expenses_app.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}