package com.onlineexam.utils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * CustomButton - Modern rounded button with hover effects
 */
public class CustomButton extends JButton {

    private Color hoverColor;
    private Color pressedColor;
    private Color normalColor;

    public CustomButton(String text, Color normalColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = lighten(normalColor, 0.1f);
        this.pressedColor = darken(normalColor, 0.1f);

        setFocusable(false);
        setBackground(normalColor);
        setForeground(UIConstants.WHITE);
        setFont(UIConstants.FONT_SUBHEADING);
        setPreferredSize(new Dimension(150, 42));
        setMargin(new Insets(8, 18, 8, 18));
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.CORNER_RADIUS, UIConstants.CORNER_RADIUS);
        g2.dispose();

        super.paintComponent(g);
    }

    private Color lighten(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() + (255 - color.getRed()) * factor));
        int g = Math.min(255, (int) (color.getGreen() + (255 - color.getGreen()) * factor));
        int b = Math.min(255, (int) (color.getBlue() + (255 - color.getBlue()) * factor));
        return new Color(r, g, b);
    }

    private Color darken(Color color, float factor) {
        int r = Math.max(0, (int) (color.getRed() * (1 - factor)));
        int g = Math.max(0, (int) (color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int) (color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }
}
