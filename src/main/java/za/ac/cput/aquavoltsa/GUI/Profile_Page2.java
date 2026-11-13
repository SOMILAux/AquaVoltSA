package za.ac.cput.aquavoltsa.GUI;

import za.ac.cput.aquavoltsa.DAO.DAO;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class Profile_Page2 extends JFrame {

    // The exact original light blue color
    private final Color BG_COLOR = new Color(173, 216, 230);

    private JLabel lbProfile_Pic, lbLogo;
    private ImageIcon logoIcon;
    
    // Icons
    private FontIcon full_Name_Icon, username_Icon, email_Icon, 
            phone_Number_Icon, change_Password_Icon, home_Adress_Icon, 
            genderIcon, date_of_birth_Icon, account_status_Icon, backIcon;

    // UI Elements
    private JLabel lbBack_Text;
    private JTextField txtFull_Name, txtUsername, txtEmail, txtPhone_Number, txtHome;
    private JLabel lbFull_Name_Val, lbUsername_Val, lbEmail_Val, lbPhone_Val, lbHome_Val;
    
    private JComboBox<String> cbGender, cbDay, cbMonth, cbYear, cbVerification;

    public Profile_Page2() {
        super("Profile Settings");
        setSize(425, 917);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        // --- INITIALIZE ICONS ---
        full_Name_Icon = FontIcon.of(FontAwesomeSolid.USER, 20, Color.BLACK);
        username_Icon = FontIcon.of(FontAwesomeSolid.USER_TAG, 20, Color.BLACK);
        email_Icon = FontIcon.of(FontAwesomeSolid.ENVELOPE, 20, Color.BLACK);
        phone_Number_Icon = FontIcon.of(FontAwesomeSolid.PHONE, 20, Color.BLACK);
        change_Password_Icon = FontIcon.of(FontAwesomeSolid.LOCK, 20, Color.BLACK);
        home_Adress_Icon = FontIcon.of(FontAwesomeSolid.MAP_MARKER_ALT, 20, Color.BLACK);
        genderIcon = FontIcon.of(FontAwesomeSolid.VENUS_MARS, 20, Color.BLACK);
        date_of_birth_Icon = FontIcon.of(FontAwesomeSolid.CALENDAR_ALT, 20, Color.BLACK);
        account_status_Icon = FontIcon.of(FontAwesomeSolid.USER_CHECK, 20, Color.BLACK);
        backIcon = FontIcon.of(FontAwesomeSolid.ARROW_LEFT, 24, Color.BLACK);

        logoIcon = new ImageIcon(getClass().getResource("/icons/plain_logo.png"));

        // --- TOP BAR (Back Button & Title) ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(BG_COLOR);
        
        JButton btBack = new JButton(backIcon);
        btBack.setBorderPainted(false);
        btBack.setContentAreaFilled(false);
        btBack.setFocusPainted(false);
        btBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btBack.addActionListener(e -> {
            Settings_Page sett = new Settings_Page();
            sett.showGui();
            sett.setVisible(true);
            this.dispose();
        });

        lbBack_Text = new JLabel("Profile Settings");
        lbBack_Text.setFont(new Font("Arial", Font.BOLD, 18));
        
        topPanel.add(btBack);
        topPanel.add(lbBack_Text);
        add(topPanel, BorderLayout.NORTH);

        // --- MAIN SCROLLABLE CONTENT ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // 1. Large Profile Picture (Clickable to change)
        JPanel picPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        picPanel.setBackground(BG_COLOR);
        
        lbProfile_Pic = new JLabel();
        updateProfilePicture();
        lbProfile_Pic.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbProfile_Pic.setToolTipText("Click to change profile picture");
        lbProfile_Pic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Session.customer == null) {
                    JOptionPane.showMessageDialog(Profile_Page2.this, "Please log in first.");
                    return;
                }
                ProfilePictureDialog dialog = new ProfilePictureDialog(Profile_Page2.this, "c", Session.username(), Session.name());
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

        // 2. Form Rows
        // Full Name
        Object[] nameRow = createEditRow("Full Name", full_Name_Icon);
        contentPanel.add((JPanel) nameRow[0]);
        lbFull_Name_Val = (JLabel) nameRow[1];
        txtFull_Name = (JTextField) nameRow[2];
        contentPanel.add(Box.createVerticalStrut(10));

        // Username
        Object[] userRow = createEditRow("Username", username_Icon);
        contentPanel.add((JPanel) userRow[0]);
        lbUsername_Val = (JLabel) userRow[1];
        txtUsername = (JTextField) userRow[2];
        contentPanel.add(Box.createVerticalStrut(10));

        // Email
        Object[] emailRow = createEditRow("Email", email_Icon);
        contentPanel.add((JPanel) emailRow[0]);
        lbEmail_Val = (JLabel) emailRow[1];
        txtEmail = (JTextField) emailRow[2];
        contentPanel.add(Box.createVerticalStrut(10));

        // Phone
        Object[] phoneRow = createEditRow("Phone Number", phone_Number_Icon);
        contentPanel.add((JPanel) phoneRow[0]);
        lbPhone_Val = (JLabel) phoneRow[1];
        txtPhone_Number = (JTextField) phoneRow[2];
        contentPanel.add(Box.createVerticalStrut(10));

        // Change Password (Action Row)
        contentPanel.add(createActionRow("Change Password", change_Password_Icon, () -> {
            Change_Password_Page change = new Change_Password_Page();
            change.setVisible(true);
            this.dispose();
        }));
        contentPanel.add(Box.createVerticalStrut(10));

        // Home Address
        Object[] addressRow = createEditRow("Home Address", home_Adress_Icon);
        contentPanel.add((JPanel) addressRow[0]);
        lbHome_Val = (JLabel) addressRow[1];
        txtHome = (JTextField) addressRow[2];
        contentPanel.add(Box.createVerticalStrut(15));

        // 3. Combo Boxes (Verification, DOB, Gender) - Rendered as clean rows
        String[] verification = {"Choose Method", "Email", "SMS", "Call"};
        cbVerification = new JComboBox<>(verification);
        contentPanel.add(createComboRow("Verification Method", genderIcon, cbVerification));
        contentPanel.add(Box.createVerticalStrut(10));

        // Date of Birth
        JPanel dobPanel = new JPanel(new BorderLayout());
        dobPanel.setBackground(BG_COLOR);
        JLabel dobTitle = new JLabel("Date of Birth", date_of_birth_Icon, SwingConstants.LEFT);
        dobTitle.setFont(new Font("Arial", Font.BOLD, 14));
        dobPanel.add(dobTitle, BorderLayout.NORTH);
        
        JPanel dobBoxes = new JPanel(new GridLayout(1, 3, 5, 0));
        dobBoxes.setBackground(BG_COLOR);
        String[] days = new String[32]; days[0] = "Day";
        for(int i=1; i<=31; i++) days[i] = String.valueOf(i);
        cbDay = new JComboBox<>(days);
        
        String[] months = {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        cbMonth = new JComboBox<>(months);
        
        String[] years = new String[100]; years[0] = "Year";
        for(int i=1; i<100; i++) years[i] = String.valueOf(1920 + i);
        cbYear = new JComboBox<>(years);

        dobBoxes.add(cbDay);
        dobBoxes.add(cbMonth);
        dobBoxes.add(cbYear);
        dobPanel.add(dobBoxes, BorderLayout.CENTER);
        contentPanel.add(dobPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        // Account Status
        contentPanel.add(createActionRow("Account Status", account_status_Icon, () -> {
            JOptionPane.showMessageDialog(this, "Account Status: Active");
        }));
        contentPanel.add(Box.createVerticalStrut(10));

        // Gender
        String[] genders = {"Gender", "Female", "Male", "Other"};
        cbGender = new JComboBox<>(genders);
        contentPanel.add(createComboRow("Gender", genderIcon, cbGender));
        contentPanel.add(Box.createVerticalStrut(25));

        // 4. Action Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton btSave = new JButton("Save Changes");
        btSave.setBackground(Color.ORANGE);
        btSave.setFont(new Font("Arial", Font.BOLD, 14));
        btSave.setFocusPainted(false);
        btSave.addActionListener(e -> saveProfile());

        JButton btCancel = new JButton("Cancel");
        btCancel.setBackground(Color.LIGHT_GRAY);
        btCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btCancel.setFocusPainted(false);
        btCancel.addActionListener(e -> {
            loadProfile();
            JOptionPane.showMessageDialog(this, "Changes cancelled.");
        });

        buttonPanel.add(btSave);
        buttonPanel.add(btCancel);
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // 5. Bottom Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(BG_COLOR);
        Image image = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        lbLogo = new JLabel(new ImageIcon(image));
        logoPanel.add(lbLogo);
        contentPanel.add(logoPanel);

        // --- ADD TO SCROLL PANE ---
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load actual data from database
        loadProfile();
    }

    // Helper to create the clean edit rows (Label -> TextField on click)
    private Object[] createEditRow(String title, FontIcon icon) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(BG_COLOR);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel titleLabel = new JLabel(title, icon, SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        row.add(titleLabel, BorderLayout.WEST);

        JPanel valuePanel = new JPanel(new CardLayout());
        valuePanel.setBackground(BG_COLOR);

        JLabel valLabel = new JLabel("Not set");
        valLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));

        valuePanel.add(valLabel, "LABEL");
        valuePanel.add(textField, "FIELD");

        CardLayout cl = (CardLayout) valuePanel.getLayout();

        // Click to edit
        valLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText(valLabel.getText().equals("Not set") ? "" : valLabel.getText());
                cl.show(valuePanel, "FIELD");
                textField.requestFocus();
            }
        });

        // Enter or click away to save the edit
        textField.addActionListener(e -> {
            valLabel.setText(textField.getText().trim().isEmpty() ? "Not set" : textField.getText().trim());
            cl.show(valuePanel, "LABEL");
        });
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                valLabel.setText(textField.getText().trim().isEmpty() ? "Not set" : textField.getText().trim());
                cl.show(valuePanel, "LABEL");
            }
        });

        row.add(valuePanel, BorderLayout.EAST);
        return new Object[]{row, valLabel, textField};
    }

    // Helper for rows that just open a page
    private JPanel createActionRow(String title, FontIcon icon, Runnable onClick) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(BG_COLOR);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel label = new JLabel(title + "  >", icon, SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });

        row.add(label, BorderLayout.WEST);
        return row;
    }

    // Helper for rows with Combo Boxes
    private JPanel createComboRow(String title, FontIcon icon, JComboBox<String> comboBox) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(BG_COLOR);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel label = new JLabel(title, icon, SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        
        comboBox.setBackground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(140, 25));

        row.add(label, BorderLayout.WEST);
        row.add(comboBox, BorderLayout.EAST);
        return row;
    }

    private void updateProfilePicture() {
        String username = Session.username();
        if (username == null) username = "guest";
        
        ImageIcon customPic = ProfilePics.circle("c", username, 100);
        if (customPic != null) {
            lbProfile_Pic.setIcon(customPic);
        } else {
            lbProfile_Pic.setIcon(FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 100, Color.BLACK));
        }
    }

    private void loadProfile() {
        if (Session.customer == null) return;

        UserStore.User user = UserStore.find(Session.username());
        if (user != null) {
            lbFull_Name_Val.setText(user.fullName.isEmpty() ? "Not set" : user.fullName);
            lbUsername_Val.setText(user.username.isEmpty() ? "Not set" : user.username);
            lbEmail_Val.setText(user.email.isEmpty() ? "Not set" : user.email);
            lbPhone_Val.setText(user.phone.isEmpty() ? "Not set" : user.phone);
            lbHome_Val.setText(user.address.isEmpty() ? "Not set" : user.address);
        }
    }

    private void saveProfile() {
        if (Session.customer == null) return;

        // Read from visible text fields or labels
        String newName = txtFull_Name.isShowing() ? txtFull_Name.getText().trim() : lbFull_Name_Val.getText();
        String newUser = txtUsername.isShowing() ? txtUsername.getText().trim() : lbUsername_Val.getText();
        String newEmail = txtEmail.isShowing() ? txtEmail.getText().trim() : lbEmail_Val.getText();
        String newPhone = txtPhone_Number.isShowing() ? txtPhone_Number.getText().trim() : lbPhone_Val.getText();
        String newAddress = txtHome.isShowing() ? txtHome.getText().trim() : lbHome_Val.getText();

        if (newName.equals("Not set") || newUser.equals("Not set") || newEmail.equals("Not set") || newPhone.equals("Not set")) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (Name, Username, Email, Phone).");
            return;
        }

        String sql = "UPDATE customers SET full_name = ?, username = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        try (Connection con = DAO.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, newUser);
            ps.setString(3, newEmail);
            ps.setString(4, newPhone);
            ps.setString(5, newAddress.equals("Not set") ? "" : newAddress);
            ps.setString(6, Session.customer.id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                // Update Session so Dashboard reflects new name instantly
                Session.customer.fullName = newName;
                Session.customer.username = newUser;
                Session.customer.email = newEmail;
                Session.customer.phone = newPhone;
                Session.customer.address = newAddress.equals("Not set") ? "" : newAddress;

                loadProfile();
                updateProfilePicture(); 
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Could not update profile.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}