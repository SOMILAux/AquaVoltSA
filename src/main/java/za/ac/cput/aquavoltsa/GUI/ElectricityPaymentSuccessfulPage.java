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
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class ElectricityPaymentSuccessfulPage extends JFrame{
    
    JPanel backgroundPanel, headerPanel, topPanel, centerPanel, picturePanel, tokenPanel, tokenHolderPanel;
    JLabel paymentSuccessfulLabel, circleLabel, smallCircleLabel, dropIconLabel, tickIconLabel, paymentLabel, purchaseText, tokenLabel, transactionID;
    JLabel ElectricityLabel, text, lightLabel, token;
    ImageIcon homeIcon, HomeIcon, electricityIcon, copyIcon, tickIcon, circle, smallCircleIcon, thunderIcon;
    JButton homeBtn, copyBtn, HomeBtn;
    
    Random rand;
    
    public ElectricityPaymentSuccessfulPage(){
        
        this.setTitle("Electricity Payment Successful Page");
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);
        
        homeIcon = new ImageIcon("home.png");
        HomeIcon = new ImageIcon("home.png");
        electricityIcon = new ImageIcon("thunder.png");
        copyIcon = new ImageIcon("copy.png");
        
        tickIcon = new ImageIcon("check.png");
        Image icon = tickIcon.getImage();
        Image Icon = icon.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon tick = new ImageIcon(Icon);

        circle = new ImageIcon("gray-circle.png");
        Image circleIcon = circle.getImage();
        Image resizedCircle = circleIcon.getScaledInstance(490, 83, Image.SCALE_SMOOTH);
        ImageIcon circular = new ImageIcon(resizedCircle);

        smallCircleIcon = new ImageIcon("circle.png");
        Image icon1 = smallCircleIcon.getImage();
        Image Icon1 = icon1.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon smallCircle = new ImageIcon(Icon1);

        thunderIcon = new ImageIcon("electrical-energy.png");
        Image icon2 = thunderIcon.getImage();
        Image Icon2 = icon2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon light = new ImageIcon(Icon2);
        
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
        
        homeBtn = new JButton();
        homeBtn.setBorderPainted(false);
        homeBtn.setBackground(Color.decode("#FFB300"));
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
        
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(headerPanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(circleLabel);

        headerPanel.add(homeBtn);
        headerPanel.add(paymentSuccessfulLabel);
        
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.decode("#EAEAEA"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        smallCircleLabel = new JLabel(smallCircle);
        smallCircleLabel.setOpaque(false);

        dropIconLabel = new JLabel(electricityIcon);
        dropIconLabel.setOpaque(false);

        tickIconLabel = new JLabel(tick);
        tickIconLabel.setOpaque(false);
        
        lightLabel = new JLabel(light);
        lightLabel.setOpaque(false);

        picturePanel = new JPanel();
        picturePanel.setOpaque(false);
        picturePanel.setLayout(null);
        picturePanel.setAlignmentX(LEFT_ALIGNMENT);
        //picturePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        picturePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        smallCircleLabel.setBounds(120, 0, 120, 120);
        lightLabel.setBounds(130, 12, 100, 100);
        tickIconLabel.setBounds(200, 7, 40, 40);

        picturePanel.add(tickIconLabel);
        picturePanel.add(lightLabel);
        picturePanel.add(smallCircleLabel);
        
        paymentLabel = new JLabel("Payment Successful🎉");
        paymentLabel.setFont(new Font("Inter", Font.BOLD, 20));
        paymentLabel.setAlignmentX(LEFT_ALIGNMENT);
        paymentLabel.setBorder(BorderFactory.createEmptyBorder(0, 75, 0, 0));

        purchaseText = new JLabel("Your purchase was successful");
        purchaseText.setFont(new Font("Arial", Font.PLAIN, 14));
        purchaseText.setAlignmentX(LEFT_ALIGNMENT);
        purchaseText.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));

        tokenPanel = new JPanel(new BorderLayout());
        tokenPanel.setBackground(Color.WHITE);
        tokenPanel.putClientProperty(FlatClientProperties.STYLE, "arc : 25");
        tokenPanel.setMaximumSize(new Dimension(350, 180));
        tokenPanel.setAlignmentX(LEFT_ALIGNMENT);

        tokenLabel = new JLabel("Your Token");
        tokenLabel.setFont(new Font("Inter", Font.BOLD, 20));
        tokenLabel.setBorder(BorderFactory.createEmptyBorder(5, 110, 0, 0));
        
        //Creating an array that will hold 4 numbers that will be used to create a token
        rand = new Random();
        int numbers [] = new int[4];
        
        for(int i = 0; i < 4; i++){
            numbers[i] = rand.nextInt(1000, 9999);
        }
        
        token = new JLabel(String.valueOf(numbers[0]) + " " + String.valueOf(numbers[1]) + " " + String.valueOf(numbers[2]) + " " + String.valueOf(numbers[3]) + " ");
        token.setFont(new Font("Roboto", Font.BOLD, 24));
        token.setForeground(Color.decode("#FFB300"));
        
        tokenHolderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        tokenHolderPanel.add(token);
        tokenHolderPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));// moves the token from the top  to the middle of the panel
        tokenHolderPanel.setOpaque(false);
        
        ElectricityLabel = new JLabel(electricityIcon);
        ElectricityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        text = new JLabel("Thank you for using our services");
        text.setFont(new Font("Arial", Font.PLAIN, 15));
        text.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        
        copyBtn = new JButton("Copy ID");
        copyBtn.setFocusPainted(false);
        copyBtn.setBackground(Color.decode("#EAEAEA"));
        copyBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        copyBtn.setFont(new Font("Inter", Font.BOLD, 16));
        copyBtn.setIcon(copyIcon);
        copyBtn.putClientProperty("FlatLaf.style",
                "borderColor: #FFA500;"
                + "arc: 30;"
                + "borderWidth: 1");

        HomeBtn = new JButton("Back to Home");
        HomeBtn.setFocusPainted(false);
        HomeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        HomeBtn.setFont(new Font("Inter", Font.BOLD, 16));
        HomeBtn.setIcon(HomeIcon);
        HomeBtn.putClientProperty("FlatLaf.style",
                "background: #FFB300;"
                + "arc: 30;"
                + "foreground : #FFFFFF;"
                + "borderWidth : 0");

        tokenPanel.add(tokenLabel, BorderLayout.NORTH);
        tokenPanel.add(tokenHolderPanel, BorderLayout.CENTER);
        tokenPanel.add(ElectricityLabel, BorderLayout.SOUTH);
        
        centerPanel.add(picturePanel);
        centerPanel.add(paymentLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(purchaseText);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(tokenPanel);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(text);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(copyBtn);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(HomeBtn);
        
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        add(backgroundPanel);
        
        
    }

}
