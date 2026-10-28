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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewNotifications {

    public static void main(String[] args) {

        String url = "jdbc:derby:AquaVoltDB";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM Notifications"
            );

            System.out.println("===== AQUAVOLT NOTIFICATIONS =====");

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("NotificationID")
                    + " | Title: " + rs.getString("NotificationTitle")
                    + " | Status: " + rs.getString("NotificationStatus")
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
