package za.ac.cput.aquavoltsa.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import javax.swing.JOptionPane;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;
import za.ac.cput.aquavoltsa.Domain.ElectricityToken;
import za.ac.cput.aquavoltsa.Domain.Payment;
import za.ac.cput.aquavoltsa.Domain.Transaction;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class DAO {

    public static void createCustomerTable() {

        String SQL = """
                     CREATE TABLE CUSTOMERS(
                     USER_ID INTEGER GENERATED ALWAYS AS IDENTITY(
                     START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     FIRST_NAME VARCHAR(100),
                     LAST_NAME VARCHAR(100),
                     USERNAME VARCHAR(100),
                     PASSWORD VARCHAR(255),
                     PHONE_NUMBER VARCHAR(50),
                     EMAIL_ADDRESS VARCHAR(100),
                     HOME_ADDRESS VARCHAR(100)
                     )
                     """;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Table 'CUSTOMERS' has been created successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    
    public static void createPaymentTable() {

        String SQL = """
                     CREATE TABLE PAYMENT(
                     PAYMENT_ID INTEGER GENERATED ALWAYS AS IDENTITY(
                     START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     USER_ID INTEGER,
                     UTILITY_ID INTEGER,
                     AMOUNT DECIMAL(10, 2),
                     SERVICE_FEE DECIMAL(10, 2),
                     PAYMENT_METHOD VARCHAR(50),
                     PAYMENT_DATE DATE,
                     PAYMENT_TIME TIME,
                     PAYMENT_STATUS VARCHAR(100),
                     
                     FOREIGN KEY(USER_ID)
                     REFERENCES CUSTOMERS(USER_ID),
                     FOREIGN KEY (UTILITY_ID)
                     REFERENCES UTILITY(UTILITY_ID)
                     )
                     """;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Table 'PAYMENT' has been created successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    
    public static void createTransactionTable(){
        
        String SQL = """
                     CREATE TABLE TRANSACTIONS(
                     TRANSACTION_ID VARCHAR(100) PRIMARY KEY,
                     PAYMENT_ID INTEGER,
                     UTILITY_ID INTEGER,
                     
                     FOREIGN KEY(PAYMENT_ID)
                     REFERENCES PAYMENT(PAYMENT_ID),
                     FOREIGN KEY(UTILITY_ID)
                     REFERENCES UTILITY(UTILITY_ID)
                     )
                     """;
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Table 'TRANSACTIONS' has been created successfully.");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    public static void createUtilityTable(){
        
        String SQL = """
                     CREATE TABLE UTILITY(
                     UTILITY_ID INTEGER PRIMARY KEY,
                     UTILITY_NAME VARCHAR(25) NOT NULL
                     )
                     """;
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Table 'UTILITY' has been created successfully.");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    public static void createElectricityTokenTable(){
        
        String SQL = """
                     CREATE TABLE ELECTRICITY_TOKEN(
                     TOKEN_ID INTEGER GENERATED ALWAYS AS IDENTITY
                     (START WITH 1, INCREMENT BY 1)
                     PRIMARY KEY,
                     PAYMENT_ID INTEGER,
                     TOKEN_CODE VARCHAR(20),
                     UNITS DECIMAL(10, 2),
                     EXPIRY_DATE DATE,
                     
                     FOREIGN KEY(PAYMENT_ID)
                     REFERENCES PAYMENT(PAYMENT_ID)
                     )
                     """;
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Table 'TOKEN' has been created successfully.");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        
    }
    
    public static int addPayment(Payment payment){
        
        String SQL = "INSERT INTO PAYMENT(USER_ID, UTILITY_ID, AMOUNT, SERVICE_FEE, PAYMENT_METHOD, PAYMENT_DATE, PAYMENT_TIME, PAYMENT_STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setInt(1, payment.getUserID());
            ps.setInt(2, payment.getUtilityID());
            ps.setDouble(3, payment.getAmount());
            ps.setDouble(4, payment.getServiceFee());
            ps.setString(5, payment.getPaymentMethod());
            ps.setDate(6, Date.valueOf(payment.getPaymentDate()));
            ps.setTime(7, Time.valueOf(payment.getPaymentTime()));
            ps.setString(8, payment.getPaymentStatus());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Your payment was successful.");
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()){
                return rs.getInt(1);
            }
            
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
                     
                     
        return -1;
    }
    
    public static void addTransaction(Transaction transaction){
        
        String SQL = "INSERT INTO TRANSACTIONS VALUES(?, ?, ?)";
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            ps.setString(1, transaction.getTransactionID());
            ps.setInt(2, transaction.getPaymentID());
            ps.setInt(3, transaction.getUtilityID());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "New Transaction created.");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    public static void addToken(ElectricityToken token){
        
        String SQL = "INSERT INTO ELECTRICITY_TOKEN (PAYMENT_ID, TOKEN_CODE, UNITS, EXPIRY_DATE) VALUES(?, ?, ?, ?)";
        
         
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            ps.setInt(1, token.getPaymentID());
            ps.setString(2, token.getTokenCode());
            ps.setDouble(3, token.getUnits());
            ps.setDate(4, Date.valueOf(token.getExpiryDate()));
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Token created");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    public static ResultSet getTransactionHistoryforWater(){
        
        String SQL = """
                     SELECT U.UTILITY_NAME, T.TRANSACTION_ID, P.PAYMENT_DATE, P.PAYMENT_TIME, P.AMOUNT 
                                          FROM 
                                          TRANSACTIONS T 
                                          INNER JOIN PAYMENT P 
                                          ON 
                                          T.PAYMENT_ID = P.PAYMENT_ID 
                                          INNER JOIN 
                                          UTILITY U 
                                          ON 
                                          P.UTILITY_ID =  U.UTILITY_ID
                                          WHERE 
                                          U.UTILITY_ID = 202
                     """;  
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            return ps.executeQuery();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return null;
    }
    
    public static ResultSet getTransactionHistoryforElectricity(){
        
        String SQL = """
                     SELECT U.UTILITY_NAME, T.TRANSACTION_ID, P.PAYMENT_DATE, P.PAYMENT_TIME, P.AMOUNT 
                                          FROM 
                                          TRANSACTIONS T 
                                          INNER JOIN PAYMENT P 
                                          ON 
                                          T.PAYMENT_ID = P.PAYMENT_ID 
                                          INNER JOIN 
                                          UTILITY U 
                                          ON 
                                          P.UTILITY_ID =  U.UTILITY_ID
                                          WHERE 
                                          U.UTILITY_ID = 101
                     """;  
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            
            return ps.executeQuery();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return null;
        
    }

 }
