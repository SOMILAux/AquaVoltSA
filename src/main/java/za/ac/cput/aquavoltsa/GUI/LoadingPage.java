
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.net.URL;
import javax.swing.*;

// 240135717 | buhle SIhlahla

public class LoadingPage extends JFrame {

    
   // private static final int WIDTH = 425;
   // private static final int HEIGHT = 917;

    // Interface colours
    private final Color BACKGROUND = Color.WHITE;
    private final Color SPINNER_COLOR = new Color(126, 91, 190);
    private final Color SPINNER_BACKGROUND = new Color(235, 226, 250);
    private final Color TEXT_COLOR = Color.BLACK;

    private SpinnerPanel spinner;

    private Timer loadingTimer;

    public LoadingPage() {

        
        setTitle("AquaVolt SA");

        setSize(425, 917);
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        setUndecorated(true);

        // Main panel
        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setLayout(null);

        

        JLabel logoLabel = new JLabel();

        
        URL logoURL = getClass().getResource("secondLogo.png");

        if (logoURL != null) {

            ImageIcon originalIcon = new ImageIcon(logoURL);

            Image originalImage = originalIcon.getImage();

            Image scaledImage = originalImage.getScaledInstance(
                    175,
                    175,
                    Image.SCALE_SMOOTH);

            logoLabel.setIcon(new ImageIcon(scaledImage));

        } else {

            
            logoLabel.setText("AquaVolt SA");

            logoLabel.setFont(
                    new Font("Arial", Font.BOLD, 24));

            logoLabel.setForeground(
                    new Color(45, 140, 190));
        }

        
        logoLabel.setBounds(
                125,
                270,
                175,
                175);

        logoLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        logoLabel.setVerticalAlignment(
                SwingConstants.CENTER);

        

        spinner = new SpinnerPanel();

        spinner.setBounds(
                190,
                690,
                45,
                45);

        

        JLabel loadingLabel = new JLabel("Loading...");

        loadingLabel.setFont(
                new Font("Arial", Font.PLAIN, 14));

        loadingLabel.setForeground(TEXT_COLOR);

        loadingLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        loadingLabel.setBounds(
                130,
                740,
                165,
                25);

        // ---------------------------------
        // ADD COMPONENTS
        // ---------------------------------

        mainPanel.add(logoLabel);

        mainPanel.add(spinner);

        mainPanel.add(loadingLabel);

        // Add main panel to JFrame
        add(mainPanel);

        // Display loading interface
        setVisible(true);

        
        startLoadingAnimation();
    }



    private void startLoadingAnimation() {

        loadingTimer = new Timer(30, e -> {

            spinner.rotate();

            spinner.repaint();

        });

        loadingTimer.start();
    }

    private class SpinnerPanel extends JPanel {

        private int angle = 0;

        public SpinnerPanel() {

            setOpaque(false);
        }

        
        public void rotate() {

            angle += 12;

            if (angle >= 360) {

                angle = 0;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            
            int size = 30;

            int x = (getWidth() - size) / 2;

            int y = (getHeight() - size) / 2;

            
            g2.setStroke(
                    new BasicStroke(
                            3.5f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND));

            g2.setColor(SPINNER_BACKGROUND);

            g2.drawOval(
                    x,
                    y,
                    size,
                    size);

            
            g2.setColor(SPINNER_COLOR);

            Arc2D arc = new Arc2D.Double(
                    x,
                    y,
                    size,
                    size,
                    angle,
                    100,
                    Arc2D.OPEN);

            g2.draw(arc);

            g2.dispose();
        }
    }

    
}

