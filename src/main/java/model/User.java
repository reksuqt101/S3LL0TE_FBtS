package model;

/**
 * Immutable data model for a row in the users table.
 */
public record User(int id, String username, String password, String roles, String status) {

    public static final String ROLE_ADMIN = "Boat Owner / Administrator";
    public static final String ROLE_CAPTAIN = "Boat Captain";
    public static final String ROLE_CLERK = "Port Clerk";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACTIVE = "ACTIVE";
}
