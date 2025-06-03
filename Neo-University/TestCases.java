import backend.AccessSimulator;
import model.*;

public class TestCases {
    public static void main(String[] args) {
        User student = new Student("student1", "pass123", "Alice");
        User staff = new Staff("staff1", "pass456", "Bob");
        User admin = new Admin("admin1", "admin789", "Carol");

        // Simulate simultaneous access
        Thread t1 = new AccessSimulator(student, "Library");
        Thread t2 = new AccessSimulator(staff, "Lab");
        Thread t3 = new AccessSimulator(admin, "Admin Office");
        Thread t4 = new AccessSimulator(student, "Admin Office"); // Should be denied

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
} 