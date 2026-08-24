package za.ac.cput.aquavoltsa.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class buyElectricityPage extends JFrame{
    
    JPanel backgroundPanel, headerPanel, centerPanel, buttonPanel, buttonPanel2, customPanel;
    ImageIcon logo, circle;
    JLabel logoLabel, circleLabel, meterNumberLabel, selectAmountLabel, currencyLabel;
    JTextField meterNumberField, customAmountField;
    JSeparator separator;
    JToggleButton firstAmount,secondAmount, thirdAmount, customAmount;
    ButtonGroup group;
    JButton continueBtn, backBtn;
    
    int amount;
    
    public buyElectricityPage(){
        
        this.setTitle("Buying electricity");
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
                
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
        
        backgroundPanel.setLayout(new BorderLayout());
        logo = new ImageIcon("firstLogo.png");
        Image logoIcon = logo.getImage();
        Image resizedLogo = logoIcon.getScaledInstance(450, 252, Image.SCALE_SMOOTH);
        ImageIcon businessLogo = new ImageIcon(resizedLogo);

        circle = new ImageIcon("gray-circle.png");
        Image circleIcon = circle.getImage();
        Image resizedCircle = circleIcon.getScaledInstance(484, 83, Image.SCALE_SMOOTH);
        ImageIcon circular = new ImageIcon(resizedCircle);

        logoLabel = new JLabel(businessLogo);
        logoLabel.setOpaque(true);

        circleLabel = new JLabel(circular);

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(-60));
        headerPanel.add(circleLabel);
        
        //Initialising the content Panel
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 50));

        meterNumberLabel = new JLabel("Meter Number");
        meterNumberLabel.setFont(new Font("Arial", Font.BOLD, 20));
        meterNumberLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        meterNumberField = new JTextField();
        meterNumberField.setFont(new Font("Arial", Font.PLAIN, 20));
        meterNumberField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        meterNumberField.setAlignmentX(LEFT_ALIGNMENT);
        meterNumberField.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        meterNumberField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your Meter Number");
        meterNumberField.setMargin(new Insets(0, 10, 0, 5));
        
        separator = new JSeparator();
        separator.setPreferredSize(new Dimension(425, 2));
        separator.setForeground(Color.decode("#FFFFFF"));
        separator.setAlignmentX(LEFT_ALIGNMENT);

        selectAmountLabel = new JLabel("Select Amount");
        selectAmountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        selectAmountLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        firstAmount = new JToggleButton("R50");
        firstAmount.setBorderPainted(false);
        firstAmount.setFocusPainted(false);
        firstAmount.setMaximumSize(new Dimension(150, 50));
        firstAmount.setFont(new Font("Inter", Font.BOLD, 18));
        firstAmount.putClientProperty(FlatClientProperties.STYLE, "arc: 30; selectedBackground: #FFB300; selectedForeground: #FFFFFF");
        
        secondAmount = new JToggleButton("R100");
        secondAmount.setBorderPainted(false);
        secondAmount.setFocusPainted(false);
        secondAmount.setMaximumSize(new Dimension(150, 50));
        secondAmount.setFont(new Font("Inter", Font.BOLD, 18));
        secondAmount.putClientProperty(FlatClientProperties.STYLE, "arc: 30; selectedBackground: #FFB300; selectedForeground: #FFFFFF");
        
        thirdAmount = new JToggleButton("R150");
        thirdAmount.setBorderPainted(false);
        thirdAmount.setFocusPainted(false);
        thirdAmount.setMaximumSize(new Dimension(150, 50));
        thirdAmount.setFont(new Font("Inter", Font.BOLD, 18));
        thirdAmount.putClientProperty(FlatClientProperties.STYLE, "arc: 30; selectedBackground: #FFB300; selectedForeground: #FFFFFF");
        
        customAmount = new JToggleButton("CUSTOM");
        customAmount.setBorderPainted(false);
        customAmount.setFocusPainted(false);
        customAmount.setMaximumSize(new Dimension(150, 50));
        customAmount.setFont(new Font("Inter", Font.BOLD, 18));
        customAmount.putClientProperty(FlatClientProperties.STYLE, "arc: 30; selectedBackground: #FFB300; selectedForeground: #FFFFFF");
        
         group = new ButtonGroup();
         
         group.add(firstAmount);
         group.add(secondAmount);
         group.add(thirdAmount);
         group.add(customAmount);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.setOpaque(false);
        
        buttonPanel.add(firstAmount);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(secondAmount);
        
        buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new BoxLayout(buttonPanel2, BoxLayout.X_AXIS));
        buttonPanel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonPanel2.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel2.setOpaque(false);
        
        buttonPanel2.add(thirdAmount);
        buttonPanel2.add(Box.createHorizontalStrut(50));
        buttonPanel2.add(customAmount);
        
        currencyLabel = new JLabel("R");
        currencyLabel.setFont(new Font("Inter", Font.BOLD, 20));
        currencyLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        customAmountField = new JTextField();
        customAmountField.setBorder(null);
        customAmountField.setFont(new Font("Arial", Font.PLAIN, 20));
        customAmountField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Amount");
        customAmountField.setOpaque(false);

        customPanel = new JPanel(new BorderLayout(5, 0));
        customPanel.setBackground(Color.WHITE);
        customPanel.setAlignmentX(LEFT_ALIGNMENT);
        customPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        customPanel.setVisible(false);
        customPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
        customPanel.setOpaque(false);
        
        customPanel.add(currencyLabel, BorderLayout.WEST);
        customPanel.add(customAmountField, BorderLayout.CENTER);
        
        continueBtn = new JButton("CONTINUE");
        continueBtn.setBorderPainted(false);
        continueBtn.setFocusPainted(false);
        continueBtn.setAlignmentX(LEFT_ALIGNMENT);
        continueBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        continueBtn.setFont(new Font("Inter", Font.BOLD, 18));
        continueBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc : 30; background : #FFB300; Foreground : #FFFFFF");

        backBtn = new JButton("BACK");
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setAlignmentX(LEFT_ALIGNMENT);
        backBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        backBtn.setFont(new Font("Inter", Font.BOLD, 18));
        backBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc : 30; background : #FFB300; Foreground : #FFFFFF");
        
        centerPanel.add(meterNumberLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(meterNumberField);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(separator);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(selectAmountLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel2);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(customPanel);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(continueBtn);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(backBtn);
        
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(backgroundPanel);
        
        //Adding Functionality             
        firstAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (firstAmount.isSelected()) {
                    amount = 50;
                }
            }
        });
        
        secondAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (secondAmount.isSelected()) {
                    amount = 100;
                }
            }
        });
        
        thirdAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (thirdAmount.isSelected()) {
                    amount = 150;
                }
            }
        });
        
        customAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (customAmount.isSelected()) {
                    customPanel.setVisible(true);
                    
                } else {
                    customPanel.setVisible(false);
                }
            }
        });
        
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String meterNumber = meterNumberField.getText();
                String Amount = customAmountField.getText();
                

                if (meterNumber.strip().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter your Meter Number", "Meter number field is empty", JOptionPane.OK_OPTION);
                } else if (!firstAmount.isSelected() && !secondAmount.isSelected() && !thirdAmount.isSelected() &&  !customAmount.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Choose or enter your Amount!", "Select one Toggle Button", JOptionPane.OK_OPTION);
                } else if (customAmount.isSelected() && Amount.strip().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter your amount", "Amount cannot be empty", JOptionPane.NO_OPTION);
                } else {
                    
                    if (customAmount.isSelected()) {
                        try{
                        amount = Integer.parseInt(Amount);
                        }catch(NumberFormatException n){
                            JOptionPane.showMessageDialog(null, "Amount should contain only Digits");
                        }
                    }
                    
                    long meter = 0;
                    try {
                        meter = Long.parseLong(meterNumber);
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Meter Number should contain only Digits!");
                    }
                    
                                        
                    paymentPageForElectricity page = new paymentPageForElectricity(meter, "Aphelele Shange", amount);
                    page.setVisible(true);
                    dispose();
                }
            }

        });
        
    }

}
