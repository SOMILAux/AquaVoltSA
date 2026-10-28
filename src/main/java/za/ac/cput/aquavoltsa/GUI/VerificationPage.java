package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class VerificationPage extends BasePage {
    private final UserStore.User user;
    private final JLabel expiryLabel = Theme.label(" ", Theme.SMALL.deriveFont(Font.BOLD), Theme.RED, SwingConstants.CENTER);
    private final Theme.HintField otpField = new Theme.HintField("Enter OTP");
    private final JLabel otpErr = Theme.err();
    private javax.swing.Timer countdown;
    private String currentOtp = null;
    private int secondsLeft = 0;

    public VerificationPage() {
        this(null);
    }

    public VerificationPage(UserStore.User user) {
        super("Verification");
        this.user = user;

        gap(0);
        put(Theme.label("Verification", Theme.TITLE, Theme.BLUE, SwingConstants.CENTER));
        gap(8);
        put(Theme.label("Send OTP via:", Theme.BODY, Theme.INK, SwingConstants.CENTER));
        JCheckBox smsBox = Theme.check("SMS");
        JCheckBox emailBox = Theme.check("Email");
        JPanel boxes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        boxes.setOpaque(false);
        boxes.add(smsBox);
        boxes.add(emailBox);
        put(boxes);
        gap(8);
        JButton sendBtn = put(new Theme.Btn("Send", Theme.BLUE));
        gap(2);
        expiryLabel.setVisible(false);
        put(expiryLabel);
        put(Theme.label("Enter OTP that has been sent:", Theme.BODY, Theme.INK, SwingConstants.CENTER));
        gap(6);
        put(otpField);
        put(otpErr);
        gap(10);

        JButton enterBtn = new Theme.Btn("Enter", Theme.BLUE);
        JButton backBtn = new Theme.Btn("Back", Theme.GREY);
        enterBtn.setPreferredSize(new Dimension(130, 44));
        backBtn.setPreferredSize(new Dimension(130, 44));
        put(Theme.row(enterBtn, backBtn));

        countdown = new javax.swing.Timer(1000, e -> {
            secondsLeft--;
            tick();
        });

        // Send button action
        sendBtn.addActionListener(e -> {
            if (!smsBox.isSelected() && !emailBox.isSelected()) {
                Theme.toast(this, "Please select SMS or Email first", 640, 2500);
                return;
            }
            currentOtp = generateOTP();
            secondsLeft = 60;
            Theme.ok(otpErr, otpField);
            tick();
            countdown.restart();

            String via = smsBox.isSelected() && emailBox.isSelected() ? "SMS & Email"
                    : smsBox.isSelected() ? "SMS" : "Email";
            Theme.toast(this, "OTP sent via " + via + ": " + currentOtp + " (demo)", 640, 4000);
            if (smsBox.isSelected()) {   // phone-style notification banner (like the original "Just Now" label)
                Theme.toast(this, "SMS \u2022 AquaVolt SA   OTP: " + currentOtp + "  Just Now", 6, 6000);
            }
        });

        enterBtn.addActionListener(e -> {
            Theme.ok(otpErr, otpField);
            String code = otpField.getText().trim();
            if (currentOtp == null) {
                Theme.toast(this, "Send an OTP first (it may have expired)", 640, 2500);
            } else if (code.isEmpty()) {
                Theme.bad(otpErr, otpField, "Enter the OTP that was sent");
            } else if (!code.equals(currentOtp)) {
                Theme.bad(otpErr, otpField, "Incorrect OTP, please try again");
            } else {
                countdown.stop();
                go(new HomePage(this.user));
            }
        });

        // Back button action
        backBtn.addActionListener(e -> {
            countdown.stop();
            go(new LoginSignupPage());
        });
    }

    private void tick() {
        if (currentOtp == null) {
            expiryLabel.setVisible(false);
            return;
        }
        expiryLabel.setVisible(true);
        if (secondsLeft <= 0) {
            expiryLabel.setText("OTP expired - press Send for a new one");
            currentOtp = null;
            countdown.stop();
        } else {
            expiryLabel.setText("OTP expires in " + secondsLeft + "s");
        }
    }

    // Generate random 6-digit OTP
    private String generateOTP() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000); // ensures 6 digits
        return String.valueOf(otp);
    }
}
