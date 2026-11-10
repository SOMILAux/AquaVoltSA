package za.ac.cput.aquavoltsa.GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Connection to the Java DB (Apache Derby) database AquaVoltDB.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public final class Database {
    // Embedded Derby URL. It creates the database in the project folder automatically.
    static final String URL = "jdbc:derby:AquaVoltDB;create=true";
    
    static final String USER = "app";
    static final String PASS = "app";

    private static boolean showing = false;

    private Database() { }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /** 
     * Runs once while the loading page is showing. 
     * Creates all the missing tables and adds starter data automatically.
     */
    public static boolean init() {
        try (Connection c = connect(); Statement st = c.createStatement()) {
            
            // ==========================================
            // 1. YOUR CUSTOMERS TABLE
            // ==========================================
            create(st, "CREATE TABLE customers ("
                    + "id VARCHAR(12) PRIMARY KEY, full_name VARCHAR(100) NOT NULL, id_number VARCHAR(20) NOT NULL, "
                    + "username VARCHAR(30) NOT NULL UNIQUE, pass_hash VARCHAR(64) NOT NULL, email VARCHAR(100) NOT NULL, "
                    + "phone VARCHAR(20) NOT NULL, address VARCHAR(200), meter VARCHAR(30), serial VARCHAR(30), "
                    + "water_meter VARCHAR(30), account_no VARCHAR(30), status VARCHAR(10) NOT NULL DEFAULT 'PENDING', "
                    + "created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            
            // ==========================================
            // 2. YOUR ADMINS TABLE
            // ==========================================
            create(st, "CREATE TABLE admins ("
                    + "username VARCHAR(40) PRIMARY KEY, full_name VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL, "
                    + "phone VARCHAR(20) NOT NULL, pass_hash VARCHAR(64) NOT NULL, is_main CHAR(1) NOT NULL DEFAULT 'N', "
                    + "status VARCHAR(10) NOT NULL DEFAULT 'PENDING', requested_by VARCHAR(40), "
                    + "created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            
            // ==========================================
            // 3. YOUR ACTIVITY LOG TABLE
            // ==========================================
            create(st, "CREATE TABLE activity_log ("
                    + "log_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, admin_username VARCHAR(40) NOT NULL, "
                    + "activity VARCHAR(30) NOT NULL, details VARCHAR(100), "
                    + "created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            
            // ==========================================
            // 4. OTHER REQUIRED TABLES FOR OUR APP SUCH AS THE NOTIFICATIONS YOU KNOW
            // ==========================================
            create(st, "CREATE TABLE notifications ("
                    + "notif_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, customer_username VARCHAR(30) NOT NULL, "
                    + "category VARCHAR(20) NOT NULL, title VARCHAR(80) NOT NULL, message VARCHAR(500) NOT NULL, "
                    + "status VARCHAR(10) NOT NULL DEFAULT 'UNREAD', sent_by VARCHAR(40) NOT NULL, "
                    + "created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            
            create(st, "CREATE TABLE transactions ("
                    + "txn_id VARCHAR(40) PRIMARY KEY, customer_username VARCHAR(30) NOT NULL, utility VARCHAR(12) NOT NULL, "
                    + "meter_no VARCHAR(30), amount DECIMAL(10,2) NOT NULL, fee DECIMAL(10,2) NOT NULL, method VARCHAR(40), "
                    + "status VARCHAR(15) NOT NULL, token VARCHAR(30), created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            
            // Add the starter data (demo users, etc.)
            seed(c);
            seedExtras(c);
            return true;
        } catch (SQLException e) {
            report(e);
            return false;
        }
    }

    // "table already exists" (X0Y32) is fine - it just means the table was made on an earlier run I HOPE
    private static void create(Statement st, String sql) throws SQLException {
        try {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) throw e;
        }
    }

    private static int count(Connection c, String table) throws SQLException {
        try (Statement st = c.createStatement(); ResultSet r = st.executeQuery("SELECT COUNT(*) FROM " + table)) {
            r.next();
            return r.getInt(1);
        }
    }

    private static void seed(Connection c) throws SQLException {
        if (count(c, "admins") == 0) {   // the main administrator: admin12304 / admin12304
            try (PreparedStatement p = c.prepareStatement("INSERT INTO admins (username, full_name, email, phone, "
                    + "pass_hash, is_main, status, requested_by) VALUES (?, ?, ?, ?, ?, 'Y', 'APPROVED', 'system')")) {
                p.setString(1, AdminStore.MAIN);
                p.setString(2, "Main Administrator");
                p.setString(3, "admin@aquavolt.sa");
                p.setString(4, "0210000000");
                p.setString(5, UserStore.hash("admin12304"));
                p.executeUpdate();
            }
        }
        if (count(c, "customers") == 0) {   // demo customers so the admin dashboard is not empty
            demo("Cheese Mokoena", "9001015800086", "Cheese", "Cheese@123", "cheese@mail.co.za", "0821234567",
                    "12 Long Street, Cape Town", "MTR-44821", "SN-77120", "WTR-1024", "ACC-9981", UserStore.VERIFIED);
            demo("Thando Nkosi", "9505055800083", "Thando", "Thando@123", "thando@gmail.com", "0735550142",
                    "8 Vilakazi Street, Soweto", "MTR-10977", "SN-30551", "", "", UserStore.PENDING);
            demo("Naledi Dlamini", "0001015800087", "Naledi", "Naledi@123", "naledi@outlook.com", "0614449087",
                    "45 Berea Road, Durban", "", "", "WTR-5521", "ACC-1207", UserStore.PENDING);
            demo("Sipho Khumalo", "8812125800085", "Sipho", "Sipho@123", "sipho.k@mail.com", "0794447712",
                    "3 Church Street, Pretoria", "MTR-88231", "SN-12089", "WTR-7710", "ACC-3345", UserStore.VERIFIED);
        }
    }

    /** Starter notifications and payments so the customer and admin screens are not empty. */
    private static void seedExtras(Connection c) throws SQLException {
        if (count(c, "notifications") == 0 && UserStore.find("Cheese") != null) {
            NotificationStore.send("Cheese", "WATER", "Drought Alert", "Water restrictions are in place in your area. Please use water sparingly.", "system");
            NotificationStore.send("Cheese", "ELECTRICITY", "Loadshedding Update", "Stage 2 loadshedding today from 18:00 to 20:30.", "system");
            NotificationStore.send("Cheese", "WATER", "Usage Warning", "Your water usage is 20% above your daily average.", "system");
            NotificationStore.send("Cheese", "PAYMENT", "Payment Reminder", "Your electricity account payment is due in 3 days.", "system");
            NotificationStore.send("Cheese", "SYSTEM", "System Update", "AquaVolt SA now has notifications and payment history.", "system");
        }
        if (count(c, "transactions") == 0 && UserStore.find("Cheese") != null) {
            TransactionStore.record("Cheese", "WTR-2026-10-20-09-15-02", "WATER", "1024", 250, 2.5, "Debit / Credit Card", "Successful", null);
            TransactionStore.record("Cheese", "ELE-2026-10-21-14-02-41", "ELECTRICITY", "44821", 100, 2.5, "EFT", "Successful", "4821 7733 1290 6654");
            TransactionStore.record("Sipho", "ELE-2026-10-22-18-30-10", "ELECTRICITY", "88231", 150, 2.5, "Debit / Credit Card", "Successful", "9034 1187 4520 7781");
        }
    }

    private static void demo(String name, String idNo, String user, String pass, String mail, String phone,
                             String addr, String meter, String serial, String water, String acc, String status) {
        UserStore.User u = new UserStore.User();
        u.fullName = name; u.idNumber = idNo; u.username = user; u.passHash = UserStore.hash(pass);
        u.email = mail; u.phone = phone; u.address = addr; u.meter = meter; u.serial = serial;
        u.waterMeter = water; u.accountNo = acc; u.status = status;
        UserStore.add(u);
    }

    /** Shows one clear message when the database cannot be reached. */
    static void report(SQLException e) {
        e.printStackTrace();
        if (showing) return;
        showing = true;
        JOptionPane.showMessageDialog(null,
                "Cannot use the AquaVolt database.\n\n" + e.getMessage() + "\n\nCheck that:\n"
                + "1. The Derby driver is in your pom if not then...yeah neh.xml\n"
                + "2. You have any issues please contact Ta Yama.",
                "Database problem", JOptionPane.ERROR_MESSAGE);
        showing = false;
    }
}