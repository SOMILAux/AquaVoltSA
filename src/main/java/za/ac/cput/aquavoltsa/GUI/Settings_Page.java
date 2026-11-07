package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.awt.Image;

/*
import main.java.za.ac.cput.aquavoltsa.GUI.Electricity_Alerts;
import main.java.za.ac.cput.aquavoltsa.GUI.Notification_Preference;
import main.java.za.ac.cput.aquavoltsa.GUI.Water_Alerts;
 */

public class Settings_Page extends JFrame implements ActionListener {

    private JLabel lbSettings, lbAccount, lbNotifications, lbApp_Preference, lbBlank, lbProfile_Settings, lbJust_b;
    private JButton btProfile_Settings, btTerms_Conditions, btNotification_Preference, btWater_Alerts, btElectricity_Alerts;
    private JButton btLanguage, btHelp_Support, btAbout_AquaVolt;
    private JButton btLogOut;
    private JLabel lbBack;
    private JPanel panel_1, panel_2, panel_3, panel_4, panel_5, inner_panel;
    private ImageIcon notificationIcon, settingsIcon, profileIcon, termsIcon, backIcon, waterIcon, electricityIcon,
            languageIcon, help_supportIcon, aboutIcon, logoutIcon, logoIcon;

    public Settings_Page() {
        super("Settings");
        lbSettings = new JLabel("Settings");
        lbAccount = new JLabel("Account");
        btProfile_Settings = new JButton("Profile Settings");
        btTerms_Conditions = new JButton("Terms & Conditions");
        lbNotifications = new JLabel("Notifications");
        lbBlank = new JLabel("");
        btNotification_Preference = new JButton("Notification Preference");
        btWater_Alerts = new JButton("Water Alerts");
        btElectricity_Alerts = new JButton("Elelectricity Alerts");
        lbApp_Preference = new JLabel("App Preference");
        btLanguage = new JButton("Language");
        btHelp_Support = new JButton("Help & Support");
        btAbout_AquaVolt = new JButton("About AquaVolt     >");
        btLogOut = new JButton("LogOut");
        backIcon = new ImageIcon(getClass().getResource("/icons/back.png"));
        lbBack = new JLabel();
        lbProfile_Settings = new JLabel("Profile_Settings", JLabel.CENTER);
        lbJust_b = new JLabel(" ");

        lbProfile_Settings.setIcon(profileIcon);
        panel_1 = new JPanel();
        panel_2 = new JPanel();
        panel_3 = new JPanel();
        panel_4 = new JPanel();
        panel_5 = new JPanel();
        inner_panel = new JPanel();

        settingsIcon = new ImageIcon(getClass().getResource("/icons/settings.png"));
        profileIcon = new ImageIcon(getClass().getResource("/icons/profile.png"));
        termsIcon = new ImageIcon(getClass().getResource("/icons/terms & conditions.png"));
        backIcon = new ImageIcon(getClass().getResource("/icons/back.png"));
        notificationIcon = new ImageIcon(getClass().getResource("/icons/notification.png"));
        waterIcon = new ImageIcon(getClass().getResource("/icons/water.png"));
        electricityIcon = new ImageIcon(getClass().getResource("/icons/electricity.png"));
        languageIcon = new ImageIcon(getClass().getResource("/icons/language.png"));
        help_supportIcon = new ImageIcon(getClass().getResource("/icons/help.png"));
        aboutIcon = new ImageIcon(getClass().getResource("/icons/about.png"));
        logoutIcon = new ImageIcon(getClass().getResource("/icons/logout.png"));
        logoIcon = new ImageIcon(getClass().getResource("/icons/plain_logo.png"));

    }

