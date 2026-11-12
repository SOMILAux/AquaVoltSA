package za.ac.cput.aquavoltsa.GUI;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.text.JTextComponent;
import za.ac.cput.aquavoltsa.DAO.Database;
 
/**
 * @Student-Number 241028213
 * @author Oyama Sibunzi
 */ 
public class LoginSignupPage extends BasePage {
    private final Theme.HintField usernameField = new Theme.HintField("Enter Username");
    private final Theme.HintPassword passwordField = new Theme.HintPassword("Password");
    private final JLabel userErr = Theme.err(), passErr = Theme.err();

    public LoginSignupPage() {
        super("Login/Sign-up Page");

        gap(6);
        put(Theme.label("Login into your Account", Theme.BODY, Theme.GREY, SwingConstants.CENTER));
        gap(12);
        put(usernameField);
        put(userErr);
        gap(8);
        put(passwordField);
        put(passErr);
        gap(14);

        JButton enterBtn = put(new Theme.Btn("Enter", Theme.BLUE));
        gap(2);
        // NEW: "Forgot Login" opens the ForgotLoginPage (ID number -> new username and password)
        JButton forgotBtn = put(Theme.link("Forgot Login"));
        forgotBtn.setFont(Theme.SMALL.deriveFont(12f));
        forgotBtn.setForeground(Theme.GREY);
        forgotBtn.addActionListener(e -> go(new ForgotLoginPage()));
        gap(6);
        JLabel or = new JLabel(Theme.icon("or1.png", 220, 25), SwingConstants.CENTER);
        put(or);
        JButton signupBtn = put(Theme.link("Sign Up"));
        JButton roleBtn = put(Theme.link("Change user type"));
        roleBtn.setFont(Theme.SMALL.deriveFont(12f));
        roleBtn.setForeground(Theme.GREY);
        roleBtn.addActionListener(e -> go(new RoleSelectPage()));

        getRootPane().setDefaultButton(enterBtn);
        
        

        enterBtn.addActionListener(e -> login());

        signupBtn.addActionListener(e -> {
            UserStore.draft = new UserStore.User();
            go(new SignupPage2());
        });
    }

    private void login() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        Theme.ok(userErr, usernameField);
        Theme.ok(passErr, passwordField);
        boolean bad = false;
        if (u.isEmpty()) bad = Theme.bad(userErr, usernameField, "Enter your username");
        if (p.isEmpty()) bad = Theme.bad(passErr, passwordField, "Enter your password");
        if (bad) return;

