package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LandingFrame extends JFrame {
    public LandingFrame() {
        setTitle("Welcome to Campus Access System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titleLabel = new JLabel("Select Your Role");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setBounds(150, 30, 300, 40);
        add(titleLabel);

        // Role buttons with icons and tooltips
        JButton studentBtn = createRoleButton("Student", "🎓", 60, 120);
        JButton staffBtn = createRoleButton("Staff", "🧑‍💼", 200, 120);
        JButton facultyBtn = createRoleButton("Faculty", "👩‍🏫", 340, 120);
        JButton adminBtn = createRoleButton("Admin", "🛡️", 130, 220);
        JButton guestBtn = createRoleButton("Guest", "👤", 270, 220);

        add(studentBtn);
        add(staffBtn);
        add(facultyBtn);
        add(adminBtn);
        add(guestBtn);

        studentBtn.addActionListener(e -> {
            dispose();
            new LoginFrame("Student");
        });
        staffBtn.addActionListener(e -> {
            dispose();
            new LoginFrame("Staff");
        });
        facultyBtn.addActionListener(e -> {
            dispose();
            new LoginFrame("Faculty");
        });
        adminBtn.addActionListener(e -> {
            dispose();
            new LoginFrame("Admin");
        });
        guestBtn.addActionListener(e -> {
            dispose();
            new GuestAccessFrame();
        });

        setVisible(true);
    }

    private JButton createRoleButton(String role, String icon, int x, int y) {
        JButton btn = new JButton(icon + "\n" + role);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBounds(x, y, 120, 70);
        btn.setBackground(new Color(236, 240, 241));
        btn.setForeground(new Color(44, 62, 80));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Login as " + role);
        return btn;
    }
} 