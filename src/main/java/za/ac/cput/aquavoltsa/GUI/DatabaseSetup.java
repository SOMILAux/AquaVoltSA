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
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {

        String url = "jdbc:derby:AquaVoltDB;create=true";

        try {
            Connection conn = DriverManager.getConnection(url);

            System.out.println("Database connected successfully!");

            Statement stmt = conn.createStatement();

            // Create Settings table
            try {

                String settingsSQL =
                        "CREATE TABLE Settings ("
                        + "SettingsID INT PRIMARY KEY,"
                        + "UserID INT,"
                        + "Water_alerts VARCHAR(20),"
                        + "Electricity_alerts VARCHAR(20),"
                        + "Language VARCHAR(50)"
                        + ")";

                stmt.executeUpdate(settingsSQL);

                System.out.println(
                        "Settings table created successfully!"
                );

            } catch (Exception e) {

                System.out.println(
                        "Settings table already exists."
                );
            }

            stmt.close();
            conn.close();

            System.out.println(
                    "Database setup completed successfully!"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}