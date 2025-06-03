package backend;

import model.User;

public class AccessSimulator extends Thread {
    private User user;
    private String resource;

    public AccessSimulator(User user, String resource) {
        this.user = user;
        this.resource = resource;
    }

    @Override
    public void run() {
        System.out.println(user.getName() + " attempting to access " + resource + " as " + user.getRole());
        try {
            Thread.sleep(1000); // Simulate access delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (((model.AccessRole)user).hasAccess(resource)) {
            System.out.println(user.getName() + " ACCESS GRANTED to " + resource);
        } else {
            System.out.println(user.getName() + " ACCESS DENIED to " + resource);
        }
    }
} 