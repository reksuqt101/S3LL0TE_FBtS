package ui;

import dao.UserDao;
import model.User;

import java.util.Optional;
import java.util.Scanner;

/**
 * Main screen: Register, Login, Exit.
 */
public class MainMenu {

    private final Scanner scanner;
    private final UserDao userDao;

    public MainMenu(Scanner scanner, UserDao userDao) {
        this.scanner = scanner;
        this.userDao = userDao;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("=====================================");
            System.out.println("      FISHING BOAT TRIP SYSTEM");
            System.out.println("=====================================");
            System.out.println("[1] Register Account");
            System.out.println("[2] Login");
            System.out.println("[3] Exit");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private void register() {
        System.out.println();
        System.out.println("--- REGISTER ACCOUNT ---");

        boolean first = userDao.isEmpty();
        if (first) {
            System.out.println("No accounts exist yet. This first account will be the");
            System.out.println("Boat Owner / Administrator.");
        }

        String username = prompt("Username: ");
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        if (userDao.usernameExists(username)) {
            System.out.println("That username is already taken.");
            return;
        }

        String password = prompt("Password: ");
        if (password.length() < 4) {
            System.out.println("Password must be at least 4 characters.");
            return;
        }

        String role = first ? User.ROLE_ADMIN : chooseRole();
        if (role == null) {
            return;
        }

        Optional<User> saved = userDao.register(username, password, role);
        if (saved.isEmpty()) {
            System.out.println("Registration failed: username already taken.");
            return;
        }

        User user = saved.get();
        System.out.println("Account created for '" + user.username() + "' as " + user.roles() + ".");
        if (User.STATUS_PENDING.equals(user.status())) {
            System.out.println("Status: PENDING. Wait for the Administrator to activate your account.");
        } else {
            System.out.println("Status: " + user.status() + ". You can log in now.");
        }
    }

    private String chooseRole() {
        System.out.println("Select role:");
        System.out.println("[1] " + User.ROLE_CAPTAIN);
        System.out.println("[2] " + User.ROLE_CLERK);
        System.out.print("Choose: ");
        return switch (scanner.nextLine().trim()) {
            case "1" -> User.ROLE_CAPTAIN;
            case "2" -> User.ROLE_CLERK;
            default -> {
                System.out.println("Invalid role. Registration cancelled.");
                yield null;
            }
        };
    }

    private void login() {
        System.out.println();
        System.out.println("--- LOGIN ---");
        String username = prompt("Username: ");
        String password = prompt("Password: ");

        Optional<User> result = userDao.login(username, password);
        if (result.isEmpty()) {
            System.out.println("Invalid username or password.");
            return;
        }

        User user = result.get();
        if (User.STATUS_PENDING.equals(user.status())) {
            System.out.println("Your account is still PENDING approval by the Administrator.");
            return;
        }
        if (!User.STATUS_ACTIVE.equals(user.status())) {
            System.out.println("Your account is " + user.status() + ". Contact the Administrator.");
            return;
        }

        new DashboardMenu(scanner, user).show();
    }

    private String prompt(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }
}
