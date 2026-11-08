package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * "Change your picture": take a photo with the camera app, or choose one from the gallery (any folder).
 * kind = "c" for customers, "a" for admins. After it closes, check the field "changed".
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
class ProfilePictureDialog extends JDialog {
    boolean changed = false;
    private final String kind, username;

    ProfilePictureDialog(Window owner, String kind, String username, String displayName) {
        super(owner, "Profile picture", Dialog.ModalityType.APPLICATION_MODAL);
        this.kind = kind;
        this.username = username;

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        UiKit.Avatar preview = new UiKit.Avatar(displayName, kind, username, 120);
        JPanel previewRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        previewRow.setOpaque(false);
        previewRow.add(preview);
        add(p, previewRow);
        p.add(Box.createVerticalStrut(10));
        add(p, Theme.label("Change your picture", Theme.TITLE.deriveFont(18f), Theme.NAVY, SwingConstants.CENTER));
        add(p, Theme.label("Choose how you want to add it", Theme.SMALL.deriveFont(12f), Theme.GREY, SwingConstants.CENTER));
        p.add(Box.createVerticalStrut(14));

        JButton camera = button("Take a photo (camera)", Theme.BLUE);
        JButton gallery = button("Choose from gallery", Theme.NAVY);
        JButton remove = button("Remove picture", Theme.RED);
        JButton cancel = button("Cancel", Theme.GREY);
        remove.setEnabled(ProfilePics.has(kind, username));
        for (JButton b : new JButton[]{camera, gallery, remove, cancel}) {
            add(p, b);
            p.add(Box.createVerticalStrut(8));
        }

        camera.addActionListener(e -> {
            ProfilePics.openCamera();
            int r = JOptionPane.showConfirmDialog(this,
                    "The camera app was opened.\nTake your photo, then click OK here to use it.\n"
                    + "(If no camera opened, cancel and use \"Choose from gallery\".)",
                    "Take a photo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (r != JOptionPane.OK_OPTION) return;
            File f = ProfilePics.newestPhoto(30 * 60 * 1000L);   // a photo taken in the last 30 minutes
            if (f == null) {
                JOptionPane.showMessageDialog(this, "No new photo was found. Please pick it yourself.");
                choose(ProfilePics.cameraFolder());
            } else {
                apply(f);
            }
        });
        gallery.addActionListener(e -> choose(ProfilePics.cameraFolder().getParentFile() == null
                ? ProfilePics.cameraFolder() : new File(System.getProperty("user.home", "."), "Pictures")));
        remove.addActionListener(e -> {
            ProfilePics.remove(kind, username);
            changed = true;
            dispose();
        });
        cancel.addActionListener(e -> dispose());

        setContentPane(p);
        pack();
        setLocationRelativeTo(owner);
    }

    private static JButton button(String text, Color c) {
        JButton b = new Theme.Btn(text, c);
        b.setPreferredSize(new Dimension(280, 44));
        return b;
    }

    private static void add(JPanel p, JComponent c) {
        c.setAlignmentX(0.5f);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        p.add(c);
    }

    private void choose(File startIn) {
        JFileChooser fc = new JFileChooser(startIn);
        fc.setDialogTitle("Choose a picture");
        fc.setFileFilter(new FileNameExtensionFilter("Pictures (png, jpg, gif, bmp)", "png", "jpg", "jpeg", "gif", "bmp"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) apply(fc.getSelectedFile());
    }

    private void apply(File f) {
        if (ProfilePics.save(kind, username, f)) {
            changed = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "That file could not be read as a picture.");
        }
    }
}