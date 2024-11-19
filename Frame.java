import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class Frame extends JFrame{
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private Customer customer;
    private final ArrayList<Item> cart = new ArrayList<>();
    private final JPanel cartItems = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(cartItems);
    private int pizzaCounter = 1;
    private int dessertCounter = 1;
    private int bSticksCounter = 1;
    private int bitesCounter = 1;
    private final File database = new File("Customer DataBase.txt");

    Frame() {
        // Sets up the frame
        setTitle("Mom and Pop's Pizzeria");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Creates the Cart Screen that items get added to
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cartItems.setLayout(new BoxLayout(cartItems, BoxLayout.Y_AXIS));
        scrollPane.setBackground(new Color(0xeeeeee));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setBounds(50, 170, 880, 360);

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
        JPanel addressInput = createAddressInput();
        JPanel pizzaMenu = createPizzaMenu();
        JPanel sidesMenu = createSidesMenu();
        JPanel drinksMenu = createDrinksMenu();
        JPanel dessertMenu = createDessertMenu();
        JPanel cart = createCartScreen();

        // Add screens to the main panel
        // ADD EACH SCREEN TO THE MAIN PANEL WITH A UNIQUE NAME
        mainPanel.add(titleScreen, "TITLE");
        mainPanel.add(customerLoginScreen, "LOGIN_CUSTOMER");
        mainPanel.add(accountCreationScreen, "CREATE_ACCOUNT");
        mainPanel.add(adminLoginScreen, "LOGIN_ADMIN");
        mainPanel.add(customerHome, "CUSTOMER_HOME");
        mainPanel.add(accountInfo, "ACCOUNT_INFO");
        mainPanel.add(addressInput, "ADDRESS_INPUT");
        mainPanel.add(pizzaMenu, "PIZZA_MENU");
        mainPanel.add(sidesMenu, "SIDES_MENU");
        mainPanel.add(drinksMenu, "DRINKS_MENU");
        mainPanel.add(dessertMenu, "DESSERT_MENU");
        mainPanel.add(cart, "CART");

        // Displays main panel
        add(mainPanel);

        //makes sure a database exists and if it doesn't create a new one
        try{
            if (!database.exists()){
                database.createNewFile();
            }
        }
        catch(IOException e){
            System.out.println("File not created");
        }
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
            back.addActionListener(e -> {
                if(previousScreen.equals("PIZZA_MENU")) {
                    cartItems.removeAll();
                    cartItems.revalidate();
                    cartItems.repaint();
                    cardLayout.show(mainPanel, previousScreen);
                }
                cardLayout.show(mainPanel, previousScreen);
            });

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
        panel.setBackground(Color.WHITE);
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
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Login", false, false, false, "NA");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the combo box that switches the page from the customer to admin login screen
        String[] loginTypes = {"Customer", "Admin"};
        JComboBox<String> loginTypeBox = new JComboBox<>(loginTypes);
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
            /*
            try (BufferedReader br = new BufferedReader(new FileReader(database))) {
            String line;
                // Read the file line by line
                boolean found = false;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split("\\|");
                    System.out.println(data[0]);
                    if(data[0].equals(phoneNumberText.getText()))
                    {
                        found = true;
                        phoneNumberText.setText("");
                        if(data.length == 2)
                        {
                        customer = new Customer(data[0], data[1]);

                        }
                        else{
                            customer = new Customer(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                        }

                        cardLayout.show(mainPanel, "CUSTOMER_HOME");
                    }
                    if(!found){
                        System.out.println("Account not found");
                    }
                }
            } catch (IOException f) {
                f.printStackTrace();
            }
            */
            cardLayout.show(mainPanel, "CUSTOMER_HOME");
        });

        panel.add(loginButton);

        return panel;
    }

    private JPanel createAdminLoginScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
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
        panel.setBackground(Color.WHITE);
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
            if(!streetText.getText().isEmpty() && !cardNumberText.getText().isEmpty()) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(database, true));
                    //adds the customer information into the database
                    writer.write(phoneNumberText.getText() + "|" + nameText.getText() + "|" + streetText.getText() + "|" +
                            cityText.getText() + "|" + stateText.getText() + "|"+ zipcodeText.getText() + "|" + cardNumberText.getText() + "|" + expirationDateText.getText() + "|" +
                            securityCodeText.getText()+"\n");
                    writer.close();
                } catch(Exception exception) {
                    System.out.println(exception.getMessage());
                }
            } else {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(database, true));
                    //adds the reduced customer information into the database
                    writer.write(phoneNumberText.getText() + "|" + nameText.getText()+"\n");
                    writer.close();
                } catch(Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
            phoneNumberText.setText("");
            nameText.setText("");
            streetText.setText("");
            cityText.setText("");
            zipcodeText.setText("");
            cardNumberText.setText("");
            expirationDateText.setText("");
            securityCodeText.setText("");
            cardLayout.show(mainPanel, "LOGIN_CUSTOMER");
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
        panel.setBackground(Color.WHITE);
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
        deliveryButton.addActionListener(e -> cardLayout.show(mainPanel, "ADDRESS_INPUT"));
        panel.add(deliveryButton);

        // Adds the Pickup button on the screen.
        JButton pickupButton = new JButton("Pickup");
        pickupButton.setBounds(170, 380, 190, 70);
        pickupButton.setFont(new Font("Arial", Font.PLAIN, 30));
        pickupButton.addActionListener(e -> cardLayout.show(mainPanel, "PIZZA_MENU"));
        panel.add(pickupButton);

        // Adds the In-Store button on the screen.
        JButton inStoreButton = new JButton("In-Store");
        inStoreButton.setBounds(600, 320, 190, 70);
        inStoreButton.setFont(new Font("Arial", Font.PLAIN, 30));
        inStoreButton.addActionListener(e -> cardLayout.show(mainPanel, "PIZZA_MENU"));
        panel.add(inStoreButton);

        return panel;
    }

    private JPanel createAccountInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
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

    private JPanel createAddressInput() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Add an Address", true, true, false, "CUSTOMER_HOME");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds Address labels and text fields on the screen.
        // Street
        JLabel street = new JLabel("Street");
        street.setFont(new Font("Arial", Font.PLAIN, 20));
        street.setBounds(390, 70, 300, 200);
        panel.add(street);
        JTextField streetText = new JTextField();
        streetText.setBounds(390, 187, 250, 50);
        streetText.setFont(new Font("Arial", Font.PLAIN, 24));
        /*if(null != customer.getStreet()) {
            streetText.setText(customer.getStreet());
        }*/
        panel.add(streetText);
        // City
        JLabel city = new JLabel("City");
        city.setFont(new Font("Arial", Font.PLAIN, 20));
        city.setBounds(390, 150, 300, 200);
        panel.add(city);
        JTextField cityText = new JTextField();
        cityText.setBounds(390, 267, 250, 50);
        cityText.setFont(new Font("Arial", Font.PLAIN, 24));
        /*if(null != customer.getCity()) {
            cityText.setText(customer.getCity());
        }*/
        panel.add(cityText);
        // State
        JLabel state = new JLabel("State");
        state.setFont(new Font("Arial", Font.PLAIN, 20));
        state.setBounds(390, 230, 300, 200);
        panel.add(state);
        JTextField stateText = new JTextField();
        stateText.setBounds(390, 347, 250, 50);
        stateText.setFont(new Font("Arial", Font.PLAIN, 24));
        /*if(null != customer.getState()) {
            stateText.setText(customer.getState());
        }*/
        panel.add(stateText);
        // Zipcode
        JLabel zipcode = new JLabel("Zipcode");
        zipcode.setFont(new Font("Arial", Font.PLAIN, 20));
        zipcode.setBounds(390, 310, 300, 200);
        panel.add(zipcode);
        JTextField zipcodeText = new JTextField();
        zipcodeText.setBounds(390, 427, 250, 50);
        zipcodeText.setFont(new Font("Arial", Font.PLAIN, 24));
        /*if(null != customer.getZipcode()) {
            zipcodeText.setText(customer.getZipcode());
        }*/
        panel.add(zipcodeText);

        // Adds the Continue button to create the account.
        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(getWidth()/2-80, 500, 190, 70);
        continueButton.setFont(new Font("Arial", Font.PLAIN, 30));
        continueButton.addActionListener(e -> {
            customer.setStreet(streetText.getText());
            customer.setCity(cityText.getText());
            customer.setState(stateText.getText());
            customer.setZipcode(zipcodeText.getText());
            /*try (BufferedWriter writer = new BufferedWriter(new FileWriter("Customer Database.txt", true))) {
                writer.write("Customer\nPhone Number: " + customer.getPhoneNumber() + "\n" +
                        "Name: " + customer.getName() + "\nStreet: " + customer.getStreet() + "\n" +
                        "City: " + customer.getCity() + "\nState: " + customer.getState() + "\n" +
                        "\nZipcode: " + customer.getZipcode() + "\n" + "Card Number: " + customer.getCardNum() +
                        "\nExpiration Date: " + customer.getExpDate() + "\n" + "CVV: " + customer.getCvv()+"\n");
                writer.newLine();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }*/
            cardLayout.show(mainPanel, "PIZZA_MENU");
        });
        panel.add(continueButton);

        return panel;
    }

    private JPanel createPizzaMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Explore the Menu", true, true, false, "CUSTOMER_HOME");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Panel with each type of menu labeled on it; to be added to every menu.
        JPanel menuLabels = new JPanel();
        menuLabels.setLayout(null);
        menuLabels.setBackground(new Color(0xeeeeee));
        menuLabels.setBorder(BorderFactory.createLineBorder(Color.black));
        menuLabels.setBounds(50, 150, 635, 75);
        // Pizza
        JLabel pizzaLabel = new JLabel("Pizza");
        pizzaLabel.setFont(new Font("Arial", Font.BOLD, 30));
        pizzaLabel.setBounds(25, 15, 200, 50);
        menuLabels.add(pizzaLabel);
        // Sides
        JLabel sidesLabel = new JLabel("Sides");
        sidesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        sidesLabel.setBounds(175, 25, 90, 30);
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sidesLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                sidesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "SIDES_MENU");
            }
        });
        menuLabels.add(sidesLabel);
        // Drinks
        JLabel drinksLabel = new JLabel("Drinks");
        drinksLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        drinksLabel.setBounds(325, 25, 95, 30);
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                drinksLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                drinksLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DRINKS_MENU");
            }
        });
        menuLabels.add(drinksLabel);
        // Dessert
        JLabel dessertLabel = new JLabel("Dessert");
        dessertLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        dessertLabel.setBounds(490, 25, 115, 30);
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dessertLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                dessertLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DESSERT_MENU");
            }
        });
        menuLabels.add(dessertLabel);

        // Adds the labels and radio buttons for the crust options
        JLabel crustText = new JLabel("<html><u>Crust</u><html>");
        crustText.setFont(new Font("Arial", Font.PLAIN, 30));
        crustText.setBounds(50, 250, 100, 50);
        panel.add(crustText);

        JLabel thinCrust = new JLabel("Thin");
        thinCrust.setFont(new Font("Arial", Font.PLAIN, 30));
        thinCrust.setBounds(25, 325, 120, 50);
        thinCrust.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(thinCrust);
        JRadioButton thinCrustButton = new JRadioButton();
        thinCrustButton.setBounds(165, 335, 30, 30);
        thinCrustButton.setBackground(Color.WHITE);
        panel.add(thinCrustButton);

        JLabel regularCrust = new JLabel("Regular");
        regularCrust.setFont(new Font("Arial", Font.PLAIN, 30));
        regularCrust.setBounds(25, 400, 120, 50);
        regularCrust.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(regularCrust);
        JRadioButton regularCrustButton = new JRadioButton();
        regularCrustButton.setBounds(165, 410, 25, 25);
        regularCrustButton.setBackground(Color.WHITE);
        panel.add(regularCrustButton);

        JLabel panCrust = new JLabel("Pan");
        panCrust.setFont(new Font("Arial", Font.PLAIN, 30));
        panCrust.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panCrust.setBounds(25, 475, 120, 50);
        panel.add(panCrust);
        JRadioButton panCrustButton = new JRadioButton();
        panCrustButton.setBounds(165, 485, 25, 25);
        panCrustButton.setBackground(Color.WHITE);
        panel.add(panCrustButton);

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustButton);
        crustGroup.add(regularCrustButton);
        crustGroup.add(panCrustButton);

        // Adds the labels and radio buttons for the pizza sizes
        JLabel sizeText = new JLabel("<html><u>Size</u><html>");
        sizeText.setFont(new Font("Arial", Font.PLAIN, 30));
        sizeText.setBounds(340, 250, 100, 50);
        panel.add(sizeText);

        JLabel smallSize = new JLabel("Small $5.00");
        smallSize.setFont(new Font("Arial", Font.PLAIN, 30));
        smallSize.setBounds(225, 305, 280, 50);
        smallSize.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(smallSize);
        JRadioButton smallSizeButton = new JRadioButton();
        smallSizeButton.setBounds(525, 315, 30, 30);
        smallSizeButton.setBackground(Color.WHITE);
        panel.add(smallSizeButton);

        JLabel mediumSize = new JLabel("Medium $7.00");
        mediumSize.setFont(new Font("Arial", Font.PLAIN, 30));
        mediumSize.setBounds(225, 365, 280, 50);
        mediumSize.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(mediumSize);
        JRadioButton mediumSizeButton = new JRadioButton();
        mediumSizeButton.setBounds(525, 375, 25, 25);
        mediumSizeButton.setBackground(Color.WHITE);
        panel.add(mediumSizeButton);

        JLabel largeSize = new JLabel("Large $9.00");
        largeSize.setFont(new Font("Arial", Font.PLAIN, 30));
        largeSize.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        largeSize.setBounds(225, 425, 280, 50);
        panel.add(largeSize);
        JRadioButton largeSizeButton = new JRadioButton();
        largeSizeButton.setBounds(525, 435, 25, 25);
        largeSizeButton.setBackground(Color.WHITE);
        panel.add(largeSizeButton);

        JLabel extraLargeSize = new JLabel("Extra Large $12.00");
        extraLargeSize.setFont(new Font("Arial", Font.PLAIN, 30));
        extraLargeSize.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        extraLargeSize.setBounds(225, 485, 280, 50);
        panel.add(extraLargeSize);
        JRadioButton extraLargeSizeButton = new JRadioButton();
        extraLargeSizeButton.setBounds(525, 495, 25, 25);
        extraLargeSizeButton.setBackground(Color.WHITE);
        panel.add(extraLargeSizeButton);

        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smallSizeButton);
        sizeGroup.add(mediumSizeButton);
        sizeGroup.add(largeSizeButton);
        sizeGroup.add(extraLargeSizeButton);

        // Adds the toppings label and the panels that show each topping.
        JLabel toppingsText = new JLabel("<html><u>Toppings *</u><html>");
        toppingsText.setFont(new Font("Arial", Font.PLAIN, 30));
        toppingsText.setBounds(700, 250, 150, 50);
        panel.add(toppingsText);
        boolean[] toppings = new boolean[9]; // Instantiates the toppings array here to that the mouseListeners below can alter the topping choices

        JPanel cheesePanel = new JPanel();
        cheesePanel.setLayout(null);
        cheesePanel.setBounds(600, 300, 110, 70);
        JLabel cheeseLabel = new JLabel("Cheese");
        cheeseLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cheeseLabel.setBounds(20, 5, 110, 60);
        cheesePanel.add(cheeseLabel);
        cheesePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cheesePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        cheesePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                cheesePanel.setBorder(null);
            }
        });
        cheesePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cheeseLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[0] = true;
            }
        });
        panel.add(cheesePanel);
        JPanel hamPanel = new JPanel();
        hamPanel.setLayout(null);
        hamPanel.setBounds(725, 300, 110, 70);
        JLabel hamLabel = new JLabel("Ham");
        hamLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        hamLabel.setBounds(23, 5, 110, 60);
        hamPanel.add(hamLabel);
        hamPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hamPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        hamPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hamPanel.setBorder(null);
            }
        });
        hamPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hamLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[1] = true;
            }
        });
        panel.add(hamPanel);
        JPanel tomatoPanel = new JPanel();
        tomatoPanel.setLayout(null);
        tomatoPanel.setBounds(850, 300, 110, 70);
        JLabel tomatoLabel = new JLabel("Tomato");
        tomatoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        tomatoLabel.setBounds(20, 5, 110, 60);
        tomatoPanel.add(tomatoLabel);
        tomatoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tomatoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        tomatoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                tomatoPanel.setBorder(null);
            }
        });
        tomatoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tomatoLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[2] = true;
            }
        });
        panel.add(tomatoPanel);

        JPanel pepperoniPanel = new JPanel();
        pepperoniPanel.setLayout(null);
        pepperoniPanel.setBounds(600, 380, 110, 70);
        JLabel pepperoniLabel = new JLabel("Pepperoni");
        pepperoniLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        pepperoniLabel.setBounds(9, 5, 110, 60);
        pepperoniPanel.add(pepperoniLabel);
        pepperoniPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pepperoniPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        pepperoniPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                pepperoniPanel.setBorder(null);
            }
        });
        pepperoniPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pepperoniLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[3] = true;
            }
        });
        panel.add(pepperoniPanel);
        JPanel greenPepperPanel = new JPanel();
        greenPepperPanel.setLayout(null);
        greenPepperPanel.setBounds(725, 380, 110, 70);
        JLabel greenPepperLabel = new JLabel("<html>Green<br/>Pepper<html>");
        greenPepperLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        greenPepperLabel.setBounds(18, 5, 110, 60);
        greenPepperPanel.add(greenPepperLabel);
        greenPepperPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                greenPepperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        greenPepperPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                greenPepperPanel.setBorder(null);
            }
        });
        greenPepperPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                greenPepperLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[4] = true;
            }
        });
        panel.add(greenPepperPanel);
        JPanel mushroomPanel = new JPanel();
        mushroomPanel.setLayout(null);
        mushroomPanel.setBounds(850, 380, 110, 70);
        JLabel mushroomLabel = new JLabel("Mushroom");
        mushroomLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        mushroomLabel.setBounds(9, 5, 110, 60);
        mushroomPanel.add(mushroomLabel);
        mushroomPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mushroomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        mushroomPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                mushroomPanel.setBorder(null);
            }
        });
        mushroomPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mushroomLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[5] = true;
            }
        });
        panel.add(mushroomPanel);

        JPanel sausagePanel = new JPanel();
        sausagePanel.setLayout(null);
        sausagePanel.setBounds(600, 460, 110, 70);
        JLabel sausageLabel = new JLabel("Sausage");
        sausageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        sausageLabel.setBounds(16, 5, 110, 60);
        sausagePanel.add(sausageLabel);
        sausagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sausagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        sausagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                sausagePanel.setBorder(null);
            }
        });
        sausagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sausageLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[6] = true;
            }
        });
        panel.add(sausagePanel);
        JPanel onionPanel = new JPanel();
        onionPanel.setLayout(null);
        onionPanel.setBounds(725, 460, 110, 70);
        JLabel onionLabel = new JLabel("Onion");
        onionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        onionLabel.setBounds(20, 5, 110, 60);
        onionPanel.add(onionLabel);
        onionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                onionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        onionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                onionPanel.setBorder(null);
            }
        });
        onionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onionLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[7] = true;
            }
        });
        panel.add(onionPanel);
        JPanel pineapplePanel = new JPanel();
        pineapplePanel.setLayout(null);
        pineapplePanel.setBounds(850, 460, 110, 70);
        JLabel pineappleLabel = new JLabel("Pineapple");
        pineappleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        pineappleLabel.setBounds(15, 5, 110, 60);
        pineapplePanel.add(pineappleLabel);
        pineapplePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pineapplePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        pineapplePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                pineapplePanel.setBorder(null);
            }
        });
        pineapplePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pineappleLabel.setFont(new Font("Arial", Font.BOLD, 20));
                toppings[8] = true;
            }
        });
        panel.add(pineapplePanel);


        // Adds the label at the bottom of the screen about topping prices
        JLabel toppingsPrice = new JLabel("*Extra topping prices vary on pizza size ($0.72, $1.00, $1.25, $1.50)");
        toppingsPrice.setFont(new Font("Arial", Font.PLAIN, 20));
        toppingsPrice.setBounds(20, 550, 610, 50);
        panel.add(toppingsPrice);

        // Adds the cart button.
        JButton cartButton = new JButton("Cart");
        cartButton.setFont(new Font("Arial", Font.PLAIN, 30));
        cartButton.setBounds(780, 155, 110, 60);
        cartButton.setBackground(new Color(0xeeeeee));
        cartButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "CART");
            for(Item item : cart) {
                JPanel itemPanel = new JPanel(new GridBagLayout());
                itemPanel.setPreferredSize(new Dimension(800, 200)); // Set fixed size for each item
                itemPanel.setMaximumSize(new Dimension(800, 800)); // Prevent resizing
                JLabel itemLabel = new JLabel(item.toString());
                itemLabel.setHorizontalAlignment(SwingConstants.LEFT);
                itemLabel.setFont(new Font("Arial", Font.PLAIN, 30));

                // Create a remove button
                JButton removeButton = new JButton("Remove");
                removeButton.setFont(new Font("Arial", Font.PLAIN, 17));
                removeButton.setPreferredSize(new Dimension(100, 30));
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cart.remove(item);
                        cartItems.remove(itemPanel); // Remove the item panel
                        cartItems.revalidate(); // Refresh the panel
                        cartItems.repaint();
                    }
                });

                // Use GridBagConstraints to position components
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.gridx = 0;
                gbc.gridy = 0;
                itemPanel.add(itemLabel, gbc);

                gbc.weightx = 0.0;
                gbc.gridx = 1;
                itemPanel.add(removeButton, gbc);

                // Add the item panel to the cart
                cartItems.add(itemPanel);
                cartItems.revalidate();
                cartItems.repaint();
            }
        });

        // Adds the counter at the bottom for how many pizzas are ordered
        JLabel count = new JLabel(pizzaCounter+"");
        count.setFont(new Font("Arial", Font.PLAIN, 25));
        count.setBounds(700, 550, 50, 50);
        panel.add(count);
        JButton leftButton = new JButton("<");
        leftButton.setFont(new Font("Arial", Font.PLAIN, 25));
        leftButton.setBounds(635, 550, 50, 50);
        leftButton.setBackground(new Color(0xeeeeee));
        leftButton.addActionListener(e -> {
            if(pizzaCounter > 0) {
                pizzaCounter--;
            }
            count.setText(pizzaCounter + "");
        });
        panel.add(leftButton);
        JButton rightButton = new JButton(">");
        rightButton.setFont(new Font("Arial", Font.PLAIN, 25));
        rightButton.setBounds(735, 550, 50, 50);
        rightButton.setBackground(new Color(0xeeeeee));
        rightButton.addActionListener(e -> {
            if(pizzaCounter < 10) {
                pizzaCounter++;
            }
            count.setText(pizzaCounter + "");
        });
        panel.add(rightButton);

        // Adds the Add to Cart button.
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.PLAIN, 25));
        addToCartButton.setBounds(800, 550, 165, 50);
        addToCartButton.setBackground(new Color(0xeeeeee));
        addToCartButton.addActionListener(e -> {
            // Determines the type of pizza crust and the size
            String pizzaCrust = "", pizzaSize = "";
            if(thinCrustButton.isSelected()) {
                pizzaCrust = "Thin";
            } else if(regularCrustButton.isSelected()){
                pizzaCrust = "Regular";
            } else if(panCrustButton.isSelected()) {
                pizzaCrust = "Pan";
            } else {
                System.out.println("Select a Crust Option");
            }
            crustGroup.clearSelection();
            if(smallSizeButton.isSelected()) {
                pizzaSize = "Small";
            } else if(mediumSizeButton.isSelected()){
                pizzaSize = "Medium";
            } else if(largeSizeButton.isSelected()) {
                pizzaSize = "Large";
            } else if(extraLargeSizeButton.isSelected()) {
                pizzaSize = "Extra Large";
            } else {
                System.out.println("Select a Size Option");
            }
            sizeGroup.clearSelection();
            cheeseLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            hamLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            tomatoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            pepperoniLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            greenPepperLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            mushroomLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            sausageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            onionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            pineappleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            Item pizza = new Item(pizzaCrust, pizzaSize, toppings, pizzaCounter);
            cart.add(pizza);
        });
        panel.add(addToCartButton);

        panel.add(cartButton);
        panel.add(menuLabels);

        return panel;
    }

    private JPanel createSidesMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Explore the Menu", true, true, false, "CUSTOMER_HOME");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Panel with each type of menu labeled on it; to be added to every menu.
        JPanel menuLabels = new JPanel();
        menuLabels.setLayout(null);
        menuLabels.setBackground(new Color(0xeeeeee));
        menuLabels.setBorder(BorderFactory.createLineBorder(Color.black));
        menuLabels.setBounds(50, 150, 635, 75);
        // Pizza
        JLabel pizzaLabel = new JLabel("Pizza");
        pizzaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        pizzaLabel.setBounds(25, 25, 85, 30);
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pizzaLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                pizzaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "PIZZA_MENU");
            }
        });
        menuLabels.add(pizzaLabel);
        // Sides
        JLabel sidesLabel = new JLabel("Sides");
        sidesLabel.setFont(new Font("Arial", Font.BOLD, 30));
        sidesLabel.setBounds(175, 15, 90, 50);
        menuLabels.add(sidesLabel);
        // Drinks
        JLabel drinksLabel = new JLabel("Drinks");
        drinksLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        drinksLabel.setBounds(325, 25, 95, 30);
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                drinksLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                drinksLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DRINKS_MENU");
            }
        });
        menuLabels.add(drinksLabel);
        // Dessert
        JLabel dessertLabel = new JLabel("Dessert");
        dessertLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        dessertLabel.setBounds(490, 25, 115, 30);
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dessertLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                dessertLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DESSERT_MENU");
            }
        });
        menuLabels.add(dessertLabel);

        JButton cartButton = new JButton("Cart");
        cartButton.setFont(new Font("Arial", Font.PLAIN, 30));
        cartButton.setBounds(780, 155, 110, 60);
        cartButton.setBackground(new Color(0xeeeeee));
        cartButton.addActionListener(e -> cardLayout.show(mainPanel, "CART"));

        panel.add(cartButton);
        panel.add(menuLabels);

        //Creates breadsticks panel
        JPanel breadsticks = new JPanel();
        breadsticks.setLayout(null);
        breadsticks.setBounds(20,240,475,155);
        panel.add(breadsticks);

        //Creates label for breadsticks
        JLabel bSticksLabel = new JLabel("Breadsticks");
        bSticksLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        bSticksLabel.setBounds(125, 25, 165, 30);
        breadsticks.add(bSticksLabel);

        //Creates description for breadsticks
        JLabel bSticksDesc = new JLabel("Soft and buttery, served with cheese dip.");
        bSticksDesc.setFont(new Font("Arial", Font.PLAIN, 16));
        bSticksDesc.setBounds(125,50,450,30);
        breadsticks.add(bSticksDesc);

        //Adds price for breadsticks
        JLabel bSticksPrice = new JLabel("$4.00");
        bSticksPrice.setFont(new Font("Arial", Font.PLAIN, 24));
        bSticksPrice.setBounds(400,116,60,50);
        breadsticks.add(bSticksPrice);

        //Creates display text for selecting quantity of breadsticks
        JLabel sticksCount = new JLabel(bSticksCounter+"");
        sticksCount.setFont(new Font("Arial", Font.PLAIN, 25));
        sticksCount.setBounds(575, 343, 50, 50);
        panel.add(sticksCount);

        //Creates left button
        JButton sticksLeftButton = new JButton("<");
        sticksLeftButton.setFont(new Font("Arial", Font.PLAIN, 25));
        sticksLeftButton.setBounds(510, 343, 50, 50);
        sticksLeftButton.setBackground(new Color(0xeeeeee));
        sticksLeftButton.addActionListener(e -> {
            if(bSticksCounter > 0) {
                bSticksCounter--;
            }
            sticksCount.setText(bSticksCounter + "");
        });
        panel.add(sticksLeftButton);

        //Creates right button
        JButton sticksRightButton = new JButton(">");
        sticksRightButton.setFont(new Font("Arial", Font.PLAIN, 25));
        sticksRightButton.setBounds(610, 343, 50, 50);
        sticksRightButton.setBackground(new Color(0xeeeeee));
        sticksRightButton.addActionListener(e -> {
            if(bSticksCounter < 10) {
                bSticksCounter++;
            }
            sticksCount.setText(bSticksCounter + "");
        });
        panel.add(sticksRightButton);


        //Creates breadstick bites panel
        JPanel bitesPanel = new JPanel();
        bitesPanel.setLayout(null);
        bitesPanel.setBounds(20,420,475,155);
        panel.add(bitesPanel);

        //Creates label for breadstick bites
        JLabel bitesLabel = new JLabel("Breadstick Bites");
        bitesLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        bitesLabel.setBounds(125, 25, 195, 30);
        bitesPanel.add(bitesLabel);

        //Creates description for breadstick bites
        JLabel bitesDesc = new JLabel("Perfect bite size treats.");
        bitesDesc.setFont(new Font("Arial", Font.PLAIN, 16));
        bitesDesc.setBounds(125,50,450,30);
        bitesPanel.add(bitesDesc);

        //Adds price for breadstick bites
        JLabel bitesPrice = new JLabel("$2.00");
        bitesPrice.setFont(new Font("Arial", Font.PLAIN, 24));
        bitesPrice.setBounds(400,116,60,50);
        bitesPanel.add(bitesPrice);

        //Creates display text for selecting quantity of breadsticks
        JLabel bitesCount = new JLabel(bitesCounter+"");
        bitesCount.setFont(new Font("Arial", Font.PLAIN, 25));
        bitesCount.setBounds(575, 523, 50, 50);
        panel.add(bitesCount);

        //Creates left button
        JButton bitesLeftButton = new JButton("<");
        bitesLeftButton.setFont(new Font("Arial", Font.PLAIN, 25));
        bitesLeftButton.setBounds(510, 523, 50, 50);
        bitesLeftButton.setBackground(new Color(0xeeeeee));
        bitesLeftButton.addActionListener(e -> {
            if(bitesCounter > 0) {
                bitesCounter--;
            }
            bitesCount.setText(bitesCounter + "");
        });
        panel.add(bitesLeftButton);

        //Creates right button
        JButton bitesRightButton = new JButton(">");
        bitesRightButton.setFont(new Font("Arial", Font.PLAIN, 25));
        bitesRightButton.setBounds(610, 523, 50, 50);
        bitesRightButton.setBackground(new Color(0xeeeeee));
        bitesRightButton.addActionListener(e -> {
            if(bitesCounter < 10) {
                bitesCounter++;
            }
            bitesCount.setText(bitesCounter + "");
        });
        panel.add(bitesRightButton);

        // Adds the Add to Cart button.
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.PLAIN, 25));
        addToCartButton.setBounds(790, 540, 165, 50);
        addToCartButton.setBackground(new Color(0xeeeeee));
        panel.add(addToCartButton);

        return panel;
    }

    private JPanel createDrinksMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Explore the Menu", true, true, false, "CUSTOMER_HOME");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Panel with each type of menu labeled on it; to be added to every menu.
        JPanel menuLabels = new JPanel();
        menuLabels.setLayout(null);
        menuLabels.setBackground(new Color(0xeeeeee));
        menuLabels.setBorder(BorderFactory.createLineBorder(Color.black));
        menuLabels.setBounds(50, 150, 635, 75);
        // Pizza
        JLabel pizzaLabel = new JLabel("Pizza");
        pizzaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        pizzaLabel.setBounds(25, 25, 85, 30);
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pizzaLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                pizzaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "PIZZA_MENU");
            }
        });
        menuLabels.add(pizzaLabel);
        // Sides
        JLabel sidesLabel = new JLabel("Sides");
        sidesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        sidesLabel.setBounds(175, 15, 90, 50);
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sidesLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                sidesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "SIDES_MENU");
            }
        });
        menuLabels.add(sidesLabel);
        // Drinks
        JLabel drinksLabel = new JLabel("Drinks");
        drinksLabel.setFont(new Font("Arial", Font.BOLD, 30));
        drinksLabel.setBounds(325, 15, 95, 50);
        menuLabels.add(drinksLabel);
        // Dessert
        JLabel dessertLabel = new JLabel("Dessert");
        dessertLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        dessertLabel.setBounds(490, 25, 115, 30);
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dessertLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                dessertLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        dessertLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DESSERT_MENU");
            }
        });
        menuLabels.add(dessertLabel);

        JButton cartButton = new JButton("Cart");
        cartButton.setFont(new Font("Arial", Font.PLAIN, 30));
        cartButton.setBounds(780, 155, 110, 60);
        cartButton.setBackground(new Color(0xeeeeee));
        cartButton.addActionListener(e -> cardLayout.show(mainPanel, "CART"));

        panel.add(cartButton);
        panel.add(menuLabels);

         //Creates label for drink prices
        JLabel drinksPrice = new JLabel("All drinks are priced at $1.75");
        drinksPrice.setFont(new Font("Arial", Font.PLAIN, 20));
        drinksPrice.setBounds(20, 225, 610, 50);
        panel.add(drinksPrice);

        //Creates panels and labels for each drink
        JPanel pepsi = createDrink("Pepsi","", 20, 290, 125, 35);
        panel.add(pepsi);
        JPanel crush = createDrink("Crush","",260, 290, 125, 35);
        panel.add(crush);
        JPanel rootBeer = createDrink("Root","Beer", 500, 290, 125, 35);
        panel.add(rootBeer);
        JPanel starry = createDrink("Starry", "",740, 290, 125, 35);
        panel.add(starry);
        JPanel dietPepsi = createDrink("Diet","Pepsi", 20, 430, 125, 35);
        panel.add(dietPepsi);
        JPanel dietCrush = createDrink("Diet", "Crush",260, 430, 125, 35);
        panel.add(dietCrush);
        JPanel dietRootBeer = createDrink("Diet Root","Beer", 500, 430, 125, 35);
        panel.add(dietRootBeer);
        JPanel lemonade = createDrink("Lemonade","", 740, 430, 125, 35);
        panel.add(lemonade);

        return panel;
    }
    //Method to create each drink
    private JPanel createDrink(String name1, String name2, int panelX, int panelY, int labelX, int labelY){
        //Creates and sets the name for the panel
        JPanel newDrink = new JPanel();
        newDrink.setLayout(null);
        newDrink.setBounds(panelX, panelY, 220, 120);
        JLabel drinkLabel = new JLabel("<html>"+name1+"<br/>"+name2+"<html>");
        drinkLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        drinkLabel.setBounds(labelX, labelY, 110, 60);
        newDrink.add(drinkLabel);

        return newDrink;
    }

    private JPanel createDessertMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Explore the Menu", true, true, false, "CUSTOMER_HOME");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Panel with each type of menu labeled on it; to be added to every menu.
        JPanel menuLabels = new JPanel();
        menuLabels.setLayout(null);
        menuLabels.setBackground(new Color(0xeeeeee));
        menuLabels.setBorder(BorderFactory.createLineBorder(Color.black));
        menuLabels.setBounds(50, 150, 635, 75);
        // Pizza
        JLabel pizzaLabel = new JLabel("Pizza");
        pizzaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        pizzaLabel.setBounds(25, 25, 85, 30);
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pizzaLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                pizzaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        pizzaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "PIZZA_MENU");
            }
        });
        menuLabels.add(pizzaLabel);
        // Sides
        JLabel sidesLabel = new JLabel("Sides");
        sidesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        sidesLabel.setBounds(175, 15, 90, 50);
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sidesLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                sidesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        sidesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "SIDES_MENU");
            }
        });
        menuLabels.add(sidesLabel);
        // Drinks
        JLabel drinksLabel = new JLabel("Drinks");
        drinksLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        drinksLabel.setBounds(325, 25, 95, 30);
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                drinksLabel.setFont(new Font("Arial", Font.BOLD, 30));
            }
        });
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                drinksLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        });
        drinksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "DRINKS_MENU");
            }
        });
        menuLabels.add(drinksLabel);
        // Dessert
        JLabel dessertLabel = new JLabel("Dessert");
        dessertLabel.setFont(new Font("Arial", Font.BOLD, 30));
        dessertLabel.setBounds(490, 15, 115, 50);
        menuLabels.add(dessertLabel);

        JButton cartButton = new JButton("Cart");
        cartButton.setFont(new Font("Arial", Font.PLAIN, 30));
        cartButton.setBounds(780, 155, 110, 60);
        cartButton.setBackground(new Color(0xeeeeee));
        cartButton.addActionListener(e -> cardLayout.show(mainPanel, "CART"));

        panel.add(cartButton);
        panel.add(menuLabels);

         //Creates panel and label for the dessert
        JPanel dessertPanel = new JPanel();
        dessertPanel.setLayout(null);
        dessertPanel.setBounds(225, 260, 500, 300);
        JLabel cookieLabel = new JLabel("<html>Mega Chocolate<br/>Chip Cookie<html>");
        cookieLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cookieLabel.setBounds(325, 20, 300, 50);
        dessertPanel.add(cookieLabel);

        //Creates label for the cookie price
        JLabel cookiePrice = new JLabel("<html>$4.00<html>");
        cookiePrice.setFont(new Font("Arial", Font.PLAIN, 20));
        cookiePrice.setBounds(425, 240, 50, 50);
        dessertPanel.add(cookiePrice);
        panel.add(dessertPanel);

        //Adds image of cookie


        //Creates display text for selecting quantity of desserts
        JLabel count = new JLabel(dessertCounter+"");
        count.setFont(new Font("Arial", Font.PLAIN, 25));
        count.setBounds(375, 570, 50, 50);
        panel.add(count);

        //Creates left button
        JButton leftButton = new JButton("<");
        leftButton.setFont(new Font("Arial", Font.PLAIN, 25));
        leftButton.setBounds(310, 570, 50, 50);
        leftButton.setBackground(new Color(0xeeeeee));
        leftButton.addActionListener(e -> {
            if(dessertCounter > 0) {
                dessertCounter--;
            }
            count.setText(dessertCounter + "");
        });
        panel.add(leftButton);

        //Creates right button
        JButton rightButton = new JButton(">");
        rightButton.setFont(new Font("Arial", Font.PLAIN, 25));
        rightButton.setBounds(410, 570, 50, 50);
        rightButton.setBackground(new Color(0xeeeeee));
        rightButton.addActionListener(e -> {
            if(dessertCounter < 10) {
                dessertCounter++;
            }
            count.setText(dessertCounter + "");
        });
        panel.add(rightButton);

        // Adds the Add to Cart button.
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.PLAIN, 25));
        addToCartButton.setBounds(500, 570, 165, 50);
        addToCartButton.setBackground(new Color(0xeeeeee));
        panel.add(addToCartButton);


        return panel;
    }

    private JPanel createCartScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Cart", true, false, false, "PIZZA_MENU");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the content label
        JLabel contentLabel = new JLabel("Contents");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        contentLabel.setBounds(50, 130, 200, 50);
        panel.add(contentLabel);

        // Adds the scrollPane that was defined above in Frame()
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
