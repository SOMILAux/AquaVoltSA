package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Profile pictures are saved as small square PNG files in the folder "profile_pictures" next to the project
 * (customers: c_username.png, admins: a_username.png) and shown as round pictures.
 * @StudentNumber 241028213
 * @author Oyama Sibunzi
 */
final class ProfilePics {
    private static final File DIR = new File("profile_pictures");

    private ProfilePics() { }

    static File file(String kind, String username) {
        return new File(DIR, kind + "_" + username.replaceAll("[^A-Za-z0-9._-]", "_") + ".png");
    }

    static boolean has(String kind, String username) {
        return username != null && file(kind, username).exists();
    }

    /** Round picture of the given size, or null when the user has no picture yet. */
    static ImageIcon circle(String kind, String username, int size) {
        if (!has(kind, username)) return null;
        try {
            BufferedImage im = ImageIO.read(file(kind, username));
            return im == null ? null : new ImageIcon(round(im, size));
        } catch (IOException e) {
            return null;
        }
    }

    static BufferedImage square(BufferedImage src, int size) {
        int s = Math.min(src.getWidth(), src.getHeight());
        BufferedImage crop = src.getSubimage((src.getWidth() - s) / 2, (src.getHeight() - s) / 2, s, s);
        BufferedImage out = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(crop, 0, 0, size, size, null);
        g.dispose();
        return out;
    }

    static BufferedImage round(BufferedImage src, int size) {
        BufferedImage sq = square(src, size);
        BufferedImage out = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(new TexturePaint(sq, new Rectangle(0, 0, size, size)));
        g.fill(new Ellipse2D.Float(0, 0, size, size));
        g.dispose();
        return out;
    }

    /** Crops the picture to a square, shrinks it to 256 x 256 and saves it for this user. */
    static boolean save(String kind, String username, File source) {
        try {
            BufferedImage im = ImageIO.read(source);
            if (im == null) return false;
            DIR.mkdirs();
            return ImageIO.write(square(im, 256), "png", file(kind, username));
        } catch (IOException e) {
            return false;
        }
    }

    static void remove(String kind, String username) {
        file(kind, username).delete();
    }

    // ---------------------------------------------------------------- camera
    /** Java has no built-in camera, so this opens the computer's own camera app. */
    static void openCamera() {
        String os = System.getProperty("os.name", "").toLowerCase();
        try {
            if (os.contains("win")) new ProcessBuilder("cmd", "/c", "start", "", "microsoft.windows.camera:").start();
            else if (os.contains("mac")) new ProcessBuilder("open", "-a", "Photo Booth").start();
            else new ProcessBuilder("cheese").start();
        } catch (IOException e) {
            // no camera app found - the user can still choose a picture from the gallery
        }
    }

    private static File[] cameraFolders() {
        File home = new File(System.getProperty("user.home", "."));
        return new File[]{new File(home, "Pictures/Camera Roll"), new File(home, "OneDrive/Pictures/Camera Roll"),
            new File(home, "Pictures"), new File(home, "OneDrive/Pictures")};
    }

    static File cameraFolder() {
        for (File f : cameraFolders()) if (f.isDirectory()) return f;
        return new File(System.getProperty("user.home", "."));
    }

    /** The newest photo in the camera folders if it was taken within maxAgeMs, otherwise null. */
    static File newestPhoto(long maxAgeMs) {
        File best = null;
        for (File dir : cameraFolders()) {
            File[] list = dir.listFiles((d, n) -> n.toLowerCase().matches(".*\\.(png|jpg|jpeg)"));
            if (list == null) continue;
            for (File f : list) if (best == null || f.lastModified() > best.lastModified()) best = f;
        }
        return best != null && System.currentTimeMillis() - best.lastModified() <= maxAgeMs ? best : null;
    }
}