package frontend;

import model.User;
import model.Admin;
import model.Student;
import model.Staff;
import model.AccessRole;
import model.Faculty;
import model.Guest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFrame extends JFrame {
    public static ArrayList<User> userList = new ArrayList<>();
    public static HashMap<String, User> userMap = new HashMap<>();
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private User currentUser;

    static {
        // Demo users
        User s = new Student("student1", "pass123", "Alice");
        User st = new Staff("staff1", "pass456", "Bob");
        User a = new Admin("admin1", "admin789", "Carol");
        User f = new Faculty("faculty1", "fac123", "Dr. Smith");
        User g = new Guest("guest1", "guest", "Visitor");
        userList.add(s);
        userList.add(st);
        userList.add(a);
        userList.add(f);
        userList.add(g);
        userMap.put(s.getUsername(), s);
        userMap.put(st.getUsername(), st);
        userMap.put(a.getUsername(), a);
        userMap.put(f.getUsername(), f);
        userMap.put(g.getUsername(), g);
    }

    // Shared entry log and stats
    public static class EntryLog {
        static java.util.List<LogEntry> log = new java.util.ArrayList<>();
        static java.util.Set<String> inside = new java.util.HashSet<>();
        static int todayEntries = 0;
        static String today = new SimpleDateFormat("yyyyMMdd").format(new Date());

        static void enter(User user) {
            String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
            log.add(0, new LogEntry(user, "ENTER", now));
            inside.add(user.getUsername());
            String todayNow = new SimpleDateFormat("yyyyMMdd").format(new Date());
            if (!todayNow.equals(today)) { today = todayNow; todayEntries = 0; }
            todayEntries++;
        }
        static void exit(User user) {
            String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
            log.add(0, new LogEntry(user, "EXIT", now));
            inside.remove(user.getUsername());
        }
        static int countRole(String role) {
            int c = 0;
            for (String u : inside) {
                User user = DashboardFrame.userMap.get(u);
                if (user != null && user.getRole().equalsIgnoreCase(role)) c++;
            }
            return c;
        }
    }
    public static class LogEntry {
        User user;
        String action;
        String time;
        LogEntry(User user, String action, String time) {
            this.user = user; this.action = action; this.time = time;
        }
    }

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle("Dashboard - " + user.getRole());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = createDashboardPanel();
        JPanel addUserPanel = createAddUserPanel();
        JPanel viewUsersPanel = createViewUsersPanel();

        mainPanel.add(dashboardPanel, "dashboard");
        mainPanel.add(addUserPanel, "addUser");
        mainPanel.add(viewUsersPanel, "viewUsers");

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 241));
        panel.setLayout(null);

        // Personalized greeting
        JLabel welcomeLabel = new JLabel(getGreeting() + ", " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(41, 128, 185));
        welcomeLabel.setBounds(60, 20, 400, 30);
        panel.add(welcomeLabel);

        // Avatar
        JLabel avatar = new JLabel(getAvatar(currentUser), SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 32));
        avatar.setOpaque(true);
        avatar.setBackground(new Color(214, 234, 248));
        avatar.setForeground(new Color(41, 128, 185));
        avatar.setBounds(480, 20, 60, 60);
        avatar.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2, true));
        panel.add(avatar);

        JLabel roleLabel = new JLabel("Role: " + badge(currentUser.getRole()));
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roleLabel.setForeground(new Color(44, 62, 80));
        roleLabel.setBounds(60, 60, 300, 25);
        panel.add(roleLabel);

        // Statistics panel (Admin only, but can show for all for demo)
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 2, 10, 5));
        statsPanel.setBackground(new Color(223, 230, 233));
        statsPanel.setBounds(60, 100, 300, 60);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statsPanel.add(new JLabel("Students inside: " + EntryLog.countRole("Student")));
        statsPanel.add(new JLabel("Faculty inside: " + EntryLog.countRole("Faculty")));
        statsPanel.add(new JLabel("Guests inside: " + EntryLog.countRole("Guest")));
        statsPanel.add(new JLabel("Today's entries: " + EntryLog.todayEntries));
        panel.add(statsPanel);

        // Entry/Exit simulation (not for Admin)
        if (!(currentUser instanceof Admin)) {
            JButton enterBtn = new JButton("Enter Campus");
            enterBtn.setBackground(new Color(39, 174, 96));
            enterBtn.setForeground(Color.WHITE);
            enterBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            enterBtn.setBounds(400, 100, 150, 35);
            enterBtn.setFocusPainted(false);
            enterBtn.setBorderPainted(false);
            enterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            enterBtn.setToolTipText("Simulate entry");
            panel.add(enterBtn);
            JButton exitBtn = new JButton("Exit Campus");
            exitBtn.setBackground(new Color(231, 76, 60));
            exitBtn.setForeground(Color.WHITE);
            exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            exitBtn.setBounds(570, 100, 150, 35);
            exitBtn.setFocusPainted(false);
            exitBtn.setBorderPainted(false);
            exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            exitBtn.setToolTipText("Simulate exit");
            panel.add(exitBtn);
            enterBtn.addActionListener(e -> { EntryLog.enter(currentUser); refreshLog(panel); });
            exitBtn.addActionListener(e -> { EntryLog.exit(currentUser); refreshLog(panel); });
        }

        // Live entry log
        JLabel logLabel = new JLabel("Live Entry Log");
        logLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logLabel.setForeground(new Color(39, 174, 96));
        logLabel.setBounds(60, 180, 200, 25);
        panel.add(logLabel);
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logArea.setBackground(new Color(245, 245, 250));
        logArea.setBounds(60, 210, 660, 120);
        logArea.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 1, true));
        panel.add(logArea);
        refreshLog(panel);

        // Role-based dashboard enhancements
        if (currentUser instanceof Faculty) {
            JLabel scheduleLabel = new JLabel("Today's Classes: 10:00 - 12:00 (Room 101), 14:00 - 16:00 (Lab)");
            scheduleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 15));
            scheduleLabel.setForeground(new Color(44, 62, 80));
            scheduleLabel.setBounds(60, 350, 500, 25);
            panel.add(scheduleLabel);
        } else if (currentUser instanceof Student) {
            JLabel eventsLabel = new JLabel("Upcoming: Math Exam (Tomorrow), Sports Meet (Friday)");
            eventsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 15));
            eventsLabel.setForeground(new Color(44, 62, 80));
            eventsLabel.setBounds(60, 350, 500, 25);
            panel.add(eventsLabel);
        } else if (currentUser instanceof Guest) {
            JLabel guestPass = new JLabel("Guest Pass: " + getGuestPass(currentUser));
            guestPass.setFont(new Font("Segoe UI", Font.BOLD, 15));
            guestPass.setForeground(new Color(41, 128, 185));
            guestPass.setBounds(60, 350, 400, 25);
            panel.add(guestPass);
        }

        // Admin: show user management buttons (already present)
        if (currentUser instanceof Admin) {
            JPanel adminBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
            adminBtnPanel.setOpaque(false);
            adminBtnPanel.setBounds(400, 160, 340, 50);

            JButton addUserButton = new JButton("Add User");
            addUserButton.setBackground(new Color(41, 128, 185));
            addUserButton.setForeground(Color.WHITE);
            addUserButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
            addUserButton.setPreferredSize(new Dimension(140, 40));
            addUserButton.setFocusPainted(false);
            addUserButton.setBorderPainted(false);
            addUserButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            addUserButton.setToolTipText("Add a new user");
            adminBtnPanel.add(addUserButton);

            JButton viewUsersButton = new JButton("View Users");
            viewUsersButton.setBackground(new Color(39, 174, 96));
            viewUsersButton.setForeground(Color.WHITE);
            viewUsersButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
            viewUsersButton.setPreferredSize(new Dimension(140, 40));
            viewUsersButton.setFocusPainted(false);
            viewUsersButton.setBorderPainted(false);
            viewUsersButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewUsersButton.setToolTipText("View all users");
            adminBtnPanel.add(viewUsersButton);

            addUserButton.addActionListener(e -> cardLayout.show(mainPanel, "addUser"));
            viewUsersButton.addActionListener(e -> {
                refreshUserTable();
                cardLayout.show(mainPanel, "viewUsers");
            });

            panel.add(adminBtnPanel);
        }

        JButton backArrow = new JButton("←");
        backArrow.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backArrow.setBounds(10, 10, 50, 35);
        backArrow.setBackground(new Color(236, 240, 241));
        backArrow.setForeground(new Color(41, 128, 185));
        backArrow.setFocusPainted(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(backArrow);
        backArrow.setToolTipText("Back to Role Selection");
        backArrow.addActionListener(e -> {
            dispose();
            new LandingFrame();
        });
        return panel;
    }

    private JPanel createAddUserPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 241));
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Add New User");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setBounds(200, 30, 200, 30);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(120, 90, 80, 25);
        panel.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(200, 90, 200, 25);
        panel.add(nameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(120, 130, 80, 25);
        panel.add(usernameLabel);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 130, 200, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(120, 170, 80, 25);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 170, 200, 25);
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(120, 210, 80, 25);
        panel.add(roleLabel);
        String[] roles = {"Student", "Staff", "Admin"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(200, 210, 200, 25);
        panel.add(roleCombo);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(39, 174, 96));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setBounds(200, 260, 90, 35);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(addButton);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(231, 76, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backButton.setBounds(310, 260, 90, 35);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(backButton);

        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(new Color(231, 76, 60));
        messageLabel.setBounds(120, 310, 300, 25);
        panel.add(messageLabel);

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = (String) roleCombo.getSelectedItem();
            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("All fields are required.");
                return;
            }
            if (userMap.containsKey(username)) {
                messageLabel.setText("Username already exists.");
                return;
            }
            User newUser;
            if (role.equals("Student")) newUser = new Student(username, password, name);
            else if (role.equals("Staff")) newUser = new Staff(username, password, name);
            else newUser = new Admin(username, password, name);
            userList.add(newUser);
            userMap.put(username, newUser);
            messageLabel.setForeground(new Color(39, 174, 96));
            messageLabel.setText("User added successfully!");
            nameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        return panel;
    }

    private JTable userTable;
    private JScrollPane userScrollPane;

    private JPanel createViewUsersPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 241));
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("All Users");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setBounds(220, 30, 200, 30);
        panel.add(titleLabel);

        userTable = new JTable();
        userScrollPane = new JScrollPane(userTable);
        userScrollPane.setBounds(60, 80, 480, 250);
        panel.add(userScrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(231, 76, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backButton.setBounds(450, 350, 100, 35);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(backButton);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        return panel;
    }

    private void refreshUserTable() {
        String[] columns = {"Name", "Username", "Role"};
        String[][] data = new String[userList.size()][3];
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            data[i][0] = u.getName();
            data[i][1] = u.getUsername();
            data[i][2] = u.getRole();
        }
        userTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    private void refreshLog(JPanel panel) {
        JTextArea logArea = null;
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextArea) logArea = (JTextArea) c;
        }
        if (logArea == null) return;
        StringBuilder sb = new StringBuilder();
        for (LogEntry entry : EntryLog.log) {
            sb.append(entry.time).append("  ")
              .append(getAvatar(entry.user)).append(" ")
              .append(entry.user.getName()).append(" [").append(badge(entry.user.getRole())).append("] ")
              .append(entry.action).append("\n");
        }
        logArea.setText(sb.toString());
    }

    private String getGreeting() {
        int hour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
        if (hour < 12) return "Good morning";
        if (hour < 18) return "Good afternoon";
        return "Good evening";
    }
    private String getAvatar(User user) {
        String role = user.getRole().toLowerCase();
        if (role.contains("admin")) return "🛡️";
        if (role.contains("faculty")) return "👩‍🏫";
        if (role.contains("staff")) return "🧑‍💼";
        if (role.contains("student")) return "🎓";
        if (role.contains("guest")) return "👤";
        return "👤";
    }
    private String badge(String role) {
        if (role.equalsIgnoreCase("Admin")) return "Admin";
        if (role.equalsIgnoreCase("Faculty")) return "Faculty";
        if (role.equalsIgnoreCase("Staff")) return "Staff";
        if (role.equalsIgnoreCase("Student")) return "Student";
        if (role.equalsIgnoreCase("Guest")) return "Guest";
        return role;
    }
    private String getGuestPass(User user) {
        return Integer.toHexString(user.getUsername().hashCode()).toUpperCase();
    }
} 