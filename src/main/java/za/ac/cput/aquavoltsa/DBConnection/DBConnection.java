//Final update done by Oyama and Aphelele
package za.ac.cput.aquavoltsa.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * THE one and only connection class for the whole AquaVolt SA project.
 * Every class must go through this so all teammates work on the SAME database.
 *
 * Team setup on each computer (do this once):
 *   1. NetBeans ▸ Services ▸ Databases ▸ Java DB ▸ Start Server
 *   2. Right-click Java DB ▸ Create Database:
 *          URL      : jdbc:derby://localhost:1527/AquaVoltDB;create=true
 *          User     : app
 *          Password : app
 *   3. Run the app – DAO.init() creates every table automatically.
 */
public class DBConnection {

    private static final String URL  = "jdbc:derby://localhost:1527/AquaVoltDB;create=true";
    private static final String USER = "app";
    private static final String PASS = "app";

    private DBConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}