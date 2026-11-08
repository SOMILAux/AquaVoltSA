package za.ac.cput.aquavoltsa.GUI;
// This class manages the AquaVolt SA notification functionality.

/**
 *
 * @author Sbahle Simelane
<<<<<<< HEAD
 * RESTYLED + CONNECTED by Oyama: notifications come from the NOTIFICATIONS table in AquaVoltDB for the logged-in
 * customer (admins send them from the admin dashboard). They are shown as cards in the same layout as the other
 * pages; unread ones have a NEW badge and become "read" when opened. The back arrow returns to the dashboard
 * when opened from there, otherwise to Settings.
=======
 * CONNECTED by Oyama: notifications now come from the NOTIFICATIONS table in AquaVoltDB for the
 * logged-in customer (admins send them from the admin dashboard). Unread ones are highlighted and
 * become "read" when opened. Back returns to the dashboard when opened from there, otherwise to Settings.
>>>>>>> ec7d8c4 (This is the Login-in pages done by Oyama)
 */

import java.awt.*;
<<<<<<< HEAD
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class NotificationPage extends BasePage {
    private final boolean fromDashboard;
=======
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationPage extends JFrame {
>>>>>>> ec7d8c4 (This is the Login-in pages done by Oyama)

    private final boolean fromDashboard;

    public NotificationPage() {
        this(false);
    }
<<<<<<< HEAD
=======

    public NotificationPage(boolean fromDashboard) {
        this.fromDashboard = fromDashboard;
>>>>>>> ec7d8c4 (This is the Login-in pages done by Oyama)

    public NotificationPage(boolean fromDashboard) {
        super("Notifications", true);
        this.fromDashboard = fromDashboard;
        setBack(this::back);

<<<<<<< HEAD
        put(Theme.label("Notifications", Theme.TITLE.deriveFont(20f), Theme.BLUE, SwingConstants.CENTER));
        gap(10);
=======
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
>>>>>>> ec7d8c4 (This is the Login-in pages done by Oyama)

        String user = Session.username();
        List<NotificationStore.Note> notes = user == null
                ? new ArrayList<NotificationStore.Note>() : NotificationStore.forUser(user);

<<<<<<< HEAD
        if (user == null || notes.isEmpty()) {
            put(Theme.label(user == null ? "Please log in to see your notifications." : "No notifications available.",
                    Theme.BODY, Theme.GREY, SwingConstants.CENTER));
            gap(20);
        }
        for (NotificationStore.Note note : notes) {
            put(card(note));
            gap(10);
        }
        if (user != null && !notes.isEmpty()) {
            JButton markAll = put(new Theme.Btn("Mark all as read", Theme.NAVY));
            markAll.addActionListener(e -> {
                NotificationStore.markAllRead(user);
                go(new NotificationPage(this.fromDashboard));
            });
            gap(8);
        }
        JButton backButton = put(new Theme.Btn("Back", Theme.GREY));
        backButton.addActionListener(e -> back());
=======
        JLabel heading = new JLabel("Notifications");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBounds(115, 70, 200, 30);
        panel.add(heading);

        // =========================
        // LOAD NOTIFICATIONS FROM THE DATABASE (for the logged-in customer)
        // =========================
        String user = Session.username();
        List<NotificationStore.Note> notes = user == null
                ? new ArrayList<NotificationStore.Note>() : NotificationStore.forUser(user);

        int yPosition = 130;

        if (user == null || notes.isEmpty()) {
            JLabel none = new JLabel(user == null
                    ? "Please log in to see your notifications."
                    : "No notifications available.");
            none.setBounds(60, 150, 280, 30);
            panel.add(none);
            yPosition = 200;
        }

        for (NotificationStore.Note note : notes) {

            JPanel notificationPanel = new JPanel();
            notificationPanel.setBounds(40, yPosition, 300, 70);
            notificationPanel.setBackground(Color.WHITE);
            notificationPanel.setBorder(BorderFactory.createLineBorder(
                    note.unread() ? new Color(33, 150, 243) : Color.LIGHT_GRAY,
                    note.unread() ? 2 : 1));
            notificationPanel.setLayout(null);
            notificationPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel notificationTitleLabel = new JLabel(note.title.toUpperCase());
            notificationTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            notificationTitleLabel.setForeground(colourFor(note));
            notificationTitleLabel.setBounds(10, 10, 270, 20);

            String shortText = note.message.length() > 40 ? note.message.substring(0, 40) + "..." : note.message;
            JLabel notificationMessageLabel = new JLabel(shortText);
            notificationMessageLabel.setBounds(10, 35, 280, 20);

            notificationPanel.add(notificationTitleLabel);
            notificationPanel.add(notificationMessageLabel);

            // CLICK NOTIFICATION
            notificationPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    NotificationStore.markRead(note.id);
                    notificationPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    openNotificationDetails(note);
                }
            });

            panel.add(notificationPanel);
            yPosition += 90;   // FIX: the old code stopped at 4 notifications and the Back button overlapped the rest
        }

        // =========================
        // MARK ALL READ + BACK BUTTON (placed under the last notification)
        // =========================
        if (user != null && !notes.isEmpty()) {
            JButton markAll = new JButton("Mark all as read");
            markAll.setBounds(110, yPosition, 160, 32);
            markAll.setFocusPainted(false);
            markAll.addActionListener(e -> {
                NotificationStore.markAllRead(user);
                new NotificationPage(fromDashboard).setVisible(true);
                dispose();
            });
            panel.add(markAll);
            yPosition += 45;
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(140, yPosition, 100, 35);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(backButton);

        panel.setPreferredSize(new Dimension(380, Math.max(750, yPosition + 80)));

        backButton.addActionListener(e -> {
            if (this.fromDashboard) {
                new DashboardFrame();   // opened from the dashboard bell: go back there
            } else {
                Settings_Page page = new Settings_Page();
                page.showGui();
                page.setSize(425, 917);
                page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                page.setLocationRelativeTo(null);
                page.setVisible(true);
            }
            this.dispose();
        });

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
>>>>>>> ec7d8c4 (This is the Login-in pages done by Oyama)

        setVisible(true);
    }

<<<<<<< HEAD
    private void back() {
        if (fromDashboard) AppHooks.toDashboard(this);
        else AppHooks.toSettings(this);
    }

    private JComponent card(NotificationStore.Note note) {
        UiKit.Card card = new UiKit.Card(new BorderLayout(0, 4), colourFor(note));
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(Theme.label(note.title.toUpperCase(), Theme.BOLD, colourFor(note), SwingConstants.LEFT), BorderLayout.CENTER);
        if (note.unread()) {
            JLabel badge = Theme.label(" NEW ", Theme.SMALL.deriveFont(Font.BOLD, 10f), Color.WHITE, SwingConstants.CENTER);
            badge.setOpaque(true);
            badge.setBackground(Theme.BLUE);
            top.add(badge, BorderLayout.EAST);
        }
        card.add(top, BorderLayout.NORTH);
        String shortText = note.message.length() > 90 ? note.message.substring(0, 90) + "..." : note.message;
        card.add(Theme.label(UiKit.html(shortText, 290), Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT), BorderLayout.CENTER);
        card.add(Theme.label(note.category + "   |   " + note.created, Theme.SMALL.deriveFont(11f), Theme.GREY, SwingConstants.LEFT), BorderLayout.SOUTH);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                NotificationStore.markRead(note.id);
                openDetails(note);
                go(new NotificationPage(fromDashboard));   // reload so the NEW badge disappears
            }
        });
        return card;
    }

    // Colours: the original five titles keep their colours, admin-sent ones are coloured by category
    private Color colourFor(NotificationStore.Note n) {
        String t = n.title;
        if (t.equalsIgnoreCase("Drought Alert")) return Color.RED;
        if (t.equalsIgnoreCase("Loadshedding Update")) return new Color(230, 140, 0);
        if (t.equalsIgnoreCase("Usage Warning")) return Color.BLUE;
        if (t.equalsIgnoreCase("Payment Reminder")) return new Color(0, 153, 0);
        if (t.equalsIgnoreCase("System Update")) return Color.BLUE;
        switch (n.category) {
            case "WATER": return new Color(0, 120, 200);
            case "ELECTRICITY": return new Color(230, 140, 0);
            case "ACCOUNT": return new Color(103, 80, 164);
            case "PAYMENT": return new Color(0, 153, 0);
            default: return Color.DARK_GRAY;
        }
    }

    // FIX: the details used to open as a second window; it is now a dialog that keeps the same app size.
    private void openDetails(NotificationStore.Note note) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        p.add(Theme.label(note.title, Theme.TITLE.deriveFont(18f), colourFor(note), SwingConstants.LEFT), BorderLayout.NORTH);
        p.add(Theme.label(UiKit.html(note.message, 300), Theme.BODY, Theme.INK, SwingConstants.LEFT), BorderLayout.CENTER);
        p.add(Theme.label(note.category + "   |   " + note.created + "   |   from " + note.sentBy,
                Theme.SMALL.deriveFont(11f), Theme.GREY, SwingConstants.LEFT), BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, p, "Notification", JOptionPane.PLAIN_MESSAGE);
    }
