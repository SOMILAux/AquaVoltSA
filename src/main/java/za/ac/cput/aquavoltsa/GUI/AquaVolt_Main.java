package za.ac.cput.aquavoltsa.GUI;

import javax.swing.JFrame;
import za.ac.cput.aquavoltsa.GUI.Settings_Page;

/**
 *
 * @author INATHI SHWALAKE
 */
public class AquaVolt_Main {
     public static void main(String[] args) {

        Settings_Page page = new Settings_Page();
        page.showGui();
        page.setSize(425, 917);
        page.setLocationRelativeTo(null);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page.setVisible(true);
    }
}