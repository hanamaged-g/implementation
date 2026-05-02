package controller;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller = maps ALL sequence diagrams
 */
public class MainController {

    private TransactionManager tm;
    private HistoryManager hm;
    private notimanager nm;
    private BudgetManager bm;

    private BudgetCycle cycle;

    public MainController() {
        DatabaseManager db = DatabaseManager.getInstance();

        tm = new TransactionManager(db);
        hm = new HistoryManager();
        nm = new notimanager();
        bm = new BudgetManager();
    }

    // ===================== US #1 SET BUDGET CYCLE =====================
    public void setBudgetCycle(double budget, String start, String end) {

        cycle = new BudgetCycle(
                budget,
                0,
                LocalDate.parse(start),
                LocalDate.parse(end)
        );

        DatabaseManager.getInstance().saveCycle(budget, start, end, budget);
    }

    // ===================== US #2 ADD TRANSACTION =====================
    public void addTransaction(double amount, String category) {
        if (cycle == null) throw new IllegalStateException("Set a budget cycle first.");
        Transaction t = new Transaction(
                (int)(Math.random() * 10000),
                amount,
                category,
                java.time.LocalDateTime.now()
        );

        tm.addTransaction(t);

        // US #6 check notification
        nm.checkThreshold(cycle);
  
   
  
}
    

    // ===================== US #7 HISTORY =====================
    public ArrayList<Transaction> getHistory() {
        return hm.getAllTransactions();
    }

    // ===================== DELETE =====================
    public void deleteLastTransaction() {
        List<Transaction> list = hm.getAllTransactions();
        if (!list.isEmpty()) {
            tm.deleteTransaction(list.get(list.size()-1).getId());
        }
    }
    // ===================== CLEAR =====================
    public void clearAll() {
        DatabaseManager.getInstance().clearAll();
    }

    // ===================== US #3 DAILY LIMIT =====================
    public double getDailyLimit() {
         if (cycle == null) return 0;
    return bm.calculateDailyLimit(cycle, hm.getAllTransactions());
    }

    // ===================== US #5 CATEGORY INSIGHTS =====================
    public Object getCategoryData() {
        return bm.categoryTotals(hm.getAllTransactions());
    }

    // ===================== US #4 REMAINING DAYS =====================
    public int getRemainingDays() {
         if (cycle == null) return 0;
    return bm.getRemainingDays(cycle);
       
    }
}