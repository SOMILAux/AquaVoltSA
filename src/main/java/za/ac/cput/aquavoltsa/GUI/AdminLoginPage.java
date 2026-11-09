package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;

/**
 * Administrator login (main admin: admin12304 / admin12304).
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class AdminLoginPage extends BasePage {
    private final Theme.HintField usernameField = new Theme.HintField("Admin username");
    private final Theme.HintPassword passwordField = new Theme.HintPassword("Admin password");
    private final JLabel userErr = Theme.err(), passErr = Theme.err();

    public AdminLoginPage() {
        super("Admin Login");

        gap(6);
        put(Theme.label("ADMIN PORTAL", Theme.TITLE, Theme.NAVY, SwingConstants.CENTER));
        put(Theme.label("Log in with your administrator account", Theme.BODY, Theme.GREY, SwingConstants.CENTER));
        gap(14);
        put(usernameField);
        put(userErr);
        gap(8);
        put(passwordField);
        put(passErr);
        gap(14);

        JButton enterBtn = put(new Theme.Btn("Enter", Theme.NAVY));
        gap(6);
        JButton requestBtn = put(Theme.link("Request admin access"));
        JButton backBtn = put(Theme.link("Back"));
        backBtn.setForeground(Theme.GREY);

        getRootPane().setDefaultButton(enterBtn);
        enterBtn.addActionListener(e -> login());
        requestBtn.addActionListener(e -> new AdminRequestDialog(this, null).setVisible(true));
        backBtn.addActionListener(e -> go(new RoleSelectPage()));
    }

    private void login() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        Theme.ok(userErr, usernameField);
        Theme.ok(passErr, passwordField);
        boolean bad = false;
        if (u.isEmpty()) bad = Theme.bad(userErr, usernameField, "Enter your admin username");
        if (p.isEmpty()) bad = Theme.bad(passErr, passwordField, "Enter your password");
        if (bad) return;

        AdminStore.Admin admin = AdminStore.find(u);
        if (admin == null || !admin.passHash.equals(UserStore.hash(p))) {
            Theme.bad(passErr, passwordField, "Incorrect username or password");
            return;
        }
        if (!AdminStore.APPROVED.equals(admin.status)) {
            JOptionPane.showMessageDialog(this,
                    "Your admin request is still waiting for approval.\n" + AdminStore.MAIN
                    + " must accept it before you can log in.",
                    "Request pending", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        go(new AdminVerificationPage(admin));
    }
}