package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * "Forgot Login" page. Same look as the Login / Sign-up pages.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class ForgotLoginPage extends BasePage {
    private static final int ID_LENGTH = 13;      // South African ID numbers have 13 digits (same as sign-up)

    private UserStore.User account;               // the customer found by the ID number (null until then)

    // ---- step 1 ----
    private final Theme.HintField idField = new Theme.HintField("Enter ID-number");
    private final JLabel idErr = Theme.err();

    // ---- step 2 ----
    private final Theme.HintField userField = new Theme.HintField("Enter new Username");
    private final Theme.HintField userField2 = new Theme.HintField("Re-enter new Username");
    private final Theme.HintPassword passField = new Theme.HintPassword("Enter new Password");
    private final Theme.HintPassword passField2 = new Theme.HintPassword("Re-enter new Password");
    private final JLabel[] err = {Theme.err(), Theme.err(), Theme.err(), Theme.err()};

    public ForgotLoginPage() {
        super("Forgot Login");
        showIdStep();
    }

    // ================================================================ step 1: ask for the ID number
    private void showIdStep() {
        body.removeAll();
        gap(6);
        put(Theme.label("Forgot Login", Theme.TITLE, Theme.BLUE, SwingConstants.CENTER));
        gap(6);
        put(Theme.label("<html><div style='text-align:center;width:300px'>Please type in your ID number "
                + "so we can find your account</div></html>", Theme.BODY, Theme.GREY, SwingConstants.CENTER));
        gap(16);
        put(idField);
        put(idErr);
        gap(14);

        JButton submitBtn = new Theme.Btn("Submit", Theme.BLUE);
        JButton backBtn = new Theme.Btn("Back", Theme.GREY);
        submitBtn.setPreferredSize(new Dimension(130, 44));
        backBtn.setPreferredSize(new Dimension(130, 44));
        put(Theme.row(submitBtn, backBtn));

        getRootPane().setDefaultButton(submitBtn);        // Enter key = Submit
        submitBtn.addActionListener(e -> checkId());
        backBtn.addActionListener(e -> go(new LoginSignupPage()));

        body.revalidate();
        body.repaint();
    }

    private void checkId() {
        String id = idField.getText().trim();
        Theme.ok(idErr, idField);
        if (!id.matches("\\d{" + ID_LENGTH + "}")) {
            Theme.bad(idErr, idField, "ID number must be " + ID_LENGTH + " digits");
            return;
        }
        UserStore.User found = UserStore.findByIdNumber(id);
        if (found == null) {
            Theme.bad(idErr, idField, "No account matches that ID number");
            return;
        }
        account = found;
        showNewLoginStep();
    }

    // ================================================================ step 2: choose a new username + password
    private void showNewLoginStep() {
        body.removeAll();
        gap(6);
        put(Theme.label("<html><div style='text-align:center;width:330px'>Welcome back<br>" + esc(account.fullName)
                + "</div></html>", Theme.TITLE.deriveFont(20f), Theme.BLUE, SwingConstants.CENTER));
        gap(6);
        put(Theme.label("Choose your new login details", Theme.BODY, Theme.GREY, SwingConstants.CENTER));
        gap(10);

        JButton randomizeBtn = new Theme.Btn("Randomize", Theme.BLUE);
        randomizeBtn.setFont(Theme.BOLD.deriveFont(12f));
        randomizeBtn.setPreferredSize(new Dimension(100, 44));
        JPanel passwordRow = new JPanel(new BorderLayout(8, 0));
        passwordRow.setOpaque(false);
        passwordRow.add(passField, BorderLayout.CENTER);
        passwordRow.add(randomizeBtn, BorderLayout.EAST);

        String[] caption = {"1. Enter your new Username", "2. Re-enter your new Username",
            "3. Enter your new Password or click Randomize", "4. Re-enter your new Password"};
        JComponent[] input = {userField, userField2, passwordRow, passField2};
        for (int i = 0; i < input.length; i++) {
            put(Theme.label("<html><div style='width:330px'>" + caption[i] + "</div></html>",
                    Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT));
            gap(3);
            put(input[i]);
            put(err[i]);
            gap(6);
        }
        gap(6);

        JButton submitBtn = new Theme.Btn("Submit", Theme.BLUE);
        JButton backBtn = new Theme.Btn("Back", Theme.GREY);
        submitBtn.setPreferredSize(new Dimension(130, 44));
        backBtn.setPreferredSize(new Dimension(130, 44));
        put(Theme.row(submitBtn, backBtn));
        gap(10);

        randomizeBtn.addActionListener(e -> {
            passField.setText(generateRandomPassword());
            passField.reveal(true);                     // show it so the customer can see / write it down
            passField2.setText("");
        });
        getRootPane().setDefaultButton(submitBtn);
        submitBtn.addActionListener(e -> saveNewLogin());
        backBtn.addActionListener(e -> go(new LoginSignupPage()));

        body.revalidate();
        body.repaint();
    }

    private void saveNewLogin() {
        String user = userField.getText().trim(), user2 = userField2.getText().trim();
        String pw = new String(passField.getPassword()), pw2 = new String(passField2.getPassword());
        JComponent[] f = {userField, userField2, passField, passField2};
        String[] m = {
            !user.matches("\\S{3,30}") ? "Username must be 3 to 30 characters, no spaces" : null,   // change the rule here if needed
            !user2.equals(user) ? "Usernames do not match" : null,
            pw.length() < 6 ? "Password must be at least 6 characters" : null,                       // same rule as sign-up
            !pw2.equals(pw) ? "Passwords do not match" : null};
        boolean bad = false;
        for (int i = 0; i < f.length; i++) {
            Theme.ok(err[i], f[i]);
            if (m[i] != null) bad = Theme.bad(err[i], f[i], m[i]);
        }
        if (bad) return;

       
           UserStore.User owner = UserStore.find(user);
        if (owner != null && !owner.id.equals(account.id)) {
            Theme.bad(err[0], userField, "That username is already taken");
            return;
        }

        String problem = UserStore.updateLogin(account, user, UserStore.hash(pw));   // saves to the database
        if (problem != null) {
            JOptionPane.showMessageDialog(this, problem, "Could not update", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LoginSignupPage login = new LoginSignupPage();
        go(login);
        Theme.toast(login, "Your login details were updated. Please log in.", 640, 4000);
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) sb.append(chars.charAt(rand.nextInt(chars.length())));
        return sb.toString();
    }

      private static String esc(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}