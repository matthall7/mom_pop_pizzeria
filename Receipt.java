import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Receipt extends JPanel {
    private final JPanel itemPanel;
    private final JLabel subtotalLabel;
    private final JLabel totalLabel;
    private double subtotal = 0.0;
    private double finalTotal = 0.0;
    private final DecimalFormat df = new DecimalFormat("#.00");

    public Receipt() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xeeeeee));

        // Title at the top
        JLabel title = new JLabel("Receipt", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Panel for items with scrolling
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout());
        itemPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Subtotal and Total panel
        JPanel totalPanel = new JPanel(new GridLayout(2, 1));
        subtotalLabel = new JLabel("Subtotal: $0.00", SwingConstants.RIGHT);
        totalLabel = new JLabel("Total: $0.00", SwingConstants.RIGHT);
        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(subtotalLabel);
        totalPanel.add(totalLabel);
        add(totalPanel, BorderLayout.SOUTH);
    }

    public void addItem(String itemName, double price) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = itemPanel.getComponentCount();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel itemLabel = new JLabel(itemName);
        itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        itemPanel.add(itemLabel, gbc);

        gbc.gridx = 1;
        //JLabel priceLabel = new JLabel("$" + df.format(price), SwingConstants.RIGHT);
        //priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        //itemPanel.add(priceLabel, gbc);

        subtotal += price;
        finalTotal = (subtotal + (price*0.07));
        updateTotals();
        revalidate();
        repaint();
    }

    private void updateTotals() {
        subtotalLabel.setText("Subtotal: $" + df.format(subtotal));
        totalLabel.setText("Total: $" +df.format(finalTotal));
    }
}
