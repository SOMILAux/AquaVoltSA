//Final update done by Oyama, Sbahle and Aphelele
package za.ac.cput.aquavoltsa.DAO;

/**
 * Manual trigger for the database setup (Shift+F6 on this file).
 * In normal use, SplashPage → DAO.init() already does it at startup, so
 * you never need to run this by hand.
 */
public class DatabaseSetup {

    public static void main(String[] args) {
        System.out.println("Setting up the AquaVolt database (via DAO.init())…");
        boolean ok = DAO.init();
        System.out.println(ok
                ? "Database setup completed successfully!"
                : "Database setup failed — see the pop-up for details.");
    }
}