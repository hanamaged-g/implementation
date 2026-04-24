import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class DatabaseManager {
    private static DatabaseManager instance = null;  // Start as null
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private final String FILE = "data.txt";

    // PRIVATE constructor (nobody can call "new DatabaseManager()")
    private DatabaseManager() {
        loadAllData();
    }

    // PUBLIC static method to get the ONE instance
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();  // Create only once
        }
        return instance;
    }

    public void saveTransaction(Transaction t) {
        boolean found = false;
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == t.getId()) {
                transactions.set(i, t);
                found = true;
                break;
            }
        }
        if (!found) transactions.add(t);
        saveAllData();
    }

    public ArrayList<Transaction> loadTransactions() { 
        return transactions; 
    }

    public void deleteTransaction(int id) {
        transactions.removeIf(t -> t.getId() == id);
        saveAllData();
    }

    public void saveCycle(double budget, String start, String end, double remaining) {
        try {
            FileWriter fw = new FileWriter("cycle.txt");
            fw.write(budget + "," + start + "," + end + "," + remaining);
            fw.close();
        } catch (Exception e) {}
    }

    public void clearAll() {
        transactions.clear();
        saveAllData();
    }

    private void saveAllData() {
        try {
            FileWriter fw = new FileWriter(FILE);
            for (Transaction t : transactions) {
                fw.write(t.getId() + "," + t.getAmount() + "," + t.getCategory() + "," + t.getTimestamp() + "\n");
            }
            fw.close();
        } catch (Exception e) {}
    }

    private void loadAllData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                transactions.add(new Transaction(
                    Integer.parseInt(p[0]), 
                    Double.parseDouble(p[1]), 
                    p[2], 
                    LocalDateTime.parse(p[3])
                ));
            }
            br.close();
        } catch (Exception e) {}
    }
}