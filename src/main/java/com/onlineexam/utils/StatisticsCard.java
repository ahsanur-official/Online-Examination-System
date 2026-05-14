package com.onlineexam.utils;

import java.awt.*;
import javax.swing.*;

/**
 * StatisticsCard - Beautiful card component for displaying statistics
 */
public class StatisticsCard extends JPanel {

    private String title;
    private String value;
    private Color backgroundColor;
    private Color iconColor;

    public StatisticsCard(String title, String value, Color backgroundColor, Color iconColor) {
        this.title = title;
        this.value = value;
        this.backgroundColor = backgroundColor;
        this.iconColor = iconColor;

        setPreferredSize(new Dimension(200, 120));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded rectangle background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Draw title
        g2.setColor(UIConstants.WHITE);
        g2.setFont(UIConstants.FONT_NORMAL);
        g2.drawString(title, 15, 30);

        // Draw value
        g2.setColor(UIConstants.WHITE);
        g2.setFont(UIConstants.FONT_HEADING);
        g2.drawString(value, 15, 70);

        g2.dispose();
    }
}
