package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  

public class LanguagePage extends JFrame {

    private JLabel titleLabel;
    private JLabel instructionLabel;
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

        // --- Gradient Background Panel ---
        JPanel backgroundPanel = new JPanel() {
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
        backgroundPanel.setLayout(new BorderLayout());

        // --- Main Content Panel ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Title & Instruction
        titleLabel = new JLabel(Translations.get("language"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        instructionLabel = new JLabel("Choose a language you prefer:", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(instructionLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Selected Language Status
        confirmationLabel = new JLabel("Selected: " + selectedLanguage, SwingConstants.CENTER);
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmationLabel.setForeground(new Color(0, 100, 0)); // Dark green
        confirmationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(confirmationLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Language Buttons
        String[] languages = {
            "English", "Afrikaans", "isiZulu", "isiXhosa", 
            "SeSotho", "Sepedi", "Setswana", "itsonga", "Tshivenda"
        };

        for (String lang : languages) {
            JButton btn = new JButton(lang);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(280, 45));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            btn.addActionListener(e -> selectLanguage(lang));
            
            contentPanel.add(btn);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        contentPanel.add(Box.createVerticalStrut(20));

        // Back Button
        JButton backButton = new JButton(Translations.get("back"));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setMaximumSize(new Dimension(140, 45));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            Settings_Page page = new Settings_Page();
            page.showGui();
            page.setVisible(true);
            this.dispose();
        });
        contentPanel.add(backButton);

        // --- Add to Scroll Pane ---
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        add(backgroundPanel);
        setVisible(true);
    }

    private void selectLanguage(String language) {
        selectedLanguage = language;
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