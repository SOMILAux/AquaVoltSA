package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class SignupPage3 extends BasePage {
    private final Theme.HintField usernameField = new Theme.HintField("Enter Username");
    private final Theme.HintField homeAddress = new Theme.HintField("Enter Home Address");
    private final Theme.HintField meterNumber = new Theme.HintField("Enter Meter Number");
    private final Theme.HintField serialNumber = new Theme.HintField("Enter Serial Number");
    private final Theme.HintField waterMeter = new Theme.HintField("Enter Water Meter Number");
    private final Theme.HintField accountNo = new Theme.HintField("Enter Municipal Account Number");
    private final JLabel userErr = Theme.err(), agreeErr = Theme.err();
    private final JCheckBox agree = Theme.check("I read and agree to these terms and conditions");

    public SignupPage3() {
        super("Sign-up Page Part 3");

        UserStore.User d = UserStore.draft;   // restore after visiting the Terms page
        usernameField.setText(d.username);
        homeAddress.setText(d.address);
        meterNumber.setText(d.meter);
        serialNumber.setText(d.serial);
        waterMeter.setText(d.waterMeter);
        accountNo.setText(d.accountNo);

        put(Theme.label("Final Step", Theme.TITLE.deriveFont(20f), Color.BLACK, SwingConstants.CENTER));
        gap(8);
        put(Theme.label("Set your Unique Username:", Theme.BOLD, Theme.INK, SwingConstants.LEFT));
        gap(4);
        put(usernameField);
        put(userErr);
        put(Theme.label("*You can skip this process and update it later on", Theme.SMALL.deriveFont(Font.ITALIC),
                Theme.RED, SwingConstants.LEFT));
        gap(8);

        put(new Collapsible("Electricity Details", bolt(),
                caption("1. Enter your Home Address"), homeAddress,
                caption("2. Enter meter Number"), meterNumber,
                caption("3. Enter meter Serial Number"), serialNumber));
        gap(8);
        put(new Collapsible("Water Details", drop(),
                caption("1. Enter your Water Meter Number"), waterMeter,
                caption("2. Enter your Municipal Account Number"), accountNo));
        gap(8);

        put(Theme.label("Please read and comply with the terms and conditions", Theme.SMALL, Theme.RED,
                SwingConstants.CENTER));
        gap(6);
        JButton termsBtn = put(new Theme.Btn("Terms and Conditions", Theme.BLUE));
        gap(4);
        put(agree);
        put(agreeErr);
        gap(8);

        JButton enterBtn = new Theme.Btn("Enter", Theme.BLUE);
        JButton backBtn = new Theme.Btn("Back", Theme.GREY);
        enterBtn.setPreferredSize(new Dimension(130, 44));
        backBtn.setPreferredSize(new Dimension(130, 44));
        put(Theme.row(enterBtn, backBtn));
        gap(10);

        termsBtn.addActionListener(e -> {
            stash();
            go(new TermsPage());
        });

        backBtn.addActionListener(e -> {
            stash();
            go(new SignupPage2());
        });

        enterBtn.addActionListener(e -> {
            String u = usernameField.getText().trim();
            boolean bad = false;
            Theme.ok(userErr, usernameField);
            agreeErr.setVisible(false);
            if (!u.matches("[A-Za-z0-9._-]{3,20}")) {
                bad = Theme.bad(userErr, usernameField, "3-20 letters, numbers, . _ or - (no spaces)");
            } else if (UserStore.exists(u)) {
                bad = Theme.bad(userErr, usernameField, "That username is already taken");
            }
            if (!agree.isSelected()) {
                agreeErr.setText("You must accept the terms and conditions");
                agreeErr.setVisible(true);
                bad = true;
            }
            if (bad) return;

            stash();
            UserStore.User nu = UserStore.draft;
            nu.passHash = UserStore.hash(nu.rawPassword);
            UserStore.add(nu);   // saved as PENDING until an admin accepts it on the website
            UserStore.draft = new UserStore.User();
            JOptionPane.showMessageDialog(this,
                    "Account submitted!\nAn AquaVolt SA administrator will verify your details.\n"
                    + "You can log in once your account has been accepted.",
                    "Almost there", JOptionPane.INFORMATION_MESSAGE);
            go(new LoginSignupPage());
        });
    }

    private void stash() {
        UserStore.User d = UserStore.draft;
        d.username = usernameField.getText().trim();
        d.address = homeAddress.getText().trim();
        d.meter = meterNumber.getText().trim();
        d.serial = serialNumber.getText().trim();
        d.waterMeter = waterMeter.getText().trim();
        d.accountNo = accountNo.getText().trim();
    }

    private static JLabel caption(String text) {
        return Theme.label(text, Theme.BODY.deriveFont(13f), Theme.INK, SwingConstants.LEFT);
    }

    private static Icon bolt() {
        return new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.YELLOW);
                g2.fillPolygon(new int[]{x + 9, x + 3, x + 8, x + 6, x + 13, x + 8},
                        new int[]{y, y + 9, y + 9, y + 16, y + 6, y + 6}, 6);
                g2.dispose();
            }
            @Override public int getIconWidth() { return 18; }
            @Override public int getIconHeight() { return 16; }
        };
    }

    private static Icon drop() {
        return new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.BLUE);
                g2.fillOval(x + 2, y + 6, 12, 10);
                g2.fillPolygon(new int[]{x + 8, x + 3, x + 13}, new int[]{y, y + 9, y + 9}, 3);
                g2.dispose();
            }
            @Override public int getIconWidth() { return 18; }
            @Override public int getIconHeight() { return 16; }
        };
    }

    /** Android-style expandable section (tap the header to open/close). */
    private static class Collapsible extends JPanel {
        private final JPanel content = new JPanel();
        private boolean open = false;

        Collapsible(String title, Icon icon, JComponent... rows) {
            setLayout(new BorderLayout());
            setOpaque(false);
            JButton head = new JButton(title, icon) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(246, 246, 246));
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                    g2.setColor(Theme.BORDER);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                    g2.setColor(Theme.BLUE);
                    int cx = getWidth() - 24, cy = getHeight() / 2;
                    if (open) g2.fillPolygon(new int[]{cx - 6, cx + 6, cx}, new int[]{cy + 3, cy + 3, cy - 4}, 3);
                    else g2.fillPolygon(new int[]{cx - 6, cx + 6, cx}, new int[]{cy - 3, cy - 3, cy + 4}, 3);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            head.setFont(Theme.BOLD);
            head.setForeground(Theme.INK);
            head.setHorizontalAlignment(SwingConstants.LEFT);
            head.setIconTextGap(10);
            head.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
            head.setContentAreaFilled(false);
            head.setFocusPainted(false);
            head.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            head.setPreferredSize(new Dimension(300, 44));

            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setOpaque(false);
            content.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
            for (JComponent r : rows) {
                Theme.fit(r);
                content.add(r);
                content.add(Box.createVerticalStrut(r instanceof Theme.HintField ? 8 : 2));
            }
            content.setVisible(false);

            head.addActionListener(e -> {
                open = !open;
                content.setVisible(open);
                head.repaint();
                revalidate();
                if (getParent() != null) getParent().revalidate();
            });
            add(head, BorderLayout.NORTH);
            add(content, BorderLayout.CENTER);
        }

        @Override public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }
    }
}
