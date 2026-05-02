package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 * Main controller of the Masroofy system.
 * Handles communication between the View and Model layers.
 * Maps all system functionalities based on the sequence diagrams.
 * 
 * @author Hana Maged
 * @version 1.0
 */
public class MainController {

    private TransactionManager tm;
    private HistoryManager hm;
    private notimanager nm;
    private BudgetManager bm;

    private BudgetCycle cycle;

    /**
     * Initializes all managers and connects to the database.
     */
    public MainController() {
        DatabaseManager db = DatabaseManager.getInstance();

        tm = new TransactionManager(db);
        hm = new HistoryManager();
        nm = new notimanager();
        bm = new BudgetManager();
    }

    /**
     * Sets a new budget cycle.
     * 
     * @param budget total budget amount
     * @param start start date (yyyy-MM-dd)
     * @param end end date (yyyy-MM-dd)
     */
    public void setBudgetCycle(double budget, String start, String end) {

        cycle = new BudgetCycle(
                budget,
                0,
                LocalDate.parse(start),
                LocalDate.parse(end)
        );

        DatabaseManager.getInstance().saveCycle(budget, start, end, budget);
    }

    /**
     * Adds a new transaction and checks budget thresholds.
     * 
     * @param amount transaction amount
     * @param category transaction category
     */
    public void addTransaction(double amount, String category) {
        if (cycle == null) throw new IllegalStateException("Set a budget cycle first.");

        Transaction t = new Transaction(
                (int)(Math.random() * 10000),
                amount,
                category,
                java.time.LocalDateTime.now()
        );

        tm.addTransaction(t);

        // Trigger notification check
        nm.checkThreshold(cycle);
    }

    /**
     * Retrieves all transaction history.
     * 
     * @return list of transactions
     */
    public ArrayList<Transaction> getHistory() {
        return hm.getAllTransactions();
    }

    /**
     * Deletes the last transaction in history.
     */
    public void deleteLastTransaction() {
        List<Transaction> list = hm.getAllTransactions();
        if (!list.isEmpty()) {
            tm.deleteTransaction(list.get(list.size() - 1).getId());
        }
    }

    /**
     * Clears all stored data from the system.
     */
    public void clearAll() {
        DatabaseManager.getInstance().clearAll();
    }

    /**
     * Calculates the daily spending limit.
     * 
     * @return daily limit value
     */
    public double getDailyLimit() {
        if (cycle == null) return 0;
        return bm.calculateDailyLimit(cycle, hm.getAllTransactions());
    }

    /**
     * Retrieves spending data grouped by category.
     * 
     * @return category totals data
     */
    public Object getCategoryData() {
        return bm.categoryTotals(hm.getAllTransactions());
    }

    /**
     * Calculates remaining days in the current cycle.
     * 
     * @return number of remaining days
     */
    public int getRemainingDays() {
        if (cycle == null) return 0;
        return bm.getRemainingDays(cycle);
    }
}