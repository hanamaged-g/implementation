import java.util.ArrayList;
/**
 * Handles retrieval of transaction history for visual spending insights.
 * Used in the Visual Spending Insights sequence diagram.
 * 
 * @author lojain essam
 * @version 1.0
 */
public class HistoryManager {  
    /**
     * Constructor - gets the DatabaseManager instance.
     */
    private DatabaseManager db = DatabaseManager.getInstance();
    
    /**
     * Returns all transactions from the database.
     * Called from Dashboard to display spending insights.
     * @return ArrayList of all Transaction objects
     */
    public ArrayList<Transaction> getAllTransactions() {
        return db.loadTransactions();
    }
}