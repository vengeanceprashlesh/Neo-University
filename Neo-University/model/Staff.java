package model;

public class Staff extends User implements AccessRole {
    public Staff(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String getRole() { return "Staff"; }

    @Override
    public String getDashboardMessage() {
        return "Welcome, Staff " + name + "!";
    }

    @Override
    public boolean hasAccess(String resource) {
        // Staff have access to more resources
        return resource.equals("Library") || resource.equals("Cafeteria") || resource.equals("Lab");
    }
} 