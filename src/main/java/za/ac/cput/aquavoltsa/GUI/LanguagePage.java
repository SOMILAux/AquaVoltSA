//Final update done by Inathi and Oyama
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  

public class LanguagePage extends JFrame {

    private JLabel titleLabel;
    private JLabel instructionLabel;
    private JButton selectedLanguageButton;
    private JLabel confirmationLabel;

    private static String selectedLanguage = "English";

    public LanguagePage() {

        setTitle("Language");
        setSize(425, 917);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fetch saved language for this user
        if (Session.customer != null) {
            selectedLanguage = SettingsStore.get(Session.username(), "language", "English");
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color topColor = new Color(255, 193, 7);
                Color bottomColor = new Color(79, 195, 247);
                GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, height, bottomColor);
                g2.setPaint(gradient);
                g2.fillRect(0, 0, width, height);
            }
        };

        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(400, 850));

        // TITLE
        titleLabel = new JLabel(Translations.get("language"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(90, 30, 200, 35);
        mainPanel.add(titleLabel);

        // INSTRUCTION
        instructionLabel = new JLabel("Choose a language you prefer:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setBounds(45, 80, 300, 30);
        mainPanel.add(instructionLabel);

        // SELECTED LANGUAGE
        selectedLanguageButton = new JButton(selectedLanguage);
        selectedLanguageButton.setBounds(60, 120, 160, 40);
        selectedLanguageButton.setFocusPainted(false);
        mainPanel.add(selectedLanguageButton);

        // CONFIRMATION LABEL
        confirmationLabel = new JLabel("Selected: " + selectedLanguage);
        confirmationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 13));
        confirmationLabel.setBounds(40, 165, 200, 30);
        mainPanel.add(confirmationLabel);

        // LANGUAGE OPTIONS
        String[] languages = {
            "English", "Afrikaans", "isiZulu", "isiXhosa", 
            "SeSotho", "Sepedi", "Setswana", "itsonga", "Tshivenda"
        };

        int y = 210;

        for (String language : languages) {
            JButton languageButton = new JButton(language);
            languageButton.setBounds(60, y, 160, 40);
            languageButton.setFocusPainted(false);
            languageButton.addActionListener(e -> selectLanguage(language));
            mainPanel.add(languageButton);
            y += 50;
        }

        // BACK BUTTON
        JButton backButton = new JButton("Back");
        backButton.setBounds(90, 680, 100, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        mainPanel.add(backButton);

        backButton.addActionListener(e -> {
            Settings_Page page = new Settings_Page();
            page.showGui();
            page.setSize(425, 917);
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            page.setLocationRelativeTo(null);
            page.setVisible(true);
            this.dispose();
        });

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        setVisible(true);
    }

    private void selectLanguage(String language) {
        selectedLanguage = language;
        selectedLanguageButton.setText(language);
        confirmationLabel.setText("Selected: " + language);

        // Save to database for this specific user
        if (Session.customer != null) {
            SettingsStore.set(Session.username(), "language", language);
            JOptionPane.showMessageDialog(this, "Language changed to: " + language);
        } else {
            JOptionPane.showMessageDialog(this, "No user logged in.");
        }
    }
}