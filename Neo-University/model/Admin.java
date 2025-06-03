package model;

public class Admin extends User implements AccessRole {
    public Admin(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String getRole() { return "Admin"; }

    @Override
    public String getDashboardMessage() {
        return "Welcome, Admin " + name + "!";
    }

    @Override
    public boolean hasAccess(String resource) {
        // Admins have access to all resources
        return true;
    }
} 