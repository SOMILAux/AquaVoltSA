//Final update done by Sbahle
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Admin sends a notification (water, electricity, account, payment or system) to one customer or to everyone.
 */
public class NotifyDialog extends JDialog {
    private static final String ALL = "All customers";
    private static final String[] CATEGORIES = {"WATER", "ELECTRICITY", "ACCOUNT", "PAYMENT", "SYSTEM"};
    // quick messages: {label, category, title, message}
    private static final String[][] TEMPLATES = {
        {"Write my own", "SYSTEM", "", ""},
        {"Planned water outage", "WATER", "Water Outage", "Water will be off in your area tomorrow from 08:00 to 14:00 for maintenance."},
        {"Possible water leak", "WATER", "Leak Alert", "We detected unusual water usage at your property. Please check for leaks."},
        {"High water usage", "WATER", "Usage Warning", "Your water usage is higher than normal this week."},
        {"Loadshedding update", "ELECTRICITY", "Loadshedding Update", "Loadshedding is scheduled in your area today. Check the times in the app."},
        {"Power restored", "ELECTRICITY", "Power Restored", "Electricity has been restored in your area. Thank you for your patience."},
        {"Account verified", "ACCOUNT", "Account Verified", "Your AquaVolt SA account has been verified. You can now use all features."},
        {"Details needed", "ACCOUNT", "Details Needed", "Please update your meter or address details so we can finish verifying your account."},
        {"Payment reminder", "PAYMENT", "Payment Reminder", "Your account payment is due soon. Pay in the app to avoid interruptions."}
    };

    private final Theme.HintField titleField = new Theme.HintField("Notification title");
    private final JTextArea messageArea = new JTextArea(5, 20);
    private final JComboBox<String> toBox = new JComboBox<>();
    private final JComboBox<String> categoryBox = new JComboBox<>(CATEGORIES);
    private final JComboBox<String> templateBox = new JComboBox<>();
    private final JLabel err = Theme.err();
    /** Set when a notification was sent, e.g. "Notification sent to Thando". */
    String sentSummary = null;

    /** presetUser = the customer to send to, or null to choose (including "All customers"). */
    public NotifyDialog(JFrame owner, String adminUsername, String presetUser) {
        super(owner, "Send notification", true);
        List<String> names = new ArrayList<>();
        names.add(ALL);
        for (UserStore.User u : UserStore.all()) names.add(u.username);
        for (String n : names) toBox.addItem(n);
        if (presetUser != null) toBox.setSelectedItem(presetUser);
        for (String[] t : TEMPLATES) templateBox.addItem(t[0]);
        for (JComboBox<String> b : java.util.Arrays.asList(toBox, categoryBox, templateBox)) b.setFont(Theme.BODY);
        messageArea.setFont(Theme.BODY);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));

        row(p, Theme.label("Send notification", Theme.TITLE, Theme.NAVY, SwingConstants.CENTER));
        p.add(Box.createVerticalStrut(10));
        row(p, caption("To"));
        row(p, toBox);
        p.add(Box.createVerticalStrut(8));
        row(p, caption("Quick message"));
        row(p, templateBox);
        p.add(Box.createVerticalStrut(8));
        row(p, caption("About"));
        row(p, categoryBox);
        p.add(Box.createVerticalStrut(8));
        row(p, caption("Title"));
        row(p, titleField);
        p.add(Box.createVerticalStrut(8));
        row(p, caption("Message"));
        JScrollPane sp = new JScrollPane(messageArea);
        sp.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        row(p, sp);
        row(p, err);
        p.add(Box.createVerticalStrut(10));

        JButton send = new Theme.Btn("Send", Theme.NAVY);
        JButton cancel = new Theme.Btn("Cancel", Theme.GREY);
        send.setPreferredSize(new Dimension(130, 44));
        cancel.setPreferredSize(new Dimension(130, 44));
        row(p, Theme.row(send, cancel));

        templateBox.addActionListener(e -> {
            String[] t = TEMPLATES[templateBox.getSelectedIndex()];
            if (!t[2].isEmpty()) {
                categoryBox.setSelectedItem(t[1]);
                titleField.setText(t[2]);
                messageArea.setText(t[3]);
            }
        });
        send.addActionListener(e -> send(adminUsername));
        cancel.addActionListener(e -> dispose());

        setContentPane(p);
        pack();
        setLocationRelativeTo(owner);
    }

    private static JLabel caption(String text) {
        return Theme.label(text, Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT);
    }

    private static void row(JPanel p, JComponent c) {
        Theme.fit(c);
        p.add(c);
    }

    private void send(String adminUsername) {
        String title = titleField.getText().trim(), message = messageArea.getText().trim();
        Theme.ok(err, titleField);
        if (title.isEmpty() || message.isEmpty()) {
            Theme.bad(err, titleField, "Enter a title and a message");
            return;
        }
        String to = (String) toBox.getSelectedItem(), category = (String) categoryBox.getSelectedItem();
        if (ALL.equals(to)) {
            int n = NotificationStore.sendToAll(category, title, message, adminUsername);
            sentSummary = "Notification sent to " + n + " customers";
        } else if (NotificationStore.send(to, category, title, message, adminUsername)) {
            sentSummary = "Notification sent to " + to;
        } else {
            return;
        }
        AdminStore.log(adminUsername, "SEND_NOTIFICATION", to + ": " + title);
        dispose();
    }
}