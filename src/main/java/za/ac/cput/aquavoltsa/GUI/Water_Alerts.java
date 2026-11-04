
package za.ac.cput.aquavoltsa.GUI;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;

public class Water_Alerts extends JFrame {

    private final Color BLUE = new Color(173, 216, 230);
    private final Color ORANGE = new Color(255, 140, 0);
    private final int userId = 1;

    private JCheckBox cbWaterOutages;
    private JCheckBox cbWaterLeaks;
    private JCheckBox cbWaterUsage;
    private JCheckBox cbLowPressure;

    private JComboBox<String> cbDeliveryMethod;

    public Water_Alerts() {
        setTitle("Water Alerts");
        setSize(425, 917);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);

        createInterface();
        loadPreferences();
    }

    private void createInterface() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(BLUE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 25, 25));

        // TOP LEFT: Back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(BLUE);

        JButton btBack = new JButton("Back");
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

        // CENTER: Icon, title, checkboxes, combo box and buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BLUE);
        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Centered water icon
        JLabel lblWaterIcon = new JLabel();
        lblWaterIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        java.net.URL iconURL =
                getClass().getResource("/icons/water.png");

        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image image = icon.getImage().getScaledInstance(
                    100, 100, Image.SCALE_SMOOTH);
            lblWaterIcon.setIcon(new ImageIcon(image));
        } else {
            lblWaterIcon.setText("WATER");
            lblWaterIcon.setFont(
                    new Font("Arial", Font.BOLD, 24));
        }

        // Centered Water Alerts title
        JLabel lblTitle = new JLabel("Water Alerts");
        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 26));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInstruction = new JLabel(
                "Select the water alerts you want to receive:");
        lblInstruction.setFont(
                new Font("Arial", Font.PLAIN, 15));
        lblInstruction.setAlignmentX(Component.LEFT_ALIGNMENT);

        cbWaterOutages =
                createCheckbox("Water Supply Outages");
        cbWaterLeaks =
                createCheckbox("Water Leak Alerts");
        cbWaterUsage =
                createCheckbox("Water Usage Notifications");
        cbLowPressure =
                createCheckbox("Low Water Pressure Alerts");

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

        // Buttons side by side under the combo box
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(BLUE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btSave = new JButton("Save Preferences");
        btSave.setBackground(ORANGE);
        btSave.setForeground(Color.BLACK);
        btSave.setFocusPainted(false);
        btSave.addActionListener(e -> savePreferences());

        JButton btReset = new JButton("Reset");
        btReset.setFocusPainted(false);
        btReset.addActionListener(e -> {
            cbWaterOutages.setSelected(false);
            cbWaterLeaks.setSelected(false);
            cbWaterUsage.setSelected(false);
            cbLowPressure.setSelected(false);
            cbDeliveryMethod.setSelectedIndex(0);
        });

        buttonPanel.add(btSave);
        buttonPanel.add(btReset);

        // Assemble the content
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(lblWaterIcon);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(lblInstruction);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(cbWaterOutages);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbWaterLeaks);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbWaterUsage);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbLowPressure);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(lblMethod);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(cbDeliveryMethod);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(buttonPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JCheckBox createCheckbox(String text) {
        JCheckBox checkbox = new JCheckBox(text);
        checkbox.setFont(
                new Font("Arial", Font.PLAIN, 16));
        checkbox.setBackground(BLUE);
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
                JOptionPane.showMessageDialog(
                        this,
                        "Could not connect to the database. "
                        + "Preferences could not be loaded.");
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, userId);
                statement.setString(2, "WATER_OUTAGE");
                statement.setString(3, "WATER_LEAK");
                statement.setString(4, "WATER_USAGE");
                statement.setString(5, "LOW_WATER_PRESSURE");

                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {

                        String type =
                                result.getString("alert_type");
                        boolean enabled =
                                result.getBoolean("enabled");
                        String method =
                                result.getString("delivery_method");

                        switch (type) {
                            case "WATER_OUTAGE":
                                cbWaterOutages.setSelected(enabled);
                                break;
                            case "WATER_LEAK":
                                cbWaterLeaks.setSelected(enabled);
                                break;
                            case "WATER_USAGE":
                                cbWaterUsage.setSelected(enabled);
                                break;
                            case "LOW_WATER_PRESSURE":
                                cbLowPressure.setSelected(enabled);
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
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load water preferences:\n"
                    + e.getMessage());
        }
    }

    private void savePreferences() {

        boolean anySelected =
                cbWaterOutages.isSelected()
                || cbWaterLeaks.isSelected()
                || cbWaterUsage.isSelected()
                || cbLowPressure.isSelected();

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
                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed.");
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                saveOnePreference(statement, "WATER_OUTAGE",
                        cbWaterOutages.isSelected(), method);

                saveOnePreference(statement, "WATER_LEAK",
                        cbWaterLeaks.isSelected(), method);

                saveOnePreference(statement, "WATER_USAGE",
                        cbWaterUsage.isSelected(), method);

                saveOnePreference(statement, "LOW_WATER_PRESSURE",
                        cbLowPressure.isSelected(), method);

                statement.executeBatch();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Water alert preferences saved successfully!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not save water preferences:\n"
                    + e.getMessage());
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
