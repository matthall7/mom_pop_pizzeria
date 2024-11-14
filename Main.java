import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame mainWindow = new Frame();
            mainWindow.setVisible(true);
        });
    }
}
