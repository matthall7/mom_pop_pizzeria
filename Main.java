import javax.swing.*;

/**
 * Main --- instantiates the Frame which show the project and makes it visible
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame mainWindow = new Frame();
            mainWindow.setVisible(true);
        });
    }
}
