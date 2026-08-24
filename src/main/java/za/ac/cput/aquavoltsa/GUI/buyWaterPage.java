package za.ac.cput.aquavoltsa.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.formdev.flatlaf.*;
import java.awt.Insets;
import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class buyWaterPage extends JFrame {

    ImageIcon logo, circle;
    JLabel logoLabel, circleLabel, meterNumberLabel, addressLabel, selectAmountLabel, currencyLabel;
    JPanel backgroundPanel, headerPanel, centerPanel, buttonPanel, customPanel;
    JTextField meterNumberField, addressField, customAmountField;
    JSeparator separator;
    JToggleButton firstAmount, customAmount;
    ButtonGroup group;
    JButton continueBtn, backBtn;

    int amount;

    public buyWaterPage() {

        this.setTitle("Buying Water");
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);

        backgroundPanel = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                Color firstElectricityColor = Color.decode("#00C6FF");
                Color secondElectricityColor = Color.decode("#C2E9FB");

                GradientPaint gp = new GradientPaint(0, 0, firstElectricityColor, getWidth(), getHeight(), secondElectricityColor);

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

        };
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setPreferredSize(new Dimension(425, 917));

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
        meterNumberField.putClientProperty(FlatClientProperties.STYLE,
                "arc: 20");
        meterNumberField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your Meter Number");
        meterNumberField.setMargin(new Insets(0, 10, 0, 5));

        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addressLabel.setAlignmentX(LEFT_ALIGNMENT);

        addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 20));
        addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addressField.setAlignmentX(LEFT_ALIGNMENT);
        addressField.putClientProperty(FlatClientProperties.STYLE,
                "arc: 20");
        addressField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your Address");
        addressField.setMargin(new Insets(0, 10, 0, 5));

        separator = new JSeparator();
        separator.setPreferredSize(new Dimension(425, 2));
        separator.setForeground(Color.decode("#00C6FF"));
        separator.setAlignmentX(LEFT_ALIGNMENT);

        selectAmountLabel = new JLabel("Select Amount");
        selectAmountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        selectAmountLabel.setAlignmentX(LEFT_ALIGNMENT);

        firstAmount = new JToggleButton("R250");
        firstAmount.setBorderPainted(false);
        firstAmount.setFocusPainted(false);
        firstAmount.setPreferredSize(new Dimension(350, 40));
        firstAmount.setMaximumSize(new Dimension(150, 50));
        firstAmount.setFont(new Font("Inter", Font.BOLD, 18));
        firstAmount.putClientProperty(FlatClientProperties.STYLE, "arc: 30; selectedBackground: #00C6FF; selectedForeground: #FFFFFF");

        customAmount = new JToggleButton("CUSTOM");
        customAmount.setBorderPainted(false);
        customAmount.setFocusPainted(false);
        customAmount.setPreferredSize(new Dimension(350, 40));
        customAmount.setMaximumSize(new Dimension(150, 50));
        customAmount.setFont(new Font("Inter", Font.BOLD, 18));
        customAmount.putClientProperty(FlatClientProperties.STYLE, "arc: 30; selectedBackground: #00C6FF; selectedForeground: #FFFFFF");

        group = new ButtonGroup();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.setOpaque(false);

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

        continueBtn = new JButton("CONTINUE");
        continueBtn.setBorderPainted(false);
        continueBtn.setFocusPainted(false);
        continueBtn.setAlignmentX(LEFT_ALIGNMENT);
        continueBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        continueBtn.setFont(new Font("Inter", Font.BOLD, 18));
        continueBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc : 30; background : #00C6FF; Foreground : #FFFFFF");

        backBtn = new JButton("BACK");
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setAlignmentX(LEFT_ALIGNMENT);
        backBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        backBtn.setFont(new Font("Inter", Font.BOLD, 18));
        backBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc : 30; background : #00C6FF; Foreground : #FFFFFF");

        customPanel.add(currencyLabel, BorderLayout.WEST);
        customPanel.add(customAmountField, BorderLayout.CENTER);

        group.add(firstAmount);
        group.add(customAmount);

        buttonPanel.add(firstAmount);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(customAmount);

        centerPanel.add(meterNumberLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(meterNumberField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(addressLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(addressField);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(separator);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(selectAmountLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(customPanel);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(continueBtn);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(backBtn);

        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        add(backgroundPanel);
        
       // DAO.createPaymentTable();

        //Adding Functionality             
        firstAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (firstAmount.isSelected()) {
                    amount = 250;
                }
            }
        });

        customAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (customAmount.isSelected()) {
                    customPanel.setVisible(true);

                    String cAmount = customAmountField.getText();
                    
                } else {
                    customPanel.setVisible(false);
                }
            }
        });

        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String meterNumber = meterNumberField.getText();
                String address = addressField.getText();
                String Amount = customAmountField.getText();
                

                if (meterNumber.strip().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter your Meter Number", "Meter number field is empty", JOptionPane.OK_OPTION);
                } else if (address.strip().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter your Address", "Address field is empty", JOptionPane.OK_OPTION);
                } else if (!firstAmount.isSelected() && !customAmount.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Choose or enter your Amount!", "Select one Toggle Button", JOptionPane.OK_OPTION);
                } else if (customAmount.isSelected() && Amount.strip().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter your amount", "Amount cannot be empty", JOptionPane.NO_OPTION);
                } else {
                    
                    if (customAmount.isSelected()) {
                        amount = Integer.parseInt(Amount);
                    }
                    
                    long meter = 0;
                    try {
                        meter = Long.parseLong(meterNumber);
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Cannot convert Meter Number");
                    }
                    
                                        
                    PaymentPageForWater page = new PaymentPageForWater(meter, "Aphelele Shange", address, amount, 2.50);
                    page.setVisible(true);
                    dispose();
                }
            }

        });

    }

}
