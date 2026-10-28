package za.ac.cput.aquavoltsa.GUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sbahle
 */
import javax.swing.*;
import java.awt.*;

public class TermsPage1 extends JFrame {

    public TermsPage1() {

        setTitle("Terms and Conditions");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(380, 2200));

        // TITLE
        JLabel title = new JLabel("Terms and conditions");

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(60, 130, 300, 40);

        panel.add(title);

        // SECTION 1
        JLabel section1 = new JLabel("1. General Provisions");

        section1.setFont(new Font("Arial", Font.BOLD, 18));
        section1.setBounds(20, 190, 250, 30);

        panel.add(section1);

        JLabel text1 = new JLabel("<html>"
                + "• AquaVolt SA provides digital services for monitoring, "
                + "billing, and payment of water and electricity usage.<br><br>"
                + "• By using the App, all parties agree to these Terms & Conditions.<br><br>"
                + "• AquaVolt SA does not supply water or electricity directly."
                + "</html>");

        text1.setFont(new Font("Arial", Font.PLAIN, 15));
        text1.setBounds(30, 230, 320, 170);

        panel.add(text1);

        // SECTION 2
        JLabel section2 = new JLabel("2. Customer Terms");

        section2.setFont(new Font("Arial", Font.BOLD, 18));
        section2.setBounds(20, 420, 250, 30);

        panel.add(section2);

        JLabel text2 = new JLabel("<html>"
                + "<font color='blue'><b>Eligibility:</b></font> Customers must register with valid details.<br><br>"
                + "<font color='blue'><b>Access & Security:</b></font> Customers must keep login details secure.<br><br>"
                + "<font color='blue'><b>Usage Monitoring:</b></font> Users can monitor utility usage.<br><br>"
                + "<font color='blue'><b>Payments:</b></font> Payments are routed securely."
                + "</html>");

        text2.setFont(new Font("Arial", Font.PLAIN, 15));
        text2.setBounds(30, 460, 320, 250);

        panel.add(text2);

// SECTION 3
        JLabel section3 = new JLabel("3. Business Team");

        section3.setFont(new Font("Arial", Font.BOLD, 18));
        section3.setBounds(20, 730, 250, 30);

        panel.add(section3);

        JLabel text3 = new JLabel("<html>"
                + "<font color='blue'><b>Confidentiality:</b></font> Team members must protect data.<br><br>"
                + "<font color='blue'><b>Integrity:</b></font> No member may alter records improperly.<br><br>"
                + "<font color='blue'><b>Transparency:</b></font> Updates and changes must be communicated.<br><br>"
                + "<font color='blue'><b>Conflict of Interest:</b></font> Team members must avoid conflicts."
                + "</html>");

        text3.setFont(new Font("Arial", Font.PLAIN, 15));
        text3.setBounds(30, 770, 320, 250);

        panel.add(text3);

// SECTION 4
        JLabel section4 = new JLabel("4. Municipality & Eskom");

        section4.setFont(new Font("Arial", Font.BOLD, 18));
        section4.setBounds(20, 1040, 300, 30);

        panel.add(section4);

        JLabel text4 = new JLabel("<html>"
                + "<font color='blue'><b>Data Sharing:</b></font> Municipalities and Eskom provide tariff data.<br><br>"
                + "<font color='blue'><b>Billing Integration:</b></font> Payments connect directly to accounts.<br><br>"
                + "<font color='blue'><b>Dashboard Access:</b></font> Authorized staff may access dashboards.<br><br>"
                + "<font color='blue'><b>Alerts & Analytics:</b></font> Municipalities may use analytics tools."
                + "</html>");

        text4.setFont(new Font("Arial", Font.PLAIN, 15));
        text4.setBounds(30, 1080, 320, 250);

        panel.add(text4);

        JLabel section5 = new JLabel("5. Legal & Compliance");

        section5.setFont(new Font("Arial", Font.BOLD, 18));
        section5.setBounds(20, 1350, 300, 30);

        panel.add(section5);

        JLabel text5 = new JLabel("<html>"
                + "<font color='blue'><b>Privacy:</b></font> User data is protected according to POPIA.<br><br>"
                + "<font color='blue'><b>Dispute Resolution:</b></font> Disputes will follow South African law.<br><br>"
                + "<font color='blue'><b>Termination of Service:</b></font> AquaVolt SA may suspend accounts for misuse.<br><br>"
                + "<font color='blue'><b>Amendments:</b></font> Terms and conditions may be updated."
                + "</html>");

        text5.setFont(new Font("Arial", Font.PLAIN, 15));
        text5.setBounds(30, 1390, 320, 220);

        panel.add(text5);

// PARTNERED WITH
        JLabel partnered = new JLabel("Partnered with:");

        partnered.setFont(new Font("Arial", Font.BOLD, 20));
        partnered.setBounds(90, 1630, 220, 30);

        panel.add(partnered);

// MUNICIPALITY LABEL
        JLabel municipality = new JLabel("SA Municipality");

        municipality.setFont(new Font("Arial", Font.BOLD, 16));
        municipality.setBounds(30, 1690, 150, 30);

        panel.add(municipality);

// ESKOM LABEL
        JLabel eskom = new JLabel("Eskom");

        eskom.setFont(new Font("Arial", Font.BOLD, 18));
        eskom.setForeground(Color.BLUE);
        eskom.setBounds(230, 1690, 100, 30);

        panel.add(eskom);

// BACK BUTTON
        JButton backButton = new JButton("Back");

        backButton.setBounds(120, 1770, 120, 40);
        backButton.setBackground(new Color(100, 149, 237));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> dispose());

        panel.add(backButton);

// SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(panel);

        add(scrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {

        new TermsPage();

    }
}
