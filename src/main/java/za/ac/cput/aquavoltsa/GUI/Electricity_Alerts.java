


package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class Electricity_Alerts extends JFrame {

    private final Color ORANGE = new Color(255, 140, 0);
    private final Color BLUE = new Color(173, 216, 230);

    private ImageIcon logoIcon;
    private JLabel lbJust_blank2;

    private final int userId = 1;

    private JCheckBox cbPowerOutage;
    private JCheckBox cbHighUsage;
    private JCheckBox cbPowerRestoration;
    private JCheckBox cbMeterAlerts;

    private JComboBox<String> cbDeliveryMethod;

    public Electricity_Alerts() {
        setTitle("Electricity Alerts");
        setSize(425, 917);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        lbJust_blank2 = new JLabel(" ");

        createInterface();
        loadPreferences();
    }

    private void createInterface() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        Color gold = new Color(218, 165, 32);
        mainPanel.setBackground(gold);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 25, 25));

        // Back button at the top-left
        JPanel topPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(gold);

        JButton btBack = new JButton("Back");

        FontIcon backIcon = FontIcon.of(
                FontAwesomeSolid.ARROW_LEFT, 18, Color.BLACK);

        btBack.setIcon(backIcon);
        btBack.setBackground(Color.orange);
        btBack.setForeground(Color.BLACK);

        btBack.addActionListener(e -> {
            Settings_Page page = new Settings_Page();
            page.showGui();
            page.setSize(425, 917);
            page.setLocationRelativeTo(null);
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            page.setVisible(true);
            this.dispose();
        });

        topPanel.add(btBack);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(gold);
        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Large black electricity icon
        JLabel lblElectricityIcon = new JLabel();

        FontIcon electricityIcon = FontIcon.of(
                FontAwesomeSolid.BOLT, 100, Color.BLACK);

        lblElectricityIcon.setIcon(electricityIcon);
        lblElectricityIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Centered title
        JLabel lblTitle = new JLabel("Electricity Alerts");
        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 26));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInstruction = new JLabel(
                "Select the electricity alerts you want to receive:");
        lblInstruction.setFont(
                new Font("Arial", Font.PLAIN, 15));
        lblInstruction.setAlignmentX(Component.LEFT_ALIGNMENT);

        cbPowerOutage =
                createCheckbox("Power Outage Alerts");

        cbHighUsage =
                createCheckbox("High Electricity Usage Notifications");

        cbPowerRestoration =
                createCheckbox("Power Restoration Alerts");

        cbMeterAlerts =
                createCheckbox("Electricity Meter Alerts");

        JLabel lblMethod = new JLabel(
                "How do you want to receive the alert?");
        lblMethod.setFont(
                new Font("Arial", Font.BOLD, 15));
        lblMethod.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] methods = {
            "Select Method",
            "Email",
            "SMS",
            "Outlook",
            "WhatsApp"
        };

        cbDeliveryMethod = new JComboBox<>(methods);
        cbDeliveryMethod.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));
        cbDeliveryMethod.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buttons side by side underneath the combo box
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(gold);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btSave = new JButton("Save Preference");

        FontIcon saveIcon = FontIcon.of(
                FontAwesomeSolid.SAVE, 18, Color.BLACK);

        btSave.setIcon(saveIcon);
        btSave.setBackground(BLUE);
        btSave.setForeground(Color.BLACK);
        btSave.setFocusPainted(false);
        btSave.addActionListener(e -> savePreferences());

        JButton btReset = new JButton("Reset");

        FontIcon resetIcon = FontIcon.of(
                FontAwesomeSolid.REDO, 18, Color.BLACK);

        btReset.setIcon(resetIcon);
        btReset.setFocusPainted(false);
        btReset.setBackground(Color.LIGHT_GRAY);
        btReset.setForeground(Color.BLACK);

        btReset.addActionListener(e -> {
            cbPowerOutage.setSelected(false);
            cbHighUsage.setSelected(false);
            cbPowerRestoration.setSelected(false);
            cbMeterAlerts.setSelected(false);
            cbDeliveryMethod.setSelectedIndex(0);
        });

        buttonPanel.add(btSave);
        buttonPanel.add(btReset);

        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(lblElectricityIcon);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(lblInstruction);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(cbPowerOutage);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbHighUsage);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbPowerRestoration);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbMeterAlerts);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(lblMethod);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbDeliveryMethod);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(buttonPanel);

        contentPanel.add(Box.createVerticalStrut(10));

        // Existing logo
        logoIcon = new ImageIcon(
                getClass().getResource("/icons/plain_logo.png"));

        Image logoImage = logoIcon.getImage().getScaledInstance(
                125, 125, Image.SCALE_SMOOTH);

        lbJust_blank2.setIcon(new ImageIcon(logoImage));

        JPanel logoPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER));

        logoPanel.setBackground(gold);
        logoPanel.add(lbJust_blank2);

        contentPanel.add(logoPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JCheckBox createCheckbox(String text) {
        JCheckBox checkbox = new JCheckBox(text);

        checkbox.setFont(
                new Font("Arial", Font.PLAIN, 16));

        checkbox.setBackground(Color.orange);
        checkbox.setForeground(Color.BLACK);
        checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);

        return checkbox;
    }

    private void loadPreferences() {

        String sql =
                "SELECT alert_type, enabled, delivery_method "
                + "FROM alert_preferences WHERE user_id = ? "
                + "AND alert_type IN (?, ?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection()) {

            if (connection == null) {
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, userId);
                statement.setString(2, "POWER_OUTAGE");
                statement.setString(3, "HIGH_ELECTRICITY_USAGE");
                statement.setString(4, "POWER_RESTORED");
                statement.setString(5, "ELECTRICITY_METER");

                try (ResultSet result = statement.executeQuery()) {

                    while (result.next()) {

                        String type =
                                result.getString("alert_type");

                        boolean enabled =
                                result.getBoolean("enabled");

                        String method =
                                result.getString("delivery_method");

                        switch (type) {

                            case "POWER_OUTAGE":
                                cbPowerOutage.setSelected(enabled);
                                break;

                            case "HIGH_ELECTRICITY_USAGE":
                                cbHighUsage.setSelected(enabled);
                                break;

                            case "POWER_RESTORED":
                                cbPowerRestoration.setSelected(enabled);
                                break;

                            case "ELECTRICITY_METER":
                                cbMeterAlerts.setSelected(enabled);
                                break;

                            default:
                                break;
                        }

                        if (method != null) {
                            cbDeliveryMethod.setSelectedItem(method);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            // Preferences could not be loaded.
        }
    }

    private void savePreferences() {

        boolean anySelected =
                cbPowerOutage.isSelected()
                || cbHighUsage.isSelected()
                || cbPowerRestoration.isSelected()
                || cbMeterAlerts.isSelected();

        String method =
                (String) cbDeliveryMethod.getSelectedItem();

        if (anySelected
                && (method == null
                || method.equals("Select Method"))) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a delivery method.");

            return;
        }

        if (method == null || method.equals("Select Method")) {
            method = "Email";
        }

        String sql =
                "INSERT INTO alert_preferences "
                + "(user_id, alert_type, enabled, delivery_method) "
                + "VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "enabled = VALUES(enabled), "
                + "delivery_method = VALUES(delivery_method)";

        try (Connection connection =
                     DBConnection.getConnection()) {

            if (connection == null) {
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                saveOnePreference(statement, "POWER_OUTAGE",
                        cbPowerOutage.isSelected(), method);

                saveOnePreference(statement, "HIGH_ELECTRICITY_USAGE",
                        cbHighUsage.isSelected(), method);

                saveOnePreference(statement, "POWER_RESTORED",
                        cbPowerRestoration.isSelected(), method);

                saveOnePreference(statement, "ELECTRICITY_METER",
                        cbMeterAlerts.isSelected(), method);

                statement.executeBatch();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Electricity alert preferences saved successfully!");

        } catch (SQLException e) {
            // Preferences could not be saved.
        }
    }

    private void saveOnePreference(
            PreparedStatement statement,
            String alertType,
            boolean enabled,
            String method) throws SQLException {

        statement.setInt(1, userId);
        statement.setString(2, alertType);
        statement.setBoolean(3, enabled);
        statement.setString(4, method);

        statement.addBatch();
    }
}
