package za.ac.cput.aquavoltsa.GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.aquavoltsa.DAO.Database;

/**
 * Data access for the ADMINS and ACTIVITY_LOG tables.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public final class AdminStore {
    public static final String MAIN = "admin12304";
    public static final String PENDING = "PENDING", APPROVED = "APPROVED";

    public static class Admin {
        public String username = "", fullName = "", email = "", phone = "", passHash = "", status = PENDING,
                requestedBy = "", created = "";
        public boolean main;
    }

    private static final String COLS = "username, full_name, email, phone, pass_hash, is_main, status, requested_by, created";

    private AdminStore() { }

    private static String s(String v) {
        return v == null ? "" : v;
    }

    private static String time(Timestamp t) {
        return t == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t);
    }

    private static Admin row(ResultSet r) throws SQLException {
        Admin a = new Admin();
        a.username = s(r.getString("username"));
        a.fullName = s(r.getString("full_name"));
        a.email = s(r.getString("email"));
        a.phone = s(r.getString("phone"));
        a.passHash = s(r.getString("pass_hash"));
        a.main = "Y".equals(r.getString("is_main"));
        a.status = s(r.getString("status"));
        a.requestedBy = s(r.getString("requested_by"));
        a.created = time(r.getTimestamp("created"));
        return a;
    }

    public static Admin find(String username) {
        try (Connection c = Database.connect();
             PreparedStatement p = c.prepareStatement("SELECT " + COLS + " FROM admins WHERE LOWER(username) = LOWER(?)")) {
            p.setString(1, username);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return row(r);
            }
        } catch (SQLException e) {
            Database.report(e);
        }
        return null;
    }

    public static List<Admin> all() {
        List<Admin> list = new ArrayList<>();
        try (Connection c = Database.connect(); Statement st = c.createStatement();
             ResultSet r = st.executeQuery("SELECT " + COLS + " FROM admins ORDER BY is_main DESC, created")) {
            while (r.next()) list.add(row(r));
        } catch (SQLException e) {
            Database.report(e);
        }
        return list;
    }

    /** A new admin always starts as PENDING until the main admin accepts it. */
    public static boolean request(Admin a) {
        String sql = "INSERT INTO admins (username, full_name, email, phone, pass_hash, is_main, status, requested_by) "
                + "VALUES (?, ?, ?, ?, ?, 'N', 'PENDING', ?)";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, a.username);
            p.setString(2, a.fullName);
            p.setString(3, a.email);
            p.setString(4, a.phone);
            p.setString(5, a.passHash);
            p.setString(6, a.requestedBy);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            Database.report(e);
            return false;
        }
    }

    public static boolean setStatus(String username, String status) {
        try (Connection c = Database.connect();
             PreparedStatement p = c.prepareStatement("UPDATE admins SET status = ? WHERE username = ?")) {
            p.setString(1, status);
            p.setString(2, username);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            Database.report(e);
            return false;
        }
    }

    /** The main admin (is_main = 'Y') can never be removed. */
    public static boolean remove(String username) {
        try (Connection c = Database.connect();
             PreparedStatement p = c.prepareStatement("DELETE FROM admins WHERE username = ? AND is_main = 'N'")) {
            p.setString(1, username);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            Database.report(e);
            return false;
        }
    }

    public static void log(String admin, String activity, String details) {
        try (Connection c = Database.connect();
             PreparedStatement p = c.prepareStatement("INSERT INTO activity_log (admin_username, activity, details) VALUES (?, ?, ?)")) {
            p.setString(1, admin);
            p.setString(2, activity);
            p.setString(3, details == null ? "" : (details.length() > 100 ? details.substring(0, 100) : details));
            p.executeUpdate();
        } catch (SQLException e) {
            Database.report(e);
        }
    }

    /** Newest 200 entries: {time, admin, activity, details}. */
    public static List<String[]> logs() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT created, admin_username, activity, details FROM activity_log "
                + "ORDER BY created DESC, log_id DESC FETCH FIRST 200 ROWS ONLY";
        try (Connection c = Database.connect(); Statement st = c.createStatement(); ResultSet r = st.executeQuery(sql)) {
            while (r.next()) {
                list.add(new String[]{time(r.getTimestamp(1)), s(r.getString(2)), s(r.getString(3)), s(r.getString(4))});
            }
        } catch (SQLException e) {
            Database.report(e);
        }
        return list;
    }
}