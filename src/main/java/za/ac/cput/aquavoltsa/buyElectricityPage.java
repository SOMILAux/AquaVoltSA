/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.aquavoltsa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class buyElectricityPage extends JFrame {

    JLabel logoLabel, curveSpaceLabel;
    JLabel meterNumberLabel, selectAmountLabel, separator;
    JPanel contentPanel, footerPanel;
    JTextField otherAmount, meterNumber;
    JToggleButton firstAmount, secondAmount, thirdAmount, customAmount;
    ButtonGroup groupToggleButtons;
    JButton continueBtn, backBtn;
    ImageIcon logo, curveSpace;

    public buyElectricityPage() {

        super();
        this.setTitle("Buy Electricity");
        this.setSize(425, 917);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        //reshaping AquaVoltSA's Logo
        logo = new ImageIcon("firstLogo.png");
        Image logoImage = logo.getImage();
        Image resizedLogo = logoImage.getScaledInstance(450, 252, Image.SCALE_SMOOTH);
        ImageIcon AquaLogo = new ImageIcon(resizedLogo);

        //reshaping the white circular space under the logo
        curveSpace = new ImageIcon("gray-circle.png");
        Image curve = curveSpace.getImage();
        Image resizedCurveSpace = curve.getScaledInstance(484, 83, Image.SCALE_SMOOTH);
        ImageIcon newCurveSpace = new ImageIcon(resizedCurveSpace);

        //Adding the logo to the label
        logoLabel = new JLabel(AquaLogo);
        logoLabel.setPreferredSize(new Dimension(450, 252));

        contentPanel = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                Color firstElectricityColor = Color.decode("#FFB300");
                Color secondElectricityColor = Color.decode("#FFFFFF");

                GradientPaint gp = new GradientPaint(0, 0, firstElectricityColor, getWidth(), getHeight(), secondElectricityColor);

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

        };
        contentPanel.setLayout(null);

        //Adding the circle to the Label
        curveSpaceLabel = new JLabel(newCurveSpace);
        curveSpaceLabel.setBounds(0, 0, 484, 83);
        curveSpaceLabel.setBorder(BorderFactory.createEmptyBorder(-120, -70, 0, 0)); //Moves the circle to the top and to the left
        //curveSpaceLabel.setPreferredSize(new Dimension(484, 83));

        //Initialising, editing, and adding the components to panels
        meterNumberLabel = new JLabel("Meter Number");
        meterNumberLabel.setBounds(50, 30, 150, 30);
        meterNumberLabel.setFont(new Font("serif", 10, 20));

        selectAmountLabel = new JLabel("Select Amount");
        selectAmountLabel.setBounds(50, 200, 150, 30);
        selectAmountLabel.setFont(new Font("serif", 10, 20));

        separator = new JLabel("________________________________________________________________________________");
        separator.setBounds(0, 180, 425, 1);
        separator.setOpaque(true);
        separator.setBackground(Color.WHITE);

        footerPanel = new JPanel();

        meterNumber = new JTextField("   Enter your meter number");
        meterNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent gained) {
                String meter = meterNumber.getText();
                if (meter.equals("   Enter your meter number")) {
                    meterNumber.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent lost) {
                String meter = meterNumber.getText();
                if (meter.isEmpty()) {
                    meterNumber.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Enter your meter number", "Meter number needed", JOptionPane.OK_OPTION);
                } else {
                    meterNumber.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        });
        meterNumber.setBounds(40, 80, 330, 50);

        otherAmount = new JTextField("  R | Amount");
        otherAmount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent gained) {
                String amount = otherAmount.getText();
                customAmount.setSelected(true);

                if (amount.equals("  R | Amount")) {
                    otherAmount.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent lost) {
                String amount = otherAmount.getText();
                if (amount.isEmpty()) {
                    otherAmount.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    JOptionPane.showMessageDialog(null, "Enter the Amount you want to purchase", "Amount needed", JOptionPane.OK_OPTION);
                } else {
                    otherAmount.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        });
        otherAmount.setBounds(40, 420, 330, 50);

        //Setting and displaying the ToggleButtons
        firstAmount = new JToggleButton("R50");
        firstAmount.setContentAreaFilled(true);
        firstAmount.setBackground(Color.WHITE);
        firstAmount.setFocusPainted(false);
        firstAmount.setBorderPainted(false);
        firstAmount.setBounds(40, 240, 150, 60);

        secondAmount = new JToggleButton("R100");
        secondAmount.setFocusPainted(false);
        secondAmount.setBorderPainted(false);
        secondAmount.setBackground(Color.WHITE);
        secondAmount.setBounds(220, 240, 150, 60);

        thirdAmount = new JToggleButton("R150");
        thirdAmount.setFocusPainted(false);
        thirdAmount.setBorderPainted(false);
        thirdAmount.setBackground(Color.WHITE);
        thirdAmount.setBounds(40, 330, 150, 60);

        customAmount = new JToggleButton("OWN AMOUNT");
        customAmount.setFocusPainted(false);
        customAmount.setBorderPainted(false);
        customAmount.setBackground(Color.WHITE);
        customAmount.setBounds(220, 330, 150, 60);
        //End

        //Adding actionListener, Disabling, and Editing the textField if the user clicked one of the ToggleButton
        firstAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent item) {
                if (firstAmount.isSelected()) {
                    otherAmount.setText("  R | 50");
                    //otherAmount.setEditable(false);
                    otherAmount.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        });

        secondAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent item) {
                if (secondAmount.isSelected()) {
                    otherAmount.setText("  R | 100");
                    //otherAmount.setEditable(false);
                    otherAmount.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        });

        thirdAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent item) {
                if (thirdAmount.isSelected()) {
                    otherAmount.setText("  R | 150");
                    //otherAmount.setEditable(false);
                    otherAmount.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        });

        customAmount.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent item) {
                if (customAmount.isSelected()) {
                    otherAmount.setText("  R | Enter Custom amount");
                    //otherAmount.setEditable(false);
                    otherAmount.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                }
            }
        });
        //End

        //Initialising and setting the display of the continue button
        continueBtn = new JButton("CONTINUE", new ImageIcon("arrow.png"));
        continueBtn.setFocusPainted(false);
        continueBtn.setBorderPainted(false);
        continueBtn.setBackground(Color.decode("#FFB300"));
        continueBtn.setBounds(40, 490, 330, 40);
        continueBtn.setForeground(Color.WHITE);
        //End

        //Initialising and setting the display of the Back button
        backBtn = new JButton("BACK");
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setBackground(Color.decode("#FFB300"));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBounds(40, 540, 330, 40);
        //End

        //adding togglebuttons to one group
        groupToggleButtons = new ButtonGroup();
        groupToggleButtons.add(firstAmount);
        groupToggleButtons.add(secondAmount);
        groupToggleButtons.add(thirdAmount);
        groupToggleButtons.add(customAmount);
        //End

        contentPanel.add(curveSpaceLabel);
        contentPanel.add(meterNumberLabel);
        contentPanel.add(meterNumber);
        contentPanel.add(separator);
        contentPanel.add(selectAmountLabel);
        contentPanel.add(firstAmount);
        contentPanel.add(secondAmount);
        contentPanel.add(thirdAmount);
        contentPanel.add(customAmount);
        contentPanel.add(otherAmount);
        contentPanel.add(continueBtn);
        contentPanel.add(backBtn);

        add(logoLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

    }

}