        UserStore.User user = UserStore.find(u);
        if (user == null || !user.passHash.equals(UserStore.hash(p))) {
            Theme.bad(passErr, passwordField, "Incorrect username or password");
            return;
        }
        if (!UserStore.VERIFIED.equals(user.status)) {
            JOptionPane.showMessageDialog(this,
                    "Your account is still waiting for admin approval.\nYou can log in once it has been verified.",
                    "Account pending", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        go(new VerificationPage(user));
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        AppHooks.install();   // constant 425 x 917 size + links between the group's pages
        SwingUtilities.invokeLater(() -> new SplashPage().setVisible(true));
    }
}

/**
 * Loading screen shown when the app opens (like the Android app), then moves to the login page.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
class SplashPage extends JFrame {
    private int progress = 0;

    public SplashPage() {
        setTitle("AquaVolt SA");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Theme.image("plain_logo.png"));

        JLabel logo = new JLabel(Theme.icon("plain_logo.png", 200, 208));
        logo.setAlignmentX(0.5f);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setForeground(Theme.BLUE);
        bar.setBackground(new Color(225, 225, 225));
        bar.setBorderPainted(false);
        bar.setPreferredSize(new Dimension(260, 8));
        bar.setMaximumSize(new Dimension(260, 8));
        bar.setAlignmentX(0.5f);

        JLabel status = Theme.label("Starting AquaVolt SA...", Theme.BODY, Theme.GREY, SwingConstants.CENTER);
        status.setAlignmentX(0.5f);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setOpaque(false);
        centre.add(logo);
        centre.add(Box.createVerticalStrut(24));
        centre.add(bar);
        centre.add(Box.createVerticalStrut(12));
        centre.add(status);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Color.WHITE);
        root.add(centre);
        JPanel all = new JPanel(new BorderLayout());
        all.add(root, BorderLayout.CENTER);
        all.add(Theme.footer(), BorderLayout.SOUTH);
        all.setPreferredSize(new Dimension(425, 917));   // GUI size
        setContentPane(all);
        pack();
        setLocationRelativeTo(null);

        String[] steps = {"Starting AquaVolt SA...", "Connecting to the database...",
            "Checking tables...", "Almost ready..."};
        javax.swing.Timer t = new javax.swing.Timer(30, null);
        t.addActionListener(e -> {
            progress++;
            bar.setValue(progress);
            status.setText(steps[Math.min(steps.length - 1, progress / 26)]);
            if (progress == 30 && !Database.init()) {   // creates the tables on the first run
                t.stop();
                System.exit(0);
            }
            if (progress >= 100) {
                t.stop();
                new RoleSelectPage().setVisible(true);
                dispose();
            }
        });
        t.start();
    }
}

/**
 * Landing page after OTP verification: profile summary plus animated usage gauges (demo values).
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
class HomePage extends BasePage {

    public HomePage(UserStore.User u) {
        super("Home");
        final UserStore.User user = u == null ? new UserStore.User() : u;
        String first = user.fullName.isEmpty() ? "Guest" : user.fullName.split("\\s+")[0];

        put(Theme.label("Welcome, " + first + "!", Theme.TITLE, Theme.BLUE, SwingConstants.CENTER));
        put(Theme.label("Your account is verified", Theme.BODY, new Color(46, 125, 50), SwingConstants.CENTER));
        gap(10);

        JPanel gauges = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        gauges.setOpaque(false);
        gauges.add(new Gauge("Water used", 62, Theme.BLUE));
        gauges.add(new Gauge("Electricity used", 48, new Color(245, 166, 0)));
        put(gauges);
        put(Theme.label("Demo usage figures", Theme.SMALL, Theme.GREY, SwingConstants.CENTER));
        gap(10);

        put(Theme.label("Your information", Theme.BOLD, Theme.INK, SwingConstants.LEFT));
        gap(4);
        info("Full name", user.fullName);
        info("Username", user.username);
        info("Email", user.email);
        info("Phone", user.phone);
        info("Address", user.address);
        info("Electricity meter", user.meter);
        info("Water meter", user.waterMeter);
        gap(14);

        JButton logout = put(new Theme.Btn("Log out", Theme.GREY));
        logout.addActionListener(e -> go(new LoginSignupPage()));
    }

    private void info(String key, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 235, 235)));
        p.add(Theme.label(key, Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT), BorderLayout.WEST);
        p.add(Theme.label(value == null || value.isEmpty() ? "-" : value, Theme.BODY, Theme.INK, SwingConstants.RIGHT), BorderLayout.EAST);
        p.setPreferredSize(new Dimension(300, 30));
        put(p);
    }
    /** Animated circular gauge. */
    private static class Gauge extends JComponent {
        private final int target;
        private final String caption;
        private final Color color;
        private int shown = 0;

        Gauge(String caption, int target, Color color) {
            this.caption = caption;
            this.target = target;
            this.color = color;
            setPreferredSize(new Dimension(130, 150));
            javax.swing.Timer t = new javax.swing.Timer(15, null);
            t.addActionListener(e -> {
                if (shown >= this.target) t.stop();
                else { shown++; repaint(); }
            });
            t.start();
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int d = 100, x = (getWidth() - d) / 2, y = 10;
            g2.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(232, 232, 232));
            g2.drawOval(x, y, d, d);
            g2.setColor(color);
            g2.drawArc(x, y, d, d, 90, -(int) (3.6 * shown));
            g2.setColor(Theme.INK);
            g2.setFont(Theme.TITLE.deriveFont(20f));
            String s = shown + "%";
            g2.drawString(s, x + (d - g2.getFontMetrics().stringWidth(s)) / 2, y + d / 2 + 7);
            g2.setFont(Theme.SMALL.deriveFont(12f));
            g2.setColor(Theme.GREY);
            g2.drawString(caption, (getWidth() - g2.getFontMetrics().stringWidth(caption)) / 2, y + d + 28);
            g2.dispose();
        }
    }
}

