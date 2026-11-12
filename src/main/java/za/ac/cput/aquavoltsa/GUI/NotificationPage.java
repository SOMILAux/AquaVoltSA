//Final update done by Sbahle
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class NotificationPage extends BasePage {
    private final boolean fromDashboard;

    public NotificationPage() {
        this(false);
    }

    public NotificationPage(boolean fromDashboard) {
        super("Notifications");
        this.fromDashboard = fromDashboard;
        setBack(this::back);

        put(Theme.label("Notifications", Theme.TITLE.deriveFont(20f), Theme.BLUE, SwingConstants.CENTER));
        gap(10);

        String user = Session.username();
        List<NotificationStore.Note> notes = user == null
                ? new ArrayList<NotificationStore.Note>() : NotificationStore.forUser(user);

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

        setVisible(true);
    }

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
                go(new NotificationPage(fromDashboard)); 
            }
        });
        return card;
    }

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

    private void openDetails(NotificationStore.Note note) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        p.add(Theme.label(note.title, Theme.TITLE.deriveFont(18f), colourFor(note), SwingConstants.LEFT), BorderLayout.NORTH);
        p.add(Theme.label(UiKit.html(note.message, 300), Theme.BODY, Theme.INK, SwingConstants.LEFT), BorderLayout.CENTER);
        p.add(Theme.label(note.category + "   |   " + note.created + "   |   from " + note.sentBy,
                Theme.SMALL.deriveFont(11f), Theme.GREY, SwingConstants.LEFT), BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, p, "Notification", JOptionPane.PLAIN_MESSAGE);
    }
}