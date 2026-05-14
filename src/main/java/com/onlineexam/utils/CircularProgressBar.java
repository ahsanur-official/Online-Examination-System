package com.onlineexam.utils;

import java.awt.*;
import javax.swing.*;

/**
 * CircularProgressBar - Beautiful circular progress indicator
 */
public class CircularProgressBar extends JComponent {

    private int progress = 0;
    private int maximum = 100;
    private Color progressColor = UIConstants.PRIMARY_COLOR;
    private Color backgroundColor = UIConstants.LIGHT_GREY;

    public CircularProgressBar() {
        setPreferredSize(new Dimension(120, 120));
    }

    public void setProgress(int progress) {
        this.progress = Math.min(progress, maximum);
        repaint();
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 10;
        int x = (width - diameter) / 2;
        int y = (height - diameter) / 2;

        // Draw background circle
        g2.setColor(backgroundColor);
        g2.fillOval(x, y, diameter, diameter);

        // Draw progress arc
        g2.setColor(progressColor);
        g2.setStroke(new BasicStroke(8));
        int angle = (int) ((progress * 360) / maximum);
        g2.drawArc(x, y, diameter, diameter, 90, -angle);

        // Draw percentage text
        String text = progress + "%";
        g2.setColor(UIConstants.TEXT_COLOR);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = (height + fm.getAscent()) / 2 - 5;
        g2.drawString(text, textX, textY);

        g2.dispose();
    }
}
