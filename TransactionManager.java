
import java.util.ArrayList;

public class TransactionManager {
    private DatabaseManager db;
    private ArrayList<Transaction> transactions;
   
   
    public TransactionManager(DatabaseManager db) {
        this.db = db;
        this.transactions = db.loadTransactions(); 
    }
    

    public void addTransaction(Transaction t) {
         db.saveTransaction(t);
        transactions = db.loadTransactions();
        System.out.println("Transaction added!");
    }

    public void editTransaction(int id, double newAmount, String newCategory) {
         for (Transaction t : transactions) {
            if (t.getId() == id) {
                t.setAmount(newAmount);
                t.setCategory(newCategory);
                db.saveTransaction(t);
                transactions = db.loadTransactions();
                System.out.println("Transaction updated!");
                return;
            }
        }
        System.out.println("Transaction not found!");
    }

   public void deleteTransaction(int id) {
        db.deleteTransaction(id);
        transactions = db.loadTransactions();
        System.out.println("Transaction deleted!");
    }

    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }
}
