//Final update done by Oyama, Sbahle and Aphelele
package za.ac.cput.aquavoltsa.DAO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JOptionPane;
import za.ac.cput.aquavoltsa.Domain.ElectricityToken;
import za.ac.cput.aquavoltsa.Domain.Payment;
import za.ac.cput.aquavoltsa.Domain.Transaction;

/**
 * THE single source of truth for the AquaVolt SA database.
 * This file contains EVERYTHING: connections, tables, views, seeds, and CRUD.
 */
public class DAO {

    private static final String MAIN_ADMIN = "admin12304";
    private static boolean showing = false;

    // Database connection details
    private static final String DB_URL  = "jdbc:derby://localhost:1527/AquaVoltDB;create=true";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "app";

    // ================================================================== connect
    /** Returns a connection to the shared AquaVoltDB database. */
    public static Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException ex) {
            throw new SQLException("Could not connect to " + DB_URL + " as '" + DB_USER + "'.\nReason: "
                    + ex.getMessage()
                    + "\n\nMake sure the Derby Network Server is running:\n"
                    + "   NetBeans ▸ Services ▸ Databases ▸ Java DB ▸ Start Server", ex);
        }
    }

    // ================================================================== Aphelele's tables
    public static boolean createUtilityTable() {
        String SQL = """
                     CREATE TABLE UTILITY(
                     UTILITY_ID INTEGER PRIMARY KEY,
                     UTILITY_NAME VARCHAR(25) NOT NULL
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createPaymentTable() {
        String SQL = """
                     CREATE TABLE PAYMENT(
                     PAYMENT_ID INTEGER GENERATED ALWAYS AS IDENTITY(
                     START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     USER_ID INTEGER,
                     UTILITY_ID INTEGER,
                     AMOUNT DECIMAL(10, 2),
                     SERVICE_FEE DECIMAL(10, 2),
                     PAYMENT_METHOD VARCHAR(50),
                     PAYMENT_DATE DATE,
                     PAYMENT_TIME TIME,
                     PAYMENT_STATUS VARCHAR(100),
                     FOREIGN KEY (UTILITY_ID)
                     REFERENCES UTILITY(UTILITY_ID)
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createTransactionTable() {
        String SQL = """
                     CREATE TABLE PAYMENT_TRANSACTIONS(
                     TRANSACTION_ID VARCHAR(100) PRIMARY KEY,
                     PAYMENT_ID INTEGER,
                     UTILITY_ID INTEGER,
                     FOREIGN KEY(PAYMENT_ID)
                     REFERENCES PAYMENT(PAYMENT_ID),
                     FOREIGN KEY(UTILITY_ID)
                     REFERENCES UTILITY(UTILITY_ID)
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createElectricityTokenTable() {
        String SQL = """
                     CREATE TABLE ELECTRICITY_TOKEN(
                     TOKEN_ID INTEGER GENERATED ALWAYS AS IDENTITY
                     (START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     PAYMENT_ID INTEGER,
                     TOKEN_CODE VARCHAR(20),
                     UNITS DECIMAL(10, 2),
                     EXPIRY_DATE DATE,
                     FOREIGN KEY(PAYMENT_ID)
                     REFERENCES PAYMENT(PAYMENT_ID)
                     )
                     """;
        return execute(SQL);
    }

    // ================================================================== Oyama's tables
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

    // ================================================================== Sbahle's tables
    public static boolean createSettingsTable() {
        String SQL = """
                     CREATE TABLE Settings(
                     SettingsID INT PRIMARY KEY,
                     UserID INT,
                     Water_alerts VARCHAR(20),
                     Electricity_alerts VARCHAR(20),
                     Language VARCHAR(50)
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createAlertPreferencesTable() {
        String SQL = """
                     CREATE TABLE alert_preferences(
                     user_id INT NOT NULL,
                     alert_type VARCHAR(40) NOT NULL,
                     enabled BOOLEAN NOT NULL,
                     delivery_method VARCHAR(20),
                     PRIMARY KEY (user_id, alert_type)
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createLockPreferencesTable() {
        String SQL = """
                     CREATE TABLE lock_preferences(
                     customer_username VARCHAR(30) PRIMARY KEY,
                     lock_type VARCHAR(10) NOT NULL
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createUsersTable() {
        String SQL = """
                     CREATE TABLE users(
                     user_id INT PRIMARY KEY,
                     username VARCHAR(30),
                     email VARCHAR(100)
                     )
                     """;
        return execute(SQL);
    }

    public static boolean createUserProfilesTable() {
        String SQL = """
                     CREATE TABLE user_profiles(
                     user_id INT PRIMARY KEY,
                     full_name VARCHAR(100),
                     phone_number VARCHAR(20),
                     home_address VARCHAR(200),
                     gender VARCHAR(20),
                     data_of_birth DATE,
                     verification_method VARCHAR(20),
                     account_status VARCHAR(20) DEFAULT 'Active'
                     )
                     """;
        return execute(SQL);
    }

    // ================================================================== views
    public static boolean createElectricityView() {
        String SQL = """
                     CREATE VIEW ELECTRICITY_VIEW AS
                     SELECT U.UTILITY_NAME, T.TRANSACTION_ID, P.PAYMENT_DATE, P.PAYMENT_TIME, P.AMOUNT
                     FROM PAYMENT_TRANSACTIONS T
                     INNER JOIN PAYMENT P ON T.PAYMENT_ID = P.PAYMENT_ID
                     INNER JOIN UTILITY U ON P.UTILITY_ID = U.UTILITY_ID
                     WHERE U.UTILITY_ID = 101
                     """;
        return execute(SQL);
    }

    public static boolean createWaterView() {
        String SQL = """
                     CREATE VIEW WATER_VIEW AS
                     SELECT U.UTILITY_NAME, T.TRANSACTION_ID, P.PAYMENT_DATE, P.PAYMENT_TIME, P.AMOUNT
                     FROM PAYMENT_TRANSACTIONS T
                     INNER JOIN PAYMENT P ON T.PAYMENT_ID = P.PAYMENT_ID
                     INNER JOIN UTILITY U ON P.UTILITY_ID = U.UTILITY_ID
                     WHERE U.UTILITY_ID = 202
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

    // ================================================================== Aphelele's CRUD
    public static int addPayment(Payment payment) {
        String SQL = "INSERT INTO PAYMENT(USER_ID, UTILITY_ID, AMOUNT, SERVICE_FEE, PAYMENT_METHOD, PAYMENT_DATE, PAYMENT_TIME, PAYMENT_STATUS) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getUserID());
            ps.setInt(2, payment.getUtilityID());
            ps.setDouble(3, payment.getAmount());
            ps.setDouble(4, payment.getServiceFee());
            ps.setString(5, payment.getPaymentMethod());
            ps.setDate(6, Date.valueOf(payment.getPaymentDate()));
            ps.setTime(7, Time.valueOf(payment.getPaymentTime()));
            ps.setString(8, payment.getPaymentStatus());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Your payment was successful.");
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return -1;
    }

    public static void addTransaction(Transaction transaction) {
        String SQL = "INSERT INTO PAYMENT_TRANSACTIONS VALUES(?, ?, ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, transaction.getTransactionID());
            ps.setInt(2, transaction.getPaymentID());
            ps.setInt(3, transaction.getUtilityID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "New Transaction created.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static void addToken(ElectricityToken token) {
        String SQL = "INSERT INTO ELECTRICITY_TOKEN (PAYMENT_ID, TOKEN_CODE, UNITS, EXPIRY_DATE) VALUES(?, ?, ?, ?)";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, token.getPaymentID());
            ps.setString(2, token.getTokenCode());
            ps.setDouble(3, token.getUnits());
            ps.setDate(4, Date.valueOf(token.getExpiryDate()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Token created");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // ================================================================== history queries
    public static ResultSet getTransactionHistoryforWater() {
        String SQL = """
                     SELECT U.UTILITY_NAME, T.TRANSACTION_ID, P.PAYMENT_DATE, P.PAYMENT_TIME, P.AMOUNT
                     FROM PAYMENT_TRANSACTIONS T
                     INNER JOIN PAYMENT P ON T.PAYMENT_ID = P.PAYMENT_ID
                     INNER JOIN UTILITY U ON P.UTILITY_ID = U.UTILITY_ID
                     WHERE U.UTILITY_ID = 202
                     """;
        return query(SQL);
    }

    public static ResultSet getTransactionHistoryforElectricity() {
        String SQL = """
                     SELECT U.UTILITY_NAME, T.TRANSACTION_ID, P.PAYMENT_DATE, P.PAYMENT_TIME, P.AMOUNT
                     FROM PAYMENT_TRANSACTIONS T
                     INNER JOIN PAYMENT P ON T.PAYMENT_ID = P.PAYMENT_ID
                     INNER JOIN UTILITY U ON P.UTILITY_ID = U.UTILITY_ID
                     WHERE U.UTILITY_ID = 101
                     """;
        return query(SQL);
    }

    // ================================================================== generic read helpers
    public static ResultSet getCustomers()     { return query("SELECT * FROM CUSTOMERS ORDER BY CREATED DESC"); }
    public static ResultSet getAdmins()        { return query("SELECT * FROM ADMINS ORDER BY IS_MAIN DESC, CREATED"); }
    public static ResultSet getNotifications() { return query("SELECT * FROM NOTIFICATIONS ORDER BY CREATED DESC"); }
    public static ResultSet getTransactions()  { return query("SELECT * FROM TRANSACTIONS ORDER BY CREATED DESC"); }
    public static ResultSet getActivityLog()   { return query("SELECT * FROM ACTIVITY_LOG ORDER BY CREATED DESC, LOG_ID DESC"); }

    // ================================================================== small helpers (used by every class)
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

    public static String meta(String key) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement("SELECT META_VALUE FROM APP_META WHERE META_KEY = ?")) {
            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getString(1) : null; }
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

    public static void report(SQLException e) {
        e.printStackTrace();
        if (showing) return;
        showing = true;
        JOptionPane.showMessageDialog(null,
                "Cannot reach the AquaVolt database.\n\n" + e.getMessage() + "\n\nCheck that:\n"
                + "1. The database server is started:\n"
                + "    Services ▸ Databases ▸ Java DB ▸ Start Server\n"
                + "2. The user name and password are right (default: app / app)\n"
                + "3. No other program is holding the database open",
                "Database problem", JOptionPane.ERROR_MESSAGE);
        showing = false;
    }

    // ================================================================== start-up
    public static boolean init() {
        try {
            connect().close();
            List<String> created = new ArrayList<>();

            // Aphelele (UTILITY first — other tables reference it)
            if (createUtilityTable())              created.add("UTILITY");
            if (createPaymentTable())              created.add("PAYMENT");
            if (createTransactionTable())          created.add("PAYMENT_TRANSACTIONS");
            if (createElectricityTokenTable())     created.add("ELECTRICITY_TOKEN");

            // Oyama
            if (createCustomersTable())            created.add("CUSTOMERS");
            if (createAdminsTable())               created.add("ADMINS");
            if (createActivityLogTable())          created.add("ACTIVITY_LOG");
            if (createNotificationsTable())        created.add("NOTIFICATIONS");
            if (createTransactionsTable())         created.add("TRANSACTIONS");
            if (createAppMetaTable())              created.add("APP_META");

            // Sbahle
            if (createSettingsTable())             created.add("SETTINGS");
            if (createAlertPreferencesTable())     created.add("ALERT_PREFERENCES");
            if (createLockPreferencesTable())      created.add("LOCK_PREFERENCES");
            if (createUsersTable())                created.add("USERS");
            if (createUserProfilesTable())         created.add("USER_PROFILES");

            String problems = findConflicts();
            if (!problems.isEmpty()) {
                JOptionPane.showMessageDialog(null, problems, "Table conflict", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (count("ADMINS") == 0) addMainAdmin();
            seedUtilities();

            // Views
            if (createElectricityView())           created.add("ELECTRICITY_VIEW");
            if (createWaterView())                 created.add("WATER_VIEW");
            if (createPendingCustomersView())      created.add("PENDING_CUSTOMERS");
            if (createPaymentsView())              created.add("ADMIN_PAYMENTS_VIEW");

            if (!created.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Created in the database: " + String.join(", ", created));
            }
            return true;
        } catch (SQLException ex) {
            report(ex);
            return false;
        }
    }

    // ================================================================== seeds
    private static void addMainAdmin() {
        String SQL = "INSERT INTO ADMINS (USERNAME, FULL_NAME, EMAIL, PHONE, PASS_HASH, IS_MAIN, STATUS, REQUESTED_BY) "
                   + "VALUES (?, ?, ?, ?, ?, 'Y', 'APPROVED', 'system')";
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, MAIN_ADMIN);
            ps.setString(2, "Main Administrator");
            ps.setString(3, "admin@aquavolt.sa");
            ps.setString(4, "0210000000");
            ps.setString(5, sha256("admin12304"));
            ps.executeUpdate();
        } catch (SQLException ignore) { /* already there */ }
    }

    /** UTILITY must contain 101 (Electricity) and 202 (Water). */
    private static void seedUtilities() {
        if (count("UTILITY") == 0) {
            execute("INSERT INTO UTILITY (UTILITY_ID, UTILITY_NAME) VALUES (101, 'Electricity')");
            execute("INSERT INTO UTILITY (UTILITY_ID, UTILITY_NAME) VALUES (202, 'Water')");
        }
    }

    // ================================================================== internal helpers
    private static boolean execute(String SQL) {
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            if (!"X0Y32".equals(ex.getSQLState())) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            return false;
        }
    }

    private static ResultSet query(String SQL) {
        try (Connection con = connect(); PreparedStatement ps = con.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
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

    private static String findConflicts() {
        String[][] expected = {{"CUSTOMERS", "PASS_HASH"}, {"ADMINS", "IS_MAIN"},
                {"ACTIVITY_LOG", "ADMIN_USERNAME"}, {"NOTIFICATIONS", "CUSTOMER_USERNAME"},
                {"TRANSACTIONS", "CUSTOMER_USERNAME"}};
        StringBuilder sb = new StringBuilder();
        for (String[] e : expected) {
            try (Connection con = connect(); Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT " + e[1] + " FROM " + e[0] + " WHERE 1 = 0")) {
                // column exists
            } catch (SQLException ex) {
                sb.append("The table ").append(e[0]).append(" already exists but has no column ")
                  .append(e[1]).append(" - it was made by an old version.\n");
            }
        }
        if (sb.length() > 0) {
            sb.append("\nDrop that table in NetBeans (Services ▸ the database ▸ Tables) then run again.");
        }
        return sb.toString();
    }

    // ================================================================== debug
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
                    System.out.println("\n--- " + name + "  (" + rows.size() + " rows) ---");
                    System.out.println(head);
                    for (String row : rows) System.out.println(row);
                }
            }
        } catch (SQLException ex) {
            report(ex);
        }
    }

    public static void main(String[] args) {
        showAll();
        System.exit(0);
    }
}