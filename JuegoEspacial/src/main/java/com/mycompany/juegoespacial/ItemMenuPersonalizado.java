package com.mycompany.juegoespacial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ItemMenuPersonalizado extends JMenuItem {
    public ItemMenuPersonalizado(String texto) {
        super(texto);
        setForeground(Color.WHITE);
        setBackground(new Color(50, 50, 80));
        setFont(new Font("Arial", Font.PLAIN, 14));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { setBackground(new Color(80, 80, 120)); }
            @Override
            public void mouseExited(MouseEvent e) { setBackground(new Color(50, 50, 80)); }
        });
    }
}