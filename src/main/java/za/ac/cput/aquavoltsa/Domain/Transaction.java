package za.ac.cput.aquavoltsa.Domain;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class Transaction {
    
    String transactionID;
    int paymentID;
    int utilityID;

    public Transaction(String transactionID, int paymentID, int utilityID) {
        this.transactionID = transactionID;
        this.paymentID = paymentID;
        this.utilityID = utilityID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getUtilityID() {
        return utilityID;
    }

    public void setUtilityID(int utilityID) {
        this.utilityID = utilityID;
    }
    
    

}
