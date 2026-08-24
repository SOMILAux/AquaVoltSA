package za.ac.cput.aquavoltsa.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class TransactionHistoryForElectricityPage extends JFrame{
    
    JPanel backgroundPanel, topPanel, headerPanel, toggleBtnPanel, centerPanel, midPanel, footerPanel, searchPanel, totalSpentGrid, totalTransactionGrid, totalPanel, historyPanel;
    JLabel transactionHistoryLabel, circleLabel, searchIconLabel, totalSpentLabel, spentLabel, totalTransactionLabel, transactionLabel, recentLabel, secureLabel, securetext;
    JButton backArrowBtn, homeIconBtn;
    JToggleButton electricityBtn, waterBtn;
    ButtonGroup group;
    ImageIcon backArrowIcon, homeIcon, searchIcon, secureIcon, circleIcon;
    JSeparator separator;
    JTextField searchField;
    JScrollPane scrollpane;
    
    public TransactionHistoryForElectricityPage(){
        
        this.setTitle("Transaction History for Electricity");
        
        backArrowIcon = new ImageIcon("back.png");
        homeIcon = new ImageIcon("home.png");
        searchIcon = new ImageIcon("search.png");
        secureIcon = new ImageIcon("secure.png");

        circleIcon = new ImageIcon("circle.png");
        Image circle = circleIcon.getImage();
        Image Circle = circle.getScaledInstance(484, 83, Image.SCALE_SMOOTH);
        ImageIcon whiteCircleIcon = new ImageIcon(Circle);
        
        backgroundPanel = new JPanel() {

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
        backgroundPanel.setLayout(new BorderLayout());
        
        backArrowBtn = new JButton();
        backArrowBtn.setIcon(backArrowIcon);
        backArrowBtn.setBorderPainted(false);

        homeIconBtn = new JButton();
        homeIconBtn.setIcon(homeIcon);
        homeIconBtn.setBorderPainted(false);

        transactionHistoryLabel = new JLabel("Transaction History");
        transactionHistoryLabel.setFont(new Font("Inter", Font.BOLD, 18));
        transactionHistoryLabel.setForeground(Color.WHITE);
        
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setAlignmentX(LEFT_ALIGNMENT);
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10));

        topPanel.add(backArrowBtn);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(transactionHistoryLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(homeIconBtn);

        circleLabel = new JLabel(whiteCircleIcon);

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(425, 90));

        headerPanel.add(topPanel);
        headerPanel.add(circleLabel);
        
        electricityBtn = new JToggleButton("Electricity");
        electricityBtn.setFont(new Font("Inter", Font.BOLD, 17));
        electricityBtn.putClientProperty(FlatClientProperties.STYLE, "selectedBackground : #FFB300; foreground : #FFB300 ; selectedForeground : #FFFFFF");
        electricityBtn.setSelected(true);

        waterBtn = new JToggleButton("Water");
        waterBtn.setFont(new Font("Inter", Font.BOLD, 17));
        waterBtn.setForeground(Color.decode("#00C6FF"));

        group = new ButtonGroup();

        group.add(electricityBtn);
        group.add(waterBtn);

        toggleBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 1));
        toggleBtnPanel.setOpaque(false);

        toggleBtnPanel.add(electricityBtn);
        toggleBtnPanel.add(waterBtn);
        
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.white);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 25));

        separator = new JSeparator();
        separator.setForeground(Color.GRAY);

        searchField = new JTextField();
        searchField.setBorder(null);
        searchField.setBackground(Color.decode("#FAFAFA"));
        searchField.setPreferredSize(new Dimension(250, 40));
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by ID");

        searchIconLabel = new JLabel(searchIcon);
        
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 1));
        searchPanel.setBackground(Color.decode("#FAFAFA"));
        searchPanel.setAlignmentX(LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(452, 45));
        searchPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 15");

        searchPanel.add(searchIconLabel);
        searchPanel.add(searchField);

        totalSpentLabel = new JLabel("Total spent");
        spentLabel = new JLabel();
        spentLabel.setFont(new Font("Inter", Font.BOLD, 17));
        spentLabel.setForeground(Color.decode("#FFB300"));

        totalTransactionLabel = new JLabel("Total Transactions");
        transactionLabel = new JLabel();
        transactionLabel.setFont(new Font("Inter", Font.BOLD, 17));
        transactionLabel.setForeground(Color.decode("#FFB300"));

        totalSpentGrid = new JPanel(new GridLayout(2, 1));
        totalSpentGrid.setOpaque(false);
        totalSpentGrid.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));

        totalSpentGrid.add(totalSpentLabel);
        totalSpentGrid.add(spentLabel);
        
        totalTransactionGrid = new JPanel(new GridLayout(2, 1));
        totalTransactionGrid.setOpaque(false);
        totalTransactionGrid.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 5));

        totalTransactionGrid.add(totalTransactionLabel);
        totalTransactionGrid.add(transactionLabel);

        totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(Color.decode("#FAFAFA"));
        totalPanel.setAlignmentX(LEFT_ALIGNMENT);
        totalPanel.setMaximumSize(new Dimension(425, 70));
        totalPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 15");

        totalPanel.add(totalSpentGrid, BorderLayout.CENTER);
        totalPanel.add(totalTransactionGrid, BorderLayout.EAST);

        recentLabel = new JLabel("Recent Transactions");
        recentLabel.setFont(new Font("Inter", Font.BOLD, 16));
        recentLabel.setForeground(Color.BLACK);
        recentLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);
        historyPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));

        getTransactionHistory();//call the method that will put every transaction into the history panel

        scrollpane = new JScrollPane(historyPanel);
        scrollpane.setAlignmentX(LEFT_ALIGNMENT);

        secureLabel = new JLabel(secureIcon);
        securetext = new JLabel("All Transactions are secure");

        footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 1));
        footerPanel.setOpaque(false);

        footerPanel.add(secureLabel);
        footerPanel.add(securetext);

        midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.setOpaque(false);
        
        midPanel.add(Box.createVerticalStrut(7));
        midPanel.add(separator);
        midPanel.add(Box.createVerticalStrut(15));
        midPanel.add(searchPanel);
        midPanel.add(Box.createVerticalStrut(15));
        midPanel.add(totalPanel);
        midPanel.add(Box.createVerticalStrut(20));
        midPanel.add(recentLabel);
        midPanel.add(Box.createVerticalStrut(10));
        midPanel.add(scrollpane);
        
        
        
        
        centerPanel.add(toggleBtnPanel, BorderLayout.NORTH);
        centerPanel.add(midPanel, BorderLayout.CENTER);
        centerPanel.add(footerPanel, BorderLayout.SOUTH);

        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        add(backgroundPanel);
        
    }
    
    public void getTransactionHistory() {

        ResultSet rs = DAO.getTransactionHistoryforElectricity();
        double amountSpent = 0;
        int transactions = 0;

        try {
            while (rs.next()) {

                String utilityName = rs.getString("UTILITY_NAME");
                String transactionID = rs.getString("TRANSACTION_ID");
                Date date = rs.getDate("PAYMENT_DATE");
                Time time = rs.getTime("PAYMENT_TIME");
                double amount = rs.getDouble("AMOUNT");

                JLabel name = new JLabel(utilityName);
                name.setFont(new Font("Inter", Font.BOLD, 18));

                JLabel id = new JLabel("ID: " + transactionID);
                JLabel Tdate = new JLabel(date.toString() + " * " + time.toString());
                JLabel Tamount = new JLabel("Amount: " + String.valueOf(amount + 2.50));
                ImageIcon electricityIcon = new ImageIcon("thunder.png");

                JPanel detailsPanel = new JPanel();
                detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

                detailsPanel.add(name);
                detailsPanel.add(id);
                detailsPanel.add(Tdate);
                detailsPanel.add(Tamount);

                JPanel transactionPanel = new JPanel(new BorderLayout(5, 5));
                transactionPanel.setMaximumSize(new Dimension(350, 85));
                transactionPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
                transactionPanel.setAlignmentX(LEFT_ALIGNMENT);

                transactionPanel.add(new JLabel(electricityIcon), BorderLayout.WEST);
                transactionPanel.add(detailsPanel, BorderLayout.CENTER);

                historyPanel.add(transactionPanel, 0);
                historyPanel.add(Box.createVerticalStrut(5), 1);
                
                amountSpent += amount + 2.5;
                transactions += 1;
                
                spentLabel.setText("R"+String.valueOf(amountSpent));
                transactionLabel.setText(String.valueOf(transactions));

            }
        } catch (SQLException ex) {
            ex.getMessage();
        }

    }

}
