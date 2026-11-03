

package za.ac.cput.aquavoltsa.GUI;

import za.ac.cput.aquavoltsa.GUI.Settings_Page;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;

public class Notification_Preference extends JFrame {

    private JCheckBox cbWaterAlerts;
    private JCheckBox cbElectricityAlerts;
    private JComboBox<String> cbChannel;
    private JComboBox<String> cbLanguage;

    // Temporary test user
    private final int userId = 1;

    public Notification_Preference() {
        setTitle("AquaVolt SA - Notification Preference");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 250, 250));

        // TOP: Back arrow and logo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(240, 250, 250));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        backPanel.setBackground(new Color(240, 250, 250));

        JButton btBack = new JButton("\u276E");
        btBack.setFont(new Font("Arial", Font.BOLD, 24));
        btBack.setBorderPainted(false);
        btBack.setContentAreaFilled(false);
        btBack.setFocusPainted(false);
        backPanel.add(btBack);

        topPanel.add(backPanel);

        JLabel logoLabel = new JLabel("", SwingConstants.CENTER);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        java.net.URL logoURL =
                getClass().getResource("/icons/logo.png");

        if (logoURL != null) {
            ImageIcon originalIcon = new ImageIcon(logoURL);
            Image scaledImage = originalIcon.getImage()
                    .getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            logoLabel.setText("\uD83D\uDCA7");
            logoLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        }

        topPanel.add(logoLabel);

        JLabel brandLabel = new JLabel("AquaVolt SA");
        brandLabel.setFont(new Font("Arial", Font.BOLD, 22));
        brandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //topPanel.add(brandLabel);

        JLabel titleLabel = new JLabel("Notification Preference");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 100, 160));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        topPanel.add(titleLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // MIDDLE: All notification options
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBackground(new Color(240, 250, 250));
        optionsPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 35, 15, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(9, 5, 9, 5);

        JLabel alertLabel = new JLabel("Choose Your Alerts");
        alertLabel.setFont(new Font("Arial", Font.BOLD, 17));

        cbWaterAlerts = new JCheckBox("Water Alerts");
        cbElectricityAlerts = new JCheckBox("Electricity Alerts");

        cbWaterAlerts.setFont(new Font("Arial", Font.PLAIN, 16));
        cbElectricityAlerts.setFont(new Font("Arial", Font.PLAIN, 16));
        cbWaterAlerts.setOpaque(false);
        cbElectricityAlerts.setOpaque(false);

        JLabel channelLabel =
                new JLabel("Preferred Notification Channel");
        channelLabel.setFont(new Font("Arial", Font.BOLD, 15));

        cbChannel = new JComboBox<>(
                new String[]{"Email", "SMS", "Call"});
        cbChannel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel languageLabel = new JLabel("Preferred Language");
        languageLabel.setFont(new Font("Arial", Font.BOLD, 15));

        cbLanguage = new JComboBox<>(
                new String[]{"English", "isiXhosa",
                    "Afrikaans", "Zulu"});
        cbLanguage.setFont(new Font("Arial", Font.PLAIN, 15));

        gbc.gridy = 0;
        optionsPanel.add(alertLabel, gbc);

        gbc.gridy = 1;
        optionsPanel.add(cbWaterAlerts, gbc);

        gbc.gridy = 2;
        optionsPanel.add(cbElectricityAlerts, gbc);

        gbc.gridy = 3;
        optionsPanel.add(channelLabel, gbc);

        gbc.gridy = 4;
        optionsPanel.add(cbChannel, gbc);

        gbc.gridy = 5;
        optionsPanel.add(languageLabel, gbc);

        gbc.gridy = 6;
        optionsPanel.add(cbLanguage, gbc);

        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        // BOTTOM: Save and Back buttons
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(240, 250, 250));

        JButton btSave = new JButton("Save Preference");
        btSave.setFont(new Font("Arial", Font.BOLD, 15));
        btSave.setBackground(new Color(245, 160, 35));
        btSave.setForeground(Color.BLACK);
        btSave.setFocusPainted(false);
        btSave.setPreferredSize(new Dimension(200, 50));

        JButton btBackBottom = new JButton("Back");
        btBackBottom.setFont(new Font("Arial", Font.BOLD, 15));
        btBackBottom.setBackground(new Color(35, 120, 220));
        btBackBottom.setForeground(Color.BLACK);
        btBackBottom.setFocusPainted(false);
        btBackBottom.setPreferredSize(new Dimension(150, 50));

        buttonPanel.add(btSave);
        buttonPanel.add(btBackBottom);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Button actions
        btSave.addActionListener(e -> savePreferences());

        btBack.addActionListener(e -> goBack());
        btBackBottom.addActionListener(e -> goBack());

        loadPreferences();
    }

    private void loadPreferences() {
        String sql = "SELECT water_alerts_enabled, "
                + "electricity_alerts_enabled, preferred_channel, language "
                + "FROM notification_preference WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cbWaterAlerts.setSelected(
                            rs.getBoolean("water_alerts_enabled"));
                    cbElectricityAlerts.setSelected(
                            rs.getBoolean("electricity_alerts_enabled"));
                    cbChannel.setSelectedItem(
                            rs.getString("preferred_channel"));
                    cbLanguage.setSelectedItem(
                            rs.getString("language"));
                } else {
                    cbWaterAlerts.setSelected(true);
                    cbElectricityAlerts.setSelected(true);
                    cbChannel.setSelectedItem("Email");
                    cbLanguage.setSelectedItem("English");
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not load preferences: " + ex.getMessage());
        }
    }

    private void savePreferences() {
        String sql = "INSERT INTO notification_preference "
                + "(user_id, water_alerts_enabled, "
                + "electricity_alerts_enabled, preferred_channel, language) "
                + "VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "water_alerts_enabled = VALUES(water_alerts_enabled), "
                + "electricity_alerts_enabled = "
                + "VALUES(electricity_alerts_enabled), "
                + "preferred_channel = VALUES(preferred_channel), "
                + "language = VALUES(language)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setBoolean(2, cbWaterAlerts.isSelected());
            stmt.setBoolean(3, cbElectricityAlerts.isSelected());
            stmt.setString(4, cbChannel.getSelectedItem().toString());
            stmt.setString(5, cbLanguage.getSelectedItem().toString());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Notification preferences saved successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not save preferences: " + ex.getMessage());
        }
    }

    private void goBack() {
        Settings_Page page = new Settings_Page();
        page.showGui();
        page.setSize(600, 810);
        //page.setLocationRelativeTo(null);
        page.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        page.setVisible(true);
        this.dispose();
    }

    
}
