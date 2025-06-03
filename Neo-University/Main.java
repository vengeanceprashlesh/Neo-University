import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new frontend.LandingFrame();
        });
    }
} 