package za.ac.cput.aquavoltsa.GUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sbahle
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewSettings {

    public static void main(String[] args) {

        String sql = "SELECT * FROM Settings";

        try {
            Connection conn = DBConnection.getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("===== AQUAVOLT SETTINGS =====");

            while (rs.next()) {

                System.out.println(
                        "Settings ID: "
                        + rs.getInt("SettingsID")
                        + " | User ID: "
                        + rs.getInt("UserID")
                        + " | Water Alerts: "
                        + rs.getString("Water_alerts")
                        + " | Electricity Alerts: "
                        + rs.getString("Electricity_alerts")
                        + " | Language: "
                        + rs.getString("Language")
                );
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
