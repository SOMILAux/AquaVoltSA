/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package za.ac.cput.aquavoltsa.GUI;

import za.ac.cput.aquavoltsa.GUI.DashboardFrame;
import javax.swing.SwingUtilities;

/**
 * Home page
 * @author Shuma
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardFrame();

        });

    }
}
