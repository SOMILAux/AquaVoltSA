/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
import org.kordamp.ikonli.swing.FontIcon;


/**
 *
 * @author Shuma
 */
public class DashboardFrame extends JFrame {
 public DashboardFrame(){
        
    setTitle("AquaVoltSA Homepage"); 
    setSize(425, 917);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new BorderLayout());

    //Content Panel
    JPanel contentPanel = new JPanel(new BorderLayout());
    
       //contentPanel.setBorder(BorderFactory.createEmptyBorder());
       
    //header banner
      /* JPanel headerPanel = new JPanel(new BorderLayout());
       headerPanel.setBackground(Color.WHITE);*/
      
       ImageIcon logoheader = new ImageIcon("image 8.png");
       JLabel imageHeader = new JLabel(logoheader);
       contentPanel.add(imageHeader, BorderLayout.NORTH);
       
      //middlePanel 
       JPanel middlePanel = new JPanel();
       middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
       middlePanel.setBackground(Color.WHITE);
       
    //profile
       JPanel userProfile = new JPanel(new BorderLayout());
       userProfile.setBackground(Color.WHITE);
      // userProfile.setOpaque(false);
       userProfile.setMaximumSize(new Dimension(420,50));
       userProfile.setPreferredSize(new Dimension(400,50));
       
       ImageIcon profileIcon = new ImageIcon("C:/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/java/Resources/user 1.png");
       Image scaledProfile = profileIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

       JButton profileButton = new JButton("Username", new ImageIcon(scaledProfile));
       profileButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
       profileButton.setBorderPainted(false);
       profileButton.setContentAreaFilled(false);
       profileButton.setFocusPainted(false);
       profileButton.setHorizontalAlignment(SwingConstants.LEFT);
       
       profileButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "User profile selected"));
       userProfile.add(profileButton,BorderLayout.WEST);
       /*JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
       leftPanel.setOpaque(false);*/
       
       JLabel profileLabel = new JLabel(new ImageIcon(scaledProfile));
       
       JLabel usernameLabel = new JLabel("Username");
       usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
       
    /*   leftPanel.add(profileLabel);
       leftPanel.add(usernameLabel);*/
    //Settings panel 
    
       FontIcon settings = FontIcon.of(Material2MZ.SETTINGS, 30, Color.BLACK);
       
       
       JButton settingsButton = new JButton();
       settingsButton.setIcon(settings);
       settingsButton.setBorderPainted(false);
       settingsButton.setContentAreaFilled(false);
       settingsButton.setFocusPainted(false);
       
       settingsButton.addActionListener(e-> JOptionPane.showMessageDialog(this, "Settings selected"));
       userProfile.add(settingsButton, BorderLayout.EAST);
       
       //JLabel gearLabel = new JLabel(new ImageIcon(scaledGear));
       
      /* JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,0));
       rightPanel.setOpaque(false);
       rightPanel.add(gearLabel);*/
       
    //Water section
       GaugePanel waterPanel = new GaugePanel(
       "Water", new Color(0,180,90),true,
       "1. Average", " Usage",
       "2. 179L"," Used"," Today");
    //water details
        JPanel waterDetails = createDetailsPanel("Water Usage History", 
        new int[]{420,510,460,620},"Pay Water Bills","Water history");
        waterDetails.setVisible(false);
        
        waterPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        waterPanel.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            waterDetails.setVisible(!waterDetails.isVisible());
             middlePanel.revalidate();
             middlePanel.repaint();
             SwingUtilities.invokeLater(() ->{ middlePanel.revalidate(); middlePanel.repaint();});
        }
        });
 
     /*  userProfile.add(leftPanel, BorderLayout.WEST);
       userProfile.add(rightPanel, BorderLayout.EAST);*/
      
    //Electricity section 
       GaugePanel electricityPanel = new GaugePanel(
       "Electricity", new Color(225,195,0), false,
       "1. Running"," low",
       "2. 29kWh"," Used"," today");
    //Electricity details
    JPanel electricityDetails = createDetailsPanel(
    "Electricity Usage History", new int[]{620,580,650,610},
    "Buy Electricity","Electricity history");
    electricityDetails.setVisible(false);
    electricityPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
    electricityPanel.addMouseListener(new MouseAdapter(){
     @Override
     public void mouseClicked(MouseEvent e){
     electricityDetails.setVisible(!electricityDetails.isVisible());
     middlePanel.revalidate();
     middlePanel.repaint();
     SwingUtilities.invokeLater(() ->{ middlePanel.revalidate(); middlePanel.repaint();});
     }
    });
 
       JButton exitButton = new JButton("Exit");
       exitButton.setFont(new Font("Arial", Font.BOLD, 20));
       exitButton.setForeground(Color.WHITE);
       exitButton.setBackground(new Color(76, 153, 223));
       exitButton.setFocusPainted(false);
       exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       exitButton.setMaximumSize(new Dimension(100,36));
       exitButton.addActionListener(e ->{new LoginSignupPage().setVisible(true);
       this.dispose();
       });
       
     
      middlePanel.add(userProfile);
      middlePanel.add(waterPanel);
      middlePanel.add(waterDetails);
      middlePanel.add(electricityPanel);
      middlePanel.add(electricityDetails);
      middlePanel.add(Box.createVerticalStrut(10));
      middlePanel.add(exitButton);
      middlePanel.add(Box.createVerticalStrut(15));
      
   //Scrollbarsection   
      JScrollPane scrollPane = new JScrollPane(middlePanel);
      scrollPane.setBorder(BorderFactory.createEmptyBorder());
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.getVerticalScrollBar().setUnitIncrement(16);
      contentPanel.add(scrollPane, BorderLayout.CENTER);
      
       
       setContentPane(contentPanel);
       setLocationRelativeTo(null);
     
       JPanel footerPanel = new JPanel(new BorderLayout());
       footerPanel.setBackground(Color.WHITE);
       ImageIcon logofooter = new ImageIcon("C:/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/java/Resources/image 1.png");
       Image scaledImage = logofooter.getImage().getScaledInstance(412,120, Image.SCALE_SMOOTH);
       logofooter = new ImageIcon(scaledImage);
       JLabel imageFooter = new JLabel(logofooter);
       contentPanel.add(imageFooter, BorderLayout.SOUTH);
       
        setVisible(true);
    }


    private JPanel createDetailsPanel(String chartTitle, int[]monthlyValues, String paymentButtonText, String historyButtonText){
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel,BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.setMaximumSize(new Dimension(350,260));
        detailsPanel.setPreferredSize(new Dimension(350,260));
        JLabel titleLabel = new JLabel(chartTitle);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(titleLabel);
        
        UsageChartPanel chartPanel = new UsageChartPanel(monthlyValues);
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(chartPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton paymentButton = new JButton(paymentButtonText);
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setBackground(new Color(0, 123, 255));
        paymentButton.setFocusPainted(false);
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paymentButtonText.equals("Pay Water Bills")) {
                    new buyWaterPage().setVisible(true);
                    DashboardFrame.this.dispose();
                } else if (paymentButtonText.equals("Buy Electricity")) {
                    new buyElectricityPage().setVisible(true);
                    DashboardFrame.this.dispose();
                }
            }
        });
        
        JButton historyButton = new JButton(historyButtonText);
        historyButton.setForeground(Color.WHITE);
        historyButton.setBackground(new Color(0,123,255));
        historyButton.setFocusPainted(false);
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (historyButtonText.equals("Water history")) {
                    new TransactionHistoryForWaterPage().setVisible(true);
                    DashboardFrame.this.dispose();
                } else if (historyButtonText.equals("Electricity history")) {
                    new TransactionHistoryForElectricityPage().setVisible(true);
                    DashboardFrame.this.dispose();
                }
            }
        });
        
       buttonPanel.add(paymentButton);
       buttonPanel.add(historyButton);
       detailsPanel.add(buttonPanel);
       return detailsPanel;
    }
    private class UsageChartPanel extends JPanel{
        private final int[]values;
        public UsageChartPanel(int[]values){
           this.values = values;
            setPreferredSize(new Dimension(300,115));
            setMinimumSize(new Dimension(300,115));
            setMaximumSize(new Dimension(300,115));
            setBackground(Color.WHITE);        
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
           Graphics2D g2 = (Graphics2D) g.create();
           String[] months = {"Jan","Feb","Mar","Apr"};
           int chartBottom = 82;
           int chartTop = 15;
           int chartHeight = chartBottom - chartTop;
           int maximumValue = 700;
           int barWidth = 28;
           int gap = 35;
           int startX = 20;
           
           g2.setColor(Color.GRAY);
           g2.drawLine(10,chartBottom,290,chartBottom);
           
           for (int i = 0; i < values.length; i++){
           int barHeight =(int)((values[i]/(double) maximumValue)* chartHeight);
           int x = startX+ i * (barWidth + gap);
           int y = chartBottom - barHeight;
           
           g2.setColor(new Color(36,112,145));
           g2.fillRect(x, y, barWidth, barHeight);
           g2.setColor(Color.BLACK);
           g2.setFont(new Font("Arial", Font.PLAIN,10));
           g2.drawString(months[i], x, chartBottom + 14);
        }   
       
        g2.dispose();
           
       }
    }
}
         
    
    
    


       
        /* //Water  
            JPanel waterPanel = new JPanel();
            waterPanel.setBackground(Color.WHITE);

            JLabel waterTitle = new JLabel("Water");
            waterPanel.add(waterTitle);
        //Water Gauge
            WaterGauge waterGuage = new WaterGauge();
            waterPanel.add(waterGuage);
            //Water Info
            JLabel WDetails = new JLabel("<html>1. Average<br>"+"Usage<br>"+"2.79 L<br>"+"Used<br>"+"Today</html>");
            waterPanel.add(WDetails);
            contentPanel.add(waterPanel);
//Profile
       /* JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        profilePanel.setOpaque(false);
        ImageIcon profileImage = new ImageIcon(getClass().getResource("C:/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/java/Resources/user 1.png"));
        JLabel profileLabel;
        Image scaledProfile = profileImage.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        profileLabel = new JLabel(new ImageIcon(scaledProfile));
        profilePanel.add(profileLabel);
        JLabel username;
        username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        profilePanel.add(username);*/
        /* ImageIcon profileIcon = new ImageIcon("C:/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/Resources/user 1.png");
            Image
            JLabel profileLabel = new JLabel(profileIcon);
            add(profileLabel); 
        //Username    
            JLabel usernameLabel = new JLabel("Username");
            add(usernameLabel);*/
                        
          /*  ImageIcon profileIcon = new ImageIcon("C:/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/Resources/user 1.png");
            Image
            JLabel profileLabel = new JLabel(profileIcon);
            contentPanel.add(profileLabel); 
        //Username    
            JLabel usernameLabel = new JLabel("Username");
            contentPanel.add(usernameLabel);
            
            JPanel contentPanel = new JPanel();
       contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
       contentPanel.setBackground(Color.WHITE);
       add(contentPanel, BorderLayout.CENTER);
       
       //Settings    
            ImageIcon settingsIcon = new ImageIcon("C/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/Resources/settings_24dp_1F1F1F 1.png");
            Image scaledSetttings = settingsIcon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);
            ImageIcon scaledSettingsIcon = new ImageIcon(scaledSetttings);
            JLabel settingsLabel = new JLabel(scaledSettingsIcon);
            add(settingsLabel);
      /*  //Water  
            JPanel waterPanel = new JPanel();
            waterPanel.setBackground(Color.WHITE);

            JLabel waterTitle = new JLabel("Water");
            waterPanel.add(waterTitle);
        //Water Gauge
            WaterGauge waterGuage = new WaterGauge();
            waterPanel.add(waterGuage);
        //Water Info
            JLabel WDetails = new JLabel("<html>1. Average<br>"+"Usage<br>"+"2.79 L<br>"+"Used<br>"+"Today</html>");
            waterPanel.add(WDetails);
            mainPanel.add(waterPanel);

        //Electricity
            JPanel electricityPanel = new JPanel();
            electricityPanel.setBackground(Color.WHITE);
            
            JLabel electricityTitle = new JLabel("Electricity");
            electricityPanel.add(electricityTitle);
        //Electricity Guage
            ElectricityGuage electricityGuage = new ElectricityGuage();
            electricityPanel.add(electricityGuage);
        //Electricity Info
            JLabel electricityInfo = new JLabel("<html>Average<br>"+"Usage<br>"+"Used Today</html>");
            electricityPanel.add(electricityInfo);
           mainPanel.add(electricityPanel);
           
        //Footer Image   
            ImageIcon footer = new ImageIcon("C:/Users/Shuma/OneDrive/Documents/NetBeansProjects/AquaVoltSA/src/main/resources/image 1.png"); 
            Image scaledFooter = footer.getImage().getScaledInstance(400,100,Image.SCALE_SMOOTH);
            ImageIcon scaledfooter = new ImageIcon(scaledImage);
            JLabel footerlabel = new JLabel(scaledfooter);
            mainPanel.add(footerlabel);   
        add(mainPanel);   
        */
   

    

   
 

        
         
    

