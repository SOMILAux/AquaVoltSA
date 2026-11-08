// This class displays the terms and conditions of the AquaVolt SA application.
package za.ac.cput.aquavoltsa.GUI;

import javax.swing.*;

/**
 * FIXED + RESTYLED by Oyama: the old file built its whole page inside the Back button's listener, so the
 * window opened blank. It is now a normal page in the same clean layout as the login pages, and the
 * Continue button runs the I Agree / I Disagree check.
 */
public class Terms_and_Conditions extends BasePage {
    private final JCheckBox cbI_Agree = Theme.check("I Agree");
    private final JCheckBox cbI_Disagree = Theme.check("I Disagree");

    public Terms_and_Conditions() {
        super("Terms and Conditions", true);
        setBack(() -> AppHooks.toSettings(this));

        put(Theme.label("Terms and Conditions", Theme.TITLE.deriveFont(20f), Theme.BLUE, SwingConstants.CENTER));
        gap(10);
        UiKit.Card card = new UiKit.Card(new java.awt.BorderLayout(), Theme.BLUE);
        card.add(Theme.label(UiKit.html("<p>Welcome to AquaVoltsa.</p><br>"
                + "<p>By using this application, you agree to use your account responsibly and provide correct information.</p><br>"
                + "<p>You are responsible for keeping your username and password secure.</p><br>"
                + "<p>Your account information should not be shared with other people.</p><br>"
                + "<p>You agree to follow the rules and requirements of the AquaVoltsa application.</p><br>"
                + "<p>If you do not agree with these terms, please select I Disagree.</p>", 300),
                Theme.BODY, Theme.INK, SwingConstants.LEFT));
        put(card);
        gap(12);

        // only one of the two boxes can be ticked
        cbI_Agree.addActionListener(e -> { if (cbI_Agree.isSelected()) cbI_Disagree.setSelected(false); });
        cbI_Disagree.addActionListener(e -> { if (cbI_Disagree.isSelected()) cbI_Agree.setSelected(false); });
        put(Theme.row(cbI_Agree, cbI_Disagree));
        gap(8);

        JButton btContinue = put(new Theme.Btn("Continue", Theme.BLUE));
        btContinue.addActionListener(e -> {
            if (cbI_Disagree.isSelected()) {
                JOptionPane.showMessageDialog(this, "You must agree to the Terms and Conditions to continue.",
                        "Terms and Conditions", JOptionPane.WARNING_MESSAGE);
            } else if (cbI_Agree.isSelected()) {
                JOptionPane.showMessageDialog(this, "Thank you for agreeing to the AquaVoltsa Terms and Conditions.",
                        "Terms and Conditions", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select I Agree or I Disagree first.",
                        "Terms and Conditions", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}