package com.example.FarmSyncProject.dto;
public class MonthlyExpenseDto {
    private String month;
    private Double totalExpense;
    public MonthlyExpenseDto(String month, Double totalExpense) {
        this.month = month;
        this.totalExpense = totalExpense;
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {

        this.month = month;
    }
    public Double getTotalExpense() {

        return totalExpense;
    }
    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
