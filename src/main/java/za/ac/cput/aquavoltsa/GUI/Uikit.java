package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * Small shared building blocks so every page has the same clean look (cards, round avatars, captions).
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
final class UiKit {
    private UiKit() { }

    /** White rounded card, optionally with a coloured stripe on the left. */
    static class Card extends JPanel {
        private final Color accent;

        Card(LayoutManager lm, Color accent) {
            super(lm);
            this.accent = accent;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(12, accent == null ? 14 : 18, 12, 14));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(247, 249, 252));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.setColor(new Color(226, 231, 239));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            if (accent != null) {
                g2.setColor(accent);
                g2.fillRoundRect(0, 14, 5, getHeight() - 28, 4, 4);
            }
            g2.dispose();
            super.paintComponent(g);
        }

        @Override public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }
    }

    /** Round picture of a person (their profile picture, or their initials when they have none). */
    static class Avatar extends JComponent {
        private final String initials, kind, username;
        private final int size;
        private ImageIcon pic;

        Avatar(String name, String kind, String username, int size) {
            StringBuilder sb = new StringBuilder();
            for (String w : name.trim().split("\\s+")) if (!w.isEmpty() && sb.length() < 2) sb.append(Character.toUpperCase(w.charAt(0)));
            this.initials = sb.length() == 0 ? "?" : sb.toString();
            this.kind = kind;
            this.username = username;
            this.size = size;
            setPreferredSize(new Dimension(size + 2, size + 2));
            reload();
        }

        void reload() {
            pic = ProfilePics.circle(kind, username, size);
            repaint();
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if (pic != null) {
                g2.drawImage(pic.getImage(), 0, 0, null);
            } else {
                g2.setColor(Theme.NAVY);
                g2.fillOval(0, 0, size, size);
                g2.setColor(Color.WHITE);
                g2.setFont(Theme.BOLD.deriveFont(size * 0.38f));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(initials, (size - fm.stringWidth(initials)) / 2, size / 2 + fm.getAscent() / 2 - 2);
            }
            g2.setColor(new Color(0, 0, 0, 40));
            g2.drawOval(0, 0, size - 1, size - 1);
            g2.dispose();
        }
    }

    static JComboBox<String> combo(String... items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(Theme.BODY);
        c.setBackground(Color.WHITE);
        c.setPreferredSize(new Dimension(300, 40));
        return c;
    }

    static JLabel section(String text) {
        return Theme.label(text, Theme.BOLD.deriveFont(15f), Theme.NAVY, SwingConstants.LEFT);
    }

    static JLabel caption(String text) {
        return Theme.label(text, Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT);
    }

    /** Wrapping text for a JLabel. */
    static String html(String text, int width) {
        return "<html><div style='width:" + width + "px'>" + text + "</div></html>";
    }
}