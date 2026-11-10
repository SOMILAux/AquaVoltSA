package za.ac.cput.aquavoltsa.GUI;

import com.formdev.flatlaf.FlatClientProperties;
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
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.swing.FontIcon;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class WaterPaymentSuccessfulPage extends JFrame {

    JPanel backgroundPanel, headerPanel, topPanel, centerPanel, picturePanel, transactionPanel;
    JLabel paymentSuccessfulLabel, circleLabel, tickIconLabel, dropIconLabel, smallCircleLabel, paymentLabel, purchaseText, transactionIDLabel, transactionID, waterDropLabel;
    JLabel text, copyLabel, homeLabel, historyLabel;
    ImageIcon circle, waterIcon, tickIcon, smallCircleIcon;
    JButton homeBtn, copyBtn, HomeBtn, historyBtn;

    public WaterPaymentSuccessfulPage() {

        this.setTitle("Payment Successful Page");
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        
        //=========================
        //ICON SECTION
        //=========================
        
        
        //FontIcon homeIcon = FontIcon.of(FontAwesomeSolid.HOME, 25, Color.BLACK);
        FontIcon copyIcon = FontIcon.of(FontAwesomeSolid.COPY, 25, Color.BLACK);
        FontIcon tintIcon = FontIcon.of(FontAwesomeSolid.TINT, 30, Color.BLUE);
        FontIcon historyIcon = FontIcon.of(FontAwesomeSolid.HISTORY, 30, Color.BLACK);
        FontIcon homeIcon = FontIcon.of(Material2AL.HOME, 35, Color.BLACK);
        FontIcon HomeIcon = FontIcon.of(Material2AL.HOME, 35, Color.BLACK);

        copyLabel = new JLabel(copyIcon);
        homeLabel = new JLabel(HomeIcon);
        historyLabel = new JLabel(historyIcon);

        tickIcon = new ImageIcon("check.png");
        Image icon = tickIcon.getImage();
        Image Icon = icon.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon tick = new ImageIcon(Icon);

        circle = new ImageIcon("circle.png");
        Image circleIcon = circle.getImage();
        Image resizedCircle = circleIcon.getScaledInstance(490, 83, Image.SCALE_SMOOTH);
        ImageIcon circular = new ImageIcon(resizedCircle);

        smallCircleIcon = new ImageIcon("gray-circle.png");
        Image icon1 = smallCircleIcon.getImage();
        Image Icon1 = icon1.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon smallCircle = new ImageIcon(Icon1);

        waterIcon = new ImageIcon("drop.png");
        Image icon2 = waterIcon.getImage();
        Image Icon2 = icon2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon water = new ImageIcon(Icon2);

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

        homeBtn = new JButton();
        homeBtn.setBorderPainted(false);
        homeBtn.setBackground(Color.decode("#00C6FF"));
        homeBtn.setIcon(homeIcon);

        paymentSuccessfulLabel = new JLabel("Payment Successful");
        paymentSuccessfulLabel.setFont(new Font("Arial", Font.BOLD, 18));
        paymentSuccessfulLabel.setBorder(BorderFactory.createEmptyBorder(0, 75, 0, 0));

        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(LEFT_ALIGNMENT);

        circleLabel = new JLabel(circular);

        topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 85));

        topPanel.add(headerPanel);
        topPanel.add(circleLabel);

        headerPanel.add(homeBtn);
        headerPanel.add(paymentSuccessfulLabel);

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.decode("#F8F8FF"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        smallCircleLabel = new JLabel(smallCircle);
        smallCircleLabel.setOpaque(false);

        dropIconLabel = new JLabel(water);
        dropIconLabel.setOpaque(false);

        tickIconLabel = new JLabel(tick);
        tickIconLabel.setOpaque(false);

        picturePanel = new JPanel();
        picturePanel.setOpaque(false);
        picturePanel.setLayout(null);
        picturePanel.setAlignmentX(LEFT_ALIGNMENT);
        //picturePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        picturePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        smallCircleLabel.setBounds(130, 0, 120, 120);
        dropIconLabel.setBounds(140, 7, 100, 100);
        tickIconLabel.setBounds(210, 7, 40, 40);

        picturePanel.add(tickIconLabel);
        picturePanel.add(dropIconLabel);
        picturePanel.add(smallCircleLabel);

        paymentLabel = new JLabel("Payment Successful🎉");
        paymentLabel.setFont(new Font("Inter", Font.BOLD, 20));
        paymentLabel.setAlignmentX(LEFT_ALIGNMENT);
        paymentLabel.setBorder(BorderFactory.createEmptyBorder(0, 85, 0, 0));

        purchaseText = new JLabel("Your purchase was successful");
        purchaseText.setFont(new Font("Arial", Font.PLAIN, 14));
        purchaseText.setAlignmentX(LEFT_ALIGNMENT);
        purchaseText.setBorder(BorderFactory.createEmptyBorder(0, 90, 0, 0));

        transactionPanel = new JPanel(new BorderLayout());
        transactionPanel.setBackground(Color.WHITE);
        transactionPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 25");
        transactionPanel.setMaximumSize(new Dimension(350, 180));
        transactionPanel.setAlignmentX(LEFT_ALIGNMENT);

        transactionIDLabel = new JLabel("Transaction ID");
        transactionIDLabel.setFont(new Font("Inter", Font.BOLD, 20));
        transactionIDLabel.setBorder(BorderFactory.createEmptyBorder(5, 110, 0, 0));

        //Creating the Transaction ID
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("'WTR'-yyyy-MM-dd-HH-mm-ss");

        String TransactionId = format.format(date);

        transactionID = new JLabel(TransactionId);
        transactionID.setFont(new Font("Inter", Font.BOLD, 20));
        transactionID.setForeground(Color.decode("#00C6FF"));
        transactionID.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        waterDropLabel = new JLabel(tintIcon);
        waterDropLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        text = new JLabel("Thank you for using our services");
        text.setFont(new Font("Arial", Font.PLAIN, 15));
        text.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        copyBtn = new JButton("Copy ID");
        copyBtn.setLayout(new BorderLayout());
        copyBtn.setFocusPainted(false);
        copyBtn.setBackground(Color.decode("#EAEAEA"));
        copyBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        copyBtn.setFont(new Font("Inter", Font.BOLD, 16));
        copyBtn.putClientProperty("FlatLaf.style",
                "borderColor: #11DDFD;"
                + "arc: 30;"
                + "borderWidth: 1");
        copyBtn.add(copyLabel, BorderLayout.WEST);

        HomeBtn = new JButton("Back to Home");
        HomeBtn.setLayout(new BorderLayout());
        HomeBtn.setFocusPainted(false);
        HomeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        HomeBtn.setFont(new Font("Inter", Font.BOLD, 16));
        HomeBtn.putClientProperty("FlatLaf.style",
                "background: #00C6FF;"
                + "arc: 30;"
                + "foreground : #FFFFFF;"
                + "borderWidth : 0");
        HomeBtn.add(homeLabel, BorderLayout.WEST);
        
        historyBtn = new JButton("View History");
        historyBtn.setLayout(new BorderLayout());
        historyBtn.setFocusPainted(false);
        historyBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        historyBtn.setFont(new Font("Inter", Font.BOLD, 16));
        historyBtn.putClientProperty("FlatLaf.style",
                "background: #00C6FF;"
                + "arc: 30;"
                + "foreground : #FFFFFF;"
                + "borderWidth : 0");
        historyBtn.add(historyLabel, BorderLayout.WEST);

        transactionPanel.add(transactionIDLabel, BorderLayout.NORTH);
        transactionPanel.add(transactionID, BorderLayout.CENTER);
        transactionPanel.add(waterDropLabel, BorderLayout.SOUTH);

        centerPanel.add(picturePanel);
        centerPanel.add(paymentLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(purchaseText);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(transactionPanel);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(text);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(copyBtn);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(HomeBtn);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(historyBtn);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        add(backgroundPanel);
        
        //======================
        //Functionality
        //======================
        
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                DashboardFrame AquaVoltSA = new DashboardFrame();
                AquaVoltSA.setVisible(true);
                dispose();
            }
        });
        
        HomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                DashboardFrame AquaVoltSA = new DashboardFrame();
                AquaVoltSA.setVisible(true);
                dispose();
            }
        });
        
        copyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                StringSelection selection = new StringSelection(TransactionId);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                JOptionPane.showMessageDialog(null, "ID was copied successfully");
            }
        });
        
        historyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                TransactionHistoryForWaterPage AquaVoltSA = new TransactionHistoryForWaterPage();
                AquaVoltSA.setVisible(true);
                dispose();
                
            }
        });

    }

}
