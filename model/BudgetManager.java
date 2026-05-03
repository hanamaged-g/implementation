package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all budget-related business logic including daily limits,
 * spending calculations, category summaries, and budget tracking rules.
 *
 * This class acts as the main service layer for financial computations.
 *
 * @author Amira Ahmed
 * @version 1.0
 */
public class BudgetManager {

    /**
     * FR-2
     * Calculates the initial daily budget limit based on the total budget
     * and the number of days in the budget cycle.
     *
     * @param cycle the budget cycle containing budget and dates
     * @return initial daily limit, or 0 if invalid date range
     */
    public double calculateInitialDailyLimit(BudgetCycle cycle) {

        long days = ChronoUnit.DAYS.between(
                cycle.getStartDate(),
                cycle.getEndDate()
        ) + 1;

        if (days <= 0) return 0;

        return cycle.getTotalBudget() / days;
    }

    /**
     * Calculates total spending from all transactions.
     *
     * @param transactions list of transactions
     * @return total spent amount
     */
    public double calculateTotalSpent(List<Transaction> transactions) {

        double total = 0;

        for (Transaction t : transactions) {
            total += t.getAmount();
        }

        return total;
    }

    /**
     * FR-4 / US3
     * Calculates the number of remaining days in the budget cycle.
     *
     * @param cycle the budget cycle
     * @return remaining days (0 if the cycle has ended)
     */
    public int getRemainingDays(BudgetCycle cycle) {

        long days = ChronoUnit.DAYS.between(
                LocalDate.now(),
                cycle.getEndDate()
        ) + 1;

        return (int) Math.max(days, 0);
    }

    /**
     * Helper method:
     * Calculates total spending for today only.
     *
     * @param transactions list of transactions
     * @return total amount spent today
     */
    public double calculateSpentToday(List<Transaction> transactions) {

        LocalDate today = LocalDate.now();
        double total = 0;

        for (Transaction t : transactions) {
            if (t.getTimestamp().toLocalDate().equals(today)) {
                total += t.getAmount();
            }
        }

        return total;
    }

    /**
     * FR-4 (Dynamic Safe Daily Limit)
     * Calculates today's remaining safe spending limit.
     * It subtracts today's spending from the initial daily limit.
     *
     * @param cycle the budget cycle
     * @param transactions list of transactions
     * @return remaining daily limit (minimum 0)
     */
    public double calculateDailyLimit(BudgetCycle cycle, List<Transaction> transactions) {

        double initialDailyLimit = calculateInitialDailyLimit(cycle);
        double spentToday = calculateSpentToday(transactions);

        return Math.max(initialDailyLimit - spentToday, 0);
    }

    /**
     * FR-6
     * Checks whether spending has reached 80% of the total budget.
     *
     * @param cycle budget cycle
     * @param transactions list of transactions
     * @return true if 80% threshold is reached, otherwise false
     */
    public boolean isEightyPercentReached(BudgetCycle cycle, List<Transaction> transactions) {

        return calculateTotalSpent(transactions)
                >= 0.8 * cycle.getTotalBudget();
    }

    /**
     * FR-5
     * Calculates total spending grouped by category.
     *
     * @param transactions list of transactions
     * @return map of category names to total spent amounts
     */
    public Map<String, Double> categoryTotals(List<Transaction> transactions) {

        Map<String, Double> map = new HashMap<>();

        for (Transaction t : transactions) {

            map.put(
                    t.getCategory(),
                    map.getOrDefault(t.getCategory(), 0.0) + t.getAmount()
            );
        }

        return map;
    }
    public double getSavedAmount(double totalBudget, double totalSpent) {
     return totalBudget - totalSpent;
    }
    public double getGoalProgress(double saved, double goal) {
    if (goal <= 0) return 0;
    return (saved / goal) * 100;
    }
}
