package model;

public class Faculty extends User implements AccessRole {
    public Faculty(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String getRole() { return "Faculty"; }

    @Override
    public String getDashboardMessage() {
        return "Welcome, Faculty " + name + "!";
    }

    @Override
    public boolean hasAccess(String resource) {
        return resource.equals("Library") || resource.equals("Cafeteria") || resource.equals("Lab") || resource.equals("Faculty Lounge");
    }
} 