/**
 * Every page shares the Android layout: logo header on top, a scrolling body, "Partnered with" footer.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
abstract class BasePage extends JFrame {
    protected final JPanel body = new Body();

    protected BasePage(String title) {
        this(title, false);
    }

    /** compact = smaller logo and no "Partnered with" footer (used by the dashboard and the settings pages). */
    protected BasePage(String title, boolean compact) {
        super("AquaVolt SA - " + title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Theme.image("plain_logo.png"));

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(6, 28, 12, 28));

        JScrollPane scroll = new JScrollPane(body, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.add(compact ? Theme.compactHeader() : Theme.header(), BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        if (!compact) root.add(Theme.footer(), BorderLayout.SOUTH);
        root.setPreferredSize(new Dimension(425, 917));   // GUI size
        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    /** Adds the round back arrow (back.png) in the top-left corner. */
    protected void setBack(Runnable action) {
        Theme.BackButton b = new Theme.BackButton();
        b.setBounds(10, 10, 44, 44);
        b.addActionListener(e -> action.run());
        getLayeredPane().add(b, JLayeredPane.PALETTE_LAYER);
    }

    /** Adds a component to the scrolling body, centred and stretched to the full width. */
    protected <T extends JComponent> T put(T c) {
        Theme.fit(c);
        body.add(c);
        return c;
    }

    protected void gap(int h) {
        body.add(Box.createVerticalStrut(h));
    }

    protected void go(JFrame next) {
        next.setVisible(true);
        dispose();
    }

    private static class Body extends JPanel implements Scrollable {
        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 18; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 120; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() {
            return getParent() instanceof JViewport && getParent().getHeight() > getPreferredSize().height;
        }
    }
}

/**
 * Shared colours, fonts and small UI helpers so every page matches the Android app.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
final class Theme {
    public static final Color BLUE = new Color(33, 150, 243), GREY = new Color(117, 117, 117),
            RED = new Color(211, 47, 47), PURPLE = new Color(103, 80, 164), BORDER = new Color(176, 176, 176),
            INK = new Color(33, 33, 33), YELLOW = new Color(255, 193, 7),
            NAVY = new Color(13, 71, 161);
    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 22), BODY = new Font("Segoe UI", Font.PLAIN, 14),
            BOLD = new Font("Segoe UI", Font.BOLD, 14), SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    private Theme() { }

    // ---------- images (same simple style: ImageIcon -> getImage -> getScaledInstance) ----------
    static ImageIcon icon(String file, int width, int height) {
        ImageIcon logo = new ImageIcon(file);
        Image logoIcon = logo.getImage();
        Image resizedLogo = logoIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedLogo);
    }

    static Image image(String file) {
        return new ImageIcon(file).getImage();
    }

    // ---------- header / footer used on every page ----------
    static JComponent header() {
        JLabel l = new JLabel(icon("Logo1.png", 425, 246), SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(Color.WHITE);
        return l;
    }

    static JComponent compactHeader() {
        JLabel l = new JLabel(icon("Logo1.png", 260, 151), SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(Color.WHITE);
        return l;
    }

    /** Round back arrow: uses /icons/back.png when it exists, otherwise draws a chevron. */
    static class BackButton extends JButton {
        private final Image img;

        BackButton() {
            java.net.URL u = Theme.class.getResource("/icons/back.png");
            img = u == null ? null : new ImageIcon(u).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(44, 44));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(245, 248, 252));
            g2.fillOval(2, 2, getWidth() - 5, getHeight() - 5);
            g2.setColor(new Color(215, 222, 232));
            g2.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
            if (img != null) {
                g2.drawImage(img, (getWidth() - 24) / 2, (getHeight() - 24) / 2, null);
            } else {
                g2.setColor(NAVY);
                g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                g2.drawLine(cx + 4, cy - 9, cx - 5, cy);
                g2.drawLine(cx - 5, cy, cx + 4, cy + 9);
            }
            g2.dispose();
        }
    }

    static JComponent footer() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        JLabel t = label("Partnered with:", BOLD, INK, SwingConstants.CENTER);
        t.setAlignmentX(0.5f);
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        row.setOpaque(false);
        row.add(new JLabel(icon("national_logo1.png", 32, 40)));
        row.add(label("SA Municipality", SMALL.deriveFont(12f), INK, SwingConstants.LEFT));
        row.add(new JLabel(icon("eskom1.png", 105, 60)));
        JPanel strip = new JPanel() {
            private final Image bg = image("bg1.png");
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, getHeight() - 261, 425, 261, this);   // bottom of the background picture
            }
        };
        strip.setBackground(Color.WHITE);
        strip.setPreferredSize(new Dimension(425, 38));
        p.add(Box.createVerticalStrut(6));
        p.add(t);
        p.add(row);
        p.add(strip);
        return p;
    }

    // ---------- small component factories ----------
    static JLabel label(String text, Font f, Color c, int align) {
        JLabel l = new JLabel(text, align);
        l.setFont(f);
        l.setForeground(c);
        return l;
    }

    static JLabel err() {
        JLabel l = label(" ", SMALL, RED, SwingConstants.LEFT);
        l.setVisible(false);
        return l;
    }

    static JButton link(String text) {
        JButton b = new JButton(text);
        b.setFont(BOLD);
        b.setForeground(BLUE);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(120, 34));
        return b;
    }

    static JCheckBox check(String text) {
        JCheckBox c = new JCheckBox(text);
        c.setFont(BODY);
        c.setOpaque(false);
        c.setFocusPainted(false);
        return c;
    }

    static JPanel row(JComponent... items) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        p.setOpaque(false);
        for (JComponent c : items) p.add(c);
        return p;
    }

    static <T extends JComponent> T fit(T c) {
        c.setAlignmentX(0.5f);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        return c;
    }

    static boolean bad(JLabel l, JComponent f, String msg) {
        l.setText(msg);
        l.setVisible(true);
        flag(f, true);
        return true;
    }

    static void ok(JLabel l, JComponent f) {
        l.setVisible(false);
        flag(f, false);
    }

    private static void flag(JComponent f, boolean e) {
        if (f instanceof HintField) ((HintField) f).error = e;
        if (f instanceof HintPassword) ((HintPassword) f).error = e;
        f.repaint();
    }

    /** Android-style popup message (also used for the SMS notification banner). */
    static void toast(JFrame f, String msg, int y, int ms) {
        JLabel l = new JLabel(msg, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50, 50, 50, 235));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setOpaque(false);
        l.setForeground(Color.WHITE);
        l.setFont(SMALL.deriveFont(12f));
        JLayeredPane lp = f.getLayeredPane();
        int w = Math.min(lp.getWidth() - 20, l.getPreferredSize().width + 40);
        l.setBounds((lp.getWidth() - w) / 2, y, w, 34);
        lp.add(l, JLayeredPane.POPUP_LAYER);
        lp.repaint();
        javax.swing.Timer t = new javax.swing.Timer(ms, e -> { lp.remove(l); lp.repaint(); });
        t.setRepeats(false);
        t.start();
    }

    // ---------- rounded buttons / fields ----------
    public static class Btn extends JButton {
        private final Color base;
        private boolean hover;

        Btn(String text, Color base) {
            super(text);
            this.base = base;
            setFont(BOLD);
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setPreferredSize(new Dimension(160, 44));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(!isEnabled() ? Color.LIGHT_GRAY : getModel().isPressed() ? base.darker().darker() : hover ? base.darker() : base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static void box(Graphics g, JComponent c, boolean error) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 12, 12);
        boolean focus = c.hasFocus();
        g2.setColor(error ? RED : focus ? PURPLE : BORDER);
        g2.setStroke(new BasicStroke(focus || error ? 2f : 1f));
        g2.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, 12, 12);
        g2.dispose();
    }

    private static void hint(Graphics g, JTextComponent c, String hint) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(new Color(150, 150, 150));
        g2.setFont(c.getFont());
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(hint, c.getInsets().left, (c.getHeight() + fm.getAscent() - fm.getDescent()) / 2);
        g2.dispose();
    }

    public static class HintField extends JTextField {
        private final String hint;
        public boolean error;

        HintField(String hint) {
            this.hint = hint;
            setFont(BODY);
            setForeground(INK);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
            setPreferredSize(new Dimension(300, 44));
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e) { repaint(); }
            });
            addKeyListener(new KeyAdapter() {
                @Override public void keyTyped(KeyEvent e) { if (error) { error = false; repaint(); } }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            box(g, this, error);
            super.paintComponent(g);
            if (getText().isEmpty()) hint(g, this, hint);
        }
    }

    /** Password field with the Android-style eye icon inside it. */
    public static class HintPassword extends JPasswordField {
        private final String hint;
        public boolean error;
        private boolean shown;
        private final JButton eye = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GREY);
                g2.setStroke(new BasicStroke(1.8f));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                g2.drawOval(cx - 10, cy - 6, 20, 12);
                g2.fillOval(cx - 3, cy - 3, 6, 6);
                if (!shown) g2.drawLine(cx - 9, cy + 8, cx + 9, cy - 8);
                g2.dispose();
            }
        };

        HintPassword(String hint) {
            this.hint = hint;
            setFont(BODY);
            setForeground(INK);
            setOpaque(false);
            setEchoChar('\u2022');
            setLayout(null);
            setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 44));
            setPreferredSize(new Dimension(300, 44));
            eye.setContentAreaFilled(false);
            eye.setBorderPainted(false);
            eye.setFocusable(false);
            eye.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            eye.addActionListener(e -> reveal(!shown));
            add(eye);
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e) { repaint(); }
            });
            addKeyListener(new KeyAdapter() {
                @Override public void keyTyped(KeyEvent e) { if (error) { error = false; repaint(); } }
            });
        }

        public void reveal(boolean on) {
            shown = on;
            setEchoChar(on ? (char) 0 : '\u2022');
            eye.repaint();
            repaint();
        }

        @Override public void doLayout() {
            eye.setBounds(getWidth() - 40, 0, 36, getHeight());
        }

        @Override protected void paintComponent(Graphics g) {
            box(g, this, error);
            super.paintComponent(g);
            if (getPassword().length == 0) hint(g, this, hint);
        }
    }
}

