package za.ac.cput.aquavoltsa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class TransactionHistoryE {
    
    String title, meterNumber, date, time, amount, status;
    public static FileWriter fw;
    public static BufferedWriter bw;
    
    public TransactionHistoryE() {
    }

    public TransactionHistoryE(String title, String meterNumber, String date, String time, String amount, String status) {
        this.title = title;
        this.meterNumber = meterNumber;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title + "#" + meterNumber + "#" + date + "#" + time + "#" + amount + "#" + status ;
    }
    
    
    
    public static void createFile(String name){
        
        try{
            
        fw = new FileWriter(name, true);
        bw = new BufferedWriter(fw);
        
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error occured while creating the file", "Try again", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public static void writeToFile(String info){
        
        try{
        bw.write(info);
        bw.newLine();
        }catch(IOException e){
           JOptionPane.showMessageDialog(null, "Error occured while writing to file", "Try again", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public static void closeFile(){
        
        try{
            bw.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "The system couldn't close the file", "Try again", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
