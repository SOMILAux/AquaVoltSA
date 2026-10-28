package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class SignupPage2 extends BasePage {
    private static final int ID_LENGTH = 13;   // South African ID numbers have 13 digits

    private final Theme.HintField nameField = new Theme.HintField("Enter Name and Surname");
    private final Theme.HintField idField = new Theme.HintField("Enter ID-number");
    private final Theme.HintPassword passwordField = new Theme.HintPassword("Enter Password");
    private final Theme.HintPassword confirmField = new Theme.HintPassword("Re-enter Password");
    private final Theme.HintField emailField = new Theme.HintField("Enter your Email Address");
    private final Theme.HintField phoneField = new Theme.HintField("Enter Phone Number");
    private final JLabel[] err = new JLabel[6];

    public SignupPage2() {
        super("Sign-up Page Part 2");
        for (int i = 0; i < err.length; i++) err[i] = Theme.err();

        UserStore.User d = UserStore.draft;   // restore what was typed if the user came back
        nameField.setText(d.fullName);
        idField.setText(d.idNumber);
        passwordField.setText(d.rawPassword);
        confirmField.setText(d.rawPassword);
        emailField.setText(d.email);
        phoneField.setText(d.phone);

        JButton randomizeBtn = new Theme.Btn("Randomize", Theme.BLUE);
        randomizeBtn.setFont(Theme.BOLD.deriveFont(12f));
        randomizeBtn.setPreferredSize(new Dimension(100, 44));
        JPanel passwordRow = new JPanel(new BorderLayout(8, 0));
        passwordRow.setOpaque(false);
        passwordRow.add(passwordField, BorderLayout.CENTER);
        passwordRow.add(randomizeBtn, BorderLayout.EAST);

        String[] caption = {"1. Enter your Name and Surname", "2. Enter your ID number",
            "3. Enter your Password or Click random generator password", "4. Re-enter your password",
            "5. Enter your email Address", "6. Enter your phone Number"};
        JComponent[] input = {nameField, idField, passwordRow, confirmField, emailField, phoneField};

        put(Theme.label("Aqua Volt SA", Theme.TITLE.deriveFont(20f), Color.BLACK, SwingConstants.CENTER));
        gap(6);
        put(Theme.label("Complete required information", Theme.BOLD, Theme.BLUE, SwingConstants.LEFT));
        gap(8);
        for (int i = 0; i < input.length; i++) {
            put(Theme.label("<html><div style='width:330px'>" + caption[i] + "</div></html>",
                    Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT));
            gap(3);
            put(input[i]);
            put(err[i]);
            gap(6);
        }
        gap(6);

        JButton enterBtn = new Theme.Btn("Enter", Theme.BLUE);
        JButton backBtn = new Theme.Btn("Back", Theme.GREY);
        enterBtn.setPreferredSize(new Dimension(130, 44));
        backBtn.setPreferredSize(new Dimension(130, 44));
        put(Theme.row(enterBtn, backBtn));
        gap(10);

        randomizeBtn.addActionListener(e -> {
            passwordField.setText(generateRandomPassword());
            passwordField.reveal(true);
            confirmField.setText("");
        });

        enterBtn.addActionListener(e -> {
            String name = nameField.getText().trim(), id = idField.getText().trim();
            String pw = new String(passwordField.getPassword()), pw2 = new String(confirmField.getPassword());
            String mail = emailField.getText().trim(), ph = phoneField.getText().replaceAll("[\\s-]", "");
            JComponent[] f = {nameField, idField, passwordField, confirmField, emailField, phoneField};
            String[] m = {
                name.split("\\s+").length < 2 ? "Enter your name and surname" : null,
                !id.matches("\\d{" + ID_LENGTH + "}") ? "ID number must be " + ID_LENGTH + " digits" : null,
                pw.length() < 6 ? "Password must be at least 6 characters" : null,
                !pw2.equals(pw) ? "Passwords do not match" : null,
                !mail.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$") ? "Enter a valid email address" : null,
                !ph.matches("^(\\+27|0)\\d{9}$") ? "Enter a valid phone number" : null};
            boolean bad = false;
            for (int i = 0; i < f.length; i++) {
                Theme.ok(err[i], f[i]);
                if (m[i] != null) bad = Theme.bad(err[i], f[i], m[i]);
            }
            if (bad) return;

            UserStore.User u = UserStore.draft;
            u.fullName = name;
            u.idNumber = id;
            u.rawPassword = pw;
            u.email = mail;
            u.phone = ph;
            go(new SignupPage3());
        });

        backBtn.addActionListener(e -> go(new LoginSignupPage()));
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) sb.append(chars.charAt(rand.nextInt(chars.length())));
        return sb.toString();
    }
}
