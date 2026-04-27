import java.time.LocalDateTime;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        
        DatabaseManager db = DatabaseManager.getInstance();
        TransactionManager manager = new TransactionManager(db);
        notimanager notifier = new notimanager();
rolloverstrat strategy = new AddToNextDayStrategy();
BudgetCycle currentCycle = new BudgetCycle(1000.0, 750.0, LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-30"));
        
        System.out.println("===== MASROOFY BUDGET APP =====\n");
        
       //save cycle
        System.out.println(" Saving Budget Cycle...");
        db.saveCycle(1000.0, "2024-04-01", "2024-04-30", 750.0);
        System.out.println("Budget cycle saved!\n");
        
      //add transactions
        System.out.println("Adding Transactions...");
        manager.addTransaction(new Transaction(1, 100.0, "Food", LocalDateTime.now()));
        manager.addTransaction(new Transaction(2, 50.0, "Transport", LocalDateTime.now()));
        manager.addTransaction(new Transaction(3, 30.0, "Coffee", LocalDateTime.now()));
        System.out.println(" transactions added!\n");
        notifier.checkThreshold(currentCycle);
        
     //get all transactions
        System.out.println("All Transactions (via HistoryManager):");
        HistoryManager history = new HistoryManager();
        for (Transaction t : history.getAllTransactions()) {
            System.out.println("   ID:" + t.getId() + " | $" + t.getAmount() + " | " + t.getCategory());
        }
        System.out.println();
        
      //edit tarnsaction
        System.out.println("Editing Transaction ID 1...");
        manager.editTransaction(1, 120.0, "Groceries");
        System.out.println(" Updated!\n");
        
      //delete transaction
        System.out.println("Deleting Transaction ID 2...");
        manager.deleteTransaction(2);
        System.out.println("Deleted!\n");
        
     //final
        double baseLimit = 50.0; 
double remainingFromYesterday = 20.0;
double todayLimit = strategy.handleRollover(remainingFromYesterday, baseLimit);
System.out.println("Strategy Applied: Today's adjusted limit is $" + todayLimit + "\n");
        System.out.println("Final Transaction List:");
        for (Transaction t : manager.getAllTransactions()) {
            System.out.println(" ID:" + t.getId() + " | $" + t.getAmount() + " | " + t.getCategory());
        }
        
        System.out.println("\n===== DEMO COMPLETE =====");
        System.out.println(" Data saved to: data.txt");
        System.out.println(" Budget saved to: cycle.txt");
    }
}
