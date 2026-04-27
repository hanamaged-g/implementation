public class notimanager {
    public void checkThreshold(BudgetCycle cycle) {
        double budget = cycle.getTotalBudget();
        double spent = cycle.getTotalSpent();

        if (budget <= 0) return;

        double percentage = (spent / budget) * 100;

        System.out.println("\n--- [System Check] ---");
        if (percentage >= 100) {
            System.out.println(" CRITICAL: You have exceeded your limit!");
        } else if (percentage >= 80) {
            System.out.println(" WARNING: You have used " + String.format("%.2f", percentage) + "% of your budget.");
        } else {
            System.out.println(" Budget Status: Healthy (" + String.format("%.2f", percentage) + "%)");
        }
        System.out.println("----------------------\n");
    }

    public void sendWelcomeNotification(String username) {
        System.out.println("Hello " + username + "! Masroofy is tracking your spending.");
    }
}
