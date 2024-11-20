import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Frame --- defines frame; creates the UI and adds functionality
 * the attributes defined are mostly variables defined here because their functionality
 * requires them to be global
 */
public class Frame extends JFrame{
    // The Frame uses CardLayout so that each screen can be made as a panel and buttons are used to swap between them
    private final CardLayout cardLayout;
    // mainPanel acts as a base panel that the other panels can be added on top of
    private final JPanel mainPanel;
    private Customer customer;
    private String orderType = "";
    private final ArrayList<Item> cart = new ArrayList<>();
    private final JPanel cartItems = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(cartItems);
    private float totalCost = 0;
    private final JLabel totalLabel = new JLabel("$0.00");
    JLabel tax = new JLabel("Tax ............................................................. $0.00");
    private int pizzaCounter = 1;
    private int dessertCounter = 1;
    private int bSticksCounter = 1;
    private int bitesCounter = 1;
    private String endStreetText = "";
    private String endCityText = "";
    private String endStateText = "";
    private String endZipText = "";
    private final JLabel completionText = new JLabel();
    private Receipt receiptPanel = new Receipt();
    private final File database = new File("Customer DataBase.txt");
    private final DecimalFormat df = new DecimalFormat("#.00");

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
        JPanel onlinePayment = createOnlinePaymentScreen();
        JPanel inStorePayment = createInStorePaymentScreen();
        JPanel deliveryFinish = createDeliveryFinishScreen();
        JPanel pickupFinish = createPickupFinishScreen();
        JPanel inStoreFinish = createInStoreFinishScreen();

        // Add screens to the main panel with a unique name so buttons can reference them
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
        mainPanel.add(onlinePayment, "ONLINE_PAYMENT");
        mainPanel.add(inStorePayment, "INSTORE_PAYMENT");
        mainPanel.add(deliveryFinish, "DELIVERY_FINISH");
        mainPanel.add(pickupFinish, "PICKUP_FINISH");
        mainPanel.add(inStoreFinish, "INSTORE_FINISH");


        // Displays main panel
        add(mainPanel);

