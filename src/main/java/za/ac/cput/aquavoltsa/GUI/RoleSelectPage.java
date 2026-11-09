package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * Shown right after the loading page: "Are you signing up as a... Customer / Administrator".
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class RoleSelectPage extends BasePage {

    public RoleSelectPage() {
        super("Welcome");

        gap(16);
        put(Theme.label("Welcome!", Theme.TITLE, Theme.BLUE, SwingConstants.CENTER));
        gap(6);
        put(Theme.label("Are you signing up as a...", Theme.BODY, Theme.GREY, SwingConstants.CENTER));
        gap(26);

        JButton customerBtn = new Theme.Btn("Customer", Theme.BLUE);
        customerBtn.setPreferredSize(new Dimension(300, 56));
        put(customerBtn);
        gap(4);
        put(Theme.label("View your water and electricity usage", Theme.SMALL, Theme.GREY, SwingConstants.CENTER));
        gap(22);

        JButton adminBtn = new Theme.Btn("Administrator", Theme.NAVY);
        adminBtn.setPreferredSize(new Dimension(300, 56));
        put(adminBtn);
        gap(4);
        put(Theme.label("Manage customer accounts and verifications", Theme.SMALL, Theme.GREY, SwingConstants.CENTER));

        customerBtn.addActionListener(e -> go(new LoginSignupPage()));
        adminBtn.addActionListener(e -> go(new AdminLoginPage()));
    }
}