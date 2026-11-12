package za.ac.cput.aquavoltsa.DAO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;

/**
 * Student Number: 241028213
 *
 * @author Oyama Sibunzi
 *
*/

public class Database {

    private static final String MAIN_ADMIN = "admin12304";   // main administrator (password admin12304)
    private static boolean showing = false;

    // ====================================================================== connection
    /** Same connection as DAO.java: DBConnection.getConnection(). */
    public static Connection connect() throws SQLException {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            throw new SQLException("DBConnection could not connect to the database.");
        }
        return con;
    }

    // ====================================================================== create tables
    public static boolean createCustomersTable() {

        String SQL = """
                     CREATE TABLE CUSTOMERS(
                     ID VARCHAR(12) PRIMARY KEY,
                     FULL_NAME VARCHAR(100) NOT NULL,
                     ID_NUMBER VARCHAR(20) NOT NULL,
                     USERNAME VARCHAR(30) NOT NULL UNIQUE,
                     PASS_HASH VARCHAR(64) NOT NULL,
                     EMAIL VARCHAR(100) NOT NULL,
                     PHONE VARCHAR(20) NOT NULL,
                     ADDRESS VARCHAR(200),
                     METER VARCHAR(30),
                     SERIAL VARCHAR(30),
                     WATER_METER VARCHAR(30),
                     ACCOUNT_NO VARCHAR(30),
                     STATUS VARCHAR(10) NOT NULL DEFAULT 'PENDING',
                     CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                     )
                     """;

        return execute(SQL);
    }

    public static boolean createAdminsTable() {

        String SQL = """
                     CREATE TABLE ADMINS(
                     USERNAME VARCHAR(40) PRIMARY KEY,
                     FULL_NAME VARCHAR(100) NOT NULL,
                     EMAIL VARCHAR(100) NOT NULL,
                     PHONE VARCHAR(20) NOT NULL,
                     PASS_HASH VARCHAR(64) NOT NULL,
                     IS_MAIN CHAR(1) NOT NULL DEFAULT 'N',
                     STATUS VARCHAR(10) NOT NULL DEFAULT 'PENDING',
                     REQUESTED_BY VARCHAR(40),
                     CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                     )
                     """;

        return execute(SQL);
    }

    public static boolean createActivityLogTable() {

        String SQL = """
                     CREATE TABLE ACTIVITY_LOG(
                     LOG_ID INTEGER GENERATED ALWAYS AS IDENTITY(
                     START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     ADMIN_USERNAME VARCHAR(40) NOT NULL,
                     ACTIVITY VARCHAR(30) NOT NULL,
                     DETAILS VARCHAR(100),
                     CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                     )
                     """;

        return execute(SQL);
    }

    public static boolean createNotificationsTable() {

        String SQL = """
                     CREATE TABLE NOTIFICATIONS(
                     NOTIF_ID INTEGER GENERATED ALWAYS AS IDENTITY(
                     START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     CUSTOMER_USERNAME VARCHAR(30) NOT NULL,
                     CATEGORY VARCHAR(20) NOT NULL,
                     TITLE VARCHAR(80) NOT NULL,
                     MESSAGE VARCHAR(500) NOT NULL,
                     STATUS VARCHAR(10) NOT NULL DEFAULT 'UNREAD',
                     SENT_BY VARCHAR(40) NOT NULL,
                     CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                     )
                     """;

        return execute(SQL);
    }

    public static boolean createTransactionsTable() {

        String SQL = """
                     CREATE TABLE TRANSACTIONS(
                     TXN_ID VARCHAR(40) PRIMARY KEY,
                     CUSTOMER_USERNAME VARCHAR(30) NOT NULL,
                     UTILITY VARCHAR(12) NOT NULL,
                     METER_NO VARCHAR(30),
                     AMOUNT DECIMAL(10, 2) NOT NULL,
                     FEE DECIMAL(10, 2) NOT NULL,
                     METHOD VARCHAR(40),
                     STATUS VARCHAR(15) NOT NULL,
                     TOKEN VARCHAR(30),
                     CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                     )
                     """;

        return execute(SQL);
    }

    public static boolean createAppMetaTable() {

        String SQL = """
                     CREATE TABLE APP_META(
                     META_KEY VARCHAR(40) PRIMARY KEY,
                     META_VALUE VARCHAR(100)
                     )
                     """;

        return execute(SQL);
    }

    public static boolean createPendingCustomersView() {

        String SQL = """
                     CREATE VIEW PENDING_CUSTOMERS AS
                     SELECT ID, FULL_NAME, USERNAME, EMAIL, PHONE, CREATED
                     FROM CUSTOMERS
                     WHERE STATUS = 'PENDING'
                     """;

        return execute(SQL);
    }

    public static boolean createPaymentsView() {

        String SQL = """
                     CREATE VIEW ADMIN_PAYMENTS_VIEW AS
                     SELECT TXN_ID, CUSTOMER_USERNAME, UTILITY, AMOUNT, FEE, STATUS, CREATED
                     FROM TRANSACTIONS
                     """;

        return execute(SQL);
    }

    
    public static void addMainAdmin() {

        String SQL = "INSERT INTO ADMINS (USERNAME, FULL_NAME, EMAIL, PHONE, PASS_HASH, IS_MAIN, STATUS, REQUESTED_BY) "
                + "VALUES (?, ?, ?, ?, ?, 'Y', 'APPROVED', 'system')";

        try {
            Connection con = connect();
            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setString(1, MAIN_ADMIN);
            ps.setString(2, "Main Administrator");
            ps.setString(3, "admin@aquavolt.sa");
            ps.setString(4, "0210000000");
            ps.setString(5, sha256("admin12304"));

            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public static ResultSet getCustomers() {
        return query("SELECT * FROM CUSTOMERS ORDER BY CREATED DESC");
    }

    public static ResultSet getAdmins() {
        return query("SELECT * FROM ADMINS ORDER BY IS_MAIN DESC, CREATED");
    }

    public static ResultSet getNotifications() {
        return query("SELECT * FROM NOTIFICATIONS ORDER BY CREATED DESC");
    }

    public static ResultSet getTransactions() {
        return query("SELECT * FROM TRANSACTIONS ORDER BY CREATED DESC");
    }

    public static ResultSet getActivityLog() {
        return query("SELECT * FROM ACTIVITY_LOG ORDER BY CREATED DESC, LOG_ID DESC");
    }

    public static boolean init() {
        try {
            Connection test = connect();   // is the database reachable?
            test.close();

            List<String> created = new ArrayList<>();
            if (createCustomersTable()) created.add("CUSTOMERS");
            if (createAdminsTable()) created.add("ADMINS");
            if (createActivityLogTable()) created.add("ACTIVITY_LOG");
            if (createNotificationsTable()) created.add("NOTIFICATIONS");
            if (createTransactionsTable()) created.add("TRANSACTIONS");
            if (createAppMetaTable()) created.add("APP_META");

            String problems = findConflicts();
            if (!problems.isEmpty()) {
                JOptionPane.showMessageDialog(null, problems, "Table conflict", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (count("ADMINS") == 0) {
                addMainAdmin();
            }
            if (createPendingCustomersView()) created.add("PENDING_CUSTOMERS (view)");
            if (createPaymentsView()) created.add("ADMIN_PAYMENTS_VIEW (view)");

            if (!created.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Created in the database: " + String.join(", ", created));
            }
            return true;
        } catch (SQLException ex) {
            report(ex);
            return false;
        }
    }

    private static String findConflicts() {
        String[][] expected = {{"CUSTOMERS", "PASS_HASH"}, {"ADMINS", "IS_MAIN"}, {"ACTIVITY_LOG", "ADMIN_USERNAME"},
            {"NOTIFICATIONS", "CUSTOMER_USERNAME"}, {"TRANSACTIONS", "CUSTOMER_USERNAME"}};
        StringBuilder sb = new StringBuilder();
        for (String[] e : expected) {
            try {
                Connection con = connect();
                Statement st = con.createStatement();
                st.executeQuery("SELECT " + e[1] + " FROM " + e[0] + " WHERE 1 = 0").close();
                st.close();
                con.close();
            } catch (SQLException ex) {
                sb.append("The table ").append(e[0]).append(" already exists but has no column ").append(e[1])
                        .append(" - it was made by another page.\n");
            }
        }
        if (sb.length() > 0) {
            sb.append("\nRename or drop that table in NetBeans (Services > the database > Tables), then run the app again.");
        }
        return sb.toString();
    }

    public static int count(String table) {
        try {
            Connection con = connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table);
            rs.next();
            int n = rs.getInt(1);
            rs.close();
            st.close();
            con.close();
            return n;
        } catch (SQLException ex) {
            report(ex);
            return -1;
        }
    }

    public static String meta(String key) {
        try {
            Connection con = connect();
            PreparedStatement ps = con.prepareStatement("SELECT META_VALUE FROM APP_META WHERE META_KEY = ?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            String value = rs.next() ? rs.getString(1) : null;
            rs.close();
            ps.close();
            con.close();
            return value;
        } catch (SQLException ex) {
            report(ex);
            return null;
        }
    }

    public static void setMeta(String key, String value) {
        try {
            Connection con = connect();
            PreparedStatement update = con.prepareStatement("UPDATE APP_META SET META_VALUE = ? WHERE META_KEY = ?");
            update.setString(1, value);
            update.setString(2, key);
            int changed = update.executeUpdate();
            update.close();
            if (changed == 0) {
                PreparedStatement insert = con.prepareStatement("INSERT INTO APP_META (META_KEY, META_VALUE) VALUES (?, ?)");
                insert.setString(1, key);
                insert.setString(2, value);
                insert.executeUpdate();
                insert.close();
            }
            con.close();
        } catch (SQLException ex) {
            report(ex);
        }
    }

    private static boolean execute(String SQL) {
        try {
            Connection con = connect();
            PreparedStatement ps = con.prepareStatement(SQL);

            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            if (!"X0Y32".equals(ex.getSQLState())) {   // X0Y32 = already exists (fine)
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            return false;
        }
    }

    private static ResultSet query(String SQL) {
        try {
            Connection con = connect();
            PreparedStatement ps = con.prepareStatement(SQL);

            return ps.executeQuery();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    private static String sha256(String s) {
        try {
            byte[] d = MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return s;
        }
    }

    // ====================================================================== see the database from code
    public static void showAll() {
        try {
            Connection con = connect();
            List<String> tables = new ArrayList<>();
            ResultSet t = con.getMetaData().getTables(null, con.getSchema(), "%", new String[]{"TABLE"});
            while (t.next()) tables.add(t.getString("TABLE_NAME"));
            t.close();

            System.out.println("===== DATABASE (schema " + con.getSchema() + ") =====");
            for (String name : tables) {
                Statement st = con.createStatement();
                ResultSet r = st.executeQuery("SELECT * FROM " + name);
                ResultSetMetaData m = r.getMetaData();
                int cols = m.getColumnCount();
                StringBuilder head = new StringBuilder();
                for (int i = 1; i <= cols; i++) head.append(i > 1 ? " | " : "").append(m.getColumnName(i));
                List<String> rows = new ArrayList<>();
                while (r.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= cols; i++) {
                        String v = m.getColumnName(i).toUpperCase().contains("PASS") ? "********" : r.getString(i);
                        if (v == null) v = "";
                        if (v.length() > 24) v = v.substring(0, 24) + "...";
                        row.append(i > 1 ? " | " : "").append(v);
                    }
                    rows.add(row.toString());
                }
                r.close();
                st.close();
                System.out.println();
                System.out.println("--- " + name + "  (" + rows.size() + " rows) ---");
                System.out.println(head);
                for (String row : rows) System.out.println(row);
            }
            System.out.println();
            con.close();
        } catch (SQLException ex) {
            report(ex);
        }
    }

    public static void main(String[] args) {
        showAll();
        System.exit(0);
    }

    public static void report(SQLException e) {
        e.printStackTrace();
        if (showing) return;
        showing = true;
        JOptionPane.showMessageDialog(null,
                "Cannot reach the AquaVolt database.\n\n" + e.getMessage() + "\n\nCheck that:\n"
                + "1. The database used by DBConnection is available (if it is on the Java DB server, start it:\n"
                + "    Services > Databases > Java DB > Start Server)\n"
                + "2. No other program is holding the database open",
                "Database problem", JOptionPane.ERROR_MESSAGE);
        showing = false;
    }
}