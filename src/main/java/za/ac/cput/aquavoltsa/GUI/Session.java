//Final update done by Oyama and Aphelele
package za.ac.cput.aquavoltsa.GUI;

/**
 * Remembers who is logged in so every page (including the group's pages) can use it.
 */
final class Session {
    static UserStore.User customer;       // set after the customer passes the OTP page
    static String lastTxnId, lastToken;   // set when a payment is recorded, read by the "payment successful" pages

    private Session() { }

    static String username() {
        return customer == null ? null : customer.username;
    }

    static String name() {
        return customer == null ? "Customer" : (customer.fullName.isEmpty() ? customer.username : customer.fullName);
    }

    static void clear() {
        customer = null;
        lastTxnId = null;
        lastToken = null;
    }
}
