package frontend;

import model.Guest;
import model.User;

import javax.swing.*;
import java.awt.*;

public class GuestAccessFrame extends JFrame {
    public GuestAccessFrame() {
        setTitle("Guest Access");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titleLabel = new JLabel("Guest Access");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setBounds(120, 20, 200, 30);
        add(titleLabel);

        JLabel nameLabel = new JLabel("Your Name:");
        nameLabel.setBounds(60, 80, 80, 25);
        add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 80, 180, 25);
        add(nameField);

        JButton accessButton = new JButton("Enter as Guest");
        accessButton.setBackground(new Color(39, 174, 96));
        accessButton.setForeground(Color.WHITE);
        accessButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        accessButton.setBounds(120, 130, 160, 35);
        accessButton.setFocusPainted(false);
        accessButton.setBorderPainted(false);
        accessButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(accessButton);

        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(new Color(231, 76, 60));
        messageLabel.setBounds(60, 180, 300, 25);
        add(messageLabel);

        JButton backArrow = new JButton("←");
        backArrow.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backArrow.setBounds(10, 10, 50, 35);
        backArrow.setBackground(new Color(236, 240, 241));
        backArrow.setForeground(new Color(41, 128, 185));
        backArrow.setFocusPainted(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(backArrow);
        backArrow.setToolTipText("Back to Role Selection");
        backArrow.addActionListener(e -> {
            dispose();
            new LandingFrame();
        });

        accessButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                messageLabel.setText("Please enter your name.");
                return;
            }
            User guest = new Guest("guest_" + System.currentTimeMillis(), "", name);
            dispose();
            new DashboardFrame(guest);
        });

        setVisible(true);
    }
} 