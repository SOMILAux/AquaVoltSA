/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.aquavoltsa.GUI;

import za.ac.cput.aquavoltsa.GUI.Settings_Page;

/**
 *
 * @author INATHI SHWALAKE
 */
public class AquaVolt_Main {
     public static void main(String[] args) {

        Settings_Page page = new Settings_Page();
        page.showGui();
        page.setSize(600, 810);
        //page.setLocationRelativeTo(null);
        page.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        page.setVisible(true);
    }
}
    

