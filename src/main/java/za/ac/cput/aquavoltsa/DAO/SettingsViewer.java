//Final update done by Oyama and Aphelele
package za.ac.cput.aquavoltsa.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;

/**
 * Debug helper — prints only the Settings table to the Output window.
 * (To see ALL tables, run DAO.java → main() → DAO.showAll().)
 */
public class SettingsViewer {

    public static void main(String[] args) {
        String sql = "SELECT * FROM Settings";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {

            System.out.println("===== AQUAVOLT SETTINGS =====");
            while (rs.next()) {
                System.out.println(
                        "Settings ID: "       + rs.getInt("SettingsID")
                        + " | User ID: "      + rs.getInt("UserID")
                        + " | Water Alerts: " + rs.getString("Water_alerts")
                        + " | Electricity Alerts: " + rs.getString("Electricity_alerts")
                        + " | Language: "     + rs.getString("Language"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}