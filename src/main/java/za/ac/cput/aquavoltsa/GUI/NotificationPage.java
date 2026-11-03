package za.ac.cput.aquavoltsa.GUI;


/**
 *
 * @author Sbahle Simelane
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class NotificationPage extends JFrame {

    public NotificationPage() {

        setTitle("AquaVolt SA Notifications");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(380, 750));

        JLabel title = new JLabel("AquaVolt SA");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(100, 30, 250, 30);
        panel.add(title);

        JLabel heading = new JLabel("Notifications");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBounds(115, 70, 200, 30);
        panel.add(heading);

        // =========================
        // LOAD NOTIFICATIONS FROM DATABASE
        // =========================
        int yPosition = 130;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT NotificationTitle, NotificationMessage "
                    + "FROM Notifications "
                    + "ORDER BY NotificationID";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next() && yPosition <= 490) {

                String notificationTitle
                        = rs.getString("NotificationTitle");

                String notificationMessage
                        = rs.getString("NotificationMessage");

                JPanel notificationPanel = new JPanel();

                notificationPanel.setBounds(
                        40, yPosition, 300, 70
                );

                notificationPanel.setBackground(Color.WHITE);

                notificationPanel.setBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        )
                );

                notificationPanel.setLayout(null);

                notificationPanel.setCursor(
                        new Cursor(Cursor.HAND_CURSOR)
                );

                JLabel notificationTitleLabel
                        = new JLabel(notificationTitle.toUpperCase());

                notificationTitleLabel.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );

// Notification colours
                if (notificationTitle.equalsIgnoreCase("Drought Alert")) {
                    notificationTitleLabel.setForeground(Color.RED);

                } else if (notificationTitle.equalsIgnoreCase("Loadshedding Update")) {
                    notificationTitleLabel.setForeground(Color.ORANGE);

                } else if (notificationTitle.equalsIgnoreCase("Usage Warning")) {
                    notificationTitleLabel.setForeground(Color.BLUE);

                } else if (notificationTitle.equalsIgnoreCase("Payment Reminder")) {
                    notificationTitleLabel.setForeground(new Color(0, 153, 0));

                } else if (notificationTitle.equalsIgnoreCase("System Update")) {
                    notificationTitleLabel.setForeground(Color.BLUE);
                }

                notificationTitleLabel.setBounds(
                        10, 10, 270, 20
                );

                JLabel notificationMessageLabel
                        = new JLabel(notificationMessage);

                notificationMessageLabel.setBounds(
                        10, 35, 280, 20
                );

                notificationPanel.add(
                        notificationTitleLabel
                );

                notificationPanel.add(
                        notificationMessageLabel
                );

                // CLICK NOTIFICATION
                notificationPanel.addMouseListener(
                        new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e) {

                        openNotificationDetails(
                                notificationTitle,
                                notificationMessage
                        );
                    }
                });

                panel.add(notificationPanel);

                yPosition += 90;
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load notifications from database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }

        // =========================
        // BACK BUTTON
        // =========================
        JButton backButton = new JButton("Back");

        backButton.setBounds(
                140, 590, 100, 35
        );

        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        backButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        panel.add(backButton);

        backButton.addActionListener(
                e -> dispose()
        );

        JScrollPane scrollPane
                = new JScrollPane(panel);

        add(scrollPane);

        setVisible(true);
    }

    // ==================================================
    // NOTIFICATION DETAILS
    // ==================================================
    private void openNotificationDetails(
            String title,
            String message) {

        JFrame detailsFrame
                = new JFrame("Notification Details");

        detailsFrame.setSize(400, 500);

        detailsFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        detailsFrame.setLocationRelativeTo(this);

        JPanel detailsPanel
                = new JPanel();

        detailsPanel.setLayout(null);
        detailsPanel.setBackground(Color.WHITE);

        JLabel heading
                = new JLabel(title);

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        heading.setBounds(
                30, 40, 330, 40
        );

        detailsPanel.add(heading);

        JTextArea messageArea
                = new JTextArea(message);

        messageArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setBackground(Color.WHITE);

        JScrollPane messageScroll
                = new JScrollPane(messageArea);

        messageScroll.setBounds(
                30, 100, 330, 200
        );

        detailsPanel.add(messageScroll);

        JButton detailsBackButton
                = new JButton("Back");

        detailsBackButton.setBounds(
                140, 340, 100, 35
        );

        detailsBackButton.setBackground(Color.BLACK);
        detailsBackButton.setForeground(Color.WHITE);
        detailsBackButton.setFocusPainted(false);

        detailsPanel.add(detailsBackButton);

        detailsBackButton.addActionListener(
                e -> detailsFrame.dispose()
        );

        detailsFrame.add(detailsPanel);

        detailsFrame.setVisible(true);
    }

    // ==================================================
    // MAIN METHOD
    // ==================================================
    public static void main(String[] args) {

        new NotificationPage();
    }
}
