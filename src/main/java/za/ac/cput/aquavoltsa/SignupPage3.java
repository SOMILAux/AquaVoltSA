/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.aquavoltsa;
import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;



public class SignupPage3 extends JFrame {
    public SignupPage3() {
        setTitle("AquaVolt SA - Sign-up Page Part 3");
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(12, 1, 10, 10));

        JCheckBox terms1 = new JCheckBox("I read and agree to these terms and conditions");
        JCheckBox terms2 = new JCheckBox("I read and agree to these terms and conditions");

        JButton termsBtn = new JButton("Terms and Condition...");
        JButton enterBtn = new JButton("Enter");
        JButton backBtn = new JButton("Back");

        add(new JLabel("Electricity Details", SwingConstants.CENTER));
        add(new JTextField("Enter Home Address"));
        add(new JTextField("Enter Meter Number"));
        add(new JTextField("Enter Serial Number"));
        add(new JLabel("*You can skip this process and update it later on", SwingConstants.CENTER));
        add(termsBtn);
        add(terms1);
        add(terms2);
        add(enterBtn);
        add(backBtn);

        termsBtn.addActionListener(e -> {
            new TermsPage().setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            new SignupPage2().setVisible(true);
            dispose();
        });
        
    }
}
