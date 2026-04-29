/**
 * Interface for the Strategy Pattern handling budget transitions between days.
 * Used in the Budget Rollover sequence diagram to determine how leftovers are handled.
 * * @author [Ahmed Nour]
 * @version 1.0
 */
public interface rolloverstrat {
    /**
     * Calculates the new daily limit based on leftovers and initial settings.
     * @param remainingBudget Leftover money from the previous day
     * @param initialDailyLimit The base limit calculated for the new day
     * @return The adjusted daily limit based on the specific strategy
     */
    double handleRollover(double remainingBudget, double initialDailyLimit);
}
