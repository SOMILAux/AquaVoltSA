package za.ac.cput.aquavoltsa.GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 * Every water / electricity payment is also saved in the TRANSACTIONS table so the admin can see it.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public final class TransactionStore {
    public static class Txn {
        public String id = "", username = "", utility = "", meter = "", method = "", status = "", token = "", created = "";
        public double amount, fee;
    }

    private TransactionStore() { }

    private static String s(String v) {
        return v == null ? "" : v;
    }

    /** Called by the payment pages. The logged-in customer (Session) is the payer. */
    public static void record(String txnId, String utility, String meter, double amount, double fee,
                              String method, String status, String token) {
        String user = Session.username();
        record(user == null ? "guest" : user, txnId, utility, meter, amount, fee, method, status, token);
        Session.lastTxnId = txnId;       // so the "payment successful" page shows the same ID that was saved
        Session.lastToken = token;
    }

    public static void record(String username, String txnId, String utility, String meter, double amount,
                              double fee, String method, String status, String token) {
        String sql = "INSERT INTO transactions (txn_id, customer_username, utility, meter_no, amount, fee, method, status, token) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = DAO.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, txnId);
            p.setString(2, username);
            p.setString(3, utility);
            p.setString(4, meter);
            p.setDouble(5, amount);
            p.setDouble(6, fee);
            p.setString(7, method);
            p.setString(8, status);
            p.setString(9, token);
            p.executeUpdate();
        } catch (SQLException e) {
            if (!"23505".equals(e.getSQLState())) DAO.report(e);   // 23505 = same ID saved twice, ignore
        }
    }

    /** 
     * Newest payments first. 
     * UPDATED: Now fetches from Aphelele's PAYMENT and PAYMENT_TRANSACTIONS tables 
     * so the Admin Dashboard can actually see the payments made via the buy pages!
     */
    public static List<Txn> all(int limit) {
        List<Txn> list = new ArrayList<>();
        String sql = "SELECT T.TRANSACTION_ID, P.USER_ID, P.UTILITY_ID, P.AMOUNT, P.SERVICE_FEE, P.PAYMENT_METHOD, P.PAYMENT_DATE, P.PAYMENT_STATUS, U.UTILITY_NAME " +
                     "FROM PAYMENT_TRANSACTIONS T " +
                     "JOIN PAYMENT P ON T.PAYMENT_ID = P.PAYMENT_ID " +
                     "LEFT JOIN UTILITY U ON P.UTILITY_ID = U.UTILITY_ID " +
                     "ORDER BY P.PAYMENT_DATE DESC, P.PAYMENT_ID DESC FETCH FIRST " + limit + " ROWS ONLY";
                     
        try (Connection c = DAO.connect(); Statement st = c.createStatement(); ResultSet r = st.executeQuery(sql)) {
            while (r.next()) {
                Txn t = new Txn();
                t.id = s(r.getString("TRANSACTION_ID"));
                
                // Aphelele's code hardcodes userID = 1. So we show "Customer 1"
                t.username = "Customer " + r.getInt("USER_ID");
                
                // Check Utility Name, fallback to hardcoded ID if name is null
                t.utility = s(r.getString("UTILITY_NAME"));
                if (t.utility.isEmpty()) {
                    t.utility = (r.getInt("UTILITY_ID") == 202) ? "Water" : "Electricity";
                }
                
                t.meter = ""; // Not stored in PAYMENT table
                t.amount = r.getDouble("AMOUNT");
                t.fee = r.getDouble("SERVICE_FEE");
                t.method = s(r.getString("PAYMENT_METHOD"));
                t.status = s(r.getString("PAYMENT_STATUS"));
                t.token = ""; // Tokens are in ELECTRICITY_TOKEN table, not joined here
                
                java.sql.Date date = r.getDate("PAYMENT_DATE");
                t.created = (date == null) ? "" : date.toString();
                
                list.add(t);
            }
        } catch (SQLException e) {
            DAO.report(e);
        }
        return list;
    }
}