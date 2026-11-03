
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class Help_and_Support_Page extends JFrame implements ActionListener {

    private JButton btHelp, btFeedback, btWebsite, btEmail, btBack;
    private JCheckBox cbUnderstand;

    public Help_and_Support_Page() {

        setTitle("Help and Support");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color gold = new Color(218, 165, 32);
        Color cream = new Color(255, 248, 220);
        Color blue = new Color(25, 70, 130);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(gold);
        mainPanel.setLayout(null);

        JLabel lblTitle = new JLabel("HELP AND SUPPORT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(150, 25, 350, 40);
        mainPanel.add(lblTitle);

        JLabel lblMessage = new JLabel("How can we help you?");
        lblMessage.setFont(new Font("Arial", Font.BOLD, 18));
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setBounds(190, 80, 300, 30);
        mainPanel.add(lblMessage);

        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(null);
        helpPanel.setBackground(cream);
        helpPanel.setBounds(50, 130, 480, 170);
        mainPanel.add(helpPanel);

        JLabel lblHelp = new JLabel("Help");
        lblHelp.setFont(new Font("Arial", Font.BOLD, 20));
        lblHelp.setForeground(blue);
        lblHelp.setBounds(25, 15, 200, 30);
        helpPanel.add(lblHelp);

        JLabel lblHelpText = new JLabel(
                "<html>Need help using AquaVolt SA?<br>"
                + "Contact our support team for assistance "
                + "with your account and settings.</html>"
        );
        lblHelpText.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHelpText.setBounds(25, 50, 420, 55);
        helpPanel.add(lblHelpText);

        btHelp = new JButton("Contact Support");
        btHelp.setBounds(25, 115, 180, 35);
        btHelp.addActionListener(this);
        helpPanel.add(btHelp);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(null);
        feedbackPanel.setBackground(cream);
        feedbackPanel.setBounds(50, 320, 480, 170);
        mainPanel.add(feedbackPanel);

        JLabel lblFeedback = new JLabel("Feedback");
        lblFeedback.setFont(new Font("Arial", Font.BOLD, 20));
        lblFeedback.setForeground(blue);
        lblFeedback.setBounds(25, 15, 200, 30);
        feedbackPanel.add(lblFeedback);

        JLabel lblFeedbackText = new JLabel(
                "<html>We value your opinion.<br>"
                + "Please share your experience to help us "
                + "improve AquaVolt SA.</html>"
        );
        lblFeedbackText.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFeedbackText.setBounds(25, 50, 420, 55);
        feedbackPanel.add(lblFeedbackText);

        btFeedback = new JButton("Send Feedback");
        btFeedback.setBounds(25, 115, 180, 35);
        btFeedback.addActionListener(this);
        feedbackPanel.add(btFeedback);

        btWebsite = new JButton("Visit Website");
        btWebsite.setBounds(75, 510, 190, 40);
        btWebsite.addActionListener(this);
        mainPanel.add(btWebsite);

        btEmail = new JButton("Email Support");
        btEmail.setBounds(300, 510, 190, 40);
        btEmail.addActionListener(this);
        mainPanel.add(btEmail);

        cbUnderstand = new JCheckBox("I understand");
        cbUnderstand.setBackground(gold);
        cbUnderstand.setForeground(Color.WHITE);
        cbUnderstand.setFont(new Font("Arial", Font.PLAIN, 14));
        cbUnderstand.setBounds(210, 565, 180, 30);
        mainPanel.add(cbUnderstand);

        btBack = new JButton("Back");
        btBack.setBounds(210, 610, 150, 35);
        btBack.addActionListener(this);
        mainPanel.add(btBack);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btHelp) {
            JOptionPane.showMessageDialog(this,
                    "Please contact our support team for assistance.",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        else if (e.getSource() == btFeedback) {
            JOptionPane.showMessageDialog(this,
                    "Thank you for your feedback!",
                    "Feedback",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        else if (e.getSource() == btWebsite) {
            try {
                Desktop.getDesktop().browse(
                        new URI("http://AquaVolt.com"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to open the website.");
            }
        }

        else if (e.getSource() == btEmail) {
            try {
                Desktop.getDesktop().mail(
                        new URI("mailto:support@aquavolt.com"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to open your email application.");
            }
        }

        else if (e.getSource() == btBack) {
            new Settings_Page().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Help_and_Support_Page().setVisible(true);
        });
    }
}
