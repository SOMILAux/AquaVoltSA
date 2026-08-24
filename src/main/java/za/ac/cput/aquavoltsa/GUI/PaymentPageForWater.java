package za.ac.cput.aquavoltsa.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.formdev.flatlaf.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import za.ac.cput.aquavoltsa.DAO.DAO;
import za.ac.cput.aquavoltsa.Domain.Payment;
import za.ac.cput.aquavoltsa.Domain.Transaction;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class PaymentPageForWater extends JFrame {

    JPanel backgroundPanel, headerPanel, centerPanel, topPanel, summaryPanel, topSummaryPanel, leftSummaryPanel, rightSummaryPanel, leftMidSummaryPanel, rightMidSummaryPanel;
    JPanel meterNumberPanel, meterNumberValue, customerNamePanel, customerNameValue, addressPanel, addressValue, purchaseAmountPanel, purchaseAmountValue, serviceFeePanel, serviceFeeValue;
    JPanel totalPanel, totalValuePanel, panel1, panel2, panel3, messagePanel;
    JLabel confirmLabel, circleLabel, purchaseSummaryLabel, meterNumberLabel, customerNameLabel, addressLabel, purchaseAmountLabel, serviceFeeLabel;
    JLabel meterLabel, nameLabel, addressLabelValue, amountLabel, serviceFee, totalLabel, totalValueLabel;
    JLabel summaryIconLabel, paymentMethodLabel, paymentText, secureIconLabel;
    JButton backBtn, buyBtn, payBtn;
    ImageIcon circle, summaryIcon, secureIcon;
    JRadioButton debit, eft, retail;
    ButtonGroup group;

    String paymentMethod;

    public PaymentPageForWater(long meterNumber, String name, String address, int amount, double fee) {

        this.setTitle("Verification Page");
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

        //Images and Icons
        circle = new ImageIcon("circle.png");
        Image circleIcon = circle.getImage();
        Image resizedCircle = circleIcon.getScaledInstance(490, 83, Image.SCALE_SMOOTH);
        ImageIcon circular = new ImageIcon(resizedCircle);

        summaryIcon = new ImageIcon("summary.png");

        secureIcon = new ImageIcon("secure.png");

        circleLabel = new JLabel(circular);
        circleLabel.setAlignmentX(LEFT_ALIGNMENT);

        secureIconLabel = new JLabel(secureIcon);
        secureIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(LEFT_ALIGNMENT);

        confirmLabel = new JLabel("Confirm & Pay");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 20));

        backBtn = new JButton(new ImageIcon("back.png"));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setBackground(Color.decode("#00C6FF"));

        buyBtn = new JButton(new ImageIcon("buy.png"));
        buyBtn.setFocusPainted(false);
        buyBtn.setBorderPainted(false);
        buyBtn.setBackground(Color.decode("#00C6FD"));

        headerPanel.add(Box.createHorizontalStrut(12));
        headerPanel.add(backBtn);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(confirmLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(buyBtn);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
        //centerPanel.setOpaque(false);
        centerPanel.setBackground(Color.WHITE);

        /*contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        contentPanel.setBackground(Color.WHITE);*/
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 79)); //hides half of the circular image

        summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setMaximumSize(new Dimension(370, 250));
        summaryPanel.setAlignmentX(LEFT_ALIGNMENT);
        summaryPanel.setBackground(Color.CYAN);
        summaryPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");

        summaryIconLabel = new JLabel(summaryIcon);

        purchaseSummaryLabel = new JLabel("Purchase Summary");
        purchaseSummaryLabel.setFont(new Font("Inter", Font.BOLD, 20));

        topSummaryPanel = new JPanel(new BorderLayout(10, 0));
        topSummaryPanel.setOpaque(false);
        topSummaryPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 10, 3));

        topSummaryPanel.add(summaryIconLabel, BorderLayout.WEST);
        topSummaryPanel.add(purchaseSummaryLabel, BorderLayout.CENTER);

        leftSummaryPanel = new JPanel(new BorderLayout());
        rightSummaryPanel = new JPanel(new BorderLayout());

        meterNumberLabel = new JLabel("Meter Number");
        customerNameLabel = new JLabel("Customer Name");
        addressLabel = new JLabel("Address");
        purchaseAmountLabel = new JLabel("Purchase Amount");
        serviceFeeLabel = new JLabel("Service Fee");
        totalLabel = new JLabel("Total Amount");

        meterNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        meterNumberPanel.setOpaque(false);
        meterNumberPanel.add(meterNumberLabel);

        customerNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerNamePanel.setOpaque(false);
        customerNamePanel.add(customerNameLabel);

        addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addressPanel.setOpaque(false);
        addressPanel.add(addressLabel);

        purchaseAmountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        purchaseAmountPanel.setOpaque(false);
        purchaseAmountPanel.add(purchaseAmountLabel);

        serviceFeePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        serviceFeePanel.setOpaque(false);
        serviceFeePanel.add(serviceFeeLabel);

        totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setOpaque(false);
        totalPanel.add(totalLabel);
        //
        //adding all labels to a panel
        leftMidSummaryPanel = new JPanel();
        leftMidSummaryPanel.setLayout(new BoxLayout(leftMidSummaryPanel, BoxLayout.Y_AXIS));
        leftMidSummaryPanel.setOpaque(false);

        rightMidSummaryPanel = new JPanel();
        rightMidSummaryPanel.setLayout(new BoxLayout(rightMidSummaryPanel, BoxLayout.Y_AXIS));
        rightMidSummaryPanel.setOpaque(false);

        meterLabel = new JLabel(String.valueOf(meterNumber));
        nameLabel = new JLabel(name);
        addressLabelValue = new JLabel(address);
        amountLabel = new JLabel("R" + String.valueOf(amount));
        serviceFee = new JLabel("R" + String.valueOf(fee));
        totalValueLabel = new JLabel("R" + String.valueOf(amount + fee));

        meterNumberValue = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        meterNumberValue.setOpaque(false);
        meterNumberValue.add(meterLabel);

        customerNameValue = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        customerNameValue.setOpaque(false);
        customerNameValue.add(nameLabel);

        addressValue = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        addressValue.setOpaque(false);
        addressValue.add(addressLabelValue);

        purchaseAmountValue = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        purchaseAmountValue.setOpaque(false);
        purchaseAmountValue.add(amountLabel);

        serviceFeeValue = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        serviceFeeValue.setOpaque(false);
        serviceFeeValue.add(serviceFee);

        totalValuePanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        totalValuePanel.setOpaque(false);
        totalValuePanel.add(totalValueLabel);

        paymentMethodLabel = new JLabel("Choose Payment Method");
        paymentMethodLabel.setFont(new Font("Inter", Font.BOLD, 19));
        paymentMethodLabel.setAlignmentX(LEFT_ALIGNMENT);
        paymentMethodLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        paymentText = new JLabel("How do you want to pay?");
        paymentText.setFont(new Font("Arial", Font.PLAIN, 15));
        paymentText.setAlignmentX(LEFT_ALIGNMENT);
        paymentText.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        debit = new JRadioButton("Debit / Credit Card");
        debit.setAlignmentX(LEFT_ALIGNMENT);

        eft = new JRadioButton("EFT");
        eft.setAlignmentX(LEFT_ALIGNMENT);

        retail = new JRadioButton("Retail / Voucher Payments");
        retail.setAlignmentX(LEFT_ALIGNMENT);
               
        panel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel1.putClientProperty(FlatClientProperties.STYLE, "arc : 15");
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel2.putClientProperty(FlatClientProperties.STYLE, "arc : 15");
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panel3 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel3.putClientProperty(FlatClientProperties.STYLE, "arc : 15");
        panel3.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        group = new ButtonGroup();

        messagePanel = new JPanel(new BorderLayout());
        messagePanel.setAlignmentX(LEFT_ALIGNMENT);
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        messagePanel.putClientProperty(FlatClientProperties.STYLE, "arc : 20");
        messagePanel.setBackground(new Color(0, 198, 255, 80));

        payBtn = new JButton("Pay");
        payBtn.setFont(new Font("Inter", Font.BOLD, 17));
        payBtn.setFocusPainted(false);
        payBtn.setAlignmentX(LEFT_ALIGNMENT);
        payBtn.setBackground(Color.decode("#00c6ff"));
        payBtn.setForeground(Color.decode("#FFFFFF"));
        payBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        payBtn.putClientProperty(FlatClientProperties.STYLE, "arc : 25");

        messagePanel.add(new JLabel("Your Information is safe with us"), BorderLayout.CENTER);
        messagePanel.add(secureIconLabel, BorderLayout.WEST);

        panel1.add(debit);
        panel1.setAlignmentX(LEFT_ALIGNMENT);

        panel2.add(eft);
        panel2.setAlignmentX(LEFT_ALIGNMENT);

        panel3.add(retail);
        panel3.setAlignmentX(LEFT_ALIGNMENT);

        group.add(debit);
        group.add(eft);
        group.add(retail);

        rightMidSummaryPanel.add(meterNumberValue);
        rightMidSummaryPanel.add(customerNameValue);
        rightMidSummaryPanel.add(addressValue);
        rightMidSummaryPanel.add(purchaseAmountValue);
        rightMidSummaryPanel.add(serviceFeeValue);
        rightMidSummaryPanel.add(totalValuePanel);

        leftMidSummaryPanel.add(meterNumberPanel);
        leftMidSummaryPanel.add(customerNamePanel);
        leftMidSummaryPanel.add(addressPanel);
        leftMidSummaryPanel.add(purchaseAmountPanel);
        leftMidSummaryPanel.add(serviceFeePanel);
        leftMidSummaryPanel.add(totalPanel);

        summaryPanel.add(topSummaryPanel, BorderLayout.NORTH);
        summaryPanel.add(leftMidSummaryPanel, BorderLayout.WEST);
        summaryPanel.add(rightMidSummaryPanel, BorderLayout.EAST);

        centerPanel.add(summaryPanel);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(paymentMethodLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(paymentText);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(panel1);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(panel2);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(panel3);
        centerPanel.add(Box.createVerticalStrut(55));
        centerPanel.add(messagePanel);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(payBtn);

        topPanel.add(headerPanel);
        topPanel.add(circleLabel);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        add(backgroundPanel);

        //Functionality for the buttons
        payBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (debit.isSelected()) {
                    paymentMethod = debit.getText();
                } else if (eft.isSelected()) {
                    paymentMethod = eft.getText();
                } else if (retail.isSelected()) {
                    paymentMethod = retail.getText();
                }

                if (!debit.isSelected() && !eft.isSelected() && !retail.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Choose Payment Method");
                } else {

                    int userID = 1; //userID does not vary
                    int utilityID = 202; //When purchasing water the utility id will always be 202

                    //==================================
                    //Getting the purchase Date and Time                    
                    LocalDate date = LocalDate.now();
                    LocalTime time = LocalTime.now();

                    //==================================
                    //Creating payment
                    Payment payment = new Payment(userID, utilityID, amount, fee, paymentMethod, date, time, "Succesful");
                    int paymentID = DAO.addPayment(payment);

                    payment.setPaymentID(paymentID);

                    //==================================
                    //Creating my Transaction ID
                    Date date2 = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("'WTR'-yyyy-MM-dd-HH-mm-ss");

                    String TransactionId = format.format(date2);

                    Transaction tr = new Transaction(TransactionId, paymentID, utilityID);
                    DAO.addTransaction(tr);

                    WaterPaymentSuccessfulPage ap = new WaterPaymentSuccessfulPage();
                    ap.setVisible(true);
                    dispose();
                }
            }

        });
    }

}
