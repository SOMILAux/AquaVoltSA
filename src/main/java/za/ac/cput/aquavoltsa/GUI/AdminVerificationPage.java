//Final update done by Oyama and Buhle
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * Admin verification: pick SMS or Email (one only), code expires after 45 seconds, 3 attempts allowed.
 */
public class AdminVerificationPage extends BasePage {
    private final AdminStore.Admin admin;
    private final JLabel expiryLabel = Theme.label(" ", Theme.SMALL.deriveFont(Font.BOLD), Theme.RED, SwingConstants.CENTER);
    private final JLabel attemptsLabel = Theme.label(" ", Theme.SMALL, Theme.GREY, SwingConstants.CENTER);
    private final Theme.HintField codeField = new Theme.HintField("6-digit code");
    private final JLabel codeErr = Theme.err();
    private javax.swing.Timer countdown;
    private String currentOtp = null;
    private int secondsLeft = 0;
    private int attemptsLeft = 3;

    public AdminVerificationPage(AdminStore.Admin admin) {
        super("Admin Verification");
        this.admin = admin;

        gap(0);
        put(Theme.label("Admin Verification", Theme.TITLE, Theme.NAVY, SwingConstants.CENTER));
        put(Theme.label("Hello " + admin.fullName + ", please confirm it is you", Theme.BODY, Theme.GREY,
                SwingConstants.CENTER));
        gap(10);
        put(Theme.label("Send my code to:", Theme.BOLD, Theme.INK, SwingConstants.LEFT));

        JRadioButton smsRb = radio("SMS   " + maskPhone(admin.phone));
        JRadioButton emailRb = radio("Email   " + maskEmail(admin.email));
        ButtonGroup group = new ButtonGroup();
        group.add(smsRb);
        group.add(emailRb);
        smsRb.setSelected(true);
        put(smsRb);
        put(emailRb);
        gap(8);

        JButton sendBtn = put(new Theme.Btn("Send code", Theme.NAVY));
        gap(2);
        expiryLabel.setVisible(false);
        attemptsLabel.setVisible(false);
        put(expiryLabel);
        put(attemptsLabel);
        gap(6);
        put(Theme.label("Enter the 6-digit code:", Theme.BODY, Theme.INK, SwingConstants.CENTER));
        gap(6);
        put(codeField);
        put(codeErr);
        gap(10);

        JButton verifyBtn = new Theme.Btn("Verify", Theme.NAVY);
        JButton cancelBtn = new Theme.Btn("Cancel", Theme.GREY);
        verifyBtn.setPreferredSize(new java.awt.Dimension(130, 44));
        cancelBtn.setPreferredSize(new java.awt.Dimension(130, 44));
        put(Theme.row(verifyBtn, cancelBtn));

        countdown = new javax.swing.Timer(1000, e -> {
            secondsLeft--;
            tick();
        });

        sendBtn.addActionListener(e -> {
            currentOtp = String.valueOf(100000 + new Random().nextInt(900000));
            secondsLeft = 45;
            attemptsLeft = 3;
            Theme.ok(codeErr, codeField);
            attemptsLabel.setText("3 attempts left");
            attemptsLabel.setVisible(true);
            tick();
            countdown.restart();

            boolean sms = smsRb.isSelected();
            Theme.toast(this, "Code sent via " + (sms ? "SMS to " + maskPhone(admin.phone) : "Email to " + maskEmail(admin.email))
                    + ": " + currentOtp + " (demo)", 640, 5000);
            if (sms) {
                Theme.toast(this, "SMS \u2022 AquaVolt Admin   Code: " + currentOtp + "  Just Now", 6, 6000);
            }
        });

        verifyBtn.addActionListener(e -> verify());
        getRootPane().setDefaultButton(verifyBtn);

        cancelBtn.addActionListener(e -> {
            countdown.stop();
            go(new AdminLoginPage());
        });
    }

    private void verify() {
        Theme.ok(codeErr, codeField);
        String code = codeField.getText().trim();
        if (currentOtp == null) {
            Theme.toast(this, "Press Send code first (your code may have expired)", 640, 2500);
        } else if (code.isEmpty()) {
            Theme.bad(codeErr, codeField, "Enter the code that was sent");
        } else if (!code.equals(currentOtp)) {
            attemptsLeft--;
            if (attemptsLeft <= 0) {
                countdown.stop();
                AdminStore.log(admin.username, "LOGIN_FAILED", "Too many wrong codes");
                JOptionPane.showMessageDialog(this, "Too many wrong codes. Please log in again.",
                        "Verification failed", JOptionPane.WARNING_MESSAGE);
                go(new AdminLoginPage());
                return;
            }
            attemptsLabel.setText(attemptsLeft + (attemptsLeft == 1 ? " attempt left" : " attempts left"));
            Theme.bad(codeErr, codeField, "Wrong code, please try again");
        } else {
            countdown.stop();
            AdminStore.log(admin.username, "LOGIN", "Admin logged in");
            go(new AdminDashboard(admin));
        }
    }

    private void tick() {
        if (currentOtp == null) {
            expiryLabel.setVisible(false);
            return;
        }
        expiryLabel.setVisible(true);
        if (secondsLeft <= 0) {
            expiryLabel.setText("Code expired - press Send code again");
            currentOtp = null;
            countdown.stop();
        } else {
            expiryLabel.setText("Code expires in " + secondsLeft + "s");
        }
    }

    private static JRadioButton radio(String text) {
        JRadioButton r = new JRadioButton(text);
        r.setFont(Theme.BODY);
        r.setOpaque(false);
        r.setFocusPainted(false);
        return r;
    }

    private static String maskPhone(String p) {
        return p.length() >= 7 ? p.substring(0, 3) + "***" + p.substring(p.length() - 4) : p;
    }

    private static String maskEmail(String e) {
        int at = e.indexOf('@');
        return at > 1 ? e.charAt(0) + "***" + e.substring(at) : e;
    }
}