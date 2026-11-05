
// This class displays the terms and conditions of the AquaVolt SA application.
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import za.ac.cput.aquavoltsa.GUI.Settings_Page;

public class Terms_and_Conditions extends JFrame {

    private JLabel lbJust_Blank, lbTerms;
    private JCheckBox cbI_Agree, cbI_Disagree;
    private JPanel panel_1, panel_2, panelTop, panelBottom;
    private ImageIcon logoIcon;
    private JButton btContinue, btBack;

    public Terms_and_Conditions() {

        setTitle("Terms and Conditions");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lbJust_Blank = new JLabel(" ");
        cbI_Agree = new JCheckBox("I Agree");
        cbI_Disagree = new JCheckBox("I Disagree");

        // Only one checkbox can be selected at a time
        cbI_Agree.addActionListener(e -> {
            if (cbI_Agree.isSelected()) {
                cbI_Disagree.setSelected(false);
            }
        });

        cbI_Disagree.addActionListener(e -> {
            if (cbI_Disagree.isSelected()) {
                cbI_Agree.setSelected(false);
            }
        });

        panel_1 = new JPanel(new BorderLayout());
        panel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelTop = new JPanel(new BorderLayout());
        panelBottom = new JPanel();

        btContinue = new JButton("Continue");
        btBack = new JButton();

        // Orange button styling
        btContinue.setBackground(new Color(255, 140, 0));
        btContinue.setForeground(Color.BLACK);
        btContinue.setFont(new Font("Arial", Font.BOLD, 16));
        btContinue.setFocusPainted(false);
        btContinue.setOpaque(true);
        btContinue.setBorderPainted(false);
        btContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btContinue.setPreferredSize(new Dimension(280, 45));

        // Load logo
        java.net.URL logoURL = getClass().getResource("/icons/logo.png");

        if (logoURL != null) {
            logoIcon = new ImageIcon(logoURL);
            Image image = logoIcon.getImage().getScaledInstance(
                    140, 140, Image.SCALE_SMOOTH);
            lbJust_Blank.setIcon(new ImageIcon(image));
        }

        lbJust_Blank.setHorizontalAlignment(JLabel.CENTER);
        lbJust_Blank.setVerticalAlignment(JLabel.CENTER);

        // Back icon in the TOP-LEFT corner
        java.net.URL backURL = getClass().getResource("/icons/back.png");

        if (backURL != null) {
            ImageIcon originalBackIcon = new ImageIcon(backURL);
            Image scaledBack = originalBackIcon.getImage()
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            btBack.setIcon(new ImageIcon(scaledBack));
        } else {
            btBack.setText("←");
            btBack.setFont(new Font("Arial", Font.BOLD, 24));
        }

        btBack.setFocusPainted(false);
        btBack.setBorderPainted(false);
        btBack.setContentAreaFilled(false);
        btBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btBack.addActionListener(e -> {
            Settings_Page page = new Settings_Page();
            page.showGui();
            page.setSize(600, 810);
          //page.setLocationRelativeTo(null);
            page.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            page.setVisible(true);

        // Terms and Conditions text
        lbTerms = new JLabel(
            "<html><div style='text-align: center;'>"
            + "<h2>Terms and Conditions</h2>"
            + "<p>Welcome to AquaVoltsa.</p>"
            + "<p>By using this application, you agree to use your account responsibly "
            + "and provide correct information.</p>"
            + "<p>You are responsible for keeping your username and password secure.</p>"
            + "<p>Your account information should not be shared with other people.</p>"
            + "<p>You agree to follow the rules and requirements of the AquaVoltsa application.</p>"
            + "<p>If you do not agree with these terms, please select I Disagree.</p>"
            + "</div></html>"
        );

        lbTerms.setHorizontalAlignment(SwingConstants.CENTER);
        lbTerms.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Place the Back icon on the left side at the top
        panelTop.setOpaque(false);
        panelTop.add(btBack, BorderLayout.WEST);
        panelTop.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Centre area containing the logo and terms
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(lbJust_Blank, BorderLayout.NORTH);
        centerPanel.add(lbTerms, BorderLayout.CENTER);

        // Agree and Disagree checkboxes
        panel_2.add(cbI_Agree);
        panel_2.add(cbI_Disagree);

        // Bottom area containing checkboxes and orange button
        panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
        panelBottom.setBorder(new EmptyBorder(10, 10, 20, 10));

        panel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        btContinue.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBottom.add(panel_2);
        panelBottom.add(Box.createVerticalStrut(12));
        panelBottom.add(btContinue);

        // Main layout
        setLayout(new BorderLayout(10, 10));
        add(panelTop, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // Read Terms and Conditions button action
        //btContinue.addActionListener(e -> {

            if (cbI_Disagree.isSelected()) {
                JOptionPane.showMessageDialog(
                    this,
                    "You must agree to the Terms and Conditions to continue.",
                    "Terms and Conditions",
                    JOptionPane.WARNING_MESSAGE
                );

            } else if (cbI_Agree.isSelected()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Thank you for agreeing to the AquaVoltsa Terms and Conditions.",
                    "Terms and Conditions",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select I Agree or I Disagree first.",
                    "Terms and Conditions",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Terms_and_Conditions page = new Terms_and_Conditions();
            page.setVisible(true);
        });
    }
}
