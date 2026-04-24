import java.util.ArrayList;

public class HistoryManager {
    private DatabaseManager db = DatabaseManager.getInstance();

    public ArrayList<Transaction> getAllTransactions() {
        return db.loadTransactions();
    }
}