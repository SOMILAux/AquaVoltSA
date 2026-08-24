package za.ac.cput.aquavoltsa.Domain;

import java.time.LocalDate;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class ElectricityToken {

    int tokenID;
    int paymentID;
    String tokenCode;
    double units;
    LocalDate expiryDate;

    public ElectricityToken(int paymentID, String tokenCode, double units, LocalDate expiryDate) {
        this.paymentID = paymentID;
        this.tokenCode = tokenCode;
        this.units = units;
        this.expiryDate = expiryDate;
    }

    public int getTokenID() {
        return tokenID;
    }

    public void setTokenID(int tokenID) {
        this.tokenID = tokenID;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    
}
