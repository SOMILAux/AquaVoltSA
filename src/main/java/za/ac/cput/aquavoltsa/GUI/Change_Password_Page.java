//Final update done by Inathi
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.*;

import za.ac.cput.aquavoltsa.DAO.DAO;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class Change_Password_Page extends JFrame {

    private JLabel lbJust_blank, lbAnother_blank, lbSet_Password,
            lbConfirm_Password, lbHeading, lbJust_blank2,
            lbCurrent_password;

    private JPasswordField txtSet_Password, txtConfirm_Password,
            txtCurrent_password;

    private JButton btSave_Password, btBack,
            btShow_Password, btConfirm_Password;

    private FontIcon lockIcon, eyeIcon;
    private ImageIcon logoIcon;

    private JPanel panel_1, panel_2, pre_panel_3,
            panel_4, password_panel, Confirm_Panel;

    private JRadioButton rbChange_Lock_Type;

    private JLabel lblLength, lblUppercase, lblNumber, lblSpecial;
    private JPanel checklistPanel;

    private static final String ICON_VALID =
            "<html><font color='green'><b>\u2713</b></font> ";

    private static final String ICON_INVALID =
            "<html><font color='red'><b>\u2717</b></font> ";

    public Change_Password_Page() {

        lbSet_Password = new JLabel("Create Your Password");
        lbConfirm_Password = new JLabel("Confirm_Password");
        lbJust_blank = new JLabel(" ");
        lbAnother_blank = new JLabel(" ");
        lbHeading = new JLabel("Lock your Account");
        lbJust_blank2 = new JLabel(" ");
        lbCurrent_password = new JLabel("Current Password");

        lblLength = new JLabel();
        lblUppercase = new JLabel();
        lblNumber = new JLabel();
        lblSpecial = new JLabel();

        txtSet_Password = new JPasswordField(5);
        txtConfirm_Password = new JPasswordField(5);
        txtCurrent_password = new JPasswordField(5);

        btSave_Password = new JButton("Save Password");
        btBack = new JButton("Back");
        rbChange_Lock_Type = new JRadioButton("Change_Lock_Type");

        panel_1 = new JPanel();
        panel_2 = new JPanel();
        pre_panel_3 = new JPanel();
        panel_4 = new JPanel();

        setTitle("Change Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(425, 917);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Large black lock icon
        lockIcon = FontIcon.of(FontAwesomeSolid.LOCK, 78, Color.BLACK);

        lbJust_blank.setIcon(lockIcon);
        lbJust_blank.setHorizontalAlignment(JLabel.CENTER);
        lbJust_blank.setVerticalAlignment(JLabel.CENTER);

        lbHeading.setFont(new Font("Arial", Font.BOLD, 24));
        lbHeading.setHorizontalAlignment(JLabel.CENTER);
        lbHeading.setVerticalAlignment(JLabel.CENTER);

        pre_panel_3.add(lbHeading);
        lbJust_blank.setFont(new Font("Arial", Font.BOLD, 30));
        pre_panel_3.add(lbJust_blank);
        pre_panel_3.setLayout(new GridLayout(2, 1));
        pre_panel_3.setBackground(Color.LIGHT_GRAY);
        add(pre_panel_3);

        // Black eye icons
        eyeIcon = FontIcon.of(FontAwesomeSolid.EYE, 25, Color.BLACK);

        btShow_Password = new JButton(eyeIcon);
        btShow_Password.addActionListener(e ->
                togglePassword(txtSet_Password));
        styleEyeButton(btShow_Password);

        password_panel = new JPanel(new BorderLayout());
        password_panel.add(txtSet_Password, BorderLayout.CENTER);
        password_panel.add(btShow_Password, BorderLayout.EAST);

        btConfirm_Password = new JButton(eyeIcon);
        btConfirm_Password.addActionListener(e ->
                togglePassword(txtConfirm_Password));
        styleEyeButton(btConfirm_Password);

        Confirm_Panel = new JPanel(new BorderLayout());
        Confirm_Panel.add(txtConfirm_Password, BorderLayout.CENTER);
        Confirm_Panel.add(btConfirm_Password, BorderLayout.EAST);

        // Password fields and layout
        lbSet_Password.setFont(new Font("Arial", Font.BOLD, 16));
        lbCurrent_password.setFont(new Font("Arial", Font.BOLD, 16));

        panel_1.add(lbCurrent_password);
        txtCurrent_password.setBackground(Color.LIGHT_GRAY);
        panel_1.add(txtCurrent_password);

        panel_1.add(lbSet_Password);
        txtSet_Password.setFont(new Font("Arial", Font.BOLD, 16));
        txtSet_Password.setBackground(Color.LIGHT_GRAY);
        password_panel.setBackground(Color.LIGHT_GRAY);
        panel_1.add(password_panel);

        panel_1.add(lbConfirm_Password);
        lbConfirm_Password.setFont(new Font("Arial", Font.BOLD, 16));
        txtConfirm_Password.setFont(new Font("Arial", Font.BOLD, 16));
        txtConfirm_Password.setBackground(Color.LIGHT_GRAY);
        Confirm_Panel.setBackground(Color.LIGHT_GRAY);
        panel_1.add(Confirm_Panel);

        panel_1.add(lbAnother_blank);
        panel_1.setBackground(Color.LIGHT_GRAY);
        panel_1.setLayout(new GridLayout(6, 1));
        add(panel_1);

        // Password checklist
        checklistPanel = new JPanel(new GridLayout(4, 1));
        checklistPanel.setBackground(Color.LIGHT_GRAY);
        checklistPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 20, 0, 20));

        Font checkFont = new Font("Arial", Font.PLAIN, 14);
        lblLength.setFont(checkFont);
        lblUppercase.setFont(checkFont);
        lblNumber.setFont(checkFont);
        lblSpecial.setFont(checkFont);

        checklistPanel.add(lblLength);
        checklistPanel.add(lblUppercase);
        checklistPanel.add(lblNumber);
        checklistPanel.add(lblSpecial);
        add(checklistPanel);

        resetChecklist();
        setupPasswordListener();

        // Save button
        btSave_Password.setFont(new Font("Arial", Font.BOLD,16));
        btSave_Password.setBackground(new Color(173, 216, 230));
        btSave_Password.addActionListener(e -> savePassword());

        // Back button
        btBack.setFont(new Font("Arial", Font.BOLD, 16));
        btBack.setBackground(Color.GRAY);
        btBack.addActionListener(e -> {
            Profile_Page2 profile = new Profile_Page2();
            profile.setSize(425, 917);
            profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            profile.setLocationRelativeTo(null);
            profile.setVisible(true);
            this.dispose();
        });

        panel_2.setLayout(new GridBagLayout());
        panel_2.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_2.add(btSave_Password, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_2.add(btBack, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;

        rbChange_Lock_Type.setBackground(Color.LIGHT_GRAY);
        rbChange_Lock_Type.addActionListener(e -> changeLockType());
        //panel_2.add(rbChange_Lock_Type, gbc);

        add(panel_2);

        // Existing logo
        logoIcon = new ImageIcon(
                getClass().getResource("/icons/plain_logo.png"));

        Image logoImage = logoIcon.getImage().getScaledInstance(
                130, 130, Image.SCALE_SMOOTH);

        lbJust_blank2.setIcon(new ImageIcon(logoImage));
        lbJust_blank2.setHorizontalAlignment(JLabel.CENTER);
        lbJust_blank2.setVerticalAlignment(JLabel.CENTER);

        panel_4.setBackground(Color.LIGHT_GRAY);
        panel_4.add(lbJust_blank2);
        add(panel_4);

        loadLockType();
        revalidate();
        repaint();
    }

    private void styleEyeButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
    }

    private void togglePassword(JPasswordField field) {
        if (field.getEchoChar() == (char) 0) {
            field.setEchoChar('\u2022');
        } else {
            field.setEchoChar((char) 0);
        }
    }

    private void resetChecklist() {
        updateRule(lblLength, "At least 8 characters", false);
        updateRule(lblUppercase,
                "At least one uppercase letter (A-Z)", false);
        updateRule(lblNumber, "At least one number (0-9)", false);
        updateRule(lblSpecial,
                "At least one special character (!@#$%^&*)", false);
    }

    private void setupPasswordListener() {
        txtSet_Password.getDocument().addDocumentListener(
                new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validatePassword();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validatePassword();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validatePassword();
            }
        });
    }

    private boolean validatePassword() {
        String password = new String(txtSet_Password.getPassword());

        boolean lengthOK = password.length() >= 8;
        boolean uppercaseOK = password.matches(".*[A-Z].*");
        boolean numberOK = password.matches(".*[0-9].*");
        boolean specialOK =
                password.matches(".*[!@#$%^&*(),.?\"{}|<>].*");

        updateRule(lblLength, "At least 8 characters", lengthOK);
        updateRule(lblUppercase,
                "At least one uppercase letter (A-Z)", uppercaseOK);
        updateRule(lblNumber, "At least one number (0-9)", numberOK);
        updateRule(lblSpecial,
                "At least one special character (!@#$%^&*)", specialOK);

        return lengthOK && uppercaseOK && numberOK && specialOK;
    }

    private void updateRule(JLabel label, String text, boolean isValid) {
        if (isValid) {
            label.setText(ICON_VALID
                    + "<font color='#006400'>" + text + "</font></html>");
        } else {
            label.setText(ICON_INVALID
                    + "<font color='#8B0000'>" + text + "</font></html>");
        }
    }

    // Update the logged-in customer's password
    private void savePassword() {
        if (Session.customer == null) {
            JOptionPane.showMessageDialog(
                    this, "Please log in first - no customer is logged in.");
            return;
        }

        String currentPassword = new String(txtCurrent_password.getPassword());
        String newPassword = new String(txtSet_Password.getPassword());
        String confirmPassword = new String(txtConfirm_Password.getPassword());

        if (currentPassword.isEmpty()
                || newPassword.isEmpty()
                || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please complete all three password fields.");
            return;
        }

        if (!validatePassword()) {
            JOptionPane.showMessageDialog(
                    this, "Your new password does not meet all requirements.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                    this, "The new password and confirmation do not match.");
            return;
        }

        if (currentPassword.equals(newPassword)) {
            JOptionPane.showMessageDialog(
                    this, "Your new password must be different "
                    + "from your current password.");
            return;
        }

        if (!UserStore.hash(currentPassword).equals(Session.customer.passHash)) {
            JOptionPane.showMessageDialog(
                    this, "Your current password is incorrect.");
            return;
        }

        String newHash = UserStore.hash(newPassword);

        try (Connection connection = DAO.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE customers SET pass_hash = ? WHERE id = ?")) {

            statement.setString(1, newHash);
            statement.setString(2, Session.customer.id);

            if (statement.executeUpdate() > 0) {
                Session.customer.passHash = newHash;

                JOptionPane.showMessageDialog(
                        this, "Password updated successfully!");

                txtCurrent_password.setText("");
                txtSet_Password.setText("");
                txtConfirm_Password.setText("");
                resetChecklist();

            } else {
                JOptionPane.showMessageDialog(
                        this, "Password could not be updated.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this, "Could not update password:\n" + e.getMessage());
        }
    }

    
    private void ensureLockTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE lock_preferences ("
                    + "customer_username VARCHAR(30) PRIMARY KEY, "
                    + "lock_type VARCHAR(10) NOT NULL)");
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) {
                throw e;
            }
        }
    }

    private void changeLockType() {
        if (Session.customer == null) {
            JOptionPane.showMessageDialog(this, "Please log in first.");
            rbChange_Lock_Type.setSelected(false);
            return;
        }

        String[] options = {"Password", "PIN", "Pattern"};

        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose your lock type:",
                "Change Lock Type",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                getCurrentLockType());

        if (selected == null) {
            rbChange_Lock_Type.setSelected(false);
            return;
        }

        try (Connection connection = DAO.connect()) {
            ensureLockTable(connection);

            int updated;

            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE lock_preferences SET lock_type = ? WHERE customer_username = ?")) {

                statement.setString(1, selected);
                statement.setString(2, Session.customer.username);
                updated = statement.executeUpdate();
            }

            if (updated == 0) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO lock_preferences (customer_username, lock_type) VALUES (?, ?)")) {

                    statement.setString(1, Session.customer.username);
                    statement.setString(2, selected);
                    statement.executeUpdate();
                }
            }

            rbChange_Lock_Type.setText("Lock Type: " + selected);
            rbChange_Lock_Type.setSelected(false);

            JOptionPane.showMessageDialog(
                    this, "Lock type preference saved: " + selected);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this, "Could not save lock type:\n" + e.getMessage());
        }
    }

    private String getCurrentLockType() {
        if (Session.customer == null) {
            return "Password";
        }

        try (Connection connection = DAO.connect()) {
            ensureLockTable(connection);

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT lock_type FROM lock_preferences WHERE customer_username = ?")) {

                statement.setString(1, Session.customer.username);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return result.getString("lock_type");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Could not read lock type: "
                    + e.getMessage());
        }

        return "Password";
    }

    private void loadLockType() {
        rbChange_Lock_Type.setText(
                "Change_Lock_Type (" + getCurrentLockType() + ")");
    }
}