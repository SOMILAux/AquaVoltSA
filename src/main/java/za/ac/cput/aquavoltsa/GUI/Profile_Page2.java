
package za.ac.cput.aquavoltsa.GUI;

import za.ac.cput.aquavoltsa.DAO.DAO;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class Profile_Page2 extends JFrame {

    private final Color BG_COLOR = new Color(173, 216, 230);

    private JLabel lbProfile_Pic, lbLogo;
    private ImageIcon logoIcon;

    private FontIcon full_Name_Icon, username_Icon, email_Icon,
            phone_Number_Icon, change_Password_Icon, home_Adress_Icon,
            genderIcon, date_of_birth_Icon, account_status_Icon, backIcon;

    private JLabel lbBack_Text;

    private JTextField txtFull_Name, txtUsername, txtEmail,
            txtPhone_Number, txtHome;

    private JLabel lbFull_Name_Val, lbUsername_Val,
            lbEmail_Val, lbPhone_Val, lbHome_Val;

    private JComboBox<String> cbGender, cbDay, cbMonth,
            cbYear, cbVerification;

    public Profile_Page2() {

        super("Profile Settings");

        setSize(425, 917);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        // INITIALIZE ICONS
        full_Name_Icon = FontIcon.of(
                FontAwesomeSolid.USER, 20, Color.BLACK);

        username_Icon = FontIcon.of(
                FontAwesomeSolid.USER_TAG, 20, Color.BLACK);

        email_Icon = FontIcon.of(
                FontAwesomeSolid.ENVELOPE, 20, Color.BLACK);

        phone_Number_Icon = FontIcon.of(
                FontAwesomeSolid.PHONE, 20, Color.BLACK);

        change_Password_Icon = FontIcon.of(
                FontAwesomeSolid.LOCK, 20, Color.BLACK);

        home_Adress_Icon = FontIcon.of(
                FontAwesomeSolid.MAP_MARKER_ALT, 20, Color.BLACK);

        genderIcon = FontIcon.of(
                FontAwesomeSolid.VENUS_MARS, 20, Color.BLACK);

        date_of_birth_Icon = FontIcon.of(
                FontAwesomeSolid.CALENDAR_ALT, 20, Color.BLACK);

        account_status_Icon = FontIcon.of(
                FontAwesomeSolid.USER_CHECK, 20, Color.BLACK);

        backIcon = FontIcon.of(
                FontAwesomeSolid.ARROW_LEFT, 24, Color.BLACK);

        // LOAD LOGO SAFELY
        java.net.URL logoURL =
                getClass().getResource("/icons/plain_logo.png");

        if (logoURL != null) {
            logoIcon = new ImageIcon(logoURL);
        }

        // TOP BAR
        JPanel topPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10));

        topPanel.setBackground(BG_COLOR);

        JButton btBack = new JButton(backIcon);

        btBack.setBorderPainted(false);
        btBack.setContentAreaFilled(false);
        btBack.setFocusPainted(false);
        btBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btBack.addActionListener(e -> {

            Settings_Page sett = new Settings_Page();

            sett.showGui();
            sett.setSize(425, 917);
            sett.setLocationRelativeTo(null);
            sett.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sett.setVisible(true);

            this.dispose();
        });

        lbBack_Text = new JLabel("Profile Settings");

        lbBack_Text.setFont(
                new Font("Arial", Font.BOLD, 18));

        topPanel.add(btBack);
        topPanel.add(lbBack_Text);

        add(topPanel, BorderLayout.NORTH);

        // MAIN CONTENT
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.setBackground(BG_COLOR);

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // PROFILE PICTURE
        JPanel picPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER));

        picPanel.setBackground(BG_COLOR);

        lbProfile_Pic = new JLabel();

        updateProfilePicture();

        lbProfile_Pic.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        lbProfile_Pic.setToolTipText(
                "Click to change profile picture");

        lbProfile_Pic.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (Session.customer == null) {

                    JOptionPane.showMessageDialog(
                            Profile_Page2.this,
                            "Please log in first.");

                    return;
                }

                ProfilePictureDialog dialog =
                        new ProfilePictureDialog(
                                Profile_Page2.this,
                                "c",
                                Session.username(),
                                Session.name());

                dialog.setVisible(true);

                if (dialog.changed) {
                    updateProfilePicture();
                }
            }
        });

        picPanel.add(lbProfile_Pic);

        picPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(picPanel);

        contentPanel.add(Box.createVerticalStrut(20));

        // FULL NAME
        Object[] nameRow =
                createEditRow("Full Name", full_Name_Icon);

        contentPanel.add((JPanel) nameRow[0]);

        lbFull_Name_Val = (JLabel) nameRow[1];
        txtFull_Name = (JTextField) nameRow[2];

        contentPanel.add(Box.createVerticalStrut(10));

        // USERNAME
        Object[] userRow =
                createEditRow("Username", username_Icon);

        contentPanel.add((JPanel) userRow[0]);

        lbUsername_Val = (JLabel) userRow[1];
        txtUsername = (JTextField) userRow[2];

        contentPanel.add(Box.createVerticalStrut(10));

        // EMAIL
        Object[] emailRow =
                createEditRow("Email", email_Icon);

        contentPanel.add((JPanel) emailRow[0]);

        lbEmail_Val = (JLabel) emailRow[1];
        txtEmail = (JTextField) emailRow[2];

        contentPanel.add(Box.createVerticalStrut(10));

        // PHONE NUMBER
        Object[] phoneRow =
                createEditRow("Phone Number", phone_Number_Icon);

        contentPanel.add((JPanel) phoneRow[0]);

        lbPhone_Val = (JLabel) phoneRow[1];
        txtPhone_Number = (JTextField) phoneRow[2];

        contentPanel.add(Box.createVerticalStrut(10));

        // CHANGE PASSWORD
        contentPanel.add(
                createActionRow(
                        "Change Password",
                        change_Password_Icon,
                        () -> {

                            Change_Password_Page change =
                                    new Change_Password_Page();

                            change.setSize(425, 917);
                            change.setLocationRelativeTo(null);
                            change.setVisible(true);

                            this.dispose();
                        }));

        contentPanel.add(Box.createVerticalStrut(10));

        // HOME ADDRESS
        Object[] addressRow =
                createEditRow("Home Address", home_Adress_Icon);

        contentPanel.add((JPanel) addressRow[0]);

        lbHome_Val = (JLabel) addressRow[1];
        txtHome = (JTextField) addressRow[2];

        contentPanel.add(Box.createVerticalStrut(15));

        // VERIFICATION METHOD
        String[] verification = {
            "Choose Method", "Email", "SMS", "Call"
        };

        cbVerification = new JComboBox<>(verification);

        contentPanel.add(
                createComboRow(
                        "Verification Method",
                        genderIcon,
                        cbVerification));

        contentPanel.add(Box.createVerticalStrut(10));

        // DATE OF BIRTH
        JPanel dobPanel = new JPanel(new BorderLayout());

        dobPanel.setBackground(BG_COLOR);

        JLabel dobTitle = new JLabel(
                "Date of Birth",
                date_of_birth_Icon,
                SwingConstants.LEFT);

        dobTitle.setFont(
                new Font("Arial", Font.BOLD, 14));

        dobPanel.add(dobTitle, BorderLayout.NORTH);

        JPanel dobBoxes =
                new JPanel(new GridLayout(1, 3, 5, 0));

        dobBoxes.setBackground(BG_COLOR);

        String[] days = new String[32];

        days[0] = "Day";

        for (int i = 1; i <= 31; i++) {
            days[i] = String.valueOf(i);
        }

        cbDay = new JComboBox<>(days);

        String[] months = {
            "Month", "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"
        };

        cbMonth = new JComboBox<>(months);

        String[] years = new String[100];

        years[0] = "Year";

        for (int i = 1; i < 100; i++) {
            years[i] = String.valueOf(1920 + i);
        }

        cbYear = new JComboBox<>(years);

        dobBoxes.add(cbDay);
        dobBoxes.add(cbMonth);
        dobBoxes.add(cbYear);

        dobPanel.add(dobBoxes, BorderLayout.CENTER);

        contentPanel.add(dobPanel);

        contentPanel.add(Box.createVerticalStrut(10));

        // ACCOUNT STATUS
        contentPanel.add(
                createActionRow(
                        "Account Status",
                        account_status_Icon,
                        () -> JOptionPane.showMessageDialog(
                                this,
                                "Account Status: Active")));

        contentPanel.add(Box.createVerticalStrut(10));

        // GENDER
        String[] genders = {
            "Gender", "Female", "Male", "Other"
        };

        cbGender = new JComboBox<>(genders);

        contentPanel.add(
                createComboRow(
                        "Gender",
                        genderIcon,
                        cbGender));

        contentPanel.add(Box.createVerticalStrut(25));

        // ACTION BUTTONS
        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 2, 10, 0));

        buttonPanel.setBackground(BG_COLOR);

        buttonPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 45));

        JButton btSave = new JButton("Save Changes");

        btSave.setBackground(Color.ORANGE);

        btSave.setFont(
                new Font("Arial", Font.BOLD, 14));

        btSave.setFocusPainted(false);

        btSave.addActionListener(e -> saveProfile());

        JButton btCancel = new JButton("Cancel");

        btCancel.setBackground(Color.LIGHT_GRAY);

        btCancel.setFont(
                new Font("Arial", Font.BOLD, 14));

        btCancel.setFocusPainted(false);

        btCancel.addActionListener(e -> {

            loadProfile();

            JOptionPane.showMessageDialog(
                    this,
                    "Changes cancelled.");
        });

        buttonPanel.add(btSave);
        buttonPanel.add(btCancel);

        contentPanel.add(buttonPanel);

        contentPanel.add(Box.createVerticalStrut(20));

        // BOTTOM LOGO
        JPanel logoPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));

        logoPanel.setBackground(BG_COLOR);

        if (logoIcon != null) {

            Image image = logoIcon.getImage().getScaledInstance(
                    100, 100, Image.SCALE_SMOOTH);

            lbLogo = new JLabel(new ImageIcon(image));

        } else {

            lbLogo = new JLabel();
        }

        logoPanel.add(lbLogo);

        contentPanel.add(logoPanel);

        // SCROLL PANE
        JScrollPane scrollPane =
                new JScrollPane(contentPanel);

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // LOAD CUSTOMER INFORMATION
        loadProfile();
    }

    // CREATE EDITABLE ROWS
    private Object[] createEditRow(
            String title, FontIcon icon) {

        JPanel row = new JPanel(new BorderLayout(10, 0));

        row.setBackground(BG_COLOR);

        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 45));

        JLabel titleLabel = new JLabel(
                title, icon, SwingConstants.LEFT);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 14));

        row.add(titleLabel, BorderLayout.WEST);

        // VALUE PANEL
        JPanel valuePanel = new JPanel(new CardLayout());

        valuePanel.setBackground(BG_COLOR);

        // EDIT LABEL - SMALL
        JLabel valLabel = new JLabel("Edit");

        valLabel.setFont(
                new Font("Arial", Font.PLAIN, 12));

        valLabel.setForeground(Color.DARK_GRAY);

        valLabel.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        // TEXT FIELD - HIDDEN INITIALLY
        JTextField textField = new JTextField();

        textField.setFont(
                new Font("Arial", Font.PLAIN, 14));

        textField.setPreferredSize(
                new Dimension(190, 32));

        valuePanel.add(valLabel, "LABEL");
        valuePanel.add(textField, "FIELD");

        CardLayout cl =
                (CardLayout) valuePanel.getLayout();

        // CLICK EDIT TO SHOW TEXT FIELD
        valLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                String currentValue = valLabel.getText();

                textField.setText(
                        currentValue.equals("Edit")
                        ? ""
                        : currentValue);

                cl.show(valuePanel, "FIELD");

                textField.requestFocusInWindow();
            }
        });

        // PRESS ENTER TO HIDE TEXT FIELD
        textField.addActionListener(e -> {

            String value = textField.getText().trim();

            valLabel.setText(
                    value.isEmpty() ? "Edit" : value);

            cl.show(valuePanel, "LABEL");
        });

        // CLICK OUTSIDE TO HIDE TEXT FIELD
        textField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {

                String value = textField.getText().trim();

                valLabel.setText(
                        value.isEmpty() ? "Edit" : value);

                cl.show(valuePanel, "LABEL");
            }
        });

        row.add(valuePanel, BorderLayout.EAST);

        return new Object[]{
            row, valLabel, textField
        };
    }

    // ACTION ROW
    private JPanel createActionRow(
            String title, FontIcon icon, Runnable onClick) {

        JPanel row = new JPanel(new BorderLayout());

        row.setBackground(BG_COLOR);

        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40));

        JLabel label = new JLabel(
                title + "  >", icon, SwingConstants.LEFT);

        label.setFont(
                new Font("Arial", Font.BOLD, 14));

        label.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });

        row.add(label, BorderLayout.WEST);

        return row;
    }

    // COMBO BOX ROW
    private JPanel createComboRow(
            String title,
            FontIcon icon,
            JComboBox<String> comboBox) {

        JPanel row = new JPanel(new BorderLayout());

        row.setBackground(BG_COLOR);

        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40));

        JLabel label = new JLabel(
                title, icon, SwingConstants.LEFT);

        label.setFont(
                new Font("Arial", Font.BOLD, 14));

        comboBox.setBackground(Color.WHITE);

        comboBox.setPreferredSize(
                new Dimension(140, 25));

        row.add(label, BorderLayout.WEST);

        row.add(comboBox, BorderLayout.EAST);

        return row;
    }

    // UPDATE PROFILE PICTURE
    private void updateProfilePicture() {

        String username = Session.username();

        if (username == null) {
            username = "guest";
        }

        ImageIcon customPic =
                ProfilePics.circle("c", username, 100);

        if (customPic != null) {

            lbProfile_Pic.setIcon(customPic);

        } else {

            lbProfile_Pic.setIcon(
                    FontIcon.of(
                            FontAwesomeSolid.USER_CIRCLE,
                            100,
                            Color.BLACK));
        }
    }

    // LOAD PROFILE DIRECTLY FROM CUSTOMERS TABLE
    private void loadProfile() {

        if (Session.customer == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please log in first.");

            return;
        }

        String sql =
                "SELECT FULL_NAME, USERNAME, EMAIL, PHONE, ADDRESS "
                + "FROM CUSTOMERS WHERE ID = ?";

        try (Connection con = DAO.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Session.customer.id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String name = rs.getString("FULL_NAME");
                    String username = rs.getString("USERNAME");
                    String email = rs.getString("EMAIL");
                    String phone = rs.getString("PHONE");
                    String address = rs.getString("ADDRESS");

                    if (name == null) name = "";
                    if (username == null) username = "";
                    if (email == null) email = "";
                    if (phone == null) phone = "";
                    if (address == null) address = "";

                    lbFull_Name_Val.setText(
                            name.isEmpty() ? "Edit" : name);

                    lbUsername_Val.setText(
                            username.isEmpty() ? "Edit" : username);

                    lbEmail_Val.setText(
                            email.isEmpty() ? "Edit" : email);

                    lbPhone_Val.setText(
                            phone.isEmpty() ? "Edit" : phone);

                    lbHome_Val.setText(
                            address.isEmpty() ? "Edit" : address);

                    Session.customer.fullName = name;
                    Session.customer.username = username;
                    Session.customer.email = email;
                    Session.customer.phone = phone;
                    Session.customer.address = address;

                    updateProfilePicture();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Customer profile was not found.");
                }
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load profile:\n" + ex.getMessage());
        }
    }

    // SAVE PROFILE TO DATABASE
    private void saveProfile() {

        if (Session.customer == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please log in first.");

            return;
        }

        // READ CURRENT VALUES
        String newName = getFieldValue(
                txtFull_Name, lbFull_Name_Val);

        String newUser = getFieldValue(
                txtUsername, lbUsername_Val);

        String newEmail = getFieldValue(
                txtEmail, lbEmail_Val);

        String newPhone = getFieldValue(
                txtPhone_Number, lbPhone_Val);

        String newAddress = getFieldValue(
                txtHome, lbHome_Val);

        // REQUIRED FIELDS
        if (newName.isEmpty()
                || newUser.isEmpty()
                || newEmail.isEmpty()
                || newPhone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields "
                    + "(Name, Username, Email, Phone).");

            return;
        }

        String oldUsername = Session.customer.username;

        String customerSQL =
                "UPDATE CUSTOMERS SET "
                + "FULL_NAME = ?, "
                + "USERNAME = ?, "
                + "EMAIL = ?, "
                + "PHONE = ?, "
                + "ADDRESS = ? "
                + "WHERE ID = ?";

        String usersSQL =
                "UPDATE users SET username = ?, email = ? "
                + "WHERE username = ?";

        try (Connection con = DAO.connect()) {

            con.setAutoCommit(false);

            try {

                // UPDATE CUSTOMERS TABLE
                try (PreparedStatement ps =
                        con.prepareStatement(customerSQL)) {

                    ps.setString(1, newName);
                    ps.setString(2, newUser);
                    ps.setString(3, newEmail);
                    ps.setString(4, newPhone);
                    ps.setString(5, newAddress);
                    ps.setString(6, Session.customer.id);

                    int rows = ps.executeUpdate();

                    if (rows == 0) {

                        throw new SQLException(
                                "Customer record was not found.");
                    }
                }

                // UPDATE MATCHING USERS TABLE RECORD
                try (PreparedStatement ps =
                        con.prepareStatement(usersSQL)) {

                    ps.setString(1, newUser);
                    ps.setString(2, newEmail);
                    ps.setString(3, oldUsername);

                    ps.executeUpdate();
                }

                con.commit();

                // UPDATE SESSION
                Session.customer.fullName = newName;
                Session.customer.username = newUser;
                Session.customer.email = newEmail;
                Session.customer.phone = newPhone;
                Session.customer.address = newAddress;

                // UPDATE DISPLAY
                loadProfile();

                JOptionPane.showMessageDialog(
                        this,
                        "Profile updated successfully!");

            } catch (SQLException ex) {

                con.rollback();

                throw ex;

            } finally {

                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error:\n" + ex.getMessage());
        }
    }

    // GET VALUE FROM LABEL OR TEXT FIELD
    private String getFieldValue(
            JTextField field, JLabel label) {

        if (field.isFocusOwner()) {
            return field.getText().trim();
        }

        String value = label.getText().trim();

        if (value.equals("Edit")) {
            return "";
        }

        return value;
    }
}
