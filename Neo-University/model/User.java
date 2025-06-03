package model;

public abstract class User {
    protected String username;
    protected String password;
    protected String name;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public boolean checkPassword(String password) { return this.password.equals(password); }

    public abstract String getRole();
    public abstract String getDashboardMessage();
} 