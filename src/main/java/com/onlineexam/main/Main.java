package com.onlineexam.main;

import com.onlineexam.ui.LoginForm;
import javax.swing.*;

/**
 * Main - Application entry point
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Set modern Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Customize UI defaults for modern appearance
            UIManager.put("Button.background", new java.awt.Color(41, 128, 185));
            UIManager.put("Button.foreground", java.awt.Color.WHITE);
            UIManager.put("Panel.background", new java.awt.Color(236, 240, 241));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();
            login.setVisible(true);
        });
    }
}
