package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;

/**
 * Terms and conditions opened from SETTINGS: Back returns to the settings page.
 * (Sign-up has its own copy, SignupTermsPage, which returns to sign-up page 3.)
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class TermsPage extends BasePage {

    public TermsPage() {
        super("Terms and Conditions", true);
        setBack(() -> AppHooks.toSettings(this));
        TermsText.addTo(this);
        JButton backBtn = put(new Theme.Btn("Back", Theme.GREY));
        backBtn.addActionListener(e -> AppHooks.toSettings(this));
    }
}