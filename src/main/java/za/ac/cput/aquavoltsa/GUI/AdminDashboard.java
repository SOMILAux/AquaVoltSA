package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Admin app screen (425 x 917, like the customer app): app bar, bottom navigation
 * (Home / Customers / Admins / Activity) and card lists instead of tables.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class AdminDashboard extends JFrame {
    private static final Color GREEN = new Color(46, 125, 50), AMBER = new Color(245, 166, 0), PAGE = new Color(245, 248, 252);
    private static final String[] TABS = {"Home", "Customers", "Payments", "Admins", "Activity"};

    private final AdminStore.Admin me;
    private final boolean isMain;

    private final CardLayout cards = new CardLayout();
    private final JPanel center = new JPanel(cards);
    private final JButton[] nav = new JButton[5];
    private final int[] badge = new int[5];
    private int current = 0;

    private final Feed homeFeed = new Feed(), custFeed = new Feed(), adminFeed = new Feed(), logFeed = new Feed(), payFeed = new Feed();
    private final JLabel[] statValue = new JLabel[4];
    private final Theme.HintField searchField = new Theme.HintField("Search name, username, email, ID or meter");
    private final JPanel chipRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    private final JLabel countLabel = Theme.label(" ", Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT);
    private final JLabel payInfo = Theme.label(" ", Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT);
    private String filter = "All";
    private List<UserStore.User> allCustomers = new ArrayList<>();
    private List<AdminStore.Admin> admins = new ArrayList<>();

    public AdminDashboard(AdminStore.Admin me) {
        this.me = me;
        this.isMain = me.main;
        setTitle("AquaVolt SA - Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Theme.image("plain_logo.png"));
        setResizable(false);

        center.add(homePage(), TABS[0]);
        center.add(customersPage(), TABS[1]);
        center.add(paymentsPage(), TABS[2]);
        center.add(adminsPage(), TABS[3]);
        center.add(logPage(), TABS[4]);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(PAGE);
        root.add(appBar(), BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(navBar(), BorderLayout.SOUTH);
        root.setPreferredSize(new Dimension(425, 917));   // GUI size
        setContentPane(root);
        pack();
        setLocationRelativeTo(null);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { fillCustomers(); }
            @Override public void removeUpdate(DocumentEvent e) { fillCustomers(); }
            @Override public void changedUpdate(DocumentEvent e) { fillCustomers(); }
        });
        buildChips();
        refresh();
        show(0);
    }

    // ================================================================ screens
    private JComponent appBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Theme.NAVY);
        bar.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 8));

        JLabel title = new JLabel("  AquaVolt SA", Theme.icon("plain_logo.png", 32, 33), SwingConstants.LEFT);
        title.setFont(Theme.TITLE.deriveFont(18f));
        title.setForeground(Color.WHITE);
        JLabel sub = Theme.label("      " + me.fullName + (isMain ? "  (main admin)" : "  (admin)"),
                Theme.SMALL.deriveFont(12f), new Color(200, 222, 255), SwingConstants.LEFT);
        JPanel names = new JPanel(new GridLayout(2, 1));
        names.setOpaque(false);
        names.add(title);
        names.add(sub);
        bar.add(names, BorderLayout.WEST);

        JButton logout = Theme.link("Log out");
        logout.setForeground(Color.WHITE);
        logout.addActionListener(e -> {
            AdminStore.log(me.username, "LOGOUT", "Admin logged out");
            new RoleSelectPage().setVisible(true);
            dispose();
        });
        // the admin's own picture: tap it to take a photo or choose one
        UiKit.Avatar myPic = new UiKit.Avatar(me.fullName, "a", me.username, 36);
        myPic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        myPic.setToolTipText("Change my picture");
        myPic.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                ProfilePictureDialog d = new ProfilePictureDialog(AdminDashboard.this, "a", me.username, me.fullName);
                d.setVisible(true);
                if (d.changed) { myPic.reload(); refresh(); }
            }
        });
        JPanel east = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 4));
        east.setOpaque(false);
        east.add(myPic);
        east.add(logout);
        bar.add(east, BorderLayout.EAST);
        return bar;
    }

    private JComponent navBar() {
        JPanel p = new JPanel(new GridLayout(1, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(225, 225, 225)));
        p.setPreferredSize(new Dimension(425, 62));
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            nav[i] = new JButton(TABS[i]) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    boolean on = current == idx;
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    if (on) {
                        g2.setColor(Theme.NAVY);
                        g2.fillRect(0, 0, getWidth(), 3);
                    }
                    g2.setFont(on ? Theme.BOLD.deriveFont(13f) : Theme.BODY.deriveFont(13f));
                    g2.setColor(on ? Theme.NAVY : Theme.GREY);
                    FontMetrics fm = g2.getFontMetrics();
                    int w = fm.stringWidth(TABS[idx]), x = (getWidth() - w) / 2, y = getHeight() / 2 + fm.getAscent() / 2 - 2;
                    g2.drawString(TABS[idx], x, y);
                    if (badge[idx] > 0) {
                        g2.setColor(Theme.RED);
                        g2.fillOval(x + w + 3, y - 20, 17, 17);
                        g2.setColor(Color.WHITE);
                        g2.setFont(Theme.BOLD.deriveFont(10f));
                        String n = String.valueOf(badge[idx]);
                        g2.drawString(n, x + w + 3 + (17 - g2.getFontMetrics().stringWidth(n)) / 2, y - 7);
                    }
                    g2.dispose();
                }
            };
            nav[i].setBorderPainted(false);
            nav[i].setContentAreaFilled(false);
            nav[i].setFocusPainted(false);
            nav[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            nav[i].addActionListener(e -> { refresh(); show(idx); });
            p.add(nav[i]);
        }
        return p;
    }

    private JComponent homePage() {
        String first = me.fullName.split("\\s+")[0];
        JLabel hello = Theme.label("Welcome back, " + first + "!", Theme.TITLE.deriveFont(20f), Theme.INK, SwingConstants.LEFT);

        String[] caption = {"Total customers", "Waiting for approval", "Verified customers", "Admin requests"};
        Color[] color = {Theme.NAVY, AMBER, GREEN, Theme.BLUE};
        JPanel grid = new JPanel(new GridLayout(2, 2, 10, 10));
        grid.setOpaque(false);
        for (int i = 0; i < 4; i++) {
            final int idx = i;
            Card c = new Card(new BorderLayout(), color[i]);
            statValue[i] = Theme.label("0", Theme.TITLE.deriveFont(30f), Theme.INK, SwingConstants.LEFT);
            c.add(Theme.label(caption[i], Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT), BorderLayout.NORTH);
            c.add(statValue[i], BorderLayout.CENTER);
            c.setPreferredSize(new Dimension(180, 84));
            c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            c.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (idx == 3) show(3);
                    else openCustomers(idx == 0 ? "All" : idx == 1 ? "Pending" : "Verified");
                }
            });
            grid.add(c);
        }
        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(16, 14, 4, 14));
        top.add(hello, BorderLayout.NORTH);
        top.add(grid, BorderLayout.CENTER);

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(PAGE);
        page.add(top, BorderLayout.NORTH);
        page.add(scroll(homeFeed), BorderLayout.CENTER);
        return page;
    }

    private JComponent customersPage() {
        chipRow.setOpaque(false);
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(14, 14, 0, 14));
        searchField.setAlignmentX(0.5f);
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        chipRow.setAlignmentX(0.5f);
        chipRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        countLabel.setAlignmentX(0.5f);
        top.add(searchField);
        top.add(Box.createVerticalStrut(10));
        top.add(chipRow);
        top.add(Box.createVerticalStrut(6));
        JButton notifyAll = Theme.link("Send notification to all");
        notifyAll.setFont(Theme.BOLD.deriveFont(12f));
        notifyAll.setPreferredSize(new Dimension(190, 30));
        notifyAll.addActionListener(e -> openNotify(null));
        JPanel countRow = new JPanel(new BorderLayout());
        countRow.setOpaque(false);
        countRow.setAlignmentX(0.5f);
        countRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        countRow.add(countLabel, BorderLayout.WEST);
        countRow.add(notifyAll, BorderLayout.EAST);
        top.add(countRow);

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(PAGE);
        page.add(top, BorderLayout.NORTH);
        page.add(scroll(custFeed), BorderLayout.CENTER);
        return page;
    }

    private JComponent adminsPage() {
        JButton add = new Theme.Btn("+ Add new admin", Theme.NAVY);
        add.addActionListener(e -> {
            new AdminRequestDialog(this, me.username).setVisible(true);
            refresh();
        });
        JLabel note = Theme.label(isMain ? "New admins need your approval before they can log in."
                : "Only " + AdminStore.MAIN + " can accept or remove admins.", Theme.SMALL.deriveFont(12f), Theme.GREY,
                SwingConstants.LEFT);
        JPanel top = new JPanel(new BorderLayout(0, 8));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(14, 14, 0, 14));
        top.add(add, BorderLayout.NORTH);
        top.add(note, BorderLayout.SOUTH);

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(PAGE);
        page.add(top, BorderLayout.NORTH);
        page.add(scroll(adminFeed), BorderLayout.CENTER);
        return page;
    }

    private JComponent logPage() {
        JLabel title = Theme.label("Recent activity", Theme.BOLD.deriveFont(16f), Theme.INK, SwingConstants.LEFT);
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(PAGE);
        page.add(title, BorderLayout.NORTH);
        page.add(scroll(logFeed), BorderLayout.CENTER);
        return page;
    }

    private JComponent paymentsPage() {
        JLabel title = Theme.label("Payments", Theme.BOLD.deriveFont(16f), Theme.INK, SwingConstants.LEFT);
        JPanel top = new JPanel(new BorderLayout(0, 4));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        top.add(title, BorderLayout.NORTH);
        top.add(payInfo, BorderLayout.SOUTH);
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(PAGE);
        page.add(top, BorderLayout.NORTH);
        page.add(scroll(payFeed), BorderLayout.CENTER);
        return page;
    }

    private void fillPayments() {
        payFeed.clear();
        List<TransactionStore.Txn> list = TransactionStore.all(200);
        double total = 0;
        for (TransactionStore.Txn t : list) {
            total += t.amount + t.fee;
            Card c = new Card(new GridLayout(3, 1, 0, 2), "WATER".equals(t.utility) ? Theme.BLUE : AMBER);
            c.add(Theme.label(("WATER".equals(t.utility) ? "Water" : "Electricity") + "   R" + String.format("%.2f", t.amount + t.fee),
                    Theme.BOLD, Theme.INK, SwingConstants.LEFT));
            c.add(Theme.label(t.id + (t.token.isEmpty() ? "" : "   |   token " + t.token), Theme.SMALL.deriveFont(12f), Theme.INK, SwingConstants.LEFT));
            c.add(Theme.label("@" + t.username + "   |   " + t.created + "   |   " + t.status, Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT));
            payFeed.addItem(c);
        }
        if (list.isEmpty()) payFeed.addItem(empty("No payments recorded yet."));
        payFeed.done();
        payInfo.setText(list.size() + (list.size() == 1 ? " payment" : " payments") + "   |   total R" + String.format("%.2f", total));
    }

    /** username = one customer, or null to choose / send to everyone. */
    private void openNotify(String username) {
        NotifyDialog d = new NotifyDialog(this, me.username, username);
        d.setVisible(true);
        if (d.sentSummary != null) {
            refresh();
            toast(d.sentSummary);
        }
    }

    private static JScrollPane scroll(Feed f) {
        JScrollPane sp = new JScrollPane(f, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        sp.getViewport().setBackground(PAGE);
        sp.getVerticalScrollBar().setUnitIncrement(18);
        return sp;
    }

    // ================================================================ data -> screens
    private void show(int i) {
        current = i;
        cards.show(center, TABS[i]);
        for (JButton b : nav) b.repaint();
    }

    private void openCustomers(String f) {
        filter = f;
        buildChips();
        fillCustomers();
        show(1);
    }

    private void refresh() {
        allCustomers = UserStore.all();
        admins = AdminStore.all();
        int pending = 0, pendingAdmins = 0;
        for (UserStore.User u : allCustomers) if (UserStore.PENDING.equals(u.status)) pending++;
        for (AdminStore.Admin a : admins) if (AdminStore.PENDING.equals(a.status)) pendingAdmins++;
        statValue[0].setText(String.valueOf(allCustomers.size()));
        statValue[1].setText(String.valueOf(pending));
        statValue[2].setText(String.valueOf(allCustomers.size() - pending));
        statValue[3].setText(String.valueOf(pendingAdmins));
        badge[1] = pending;
        badge[3] = pendingAdmins;

        // Home: what needs attention
        homeFeed.clear();
        homeFeed.addItem(Theme.label("Needs your attention", Theme.BOLD.deriveFont(16f), Theme.INK, SwingConstants.LEFT));
        int shownCount = 0;
        for (UserStore.User u : allCustomers) {
            if (UserStore.PENDING.equals(u.status) && shownCount++ < 5) homeFeed.addItem(customerCard(u));
        }
        if (isMain) {
            for (AdminStore.Admin a : admins) if (AdminStore.PENDING.equals(a.status)) homeFeed.addItem(adminCard(a));
        }
        if (shownCount == 0 && pendingAdmins == 0) homeFeed.addItem(empty("All caught up - nothing is waiting for approval."));
        homeFeed.done();

        fillCustomers();

        adminFeed.clear();
        for (AdminStore.Admin a : admins) adminFeed.addItem(adminCard(a));
        adminFeed.done();

        logFeed.clear();
        List<String[]> logs = AdminStore.logs();
        for (String[] r : logs) logFeed.addItem(logCard(r));
        if (logs.isEmpty()) logFeed.addItem(empty("No activity yet."));
        logFeed.done();

        fillPayments();
        for (JButton b : nav) b.repaint();
    }

    private List<UserStore.User> matching() {
        String q = searchField.getText().trim().toLowerCase();
        List<UserStore.User> list = new ArrayList<>();
        for (UserStore.User u : allCustomers) {
            boolean statusOk = "All".equals(filter) || ("Pending".equals(filter) == UserStore.PENDING.equals(u.status));
            String hay = (u.fullName + " " + u.username + " " + u.email + " " + u.idNumber + " " + u.phone + " "
                    + u.meter + " " + u.waterMeter).toLowerCase();
            if (statusOk && (q.isEmpty() || hay.contains(q))) list.add(u);
        }
        return list;
    }

    private void fillCustomers() {
        custFeed.clear();
        List<UserStore.User> list = matching();
        for (UserStore.User u : list) custFeed.addItem(customerCard(u));
        if (list.isEmpty()) custFeed.addItem(empty("No customers found."));
        custFeed.done();
        countLabel.setText(list.size() + (list.size() == 1 ? " customer" : " customers"));
    }

    private void buildChips() {
        chipRow.removeAll();
        for (String name : new String[]{"All", "Pending", "Verified"}) {
            JButton b = new Theme.Btn(name, name.equals(filter) ? Theme.NAVY : new Color(185, 194, 206));
            b.setFont(Theme.BOLD.deriveFont(12f));
            b.setPreferredSize(new Dimension(84, 34));
            b.addActionListener(e -> { filter = name; buildChips(); fillCustomers(); });
            chipRow.add(b);
        }
        JButton export = Theme.link("Export CSV");
        export.setFont(Theme.BOLD.deriveFont(12f));
        export.addActionListener(e -> exportCsv());
        chipRow.add(export);
        chipRow.revalidate();
        chipRow.repaint();
    }

    // ================================================================ cards
    private JComponent customerCard(UserStore.User u) {
        Card c = new Card(new BorderLayout(0, 8), null);
        c.add(head(u.fullName, "@" + u.username + "   " + u.phone, label(u.status), "c", u.username), BorderLayout.NORTH);
        c.add(Theme.label(u.email, Theme.SMALL.deriveFont(12f), Theme.INK, SwingConstants.LEFT), BorderLayout.CENTER);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setOpaque(false);
        row.add(small("View", Theme.GREY, e -> viewCustomer(u)));
        if (UserStore.PENDING.equals(u.status)) {
            row.add(small("Accept", GREEN, e -> setCustomerStatus(u, UserStore.VERIFIED, "ACCEPT_CUSTOMER", "was accepted and can now log in")));
        } else {
            row.add(small("Revoke", AMBER, e -> setCustomerStatus(u, UserStore.PENDING, "REVOKE_CUSTOMER", "was moved back to pending")));
        }
        row.add(small("Notify", Theme.BLUE, e -> openNotify(u.username)));
        row.add(small("Delete", Theme.RED, e -> deleteCustomer(u)));
        c.add(row, BorderLayout.SOUTH);
        return c;
    }

    private JComponent adminCard(AdminStore.Admin a) {
        Card c = new Card(new BorderLayout(0, 8), null);
        c.add(head(a.fullName, "@" + a.username, label(a.status), "a", a.username), BorderLayout.NORTH);
        String info = a.main ? "Main administrator" : a.email + "   |   requested by " + a.requestedBy;
        c.add(Theme.label(info, Theme.SMALL.deriveFont(12f), Theme.INK, SwingConstants.LEFT), BorderLayout.CENTER);
        if (isMain && !a.main) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
            row.setOpaque(false);
            if (AdminStore.PENDING.equals(a.status)) row.add(small("Accept", GREEN, e -> acceptAdmin(a)));
            row.add(small("Remove", Theme.RED, e -> removeAdmin(a)));
            c.add(row, BorderLayout.SOUTH);
        }
        return c;
    }

    private JComponent logCard(String[] r) {   // {time, admin, activity, details}
        Card c = new Card(new GridLayout(2, 1, 0, 2), null);
        String what = r[2].replace('_', ' ') + (r[3].isEmpty() ? "" : "  -  " + r[3]);
        c.add(Theme.label(what, Theme.BOLD.deriveFont(13f), Theme.INK, SwingConstants.LEFT));
        c.add(Theme.label("by " + r[1] + "   |   " + r[0], Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT));
        return c;
    }

    private JComponent head(String name, String line2, String status, String kind, String username) {
        JPanel h = new JPanel(new BorderLayout(10, 0));
        h.setOpaque(false);
        JPanel names = new JPanel(new GridLayout(2, 1));
        names.setOpaque(false);
        names.add(Theme.label(name, Theme.BOLD, Theme.INK, SwingConstants.LEFT));
        names.add(Theme.label(line2, Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT));
        h.add(new UiKit.Avatar(name, kind, username, 42), BorderLayout.WEST);   // profile picture (or initials)
        h.add(names, BorderLayout.CENTER);
        JPanel pill = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 6));
        pill.setOpaque(false);
        pill.add(new Pill(status));
        h.add(pill, BorderLayout.EAST);
        return h;
    }

    private JComponent empty(String text) {
        JLabel l = Theme.label(text, Theme.BODY, Theme.GREY, SwingConstants.CENTER);
        l.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        return l;
    }

    private static JButton small(String text, Color color, ActionListener al) {
        JButton b = new Theme.Btn(text, color);
        b.setFont(Theme.BOLD.deriveFont(12f));
        b.setPreferredSize(new Dimension(84, 32));
        b.addActionListener(al);
        return b;
    }

    private static String label(String status) {
        if (UserStore.VERIFIED.equals(status)) return "Verified";
        if (AdminStore.APPROVED.equals(status)) return "Approved";
        return "Pending";
    }

    private void toast(String text) {
        Theme.toast(this, text, 800, 3500);
    }

    // ================================================================ actions
    private void viewCustomer(UserStore.User u) {
        String[][] rows = {{"Full name", u.fullName}, {"Username", u.username}, {"ID number", u.idNumber},
            {"Email", u.email}, {"Phone", u.phone}, {"Home address", u.address}, {"Electricity meter", u.meter},
            {"Meter serial", u.serial}, {"Water meter", u.waterMeter}, {"Municipal account", u.accountNo},
            {"Status", label(u.status)}, {"Signed up", u.created}};
        JPanel p = new JPanel(new GridLayout(rows.length, 2, 10, 6));
        for (String[] r : rows) {
            p.add(Theme.label(r[0], Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.LEFT));
            p.add(Theme.label(r[1].isEmpty() ? "-" : r[1], Theme.BOLD.deriveFont(13f), Theme.INK, SwingConstants.LEFT));
        }
        JOptionPane.showMessageDialog(this, p, "Customer details", JOptionPane.PLAIN_MESSAGE);
    }

    private void setCustomerStatus(UserStore.User u, String status, String activity, String text) {
        if (UserStore.setStatus(u.id, status)) {
            AdminStore.log(me.username, activity, u.username);
            refresh();
            toast(u.fullName + " " + text);
        }
    }

    private void deleteCustomer(UserStore.User u) {
        int ok = JOptionPane.showConfirmDialog(this, "Permanently delete " + u.fullName + " (" + u.username + ")?\nThis cannot be undone.",
                "Delete account", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION && UserStore.delete(u.id)) {
            AdminStore.log(me.username, "DELETE_CUSTOMER", u.username);
            refresh();
            toast(u.fullName + " was deleted");
        }
    }

    private void acceptAdmin(AdminStore.Admin a) {
        if (AdminStore.setStatus(a.username, AdminStore.APPROVED)) {
            AdminStore.log(me.username, "ADMIN_ACCEPTED", a.username);
            refresh();
            toast(a.username + " is now an approved admin");
        }
    }

    private void removeAdmin(AdminStore.Admin a) {
        int ok = JOptionPane.showConfirmDialog(this, "Remove admin " + a.username + "?", "Remove admin",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION && AdminStore.remove(a.username)) {
            AdminStore.log(me.username, "ADMIN_REMOVED", a.username);
            refresh();
            toast(a.username + " was removed");
        }
    }

    private void exportCsv() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("aquavolt_customers.csv"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        List<UserStore.User> list = matching();
        try (PrintWriter w = new PrintWriter(fc.getSelectedFile(), "UTF-8")) {
            w.println("Name,Username,ID number,Email,Phone,Address,Electricity meter,Water meter,Status,Signed up");
            for (UserStore.User u : list) {
                String[] v = {u.fullName, u.username, u.idNumber, u.email, u.phone, u.address, u.meter, u.waterMeter,
                    label(u.status), u.created};
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < v.length; i++) sb.append(i > 0 ? "," : "").append('"').append(v[i].replace("\"", "\"\"")).append('"');
                w.println(sb);
            }
            AdminStore.log(me.username, "EXPORT_CSV", list.size() + " customers");
            toast("Exported " + list.size() + " customers");
        } catch (Exception ex) {
            toast("Could not save the file");
        }
    }

    // ================================================================ small custom components
    /** Scrolling vertical list that always matches the width of the screen. */
    private static class Feed extends JPanel implements Scrollable {
        Feed() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(PAGE);
            setBorder(BorderFactory.createEmptyBorder(10, 14, 12, 14));
        }

        void clear() { removeAll(); }

        void addItem(JComponent c) {
            c.setAlignmentX(0.5f);
            c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
            add(c);
            add(Box.createVerticalStrut(10));
        }

        void done() { revalidate(); repaint(); }

        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 18; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 160; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() {
            return getParent() instanceof JViewport && getParent().getHeight() > getPreferredSize().height;
        }
    }

    /** White rounded card (optionally with a coloured stripe on the left). */
    private static class Card extends JPanel {
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
            g2.setColor(Color.WHITE);
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
    }

    /** Round avatar with the person's initials. */
    private static class Avatar extends JComponent {
        private final String initials;

        Avatar(String name) {
            StringBuilder sb = new StringBuilder();
            for (String w : name.trim().split("\\s+")) if (!w.isEmpty() && sb.length() < 2) sb.append(Character.toUpperCase(w.charAt(0)));
            initials = sb.length() == 0 ? "?" : sb.toString();
            setPreferredSize(new Dimension(42, 42));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(Theme.NAVY);
            g2.fillOval(0, 0, 40, 40);
            g2.setColor(Color.WHITE);
            g2.setFont(Theme.BOLD);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(initials, (40 - fm.stringWidth(initials)) / 2, 20 + fm.getAscent() / 2 - 2);
            g2.dispose();
        }
    }

    /** Small coloured status badge. */
    private static class Pill extends JLabel {
        private final boolean pending;

        Pill(String text) {
            super(text, SwingConstants.CENTER);
            pending = "Pending".equals(text);
            setFont(Theme.BOLD.deriveFont(11f));
            setForeground(pending ? new Color(138, 101, 0) : GREEN);
            setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(pending ? new Color(255, 243, 205) : new Color(232, 245, 233));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }
}