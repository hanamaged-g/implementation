import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class MainGUI {

    public static void main(String[] args) {

        // ===================== DATABASE LAYER (PERSON 3) =====================
        // Sequence Diagram: ALL User Stories using persistence (US #2, #4, #7)
        DatabaseManager db = DatabaseManager.getInstance();
        notimanager notifier = new notimanager();
        // Create a fake cycle for testing since Person 1's cycle isn't loaded yet
        BudgetCycle currentCycle = new BudgetCycle(1000.0, 850.0, java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(30));

        // ===================== FRAME =====================
        // UI container for all components
        JFrame frame = new JFrame("Budget System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // ===================== OUTPUT AREA =====================
        // Used to display data from DatabaseManager
        // Sequence Diagram: US #7 (Transaction History Review)
        JTextArea output = new JTextArea(15, 40);

        // ===================== VIEW HISTORY BUTTON =====================
        JButton viewBtn = new JButton("View Transactions");

        viewBtn.addActionListener(e -> {

            output.setText("");

            // Sequence Diagram: US #7
            // GUI → DatabaseManager.loadTransactions()
            for (Transaction t : db.loadTransactions()) {
                output.append(
                        "ID: " + t.getId() +
                        " | Amount: " + t.getAmount() +
                        " | Category: " + t.getCategory() +
                        " | Time: " + t.getTimestamp() + "\n"
                );
            }
        });

        // ===================== CLEAR ALL BUTTON =====================
        JButton clearBtn = new JButton("Clear All Data");

        clearBtn.addActionListener(e -> {

            // Sequence Diagram: (System Maintenance / Persistence Control)
            // GUI → DatabaseManager.clearAll()
            db.clearAll();

            output.setText("All data cleared.");
        });

        // ===================== SAMPLE ADD (ONLY FOR TESTING DB) =====================
        JButton testAddBtn = new JButton("Test Add (optional)");

        testAddBtn.addActionListener(e -> {
            Transaction t = new Transaction((int)(Math.random() * 1000), 100, "Test", LocalDateTime.now());
    db.saveTransaction(t);
    
    // --- ADD YOUR LINE HERE ---
    notifier.checkThreshold(currentCycle); 
    // --------------------------

    output.setText("Test transaction saved.");


            // Sequence Diagram: US #2 (Add Transaction flow - backend part only)
            Transaction t = new Transaction(
                    (int)(Math.random() * 1000),
                    100,
                    "Test",
                    LocalDateTime.now()
            );

            db.saveTransaction(t);

            output.setText("Test transaction saved.");
        });

        // ===================== ADD COMPONENTS =====================
        frame.add(viewBtn);
        frame.add(clearBtn);
        frame.add(testAddBtn);
        frame.add(new JScrollPane(output));

        // ===================== SHOW FRAME =====================
        frame.setVisible(true);
    }
}
