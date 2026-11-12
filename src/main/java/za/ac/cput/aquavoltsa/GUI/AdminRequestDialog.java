//Final update done by Oyama and Buhle
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * "Add new admin" form. The new admin is saved as PENDING; the main admin must accept it on the dashboard.
 */
public class AdminRequestDialog extends JDialog {
    private final Theme.HintField nameField = new Theme.HintField("Full name and surname");
    private final Theme.HintField userField = new Theme.HintField("e.g. thando.aquavolt.sa");
    private final Theme.HintField emailField = new Theme.HintField("Email address");
    private final Theme.HintField phoneField = new Theme.HintField("Phone number");
    private final Theme.HintPassword passField = new Theme.HintPassword("Password (8+ characters)");
    private final Theme.HintPassword confirmField = new Theme.HintPassword("Re-enter password");
    private final JLabel[] err = new JLabel[6];

    /** requestedBy = the logged-in admin who is adding this person, or null when it is a self-request. */
    public AdminRequestDialog(JFrame owner, String requestedBy) {
        super(owner, "Request admin access", true);
        for (int i = 0; i < err.length; i++) err[i] = Theme.err();

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));

        String[] caption = {"Full name", "Username (must end with .aquavolt.sa)", "Email address", "Phone number",
            "Password", "Re-enter password"};
        JComponent[] input = {nameField, userField, emailField, phoneField, passField, confirmField};

        JLabel title = Theme.label("New administrator", Theme.TITLE, Theme.NAVY, SwingConstants.CENTER);
        addTo(p, title);
        p.add(Box.createVerticalStrut(10));
        for (int i = 0; i < input.length; i++) {
            addTo(p, Theme.label(caption[i], Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT));
            p.add(Box.createVerticalStrut(3));
            addTo(p, input[i]);
            addTo(p, err[i]);
            p.add(Box.createVerticalStrut(6));
        }
        addTo(p, Theme.label("The main admin (" + AdminStore.MAIN + ") must accept this request.", Theme.SMALL,
                Theme.GREY, SwingConstants.CENTER));
        p.add(Box.createVerticalStrut(10));

        JButton sendBtn = new Theme.Btn("Send request", Theme.NAVY);
        JButton cancelBtn = new Theme.Btn("Cancel", Theme.GREY);
        sendBtn.setPreferredSize(new Dimension(140, 44));
        cancelBtn.setPreferredSize(new Dimension(120, 44));
        addTo(p, Theme.row(sendBtn, cancelBtn));

        sendBtn.addActionListener(e -> submit(requestedBy));
        cancelBtn.addActionListener(e -> dispose());

        setContentPane(p);
        pack();
        setLocationRelativeTo(owner);
    }

    private static void addTo(JPanel p, JComponent c) {
        Theme.fit(c);
        p.add(c);
    }

    private void submit(String requestedBy) {
        String name = nameField.getText().trim();
        String user = userField.getText().trim().toLowerCase();
        String mail = emailField.getText().trim();
        String phone = phoneField.getText().replaceAll("[\\s-]", "");
        String pw = new String(passField.getPassword()), pw2 = new String(confirmField.getPassword());

        JComponent[] f = {nameField, userField, emailField, phoneField, passField, confirmField};
        String[] m = {
            name.split("\\s+").length < 2 ? "Enter first name and surname" : null,
            !user.matches("[a-z0-9._-]{3,24}\\.aquavolt\\.sa") ? "Username must end with .aquavolt.sa (e.g. thando.aquavolt.sa)"
                : AdminStore.find(user) != null ? "That username is already taken" : null,
            !mail.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$") ? "Enter a valid email address" : null,
            !phone.matches("^(\\+27|0)\\d{9}$") ? "Enter a valid phone number" : null,
            pw.length() < 8 ? "Password must be at least 8 characters" : null,
            !pw2.equals(pw) ? "Passwords do not match" : null};
        boolean bad = false;
        for (int i = 0; i < f.length; i++) {
            Theme.ok(err[i], f[i]);
            if (m[i] != null) bad = Theme.bad(err[i], f[i], m[i]);
        }
        if (bad) return;

        AdminStore.Admin a = new AdminStore.Admin();
        a.username = user;
        a.fullName = name;
        a.email = mail;
        a.phone = phone;
        a.passHash = UserStore.hash(pw);
        a.requestedBy = requestedBy == null ? "self-request" : requestedBy;
        if (AdminStore.request(a)) {
            AdminStore.log(requestedBy == null ? user : requestedBy, "ADMIN_REQUEST", user);
            JOptionPane.showMessageDialog(this, "Request sent!\n" + AdminStore.MAIN
                    + " must accept it before " + user + " can log in.", "Request sent", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}