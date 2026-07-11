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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class paymentPageForElectricity extends JFrame {

    JLabel confirmLabel, purchaseLabel, meterNumberLabel, customerNameLabel, purchaseAmountLabel, serviceFeeLabel, totalAmountLabel;
    JLabel paymentMethodLabel, paymentText, curveSpaceLabel, summaryIconLabel, securityIconLabel, securityText;
    JLabel enteredMeterNumberLabel, userNameLabel, purchasingAmountLabel, serviceFeeChargedLabel, amountToBePaidLabel;
    JRadioButton debit_creditCard, eft, retail_voucherPayments;
    ButtonGroup group;
    JButton backButton, payingButton, buyButton;
    JSeparator separator;
    JPanel backgroundPanel, plainPanel, summaryPanel, separatorPanel, securityPanel, leftPanel, rightPanel, totalPanel, amountPanel;
    JPanel purchaseSummaryPanel, meterNumberPanel, customerNamePanel, purchaseAmountPanel, serviceFeePanel, totalAmountPanel, footerPanel;
    JPanel panelMeterNumber, panelName, panelAmount, panelServiceFee;
    ImageIcon curveSpace, summaryIcon, security;

    public paymentPageForElectricity(long meterNumber, String name, int amount) {

        super();
        this.setTitle("Payment Page for Electricity");
        this.setLayout(new BorderLayout());
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);

        //reshaping the white circular space 
        curveSpace = new ImageIcon("gray-circle.png");
        Image curve = curveSpace.getImage();
        Image resizedCurveSpace = curve.getScaledInstance(484, 83, Image.SCALE_SMOOTH);
        ImageIcon newCurveSpace = new ImageIcon(resizedCurveSpace);

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
        backgroundPanel.setLayout(null);

        curveSpaceLabel = new JLabel(newCurveSpace);
        curveSpaceLabel.setBounds(0, 0, 484, 83);
        curveSpaceLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 80)); //Moves the circle to the down and to the left

        confirmLabel = new JLabel("Confirm & Pay");
        confirmLabel.setFont(new Font("Inter", Font.BOLD, 18));
        confirmLabel.setBounds(137, 9, 150, 30);

        //Initialising the buttons
        backButton = new JButton(new ImageIcon("back.png"));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setToolTipText("Go back to the previous page");
        backButton.setBounds(5, 7, 40, 30);

        //Adding functionality to be back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent A) {
                dispose();

                buyElectricityPage AquaVolt = new buyElectricityPage();
                AquaVolt.setVisible(true);
            }
        });

        buyButton = new JButton(new ImageIcon("buy.png"));
        buyButton.setFocusPainted(false);
        buyButton.setContentAreaFilled(false);
        buyButton.setBorderPainted(false);
        buyButton.setBounds(365, 7, 40, 30);

        payingButton = new JButton("Pay");
        payingButton.setFont(new Font("Inter", Font.BOLD, 20));
        payingButton.setFocusPainted(false);
        //payingButton.setContentAreaFilled(false);
        payingButton.setBorderPainted(false);
        payingButton.setBackground(Color.decode("#FFB300"));
        payingButton.setForeground(Color.WHITE);
        payingButton.setOpaque(true);
        payingButton.setBounds(20, 660, 370, 45);

        //Adding Functionality to the pay button
        payingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent A) {

                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();

                //Formatting time so it shows only hours and minutes a purchase was made
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTime = time.format(formatter);

                if (!debit_creditCard.isSelected() && !retail_voucherPayments.isSelected() && !eft.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Choose payment methods", "Select your payment method", JOptionPane.OK_OPTION);
                } else {

                    /*TransactionHistoryE a = new TransactionHistoryE("Electricity Purchase", String.valueOf(meterNumber), date.toString(), time.toString(), String.valueOf(amount), "Successful");

                    TransactionHistoryE.createFile("Experiment.txt");
                    TransactionHistoryE.writeToFile(a.toString());
                    TransactionHistoryE.closeFile();*/

                TransactionHistoryE.createFile("Transaction_History_for_Electricity.txt");
                TransactionHistoryE.writeToFile("Electricity Purchase" + "#" + "Meter: " + meterNumber + "#" + amount + "#" + date + " - " + formattedTime + "#" + "Successful");
                TransactionHistoryE.closeFile();
                    dispose();
                    ElectricityPaymentSuccessfulPage paid = new ElectricityPaymentSuccessfulPage();
                    paid.setVisible(true);

                }

            }
        });

        plainPanel = new JPanel();
        plainPanel.setLayout(null);
        plainPanel.setBounds(0, 80, 425, 837);

        summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBounds(20, 0, 370, 250);
        summaryPanel.setLayout(new BorderLayout());

        purchaseLabel = new JLabel("Purchase Summary");
        purchaseLabel.setFont(new Font("Inter", Font.BOLD, 20));

        summaryPanel.add(purchaseLabel);

        //setting the summary icon
        summaryIcon = new ImageIcon("summary.png");
        summaryIconLabel = new JLabel(summaryIcon);

        //Adding the summary Icon and the Title to their label
        purchaseSummaryPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        purchaseSummaryPanel.add(summaryIconLabel);
        purchaseSummaryPanel.add(purchaseLabel);
        purchaseSummaryPanel.setOpaque(false);

        //Initialising the panels that will hold the customer's information
        panelMeterNumber = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panelName = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panelAmount = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panelServiceFee = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        //Adding the meter number label to its panel
        meterNumberLabel = new JLabel("Meter Number ");
        meterNumberLabel.setFont(new Font("Inter", Font.PLAIN, 17));
        meterNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        meterNumberPanel.setOpaque(false);
        meterNumberPanel.add(meterNumberLabel);

        enteredMeterNumberLabel = new JLabel(String.valueOf(meterNumber)); //converting the long value into a String since labels only take a String as a parameter
        enteredMeterNumberLabel.setFont(new Font("Inter", Font.PLAIN, 15));

        panelMeterNumber.add(enteredMeterNumberLabel);
        panelMeterNumber.setOpaque(false);

        //Adding the customer name label to its panel
        customerNameLabel = new JLabel("Customer Name");
        customerNameLabel.setFont(new Font("Inter", Font.PLAIN, 17));
        customerNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerNamePanel.setOpaque(false);
        customerNamePanel.add(customerNameLabel);

        userNameLabel = new JLabel(name);
        userNameLabel.setFont(new Font("Inter", Font.PLAIN, 15));

        panelName.add(userNameLabel);
        panelName.setOpaque(false);

        //adding the purchase label to its Panel
        purchaseAmountLabel = new JLabel("Purchase Amount");
        purchaseAmountLabel.setFont(new Font("Inter", Font.PLAIN, 17));
        purchaseAmountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        purchaseAmountPanel.setOpaque(false);
        purchaseAmountPanel.add(purchaseAmountLabel);

        purchasingAmountLabel = new JLabel("R" + String.valueOf(amount)); //converting the int value into a String since labels only take a String as a parameter
        purchasingAmountLabel.setFont(new Font("Inter", Font.PLAIN, 15));

        panelAmount.add(purchasingAmountLabel);
        panelAmount.setOpaque(false);

        //Adding the Service Fee label to its Panel
        serviceFeeLabel = new JLabel("Service Fee");
        serviceFeeLabel.setFont(new Font("Inter", Font.PLAIN, 17));
        serviceFeePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        serviceFeePanel.add(serviceFeeLabel);
        serviceFeePanel.setOpaque(false);

        serviceFeeChargedLabel = new JLabel("R" + 2.50);
        serviceFeeChargedLabel.setFont(new Font("Inter", Font.PLAIN, 15));

        panelServiceFee.add(serviceFeeChargedLabel);
        panelServiceFee.setOpaque(false);

        //Adding the Total amount to its Panel
        totalAmountLabel = new JLabel("Total Amount");
        totalAmountLabel.setFont(new Font("Inter", Font.BOLD, 20));
        totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.add(totalAmountLabel);
        totalPanel.setOpaque(false);

        amountToBePaidLabel = new JLabel("R" + String.valueOf(amount + 2.50));
        amountToBePaidLabel.setFont(new Font("Inter", Font.BOLD, 20));
        amountToBePaidLabel.setForeground(Color.decode("#FFB300"));

        amountPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        amountPanel.add(amountToBePaidLabel);
        amountPanel.setOpaque(false);

        // Setting the separator and adding it to the panel
        separator = new JSeparator();
        separator.setForeground(Color.GRAY);
        separator.setPreferredSize(new Dimension(400, 5));
        separator.setOpaque(true);

        separatorPanel = new JPanel(new FlowLayout());
        separatorPanel.add(separator);
        separatorPanel.setOpaque(false);

        //Initialising My left summary Panel
        leftPanel = new JPanel(new GridLayout(6, 0));
        leftPanel.add(meterNumberPanel);
        leftPanel.add(customerNamePanel);
        leftPanel.add(purchaseAmountPanel);
        leftPanel.add(serviceFeePanel);
        leftPanel.add(separatorPanel);
        leftPanel.add(totalPanel);
        leftPanel.setOpaque(false);

        //Initialising my right summary Panel
        rightPanel = new JPanel(new GridLayout(6, 0));
        rightPanel.add(panelMeterNumber);
        rightPanel.add(panelName);
        rightPanel.add(panelAmount);
        rightPanel.add(panelServiceFee);
        rightPanel.add(separator);
        rightPanel.add(amountPanel);
        rightPanel.setOpaque(false);

        //Adding the leftPanel to the left side of the summary panel and the rightPanel to the right side of the summary panel
        summaryPanel.add(leftPanel, BorderLayout.WEST);
        summaryPanel.add(rightPanel, BorderLayout.EAST);

        //adding labels to the summary panel
        summaryPanel.add(purchaseSummaryPanel, BorderLayout.NORTH);

        //Payment method section
        paymentMethodLabel = new JLabel("Choose Payment Method");
        paymentMethodLabel.setFont(new Font("Inter", Font.BOLD, 20));
        paymentMethodLabel.setBounds(80, 300, 250, 30);

        paymentText = new JLabel("How do you want to pay?");
        paymentText.setFont(new Font("Inter", Font.PLAIN, 15));
        paymentText.setBounds(80, 330, 250, 30);

        //Initialising radio buttons 
        debit_creditCard = new JRadioButton("Debit / Credit Card");
        //debit_creditCard.setBackground(Color.ORANGE);
        debit_creditCard.setBounds(40, 370, 300, 40);
        debit_creditCard.setFocusPainted(false);
        debit_creditCard.setBorderPainted(true);
        debit_creditCard.setFont(new Font("Inter", Font.BOLD, 18));

        eft = new JRadioButton("EFT");
        eft.setBounds(40, 430, 300, 40);
        eft.setBorderPainted(true);
        eft.setFocusPainted(false);
        eft.setFont(new Font("Inter", Font.BOLD, 18));

        retail_voucherPayments = new JRadioButton("Retail / Voucher Payment");
        retail_voucherPayments.setBounds(40, 490, 300, 40);
        retail_voucherPayments.setFocusPainted(false);
        retail_voucherPayments.setBorderPainted(true);
        retail_voucherPayments.setFont(new Font("Inter", Font.BOLD, 18));

        //Initialising button group so we can add radio buttons inside
        group = new ButtonGroup();

        //Adding the radio buttons to the group
        group.add(debit_creditCard);
        group.add(eft);
        group.add(retail_voucherPayments);

        //Default radio button color
        Color defaultRadioButtonColor = eft.getBackground();

        //Registering and adding listeners to the radio buttons
        debit_creditCard.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (debit_creditCard.isSelected()) {
                    debit_creditCard.setBackground(Color.decode("#FFB300"));
                } else {
                    debit_creditCard.setBackground(defaultRadioButtonColor);
                }
            }
        });
        eft.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (eft.isSelected()) {
                    eft.setBackground(Color.decode("#FFB300"));
                } else {
                    eft.setBackground(defaultRadioButtonColor);
                }
            }
        });
        retail_voucherPayments.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent I) {
                if (retail_voucherPayments.isSelected()) {
                    retail_voucherPayments.setBackground(Color.decode("#FFB300"));
                } else {
                    retail_voucherPayments.setBackground(defaultRadioButtonColor);
                }
            }
        });

        //Setting my orange color
        Color orangeColor = new Color(255, 165, 0, 60);

        // Initialising the security icon and its panel
        security = new ImageIcon("cyber-security.png");
        securityIconLabel = new JLabel(security);
        securityText = new JLabel("Your information is safe with us");
        securityText.setFont(new Font("Inter", Font.ITALIC, 14));

        securityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //securityPanel.setPreferredSize(new Dimension(300, 30));
        securityPanel.setBackground(orangeColor);
        securityPanel.add(securityIconLabel);
        securityPanel.add(securityText);
        securityPanel.setBounds(20, 570, 370, 45);

        plainPanel.add(summaryPanel);
        plainPanel.add(paymentMethodLabel);
        plainPanel.add(paymentText);
        plainPanel.add(debit_creditCard);
        plainPanel.add(eft);
        plainPanel.add(retail_voucherPayments);
        plainPanel.add(securityPanel);
        plainPanel.add(payingButton);

        backgroundPanel.add(backButton);
        backgroundPanel.add(curveSpaceLabel);
        backgroundPanel.add(confirmLabel);
        backgroundPanel.add(buyButton);
        backgroundPanel.add(plainPanel);

        add(backgroundPanel, BorderLayout.CENTER);

    }

}
