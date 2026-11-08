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
