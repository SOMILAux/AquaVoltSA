/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.aquavoltsa.GUI;

import java.awt.*;
//import java.awt.geom.Path2D;
import javax.swing.*;

/**
 *
 * @author Shuma
 */
public class GaugePanel extends JPanel{

   private final String heading;
   private final Color gaugeColor;
   private final boolean water;
    private final String line1;
   private final String line2, line3, line4, line5;
   
   GaugePanel(String heading, Color gaugeColor, boolean water, String line1, String line2, String line3, String line4, String line5){
   this.heading = heading;
   this.gaugeColor = gaugeColor;
   this.water = water;
   this.line1 = line1;
   this.line2 = line2;
   this.line3 = line3;
   this.line4 = line4;
   this.line5 = line5;
   setPreferredSize(new Dimension(285, 238));
   setMinimumSize(new Dimension(285, 238));
   setMaximumSize(new Dimension(285, 238));
   setBackground(Color.WHITE);
   }
   @Override
   protected void paintComponent(Graphics g){
       super.paintComponent(g);
       Graphics2D g2 =(Graphics2D)g.create();
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      
       g2.setFont(new Font("Arial", Font.BOLD,22));
       g2.setColor(Color.BLACK);
       FontMetrics fm = g2.getFontMetrics();
       int titleX =(getWidth()- fm.stringWidth(heading))/2;
       g2.drawString(heading, titleX, 24);
       g2.setStroke(new BasicStroke(1));
       g2.drawLine(titleX, 28, titleX + fm.stringWidth(heading),28);
       
       int x = 28;
       int y = 40;
       int diameter = 192;
       g2.setColor(new Color(190, 190, 190));
       g2.fillOval(x, y, diameter, diameter);
       
       g2.setColor(gaugeColor);
       g2.fillArc(x, y, diameter, diameter, 90, water ? 280: 205);
       g2.setColor(Color.WHITE);
       g2.fillOval(x + 20, y + 20, diameter - 40, diameter - 40);
       
       /* if (water) {
       drawWaterDrop(g2, x + 65, y + 41);
       }else{
       drawLightning(g2, x + 73, y + 46);
       }*/
       g2.setFont(new Font("Arial",Font.PLAIN,11));
       g2.setColor(Color.BLACK);
       
       int textX = 226;
       g2.drawString(line1, textX,y +58);
       g2.drawString(line2, textX,y +72);
       g2.drawString(line3, textX,y +98);
       g2.drawString(line4, textX,y +112);
       g2.drawString(line5, textX,y +126);
       g2.dispose();
  }
   /* private void drawWaterDrop(Graphics2D g2, int x, int y){
        Path2D drop = new Path2D.Double();
        drop.moveTo(x + 30,y);
        drop.curveTo(x + 22, y + 30, x + 5, y + 42, x + 5, y + 65);
        drop.curveTo(x + 5, y + 85, x + 17, y + 103 , x + 30, y + 103);
        drop.curveTo(x + 43, y + 103, x + 55, y + 85, x + 55, y + 65);
        drop.curveTo(x + 55, y + 42, x +38, y + 30, x + 30, y);
        drop.closePath();
        
        g2.setColor(new Color(0,180,90));
        g2.fill(drop);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.draw(drop);
    }*/

   
  /*  private void drawLightning(Graphics2D g2, int x, int y){
        
     int[] px = {x +10, x + 38, x + 40, x + 25, x + 38, x + 35, x + 17, x + 22};
     int[] py = {y, y, y + 28, y + 28, y + 65, y + 65, y + 32, y + 32};
     Polygon bolt = new Polygon(px, py, px.length);
     g2.setColor(new Color(225,195,0));
     g2.fillPolygon(bolt);
     g2.setColor(Color.BLACK);
     g2.setStroke(new BasicStroke(2));
     g2.drawPolygon(bolt);
    
    }*/
}
   
    /*    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public interface IconDrawer{
        void draw(Graphics2D g2, int size);
    }
    private final int size;
    private final double percent;
    private final Color activeColor;
    private final Color trackColor;
    private final IconDrawer iconDrawer;
    
    public GaugePanel( int size, double percent, Color activeColor, Color trackColor,IconDrawer iconDrawer){
    this.size = size;
    this.percent = percent;
    this.activeColor = activeColor;
    this.trackColor = trackColor;
    this.iconDrawer  = iconDrawer;
    setPreferredSize(new Dimension(size,size));
    
}
            protected void painComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
    int stroke = size /8;
    int arcSize = size - stroke;
    int offset = stroke/ 2;
    
    g2.setStroke(new BasicStroke(stroke));
    g2.setColor(trackColor);
    g2.drawArc(offset, offset, arcSize, arcSize, 0, 360);
    
    g2.setColor(activeColor);
    int degrees = (int) Math.round(360*percent);
    g2.drawArc(offset, offset, arcSize, arcSize, 90, degrees);
    
    int innerSize = (int) (size*0.55);
    int innerOffset = (size - innerSize)/ 2;
    g2.translate(innerOffset, innerOffset);
    iconDrawer.draw(g2, innerSize);
    
    g2.dispose();
            }
    public class GaugeIcons{
        public static void drawDroplet(Graphics2D g2, int size, Color color){
            int w = size, h = size;
            Path2D drop = new Path2D.Double();
        }
    }
    Color waterGreen = new Color(46,160,67);
    Color electricYellow = new Color(247,181,0);
    Color trackGray = new Color(200,200,200);
    
    GaugePanel waterGuage = new GaugePanel(150, 0.65, waterGreen, trackGray, (g2,size)-> GaugeIcons.drawDroplet(g2, size,waterGreen));
}
        
   /* public WaterGauge(){
        setPreferredSize(new Dimension(350,300));
        setBackground(Color.WHITE);
    }
    @Override protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    
    g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int centerX = 150;
    int centerY = 150;
    
    g2.setColor(Color.LIGHT_GRAY);
    g2.setStroke(new BasicStroke(25));
    g2.drawArc(40,40,220,220,0,360);
    g2.setColor(new Color(0,180,80));
    g2.drawArc(40, 40, 220, 220, 90, -270);
   /*g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(2));
    g2.drawOval(70,70,160,160);
    
    Polygon drop = new Polygon();
    drop.addPoint(150,95);
    drop.addPoint(120,150);
    drop.addPoint(120,175);
    drop.addPoint(130,195);
    drop.addPoint(150,205);
    drop.addPoint(170,195);
    drop.addPoint(180,175);
    drop.addPoint(180,150);
    
    g2.setColor(Color.WHITE);
    g2.fillPolygon(drop);
    
    g2.setColor(Color.BLACK);
    g2.drawPolygon(drop);
    
    g2.setColor(new Color(0,180,80));
    g2.fillOval(120,140,60,65);
    
    g2.setColor(Color.BLACK);
    g2.setFont(new Font("Arial", Font.BOLD,16));
    g2.drawString("1 Average",280,115);
    g2.drawString("Usage",280,135);
    g2.drawString("Used Today",280,190);

}*/



