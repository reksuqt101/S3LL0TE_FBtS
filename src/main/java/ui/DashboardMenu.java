package ui;

import model.User;

import java.util.Scanner;

/**
 * Screen shown after a successful login. Logging out ends the session
 * and returns to the main menu.
 */
public class DashboardMenu {

    private final Scanner scanner;
    private final User user;

    public DashboardMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }

    public void show() {
        System.out.println();
        System.out.println("Welcome, " + user.username() + "! (" + user.roles() + ")");

        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("--- DASHBOARD ---");
            System.out.println("[1] View my profile");
            System.out.println("[2] Logout");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    System.out.println("ID       : " + user.id());
                    System.out.println("Username : " + user.username());
                    System.out.println("Role     : " + user.roles());
                    System.out.println("Status   : " + user.status());
                }
                case "2" -> {
                    System.out.println("Logged out. Returning to main menu...");
                    loggedIn = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }
}
