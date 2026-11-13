
// Final update done by Inathi
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class Help_and_Support_Page extends JFrame implements ActionListener {

    private JButton btHelp, btFeedback, btWebsite, btEmail, btBack;
    private JCheckBox cbUnderstand;

    private final Color BLUE = new Color(173, 216, 230);

    public Help_and_Support_Page() {

        setTitle("Help and Support");
        setSize(425, 917);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color gold = new Color(218, 165, 32);
        Color cream = new Color(255, 248, 220);
        Color blue = new Color(25, 70, 130);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(gold);
        mainPanel.setLayout(null);

        // TITLE
        JLabel lblTitle = new JLabel("HELP AND SUPPORT");

        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(85, 25, 255, 35);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(lblTitle);

        // MESSAGE
        JLabel lblMessage = new JLabel("How can we help you?");

        lblMessage.setFont(new Font("Arial", Font.BOLD, 16));
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setBounds(75, 75, 275, 30);
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(lblMessage);

        // =========================
        // HELP PANEL
        // =========================

        JPanel helpPanel = new JPanel();

        helpPanel.setLayout(null);
        helpPanel.setBackground(cream);
        helpPanel.setBounds(30, 125, 350, 155);

        mainPanel.add(helpPanel);

        JLabel lblHelp = new JLabel("Help");

        lblHelp.setFont(new Font("Arial", Font.BOLD, 18));
        lblHelp.setForeground(blue);
        lblHelp.setBounds(20, 12, 200, 25);

        helpPanel.add(lblHelp);

        JLabel lblHelpText = new JLabel(
                "<html>Need help using AquaVolt SA?<br>"
                + "Contact our support team for assistance "
                + "with your account and settings.</html>"
        );

        lblHelpText.setFont(new Font("Arial", Font.PLAIN, 13));
        lblHelpText.setBounds(20, 45, 310, 45);

        helpPanel.add(lblHelpText);

        btHelp = new JButton("Contact Support");

        btHelp.setBounds(20, 105, 155, 32);
        btHelp.setFont(new Font("Arial", Font.PLAIN, 12));
        btHelp.setBackground(Color.WHITE);
        btHelp.addActionListener(this);

        helpPanel.add(btHelp);

        // =========================
        // FEEDBACK PANEL
        // =========================

        JPanel feedbackPanel = new JPanel();

        feedbackPanel.setLayout(null);
        feedbackPanel.setBackground(cream);
        feedbackPanel.setBounds(30, 300, 350, 155);

        mainPanel.add(feedbackPanel);

        JLabel lblFeedback = new JLabel("Feedback");

        lblFeedback.setFont(new Font("Arial", Font.BOLD, 18));
        lblFeedback.setForeground(blue);
        lblFeedback.setBounds(20, 12, 200, 25);

        feedbackPanel.add(lblFeedback);

        JLabel lblFeedbackText = new JLabel(
                "<html>We value your opinion.<br>"
                + "Please share your experience to help us "
                + "improve AquaVolt SA.</html>"
        );

        lblFeedbackText.setFont(new Font("Arial", Font.PLAIN, 13));
        lblFeedbackText.setBounds(20, 45, 310, 45);

        feedbackPanel.add(lblFeedbackText);

        btFeedback = new JButton("Send Feedback");

        btFeedback.setBounds(20, 105, 155, 32);
        btFeedback.setFont(new Font("Arial", Font.PLAIN, 12));
        btFeedback.setBackground(Color.WHITE);
        btFeedback.addActionListener(this);

        feedbackPanel.add(btFeedback);

        // =========================
        // WEBSITE BUTTON
        // =========================

        btWebsite = new JButton("Visit Website");

        btWebsite.setBounds(30, 485, 165, 38);
        btWebsite.setFont(new Font("Arial", Font.PLAIN, 12));
        btWebsite.setBackground(BLUE);
        btWebsite.addActionListener(this);

        mainPanel.add(btWebsite);

        // =========================
        // EMAIL BUTTON
        // =========================

        btEmail = new JButton("Email Support");

        btEmail.setBounds(215, 485, 165, 38);
        btEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        btEmail.setBackground(Color.LIGHT_GRAY);
        btEmail.addActionListener(this);

        mainPanel.add(btEmail);

        // =========================
        // UNDERSTAND CHECKBOX
        // =========================

        cbUnderstand = new JCheckBox("I understand");

        cbUnderstand.setBackground(gold);
        cbUnderstand.setForeground(Color.WHITE);
        cbUnderstand.setFont(new Font("Arial", Font.PLAIN, 13));
        cbUnderstand.setBounds(125, 545, 160, 30);

        mainPanel.add(cbUnderstand);

        // =========================
        // BACK BUTTON
        // =========================

        btBack = new JButton("Back");

        btBack.setBounds(135, 600, 140, 35);
        btBack.setFont(new Font("Arial", Font.BOLD, 13));
        btBack.setBackground(Color.ORANGE);
        btBack.addActionListener(this);

        mainPanel.add(btBack);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // CONTACT SUPPORT
        if (e.getSource() == btHelp) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please contact our support team for assistance.",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        // SEND FEEDBACK
        else if (e.getSource() == btFeedback) {

            JTextArea feedbackArea = new JTextArea(6, 25);

            feedbackArea.setLineWrap(true);
            feedbackArea.setWrapStyleWord(true);

            JScrollPane scrollPane =
                    new JScrollPane(feedbackArea);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Send Your Feedback",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {

                String feedback = feedbackArea.getText().trim();

                if (feedback.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter your feedback."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Thank you for your feedback!",
                            "Feedback",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }

        // OPEN WEBSITE
        else if (e.getSource() == btWebsite) {

            try {

                if (Desktop.isDesktopSupported()) {

                    Desktop.getDesktop().browse(
                            new URI("http://AquaVolt.com")
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Your computer does not support opening websites."
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to open the website."
                );
            }
        }

        // OPEN EMAIL
        else if (e.getSource() == btEmail) {

            try {

                if (Desktop.isDesktopSupported()) {

                    Desktop.getDesktop().mail(
                            new URI("mailto:support@aquavolt.com")
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Your computer does not support opening email."
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to open your email application."
                );
            }
        }

        // BACK BUTTON
        else if (e.getSource() == btBack) {

            Settings_Page page = new Settings_Page();

            page.showGui();
            page.setSize(425, 917);
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            page.setLocationRelativeTo(null);
            page.setVisible(true);

            this.dispose();
        }
    }
}
