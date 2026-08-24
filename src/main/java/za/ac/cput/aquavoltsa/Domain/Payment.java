package za.ac.cput.aquavoltsa.Domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class Payment {

    int paymentID;
    int userID;
    int utilityID;
    double amount;
    double serviceFee;
    String paymentMethod;
    LocalDate paymentDate;
    LocalTime paymentTime;
    String paymentStatus;

    public Payment(int userID, int utilityID, double amount, double serviceFee, String paymentMethod, LocalDate paymentDate, LocalTime paymentTime, String paymentStatus) {
        this.userID = userID;
        this.utilityID = utilityID;
        this.amount = amount;
        this.serviceFee = serviceFee;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.paymentStatus = paymentStatus;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUtilityID() {
        return utilityID;
    }

    public void setUtilityID(int utilityID) {
        this.utilityID = utilityID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
