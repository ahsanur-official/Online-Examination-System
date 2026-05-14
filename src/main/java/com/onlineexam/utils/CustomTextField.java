package com.onlineexam.utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

/**
 * CustomTextField - Modern rounded text field with border
 */
public class CustomTextField extends JTextField {

    private static final int PADDING = 10;

    public CustomTextField() {
        this("");
    }

    public CustomTextField(String text) {
        super(text);
        setPreferredSize(UIConstants.TEXT_FIELD_SIZE);
        setFont(UIConstants.FONT_NORMAL);
        setForeground(UIConstants.TEXT_COLOR);
        setCaretColor(UIConstants.PRIMARY_COLOR);
        setBorder(new RoundedBorder(UIConstants.PRIMARY_COLOR));
        setBackground(UIConstants.WHITE);
    }

    static class RoundedBorder extends AbstractBorder {

        private Color color;

        public RoundedBorder(Color color) {
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x, y, width - 1, height - 1, UIConstants.CORNER_RADIUS, UIConstants.CORNER_RADIUS);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(PADDING, PADDING, PADDING, PADDING);
        }
    }
}
