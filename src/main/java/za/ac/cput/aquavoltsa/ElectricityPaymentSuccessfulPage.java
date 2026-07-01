package za.ac.cput.aquavoltsa;

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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;

/**
 *Student Number: 251239853
 * @author Aphelele Shange
 */
public class ElectricityPaymentSuccessfulPage extends JFrame{
    
    JPanel backgroundPanel, plainPanel, tokenPanel, tokenTitlePanel, tokenHolderPanel;
    JLabel title, successfulLabel, text, tokenText, tokenTitle, token, copyIconLabel, homeIconLabel;
    JLabel whiteCircleLabel, circleLabel, electricityIconLabel, tickIconLabel, thunderIconLabel;
    JButton homeButton, copyButton, backToHomeButton;
    ImageIcon curveSpace, circularIcon, electricityIcon, tickIcon, thunderIcon, copyIcon, homeIcon;
    
    Random rand;
    
    public ElectricityPaymentSuccessfulPage(){
        
        super();
        this.setTitle("Buy Electricity");
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);
        this.setTitle("Electricity Payment Successful Page");
        this.setLayout(new BorderLayout());
        
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
        
        backgroundPanel.setLayout(null);
        
        homeButton = new JButton(new ImageIcon("home.png"));
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setToolTipText("Go to home page");
        homeButton.setBounds(20, 15, 32, 32);
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent A){
                
                dispose();
                buyElectricityPage buy = new buyElectricityPage();
                buy.setVisible(true);
                
            }
        });
        
        title = new JLabel("Payment Successful");
        title.setBounds(140, 18, 150, 30);
        
        //reshaping the white circular space under the logo
        curveSpace = new ImageIcon("gray-circle.png");
        Image curve = curveSpace.getImage();
        Image resizedCurveSpace = curve.getScaledInstance(484, 83, Image.SCALE_SMOOTH);
        ImageIcon newCurveSpace = new ImageIcon(resizedCurveSpace);
        
        //Adding the circle to the Label
        whiteCircleLabel = new JLabel(newCurveSpace);
        whiteCircleLabel.setBounds(0, 0, 484, 83);
        whiteCircleLabel.setBorder(BorderFactory.createEmptyBorder(110, -70, 0, 0)); //Moves the circle to the top and to the left
        
        plainPanel = new JPanel();
        plainPanel.setLayout(null);
        //plainPanel.setBackground(Color.red);
        plainPanel.setBounds(0, 80, 425, 837);
        
        //adding and scaling the circular icon
        circularIcon = new ImageIcon("gray-circle.png");
        Image image = circularIcon.getImage();
        Image sizedImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon Icon = new ImageIcon(sizedImage);
        
        circleLabel = new JLabel(Icon);
        circleLabel.setBounds(130, 30, 120, 120);
        
        //adding and scaling the electricity icon
        electricityIcon = new ImageIcon("electrical-energy.png");
        Image eleIcon = electricityIcon.getImage();
        Image ele = eleIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon electricity = new ImageIcon(ele);
        
        electricityIconLabel = new JLabel(electricity);
        electricityIconLabel.setBounds(140, 44, 100, 100);
        
        //adding and scaling the Tick icon
        tickIcon = new ImageIcon("check.png");
        Image tick = tickIcon.getImage();
        Image iconTick = tick.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon TickIcon = new ImageIcon(iconTick);
        
        tickIconLabel = new JLabel(TickIcon);
        tickIconLabel.setBounds(215, 35, 40, 40);
        
        successfulLabel = new JLabel("Payment Successful🎉");
        successfulLabel.setFont(new Font("Inter", Font.BOLD, 24));
        successfulLabel.setForeground(Color.BLACK);
        successfulLabel.setBounds(70, 180, 270, 30);
        
        text = new JLabel("Your electricity purchase was Successful.");
        text.setFont(new Font("Inter", Font.PLAIN, 14));
        text.setForeground(Color.GRAY);
        text.setBounds(70, 220, 280, 30);
        
        //Initialising the token panel
        tokenPanel = new JPanel(new BorderLayout());
        tokenPanel.setBackground(Color.CYAN);
        tokenPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        tokenPanel.setBounds(29, 280, 350, 200);
        
        tokenTitle = new JLabel("Your Token");
        tokenTitle.setFont(new Font("Roboto", Font.BOLD, 17));
        
        tokenTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tokenTitlePanel.setOpaque(false);
        tokenTitlePanel.add(tokenTitle);
        
        //Creating an array that will hold 4 numbers that will be used to create a token
        rand = new Random();
        int numbers [] = new int[4];
        
        for(int i = 0; i < 4; i++){
            numbers[i] = rand.nextInt(1000, 9999);
        }
        
        token = new JLabel(String.valueOf(numbers[0]) + " " + String.valueOf(numbers[1]) + " " + String.valueOf(numbers[2]) + " " + String.valueOf(numbers[3]) + " ");
        token.setFont(new Font("Roboto", Font.BOLD, 24));
        
        tokenHolderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tokenHolderPanel.add(token);
        tokenHolderPanel.setBorder(BorderFactory.createEmptyBorder(45, 0, 0, 0));// moves the token to the middle of the panel
        tokenHolderPanel.setOpaque(false);
        
        //setting the thunder Icon
        thunderIcon = new ImageIcon("thunder.png");
        
        thunderIconLabel = new JLabel(thunderIcon);
        thunderIconLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        //
        tokenText = new JLabel("Enter this token to load electricity");
        tokenText.setFont(new Font("Inter", Font.PLAIN, 17));
        tokenText.setBounds(80, 500, 250, 30);
        
        //Initialising buttons
        copyButton = new JButton("Copy Token");
        copyButton.setLayout(null);
        copyButton.setBackground(new Color(238, 238, 238));
        copyButton.setForeground(Color.BLACK);
        copyButton.setOpaque(false);
        copyButton.setFont(new Font("Inter", Font.BOLD, 18));
        copyButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        copyButton.setFocusPainted(false);
        copyButton.setBounds(30, 560, 330, 40);
        
        //setting the copy icon
        copyIcon = new ImageIcon("copy.png");
        copyIconLabel = new JLabel(copyIcon);
        copyIconLabel.setBounds(40, 6, 32, 32);
                     
        copyButton.add(copyIconLabel);
        
        
        backToHomeButton = new JButton("Back to Home");
        backToHomeButton.setLayout(null);
        backToHomeButton.setFocusPainted(false);
        backToHomeButton.setBackground(Color.decode("#FFB300"));
        backToHomeButton.setForeground(Color.WHITE);
        backToHomeButton.setFont(new Font("Inter", Font.BOLD, 18));
        backToHomeButton.setBounds(30, 680, 330, 45);
        
        //Adding functionality to the back to home button
        backToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent A){
                
                dispose();
                buyElectricityPage buy = new buyElectricityPage();
                buy.setVisible(true);
                
            }
        });
        
        //setting the home Icon
        homeIcon = new ImageIcon("home.png");
        homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setBounds(40, 7, 32, 32);
        
        backToHomeButton.add(homeIconLabel);
                
        //Adding components to the token panel
        tokenPanel.add(tokenTitlePanel, BorderLayout.NORTH);
        tokenPanel.add(tokenHolderPanel, BorderLayout.CENTER);
        tokenPanel.add(thunderIconLabel, BorderLayout.SOUTH);
        
        plainPanel.add(tickIconLabel);
        plainPanel.add(electricityIconLabel);
        plainPanel.add(circleLabel);
        plainPanel.add(successfulLabel);
        plainPanel.add(text);
        plainPanel.add(tokenPanel);
        plainPanel.add(tokenText);
        plainPanel.add(copyButton);
        plainPanel.add(backToHomeButton);
        
        
        backgroundPanel.add(homeButton);
        backgroundPanel.add(title);
        backgroundPanel.add(whiteCircleLabel);
        backgroundPanel.add(plainPanel);
        
        add(backgroundPanel, BorderLayout.CENTER);
                
    }
    
}
