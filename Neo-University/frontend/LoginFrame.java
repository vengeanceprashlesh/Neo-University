package frontend;

import model.*;
import exception.InvalidLoginException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private String forcedRole = null;

    public LoginFrame() {
        this(null);
    }

    public LoginFrame(String role) {
        this.forcedRole = role;
        setTitle("Campus Access Control System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 340);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Campus Access Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setBounds(70, 20, 300, 40);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(44, 62, 80));
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userLabel.setBounds(60, 80, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 180, 30);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        usernameField.setBackground(new Color(245, 245, 250));
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(44, 62, 80));
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passLabel.setBounds(60, 130, 80, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 180, 30);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passwordField.setBackground(new Color(245, 245, 250));
        panel.add(passwordField);

        loginButton = new RoundedButton("Login");
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setBounds(150, 190, 180, 40);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(loginButton, new Color(31, 97, 141), new Color(41, 128, 185));
        panel.add(loginButton);

        messageLabel = new JLabel("");
        messageLabel.setForeground(new Color(231, 76, 60));
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setBounds(60, 250, 300, 25);
        panel.add(messageLabel);

        JButton backArrow = new RoundedButton("←");
        backArrow.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backArrow.setBounds(10, 10, 50, 35);
        backArrow.setBackground(new Color(236, 240, 241));
        backArrow.setForeground(new Color(41, 128, 185));
        backArrow.setFocusPainted(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backArrow.setToolTipText("Back to Role Selection");
        addHoverEffect(backArrow, new Color(200, 220, 240), new Color(236, 240, 241));
        panel.add(backArrow);
        backArrow.addActionListener(e -> {
            dispose();
            new LandingFrame();
        });

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        try {
            User user = authenticate(username, password);
            dispose();
            new DashboardFrame(user);
        } catch (InvalidLoginException ex) {
            messageLabel.setText(ex.getMessage());
        }
    }

    private User authenticate(String username, String password) throws InvalidLoginException {
        java.util.HashMap<String, model.User> userMap = frontend.DashboardFrame.userMap;
        if (!userMap.containsKey(username)) {
            throw new InvalidLoginException("Invalid username or password.");
        }
        User user = userMap.get(username);
        if (!user.checkPassword(password)) {
            throw new InvalidLoginException("Invalid username or password.");
        }
        if (forcedRole != null && !user.getRole().equalsIgnoreCase(forcedRole)) {
            throw new InvalidLoginException("Please login as a " + forcedRole + ".");
        }
        return user;
    }

    // Add hover effect to buttons
    private void addHoverEffect(final JButton button, final Color hover, final Color normal) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
    }

    // Custom rounded button
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g2);
            g2.dispose();
        }
        @Override
        public void setContentAreaFilled(boolean b) {}
    }

    // Custom gradient panel
    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            Color color1 = new Color(236, 240, 241);
            Color color2 = new Color(214, 234, 248);
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
} 