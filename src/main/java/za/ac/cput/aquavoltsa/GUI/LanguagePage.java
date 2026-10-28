package za.ac.cput.aquavoltsa.GUI;


/**
 *
 * @author Sbahle
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LanguagePage extends JFrame {

    private JLabel titleLabel;
    private JLabel instructionLabel;
    private JButton selectedLanguageButton;
    private JLabel confirmationLabel;

    private static String selectedLanguage = "English";

    private Map<String, String[]> translations;

    public LanguagePage() {

        setTitle("Language");
        setSize(300, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createTranslations();

        JPanel mainPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                int width = getWidth();
                int height = getHeight();

                Color topColor
                        = new Color(255, 193, 7);

                Color bottomColor
                        = new Color(79, 195, 247);

                GradientPaint gradient
                        = new GradientPaint(
                                0, 0,
                                topColor,
                                0, height,
                                bottomColor
                        );

                g2.setPaint(gradient);
                g2.fillRect(
                        0, 0,
                        width,
                        height
                );
            }
        };

        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(
                new Dimension(280, 850)
        );

        // =========================
        // TITLE
        // =========================
        titleLabel
                = new JLabel("Language");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setBounds(
                90, 30, 150, 35
        );

        mainPanel.add(titleLabel);

        // =========================
        // INSTRUCTION
        // =========================
        instructionLabel
                = new JLabel(
                        "Choose a language you prefer:"
                );

        instructionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        instructionLabel.setBounds(
                45, 80, 220, 30
        );

        mainPanel.add(instructionLabel);

        // =========================
        // SELECTED LANGUAGE
        // =========================
        selectedLanguageButton
                = new JButton("English");

        selectedLanguageButton.setBounds(
                60, 120, 160, 40
        );

        selectedLanguageButton.setFocusPainted(false);

        mainPanel.add(
                selectedLanguageButton
        );

        // =========================
        // CONFIRMATION LABEL
        // =========================
        confirmationLabel
                = new JLabel(
                        "Selected: English"
                );

        confirmationLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        confirmationLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        confirmationLabel.setBounds(
                40, 165, 200, 30
        );

        mainPanel.add(
                confirmationLabel
        );

        // =========================
        // LANGUAGE OPTIONS
        // =========================
        String[] languages = {
            "Afrikaans",
            "isiZulu",
            "isiXhosa",
            "SeSotho",
            "Sepedi",
            "Setswana",
            "itsonga",
            "Tshivenda"
        };

        int y = 210;

        for (String language : languages) {

            JButton languageButton
                    = new JButton(language);

            languageButton.setBounds(
                    60, y, 160, 40
            );

            languageButton.setFocusPainted(
                    false
            );

            languageButton.addActionListener(
                    new ActionListener() {

                @Override
                public void actionPerformed(
                        ActionEvent e) {

                    selectLanguage(language);
                }
            });

            mainPanel.add(languageButton);

            y += 55;
        }

        // =========================
        // BACK BUTTON
        // =========================
        JButton backButton
                = new JButton("Back");

        backButton.setBounds(
                90, 670, 100, 35
        );

        backButton.setBackground(
                Color.BLACK
        );

        backButton.setForeground(
                Color.WHITE
        );

        backButton.setFocusPainted(false);

        mainPanel.add(backButton);

        backButton.addActionListener(
                e -> dispose()
        );

        // =========================
        // SCROLL
        // =========================
        JScrollPane scrollPane
                = new JScrollPane(mainPanel);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        add(scrollPane);

        setVisible(true);
    }

    // ==================================================
    // TRANSLATIONS
    // ==================================================
    private void createTranslations() {

        translations
                = new HashMap<>();

        translations.put(
                "English",
                new String[]{
                    "Language",
                    "Choose a language you prefer:",
                    "Selected: English"
                }
        );

        translations.put(
                "Afrikaans",
                new String[]{
                    "Taal",
                    "Kies 'n taal wat jy verkies:",
                    "Gekies: Afrikaans"
                }
        );

        translations.put(
                "isiZulu",
                new String[]{
                    "Ulimi",
                    "Khetha ulimi oluthandayo:",
                    "Okhethiwe: isiZulu"
                }
        );

        translations.put(
                "isiXhosa",
                new String[]{
                    "Ulwimi",
                    "Khetha ulwimi oluthandayo:",
                    "Okhethiweyo: isiXhosa"
                }
        );

        translations.put(
                "SeSotho",
                new String[]{
                    "Puo",
                    "Khetha puo eo o e ratang:",
                    "E khethilweng: SeSotho"
                }
        );

        translations.put(
                "Sepedi",
                new String[]{
                    "Leleme",
                    "Kgetha leleme leo o le ratago:",
                    "Leleme le le kgethilwego: Sepedi"
                }
        );

        translations.put(
                "Setswana",
                new String[]{
                    "Puo",
                    "Tlhopha puo e o e ratang:",
                    "E e tlhophilweng: Setswana"
                }
        );

        translations.put(
                "itsonga",
                new String[]{
                    "Ririmi",
                    "Hlawula ririmi leri u ri rhandzaka:",
                    "Ri hlawuriwile: Xitsonga"
                }
        );

        translations.put(
                "Tshivenda",
                new String[]{
                    "Luambo",
                    "Nangani luambo lune na lu takalela:",
                    "Lo nangiwaho: Tshivenda"
                }
        );
    }

    // ==================================================
    // SELECT LANGUAGE + SAVE TO DATABASE
    // ==================================================
    private void selectLanguage(
            String language) {

        selectedLanguage
                = language;

        String[] translation
                = translations.get(language);

        if (translation != null) {

            titleLabel.setText(
                    translation[0]
            );

            instructionLabel.setText(
                    translation[1]
            );

            confirmationLabel.setText(
                    translation[2]
            );
        }

        selectedLanguageButton.setText(
                language
        );

        // =========================
        // SAVE LANGUAGE TO DATABASE
        // =========================
        try {

            Connection conn
                    = DBConnection.getConnection();

            // Check whether settings already exist
            String checkSQL
                    = "SELECT SettingsID "
                    + "FROM Settings "
                    + "WHERE SettingsID = 1";

            PreparedStatement checkStatement
                    = conn.prepareStatement(checkSQL);

            java.sql.ResultSet rs
                    = checkStatement.executeQuery();

            if (rs.next()) {

                // UPDATE existing settings
                String updateSQL
                        = "UPDATE Settings "
                        + "SET Language = ? "
                        + "WHERE SettingsID = 1";

                PreparedStatement updateStatement
                        = conn.prepareStatement(
                                updateSQL
                        );

                updateStatement.setString(
                        1,
                        language
                );

                updateStatement.executeUpdate();

                updateStatement.close();

            } else {

                // INSERT settings for first time
                String insertSQL
                        = "INSERT INTO Settings "
                        + "(SettingsID, UserID, "
                        + "Water_alerts, "
                        + "Electricity_alerts, "
                        + "Language) "
                        + "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement insertStatement
                        = conn.prepareStatement(
                                insertSQL
                        );

                insertStatement.setInt(
                        1, 1
                );

                insertStatement.setInt(
                        2, 1
                );

                insertStatement.setString(
                        3, "Enabled"
                );

                insertStatement.setString(
                        4, "Enabled"
                );

                insertStatement.setString(
                        5, language
                );

                insertStatement.executeUpdate();

                insertStatement.close();
            }

            rs.close();
            checkStatement.close();
            conn.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Language saved: "
                    + language
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save language.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // ==================================================
    // MAIN
    // ==================================================
    public static void main(String[] args) {

        new LanguagePage();
    }
}
