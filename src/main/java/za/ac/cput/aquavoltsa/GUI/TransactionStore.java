//Final update done by Aphelele and Oyama
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

    /** Newest payments first. */
    public static List<Txn> all(int limit) {
        List<Txn> list = new ArrayList<>();
        String sql = "SELECT txn_id, customer_username, utility, meter_no, amount, fee, method, status, token, created "
                + "FROM transactions ORDER BY created DESC FETCH FIRST " + limit + " ROWS ONLY";
        try (Connection c = DAO.connect(); Statement st = c.createStatement(); ResultSet r = st.executeQuery(sql)) {
            while (r.next()) {
                Txn t = new Txn();
                t.id = s(r.getString(1));
                t.username = s(r.getString(2));
                t.utility = s(r.getString(3));
                t.meter = s(r.getString(4));
                t.amount = r.getDouble(5);
                t.fee = r.getDouble(6);
                t.method = s(r.getString(7));
                t.status = s(r.getString(8));
                t.token = s(r.getString(9));
                Timestamp ts = r.getTimestamp(10);
                t.created = ts == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(ts);
                list.add(t);
            }
        } catch (SQLException e) {
            DAO.report(e);
        }
        return list;
    }
}
