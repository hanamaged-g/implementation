package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Map;

/**
 * SpendingPieChart — Visual Spending Insights (US #5)
 * Simple pie chart of spending by category, one colour per slice.
 * Usage: SpendingPieChart.show(c.getCategoryData());
 *
 * @author Masroofy Team
 * @version 1.0
 */
public class SpendingPieChart extends JPanel {

    private static final Color[] COLORS = {
        new Color(0xFF6B6B), new Color(0xFFD93D), new Color(0x6BCB77),
        new Color(0x4D96FF), new Color(0xFF9F43), new Color(0xA29BFE),
        new Color(0xFD79A8), new Color(0x55EFC4), new Color(0xB2BEC3)
    };

    private final Map<String, Double> data;
    private final double total;

    /**
     * @param data map produced by BudgetManager.categoryTotals()
     */
    public SpendingPieChart(Map<String, Double> data) {
        this.data  = data;
        this.total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        setPreferredSize(new Dimension(500, 400));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (total <= 0) {
            g2.drawString("No transactions yet.", 200, 200);
            return;
        }

        // Draw pie
        int sz = 220, x = 30, y = 60;
        double start = 0; int i = 0;
        for (Map.Entry<String, Double> e : data.entrySet()) {
            double sweep = e.getValue() / total * 360.0;
            g2.setColor(COLORS[i % COLORS.length]);
            g2.fill(new Arc2D.Double(x, y, sz, sz, start, sweep, Arc2D.PIE));
            start += sweep; i++;
        }

        // Draw legend
        int lx = x + sz + 30, ly = y + 10; i = 0;
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (Map.Entry<String, Double> e : data.entrySet()) {
            g2.setColor(COLORS[i % COLORS.length]);
            g2.fillRect(lx, ly + i*28, 14, 14);
            g2.setColor(Color.DARK_GRAY);
            g2.drawString(String.format("%s: %.2f", e.getKey(), e.getValue()), lx + 20, ly + i*28 + 12);
            i++;
        }
    }

    /**
     * Opens the chart in a new window.
     * @param categoryTotals map from MainController.getCategoryData()
     */
    @SuppressWarnings("unchecked")
    public static void show(Object categoryTotals) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Spending Insights");
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.add(new SpendingPieChart((Map<String, Double>) categoryTotals));
            f.pack(); f.setLocationRelativeTo(null); f.setVisible(true);
        });
    }
}