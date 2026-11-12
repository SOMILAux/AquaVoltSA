//Final update done by Inathi and Oyama
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Language extends JFrame {

    private JRadioButton rbEnglish;
    private JRadioButton rbAfrikaans;
    private JRadioButton rbIsiXhosa;
    private JRadioButton rbIsiZulu;
    private JButton btSave;
    private JButton btBack;

    public Language() {

        setTitle("Language");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(new EmptyBorder(15, 15, 20, 15));

        // Top section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);

        btBack = new JButton();
        java.net.URL backURL = getClass().getResource("/icons/back.png");

        if (backURL != null) {
            ImageIcon original = new ImageIcon(backURL);
            Image scaled = original.getImage().getScaledInstance(
                    30, 30, Image.SCALE_SMOOTH);
            btBack.setIcon(new ImageIcon(scaled));
        } else {
            btBack.setText("← Back");
        }

        btBack.setFocusPainted(false);
        btBack.setBorderPainted(false);
        btBack.setContentAreaFilled(false);
        btBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel title = new JLabel("Language", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        topPanel.add(btBack, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(30, 25, 30, 25));

        JLabel heading = new JLabel("Choose Your Language");
        heading.setFont(new Font("Arial", Font.BOLD, 21));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html>Select your preferred language below.</html>");
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(heading);
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(description);
        contentPanel.add(Box.createVerticalStrut(25));

        rbEnglish = new JRadioButton("English");
        rbAfrikaans = new JRadioButton("Afrikaans");
        rbIsiXhosa = new JRadioButton("isiXhosa");
        rbIsiZulu = new JRadioButton("isiZulu");

        JRadioButton[] languages = {
            rbEnglish, rbAfrikaans, rbIsiXhosa, rbIsiZulu
        };

        ButtonGroup languageGroup = new ButtonGroup();

        for (JRadioButton language : languages) {
            languageGroup.add(language);
            language.setFont(new Font("Arial", Font.PLAIN, 17));
            language.setBackground(Color.WHITE);
            language.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(language);
            contentPanel.add(Box.createVerticalStrut(18));
        }

        // English is selected by default
        rbEnglish.setSelected(true);

        // Save button
        btSave = new JButton("Save Changes");
        btSave.setBackground(new Color(255, 140, 0));
        btSave.setForeground(Color.BLACK);
        btSave.setFont(new Font("Arial", Font.BOLD, 16));
        btSave.setFocusPainted(false);
        btSave.setOpaque(true);
        btSave.setBorderPainted(false);
        btSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btSave.setPreferredSize(new Dimension(220, 45));
        btSave.setMaximumSize(new Dimension(220, 45));
        btSave.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(btSave);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Back to Settings
        btBack.addActionListener(e -> {
            Settings_Page settings = new Settings_Page();
            settings.showGui();
            settings.setSize(600, 800);
            settings.setLocationRelativeTo(null);
            settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            settings.setVisible(true);
            dispose();
        });

        // Save selected language
        btSave.addActionListener(e -> {
            String selectedLanguage = "";

            if (rbEnglish.isSelected()) {
                selectedLanguage = "English";
            } else if (rbAfrikaans.isSelected()) {
                selectedLanguage = "Afrikaans";
            } else if (rbIsiXhosa.isSelected()) {
                selectedLanguage = "isiXhosa";
            } else if (rbIsiZulu.isSelected()) {
                selectedLanguage = "isiZulu";
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Your selected language is " + selectedLanguage + ".",
                    "Language Settings",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
