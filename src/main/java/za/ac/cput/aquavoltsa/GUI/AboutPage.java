package za.ac.cput.aquavoltsa.GUI;


/**
 *
 * @author Sbahle
 */
import javax.swing.*;
import java.awt.*;

public class AboutPage extends JFrame {

    public AboutPage() {

        setTitle("About AquaVolt SA");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(380, 1100));

        // =========================
        // BACK ARROW
        // =========================
        JButton backArrow = new JButton("‹");
        backArrow.setFont(new Font("Arial", Font.PLAIN, 40));
        backArrow.setBounds(10, 15, 45, 45);
        backArrow.setBorderPainted(false);
        backArrow.setContentAreaFilled(false);
        backArrow.setFocusPainted(false);
        backArrow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        backArrow.addActionListener(e -> dispose());

        panel.add(backArrow);

        // =========================
        // LOGO
        // =========================
        ImageIcon logoIcon = new ImageIcon("logo.png");

        JLabel logo = new JLabel();

        if (logoIcon.getIconWidth() > 0) {
            Image image = logoIcon.getImage();
            Image resizedImage = image.getScaledInstance(
                    150,
                    150,
                    Image.SCALE_SMOOTH
            );

            logo.setIcon(new ImageIcon(resizedImage));
        }

        logo.setBounds(115, 35, 150, 150);
        panel.add(logo);

        // =========================
        // TITLE
        // =========================
        JLabel title = new JLabel("AquaVolt SA", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(55, 170, 270, 40);

        panel.add(title);

        // =========================
        // FIRST PARAGRAPH
        // =========================
        JLabel para1 = new JLabel(
                "<html><div style='text-align:center;'>"
                + "<b>AquaVolt SA</b> is a smart utility "
                + "management platform designed to bring "
                + "<font color='blue'>clarity</font>, "
                + "<font color='blue'>convenience</font>, and "
                + "<font color='blue'>control</font> to "
                + "households across South Africa."
                + "</div></html>"
        );

        para1.setFont(new Font("Arial", Font.PLAIN, 15));
        para1.setBounds(25, 215, 330, 100);

        panel.add(para1);

        // =========================
        // SECOND PARAGRAPH
        // =========================
        JLabel para2 = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Our app empowers users to monitor their "
                + "water and electricity usage in real time, "
                + "view tariff fees, track free allocations, and "
                + "receive instant alerts when consumption is "
                + "high."
                + "</div></html>"
        );

        para2.setFont(new Font("Arial", Font.PLAIN, 15));
        para2.setBounds(25, 320, 330, 110);

        panel.add(para2);

        // =========================
        // THIRD PARAGRAPH
        // =========================
        JLabel para3 = new JLabel(
                "<html><div style='text-align:center;'>"
                + "By partnering with South African "
                + "municipalities and Eskom, AquaVolt SA acts "
                + "as a bridge between consumers and "
                + "providers, simplifying billing and payments "
                + "while ensuring transparency. Instead of "
                + "standing in queues or juggling multiple apps, "
                + "households can manage everything in one "
                + "secure, user-friendly platform."
                + "</div></html>"
        );

        para3.setFont(new Font("Arial", Font.PLAIN, 15));
        para3.setBounds(25, 425, 330, 165);

        panel.add(para3);

        // =========================
        // MISSION TITLE
        // =========================
        JLabel mission = new JLabel(
                "Our mission is simple:",
                SwingConstants.CENTER
        );

        mission.setFont(new Font("Arial", Font.BOLD, 16));
        mission.setBounds(75, 590, 230, 30);

        panel.add(mission);

        // =========================
        // MISSION BULLET 1
        // =========================
        JLabel bullet1 = new JLabel(
                "<html><div style='text-align:left;'>"
                + "• 💡 Empower communities with easy<br>"
                + "&nbsp;&nbsp;&nbsp;access to usage data."
                + "</div></html>"
        );

        bullet1.setFont(new Font("Arial", Font.PLAIN, 14));
        bullet1.setBounds(25, 625, 330, 55);

        panel.add(bullet1);

        // =========================
        // MISSION BULLET 2
        // =========================
        JLabel bullet2 = new JLabel(
                "<html><div style='text-align:left;'>"
                + "• 🌍 Promote sustainability by encouraging<br>"
                + "&nbsp;&nbsp;&nbsp;smarter consumption."
                + "</div></html>"
        );

        bullet2.setFont(new Font("Arial", Font.PLAIN, 14));
        bullet2.setBounds(25, 690, 330, 55);

        panel.add(bullet2);

        // =========================
        // MISSION BULLET 3
        // =========================
        JLabel bullet3 = new JLabel(
                "<html><div style='text-align:left;'>"
                + "• 🤝 Support municipalities and Eskom with<br>"
                + "&nbsp;&nbsp;&nbsp;efficient revenue collection."
                + "</div></html>"
        );

        bullet3.setFont(new Font("Arial", Font.PLAIN, 14));
        bullet3.setBounds(25, 755, 330, 55);

        panel.add(bullet3);

        // =========================
        // FINAL PARAGRAPH
        // =========================
        JLabel para4 = new JLabel(
                "<html><div style='text-align:center;'>"
                + "AquaVolt SA isn't just an app—it's a step "
                + "toward a smarter, more connected future "
                + "where managing essential resources is "
                + "effortless, transparent, and sustainable."
                + "</div></html>"
        );

        para4.setFont(new Font("Arial", Font.PLAIN, 14));
        para4.setBounds(25, 825, 330, 100);

        panel.add(para4);

        // =========================
        // BOTTOM BACK BUTTON
        // =========================
        JButton backButton = new JButton("Back");

        backButton.setBounds(135, 950, 100, 35);
        backButton.setBackground(new Color(66, 133, 244));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(
                new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );

        backButton.addActionListener(e -> dispose());

        panel.add(backButton);

        // =========================
        // SCROLL PANE
        // =========================
        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {

        new AboutPage();

    }
}
