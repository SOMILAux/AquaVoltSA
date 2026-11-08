package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;

/**
 * Terms and conditions for SIGN-UP: the back arrow and the Back button return to sign-up page 3
 * (what was typed is kept). The Settings version is TermsPage.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
public class SignupTermsPage extends BasePage {

    public SignupTermsPage() {
        super("Terms and Conditions", true);
        setBack(this::back);
        TermsText.addTo(this);
        JButton backBtn = put(new Theme.Btn("Back", Theme.GREY));
        backBtn.addActionListener(e -> back());
    }

    private void back() {
        go(new SignupPage3());
    }
}