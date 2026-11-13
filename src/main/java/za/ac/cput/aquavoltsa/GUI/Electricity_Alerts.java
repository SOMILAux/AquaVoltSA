
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import za.ac.cput.aquavoltsa.DAO.DAO;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class Electricity_Alerts extends JFrame {

    private final Color ORANGE = new Color(255, 140, 0);
    private final Color BLUE = new Color(173, 216, 230);
    private final Color GOLD = new Color(218, 165, 32);

    private ImageIcon logoIcon;
    private JLabel lbJust_blank2;

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

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 15));

        mainPanel.setBackground(GOLD);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 25, 25));

        // BACK BUTTON
        JPanel topPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.setBackground(GOLD);

        JButton btBack = new JButton("Back");

        FontIcon backIcon = FontIcon.of(
                FontAwesomeSolid.ARROW_LEFT, 18, Color.BLACK);

        btBack.setIcon(backIcon);
        btBack.setBackground(ORANGE);
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

        // MAIN CONTENT
        JPanel contentPanel = new JPanel();

        contentPanel.setBackground(GOLD);

        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // ELECTRICITY ICON
        JLabel lblElectricityIcon = new JLabel();

        FontIcon electricityIcon = FontIcon.of(
                FontAwesomeSolid.BOLT, 100, Color.BLACK);

        lblElectricityIcon.setIcon(electricityIcon);

        lblElectricityIcon.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // TITLE
        JLabel lblTitle = new JLabel("Electricity Alerts");

        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 26));

        lblTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // INSTRUCTIONS
        JLabel lblInstruction = new JLabel(
                "Which alerts to receive ?");

        lblInstruction.setFont(
                new Font("Arial", Font.BOLD, 18));

        lblInstruction.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        // ALERT CHECKBOXES
        cbPowerOutage =
                createCheckbox("Power Outage Alerts");

        cbHighUsage =
                createCheckbox(
                        "High Electricity Usage Notifications");

        cbPowerRestoration =
                createCheckbox("Power Restoration Alerts");

        cbMeterAlerts =
                createCheckbox("Electricity Meter Alerts");

        // DELIVERY METHOD
        JLabel lblMethod = new JLabel(
                "Receive alerts Via?");

        lblMethod.setFont(
                new Font("Arial", Font.BOLD, 18));

        lblMethod.setAlignmentX(
                Component.LEFT_ALIGNMENT);

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

        cbDeliveryMethod.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        // BUTTON PANEL
        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.CENTER, 15, 5));

        buttonPanel.setBackground(GOLD);

        buttonPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // SAVE BUTTON
        JButton btSave = new JButton("Save Preference");

        FontIcon saveIcon = FontIcon.of(
                FontAwesomeSolid.SAVE, 18, Color.BLACK);

        btSave.setIcon(saveIcon);
        btSave.setBackground(BLUE);
        btSave.setForeground(Color.BLACK);
        btSave.setFocusPainted(false);

        btSave.addActionListener(e -> savePreferences());

        // RESET BUTTON
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

        // ADD CONTENT
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

        // LOGO
        java.net.URL logoURL =
                getClass().getResource("/icons/plain_logo.png");

        if (logoURL != null) {

            logoIcon = new ImageIcon(logoURL);

            Image logoImage =
                    logoIcon.getImage().getScaledInstance(
                            125, 125, Image.SCALE_SMOOTH);

            lbJust_blank2.setIcon(
                    new ImageIcon(logoImage));

        } else {

            System.out.println(
                    "Logo not found: /icons/plain_logo.png");
        }

        JPanel logoPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));

        logoPanel.setBackground(GOLD);

        logoPanel.add(lbJust_blank2);

        contentPanel.add(logoPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // CREATE CHECKBOX
    private JCheckBox createCheckbox(String text) {

        JCheckBox checkbox = new JCheckBox(text);

        checkbox.setFont(
                new Font("Arial", Font.PLAIN, 14));

        checkbox.setBackground(ORANGE);
        checkbox.setForeground(Color.BLACK);

        checkbox.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        return checkbox;
    }

    // FIND THE LOGGED-IN CUSTOMER'S INTEGER USER ID
    private int getLoggedInUserId(Connection connection)
            throws SQLException {

        if (Session.customer == null) {
            return -1;
        }

        String sql =
                "SELECT user_id FROM users WHERE username = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setString(1, Session.username());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt("user_id");
                }
            }
        }

        return -1;
    }

    // LOAD PREFERENCES FROM DATABASE
    private void loadPreferences() {

        if (Session.customer == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please log in first.");

            return;
        }

        String sql =
                "SELECT alert_type, enabled, delivery_method "
                + "FROM alert_preferences "
                + "WHERE user_id = ?";

        try (Connection connection = DAO.connect()) {

            int userId = getLoggedInUserId(connection);

            if (userId == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "No matching user record was found. "
                        + "Please check the users table.");

                return;
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(sql)) {

                statement.setInt(1, userId);

                try (ResultSet result =
                        statement.executeQuery()) {

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

            JOptionPane.showMessageDialog(
                    this,
                    "Preferences could not be loaded:\n"
                    + e.getMessage());
        }
    }

    // SAVE PREFERENCES
    private void savePreferences() {

        if (Session.customer == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please log in first.");

            return;
        }

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

        String updateSQL =
                "UPDATE alert_preferences "
                + "SET enabled = ?, delivery_method = ? "
                + "WHERE user_id = ? AND alert_type = ?";

        String insertSQL =
                "INSERT INTO alert_preferences "
                + "(user_id, alert_type, enabled, delivery_method) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DAO.connect()) {

            int userId = getLoggedInUserId(connection);

            if (userId == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "No matching user record was found. "
                        + "Your preferences were not saved.");

                return;
            }

            connection.setAutoCommit(false);

            try (PreparedStatement updateStatement =
                    connection.prepareStatement(updateSQL);
                 PreparedStatement insertStatement =
                    connection.prepareStatement(insertSQL)) {

                saveOnePreference(
                        connection,
                        updateStatement,
                        insertStatement,
                        userId,
                        "POWER_OUTAGE",
                        cbPowerOutage.isSelected(),
                        method);

                saveOnePreference(
                        connection,
                        updateStatement,
                        insertStatement,
                        userId,
                        "HIGH_ELECTRICITY_USAGE",
                        cbHighUsage.isSelected(),
                        method);

                saveOnePreference(
                        connection,
                        updateStatement,
                        insertStatement,
                        userId,
                        "POWER_RESTORED",
                        cbPowerRestoration.isSelected(),
                        method);

                saveOnePreference(
                        connection,
                        updateStatement,
                        insertStatement,
                        userId,
                        "ELECTRICITY_METER",
                        cbMeterAlerts.isSelected(),
                        method);

                connection.commit();

                JOptionPane.showMessageDialog(
                        this,
                        "Electricity alert preferences saved successfully!");

            } catch (SQLException e) {

                connection.rollback();

                throw e;

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preferences could not be saved:\n"
                    + e.getMessage());
        }
    }

    // UPDATE EXISTING PREFERENCE OR INSERT A NEW ONE
    private void saveOnePreference(
            Connection connection,
            PreparedStatement updateStatement,
            PreparedStatement insertStatement,
            int userId,
            String alertType,
            boolean enabled,
            String method) throws SQLException {

        updateStatement.setBoolean(1, enabled);
        updateStatement.setString(2, method);
        updateStatement.setInt(3, userId);
        updateStatement.setString(4, alertType);

        int rows = updateStatement.executeUpdate();

        if (rows == 0) {

            insertStatement.setInt(1, userId);
            insertStatement.setString(2, alertType);
            insertStatement.setBoolean(3, enabled);
            insertStatement.setString(4, method);

            insertStatement.executeUpdate();
        }
    }
}
