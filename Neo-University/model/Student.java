package model;

public class Student extends User implements AccessRole {
    public Student(String username, String password, String name) {
        super(username, password, name);
    }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String getDashboardMessage() {
        return "Welcome, Student " + name + "!";
    }

    @Override
    public boolean hasAccess(String resource) {
        // Students have limited access
        return resource.equals("Library") || resource.equals("Cafeteria");
    }
} 