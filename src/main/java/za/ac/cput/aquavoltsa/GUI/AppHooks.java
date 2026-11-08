package za.ac.cput.aquavoltsa.GUI;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Connects the group's pages WITHOUT changing their files:
 *  1. every window gets the same constant size (425 x 917) the moment it opens;
 *  2. pages built with absolute positions are centred in that width;
 *  3. buttons that had no action yet (back, home, history, copy, ...) are wired to the right page;
 *  4. the logged-in customer's details are filled into the buy pages.
 * Call AppHooks.install() once at start-up (LoginSignupPage.main does this).
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public final class AppHooks {
    static final int W = 425, H = 917;
    private static boolean installed = false;

    private AppHooks() { }

    public static void install() {
        if (installed) return;
        installed = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(ev -> {
            if (ev.getID() == WindowEvent.WINDOW_OPENED && ev.getSource() instanceof JFrame) {
                JFrame f = (JFrame) ev.getSource();
                if (f.getRootPane().getClientProperty("aquavolt.hooked") == null) {
                    f.getRootPane().putClientProperty("aquavolt.hooked", Boolean.TRUE);
                    fixSize(f);
                    wire(f);
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    // ------------------------------------------------------------------ constant size
    static void fixSize(JFrame f) {
        centreAbsolutePage(f);
        f.setResizable(false);
        f.getContentPane().setPreferredSize(new Dimension(W, H));
        f.pack();
        f.setLocationRelativeTo(null);
    }

    /** Pages laid out with absolute positions (setLayout(null)) were drawn for other widths: centre them. */
    private static void centreAbsolutePage(JFrame f) {
        JScrollPane sp = findScroll(f.getContentPane());
        if (sp == null || !(sp.getViewport().getView() instanceof JPanel)) return;
        JPanel p = (JPanel) sp.getViewport().getView();
        int pw = p.getPreferredSize().width, ph = p.getPreferredSize().height;
        if (p.getLayout() != null || pw <= 0) return;
        int target = W - 18;                       // minus the scroll bar
        int dx = (target - pw) / 2;
        if (dx > 0) {
            for (Component c : p.getComponents()) c.setLocation(c.getX() + dx, c.getY());
            p.setPreferredSize(new Dimension(target, ph));
        }
    }

    private static JScrollPane findScroll(Container c) {
        for (Component k : c.getComponents()) {
            if (k instanceof JScrollPane) return (JScrollPane) k;
            if (k instanceof Container) {
                JScrollPane s = findScroll((Container) k);
                if (s != null) return s;
            }
        }
        return null;
    }

    // ------------------------------------------------------------------ navigation
    private static void go(JFrame from, JFrame to) {
        to.setVisible(true);
        from.dispose();
    }

    private static void wire(JFrame f) {
        if (f instanceof Settings_Page) {
            wireSettingsBack((Settings_Page) f);
        } else if (f instanceof buyWaterPage) {
            buyWaterPage p = (buyWaterPage) f;
            p.backBtn.addActionListener(e -> go(p, new DashboardFrame()));
            if (Session.customer != null) {
                prefill(p.meterNumberField, Session.customer.waterMeter);
                if (p.addressField.getText().isEmpty()) p.addressField.setText(Session.customer.address);
            }
        } else if (f instanceof buyElectricityPage) {
            buyElectricityPage p = (buyElectricityPage) f;
            p.backBtn.addActionListener(e -> go(p, new DashboardFrame()));
            if (Session.customer != null) prefill(p.meterNumberField, Session.customer.meter);
        } else if (f instanceof PaymentPageForWater) {
            PaymentPageForWater p = (PaymentPageForWater) f;
            p.backBtn.addActionListener(e -> go(p, new buyWaterPage()));
        } else if (f instanceof paymentPageForElectricity) {
            paymentPageForElectricity p = (paymentPageForElectricity) f;
            p.backBtn.addActionListener(e -> go(p, new buyElectricityPage()));
        } else if (f instanceof WaterPaymentSuccessfulPage) {
            WaterPaymentSuccessfulPage p = (WaterPaymentSuccessfulPage) f;
            if (Session.lastTxnId != null) p.transactionID.setText(Session.lastTxnId);   // same ID that was saved
            p.homeBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.HomeBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.historyBtn.addActionListener(e -> go(p, new TransactionHistoryForWaterPage()));
            p.copyBtn.addActionListener(e -> copy(p, p.transactionID.getText()));
        } else if (f instanceof ElectricityPaymentSuccessfulPage) {
            ElectricityPaymentSuccessfulPage p = (ElectricityPaymentSuccessfulPage) f;
            if (Session.lastToken != null) p.token.setText(Session.lastToken + " ");     // same token that was saved
            p.homeBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.HomeBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.historyBtn.addActionListener(e -> go(p, new TransactionHistoryForElectricityPage()));
            p.copyBtn.addActionListener(e -> copy(p, p.token.getText().trim()));
        } else if (f instanceof TransactionHistoryForWaterPage) {
            TransactionHistoryForWaterPage p = (TransactionHistoryForWaterPage) f;
            p.backArrowBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.homeIconBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.electricityBtn.addActionListener(e -> go(p, new TransactionHistoryForElectricityPage()));
            searchable(p.searchField, p.historyPanel);
        } else if (f instanceof TransactionHistoryForElectricityPage) {
            TransactionHistoryForElectricityPage p = (TransactionHistoryForElectricityPage) f;
            p.backArrowBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.homeIconBtn.addActionListener(e -> go(p, new DashboardFrame()));
            p.waterBtn.addActionListener(e -> go(p, new TransactionHistoryForWaterPage()));
            searchable(p.searchField, p.historyPanel);
        }
    }

    /**
     * Settings_Page: the picture at the top-left (back.png) now takes the customer back to the dashboard.
     * It looks for the top-left-most button/label that only shows a small picture. If the page has none, a round
     * back arrow is added in the corner instead.
     */
    private static void wireSettingsBack(Settings_Page f) {
        Component best = null;
        int by = Integer.MAX_VALUE, bx = Integer.MAX_VALUE;
        for (Component c : allComponents(f.getContentPane())) {
            javax.swing.Icon icon = null;
            if (c instanceof JButton && blank(((JButton) c).getText())) icon = ((JButton) c).getIcon();
            else if (c instanceof JLabel && blank(((JLabel) c).getText())) icon = ((JLabel) c).getIcon();
            if (icon == null || icon.getIconWidth() > 80 || !c.isShowing()) continue;
            java.awt.Point p = javax.swing.SwingUtilities.convertPoint(c.getParent(), c.getLocation(), f.getContentPane());
            if (p.y > 140 || p.x > W / 2) continue;
            if (p.y < by || (p.y == by && p.x < bx)) { best = c; by = p.y; bx = p.x; }
        }
        if (best instanceof JButton) {
            JButton b = (JButton) best;
            for (java.awt.event.ActionListener l : b.getActionListeners()) b.removeActionListener(l);
            b.addActionListener(e -> toDashboard(f));
        } else if (best instanceof JLabel) {
            JLabel l = (JLabel) best;
            for (java.awt.event.MouseListener m : l.getMouseListeners()) l.removeMouseListener(m);
            l.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
            l.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseClicked(java.awt.event.MouseEvent e) { toDashboard(f); }
            });
        } else {
            Theme.BackButton back = new Theme.BackButton();
            back.setBounds(8, 8, 44, 44);
            back.addActionListener(e -> toDashboard(f));
            f.getLayeredPane().add(back, JLayeredPane.POPUP_LAYER);
        }
    }

    private static boolean blank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static java.util.List<Component> allComponents(Container c) {
        java.util.List<Component> list = new java.util.ArrayList<>();
        for (Component k : c.getComponents()) {
            list.add(k);
            if (k instanceof Container) list.addAll(allComponents((Container) k));
        }
        return list;
    }

    /** Opens the settings page (same steps the group's pages use) and closes the current window. */
    static void toSettings(JFrame from) {
        Settings_Page page = new Settings_Page();
        page.showGui();
        page.setSize(W, H);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        from.dispose();
    }

    static void toDashboard(JFrame from) {
        new DashboardFrame().setVisible(true);
        from.dispose();
    }

    /** The buy pages read the meter with Long.parseLong, so only the digits of "MTR-44821" are used. */
    private static void prefill(JTextField field, String value) {
        String digits = value == null ? "" : value.replaceAll("\\D", "");
        if (!digits.isEmpty() && field.getText().isEmpty()) field.setText(digits);
    }

    private static void copy(JFrame f, String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        JOptionPane.showMessageDialog(f, "Copied: " + text);
    }

    /** Makes the "Search by ID" box on the history pages work. */
    private static void searchable(JTextField field, JPanel history) {
        DocumentListener l = new DocumentListener() {
            private void filter() {
                String q = field.getText().trim().toLowerCase();
                for (Component c : history.getComponents()) {
                    if (c instanceof JPanel) c.setVisible(q.isEmpty() || textOf((Container) c).contains(q));
                }
                history.revalidate();
                history.repaint();
            }
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) { filter(); }
        };
        field.getDocument().addDocumentListener(l);
    }

    private static String textOf(Container c) {
        StringBuilder sb = new StringBuilder();
        for (Component k : c.getComponents()) {
            if (k instanceof JLabel) sb.append(((JLabel) k).getText()).append(' ');
            else if (k instanceof Container) sb.append(textOf((Container) k));
        }
        return sb.toString().toLowerCase();
    }
}