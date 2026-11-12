//Final update done by Oyama
package za.ac.cput.aquavoltsa.GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 * One small table (CUSTOMER_SETTINGS) keeps every setting of a customer as "key = value":
 * language, alert choices, notification channel, lock type, gender, date of birth ...
 * The table is created the first time it is needed.
 */
final class SettingsStore {
    private SettingsStore() { }

    private static void ensure(Connection c) throws SQLException {
        try (Statement st = c.createStatement()) {
            st.executeUpdate("CREATE TABLE customer_settings (customer_username VARCHAR(30) NOT NULL, "
                    + "setting_key VARCHAR(40) NOT NULL, setting_value VARCHAR(100), "
                    + "PRIMARY KEY (customer_username, setting_key))");
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) throw e;   // X0Y32 = table already exists
        }
    }

    static String get(String user, String key, String def) {
        if (user == null) return def;
        try (Connection c = DAO.connect()) {
            ensure(c);
            try (PreparedStatement p = c.prepareStatement(
                    "SELECT setting_value FROM customer_settings WHERE customer_username = ? AND setting_key = ?")) {
                p.setString(1, user);
                p.setString(2, key);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next() && r.getString(1) != null) return r.getString(1);
                }
            }
        } catch (SQLException e) {
            DAO.report(e);
        }
        return def;
    }

    static void set(String user, String key, String value) {
        if (user == null) return;
        try (Connection c = DAO.connect()) {
            ensure(c);
            int n;
            try (PreparedStatement p = c.prepareStatement(
                    "UPDATE customer_settings SET setting_value = ? WHERE customer_username = ? AND setting_key = ?")) {
                p.setString(1, value);
                p.setString(2, user);
                p.setString(3, key);
                n = p.executeUpdate();
            }
            if (n == 0) {   // Derby has no "ON DUPLICATE KEY UPDATE": update first, insert when nothing was updated
                try (PreparedStatement p = c.prepareStatement(
                        "INSERT INTO customer_settings (customer_username, setting_key, setting_value) VALUES (?, ?, ?)")) {
                    p.setString(1, user);
                    p.setString(2, key);
                    p.setString(3, value);
                    p.executeUpdate();
                }
            }
        } catch (SQLException e) {
            DAO.report(e);
        }
    }
}