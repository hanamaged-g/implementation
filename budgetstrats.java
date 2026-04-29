class resetstrategy implements rolloverstrat {
    @Override
    public double handleRollover(double remainingBudget, double initialDailyLimit) {
        System.out.println("New Day: Budget reset to initial limit.");
        return initialDailyLimit;
    }
}

class AddToNextDayStrategy implements rolloverstrat {
    @Override
    public double handleRollover(double remainingBudget, double initialDailyLimit) {
        double newLimit = initialDailyLimit + remainingBudget;
        System.out.println("New Day: Saved " + remainingBudget + "! New limit: " + newLimit);
        return newLimit;
    }
}
