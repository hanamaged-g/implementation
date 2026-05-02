package model;

import java.time.LocalDate;

/**
 * Represents a budget cycle that tracks the total budget, spending,
 * and the time period (start and end dates) of the budget.
 *
 * This class is used as the main entity for budget tracking and calculations.
 *
 * @author Amira Ahmed
 * @version 1.0
 */
public class BudgetCycle {

    private double totalBudget;
    private double totalSpent;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs a BudgetCycle with the given budget details and time period.
     *
     * @param totalBudget the total allocated budget for the cycle
     * @param totalSpent the amount already spent in the cycle
     * @param startDate the start date of the budget cycle
     * @param endDate the end date of the budget cycle
     */
    public BudgetCycle(double totalBudget, double totalSpent, LocalDate startDate, LocalDate endDate) {
        this.totalBudget = totalBudget;
        this.totalSpent = totalSpent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the total budget allocated for this cycle
     */
    public double getTotalBudget() {
        return totalBudget;
    }

    /**
     * @return the total amount spent in this budget cycle
     */
    public double getTotalSpent() {
        return totalSpent;
    }

    /**
     * @return the end date of the budget cycle
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @return the start date of the budget cycle
     */
    public LocalDate getStartDate() {
        return startDate;
    }
}
