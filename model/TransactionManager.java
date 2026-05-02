package model;

import java.util.ArrayList;

/**
 * Manages all transaction operations in the Masroofy system.
 * Handles adding, editing, deleting, and retrieving transactions
 * by communicating with the DatabaseManager.
 * 
 * @author Hana Maged
 * @version 1.0
 */
public class TransactionManager {
    private DatabaseManager db;
    private ArrayList<Transaction> transactions;
   
    /**
     * Constructs a TransactionManager and loads existing transactions.
     * 
     * @param db DatabaseManager instance used for data storage
     */
    public TransactionManager(DatabaseManager db) {
        this.db = db;
        this.transactions = db.loadTransactions(); 
    }
    
    /**
     * Adds a new transaction to the system.
     * 
     * @param t the transaction to be added
     */
    public void addTransaction(Transaction t) {
        db.saveTransaction(t);
        transactions = db.loadTransactions();
        System.out.println("Transaction added!");
    }

    /**
     * Edits an existing transaction based on its ID.
     * 
     * @param id ID of the transaction to edit
     * @param newAmount updated amount
     * @param newCategory updated category
     */
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

    /**
     * Deletes a transaction from the system.
     * 
     * @param id ID of the transaction to delete
     */
    public void deleteTransaction(int id) {
        db.deleteTransaction(id);
        transactions = db.loadTransactions();
        System.out.println("Transaction deleted!");
    }

    /**
     * Retrieves all stored transactions.
     * 
     * @return list of all transactions
     */
    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }
}