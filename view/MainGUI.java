package view;

import controller.MainController;
import model.Transaction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * MainGUI — Entry point and primary view for Masroofy.
 * @author Masroofy Team
 * @version 1.0
 */
public class MainGUI {

    public static void main(String[] args) {
        MainController c = new MainController();
        controller.notimanager notifier = new controller.notimanager();

        // ── Frame setup ───────────────────────────────────────────────────────
        JFrame f = new JFrame("Masroofy — Budget Tracker");
        f.setSize(680, 580);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout(10, 10));

        // Light grey background
        Color bg = new Color(0xF4F4F4);
        f.getContentPane().setBackground(bg);

        // ── Output area ───────────────────────────────────────────────────────
        JTextArea output = new JTextArea(14, 50);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setBackground(new Color(0xFDFDFD));
        output.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder("Output"));

        // ── Input panel ───────────────────────────────────────────────────────
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        inputPanel.setBackground(bg);
        inputPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        // Budget cycle inputs
        JTextField budgetField = new JTextField();
        JTextField startField  = new JTextField("yyyy-MM-dd");
        JTextField endField    = new JTextField("yyyy-MM-dd");
        JButton setCycle       = new JButton("Set Budget Cycle");
        JTextField goalField = new JTextField();

        inputPanel.add(new JLabel("Total Budget:")); inputPanel.add(budgetField);
        inputPanel.add(new JLabel("Start Date:"));   inputPanel.add(startField);
        inputPanel.add(new JLabel("End Date:"));     inputPanel.add(endField);
       
        inputPanel.add(new JLabel("Savings Goal:")); inputPanel.add(goalField);
        inputPanel.add(new JLabel(""));              inputPanel.add(setCycle);

        // Transaction inputs
        JTextField amountField = new JTextField();
        String[] categories = {"Food","Transport","Shopping","Bills","Drinks","Makeup","Jewellery","Gas","Other"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        JButton add = new JButton("Add Transaction");

        inputPanel.add(new JLabel("Amount:"));   inputPanel.add(amountField);
        inputPanel.add(new JLabel("Category:")); inputPanel.add(categoryBox);
        inputPanel.add(new JLabel(""));          inputPanel.add(add);

        // ── Button panel ──────────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnPanel.setBackground(bg);

        JButton history   = new JButton("View History");
        JButton deleteBtn = new JButton("Delete Last");
        JButton clear     = new JButton("Clear All");
        JButton daily     = new JButton("Daily Limit");
        JButton chart     = new JButton("Show Chart");
        JButton days      = new JButton("Remaining Days");
        JButton reportBtn = new JButton("Weekly Report");

        for (JButton b : new JButton[]{history, deleteBtn, clear, daily, chart, days,reportBtn})
            btnPanel.add(b);

        // ── Listeners ─────────────────────────────────────────────────────────
         setCycle.addActionListener(e -> {
            try {
                c.setBudgetCycle(Double.parseDouble(budgetField.getText()),
                                 startField.getText().trim(),
                                 endField.getText().trim());
                output.setText("Budget cycle set.");
                c.getCycle().setSavingsGoal(
                   Double.parseDouble(goalField.getText())
                );
                double spent = 0;
                for (Transaction t : c.getHistory()) {
                    spent += t.getAmount();
                }
                 
                double goal = Double.parseDouble(goalField.getText());
                double totalBudget = Double.parseDouble(budgetField.getText());
                double remaining = totalBudget - spent;
                
                String goalStatus;
                if (goal <= 0) {
                    goalStatus = "No Savings Goal Set";
                }
                else if (spent <= 0) {
                    goalStatus = " Goal Achieved";
                }
                else if ((totalBudget - spent) >= goal) {
                    goalStatus = " Goal Achieved";
                }
                else if ((totalBudget - spent) >= goal * 0.8) {
                    goalStatus = " Close to Goal";
                }
                else {
                    goalStatus = " Not Yet Achieved";
                }
                output.append("\nSpent: " + spent);
                output.append("\nRemaining: " + remaining);
                
                output.append("\nGoal: " + goal);
                output.append("\nStatus: " + goalStatus + "\n");
                
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Use format YYYY-MM-DD for dates.");
            }
        });
        reportBtn.addActionListener(e -> {
            if (c.getCycle() != null) {
                notifier.showWeeklyReport(c.getCycle());
            } else {
                JOptionPane.showMessageDialog(f, "Please set a budget cycle first!");
            }
        });

        
        add.addActionListener(e -> {
            try {
                c.addTransaction(Double.parseDouble(amountField.getText()),
                                 (String) categoryBox.getSelectedItem());
                output.append("Transaction added.\n");
                notifier.checkThreshold(c.getCycle());
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(f, "Set a budget cycle first.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(f, "Enter a valid amount.");
            }
        });

        history.addActionListener(e -> {
            output.setText("");
            for (Transaction t : c.getHistory())
                output.append(t.getCategory() + "  —  " + t.getAmount() + "\n");
        });

        deleteBtn.addActionListener(e -> {
            c.deleteLastTransaction();
            output.setText("Last transaction deleted.");
        });
        clear.addActionListener(e -> {
            c.clearAll();
            output.setText("All transactions cleared.");
        });

        daily.addActionListener(e ->
            output.setText("Daily Limit: " + String.format("%.2f", c.getDailyLimit())));

        chart.addActionListener(e ->
            SpendingPieChart.show(c.getCategoryData()));

        days.addActionListener(e ->
            output.setText("Days Remaining: " + c.getRemainingDays()));



        // ── Assemble ──────────────────────────────────────────────────────────
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(bg);
        top.add(inputPanel, BorderLayout.CENTER);
        top.add(btnPanel,   BorderLayout.SOUTH);

        f.add(top,    BorderLayout.NORTH);
        f.add(scroll, BorderLayout.CENTER);
        f.setVisible(true);
    }
}
