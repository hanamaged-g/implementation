import javax.swing.JOptionPane;
import java.util.List;
/**
 * Manages user alerts and budget threshold notifications.
 * Acts as the logic layer for the Notification System sequence diagram.
 * * @author Ahmed Nour
 * @version 1.0
 */
public class notimanager {
    private BudgetManager budgetManager = new BudgetManager();
    /**
     * Checks if current spending has reached critical thresholds.
     * Displays a JOptionPane alert if spending is above 80% or 100%.
     * @param cycle The current BudgetCycle data
     */
    public void checkThreshold(BudgetCycle cycle) {
        DatabaseManager db = DatabaseManager.getInstance();
        List<Transaction> transactions = db.loadTransactions();
        double allowance = cycle.getTotalAllowance();
        double spent = budgetManager.calculateTotalSpent(transactions);
        if (allowance <= 0) return;
        double percentage = (spent / allowance) * 100;
        if (percentage >= 100) {
            JOptionPane.showMessageDialog(null,
                    " CRITICAL: Budget Exceeded!",
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
     * Displays a welcome message to the user upon login.
     * @param username The name of the current user
     */
    public void sendWelcomeNotification(String username) {
        JOptionPane.showMessageDialog(null, "Hello " + username + "! Masroofy is tracking your spending.");
    }
}