        // makes sure a database exists and if it doesn't create a new one
        try{
            if (!database.exists()){
                database.createNewFile();
            }
        }
        catch(IOException e){
            System.out.println("File not created");
        }
    }

    /**
     * Creates the red banner that appears at the top of every screen.
     * The banner has the option to include a back button, login button, and an account button.
     * @param title String for title to be shown at the top.
     * @param backButton boolean for back button.
     * @param logoutButton boolean for login button.
     * @param accountButton boolean for account button.
     * @param previousScreen String to reference previous screen, or screen that the back button transfers to.
     * @return Returns a JPanel.
     */
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

                    // Updates the Cost of the cart
                    totalCost = 0;
                    for(Item item : cart) {
                        totalCost += item.getPrice();
                    }
                    // Tax
                    totalCost += (totalCost * 0.07f);
                    totalLabel.setText("$"+totalCost);
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

    /**
     * Creates the title screen to show store information and begin the process
     * @return Returns a JPanel.
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

    /**
     * Creates customer login screen that allows the user to login with a phone number
     * and links to the account creation screen.
     * @return Returns a JPanel.
     */
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
                            customer = new Customer(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
                        }

                        cardLayout.show(mainPanel, "CUSTOMER_HOME");
                    }
                    if(!found){
                        JOptionPane.showMessageDialog(mainPanel, "Account Not Found");
                    }
                }
            } catch (IOException f) {
                f.printStackTrace();
            }
        });

        panel.add(loginButton);

        return panel;
    }

    /**
     * Creates the admin login screen which allows the user to login with a password.
     * @return Returns a JPanel.
     */
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
            if(passwordText.getText().equals("123")) {
                cardLayout.show(mainPanel, "CUSTOMER_HOME");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Ivalid password");
            }
        });
        panel.add(loginButton);

        return panel;
    }


    /**
     * Creates the title screen to show store information and begin the process
     * @return Returns a JPanel.
     */
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

    /**
     * Creates the customer home screen. From here, the user can begin their order in three different modalities:
     * Delivery, Pickup, and In-Store.
     * @return Returns a JPanel.
     */
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
        deliveryButton.addActionListener(e -> {
            orderType = "delivery";
            cardLayout.show(mainPanel, "ADDRESS_INPUT");
        });
        panel.add(deliveryButton);

        // Adds the Pickup button on the screen.
        JButton pickupButton = new JButton("Pickup");
        pickupButton.setBounds(170, 380, 190, 70);
        pickupButton.setFont(new Font("Arial", Font.PLAIN, 30));
        pickupButton.addActionListener(e -> {
            orderType = "pickup";
            cardLayout.show(mainPanel, "PIZZA_MENU");
        });
        panel.add(pickupButton);

        // Adds the In-Store button on the screen.
        JButton inStoreButton = new JButton("In-Store");
        inStoreButton.setBounds(600, 320, 190, 70);
        inStoreButton.setFont(new Font("Arial", Font.PLAIN, 30));
        inStoreButton.addActionListener(e -> {
            orderType = "inStore";
            cardLayout.show(mainPanel, "PIZZA_MENU");
        });
        panel.add(inStoreButton);

        return panel;
    }

    /**
     * Creates the account information screen that the user can go to alter their information
     * @return Returns a JPanel.
     */
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

        // Adds the Save Changes button to create the account.
        JButton saveChangesButton = new JButton("<html>Save<br/>Changes<html>");

        saveChangesButton.setBounds(getWidth()/2-80, 500, 160, 90);
        saveChangesButton.setFont(new Font("Arial", Font.PLAIN, 30));
        saveChangesButton.addActionListener(e -> {
            //save changes to the account
        });
        panel.add(saveChangesButton);
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

    /**
     * Creates the address input screen for the delivery order process.
     * The user can input a street, city, state, and zipcode.
     * @return Returns a JPanel.
     */
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
        panel.add(streetText);
        // City
        JLabel city = new JLabel("City");
        city.setFont(new Font("Arial", Font.PLAIN, 20));
        city.setBounds(390, 150, 300, 200);
        panel.add(city);
        JTextField cityText = new JTextField();
        cityText.setBounds(390, 267, 250, 50);
        cityText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cityText);
        // State
        JLabel state = new JLabel("State");
        state.setFont(new Font("Arial", Font.PLAIN, 20));
        state.setBounds(390, 230, 300, 200);
        panel.add(state);
        JTextField stateText = new JTextField();
        stateText.setBounds(390, 347, 250, 50);
        stateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(stateText);
        // Zipcode
        JLabel zipcode = new JLabel("Zipcode");
        zipcode.setFont(new Font("Arial", Font.PLAIN, 20));
        zipcode.setBounds(390, 310, 300, 200);
        panel.add(zipcode);
        JTextField zipcodeText = new JTextField();
        zipcodeText.setBounds(390, 427, 250, 50);
        zipcodeText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(zipcodeText);

        // Adds the Continue button to create the account.
        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(getWidth()/2-80, 500, 190, 70);
        continueButton.setFont(new Font("Arial", Font.PLAIN, 30));
        continueButton.addActionListener(e -> {
            endStreetText = streetText.getText();
            endCityText = cityText.getText();
            endStateText = stateText.getText();
            endZipText = zipcodeText.getText();
            if(streetText.getText().isEmpty() || cityText.getText().isEmpty() || streetText.getText().isEmpty() || zipcodeText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid: Missing Component");
            } else {
                cardLayout.show(mainPanel, "PIZZA_MENU");
            }
        });
        panel.add(continueButton);

        return panel;
    }

    /**
     * Creates the pizza menu screen. The user can add a pizza of a certain crust type, size, and with
     * a choice of nine different toppings. The user can change how many are added and add them to the cart.
     * @return Returns a JPanel.
     */
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

        // Instantiates the toppings array here to that the mouseListeners below can alter the topping choices
        boolean[] toppings = new boolean[9];

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
                cheesePanel.setBackground(new Color(0xA7A6A6));
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
                hamPanel.setBackground(new Color(0xA7A6A6));
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
                tomatoPanel.setBackground(new Color(0xA7A6A6));
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
                pepperoniPanel.setBackground(new Color(0xA7A6A6));
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
                greenPepperPanel.setBackground(new Color(0xA7A6A6));
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
                mushroomPanel.setBackground(new Color(0xA7A6A6));
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
                sausagePanel.setBackground(new Color(0xA7A6A6));
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
                onionPanel.setBackground(new Color(0xA7A6A6));
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
                pineapplePanel.setBackground(new Color(0xA7A6A6));
                toppings[8] = true;
            }
        });
        panel.add(pineapplePanel);


        // Adds the label at the bottom of the screen about topping prices
        JLabel toppingsPrice = new JLabel("*Extra topping prices vary on pizza size ($0.75, $1.00, $1.25, $1.50)");
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
                removeButton.addActionListener(e1 -> {
                    cart.remove(item);
                    cartItems.remove(itemPanel); // Remove the item panel
                    cartItems.revalidate(); // Refresh the panel
                    cartItems.repaint();

                    // Updates the Cost of the cart
                    totalCost = 0;
                    for(Item item1 : cart) {
                        totalCost += item1.getPrice();
                    }
                    // Tax
                    totalCost += (totalCost * 0.07f);
                    totalLabel.setText("$"+df.format(totalCost));
                    float taxFloat = totalCost*0.07f;
                    String formatted = String.format("%.2f", taxFloat);
                    tax.setText("Tax ............................................................. $"+formatted);
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
            JPanel itemPanel = new JPanel(new GridBagLayout());
            itemPanel.setPreferredSize(new Dimension(800, 100)); // Set fixed size for each item
            itemPanel.setMaximumSize(new Dimension(800, 200)); // Prevent resizing
            float taxFloat =totalCost*0.07f;
            String formatted = String.format("%.2f", taxFloat);
            JLabel tax = new JLabel("Tax ............................................................. $"+formatted);
            tax.setHorizontalAlignment(SwingConstants.LEFT);
            tax.setFont(new Font("Arial", Font.PLAIN, 30));

            // Invisible remove button so that Tax is formatted on the Cart screen correctly
            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Arial", Font.PLAIN, 17));
            removeButton.setPreferredSize(new Dimension(100, 30));
            removeButton.setVisible(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            itemPanel.add(tax, gbc);
            gbc.weightx = 0.0;
            gbc.gridx = 1;
            itemPanel.add(removeButton, gbc);
            cartItems.add(itemPanel);
            cartItems.revalidate();
            cartItems.repaint();
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
            if(pizzaCounter > 1) {
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
            boolean crustSelected = false;
            boolean sizeSelected = false;
            // Determines the type of pizza crust and the size
            String pizzaCrust = "", pizzaSize = "";
            if(thinCrustButton.isSelected()) {
                pizzaCrust = "Thin";
                crustSelected = true;
            } else if(regularCrustButton.isSelected()){
                pizzaCrust = "Regular";
                crustSelected = true;
            } else if(panCrustButton.isSelected()) {
                pizzaCrust = "Pan";
                crustSelected = true;
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select a crust option");
            }
            if(smallSizeButton.isSelected()) {
                pizzaSize = "Small";
                sizeSelected = true;
            } else if(mediumSizeButton.isSelected()){
                pizzaSize = "Medium";
                sizeSelected = true;
            } else if(largeSizeButton.isSelected()) {
                pizzaSize = "Large";
                sizeSelected = true;
            } else if(extraLargeSizeButton.isSelected()) {
                pizzaSize = "Extra Large";
                sizeSelected = true;
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select a size option");
            }
            if(sizeSelected && crustSelected) {
                crustGroup.clearSelection();
                sizeGroup.clearSelection();
                // Resets the selections for the toppings
                cheesePanel.setBackground(new Color(0xeeeeee));
                hamPanel.setBackground(new Color(0xeeeeee));
                tomatoPanel.setBackground(new Color(0xeeeeee));
                pepperoniPanel.setBackground(new Color(0xeeeeee));
                greenPepperPanel.setBackground(new Color(0xeeeeee));
                mushroomPanel.setBackground(new Color(0xeeeeee));
                sausagePanel.setBackground(new Color(0xeeeeee));
                onionPanel.setBackground(new Color(0xeeeeee));
                pineapplePanel.setBackground(new Color(0xeeeeee));
                Item pizza = new Item(pizzaCrust, pizzaSize, toppings);
                for(int i=0; i<pizzaCounter; i++) {
                    cart.add(pizza);
                }
                Arrays.fill(toppings, false);
                pizzaCounter = 1;
                count.setText(pizzaCounter + "");

                // Updates the Cost of the cart
                totalCost = 0;
                for(Item item : cart) {
                    totalCost += item.getPrice();
                }
                // Tax
                totalCost += (totalCost * 0.07f);
                totalLabel.setText("$"+df.format(totalCost));
            }
            // Resets Selection
            crustGroup.clearSelection();
            sizeGroup.clearSelection();
            cheesePanel.setBackground(new Color(0xeeeeee));
            hamPanel.setBackground(new Color(0xeeeeee));
            tomatoPanel.setBackground(new Color(0xeeeeee));
            pepperoniPanel.setBackground(new Color(0xeeeeee));
            greenPepperPanel.setBackground(new Color(0xeeeeee));
            mushroomPanel.setBackground(new Color(0xeeeeee));
            sausagePanel.setBackground(new Color(0xeeeeee));
            onionPanel.setBackground(new Color(0xeeeeee));
            pineapplePanel.setBackground(new Color(0xeeeeee));
            Arrays.fill(toppings, false);
        });
        panel.add(addToCartButton);

        // Button to clear toppings
        JButton clearToppings = new JButton("Clear");
        clearToppings.setFont(new Font("Arial", Font.PLAIN, 15));
        clearToppings.setBounds(880, 240, 70, 50);
        clearToppings.setBackground(new Color(0xeeeeee));
        clearToppings.addActionListener(e -> {
            cheesePanel.setBackground(new Color(0xeeeeee));
            hamPanel.setBackground(new Color(0xeeeeee));
            tomatoPanel.setBackground(new Color(0xeeeeee));
            pepperoniPanel.setBackground(new Color(0xeeeeee));
            greenPepperPanel.setBackground(new Color(0xeeeeee));
            mushroomPanel.setBackground(new Color(0xeeeeee));
            sausagePanel.setBackground(new Color(0xeeeeee));
            onionPanel.setBackground(new Color(0xeeeeee));
            pineapplePanel.setBackground(new Color(0xeeeeee));
            Arrays.fill(toppings, false);
        });
        panel.add(clearToppings);

        panel.add(cartButton);
        panel.add(menuLabels);

        return panel;
    }

    /**
     * Creates the side menu scree. The user has a choice of two sides and can change how many they want to add to their cart.
     * @return Returns a JPanel.
     */
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
                removeButton.addActionListener(e1 -> {
                    cart.remove(item);
                    cartItems.remove(itemPanel); // Remove the item panel
                    cartItems.revalidate(); // Refresh the panel
                    cartItems.repaint();

                    // Updates the Cost of the cart
                    totalCost = 0;
                    for(Item item1 : cart) {
                        totalCost += item1.getPrice();
                    }
                    // Tax
                    totalCost += (totalCost * 0.07f);
                    totalLabel.setText("$"+df.format(totalCost));
                    float taxFloat = totalCost*0.07f;
                    String formatted = String.format("%.2f", taxFloat);
                    tax.setText("Tax ............................................................. $"+formatted);
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
            JPanel itemPanel = new JPanel(new GridBagLayout());
            itemPanel.setPreferredSize(new Dimension(800, 100)); // Set fixed size for each item
            itemPanel.setMaximumSize(new Dimension(800, 200)); // Prevent resizing
            float taxFloat =totalCost*0.07f;
            String formatted = String.format("%.2f", taxFloat);
            JLabel tax = new JLabel("Tax ............................................................. $"+formatted);
            tax.setHorizontalAlignment(SwingConstants.LEFT);
            tax.setFont(new Font("Arial", Font.PLAIN, 30));

            // Invisible remove button so that Tax is formatted on the Cart screen correctly
            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Arial", Font.PLAIN, 17));
            removeButton.setPreferredSize(new Dimension(100, 30));
            removeButton.setVisible(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            itemPanel.add(tax, gbc);
            gbc.weightx = 0.0;
            gbc.gridx = 1;
            itemPanel.add(removeButton, gbc);
            cartItems.add(itemPanel);
            cartItems.revalidate();
            cartItems.repaint();
        });

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
        bSticksPrice.setBounds(360,116,80,50);
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
        bitesPrice.setBounds(360,116,80,50);
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
        addToCartButton.addActionListener(e -> {
            for(int i=0; i<bSticksCounter; i++) {
                cart.add(new Item("breadStick"));
            }
            for(int i=0; i<bitesCounter; i++) {
                cart.add(new Item("bites"));
            }
            bitesCounter = 1;
            bitesCount.setText(bitesCount+"");
            bSticksCounter = 1;
            sticksCount.setText(bSticksCounter+"");

            // Updates the Cost of the cart
            totalCost = 0;
            for(Item item : cart) {
                totalCost += item.getPrice();
            }
            // Tax
            totalCost += (totalCost * 0.07f);
            totalLabel.setText("$"+totalCost);
        });
        panel.add(addToCartButton);

        return panel;
    }

    /**
     * Creates the drinks menu screen. The user has a choice of 8 different drinks of three different sizes.
     * @return Returns a JPanel.
     */
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
                removeButton.addActionListener(e1 -> {
                    cart.remove(item);
                    cartItems.remove(itemPanel); // Remove the item panel
                    cartItems.revalidate(); // Refresh the panel
                    cartItems.repaint();

                    // Updates the Cost of the cart
                    totalCost = 0;
                    for(Item item1 : cart) {
                        totalCost += item1.getPrice();
                    }
                    // Tax
                    totalCost += (totalCost * 0.07f);
                    totalLabel.setText("$"+df.format(totalCost));
                    float taxFloat = totalCost*0.07f;
                    String formatted = String.format("%.2f", taxFloat);
                    tax.setText("Tax ............................................................. $"+formatted);
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
            JPanel itemPanel = new JPanel(new GridBagLayout());
            itemPanel.setPreferredSize(new Dimension(800, 100)); // Set fixed size for each item
            itemPanel.setMaximumSize(new Dimension(800, 200)); // Prevent resizing
            float taxFloat =totalCost*0.07f;
            String formatted = String.format("%.2f", taxFloat);
            JLabel tax = new JLabel("Tax ............................................................. $"+formatted);
            tax.setHorizontalAlignment(SwingConstants.LEFT);
            tax.setFont(new Font("Arial", Font.PLAIN, 30));

            // Invisible remove button so that Tax is formatted on the Cart screen correctly
            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Arial", Font.PLAIN, 17));
            removeButton.setPreferredSize(new Dimension(100, 30));
            removeButton.setVisible(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            itemPanel.add(tax, gbc);
            gbc.weightx = 0.0;
            gbc.gridx = 1;
            itemPanel.add(removeButton, gbc);
            cartItems.add(itemPanel);
            cartItems.revalidate();
            cartItems.repaint();
        });

        panel.add(cartButton);
        panel.add(menuLabels);

        //Creates label for drink prices
        JLabel drinksPrice = new JLabel("All drinks are priced at $1.75");
        drinksPrice.setFont(new Font("Arial", Font.PLAIN, 20));
        drinksPrice.setBounds(20, 225, 610, 50);
        panel.add(drinksPrice);

        //Creates panels and labels for each drink
        JPanel pepsi = createDrink("Pepsi","", 20, 290);
        panel.add(pepsi);
        JPanel crush = createDrink("Crush","",260, 290);
        panel.add(crush);
        JPanel rootBeer = createDrink("Root","Beer", 500, 290);
        panel.add(rootBeer);
        JPanel starry = createDrink("Starry", "",740, 290);
        panel.add(starry);
        JPanel dietPepsi = createDrink("Diet","Pepsi", 20, 430);
        panel.add(dietPepsi);
        JPanel dietCrush = createDrink("Diet", "Crush",260, 430);
        panel.add(dietCrush);
        JPanel dietRootBeer = createDrink("Diet Root","Beer", 500, 430);
        panel.add(dietRootBeer);
        JPanel lemonade = createDrink("Lemonade","", 740, 430);
        panel.add(lemonade);

        return panel;
    }

    /**
     * Creates the panel for each drink. These panels are used on the drink menu screen.
     * @param name1 first part of drink name.
     * @param name2 second part of drink name.
     * @param panelX panel location on the x-axis
     * @param panelY panel location on the y-axis
     * @return Returns a JPanel.
     */
    private JPanel createDrink(String name1, String name2, int panelX, int panelY){
        //Creates and sets the name for the panel
        JPanel newDrink = new JPanel();
        newDrink.setLayout(null);
        newDrink.setBounds(panelX, panelY, 220, 120);
        JLabel drinkLabel = new JLabel("<html>"+name1+"<br/>"+name2+"<html>");
        drinkLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        drinkLabel.setBounds(125, 35, 130, 80);
        newDrink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newDrink.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        newDrink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                newDrink.setBorder(null);
            }
        });
        newDrink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drinkPopup(name1+name2);
            }
        });
        newDrink.add(drinkLabel);

        return newDrink;
    }

    /**
     * Generates the JOptionPane that appears when a drink is selected on the drink screen menu.
     * The option pane offers the choice of three different sizes and to choose the amount.
     * @param nameOfDrink string with the name of the drink.
     */
    private void drinkPopup(String nameOfDrink) {
        // Create components
        JComboBox<String> sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});

        JTextField quantityField = new JTextField("1", 5);
        quantityField.setEditable(false);
        quantityField.setHorizontalAlignment(JTextField.CENTER);

        JButton incrementButton = new JButton(">");
        JButton decrementButton = new JButton("<");

        // Add action listeners for buttons
        incrementButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(quantityField.getText());
                quantityField.setText(String.valueOf(value + 1));
            } catch (NumberFormatException ex) {
                quantityField.setText("1");
            }
        });

        decrementButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(quantityField.getText());
                if (value > 1) { // Prevent quantity from going below 1
                    quantityField.setText(String.valueOf(value - 1));
                }
            } catch (NumberFormatException ex) {
                quantityField.setText("1");
            }
        });

        JButton addToCartButton = new JButton("Add to Cart");

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Size: "), gbc);

        gbc.gridx = 1;
        panel.add(sizeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(decrementButton, gbc);

        gbc.gridx = 1;
        panel.add(quantityField, gbc);

        gbc.gridx = 2;
        panel.add(incrementButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(addToCartButton, gbc);

        // Display the JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel, "Select Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String size = (String) sizeComboBox.getSelectedItem();
            String quantity = quantityField.getText();
            JOptionPane.showMessageDialog(null, "Added to cart: " + size + " - Quantity: " + quantity);
            for(int i=0; i<Integer.parseInt(quantity); i++) {
                cart.add(new Item(nameOfDrink, size));
            }
            // Updates the Cost of the cart
            totalCost = 0;
            for(Item item : cart) {
                totalCost += item.getPrice();
            }
            // Tax
            totalCost += (totalCost * 0.07f);
            totalLabel.setText("$"+totalCost);
        }
    }

    /**
     * Creates the dessert screen. The user has an option to add any amount of one dessert.
     * @return Returns a JPanel.
     */
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
                removeButton.addActionListener(e1 -> {
                    cart.remove(item);
                    cartItems.remove(itemPanel); // Remove the item panel
                    cartItems.revalidate(); // Refresh the panel
                    cartItems.repaint();

                    // Updates the Cost of the cart
                    totalCost = 0;
                    for(Item item1 : cart) {
                        totalCost += item1.getPrice();
                    }
                    // Tax
                    totalCost += (totalCost * 0.07f);
                    totalLabel.setText("$"+df.format(totalCost));
                    float taxFloat = totalCost*0.07f;
                    String formatted = String.format("%.2f", taxFloat);
                    tax.setText("Tax ............................................................. $"+formatted);
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
            JPanel itemPanel = new JPanel(new GridBagLayout());
            itemPanel.setPreferredSize(new Dimension(800, 100)); // Set fixed size for each item
            itemPanel.setMaximumSize(new Dimension(800, 200)); // Prevent resizing
            float taxFloat =totalCost*0.07f;
            String formatted = String.format("%.2f", taxFloat);
            JLabel tax = new JLabel("Tax ............................................................. $"+formatted);
            tax.setHorizontalAlignment(SwingConstants.LEFT);
            tax.setFont(new Font("Arial", Font.PLAIN, 30));

            // Invisible remove button so that Tax is formatted on the Cart screen correctly
            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Arial", Font.PLAIN, 17));
            removeButton.setPreferredSize(new Dimension(100, 30));
            removeButton.setVisible(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            itemPanel.add(tax, gbc);
            gbc.weightx = 0.0;
            gbc.gridx = 1;
            itemPanel.add(removeButton, gbc);
            cartItems.add(itemPanel);
            cartItems.revalidate();
            cartItems.repaint();
        });

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
            if(dessertCounter > 1) {
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
        addToCartButton.setBounds(790, 540, 165, 50);
        addToCartButton.setBackground(new Color(0xeeeeee));
        addToCartButton.addActionListener(e -> {
            for(int i=0; i<dessertCounter; i++) {
                cart.add(new Item());
            }
            dessertCounter = 1;
            count.setText(dessertCounter+"");

            // Updates the Cost of the cart
            totalCost = 0;
            for(Item item : cart) {
                totalCost += item.getPrice();
            }
            // Tax
            totalCost += (totalCost * 0.07f);
            totalLabel.setText("$"+totalCost);
        });
        panel.add(addToCartButton);


        return panel;
    }

    /**
     * Creates the cart screen where the user can view every item that was added and the price.
     * The user can also remove items from the cart and select continue to pay for the order.
     * @return Returns a JPanel.
     */
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

        // Adds the total price to the bottom of the cart screen
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        totalLabel.setBounds(50, 550, 140, 50);
        totalLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        totalLabel.setBackground(new Color(0xeeeeee));
        panel.add(totalLabel);

        // Adds the Cancel Order button to clear the cart
        JButton cancelOrder = new JButton("Cancel Order");
        cancelOrder.setBounds(350, 550, 200, 50);
        cancelOrder.setFont(new Font("Arial", Font.PLAIN, 25));
        cancelOrder.setBackground(new Color(0xeeeeee));
        cancelOrder.addActionListener(e -> {
            cardLayout.show(mainPanel, "Title");
            cart.clear();
            cartItems.revalidate();
            cartItems.repaint();
        });
        panel.add(cancelOrder);

        // Adds the Continue button to clear the cart
        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(720, 550, 200, 50);
        continueButton.setFont(new Font("Arial", Font.PLAIN, 25));
        continueButton.setBackground(new Color(0xeeeeee));
        continueButton.addActionListener(e -> {
            if(cart.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Add something to your cart!");
            } else {
                switch (orderType) {
                    case "delivery", "pickup" -> cardLayout.show(mainPanel, "ONLINE_PAYMENT");
                    default -> cardLayout.show(mainPanel, "INSTORE_PAYMENT");
                }
            }
        });
        panel.add(continueButton);

        return panel;
    }

    /**
     * Creates the Online payment screen where the user can enter their payment details:
     * card number, expiration date, and CVV.
     * @return Returns a JPanel.
     */
    private JPanel createOnlinePaymentScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Online Payment", true, false, false, "CART");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds Payment labels and text fields on the screen.
        // Card Number
        JLabel cardNum = new JLabel("Card Number");
        cardNum.setFont(new Font("Arial", Font.PLAIN, 20));
        cardNum.setBounds(390, 70, 300, 200);
        panel.add(cardNum);
        JTextField cardNumText = new JTextField();
        cardNumText.setBounds(390, 187, 250, 50);
        cardNumText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cardNumText);
        // Expiration Date
        JLabel expDate = new JLabel("Expiration Date");
        expDate.setFont(new Font("Arial", Font.PLAIN, 20));
        expDate.setBounds(390, 150, 300, 200);
        panel.add(expDate);
        JTextField expDateText = new JTextField();
        expDateText.setBounds(390, 267, 250, 50);
        expDateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(expDateText);
        // CVV
        JLabel cvv = new JLabel("CVV");
        cvv.setFont(new Font("Arial", Font.PLAIN, 20));
        cvv.setBounds(390, 230, 300, 200);
        panel.add(cvv);
        JTextField cvvText = new JTextField();
        cvvText.setBounds(390, 347, 250, 50);
        cvvText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cvvText);

        // Adds the complete order button to create the account.
        JButton completeOrderButton = new JButton("Complete Order");
        completeOrderButton.setBounds(getWidth()/2-80, 500, 190, 70);
        completeOrderButton.setFont(new Font("Arial", Font.PLAIN, 20));
        completeOrderButton.addActionListener(e -> {
            if(cardNumText.getText().isEmpty() || expDateText.getText().isEmpty() || cvvText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid: Missing Component");
            } else {
                if ("delivery".equals(orderType)) {
                    completionText.setText("<html>Your order is complete!</br>It will be delivered to:</br>" + endStreetText + "</br>" + endCityText + ", " + endStateText + "</br>" + endZipText + "</br>In 20 minutes</html>");
                    cardLayout.show(mainPanel, "DELIVERY_FINISH");
                } else {
                    cardLayout.show(mainPanel, "PICKUP_FINISH");
                }
            }
        });
        panel.add(completeOrderButton);

        return panel;
    }

    /**
     * Creates the In-Store payment screen where the user can either enter their card payment details,
     * or an amount of cash or by check.
     * @return Returns a JPanel.
     */
    private JPanel createInStorePaymentScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("In-Store Payment", true, false, false, "CART");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds Payment labels and text fields on the screen.
        // Card Number
        JLabel cardNum = new JLabel("Card Number");
        cardNum.setFont(new Font("Arial", Font.PLAIN, 20));
        cardNum.setBounds(100, 70, 300, 200);
        panel.add(cardNum);
        JTextField cardNumText = new JTextField();
        cardNumText.setBounds(100, 187, 250, 50);
        cardNumText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cardNumText);

        // Expiration Date
        JLabel expDate = new JLabel("Expiration Date");
        expDate.setFont(new Font("Arial", Font.PLAIN, 20));
        expDate.setBounds(100, 150, 300, 200);
        panel.add(expDate);
        JTextField expDateText = new JTextField();
        expDateText.setBounds(100, 267, 250, 50);
        expDateText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(expDateText);

        // CVV
        JLabel cvv = new JLabel("CVV");
        cvv.setFont(new Font("Arial", Font.PLAIN, 20));
        cvv.setBounds(100, 230, 300, 200);
        panel.add(cvv);
        JTextField cvvText = new JTextField();
        cvvText.setBounds(100, 347, 250, 50);
        cvvText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cvvText);

        // Cash Amount
        JLabel cash = new JLabel("Enter Cash Amount");
        cash.setFont(new Font("Arial", Font.PLAIN, 20));
        cash.setBounds(600, 100, 300, 200);
        panel.add(cash);
        JTextField cashText = new JTextField();
        cashText.setBounds(600, 217, 250, 50);
        cashText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(cashText);

        // Check Amount
        JLabel check = new JLabel("Enter Check Amount");
        check.setFont(new Font("Arial", Font.PLAIN, 20));
        check.setBounds(600, 230, 300, 200);
        panel.add(check);
        JTextField checkText = new JTextField();
        checkText.setBounds(600, 347, 250, 50);
        checkText.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(checkText);

        // Adds the complete order button to create the account.
        JButton completeOrderButton = new JButton("Complete Order");
        completeOrderButton.setBounds(getWidth()/2-80, 500, 190, 70);
        completeOrderButton.setFont(new Font("Arial", Font.PLAIN, 20));
        completeOrderButton.addActionListener(e -> {
            if((cardNumText.getText().isEmpty() || expDateText.getText().isEmpty() || cvvText.getText().isEmpty()) || (checkText.getText().isEmpty() && cashText.getText().isEmpty())) {
                JOptionPane.showMessageDialog(mainPanel, "Please enter a payment method");
            } else {
                for(Item item : cart) {
                    receiptPanel.addItem(item.toString(), item.getPrice());
                }
                cardLayout.show(mainPanel, "INSTORE_FINISH");
            }
        });
        panel.add(completeOrderButton);

        return panel;
    }

    /**
     * Creates the final screen for the delivery order process.
     * The user can exit to go back to the home screen.
     * @return Returns a JPanel.
     */
    private JPanel createDeliveryFinishScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Order Complete", false, false, false, "CART");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the text show when the order is completed
        completionText.setText("<html>Your order is complete!</br>It will be delivered to: </br>"+endStreetText+" </br>"+endCityText+", "+endStateText+" </br>"+endZipText+"</br> In 20 minutes</html>");
        completionText.setFont(new Font("Arial", Font.PLAIN, 30));
        completionText.setBounds(100, 60, 400, 500);
        panel.add(completionText);

        // Adds the receipt on the right of the screen
        JPanel receipt = createReceiptPanel();
        receipt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        receipt.setBounds(560, 170, 380, 400);
        panel.add(receipt);

        // Adds the exit button to exit back to the customer home
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(180, 470, 150, 70);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 30));
        exitButton.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_HOME"));
        panel.add(exitButton);

        return panel;
    }

    /**
     * Creates the final screen for the pickup order process.
     * The user can exit to go back to the home screen.
     * @return Returns a JPanel.
     */
    private JPanel createPickupFinishScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Order Complete", false, false, false, "CART");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);


        completionText.setText("<html>Your order is complete!</br>It will be ready for pickup in</br>20minutes!</html>");
        completionText.setFont(new Font("Arial", Font.PLAIN, 30));
        completionText.setBounds(100, 60, 400, 500);
        panel.add(completionText);

        // Adds the receipt on the right of the screen
        JPanel receipt = createReceiptPanel();
        receipt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        receipt.setBounds(560, 170, 380, 400);
        panel.add(receipt);

        // Adds the exit button to exit back to the customer home
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(180, 470, 150, 70);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 30));
        exitButton.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_HOME"));
        panel.add(exitButton);

        return panel;
    }

    /**
     * Creates the final screen for the in-store order process.
     * The user can exit to go back to the home screen.
     * @return Returns a JPanel.
     */
    private JPanel createInStoreFinishScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        // Adds the red panel and text at the top of the screen.
        JPanel redPanel = createRedBanner("Order Complete", false, false, false, "CART");
        redPanel.setBounds(0, 0, getWidth(), getHeight()/5);
        panel.add(redPanel);

        // Adds the text on the left of the screen
        completionText.setText("<html>Your order is complete!</br> It will be ready in</br> 10minutes!</html>");
        completionText.setFont(new Font("Arial", Font.PLAIN, 30));
        completionText.setBounds(100, 60, 330, 500);
        panel.add(completionText);

        // Adds the receipt on the right of the screen
        JPanel receipt = createReceiptPanel();
        receipt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        receipt.setBounds(560, 170, 350, 400);
        for(Item item : cart) {
            receiptPanel.addItem(item.toString(), item.getPrice());
        }
        panel.add(receipt);

        // Adds the exit button to exit back to the customer home
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(180, 470, 150, 70);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 30));
        exitButton.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_HOME"));
        panel.add(exitButton);

        return panel;
    }

    /**
     * Creates the receipt panel that is visible on the last slide of each order process.
     * @return Returns a JPanel.
     */
    private JPanel createReceiptPanel() {
        receiptPanel = new Receipt();
        for(Item item : cart) {
            receiptPanel.addItem(item.toString(), item.getPrice());
        }
        return receiptPanel;
    }
}
