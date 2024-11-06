import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //Creates the main window the program will be presented as.
        JFrame window = new JFrame();
        window.setTitle("Mom and Pop's Ordering System");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(false);
        window.setSize(1500, 1000);
        window.setVisible(true);

        //Creates the text on top of the screen that acts as the title for each slide.
        JLabel title = new JLabel();
        title.setText("Mom and Pop's Pizzeria");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Arial", Font.PLAIN, 100));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton loginButton = new JButton();
        loginButton.setBounds(window.getWidth()/2 - 100, 550, 200, 100);

        //Creates the red banner at the top of the window for each screen.
        JPanel header = new JPanel();
        header.setBackground(new Color(0xe75959));
        header.setBounds(0, 0, window.getWidth(), 180);
        header.setLayout(new BorderLayout());

        header.add(title);
        window.add(header);
        window.add(loginButton);
        window.setVisible(true);
    }
}
