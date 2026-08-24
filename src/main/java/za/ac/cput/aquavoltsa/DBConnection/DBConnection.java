package za.ac.cput.aquavoltsa.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class DBConnection {

    public static Connection getConnection() {

        String username = "Aqua";
        String password = "VoltSA";
        String databaseURL = "jdbc:derby://localhost:1527/AquaVoltSA";

        Connection con = null;

        System.out.println("Hello");
        System.out.println("===============");
        System.out.println("Requesting connection...");

        try {

            con = DriverManager.getConnection(databaseURL, username, password);
            
            System.out.println("===============");
            System.out.println("Connected!");

        } catch (SQLException ex) {
            
            System.out.println("===============");
            System.out.println("Connection Failed!");
            
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

        return con;
    }

}
