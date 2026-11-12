//Final update done by Sbahle and Oyama
package za.ac.cput.aquavoltsa.GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 * Notifications stored in the NOTIFICATIONS table. Admins send them, customers read them
 * on the NotificationPage.
 */
public final class NotificationStore {
    public static class Note {
        public int id;
        public String username = "", category = "", title = "", message = "", status = "", sentBy = "", created = "";

        public boolean unread() {
            return "UNREAD".equals(status);
        }
    }

    private static final String COLS = "notif_id, customer_username, category, title, message, status, sent_by, created";

    private NotificationStore() { }

    private static String s(String v) {
        return v == null ? "" : v;
    }

    private static String cut(String v, int max) {
        v = s(v);
        return v.length() > max ? v.substring(0, max) : v;
    }

    private static Note row(ResultSet r) throws SQLException {
        Note n = new Note();
        n.id = r.getInt("notif_id");
        n.username = s(r.getString("customer_username"));
        n.category = s(r.getString("category"));
        n.title = s(r.getString("title"));
        n.message = s(r.getString("message"));
        n.status = s(r.getString("status"));
        n.sentBy = s(r.getString("sent_by"));
        Timestamp t = r.getTimestamp("created");
        n.created = t == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t);
        return n;
    }

    /** category: WATER, ELECTRICITY, ACCOUNT, PAYMENT or SYSTEM. */
    public static boolean send(String username, String category, String title, String message, String sentBy) {
        String sql = "INSERT INTO notifications (customer_username, category, title, message, status, sent_by) "
                + "VALUES (?, ?, ?, ?, 'UNREAD', ?)";
        try (Connection c = DAO.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, cut(username, 30));
            p.setString(2, cut(category, 20));
            p.setString(3, cut(title, 80));
            p.setString(4, cut(message, 500));
            p.setString(5, cut(sentBy, 40));
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            DAO.report(e);
            return false;
        }
    }

    /** Sends the same notification to every customer; returns how many received it. */
    public static int sendToAll(String category, String title, String message, String sentBy) {
        int count = 0;
        for (UserStore.User u : UserStore.all()) {
            if (send(u.username, category, title, message, sentBy)) count++;
        }
        return count;
    }

    public static List<Note> forUser(String username) {
        List<Note> list = new ArrayList<>();
        String sql = "SELECT " + COLS + " FROM notifications WHERE LOWER(customer_username) = LOWER(?) "
                + "ORDER BY created DESC, notif_id DESC";
        try (Connection c = DAO.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) list.add(row(r));
            }
        } catch (SQLException e) {
            DAO.report(e);
        }
        return list;
    }

    public static int unread(String username) {
        if (username == null) return 0;
        String sql = "SELECT COUNT(*) FROM notifications WHERE LOWER(customer_username) = LOWER(?) AND status = 'UNREAD'";
        try (Connection c = DAO.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            try (ResultSet r = p.executeQuery()) {
                r.next();
                return r.getInt(1);
            }
        } catch (SQLException e) {
            DAO.report(e);
            return 0;
        }
    }

    public static void markRead(int id) {
        try (Connection c = DAO.connect();
             PreparedStatement p = c.prepareStatement("UPDATE notifications SET status = 'READ' WHERE notif_id = ?")) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            DAO.report(e);
        }
    }

    public static void markAllRead(String username) {
        try (Connection c = DAO.connect();
             PreparedStatement p = c.prepareStatement("UPDATE notifications SET status = 'READ' WHERE LOWER(customer_username) = LOWER(?)")) {
            p.setString(1, username);
            p.executeUpdate();
        } catch (SQLException e) {
            DAO.report(e);
        }
    }
}