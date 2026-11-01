

package za.ac.cput.aquavoltsa.GUI;
import za.ac.cput.aquavoltsa.GUI.Settings_Page;
import za.ac.cput.aquavoltsa.GUI.Change_Password_Page;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Profile_Page2 extends JFrame {

    private ImageIcon profile_picture_Icon, full_Name_Icon, username_Icon,
            email_Icon, phone_Number_Icon, change_Password_Icon,
            home_Adress_Icon;

    private JLabel lbBack_Text, lbJust_blank, lbFull_Name, lbLogo,
            lbUsername, lbEmail_Adress, lbPhone_Number,
            lbChange_Password, lbHome_Adress, lbProfile_Settings;

    private ImageIcon backIcon, genderIcon, date_of_birth_Icon,
            account_status_Icon, logoIcon;

    private JLabel lbDate_of_birth, lbAccount_status, lbKeep_secure,
            lbVerification, lbBlank, lbGender;

    private JComboBox cbGender, cbDay, cbMonth, cbYear, cbG;

    private JButton btSave_Changes, btCancel, btBack;

    private JTextField txtFull_Name, txtUsername, txtEmail,
            txtPhone_Number, txtHome;

    private JTextArea txtHome_Adress;
    private JRadioButton btReset;

    private JPanel panel_1, panel_2, panel_3, panel_4,
            inner_panel, panel_another4, panel_5, panel_6;

    public Profile_Page2() {
        super("Profile_Setings");

        ImageIcon profile_picture_Icon = new ImageIcon(
                getClass().getResource("/icons/picture.png"));
        Image img = profile_picture_Icon.getImage();
        Image resizedImg = img.getScaledInstance(
                300, 150, Image.SCALE_SMOOTH);
        profile_picture_Icon = new ImageIcon(resizedImg);

        full_Name_Icon = new ImageIcon(
                getClass().getResource("/icons/picture.png"));
        username_Icon = new ImageIcon(
                getClass().getResource("/icons/picture.png"));
        email_Icon = new ImageIcon(
                getClass().getResource("/icons/email.png"));
        phone_Number_Icon = new ImageIcon(
                getClass().getResource("/icons/phone.png"));
        change_Password_Icon = new ImageIcon(
                getClass().getResource("/icons/lock.png"));
        backIcon = new ImageIcon(
                getClass().getResource("/icons/back.png"));
        home_Adress_Icon = new ImageIcon(
                getClass().getResource("/icons/location.png"));
        genderIcon = new ImageIcon(
                getClass().getResource("/icons/profile.png"));
        date_of_birth_Icon = new ImageIcon(
                getClass().getResource("/icons/calendar.png"));
        account_status_Icon = new ImageIcon(
                getClass().getResource("/icons/account.png"));
        logoIcon = new ImageIcon(
                getClass().getResource("/icons/logo.png"));

        lbFull_Name = new JLabel("Full Name");
        lbUsername = new JLabel("Username");
        lbEmail_Adress = new JLabel("Email");
        lbPhone_Number = new JLabel("Phone Number");
        lbChange_Password = new JLabel("Change Password");
        lbHome_Adress = new JLabel("Home Adress");
        lbProfile_Settings = new JLabel(" .");
        lbJust_blank = new JLabel(" .");
        lbBack_Text = new JLabel("Profile Settings");
        lbVerification = new JLabel("Verification Method");
        lbDate_of_birth = new JLabel("Date of birth ");
        lbAccount_status = new JLabel("Account Status");
        lbKeep_secure = new JLabel(" Keep Account secured !");
        lbBlank = new JLabel("XXXXXXXXXXXXXXXXXXXXXXXX");
        lbGender = new JLabel("Gender");
        lbLogo = new JLabel(" ");

        btSave_Changes = new JButton("Save Changes");
        btCancel = new JButton("Cancel");
        btBack = new JButton(" ");
        btReset = new JRadioButton("Reset");

        txtFull_Name = new JTextField("Edit");
        txtFull_Name.setFont(new Font("Arial", Font.PLAIN, 20));
        txtFull_Name.setBackground(new Color(173, 216, 230));
        txtFull_Name.setVisible(false);
        txtFull_Name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredName = txtFull_Name.getText();
                if (!enteredName.trim().isEmpty()) {
                    lbFull_Name.setText(enteredName);
                    txtFull_Name.setVisible(false);
                }
            }
        });

        txtUsername = new JTextField("Edit");
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 20));
        txtUsername.setBackground(new Color(173, 216, 230));
        txtUsername.setVisible(false);
        txtUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = txtUsername.getText();
                if (!enteredUsername.trim().isEmpty()) {
                    lbUsername.setText(enteredUsername);
                    txtUsername.setVisible(false);
                }
            }
        });

        txtEmail = new JTextField("Edit");
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 20));
        txtEmail.setBackground(new Color(173, 216, 230));
        txtEmail.setVisible(false);
        txtEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredEmail = txtEmail.getText();
                if (!enteredEmail.trim().isEmpty()) {
                    lbEmail_Adress.setText(enteredEmail);
                    txtEmail.setVisible(false);
                }
            }
        });

        txtPhone_Number = new JTextField("Edit");
        txtPhone_Number.setFont(new Font("Arial", Font.PLAIN, 20));
        txtPhone_Number.setBackground(new Color(173, 216, 230));
        txtPhone_Number.setVisible(false);
        txtPhone_Number.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPhone = txtPhone_Number.getText();
                if (!enteredPhone.trim().isEmpty()) {
                    lbPhone_Number.setText(enteredPhone);
                    txtPhone_Number.setVisible(false);
                }
            }
        });

        txtHome = new JTextField("Home Adress");
        txtHome.setFont(new Font("Arial", Font.PLAIN, 20));
        txtHome.setBackground(new Color(173, 216, 230));
        txtHome.setVisible(false);
        txtHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredAdress = txtHome.getText();
                if (!enteredAdress.trim().isEmpty()) {
                    lbHome_Adress.setText(enteredAdress);
                    txtHome.setVisible(false);
                }
            }
        });

        panel_1 = new JPanel();
        panel_2 = new JPanel();
        panel_3 = new JPanel();
        panel_4 = new JPanel();
        inner_panel = new JPanel();
        panel_another4 = new JPanel();
        panel_5 = new JPanel();
        panel_6 = new JPanel();

        String[] verification = {
            "Choose Method", "Email", "SMS", "Call"
        };
        cbGender = new JComboBox(verification);

        String[] Days = {
            "Days", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "30", "31"
        };
        cbDay = new JComboBox(Days);

        String[] Months = {
            "Months", "January", "Febuary", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"
        };
        cbMonth = new JComboBox(Months);

        String[] Years = {
            "Year", "1950", "1951", "1952", "1953", "1954",
            "1955", "1956", "1957", "1958", "1959", "1960",
            "1961", "1962", "1963", "1964", "1965", "1966",
            "1967", "1968", "1969", "1970", "1971", "1972",
            "1973", "1974", "1975", "1976", "1977", "1978",
            "1979", "1980", "1981", "1982", "1983", "1984",
            "1985", "1986", "1987", "1988", "1989", "1990",
            "1991", "1992", "1993", "1994", "1995", "1996",
            "1997", "1998", "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006", "2007", "2008",
            "2009", "2010", "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018", "2019", "2020",
            "2021", "2022", "2023", "2024", "2025", "2026",
            "2027", "2028", "2029", "2030", "2031", "2032",
            "2033", "2034", "2035", "2036", "2037", "2038",
            "2039", "2040"
        };
        cbYear = new JComboBox(Years);

        String[] gender = {"Gender", "Female", "Male", "Other"};
        cbG = new JComboBox(gender);

        this.setLayout(new GridLayout(8, 1));

        btBack.setIcon(backIcon);
        btBack.setBackground(new Color(173, 216, 230));

        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings_Page sett = new Settings_Page();
                sett.showGui();
                sett.setSize(600, 810);
                sett.setLocationRelativeTo(null);
                sett.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sett.setVisible(true);
                dispose();
            }
        });

        panel_1.add(btBack);
        lbBack_Text.setFont(new Font("Arial", Font.BOLD, 30));
        panel_1.setBackground(new Color(173, 216, 230));
        panel_1.setLayout(new GridLayout(2, 1));
        panel_1.add(lbBack_Text);
        panel_1.setLayout(new FlowLayout());
        this.add(panel_1);

        lbProfile_Settings.setIcon(profile_picture_Icon);
        panel_2.add(lbProfile_Settings);
        panel_2.setBackground(new Color(173, 216, 230));
        panel_2.setLayout(new FlowLayout());
        this.add(panel_2);

        lbFull_Name.setIcon(full_Name_Icon);
        lbFull_Name.setFont(new Font("Arial", Font.BOLD, 20));
        lbFull_Name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtFull_Name.setVisible(true);
                txtFull_Name.requestFocus();
            }
        });
        panel_3.add(lbFull_Name);
        panel_3.add(txtFull_Name);

        lbUsername.setIcon(username_Icon);
        lbUsername.setFont(new Font("Arial", Font.BOLD, 20));
        lbUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUsername.setVisible(true);
                txtUsername.requestFocus();
            }
        });
        panel_3.add(lbUsername);
        panel_3.add(txtUsername);

        lbEmail_Adress.setIcon(email_Icon);
        lbEmail_Adress.setFont(new Font("Arial", Font.BOLD, 20));
        lbEmail_Adress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtEmail.setVisible(true);
                txtEmail.requestFocus();
            }
        });
        panel_3.add(lbEmail_Adress);
        panel_3.add(txtEmail);

        panel_3.setBackground(new Color(173, 216, 230));
        panel_3.setLayout(new GridLayout(4, 2));
        this.add(panel_3);

        lbPhone_Number.setIcon(phone_Number_Icon);
        lbPhone_Number.setFont(new Font("Arial", Font.BOLD, 20));
        lbPhone_Number.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtPhone_Number.setVisible(true);
                txtPhone_Number.requestFocus();
            }
        });
        panel_4.add(lbPhone_Number);
        panel_4.add(txtPhone_Number);

        lbChange_Password.setIcon(change_Password_Icon);
        lbChange_Password.setFont(new Font("Arial", Font.BOLD, 20));
        lbChange_Password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Change_Password_Page change = new Change_Password_Page();
                change.setSize(500, 800);
                change.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                change.setVisible(true);
                dispose();
            }
        });
        panel_4.add(lbChange_Password);

        panel_4.add(lbHome_Adress);
        lbHome_Adress.setIcon(home_Adress_Icon);
        lbHome_Adress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtHome.setVisible(true);
                txtHome.requestFocus();
            }
        });
        panel_4.add(txtHome);
        panel_4.setLayout(new GridLayout(3, 2, 3, 2));
        panel_4.setBackground(new Color(173, 216, 230));
        this.add(panel_4);

        lbVerification.setIcon(genderIcon);
        lbVerification.setFont(new Font("Arial", Font.BOLD, 20));
        panel_another4.add(lbVerification);
        panel_another4.add(cbGender);

        lbDate_of_birth.setIcon(date_of_birth_Icon);
        lbDate_of_birth.setFont(new Font("Arial", Font.BOLD, 20));
        panel_another4.add(lbDate_of_birth);

        lbAccount_status.setIcon(account_status_Icon);
        lbAccount_status.setFont(new Font("Arial", Font.BOLD, 20));
        panel_another4.add(lbAccount_status);
        panel_another4.setLayout(new GridLayout(3, 1));
        panel_another4.setBackground(new Color(173, 216, 230));
        this.add(panel_another4);

        inner_panel.add(cbDay);
        inner_panel.add(new JLabel("   "));
        inner_panel.add(cbMonth);
        inner_panel.add(new JLabel("  "));
        inner_panel.add(cbYear);
        inner_panel.add(new JLabel("   "));
        inner_panel.setLayout(new GridLayout(3, 2, 10, 10));
        inner_panel.setBackground(new Color(173, 216, 230));
        this.add(inner_panel);

        btSave_Changes.setBackground(Color.orange);
        btSave_Changes.setFont(new Font("Arial", Font.BOLD, 20));
        lbGender.setFont(new Font("Arial", Font.BOLD, 20));
        panel_5.add(lbGender);
        panel_5.add(cbG);
        panel_5.add(btSave_Changes);

        btCancel.setBackground(Color.gray);
        btCancel.setFont(new Font("Arial", Font.BOLD, 20));
        panel_5.add(btCancel);

        btSave_Changes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fullName = txtFull_Name.getText().trim();
                String username = txtUsername.getText().trim();
                String email = txtEmail.getText().trim();
                String phone = txtPhone_Number.getText().trim();
                String address = txtHome.getText().trim();

                String selectedGender = cbG.getSelectedItem().toString();
                String verification = cbGender.getSelectedItem().toString();

                String day = cbDay.getSelectedItem().toString();
                String month = cbMonth.getSelectedItem().toString();
                String year = cbYear.getSelectedItem().toString();

                if (fullName.isEmpty() || fullName.equals("Edit")) {
                    JOptionPane.showMessageDialog(Profile_Page2.this,
                            "Please enter your full name.");
                    return;
                }

                if (username.isEmpty() || username.equals("Edit")) {
                    JOptionPane.showMessageDialog(Profile_Page2.this,
                            "Please enter your username.");
                    return;
                }

                if (email.isEmpty() || email.equals("Edit")
                        || !email.contains("@")
                        || !email.contains(".")) {
                    JOptionPane.showMessageDialog(Profile_Page2.this,
                            "Please enter a valid email address.");
                    return;
                }

                if (selectedGender.equals("Gender")
                        || verification.equals("Choose Method")
                        || day.equals("Days")
                        || month.equals("Months")
                        || year.equals("Year")) {
                    JOptionPane.showMessageDialog(Profile_Page2.this,
                            "Please complete all required fields.");
                    return;
                }

                int monthNumber;

                switch (month) {
                    case "January": monthNumber = 1; break;
                    case "February":
                    case "Febuary": monthNumber = 2; break;
                    case "March": monthNumber = 3; break;
                    case "April": monthNumber = 4; break;
                    case "May": monthNumber = 5; break;
                    case "June": monthNumber = 6; break;
                    case "July": monthNumber = 7; break;
                    case "August": monthNumber = 8; break;
                    case "September": monthNumber = 9; break;
                    case "October": monthNumber = 10; break;
                    case "November": monthNumber = 11; break;
                    case "December": monthNumber = 12; break;
                    default:
                        JOptionPane.showMessageDialog(Profile_Page2.this,
                                "Please select a valid month.");
                        return;
                }

                String dateOfBirth = String.format(
                        "%04d-%02d-%02d",
                        Integer.parseInt(year),
                        monthNumber,
                        Integer.parseInt(day));

                String updateUserSql =
                        "UPDATE users SET username = ?, email = ? "
                        + "WHERE user_id = ?";

                String profileSql = "INSERT INTO user_profiles "
                        + "(user_id, full_name, phone_number, home_address, "
                        + "gender, data_of_birth, verification_method) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE "
                        + "full_name = VALUES(full_name), "
                        + "phone_number = VALUES(phone_number), "
                        + "home_address = VALUES(home_address), "
                        + "gender = VALUES(gender), "
                        + "data_of_birth = VALUES(data_of_birth), "
                        + "verification_method = VALUES(verification_method)";

                try (Connection connection =
                             DBConnection.getConnection()) {

                    if (connection == null) {
                        JOptionPane.showMessageDialog(
                                Profile_Page2.this,
                                "Database connection failed.");
                        return;
                    }

                    connection.setAutoCommit(false);

                    try {
                        try (PreparedStatement userStatement =
                                     connection.prepareStatement(updateUserSql)) {
                            userStatement.setString(1, username);
                            userStatement.setString(2, email);
                            userStatement.setInt(3, 1);

                            int userRows = userStatement.executeUpdate();

                            if (userRows == 0) {
                                throw new SQLException(
                                        "No user found with user_id = 1.");
                            }
                        }

                        try (PreparedStatement profileStatement =
                                     connection.prepareStatement(profileSql)) {
                            profileStatement.setInt(1, 1);
                            profileStatement.setString(2, fullName);
                            profileStatement.setString(3, phone);
                            profileStatement.setString(4, address);
                            profileStatement.setString(5, selectedGender);
                            profileStatement.setDate(
                                    6, java.sql.Date.valueOf(dateOfBirth));
                            profileStatement.setString(7, verification);

                            profileStatement.executeUpdate();
                        }

                        connection.commit();

                        lbFull_Name.setText(fullName);
                        lbUsername.setText(username);
                        lbEmail_Adress.setText(email);
                        lbPhone_Number.setText(phone);
                        lbHome_Adress.setText(address);

                        JOptionPane.showMessageDialog(
                                Profile_Page2.this,
                                "Profile saved successfully!");

                    } catch (SQLException | IllegalArgumentException ex) {
                        connection.rollback();

                        JOptionPane.showMessageDialog(
                                Profile_Page2.this,
                                "Could not save profile: " + ex.getMessage());
                    } finally {
                        connection.setAutoCommit(true);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                            Profile_Page2.this,
                            "Database error: " + ex.getMessage());
                }
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // loadProfile();

                txtFull_Name.setVisible(false);
                txtUsername.setVisible(false);
                txtEmail.setVisible(false);
                txtPhone_Number.setVisible(false);
                txtHome.setVisible(false);

                JOptionPane.showMessageDialog(Profile_Page2.this,
                        "Changes cancelled. Saved details restored.");
            }
        });

        panel_5.setBackground(new Color(173, 216, 230));
        panel_5.setLayout(new GridLayout(3, 1, 10, 10));
        this.add(panel_5);

        panel_6.setLayout(new GridLayout(1, 1));
        lbKeep_secure.setFont(new Font("Arial", Font.BOLD, 22));

        Image image = logoIcon.getImage().getScaledInstance(
                100, 100, Image.SCALE_SMOOTH);
        lbLogo.setIcon(new ImageIcon(image));
        lbLogo.setHorizontalAlignment(JLabel.CENTER);
        lbLogo.setVerticalAlignment(JLabel.CENTER);

        this.panel_6.add(lbLogo);
        panel_6.setBackground(new Color(173, 216, 230));
        this.add(panel_6);

       //loadProfile();
    }

    private void loadProfile() {

        String userSql =
                "SELECT username, email FROM users WHERE user_id = ?";

        String profileSql =
                "SELECT full_name, phone_number, home_address, gender, "
                + "data_of_birth, verification_method "
                + "FROM user_profiles WHERE user_id = ?";

        try (Connection connection =

                DBConnection.getConnection()) {
            

            if (connection == null) {
                JOptionPane.showMessageDialog(
                        this, "Database connection failed.");
                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(userSql)) {

                statement.setInt(1, 1);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        String username = result.getString("username");
                        String email = result.getString("email");

                        txtUsername.setText(
                                username == null ? "" : username);
                        txtEmail.setText(email == null ? "" : email);

                        lbUsername.setText(
                                username == null ? "Username" : username);
                        lbEmail_Adress.setText(
                                email == null ? "Email" : email);
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(profileSql)) {

                statement.setInt(1, 1);

                try (ResultSet result = statement.executeQuery()) {

                    if (result.next()) {
                        String fullName = result.getString("full_name");
                        String phone = result.getString("phone_number");
                        String address = result.getString("home_address");

                        txtFull_Name.setText(
                                fullName == null ? "" : fullName);
                        txtPhone_Number.setText(
                                phone == null ? "" : phone);
                        txtHome.setText(
                                address == null ? "" : address);

                        lbFull_Name.setText(
                                fullName == null ? "Full Name" : fullName);
                        lbPhone_Number.setText(
                                phone == null ? "Phone Number" : phone);
                        lbHome_Adress.setText(
                                address == null ? "Home Adress" : address);

                        String gender = result.getString("gender");
                        if (gender != null) {
                            cbG.setSelectedItem(gender);
                        }

                        String verification =
                                result.getString("verification_method");
                        if (verification != null) {
                            cbGender.setSelectedItem(verification);
                        }

                        java.sql.Date dob =
                                result.getDate("data_of_birth");

                        if (dob != null) {
                            cbDay.setSelectedItem(
                                    String.valueOf(
                                            dob.toLocalDate().getDayOfMonth()));

                            String monthName = dob.toLocalDate()
                                    .getMonth()
                                    .getDisplayName(
                                            java.time.format.TextStyle.FULL,
                                            java.util.Locale.ENGLISH);

                            if (monthName.equals("February")) {
                                monthName = "Febuary";
                            }

                            cbMonth.setSelectedItem(monthName);
                            cbYear.setSelectedItem(
                                    String.valueOf(
                                            dob.toLocalDate().getYear()));
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load profile: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Profile_Page2 profile = new Profile_Page2();
        profile.setSize(600, 800);
        profile.setLocationRelativeTo(null);
        profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profile.setVisible(true);
    }
        }
