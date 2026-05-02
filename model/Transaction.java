package model;

import java.time.LocalDateTime;

/**
 * Represents a financial transaction in the Masroofy system.
 * Stores details such as amount, category, and timestamp.
 * 
 * @author Hana Maged
 * @version 1.0
 */
public class Transaction {
    private int id;
    private double amount;
    private String category;
    private LocalDateTime timestamp;

    /**
     * Constructs a Transaction object with all required details.
     * 
     * @param id Unique identifier for the transaction
     * @param amount Amount of money spent
     * @param category Category of the transaction (e.g., Food, Transport)
     * @param timestamp Date and time of the transaction
     */
    public Transaction(int id, double amount, String category, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.timestamp = timestamp;
    }

    /**
     * Gets the transaction ID.
     * @return transaction ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the transaction amount.
     * @return amount spent
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the transaction category.
     * @return category name
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the transaction timestamp.
     * @return date and time of transaction
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Updates the transaction amount.
     * @param amount new amount value
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Updates the transaction category.
     * @param category new category value
     */
    public void setCategory(String category) {
        this.category = category;
    }
}