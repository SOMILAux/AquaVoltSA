package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class TermsPage extends JFrame{
    public TermsPage() {
        setTitle("AquaVolt SA - Terms and Conditions");
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (Theme.load("plain_logo.png") != null) setIconImage(Theme.load("plain_logo.png"));

        JTextArea termsArea = new JTextArea();
        termsArea.setText("AquaVolt SA Terms and Conditions...\n\n1. General Provisions...\n AquaVolt SA (“the App”) provides digital services for monitoring, billing, and payment of water and electricity usage.\n" +
"By using the App, all parties (Customers, Business Team, Municipalities/Eskom) agree to these Terms & Conditions.\n" +
"AquaVolt SA does not supply water or electricity directly; it facilitates access, transparency, and payments.\n" +
" \n2. Customer Terms...\nEligibility: Customers must be registered with a valid municipal account number, ID, or address.\n" +
"Access & Security: Customers agree to keep login credentials confidential and use OTP verification when required.\n" +
"Usage Monitoring: Customers can view real‑time water and electricity consumption, tariff rates, and free allocations.\n" +
"Payments: All payments made through the App are routed securely to the relevant municipality or Eskom. AquaVolt SA does not hold funds.\n" +
"Data Accuracy: Customers acknowledge that consumption data is provided by municipalities/Eskom and AquaVolt SA is not liable for discrepancies.\n" +
"Alerts & Notifications: Customers consent to receiving usage alerts, tariff updates, and payment reminders.\n" +
"Prohibited Use: Customers may not attempt to manipulate consumption data, bypass billing, or share unauthorized access.\n" +
"Liability: AquaVolt SA is not responsible for service interruptions caused by municipal or Eskom systems.\n"
                + "\n3. Business Team...\nConfidentiality: Team members must protect customer and municipal data, following strict privacy protocols.\n" +
"Integrity: No member may alter consumption data, tariffs, or payment records for personal gain.\n" +
"Transparency: All updates, bug fixes, and new features must be documented and communicated to stakeholders.\n" +
"Conflict of Interest: Team members must disclose any external affiliations with municipalities, Eskom, or competing apps.\n" +
"Intellectual Property: All code, designs, and branding created for AquaVolt SA remain the property of the company.\n" +
"Compliance: The team must adhere to South African laws on data protection, financial transactions, and municipal regulations.\n" +
"Termination: Breach of these terms may result in removal from the project and potential legal action.\n "
                + "\n4. Municipality & Eskom Terms...\nData Sharing: Municipalities and Eskom agree to provide accurate consumption and tariff data to AquaVolt SA.\n" +
"Billing Integration: Payments collected via the App are transferred directly to municipal/Eskom accounts.\n" +
"Tariff Updates: Municipalities/Eskom must update tariffs promptly to ensure customers see correct rates.\n" +
"Dashboard Access: Authorized municipal staff may access dashboards to monitor usage by province, city, suburb, and street.\n" +
"Alerts & Analytics: Municipalities/Eskom may use the App’s analytics to detect leaks, illegal connections, or grid strain.\n" +
"Revenue Collection: AquaVolt SA supports revenue collection but does not assume responsibility for unpaid bills.\n" +
"Data Ownership: Municipalities/Eskom retain ownership of consumption and tariff data; AquaVolt SA only displays and processes it.\n" +
"Partnership Scope: AquaVolt SA acts as a digital partner, not a supplier of water/electricity.\n"
                + "\n5. Legal & Compliance...\nPrivacy: All data is handled according to South Africa’s POPIA (Protection of Personal Information Act).\n" +
"Dispute Resolution: Any disputes between customers, municipalities, Eskom, or AquaVolt SA will be resolved under South African law.\n" +
"Termination of Service: AquaVolt SA reserves the right to suspend accounts for misuse, fraud, or non‑compliance.\n" +
"Amendments: Terms & Conditions may be updated; customers and partners will be notified of changes.");

        termsArea.setEditable(false);
        termsArea.setLineWrap(true);
        termsArea.setWrapStyleWord(true);
        termsArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        termsArea.setMargin(new Insets(8, 16, 8, 16));
        termsArea.setCaretPosition(0);

        JLabel heading = Theme.label("Terms and Conditions", Theme.TITLE, Theme.BLUE, SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(18, 0, 10, 0));

        JScrollPane scrollPane = new JScrollPane(termsArea);
        scrollPane.setBorder(null);
        JButton backBtn = new Theme.Btn("Back", Theme.GREY);

        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        backRow.setBackground(Color.WHITE);
        backRow.add(backBtn);

        JPanel south = new JPanel(new BorderLayout());
        south.add(backRow, BorderLayout.NORTH);
        south.add(Theme.footer(), BorderLayout.SOUTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.add(heading, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);
        root.add(south, BorderLayout.SOUTH);
        setContentPane(root);

        backBtn.addActionListener(e -> {
            new SignupPage3().setVisible(true);
            dispose();
        });
    }
}