    public void showGui() {
        this.setLayout(new GridLayout(6, 1));

        
        Image backImage = backIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        lbBack.setIcon(new ImageIcon(backImage));
        lbBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

         panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        lbSettings.setIcon(settingsIcon);
        lbSettings.setFont(new Font("Arial", Font.BOLD, 24));

        panel_1.add(lbBack);
        panel_1.add(lbSettings);

        panel_1.setBackground(Color.LIGHT_GRAY);
        this.add(panel_1);

        Image image = logoIcon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
        lbJust_b.setIcon(new ImageIcon(image));
        lbJust_b.setHorizontalAlignment(JLabel.CENTER);
        lbJust_b.setVerticalAlignment(JLabel.CENTER);
        inner_panel.setBackground(Color.LIGHT_GRAY);
        this.inner_panel.add(lbJust_b);
        this.add(inner_panel);

        panel_2.add(lbAccount);
        lbAccount.setFont(new Font("Arial", Font.BOLD, 16));
        btProfile_Settings.setIcon(profileIcon);
        btProfile_Settings.setBackground(Color.LIGHT_GRAY);
        btProfile_Settings.setLayout(new GridLayout(1, 2));
        panel_2.add(btProfile_Settings);
        btTerms_Conditions.setBackground(Color.LIGHT_GRAY);
        btTerms_Conditions.setIcon(termsIcon);
        panel_2.add(btTerms_Conditions);
        lbNotifications.setFont(new Font("Arial", Font.BOLD, 16));
        panel_2.add(lbNotifications);
        panel_2.setLayout(new GridLayout(4, 1));
        panel_2.setBackground(Color.LIGHT_GRAY);
        this.add(panel_2);

        btNotification_Preference.setIcon(notificationIcon);
        btNotification_Preference.setBackground(Color.LIGHT_GRAY);
        panel_3.add(btNotification_Preference);
        btWater_Alerts.setIcon(waterIcon);
        btWater_Alerts.setBackground(Color.LIGHT_GRAY);
        panel_3.add(btWater_Alerts);
        btElectricity_Alerts.setIcon(electricityIcon);
        btElectricity_Alerts.setBackground(Color.LIGHT_GRAY);
        panel_3.add(btElectricity_Alerts);
        panel_3.setLayout(new GridLayout(3, 1));
        this.add(panel_3);

        lbApp_Preference.setFont(new Font("Arial", Font.BOLD, 16));
        panel_4.add(lbApp_Preference);
        btLanguage.setIcon(languageIcon);
        btLanguage.setBackground(Color.LIGHT_GRAY);
        panel_4.add(btLanguage);
        btHelp_Support.setIcon(help_supportIcon);
        btHelp_Support.setBackground(Color.LIGHT_GRAY);
        panel_4.add(btHelp_Support);
        btAbout_AquaVolt.setIcon(aboutIcon);
        btAbout_AquaVolt.setBackground(Color.LIGHT_GRAY);
        panel_4.add(btAbout_AquaVolt);
        panel_4.setLayout(new GridLayout(4, 1));
        panel_4.setBackground(Color.LIGHT_GRAY);
        this.add(panel_4);

        btLogOut.setIcon(logoutIcon);
        btLogOut.setBackground(Color.ORANGE);
        panel_5.add(btLogOut);
        panel_5.setBackground(Color.LIGHT_GRAY);
        this.add(panel_5);

        btProfile_Settings.addActionListener(this);
        btTerms_Conditions.addActionListener(this);
        btNotification_Preference.addActionListener(this);
        btWater_Alerts.addActionListener(this);
        btElectricity_Alerts.addActionListener(this);
        btLanguage.addActionListener(this);
        btHelp_Support.addActionListener(this);
        btAbout_AquaVolt.addActionListener(this);
        btLogOut.addActionListener(this);
        
         lbBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                DashboardFrame change = new DashboardFrame();
                change.setSize(425, 917);
                change.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                change.setLocationRelativeTo(null);
                change.setVisible(true);
                dispose();
            }
        });
        

    }
    
    

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Profile Settings") || e.getSource() == btProfile_Settings) {
            Profile_Page2 profile = new Profile_Page2();
            profile.setSize(425, 917);
            profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            profile.setLocationRelativeTo(null);
            profile.setVisible(true);
            this.dispose();

        }
        if (e.getActionCommand().equals("Terms & Conditions") || e.getSource() == btTerms_Conditions) {
            TermsPage tm = new TermsPage();
            tm.setSize(425, 917);
            tm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tm.setLocationRelativeTo(null);
            tm.setVisible(true);
            this.dispose();

        }
        if (e.getSource() == btNotification_Preference) {
            NotificationPage noti = new NotificationPage();
            noti.setSize(425, 917);
            noti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            noti.setLocationRelativeTo(null);
            noti.setVisible(true);
            this.dispose();

        }
        if (e.getSource() == btWater_Alerts) {
            Water_Alerts water = new Water_Alerts();
            water.setSize(425, 917);
            water.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            water.setLocationRelativeTo(null);
            water.setVisible(true);
            this.dispose();

        }
        if (e.getSource() == btElectricity_Alerts) {
            Electricity_Alerts elect = new Electricity_Alerts();
            elect.setSize(425, 917);
            elect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            elect.setLocationRelativeTo(null);
            elect.setVisible(true);
            this.dispose();

        }
        if (e.getSource() == btLanguage) {
            LanguagePage language = new LanguagePage();
            language.setSize(425, 917);
            language.setLocationRelativeTo(null);
            language.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            language.setVisible(true);
            this.dispose();

        }
        if (e.getActionCommand().equals("Help & Support") || e.getSource() == btHelp_Support) {
            Help_and_Support_Page help = new Help_and_Support_Page();
            help.setSize(425, 917);
            help.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            help.setLocationRelativeTo(null);
            help.setVisible(true);
            this.dispose();

        }
        if (e.getSource() == btAbout_AquaVolt) {
            AboutPage about = new AboutPage();
            about.setSize(425, 917);
            about.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            about.setLocationRelativeTo(null);
            about.setVisible(true);
            this.dispose();

        }

        if (e.getSource() == btLogOut) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                new LoginSignupPage().setVisible(true);
                this.dispose();
            }
        }

    }
    

}
