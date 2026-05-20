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
public class SignupPage2 extends JFrame{
    private JTextField usernameField, idField, emailField, phoneField;
    private JPasswordField passwordField, confirmField;
    private boolean showPassword = false;
    private boolean showConfirm = false;

    public SignupPage2() {
        setTitle("AquaVolt SA - Sign-up Page Part 2");
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1, 10, 10));

        usernameField = new JTextField("Enter Username");
        idField = new JTextField("Enter ID-number");
        passwordField = new JPasswordField("Enter Password");
        confirmField = new JPasswordField("Re-enter Password");
        emailField = new JTextField("Enter your Email Address");
        phoneField = new JTextField("Enter Phone Number");

        JButton randomizeBtn = new JButton("Randomize");
        JButton togglePassBtn = new JButton("Show");
        JButton toggleConfirmBtn = new JButton("Show");
        JButton enterBtn = new JButton("Enter");
        JButton backBtn = new JButton("Back");

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(togglePassBtn, BorderLayout.EAST);

        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.add(confirmField, BorderLayout.CENTER);
        confirmPanel.add(toggleConfirmBtn, BorderLayout.EAST);

        add(new JLabel("Set your Unique Username:", SwingConstants.CENTER));
        add(usernameField);
        add(idField);
        add(passwordPanel);
        add(randomizeBtn);
        add(confirmPanel);
        add(emailField);
        add(phoneField);
        add(enterBtn);
        add(backBtn);

        randomizeBtn.addActionListener(e -> {
            passwordField.setText(generateRandomPassword());
        });

        togglePassBtn.addActionListener(e -> {
            showPassword = !showPassword;
            if (showPassword) {
                passwordField.setEchoChar((char) 0);
                togglePassBtn.setText("Hide");
            } else {
                passwordField.setEchoChar('•');
                togglePassBtn.setText("Show");
            }
        });

        toggleConfirmBtn.addActionListener(e -> {
            showConfirm = !showConfirm;
            if (showConfirm) {
                confirmField.setEchoChar((char) 0);
                toggleConfirmBtn.setText("Hide");
            } else {
                confirmField.setEchoChar('•');
                toggleConfirmBtn.setText("Show");
            }
        });

        enterBtn.addActionListener(e -> {
            new SignupPage3().setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            new LoginSignupPage().setVisible(true);
            dispose();
        });
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) sb.append(chars.charAt(rand.nextInt(chars.length())));
        return sb.toString();
    }
}

