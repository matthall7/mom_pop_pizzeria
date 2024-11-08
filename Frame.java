import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        JPanel customerLoginScreen = createCustomerLoginScreen();
        JPanel accountCreationScreen = createAccountCreationScreen();
        JPanel adminLoginScreen = createAdminLoginScreen();
        JPanel customerHome = createCustomerHome();
        JPanel accountInfo = createAccountInfo();

        // Add screens to the main panel
        // ADD EACH SCREEN TO THE MAIN PANEL WITH A UNIQUE NAME
        mainPanel.add(titleScreen, "TITLE");
        mainPanel.add(customerLoginScreen, "LOGIN_CUSTOMER");
        mainPanel.add(accountCreationScreen, "CREATE_ACCOUNT");
        mainPanel.add(adminLoginScreen, "LOGIN_ADMIN");
        mainPanel.add(customerHome, "CUSTOMER_HOME");
        mainPanel.add(accountInfo, "ACCOUNT_INFO");

        // Displays main panel
        add(mainPanel);
    }

    private JPanel createRedBanner(String title, boolean backButton, boolean logoutButton, boolean accountButton, String previousScreen) {
        // Creates the red banner.
        JPanel redPanel = new JPanel();
        redPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        redPanel.setBackground(new Color(0xe75959));
        redPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/5));

        // Creates the back button.
        if(backButton) {
            JButton back = new JButton("Back");
            back.setFont(new Font("Arial", Font.PLAIN, 25));
            back.setSize(110, 60);
            back.addActionListener(e -> cardLayout.show(mainPanel, previousScreen));

            redPanel.add(back);
        }

        // Creates the title
        JLabel titleText = new JLabel(title);
        titleText.setForeground(Color.BLACK);
        titleText.setFont(new Font("Arial", Font.PLAIN, 70));
        redPanel.add(titleText);

        // Creates the Account button.
        if(accountButton) {
            JButton account = new JButton("Account");
            account.setFont(new Font("Arial", Font.PLAIN, 25));
            account.setSize(110, 60);
            account.addActionListener(e -> cardLayout.show(mainPanel, "ACCOUNT_INFO"));

            redPanel.add(account);
        }

        // Creates the Logout button.
        if(logoutButton) {
            JButton logout = new JButton("Logout");
            logout.setFont(new Font("Arial", Font.PLAIN, 25));
            logout.setSize(110, 60);
            logout.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN_CUSTOMER"));

            redPanel.add(logout);
        }

        return redPanel;
    }

    /* Each method below should create a different screen. They can be used multiple times,
     but should have a distinct name with distinct functionality.
     */
    private JPanel createTitleScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Mom and Pop's Pizzeria", false, false, false, "NA");
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

    private JPanel createCustomerLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Login", false, false, false, "NA");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the combo box that switches the page from the customer to admin login screen
        String[] loginTypes = {"Customer", "Admin"};
        JComboBox loginTypeBox = new JComboBox(loginTypes);
        loginTypeBox.setSelectedIndex(0);
        loginTypeBox.setBounds(getWidth()/2-100, 150, 200, 50);
        loginTypeBox.setFont(new Font("Arial", Font.PLAIN, 20));

        loginTypeBox.addActionListener(e -> {
            if(loginTypeBox.getSelectedIndex() == 1) {
                cardLayout.show(mainPanel, "LOGIN_ADMIN");
            }
        });
        panel.add(loginTypeBox);

        // Adds the Phone number label on the screen.
        JLabel locationText = new JLabel("Phone Number");
        locationText.setFont(new Font("Arial", Font.PLAIN, 30));
        locationText.setBounds(getWidth()/2-95, 170, 300, 200);
        panel.add(locationText);

        // Adds the Phone number text field on the screen.
        JTextField phoneNumberText = new JTextField();
        phoneNumberText.setBounds(getWidth()/2-100, 300, 200, 50);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(phoneNumberText);

        // Adds the "Create an Account" link
        JLabel createAnAccountLink = new JLabel("<html><u>Create an Account</u><html>");
        createAnAccountLink.setFont(new Font("Arial", Font.PLAIN, 25));
        createAnAccountLink.setBounds(getWidth()/2-105, 400, 210, 30);
        createAnAccountLink.setForeground(Color.BLUE);
        createAnAccountLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createAnAccountLink.setForeground(Color.RED);
            }
        });
        createAnAccountLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                createAnAccountLink.setForeground(Color.BLUE);
            }
        });
        createAnAccountLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "CREATE_ACCOUNT");
            }
        });
        panel.add(createAnAccountLink);

        // Adds the Login button to move on to the next screen.
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(getWidth()/2-65, 500, 130, 70);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loginButton.addActionListener(e -> {
            // NEEDS TO BE CHANGE TO ONLY ALLOW IF CUSTOMER HAS ENTERED CORRECT PHONE NUMBER
            cardLayout.show(mainPanel, "CUSTOMER_HOME");
        });

        panel.add(loginButton);

        return panel;
    }

    private JPanel createAdminLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Login", false, false, false, "NA");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the combo box that switches the page from the customer to admin login screen
        String[] loginTypes = {"Customer", "Admin"};
        JComboBox<String> loginTypeBox = new JComboBox<>(loginTypes);
        loginTypeBox.setSelectedIndex(1);
        loginTypeBox.setBounds(getWidth()/2-100, 150, 200, 50);
        loginTypeBox.setFont(new Font("Arial", Font.PLAIN, 20));

        loginTypeBox.addActionListener(e -> {
            if(loginTypeBox.getSelectedIndex() == 0) {
                cardLayout.show(mainPanel, "LOGIN_CUSTOMER");
            }
        });
        panel.add(loginTypeBox);

        // Adds the Password label on the screen.
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 30));
        password.setBounds(getWidth()/2-95, 170, 300, 200);
        panel.add(password);

        // Adds the Password text field on the screen.
        JTextField passwordText = new JTextField();
        passwordText.setBounds(getWidth()/2-100, 300, 200, 50);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(passwordText);

        // Adds the Login button to move on to the next screen.
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(getWidth()/2-65, 500, 130, 70);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loginButton.addActionListener(e -> {
            if(passwordText.getText().equals("adminpassword")) {
                cardLayout.show(mainPanel, "ADMIN_HOME");
            }
        });
        panel.add(loginButton);

        return panel;
    }

    private JPanel createAccountCreationScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Create an Account", true, false, false, "LOGIN_CUSTOMER");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the Name label and text field on the screen.
        JLabel name = new JLabel("Name *");
        name.setFont(new Font("Arial", Font.PLAIN, 30));
        name.setBounds(360, 60, 300, 200);
        panel.add(name);
        JTextField nameText = new JTextField();
        nameText.setBounds(360, 177, 250, 50);
        nameText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(nameText);

        // Adds the Phone number label and text field on the screen.
        JLabel phoneNumber = new JLabel("Phone Number *");
        phoneNumber.setFont(new Font("Arial", Font.PLAIN, 30));
        phoneNumber.setBounds(360, 155, 300, 200);
        panel.add(phoneNumber);
        JTextField phoneNumberText = new JTextField();
        phoneNumberText.setBounds(360, 275, 250, 50);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(phoneNumberText);

        // Adds Payment labels and text fields on the screen.
        JLabel payment = new JLabel("Payment");
        payment.setFont(new Font("Arial", Font.PLAIN, 30));
        payment.setBounds(70, 155, 300, 200);
        panel.add(payment);
        // Card Number
        JLabel cardNumber = new JLabel("Card Number");
        cardNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        cardNumber.setBounds(70, 190, 300, 200);
        panel.add(cardNumber);
        JTextField cardNumberText = new JTextField();
        cardNumberText.setBounds(70, 307, 250, 50);
        cardNumberText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cardNumberText);
        // Expiration Date
        JLabel expirationDate = new JLabel("Expiration Date");
        expirationDate.setFont(new Font("Arial", Font.PLAIN, 20));
        expirationDate.setBounds(70, 270, 300, 200);
        panel.add(expirationDate);
        JTextField expirationDateText = new JTextField();
        expirationDateText.setBounds(70, 387, 250, 50);
        expirationDateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(expirationDateText);
        // Security Code
        JLabel securityCode = new JLabel("Security Code");
        securityCode.setFont(new Font("Arial", Font.PLAIN, 20));
        securityCode.setBounds(70, 350, 300, 200);
        panel.add(securityCode);
        JTextField securityCodeText = new JTextField();
        securityCodeText.setBounds(70, 467, 250, 50);
        securityCodeText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(securityCodeText);

        // Adds Address labels and text fields on the screen.
        JLabel address = new JLabel("Address");
        address.setFont(new Font("Arial", Font.PLAIN, 30));
        address.setBounds(660, 135, 300, 200);
        panel.add(address);
        // Street
        JLabel street = new JLabel("Street");
        street.setFont(new Font("Arial", Font.PLAIN, 20));
        street.setBounds(660, 170, 300, 200);
        panel.add(street);
        JTextField streetText = new JTextField();
        streetText.setBounds(660, 287, 250, 50);
        streetText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(streetText);
        // City
        JLabel city = new JLabel("City");
        city.setFont(new Font("Arial", Font.PLAIN, 20));
        city.setBounds(660, 250, 300, 200);
        panel.add(city);
        JTextField cityText = new JTextField();
        cityText.setBounds(660, 367, 250, 50);
        cityText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cityText);
        // State
        JLabel state = new JLabel("State");
        state.setFont(new Font("Arial", Font.PLAIN, 20));
        state.setBounds(660, 330, 300, 200);
        panel.add(state);
        JTextField stateText = new JTextField();
        stateText.setBounds(660, 447, 250, 50);
        stateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(stateText);
        // Zipcode
        JLabel zipcode = new JLabel("Zipcode");
        zipcode.setFont(new Font("Arial", Font.PLAIN, 20));
        zipcode.setBounds(660, 410, 300, 200);
        panel.add(zipcode);
        JTextField zipcodeText = new JTextField();
        zipcodeText.setBounds(660, 527, 250, 50);
        zipcodeText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(zipcodeText);

        // Adds the Create button to create the account.
        JButton createButton = new JButton("Create");
        createButton.setBounds(getWidth()/2-65, 500, 130, 70);
        createButton.setFont(new Font("Arial", Font.PLAIN, 30));
        createButton.addActionListener(e -> {
            //create account
        });
        panel.add(createButton);
        JLabel required = new JLabel("* Required");
        required.setFont(new Font("Arial", Font.PLAIN, 20));
        required.setBounds(getWidth()/2-65, 380, 300, 200);
        panel.add(required);

        return panel;
    }

    private JPanel createCustomerHome() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Home", false, true, true, "NA");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the Order Now label on the screen.
        JLabel locationText = new JLabel("Order Now");
        locationText.setFont(new Font("Arial", Font.PLAIN, 30));
        locationText.setBounds(getWidth()/2-80, 90, 300, 200);
        panel.add(locationText);

        // Adds the Online label to the screen.
        JLabel onlineLabel = new JLabel("Online");
        onlineLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        onlineLabel.setBounds(230, 155, 300, 200);
        panel.add(onlineLabel);

        // Adds the Delivery button on the screen.
        JButton deliveryButton = new JButton("Delivery");
        deliveryButton.setBounds(170, 280, 190, 70);
        deliveryButton.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(deliveryButton);

        // Adds the Pickup button on the screen.
        JButton pickupButton = new JButton("Pickup");
        pickupButton.setBounds(170, 380, 190, 70);
        pickupButton.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(pickupButton);

        // Adds the In-Store button on the screen.
        JButton inStoreButton = new JButton("In-Store");
        inStoreButton.setBounds(600, 320, 190, 70);
        inStoreButton.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(inStoreButton);

        return panel;
    }

    private JPanel createAccountInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Account Information", true, true, false, "CUSTOMER_HOME");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the Name label and text field on the screen.
        JLabel name = new JLabel("Name *");
        name.setFont(new Font("Arial", Font.PLAIN, 30));
        name.setBounds(360, 60, 300, 200);
        panel.add(name);
        JTextField nameText = new JTextField();
        nameText.setBounds(360, 177, 250, 50);
        nameText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(nameText);

        // Adds the Phone number label and text field on the screen.
        JLabel phoneNumber = new JLabel("Phone Number *");
        phoneNumber.setFont(new Font("Arial", Font.PLAIN, 30));
        phoneNumber.setBounds(360, 155, 300, 200);
        panel.add(phoneNumber);
        JTextField phoneNumberText = new JTextField();
        phoneNumberText.setBounds(360, 275, 250, 50);
        phoneNumberText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(phoneNumberText);

        // Adds Payment labels and text fields on the screen.
        JLabel payment = new JLabel("Payment");
        payment.setFont(new Font("Arial", Font.PLAIN, 30));
        payment.setBounds(70, 155, 300, 200);
        panel.add(payment);
        // Card Number
        JLabel cardNumber = new JLabel("Card Number");
        cardNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        cardNumber.setBounds(70, 190, 300, 200);
        panel.add(cardNumber);
        JTextField cardNumberText = new JTextField();
        cardNumberText.setBounds(70, 307, 250, 50);
        cardNumberText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cardNumberText);
        // Expiration Date
        JLabel expirationDate = new JLabel("Expiration Date");
        expirationDate.setFont(new Font("Arial", Font.PLAIN, 20));
        expirationDate.setBounds(70, 270, 300, 200);
        panel.add(expirationDate);
        JTextField expirationDateText = new JTextField();
        expirationDateText.setBounds(70, 387, 250, 50);
        expirationDateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(expirationDateText);
        // Security Code
        JLabel securityCode = new JLabel("Security Code");
        securityCode.setFont(new Font("Arial", Font.PLAIN, 20));
        securityCode.setBounds(70, 350, 300, 200);
        panel.add(securityCode);
        JTextField securityCodeText = new JTextField();
        securityCodeText.setBounds(70, 467, 250, 50);
        securityCodeText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(securityCodeText);

        // Adds Address labels and text fields on the screen.
        JLabel address = new JLabel("Address");
        address.setFont(new Font("Arial", Font.PLAIN, 30));
        address.setBounds(660, 135, 300, 200);
        panel.add(address);
        // Street
        JLabel street = new JLabel("Street");
        street.setFont(new Font("Arial", Font.PLAIN, 20));
        street.setBounds(660, 170, 300, 200);
        panel.add(street);
        JTextField streetText = new JTextField();
        streetText.setBounds(660, 287, 250, 50);
        streetText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(streetText);
        // City
        JLabel city = new JLabel("City");
        city.setFont(new Font("Arial", Font.PLAIN, 20));
        city.setBounds(660, 250, 300, 200);
        panel.add(city);
        JTextField cityText = new JTextField();
        cityText.setBounds(660, 367, 250, 50);
        cityText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cityText);
        // State
        JLabel state = new JLabel("State");
        state.setFont(new Font("Arial", Font.PLAIN, 20));
        state.setBounds(660, 330, 300, 200);
        panel.add(state);
        JTextField stateText = new JTextField();
        stateText.setBounds(660, 447, 250, 50);
        stateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(stateText);
        // Zipcode
        JLabel zipcode = new JLabel("Zipcode");
        zipcode.setFont(new Font("Arial", Font.PLAIN, 20));
        zipcode.setBounds(660, 410, 300, 200);
        panel.add(zipcode);
        JTextField zipcodeText = new JTextField();
        zipcodeText.setBounds(660, 527, 250, 50);
        zipcodeText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(zipcodeText);

        // Adds the Create button to create the account.
        JButton createButton = new JButton("<html>Save<br/>Changes<html>");

        createButton.setBounds(getWidth()/2-80, 500, 160, 90);
        createButton.setFont(new Font("Arial", Font.PLAIN, 30));
        createButton.addActionListener(e -> {
            //save changes to the account
        });
        panel.add(createButton);
        JLabel required = new JLabel("* Required");
        required.setFont(new Font("Arial", Font.PLAIN, 20));
        required.setBounds(getWidth()/2-65, 380, 300, 200);
        panel.add(required);

        // Adds the "View Past Orders" link
        JLabel viewPastOrdersLink = new JLabel("<html><u>View Past Orders</u><html>");
        viewPastOrdersLink.setFont(new Font("Arial", Font.PLAIN, 25));
        viewPastOrdersLink.setBounds(getWidth()/2-105, 400, 210, 30);
        viewPastOrdersLink.setForeground(Color.BLUE);
        viewPastOrdersLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewPastOrdersLink.setForeground(Color.RED);
            }
        });
        viewPastOrdersLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                viewPastOrdersLink.setForeground(Color.BLUE);
            }
        });
        viewPastOrdersLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "VIEW_PAST_ORDERS");
            }
        });
        panel.add(viewPastOrdersLink);

        return panel;
    }
}
