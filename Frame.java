import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    Frame() {
        // Sets up the frame
        setTitle("Mom and Pop's Pizzeria");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Creates the different screens (panels)
        // EACH SCREEN REQUIRES ITS OWN METHOD. WRITE IT HERE
        JPanel titleScreen = createTitleScreen();

        // Add screens to the main panel
        // ADD EACH SCREEN TO THE MAIN PANEL WITH A UNIQUE NAME
        mainPanel.add(titleScreen, "TITLE");

        // Displays main panel
        add(mainPanel);
    }

    /* Each method below should create a different screen. They can be used multiple times,
     but should have a distinct name with distinct functionality.
     */
    private JPanel createTitleScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.RED);
        redPanel.setBackground(new Color(0xe75959));
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel, BorderLayout.NORTH);
        // Adds the title to the red panel.
        JLabel title = new JLabel("Mom and Pop's Pizzeria", SwingConstants.CENTER);
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Arial", Font.PLAIN, 70));
        redPanel.add(title);

        // Adds the Login button to move on to the next screen.
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "TITLE"));
        // Adds components to the Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        panel.add(buttonPanel);

        return panel;
    }
}