=======
    // Colours: the original five titles keep their colours, admin-sent ones are coloured by category
    private Color colourFor(NotificationStore.Note n) {
        String t = n.title;
        if (t.equalsIgnoreCase("Drought Alert")) return Color.RED;
        if (t.equalsIgnoreCase("Loadshedding Update")) return Color.ORANGE;
        if (t.equalsIgnoreCase("Usage Warning")) return Color.BLUE;
        if (t.equalsIgnoreCase("Payment Reminder")) return new Color(0, 153, 0);
        if (t.equalsIgnoreCase("System Update")) return Color.BLUE;
        switch (n.category) {
            case "WATER": return new Color(0, 120, 200);
            case "ELECTRICITY": return new Color(230, 140, 0);
            case "ACCOUNT": return new Color(103, 80, 164);
            case "PAYMENT": return new Color(0, 153, 0);
            default: return Color.DARK_GRAY;
        }
    }

    // ==================================================
    // NOTIFICATION DETAILS
    // ==================================================
    // FIX: this was a second JFrame; it is now a dialog so it keeps the same app size and closes cleanly.
    private void openNotificationDetails(NotificationStore.Note note) {

        JDialog detailsFrame = new JDialog(this, "Notification Details", true);
        detailsFrame.setSize(380, 400);
        detailsFrame.setLocationRelativeTo(this);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        detailsPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel(note.title);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBounds(30, 25, 310, 40);
        detailsPanel.add(heading);

        JLabel info = new JLabel(note.category + "   |   " + note.created + "   |   from " + note.sentBy);
        info.setFont(new Font("Arial", Font.PLAIN, 12));
        info.setForeground(Color.GRAY);
        info.setBounds(30, 65, 320, 20);
        detailsPanel.add(info);

        JTextArea messageArea = new JTextArea(note.message);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setBackground(Color.WHITE);

        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBounds(30, 95, 310, 180);
        detailsPanel.add(messageScroll);

        JButton detailsBackButton = new JButton("Back");
        detailsBackButton.setBounds(140, 300, 100, 35);
        detailsBackButton.setBackground(Color.BLACK);
        detailsBackButton.setForeground(Color.WHITE);
        detailsBackButton.setFocusPainted(false);
        detailsPanel.add(detailsBackButton);

        detailsBackButton.addActionListener(e -> detailsFrame.dispose());

        detailsFrame.add(detailsPanel);
        detailsFrame.setVisible(true);
    }
>>>>>>> ec7d8c4 (This is the Login-in pages done by Oyama)
}