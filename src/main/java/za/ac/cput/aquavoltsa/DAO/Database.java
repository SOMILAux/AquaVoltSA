package za.ac.cput.aquavoltsa.DAO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JOptionPane;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;

/**
 * Student Number: 241028213
 *
 * @author Oyama Sibunzi
 *
 * WHAT WAS CHANGED IN THIS VERSION (search for "FIX" to see each place):
 *  FIX 1  connect()  - if DBConnection returns null, we now try the database directly and show the REAL error
 *                      (before, you only saw "DBConnection could not connect" with no reason).
 *  FIX 2  query()    - the old version never closed the connection, so connections piled up. It now copies the
 *                      rows into a CachedRowSet and closes everything. Your other code does NOT change:
 *                      you still use rs.next() and rs.getString("COLUMN").
 *  FIX 3  every method now uses try-with-resources, so connections are always closed, even after an error.
 */
public class Database {

    private static final String MAIN_ADMIN = "admin12304";   // main administrator (password admin12304)
    private static boolean showing = false;

    // ---- FIX 1: used only if DBConnection fails. These match the connection shown in NetBeans Services. ----
    // If your database password is not "app", change DB_PASS (Services > right-click the connection > Properties).
    private static final String DB_URL = "jdbc:derby://localhost:1527/AquaVoltDB";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "app";

    public static Connection connect() throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
        } catch (Exception ignore) {
            // fall through to the direct connection below
        }
        if (con != null) {
            return con;
        }
        // DBConnection gave us nothing, so try directly. If this also fails, the message below is the REAL reason.
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException ex) {
            throw new SQLException("Could not connect to " + DB_URL + " as '" + DB_USER + "'.\nReason: "
                    + ex.getMessage() + "\n(DBConnection also returned no connection - check DBConnection.java)", ex);
        }
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
                        //ACCOUNT_NO check out
        
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

    // ====================================================================== create views (like DAO.createWaterView)
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

    // ====================================================================== add rows
    /** The main administrator is always present, so the admin side can never be locked out. */
    public static void addMainAdmin() {

        String SQL = "INSERT INTO ADMINS (USERNAME, FULL_NAME, EMAIL, PHONE, PASS_HASH, IS_MAIN, STATUS, REQUESTED_BY) "
                + "VALUES (?, ?, ?, ?, ?, 'Y', 'APPROVED', 'system')";

        // FIX 3: try-with-resources closes the connection and statement for us
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setString(1, MAIN_ADMIN);
            ps.setString(2, "Main Administrator");
            ps.setString(3, "admin@aquavolt.sa");
            ps.setString(4, "0210000000");
            ps.setString(5, sha256("admin12304"));

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    // ====================================================================== get rows (return a ResultSet, like DAO)
    // The caller reads the rows with rs.next() and rs.getString("COLUMN").

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

    // ====================================================================== start-up
    /** Called while the loading page shows: makes any missing table / view and checks they match this code. */
    public static boolean init() {
        try {
            connect().close();   // is the database reachable? (connect() now shows the real reason if not)

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

    // A table with the same name may already exist from another page of the app (for example TRANSACTIONS or
    // NOTIFICATIONS) with different columns. This finds that, so the problem is clear instead of a strange error.
    private static String findConflicts() {
        String[][] expected = {{"CUSTOMERS", "PASS_HASH"}, {"ADMINS", "IS_MAIN"}, {"ACTIVITY_LOG", "ADMIN_USERNAME"},
            {"NOTIFICATIONS", "CUSTOMER_USERNAME"}, {"TRANSACTIONS", "CUSTOMER_USERNAME"}};
        StringBuilder sb = new StringBuilder();
        for (String[] e : expected) {
            try (Connection con = connect(); Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT " + e[1] + " FROM " + e[0] + " WHERE 1 = 0")) {
                // the column exists - nothing to report
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

    // ====================================================================== small helpers
    /** Number of rows in a table (what NetBeans shows under View Data). Returns -1 if it cannot be read. */
    public static int count(String table) {
        try (Connection con = connect(); Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table)) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            report(ex);
            return -1;
        }
    }

    /** A small remembered value, for example "demo data was already added". */
    public static String meta(String key) {
        try (Connection con = connect();
                PreparedStatement ps = con.prepareStatement("SELECT META_VALUE FROM APP_META WHERE META_KEY = ?")) {
            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        } catch (SQLException ex) {
            report(ex);
            return null;
        }
    }

    public static void setMeta(String key, String value) {
        try (Connection con = connect()) {
            int changed;
            try (PreparedStatement update = con.prepareStatement("UPDATE APP_META SET META_VALUE = ? WHERE META_KEY = ?")) {
                update.setString(1, value);
                update.setString(2, key);
                changed = update.executeUpdate();
            }
            if (changed == 0) {
                try (PreparedStatement insert = con.prepareStatement("INSERT INTO APP_META (META_KEY, META_VALUE) VALUES (?, ?)")) {
                    insert.setString(1, key);
                    insert.setString(2, value);
                    insert.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            report(ex);
        }
    }

    // runs one CREATE statement: true = created now, false = it was already there
    private static boolean execute(String SQL) {
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            if (!"X0Y32".equals(ex.getSQLState())) {   // X0Y32 = already exists (fine)
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            return false;
        }
    }

    // FIX 2: the rows are copied into a CachedRowSet, then the real connection is closed straight away.
    // The caller uses it exactly like before: rs.next(), rs.getString("COLUMN"), rs.close().
    private static ResultSet query(String SQL) {
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL); ResultSet rs = ps.executeQuery()) {
            CachedRowSet rows = RowSetProvider.newFactory().createCachedRowSet();
            rows.populate(rs);
            return rows;
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
    /** Prints EVERY table of the database (this code's tables and DAO's tables) to the Output window. */
    public static void showAll() {
        try (Connection con = connect()) {
            List<String> tables = new ArrayList<>();
            try (ResultSet t = con.getMetaData().getTables(null, con.getSchema(), "%", new String[]{"TABLE"})) {
                while (t.next()) tables.add(t.getString("TABLE_NAME"));
            }

            System.out.println("===== DATABASE (schema " + con.getSchema() + ") =====");
            for (String name : tables) {
                try (Statement st = con.createStatement(); ResultSet r = st.executeQuery("SELECT * FROM " + name)) {
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
                    System.out.println();
                    System.out.println("--- " + name + "  (" + rows.size() + " rows) ---");
                    System.out.println(head);
                    for (String row : rows) System.out.println(row);
                }
            }
            System.out.println();
        } catch (SQLException ex) {
            report(ex);
        }
    }

    /** Run this file (Shift+F6) to see the whole database in the Output window. */
    public static void main(String[] args) {
        showAll();
        System.exit(0);
    }

    /** One clear message when the database cannot be reached. */
    public static void report(SQLException e) {
        e.printStackTrace();
        if (showing) return;
        showing = true;
        JOptionPane.showMessageDialog(null,
                "Cannot reach the AquaVolt database.\n\n" + e.getMessage() + "\n\nCheck that:\n"
                + "1. The database server is started:\n"
                + "    Services > Databases > Java DB > Start Server\n"
                + "2. The user name and password are right (Services > right-click the connection > Properties)\n"
                + "3. No other program is holding the database open",
                "Database problem", JOptionPane.ERROR_MESSAGE);
        showing = false;
    }
}