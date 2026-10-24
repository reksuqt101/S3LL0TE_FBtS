package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Opens SQLite connections and creates the schema on first run.
 */
public class DatabaseConnection {

    private static final String DEFAULT_URL = "jdbc:sqlite:fbts.db";

    private static String url = DEFAULT_URL;

    private DatabaseConnection() {
    }

    /** Overrides the database location (used for testing). */
    public static void setUrl(String newUrl) {
        url = newUrl;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /** Creates the users table if it does not exist yet. */
    public static void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id       INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    roles    TEXT NOT NULL,
                    status   TEXT NOT NULL DEFAULT 'PENDING'
                )
                """;
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not initialize database: " + e.getMessage(), e);
        }
    }
}