/**
 * Customer data access through JDBC. Everything is stored in the Java DB table CUSTOMERS.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
final class UserStore {
    public static final String PENDING = "PENDING", VERIFIED = "VERIFIED";

    /** Holds the sign-up details while the user moves between the sign-up pages. */
    public static User draft = new User();

    public static class User {
        public String id = "", fullName = "", idNumber = "", username = "", passHash = "", email = "", phone = "",
                address = "", meter = "", serial = "", waterMeter = "", accountNo = "", status = PENDING, created = "";
        /** Plain password, only kept in memory during sign-up (never saved). */
        public String rawPassword = "";
    }

    private static final String COLS = "id, full_name, id_number, username, pass_hash, email, phone, address, "
            + "meter, serial, water_meter, account_no, status, created";

    private UserStore() { }

    private static String s(String v) {
        return v == null ? "" : v;
    }

    private static String cut(String v, int max) {
        v = s(v);
        return v.length() > max ? v.substring(0, max) : v;
    }

    private static User row(ResultSet r) throws SQLException {
        User u = new User();
        u.id = s(r.getString("id"));
        u.fullName = s(r.getString("full_name"));
        u.idNumber = s(r.getString("id_number"));
        u.username = s(r.getString("username"));
        u.passHash = s(r.getString("pass_hash"));
        u.email = s(r.getString("email"));
        u.phone = s(r.getString("phone"));
        u.address = s(r.getString("address"));
        u.meter = s(r.getString("meter"));
        u.serial = s(r.getString("serial"));
        u.waterMeter = s(r.getString("water_meter"));
        u.accountNo = s(r.getString("account_no"));
        u.status = s(r.getString("status"));
        Timestamp t = r.getTimestamp("created");
        u.created = t == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t);
        return u;
    }

    public static List<User> all() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT " + COLS + " FROM customers ORDER BY created DESC";
        try (Connection c = Database.connect(); Statement st = c.createStatement(); ResultSet r = st.executeQuery(sql)) {
            while (r.next()) list.add(row(r));
        } catch (SQLException e) {
            Database.report(e);
        }
        return list;
    }

    public static User find(String username) {
        String sql = "SELECT " + COLS + " FROM customers WHERE LOWER(username) = LOWER(?)";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return row(r);
            }
        } catch (SQLException e) {
            Database.report(e);
        }
        return null;
    }

    public static boolean exists(String username) {
        return find(username) != null;
    }

    /** New customers always start as PENDING until an admin accepts them. */
    public static void add(User u) {
        u.id = "U" + UUID.randomUUID().toString().substring(0, 8);
        String sql = "INSERT INTO customers (id, full_name, id_number, username, pass_hash, email, phone, address, "
                + "meter, serial, water_meter, account_no, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            String[] v = {u.id, cut(u.fullName, 100), cut(u.idNumber, 20), cut(u.username, 30), u.passHash,
                cut(u.email, 100), cut(u.phone, 20), cut(u.address, 200), cut(u.meter, 30), cut(u.serial, 30),
                cut(u.waterMeter, 30), cut(u.accountNo, 30), u.status};
            for (int i = 0; i < v.length; i++) p.setString(i + 1, v[i]);
            p.executeUpdate();
        } catch (SQLException e) {
            Database.report(e);
        }
    }

    /** NEW - Forgot Login: the customer with this ID number (the first one if there are several), or null. */
    public static User findByIdNumber(String idNumber) {
        String sql = "SELECT " + COLS + " FROM customers WHERE id_number = ? ORDER BY created";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, idNumber);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return row(r);
            }
        } catch (SQLException e) {
            Database.report(e);
        }
        return null;
    }

    /**
     * NEW - Forgot Login: gives one customer a new username and password.
     * The username is also what links the customer to their notifications and payments, so those rows are moved to
     * the new username in the same go (all or nothing).
     * Returns null when it worked, otherwise a message to show the customer.
     */
    public static String updateLogin(User account, String newUsername, String newPassHash) {
        String newName = cut(newUsername, 30);
        try (Connection c = Database.connect()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement("UPDATE customers SET username = ?, pass_hash = ? WHERE id = ?")) {
                    p.setString(1, newName);
                    p.setString(2, newPassHash);
                    p.setString(3, account.id);
                    if (p.executeUpdate() == 0) {
                        c.rollback();
                        return "That account could not be found any more.";
                    }
                }
                if (!newName.equals(account.username)) {
                    for (String table : new String[]{"notifications", "transactions"}) {
                        try (PreparedStatement p = c.prepareStatement("UPDATE " + table
                                + " SET customer_username = ? WHERE LOWER(customer_username) = LOWER(?)")) {
                            p.setString(1, newName);
                            p.setString(2, account.username);
                            p.executeUpdate();
                        }
                    }
                }
                c.commit();
                return null;
            } catch (SQLException e) {
                c.rollback();
                if ("23505".equals(e.getSQLState())) {   // 23505 = the username is already used
                    return "That username was just taken. Please choose another one.";
                }
                throw e;
            }
        } catch (SQLException e) {
            Database.report(e);
            return "Could not save the new login details. Please try again.";
        }
    }

    public static boolean setStatus(String id, String status) {
        try (Connection c = Database.connect();
             PreparedStatement p = c.prepareStatement("UPDATE customers SET status = ? WHERE id = ?")) {
            p.setString(1, status);
            p.setString(2, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            Database.report(e);
            return false;
        }
    }

    public static boolean delete(String id) {
        try (Connection c = Database.connect();
             PreparedStatement p = c.prepareStatement("DELETE FROM customers WHERE id = ?")) {
            p.setString(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            Database.report(e);
            return false;
        }
    }

    public static String hash(String s) {
        try {
            byte[] d = MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return s;
        }
    }
}