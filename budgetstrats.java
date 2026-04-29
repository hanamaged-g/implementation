/**
 * Concrete implementations of the Rollover Strategy.
 * Demonstrates the Strategy Pattern for managing budget transitions.
 * * @author [Ahmed Nour]
 * @version 1.0
 */

/**
 * Implementation that resets the budget daily regardless of leftovers.
 * Used for the "Standard" budget mode.
 */
class resetstrategy implements rolloverstrat {
    /**
     * Resets the budget to the initial daily limit.
     * @param remainingBudget The amount not spent yesterday
     * @param initialDailyLimit The base limit for the day
     * @return The original initialDailyLimit
     */
    @Override
    public double handleRollover(double remainingBudget, double initialDailyLimit) {
        System.out.println("New Day: Budget reset to initial limit.");
        return initialDailyLimit;
    }
}

/**
 * Implementation that rolls over savings to the next day's budget.
 * Used for the "Savings" budget mode.
 */
class AddToNextDayStrategy implements rolloverstrat {
    /**
     * Adds yesterday's savings to today's initial limit.
     * @param remainingBudget The amount saved yesterday
     * @param initialDailyLimit The base limit for the day
     * @return The combined total of limit and savings
     */
    @Override
    public double handleRollover(double remainingBudget, double initialDailyLimit) {
        double newLimit = initialDailyLimit + remainingBudget;
        System.out.println("New Day: Saved " + remainingBudget + "! New limit: " + newLimit);
        return newLimit;
    }
}
