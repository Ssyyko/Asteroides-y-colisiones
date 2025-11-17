package com.mycompany.juegoespacial;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import java.awt.Font;

public class BotonSimple extends JButton {
    private Color colorOriginal;

    public BotonSimple(String texto, Color color) {
        super(texto);
        this.colorOriginal = color;
        setBackground(color);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorOriginal.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorOriginal);
            }
        });
    }
}