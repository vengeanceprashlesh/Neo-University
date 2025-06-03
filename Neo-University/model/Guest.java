package model;

public class Guest extends User implements AccessRole {
    public Guest(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String getRole() { return "Guest"; }

    @Override
    public String getDashboardMessage() {
        return "Welcome, Guest " + name + "!";
    }

    @Override
    public boolean hasAccess(String resource) {
        return resource.equals("Library");
    }
} 