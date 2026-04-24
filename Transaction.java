import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private double amount;
    private String category;
    private LocalDateTime timestamp;

    public Transaction(int id, double amount, String category, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}