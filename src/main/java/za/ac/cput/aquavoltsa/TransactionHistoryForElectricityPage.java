package za.ac.cput.aquavoltsa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class TransactionHistoryForElectricityPage extends JFrame{
    
    JPanel backgroundPanel, plainPanel, historyPanel, footerPanel, myPanel, searchPanel, totalPanel, totalAmountPanel, totalTransactionPanel;
    JPanel statusPanel, centerTransactionPanel, rightTransactionPanel;
    JLabel bulbLabel, whiteCircleLabel, searchLabel, secureLabel, recentTransactionLabel;
    JLabel footer, utilityPurchaseLabel, meterNumberLabel, amountLabel, dateTimeLabel, statusLabel, headingTitle, totalSpentText, totalTransactionsText, totalAmountSpent, totalTransactions;
    JScrollPane scrollpane;
    ImageIcon bulbIcon, curveCircle;
    JButton electricitybtn, waterbtn, backbtn;
    JTextField searchField;
    
    public static FileReader fr;
    public static BufferedReader br;
    
    double total = 0;
    int totalTransactionsMade = 0;
        
    public TransactionHistoryForElectricityPage(){
        
        super();
        this.setTitle("Transaction History");
        this.setLayout(new BorderLayout());
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);
        
        backgroundPanel = new JPanel(){

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                Color firstElectricityColor = Color.decode("#FFB300");
                Color secondElectricityColor = Color.decode("#FFFFFF");

                GradientPaint gp = new GradientPaint(0, 0, firstElectricityColor, getWidth(), getHeight(), secondElectricityColor);

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

        };
        
        backbtn = new JButton(new ImageIcon("back.png"));
        backbtn.setContentAreaFilled(false);
        backbtn.setBorderPainted(false);
        backbtn.setToolTipText("Go to the previous page");
        backbtn.setBounds(9, 15, 30, 30);
        
        backgroundPanel.setLayout(null);
        
        headingTitle = new JLabel("Transaction History");
        headingTitle.setFont(new Font("Arial", Font.BOLD, 19));
        headingTitle.setBounds(123, 15, 180, 30);
        
        //Resizing the white circle below the title
        curveCircle = new ImageIcon("circle.png");
        Image curve = curveCircle.getImage();
        Image resizedCurveSpace = curve.getScaledInstance(484, 83, Image.SCALE_SMOOTH);
        ImageIcon newCurveSpace = new ImageIcon(resizedCurveSpace);
        
        //Adding the circle to the Label
        whiteCircleLabel = new JLabel(newCurveSpace);
        whiteCircleLabel.setBounds(0, 0, 484, 83);
        whiteCircleLabel.setBorder(BorderFactory.createEmptyBorder(100, -70, 0, 0)); //Moves the circle to the top and to the left
        
        //initialising the plain panel
        plainPanel = new JPanel();
        plainPanel.setLayout(null);
        plainPanel.setBackground(Color.WHITE);
        plainPanel.setBounds(0, 80, 425, 837);
                
        //initialising the buttons
        electricitybtn = new JButton("ELECTRICITY");
        electricitybtn.setFocusPainted(false);
        electricitybtn.setBorderPainted(false);
        electricitybtn.setBackground(Color.WHITE);
        electricitybtn.setFont(new Font("Arial", Font.BOLD, 17));
        electricitybtn.setForeground(Color.decode("#FFB300"));
        electricitybtn.setToolTipText("Show transaction history for electricity");
        electricitybtn.setBounds(20, 80, 150, 30);
        
        
        waterbtn = new JButton("WATER");
        waterbtn.setFocusPainted(false);
        waterbtn.setBorderPainted(false);
        waterbtn.setContentAreaFilled(false);
        waterbtn.setFont(new Font("Arial", Font.BOLD, 17));
        waterbtn.setForeground(Color.decode("#11DDFD"));
        waterbtn.setToolTipText("Show transaction history for water");
        waterbtn.setBounds(230, 80, 150, 30);
        
        //Adding listeners for the buttons
        
        
        //initialising the searchPanel
        searchPanel = new JPanel(new BorderLayout(10, 0));
        
        searchLabel = new JLabel(new ImageIcon("search.png"));
        searchLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        
        searchField = new JTextField();
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setText("Search by meter number or amount");
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        
        //adding listener to the searchfield
        searchField.addFocusListener(new FocusAdapter() { 
            @Override
            public void focusGained(FocusEvent F){
                if (searchField.getText().equals("Search by meter number or amount")){
                    searchField.setText("");
                }
            }
            
            @Override
            public void focusLost(FocusEvent E){
                searchField.setText("Search by meter number or amount");
            }
        });
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setBounds(20, 65, 360, 40);
        
        //Initialising the totap panel
        totalPanel = new JPanel(new BorderLayout());
        
        totalAmountPanel = new JPanel(new GridLayout(2, 0));
        totalTransactionPanel = new JPanel(new GridLayout(2, 0));
        
        totalTransactions = new JLabel(); //value will be added after reading the database
        totalTransactions.setForeground(Color.decode("#FFB300"));
        totalTransactions.setFont(new Font("Arial", Font.BOLD, 17));
        
        totalSpentText = new JLabel("Total Spent");
        totalSpentText.setFont(new Font("Arial", Font.TYPE1_FONT, 14));
        
        totalTransactionsText = new JLabel("Total Transactions");
        totalTransactionsText.setFont(new Font("Arial", Font.TYPE1_FONT, 14));
        
        totalAmountSpent = new JLabel(); //The total amount will be passed here after reading the database
        totalAmountSpent.setForeground(Color.decode("#FFB300"));
        totalAmountSpent.setFont(new Font("Arial", Font.BOLD, 17));
                
        totalAmountPanel.add(totalSpentText);
        totalAmountPanel.add(totalAmountSpent);
        
        totalTransactionPanel.add(totalTransactionsText);
        totalTransactionPanel.add(totalTransactions);
        
        totalPanel.add(totalAmountPanel, BorderLayout.WEST);
        totalPanel.add(totalTransactionPanel, BorderLayout.EAST);
        totalPanel.setBounds(20, 130, 360, 50);
        
        //
        recentTransactionLabel = new JLabel("Recent Transactions");
        recentTransactionLabel.setForeground(Color.BLACK);
        recentTransactionLabel.setFont(new Font("Inter", Font.BOLD, 15));
        recentTransactionLabel.setBounds(20, 200, 200, 30);
        
        historyPanel = new JPanel(new GridLayout(0,1,0,10));
        historyPanel.setBackground(Color.WHITE);
        
        secureLabel = new JLabel(new ImageIcon("secure.png"));
        footer = new JLabel("All transactions are secure");
             
        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(secureLabel);
        footerPanel.add(footer);
        footerPanel.setBackground(Color.white);
        footerPanel.setBounds(20, 700, 360, 40);
        
        //Scaling the light bulb image
        bulbIcon = new ImageIcon("thunder.png");
        
        scrollpane = new JScrollPane(historyPanel);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollpane.getVerticalScrollBar().setUnitIncrement(7);
        scrollpane.setBorder(null);
        scrollpane.setBounds(20, 230, 360, 450);
        
        //Opening the database and reading it so it will load the transaction history
        openFile("Transaction_History_for_Electricity.txt");
        readFile();
        closeFile();
        
        plainPanel.add(searchPanel);
        plainPanel.add(totalPanel);
        plainPanel.add(recentTransactionLabel);
        plainPanel.add(scrollpane);
        plainPanel.add(footerPanel);
        
        
        backgroundPanel.add(headingTitle);
        backgroundPanel.add(electricitybtn);
        backgroundPanel.add(waterbtn);
        backgroundPanel.add(whiteCircleLabel);
        backgroundPanel.add(plainPanel);
        backgroundPanel.add(backbtn);

        
        add(backgroundPanel, BorderLayout.CENTER);
        
    }
    
    //Reading an Existing File
    
    private void openFile(String name){
        
        try{
            fr = new FileReader(name);
            br = new BufferedReader(fr);
        }catch(IOException E){
            JOptionPane.showMessageDialog(null, "Error occured while opening the file", "Something is wrong", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    private void readFile(){
        
        try{
            
            String line;
            while((line = br.readLine()) != null){
                
                String details [] = line.split("#");
                //JOptionPane.showMessageDialog(null, details);
                
                String Etitle = details[0];
                String meterNumber = details[1];
                double amount = Double.parseDouble(details[2]);
                String dateTime = details[3];
                String status = details[4];
                
                total += amount;
                totalAmountSpent.setText("R" + total); //Changes the total amount spent
                                
                bulbLabel = new JLabel(bulbIcon);
                bulbLabel.setOpaque(true);
                
                //initialising transaction information into labels
                utilityPurchaseLabel = new JLabel(Etitle);
                
                meterNumberLabel = new JLabel(meterNumber);
                
                amountLabel = new JLabel("R" + amount);
                
                dateTimeLabel = new JLabel(dateTime);
                
                statusLabel = new JLabel(status);
                statusLabel.setForeground(Color.GREEN);
                //statusLabel.setFont(new Font("Inter", Font.BOLD, 14));
                
                //setting the status panel
                statusPanel = new JPanel();
                statusPanel.setBackground(new Color(0, 128, 0));
                statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7));
                statusPanel.add(statusLabel);
                
                //initialising the panel that will be placed in the center of the history panel
                centerTransactionPanel = new JPanel(new GridLayout(3, 0, 0, 3));
                centerTransactionPanel.add(utilityPurchaseLabel);
                centerTransactionPanel.add(meterNumberLabel);
                centerTransactionPanel.add(dateTimeLabel);
                
                //initialising the panel that will be placed in the right side of the history panel
                rightTransactionPanel = new JPanel(new GridLayout(2,0,0,3));
                rightTransactionPanel.add(amountLabel);
                rightTransactionPanel.add(statusPanel);
                
                //setting the panel that will contain the purchase history
                myPanel = new JPanel(new BorderLayout());
                //myPanel.setPreferredSize(new Dimension(100, 30));
                myPanel.setBackground(Color.GRAY);
                
                myPanel.add(bulbLabel, BorderLayout.WEST);
                myPanel.add(centerTransactionPanel, BorderLayout.CENTER);
                myPanel.add(rightTransactionPanel, BorderLayout.EAST);
                
                
                historyPanel.add(myPanel, 0); //add new panels on top
                totalTransactionsMade += 1; // calculates the number of transactions 
                totalTransactions.setText(String.valueOf(totalTransactionsMade));
                
            }
            
        }catch(IOException E){
            JOptionPane.showMessageDialog(null, "Failed to read the file", "IOException", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void closeFile(){
        
        try {
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occured when closing the file", "IOException", JOptionPane.ERROR_MESSAGE);
        }
        
    }

}
