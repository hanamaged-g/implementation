import java.time.LocalDate;

public class BudgetCycle {

    private double totalAllowance;
    private LocalDate startDate;
    private LocalDate endDate;

    public BudgetCycle(double totalAllowance, LocalDate startDate, LocalDate endDate) {

        if (totalAllowance <= 0) {
            throw new IllegalArgumentException("Allowance must be positive");
        }

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        this.totalAllowance = totalAllowance;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getTotalAllowance() {
        return totalAllowance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
