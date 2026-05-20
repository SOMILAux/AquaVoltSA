package za.ac.cput.aquavoltsa;
import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *@Student-Number 241028213
 * @author Oyama Sibunzi
 */

public class LoginSignupPage extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean showPassword = false;

    public LoginSignupPage() {
        setTitle("AquaVolt SA - Login/Sign-up Page");
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Login into your Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        usernameField = new JTextField("Enter Username");
        passwordField = new JPasswordField("Password");

        JButton toggleBtn = new JButton("Show");
        toggleBtn.addActionListener(e -> {
            showPassword = !showPassword;
            if (showPassword) {
                passwordField.setEchoChar((char) 0);
                toggleBtn.setText("Hide");
            } else {
                passwordField.setEchoChar('•');
                toggleBtn.setText("Show");
            }
        });

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(toggleBtn, BorderLayout.EAST);

        JButton enterBtn = new JButton("Enter");
        JButton signupBtn = new JButton("Sign Up");

        panel.add(title);
        panel.add(usernameField);
        panel.add(passwordPanel);
        panel.add(enterBtn);
        panel.add(new JLabel("Or", SwingConstants.CENTER));
        panel.add(signupBtn);

        add(panel, BorderLayout.CENTER);

        enterBtn.addActionListener(e -> {
            new VerificationPage().setVisible(true);
            dispose();
        });

        signupBtn.addActionListener(e -> {
            new SignupPage2().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignupPage().setVisible(true));
    }
}

