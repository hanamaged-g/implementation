import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
    /**
 * Manages all data persistence operations including saving and loading
 * transactions and budget cycles to/from text files and saves it all.
 * Implements Singleton pattern as mentioned in the SDS to ensure single database connection.
 * 
 * @author lojain essam
 * @version 1.0
 */


public class DatabaseManager {
    private static DatabaseManager instance = null; 
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private final String FILE = "data.txt";


    /**
     * Private constructor for Singleton pattern.
     * Loads existing data from data.txt on initialization.
     */

    private DatabaseManager() {
        loadAllData();
    }

        /**
     * Returns the single instance of DatabaseManager.
     * Creates the instance if it doesn't exist yet.
     * SINGLETON PATTERN: Ensures only one instance of DatabaseManager exists throughout the application.
     * @return the only DatabaseManager instance
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();  
        }
        return instance;
    }


        /**
     * Saves a transaction to memory and persists to file.
     * If a transaction with the same ID exists, it updates it.
     * @param t the Transaction object to save
     */
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

    /**
     * Loads all transactions from memory.
     * Data is loaded from data.txt when the class is first instantiated.
     * @return ArrayList of all Transaction objects
     */
    public ArrayList<Transaction> loadTransactions() { 
        return transactions; 
    }

    /**
     * Deletes a transaction by its ID from memory and file.
     * @param id the ID of the transaction to delete
     */
    public void deleteTransaction(int id) {
        transactions.removeIf(t -> t.getId() == id);
        saveAllData();
    }

    /**
     * Saves the budget cycle information to cycle.txt file.
     * Used for tracking total budget, dates, and remaining balance.
     * @param budget total budget amount for the cycle
     * @param start start date of the budget cycle
     * @param end end date of the budget cycle
     * @param remaining remaining balance in the cycle
     */
    public void saveCycle(double budget, String start, String end, double remaining) {
        try {
            FileWriter fw = new FileWriter("cycle.txt");
            fw.write(budget + "," + start + "," + end + "," + remaining);
            fw.close();
        } catch (Exception e) {}
    }

 /**
     * Clears all transactions from memory and file.
     * Resets the entire transaction history.
     */
    public void clearAll() {
        transactions.clear();
        saveAllData();
    }
    /**
     * Writes all transactions to the data.txt file.
     * Each transaction is written as: id,amount,category,timestamp
     */
    private void saveAllData() {
        try {
            FileWriter fw = new FileWriter(FILE);
            for (Transaction t : transactions) {
                fw.write(t.getId() + "," + t.getAmount() + "," + t.getCategory() + "," + t.getTimestamp() + "\n");
            }
            fw.close();
        } catch (Exception e) {}
    }

       /**
     * Reads all transactions from the data.txt file on startup.
     * File format: id,amount,category,timestamp per line.
     */
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
