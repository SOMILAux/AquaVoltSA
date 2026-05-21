/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.aquavoltsa;
import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.Random;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class VerificationPage extends JFrame {
    private JLabel notificationLabel;

    public VerificationPage() {
        setTitle("AquaVolt SA - Verification");
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Notification area (hidden by default)
        notificationLabel = new JLabel("", SwingConstants.CENTER);
        notificationLabel.setOpaque(true);
        notificationLabel.setBackground(Color.lightGray);
        notificationLabel.setVisible(false);
        add(notificationLabel, BorderLayout.NORTH);

        // Main content
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        JLabel title = new JLabel("Verification", SwingConstants.CENTER);
        JCheckBox smsBox = new JCheckBox("SMS");
        JCheckBox emailBox = new JCheckBox("Email");
        JButton sendBtn = new JButton("Send");
        JTextField otpField = new JTextField("Enter OTP");
        JButton enterBtn = new JButton("Enter");
        JButton backBtn = new JButton("Back");

        panel.add(title);
        panel.add(new JLabel("Send OTP via:", SwingConstants.CENTER));
        panel.add(smsBox);
        panel.add(emailBox);
        panel.add(sendBtn);
        panel.add(otpField);
        panel.add(enterBtn);
        panel.add(backBtn);

        add(panel, BorderLayout.CENTER);

        // Send button action
        sendBtn.addActionListener(e -> {
            String otp = generateOTP();
            notificationLabel.setText("OTP: " + otp + "  Just Now");
            notificationLabel.setVisible(true);

            // Hide after 60 seconds
            Timer timer = new Timer(60000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    notificationLabel.setVisible(false);
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        // Back button action
        backBtn.addActionListener(e -> {
            new LoginSignupPage().setVisible(true);
            dispose();
        });
    }

    // Generate random 6-digit OTP
    private String generateOTP() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000); // ensures 6 digits
        return String.valueOf(otp);
    }
}
