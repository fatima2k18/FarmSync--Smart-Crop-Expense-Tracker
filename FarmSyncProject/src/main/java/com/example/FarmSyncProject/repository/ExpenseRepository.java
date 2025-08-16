package com.example.FarmSyncProject.repository;

import com.example.FarmSyncProject.dto.MonthlyExpenseDto;
import com.example.FarmSyncProject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    @Query(value = "SELECT DATE_FORMAT(e.date, '%Y-%m') AS month, SUM(e.amount) AS totalExpense " +
            "FROM expense e " +
            "WHERE e.user_id = :userId AND e.date IS NOT NULL " +
            "GROUP BY DATE_FORMAT(e.date, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(e.date, '%Y-%m')",
            nativeQuery = true)
    List<Object[]> findMonthlySummaryRaw(@Param("userId") Long userId);

//    @Query("SELECT new com.example.FarmSyncProject.dto.MonthlyExpenseDto(" +
//            "FUNCTION('DATE_FORMAT', e.date, '%Y-%m'), SUM(e.amount)) " +
//            "FROM Expense e WHERE e.user.id = :userId AND e.date IS NOT NULL " +
//            "GROUP BY FUNCTION('DATE_FORMAT', e.date, '%Y-%m')")
//    List<MonthlyExpenseDto> findMonthlySummary(@Param("userId") Long userId);


}
