
package za.ac.cput.aquavoltsa.GUI;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
import za.ac.cput.aquavoltsa.DBConnection.DBConnection;


public class Change_Password_Page extends JFrame {

    private JLabel lbJust_blank, lbAnother_blank, lbSet_Password,
            lbConfirm_Password, lbHeading, lbJust_blank2,
            lbCurrent_password;

    private JPasswordField txtSet_Password, txtConfirm_Password,
            txtCurrent_password;

    private JButton btSave_Password, btBack,
            btShow_Password, btConfirm_Password;

    private ImageIcon lockIcon, eyeIcon, logoIcon;

    private JPanel before_panel, panel_1, panel_2, pre_panel_3,
            panel_4, password_panel, Confirm_Panel;

    private JRadioButton rbChange_Lock_Type;

    private JLabel lblLength, lblUppercase, lblNumber, lblSpecial;
    private JPanel checklistPanel;

    private static final String ICON_VALID =
            "<html><font color='green'><b>\u2713</b></font> ";

    private static final String ICON_INVALID =
            "<html><font color='red'><b>\u2717</b></font> ";

    private final int userId = 1;

    public Change_Password_Page () {

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
        before_panel = new JPanel();

        setTitle("Change Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(425, 917);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Lock icon and heading
        lockIcon = new ImageIcon(getClass().getResource("/icons/lock.png"));
        Image image = lockIcon.getImage().getScaledInstance(
                90, 90, Image.SCALE_SMOOTH);

        lbJust_blank.setIcon(new ImageIcon(image));
        lbJust_blank.setHorizontalAlignment(JLabel.CENTER);
        lbJust_blank.setVerticalAlignment(JLabel.CENTER);

        lbHeading.setFont(new Font("Arial", Font.BOLD, 24));
        lbHeading.setHorizontalAlignment(JLabel.CENTER);
        lbHeading.setVerticalAlignment(JLabel.CENTER);

        pre_panel_3.add(lbHeading);
        lbJust_blank.setFont(new Font("Arial", Font.BOLD, 30));
        pre_panel_3.add(lbJust_blank);
        pre_panel_3.setLayout(new GridLayout(2, 1));
        Color gold = new Color(218, 165, 32);
        pre_panel_3.setBackground(gold);
        add(pre_panel_3);

        // Eye icon
        eyeIcon = new ImageIcon(getClass().getResource("/icons/eye.png"));
        Image eyeImage = eyeIcon.getImage().getScaledInstance(
                30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedEyeIcon = new ImageIcon(eyeImage);

        btShow_Password = new JButton(resizedEyeIcon);
        btShow_Password.addActionListener(e ->
                togglePassword(txtSet_Password));
        styleEyeButton(btShow_Password);

        password_panel = new JPanel(new BorderLayout());
        password_panel.add(txtSet_Password, BorderLayout.CENTER);
        password_panel.add(btShow_Password, BorderLayout.EAST);

        btConfirm_Password = new JButton(resizedEyeIcon);
        btConfirm_Password.addActionListener(e ->
                togglePassword(txtConfirm_Password));
        styleEyeButton(btConfirm_Password);

        Confirm_Panel = new JPanel(new BorderLayout());
        Confirm_Panel.add(txtConfirm_Password, BorderLayout.CENTER);
        Confirm_Panel.add(btConfirm_Password, BorderLayout.EAST);

        // Existing password fields and panel layout
        lbSet_Password.setFont(new Font("Arial", Font.BOLD, 20));
        lbCurrent_password.setFont(new Font("Arial", Font.BOLD, 20));

        panel_1.add(lbCurrent_password);
        txtCurrent_password.setBackground(gold);
        panel_1.add(txtCurrent_password);

        panel_1.add(lbSet_Password);
        txtSet_Password.setFont(new Font("Arial", Font.BOLD, 20));
        txtSet_Password.setBackground(gold);
        password_panel.setBackground(gold);
        panel_1.add(password_panel);

        panel_1.add(lbConfirm_Password);
        lbConfirm_Password.setFont(new Font("Arial", Font.BOLD, 20));
        txtConfirm_Password.setFont(new Font("Arial", Font.BOLD, 20));
        txtConfirm_Password.setBackground(gold);
        Confirm_Panel.setBackground(gold);
        panel_1.add(Confirm_Panel);

        panel_1.add(lbAnother_blank);
        panel_1.setBackground(gold);
        panel_1.setLayout(new GridLayout(6, 1));
        add(panel_1);

        // Existing password checklist
        checklistPanel = new JPanel(new GridLayout(4, 1));
        checklistPanel.setBackground(gold);
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

        // Existing buttons and lock-type control
        btSave_Password.setFont(new Font("Arial", Font.BOLD, 20));
        btSave_Password.setBackground(new Color(173, 216, 230));
        btSave_Password.addActionListener(e -> savePassword());

        btBack.setFont(new Font("Arial", Font.BOLD, 20));
        btBack.setBackground(Color.gray);
        btBack.addActionListener(e -> {
            Profile_Page2 page = new Profile_Page2();
            page.setSize(425, 917);
            page.setLocationRelativeTo(null);
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            page.setVisible(true);
        });

        panel_2.setLayout(new GridBagLayout());
        panel_2.setBackground(gold);

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

        rbChange_Lock_Type.setBackground(gold);
        rbChange_Lock_Type.addActionListener(e -> changeLockType());
        //panel_2.add(rbChange_Lock_Type, gbc);

        add(panel_2);

        // Existing logo
        logoIcon = new ImageIcon(getClass().getResource("/icons/plain_logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(
                130, 130, Image.SCALE_SMOOTH);
        lbJust_blank2.setIcon(new ImageIcon(logoImage));
        lbJust_blank2.setHorizontalAlignment(JLabel.CENTER);
        lbJust_blank2.setVerticalAlignment(JLabel.CENTER);

        panel_4.setBackground(gold);
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

    private void savePassword() {
        char[] currentChars = txtCurrent_password.getPassword();
        char[] newChars = txtSet_Password.getPassword();
        char[] confirmChars = txtConfirm_Password.getPassword();

        String currentPassword = new String(currentChars);
        String newPassword = new String(newChars);
        String confirmPassword = new String(confirmChars);

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

        String selectSQL =
                "SELECT password_hash FROM users WHERE user_id = ?";

        try (Connection connection =
                     DBConnection.getConnection()) {

            if (connection == null) {
                JOptionPane.showMessageDialog(
                        this, "Database connection failed.");
                return;
            }

            String storedPassword;

            try (PreparedStatement statement =
                         connection.prepareStatement(selectSQL)) {
                statement.setInt(1, userId);

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        JOptionPane.showMessageDialog(
                                this, "User account was not found.");
                        return;
                    }
                    storedPassword = result.getString("password_hash");
                }
            }

            if (!passwordMatches(currentPassword, storedPassword)) {
                JOptionPane.showMessageDialog(
                        this, "Your current password is incorrect.");
                return;
            }

            String hashedPassword = hashPassword(newPassword);

            String updateSQL =
                    "UPDATE users SET password_hash = ? WHERE user_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(updateSQL)) {
                statement.setString(1, hashedPassword);
                statement.setInt(2, userId);

                int updated = statement.executeUpdate();

                if (updated > 0) {
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
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, "Could not update password:\n" + e.getMessage());
        }
    }

    // Supports the old plain-text test value and the new PBKDF2 hashes.
    private boolean passwordMatches(String password, String stored)
            throws Exception {

        if (stored == null) {
            return false;
        }

        if (stored.startsWith("PBKDF2$")) {
            String[] parts = stored.split("\\$");

            if (parts.length != 4) {
                return false;
            }

            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expected = Base64.getDecoder().decode(parts[3]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(), salt, iterations, 256);

            byte[] actual;
            try {
                SecretKeyFactory factory =
                        SecretKeyFactory.getInstance(
                                "PBKDF2WithHmacSHA256");
                actual = factory.generateSecret(spec).getEncoded();
            } finally {
                spec.clearPassword();
            }

            return java.security.MessageDigest.isEqual(expected, actual);
        }

        // Temporary compatibility for the existing test account.
        return password.equals(stored);
    }

    private String hashPassword(String password) throws Exception {
        int iterations = 120000;
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), salt, iterations, 256);

        byte[] hash;
        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256");
            hash = factory.generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword();
        }

        return "PBKDF2$" + iterations + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    private void changeLockType() {
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

        String sql =
                "INSERT INTO user_lock_preferences (user_id, lock_type) "
                + "VALUES (?, ?) ON DUPLICATE KEY UPDATE "
                + "lock_type = VALUES(lock_type)";

        try (Connection connection =
                     DBConnection.getConnection()) {

            if (connection == null) {
                JOptionPane.showMessageDialog(
                        this, "Database connection failed.");
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setString(2, selected);
                statement.executeUpdate();
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
        String sql =
                "SELECT lock_type FROM user_lock_preferences "
                + "WHERE user_id = ?";

        try (Connection connection =
                     DBConnection.getConnection()) {

            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);

                    try (ResultSet result = statement.executeQuery()) {
                        if (result.next()) {
                            return result.getString("lock_type");
                        }
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

