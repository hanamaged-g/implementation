package model;

import javax.swing.JOptionPane;
import model.BudgetCycle;
import model.BudgetManager;
import model.DatabaseManager;
import model.Transaction;
import java.util.List;
import java.util.Map;

/**
 * Manages user alerts, budget threshold notifications, and spending insights.
 * Acts as the logic layer for the Notification System sequence diagram.
 *
 * @author Ahmed Nour
 * @version 2.0
 */
public class notimanager {

    private BudgetManager budgetManager = new BudgetManager();

    /**
     * Checks if current spending has reached critical thresholds.
     * Displays a JOptionPane alert if spending is above 80% or 100%.
     * @param cycle The current BudgetCycle data from Person 1
     */
    public void checkThreshold(BudgetCycle cycle) {
        DatabaseManager db = DatabaseManager.getInstance();
        List<Transaction> transactions = db.loadTransactions();

        // Fixed: Using getTotalAllowance() to match BudgetCycle.java[cite: 1, 8]
        double allowance = cycle.getTotalBudget();

        // Fixed: Using BudgetManager to calculate total spent[cite: 2, 8]
        double spent = budgetManager.calculateTotalSpent(transactions);

        if (allowance <= 0) return;

        double percentage = (spent / allowance) * 100;

        if (percentage >= 100) {
            JOptionPane.showMessageDialog(null,
                    "CRITICAL: Budget Exceeded!",
                    "Masroofy Alert",
                    JOptionPane.ERROR_MESSAGE);
        } else if (percentage >= 80) {
            JOptionPane.showMessageDialog(null,
                    " Warning: You've used " + String.format("%.1f", percentage) + "% of your budget.",
                    "Masroofy Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * BONUS FEATURE: Weekly Insight Report
     * Generates a breakdown of spending by category and provides financial tips.
     * @param cycle The current BudgetCycle data
     */
    public void showWeeklyReport(BudgetCycle cycle) {
        DatabaseManager db = DatabaseManager.getInstance();
        List<Transaction> transactions = db.loadTransactions();

        // Uses teammate's existing logic to get category totals and total spent[cite: 2, 8]
        Map<String, Double> totals = budgetManager.categoryTotals(transactions);
        double totalSpent = budgetManager.calculateTotalSpent(transactions);

        StringBuilder report = new StringBuilder(" --- Masroofy Weekly Insight Report ---\n\n");
        report.append("Total Spending: $").append(String.format("%.2f", totalSpent)).append("\n");

        // Breakdown by category using the Map from BudgetManager[cite: 2, 8]
        if (totals.isEmpty()) {
            report.append("No transactions recorded yet.");
        } else {
            totals.forEach((category, amount) -> {
                double percent = (amount / totalSpent) * 100;
                report.append(String.format("• %s: $%.2f (%.1f%%)\n", category, amount, percent));
            });
        }

        // Financial Coaching Logic[cite: 8]
        double allowance = cycle.getTotalBudget();
        if (totalSpent > allowance * 0.5) {
            report.append("\n Tip: You've used over half your budget. Slow down on non-essentials!");
        } else {
            report.append("\n Tip: Great job! You are managing your budget well.");
        }

        JOptionPane.showMessageDialog(null, report.toString(), "Weekly Insights", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a welcome message to the user upon login.
     * @param username The name of the current user
     */
    public void sendWelcomeNotification(String username) {
        JOptionPane.showMessageDialog(null, "Hello " + username + "! Masroofy is tracking your spending.");
    }
}