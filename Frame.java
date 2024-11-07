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
        JPanel loginScreen = createLoginScreen();

        // Add screens to the main panel
        // ADD EACH SCREEN TO THE MAIN PANEL WITH A UNIQUE NAME
        mainPanel.add(titleScreen, "TITLE");
        mainPanel.add(loginScreen, "LOGIN_CUSTOMER");

        // Displays main panel
        add(mainPanel);
    }

    private JPanel createRedBanner(String title, boolean backButton, boolean logoutButton, boolean accountButton) {
        JPanel redPanel = new JPanel();
        redPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        redPanel.setBackground(new Color(0xe75959));
        redPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/5));
        JLabel titleText = new JLabel(title);
        titleText.setForeground(Color.BLACK);
        titleText.setFont(new Font("Arial", Font.PLAIN, 70));
        redPanel.add(titleText);

        return redPanel;
    }

    /* Each method below should create a different screen. They can be used multiple times,
     but should have a distinct name with distinct functionality.
     */
    private JPanel createTitleScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Mom and Pop's Pizzeria", false, false, false);
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the Login button to move on to the next screen.
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN_CUSTOMER"));
        loginButton.setBounds(getWidth()/2-65, getHeight()/2-35, 130, 70);
        panel.add(loginButton);

        // Adds the Location text on the left of the screen
        JLabel locationText = new JLabel("<html>Location<br/>680 Arnston Rd ste 217<br/>Marietta, GA 30060<html>");
        locationText.setFont(new Font("Arial", Font.PLAIN, 25));
        locationText.setBounds(80, 180, 300, 200);
        panel.add(locationText);

        // Adds the Operating Hours text on the right of the screen
        JLabel operatingHours = new JLabel("<html>Operating Hours<br/>Sun-Thurs. 11am-9pm<br/>F & Sat. 11am-12am<html>");
        operatingHours.setFont(new Font("Arial", Font.PLAIN, 25));
        operatingHours.setBounds(680, 180, 300, 200);
        panel.add(operatingHours);

        // Adds the Contact Information text at the bottom of the screen
        JLabel contactInfo = new JLabel("<html>770-555-1212<br/>MomAndPopPizzeria.com<br/>Est. 2023<html>");
        contactInfo.setFont(new Font("Arial", Font.PLAIN, 25));
        contactInfo.setBounds(400, 400, 300, 200);
        panel.add(contactInfo);

        return panel;
    }

    private JPanel createLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Login", false, false, false);
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the Login button to move on to the next screen.
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "home screen"));
        loginButton.setBounds(getWidth()/2-65, getHeight()/2-35, 130, 70);
        panel.add(loginButton);

        // Adds the combo box that switches the page from the customer to admin login screen
        String[] loginTypes = {"Customer", "Admin"};
        JComboBox loginTypeBox = new JComboBox(loginTypes);
        loginTypeBox.setSelectedIndex(0);
        loginTypeBox.setBounds(getWidth()/2-100, 200, 200, 50);

        loginTypeBox.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN_ADMIN"));

        panel.add(loginTypeBox);
        return panel;
    }
}
