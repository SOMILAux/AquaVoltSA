package za.ac.cput.aquavoltsa.GUI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.text.JTextComponent;

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
        gap(10);
        JLabel or = new JLabel(Theme.icon(Theme.load("or1.png"), 220, 0), SwingConstants.CENTER);
        if (or.getIcon() == null) { or.setText("Or"); or.setFont(Theme.BODY); }
        put(or);
        JButton signupBtn = put(Theme.link("Sign Up"));

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
        AdminServer.start();   // admin website: http://localhost:8080
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
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (Theme.load("plain_logo.png") != null) setIconImage(Theme.load("plain_logo.png"));

        JLabel logo = new JLabel(Theme.icon(Theme.load("plain_logo.png"), 200, 0));
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
        setContentPane(all);

        String[] steps = {"Starting AquaVolt SA...", "Connecting to municipality services...",
            "Syncing Eskom tariffs...", "Almost ready..."};
        javax.swing.Timer t = new javax.swing.Timer(30, null);
        t.addActionListener(e -> {
            progress++;
            bar.setValue(progress);
            status.setText(steps[Math.min(steps.length - 1, progress / 26)]);
            if (progress >= 100) {
                t.stop();
                new LoginSignupPage().setVisible(true);
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
        super("AquaVolt SA - " + title);
        setSize(425, 917);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        BufferedImage ic = Theme.load("plain_logo.png");
        if (ic != null) setIconImage(ic);

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(6, 28, 12, 28));

        JScrollPane scroll = new JScrollPane(body, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.add(Theme.header(), BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(Theme.footer(), BorderLayout.SOUTH);
        setContentPane(root);
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
            INK = new Color(33, 33, 33), YELLOW = new Color(255, 193, 7);
    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 22), BODY = new Font("Segoe UI", Font.PLAIN, 14),
            BOLD = new Font("Segoe UI", Font.BOLD, 14), SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    private Theme() { }

    // ---------- images ----------
    static BufferedImage load(String name) {
        try {
            URL u = Theme.class.getResource("images/" + name);
            if (u != null) return javax.imageio.ImageIO.read(u);
            for (String dir : new String[]{"images/", "src/loginsignuppage/images/"}) {
                java.io.File f = new java.io.File(dir + name);
                if (f.exists()) return javax.imageio.ImageIO.read(f);
            }
        } catch (Exception e) {
            // fall through
        }
        return null;
    }

    /** Trims blank margins (the Eskom logo has a lot of white around it). */
    static BufferedImage crop(BufferedImage im) {
        if (im == null) return null;
        int x0 = im.getWidth(), y0 = im.getHeight(), x1 = 0, y1 = 0;
        for (int y = 0; y < im.getHeight(); y++) {
            for (int x = 0; x < im.getWidth(); x++) {
                int p = im.getRGB(x, y), a = p >>> 24, r = (p >> 16) & 255, g = (p >> 8) & 255, b = p & 255;
                if (a > 30 && (r < 235 || g < 235 || b < 235)) {
                    x0 = Math.min(x0, x); x1 = Math.max(x1, x);
                    y0 = Math.min(y0, y); y1 = Math.max(y1, y);
                }
            }
        }
        return x1 <= x0 ? im : im.getSubimage(x0, y0, x1 - x0 + 1, y1 - y0 + 1);
    }

    /** h <= 0 keeps the aspect ratio. */
    static ImageIcon icon(BufferedImage im, int w, int h) {
        if (im == null) return null;
        if (h <= 0) h = Math.max(1, w * im.getHeight() / im.getWidth());
        return new ImageIcon(im.getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    // ---------- header / footer used on every page ----------
    static JComponent header() {
        JLabel l = new JLabel(icon(load("Logo1.png"), 425, 0), SwingConstants.CENTER);
        if (l.getIcon() == null) { l.setText("AquaVolt SA"); l.setFont(TITLE); }
        l.setOpaque(true);
        l.setBackground(Color.WHITE);
        return l;
    }

    static JComponent footer() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        JLabel t = label("Partnered with:", BOLD, INK, SwingConstants.CENTER);
        t.setAlignmentX(0.5f);
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        row.setOpaque(false);
        row.add(new JLabel(icon(crop(load("national_logo1.png")), 32, 0)));
        row.add(label("SA Municipality", SMALL.deriveFont(12f), INK, SwingConstants.LEFT));
        row.add(new JLabel(icon(crop(load("eskom1.png")), 105, 0)));
        JPanel strip = new JPanel() {
            private final BufferedImage bg = load("bg1.png");
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    int h = getWidth() * bg.getHeight() / bg.getWidth();
                    g2.drawImage(bg, 0, getHeight() - h, getWidth(), h, null);
                }
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
 * The app's "database": a tab-separated text file (aquavolt_users.txt) in the project folder.
 * The Java app and the admin website (through AdminServer) both read and write through this class.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
final class UserStore {
    public static final String PENDING = "PENDING", VERIFIED = "VERIFIED";
    private static final Path FILE = Paths.get("aquavolt_users.txt");
    private static final List<User> USERS = new ArrayList<>();

    /** Holds the sign-up details while the user moves between the sign-up pages. */
    public static User draft = new User();

    public static class User {
        public String id = "", fullName = "", idNumber = "", username = "", passHash = "", email = "", phone = "",
                address = "", meter = "", serial = "", waterMeter = "", accountNo = "", status = PENDING, created = "";
        /** Plain password, only kept in memory during sign-up (never saved). */
        public String rawPassword = "";

        String[] arr() {
            return new String[]{id, fullName, idNumber, username, passHash, email, phone, address, meter, serial,
                waterMeter, accountNo, status, created};
        }

        static User of(String[] a) {
            if (a.length < 14) return null;
            User u = new User();
            u.id = a[0]; u.fullName = a[1]; u.idNumber = a[2]; u.username = a[3]; u.passHash = a[4];
            u.email = a[5]; u.phone = a[6]; u.address = a[7]; u.meter = a[8]; u.serial = a[9];
            u.waterMeter = a[10]; u.accountNo = a[11]; u.status = a[12]; u.created = a[13];
            return u;
        }
    }

    static {
        load();
    }

    private UserStore() { }

    private static synchronized void load() {
        boolean existed = Files.exists(FILE);
        try {
            if (existed) {
                for (String line : Files.readAllLines(FILE, StandardCharsets.UTF_8)) {
                    User u = User.of(line.split("\t", -1));
                    if (u != null) USERS.add(u);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!existed) seed();
    }

    private static void seed() {
        demo("Cheese Mokoena", "9001015800086", "Cheese", "Cheese@123", "cheese@mail.co.za", "0821234567",
                "12 Long Street, Cape Town", "MTR-44821", "SN-77120", "WTR-1024", "ACC-9981", VERIFIED);
        demo("Thando Nkosi", "9505055800083", "Thando", "Thando@123", "thando@gmail.com", "0735550142",
                "8 Vilakazi Street, Soweto", "MTR-10977", "SN-30551", "", "", PENDING);
        demo("Naledi Dlamini", "0001015800087", "Naledi", "Naledi@123", "naledi@outlook.com", "0614449087",
                "45 Berea Road, Durban", "", "", "WTR-5521", "ACC-1207", PENDING);
        demo("Sipho Khumalo", "8812125800085", "Sipho", "Sipho@123", "sipho.k@mail.com", "0794447712",
                "3 Church Street, Pretoria", "MTR-88231", "SN-12089", "WTR-7710", "ACC-3345", VERIFIED);
    }

    private static void demo(String name, String idNo, String user, String pass, String mail, String phone,
                             String addr, String meter, String serial, String water, String acc, String status) {
        User u = new User();
        u.fullName = name; u.idNumber = idNo; u.username = user; u.passHash = hash(pass); u.email = mail;
        u.phone = phone; u.address = addr; u.meter = meter; u.serial = serial; u.waterMeter = water;
        u.accountNo = acc; u.status = status;
        add(u);
    }

    private static void save() {
        StringBuilder sb = new StringBuilder();
        for (User u : USERS) {
            String[] a = u.arr();
            for (int i = 0; i < a.length; i++) {
                if (i > 0) sb.append('\t');
                sb.append(a[i] == null ? "" : a[i].replaceAll("[\\t\\r\\n]", " "));
            }
            sb.append('\n');
        }
        try {
            Files.write(FILE, sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized List<User> all() {
        return new ArrayList<>(USERS);
    }

    public static synchronized User find(String username) {
        for (User u : USERS) if (u.username.equalsIgnoreCase(username)) return u;
        return null;
    }

    public static synchronized boolean exists(String username) {
        return find(username) != null;
    }

    public static synchronized void add(User u) {
        u.id = "U" + UUID.randomUUID().toString().substring(0, 8);
        u.created = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        USERS.add(u);
        save();
    }

    public static synchronized boolean setStatus(String id, String status) {
        for (User u : USERS) {
            if (u.id.equals(id)) { u.status = status; save(); return true; }
        }
        return false;
    }

    public static synchronized boolean delete(String id) {
        boolean removed = USERS.removeIf(u -> u.id.equals(id));
        if (removed) save();
        return removed;
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

    private static String esc(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /** JSON for the admin website (password hashes are never sent). */
    public static synchronized String json() {
        String[] k = {"id", "fullName", "idNumber", "username", "email", "phone", "address", "meter", "serial",
            "waterMeter", "accountNo", "status", "created"};
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < USERS.size(); i++) {
            User u = USERS.get(i);
            String[] v = {u.id, u.fullName, u.idNumber, u.username, u.email, u.phone, u.address, u.meter, u.serial,
                u.waterMeter, u.accountNo, u.status, u.created};
            sb.append(i > 0 ? "," : "").append('{');
            for (int j = 0; j < k.length; j++) {
                sb.append(j > 0 ? "," : "").append('"').append(k[j]).append("\":\"").append(esc(v[j])).append('"');
            }
            sb.append('}');
        }
        return sb.append(']').toString();
    }
}

/**
 * Runs inside the Java app (no extra libraries). Open http://localhost:8080 while the app is running
 * to reach the admin website, which reads the same user database as the app.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
final class AdminServer {
    private static final String KEY = "admin12304";
    private static final Path SITE = Paths.get("admin-website").toAbsolutePath().normalize();

    private AdminServer() { }

    public static void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8080), 0);
            server.createContext("/", AdminServer::files);
            server.createContext("/api/", AdminServer::api);
            server.setExecutor(null);
            server.start();
            System.out.println("AquaVolt admin website: http://localhost:8080");
        } catch (Exception e) {
            System.err.println("Admin server not started: " + e.getMessage());
        }
    }

    private static void api(HttpExchange x) throws IOException {
        String q = x.getRequestURI().getRawQuery() == null ? "" : x.getRequestURI().getRawQuery();
        String path = x.getRequestURI().getPath();
        int code = 200;
        String out;
        if (!q.contains("k=" + KEY)) {
            code = 401;
            out = "{\"error\":\"unauthorised\"}";
        } else if (path.equals("/api/users")) {
            out = UserStore.json();
        } else if (path.equals("/api/approve")) {
            out = "{\"ok\":" + UserStore.setStatus(param(q, "id"), UserStore.VERIFIED) + "}";
        } else if (path.equals("/api/pending")) {
            out = "{\"ok\":" + UserStore.setStatus(param(q, "id"), UserStore.PENDING) + "}";
        } else if (path.equals("/api/delete")) {
            out = "{\"ok\":" + UserStore.delete(param(q, "id")) + "}";
        } else {
            code = 404;
            out = "{\"error\":\"not found\"}";
        }
        send(x, code, "application/json; charset=utf-8", out.getBytes(StandardCharsets.UTF_8));
    }

    private static void files(HttpExchange x) throws IOException {
        String p = x.getRequestURI().getPath();
        if (p.equals("/")) p = "/index.html";
        Path f = SITE.resolve(p.substring(1)).normalize();
        if (!f.startsWith(SITE) || !Files.isRegularFile(f)) {
            send(x, 404, "text/plain", "Not found".getBytes(StandardCharsets.UTF_8));
            return;
        }
        String n = f.toString().toLowerCase();
        String type = n.endsWith(".html") ? "text/html; charset=utf-8" : n.endsWith(".png") ? "image/png"
                : n.endsWith(".css") ? "text/css" : n.endsWith(".js") ? "text/javascript" : "application/octet-stream";
        send(x, 200, type, Files.readAllBytes(f));
    }

    private static String param(String q, String name) {
        for (String kv : q.split("&")) {
            if (kv.startsWith(name + "=")) return kv.substring(name.length() + 1);
        }
        return "";
    }

    private static void send(HttpExchange x, int code, String type, byte[] data) throws IOException {
        x.getResponseHeaders().add("Content-Type", type);
        x.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        x.sendResponseHeaders(code, data.length);
        try (OutputStream o = x.getResponseBody()) {
            o.write(data);
        }
    }
}