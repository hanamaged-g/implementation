package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class BudgetManager {

    // FR-2
    public double calculateInitialDailyLimit(BudgetCycle cycle) {

        long days = ChronoUnit.DAYS.between(
                cycle.getStartDate(),
                cycle.getEndDate()
        ) + 1;

        if (days <= 0) return 0;

        return cycle.getTotalBudget() / days;
    }

    // total spent
    public double calculateTotalSpent(List<Transaction> transactions) {

        double total = 0;

        for (Transaction t : transactions) {
            total += t.getAmount();
        }

        return total;
    }

    // FR-4 / US3
    public int getRemainingDays(BudgetCycle cycle) {

        long days = ChronoUnit.DAYS.between(
                LocalDate.now(),
                cycle.getEndDate()
        ) + 1;

        return (int) Math.max(days, 0);
    }
// Helper: get spending for today only
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

// FR-4 (Dynamic Safe Daily Limit) - FIXED
// Deducts only TODAY'S spending from the daily limit, not total spending
public double calculateDailyLimit(BudgetCycle cycle, List<Transaction> transactions) {
    double initialDailyLimit = calculateInitialDailyLimit(cycle);
    double spentToday = calculateSpentToday(transactions);

    return Math.max(initialDailyLimit - spentToday, 0);
}
    // FR-6
    public boolean isEightyPercentReached(BudgetCycle cycle, List<Transaction> transactions) {

        return calculateTotalSpent(transactions)
                >= 0.8 * cycle.getTotalBudget();
    }

    // FR-5
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
}
