package com.onlineexam.utils;

import java.awt.*;

/**
 * UIConstants - Centralized color scheme, fonts, and styling constants
 */
public class UIConstants {

    // Modern Color Palette
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Professional blue
    public static final Color PRIMARY_DARK = new Color(25, 77, 111);         // Darker blue
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219);     // Light blue
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);       // Green
    public static final Color WARNING_COLOR = new Color(241, 196, 15);       // Orange
    public static final Color DANGER_COLOR = new Color(231, 76, 60);         // Red
    public static final Color INFO_COLOR = new Color(52, 73, 94);            // Dark grey
    public static final Color LIGHT_GREY = new Color(236, 240, 241);         // Light grey
    public static final Color DARK_GREY = new Color(44, 62, 80);             // Dark grey
    public static final Color WHITE = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(44, 62, 80);            // Dark text
    public static final Color BORDER_COLOR = new Color(189, 195, 199);       // Light border

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    // Insets and Spacing
    public static final int PADDING_SMALL = 5;
    public static final int PADDING_MEDIUM = 10;
    public static final int PADDING_LARGE = 15;
    public static final int PADDING_XLARGE = 20;

    // Dimensions
    public static final Dimension BUTTON_SIZE = new Dimension(100, 35);
    public static final Dimension LARGE_BUTTON_SIZE = new Dimension(140, 40);
    public static final Dimension TEXT_FIELD_SIZE = new Dimension(250, 35);

    // Corner radius for components
    public static final int CORNER_RADIUS = 8;
    public static final int BORDER_WIDTH = 2;
}
