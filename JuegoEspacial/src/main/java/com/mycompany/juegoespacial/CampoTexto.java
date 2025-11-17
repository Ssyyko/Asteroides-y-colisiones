package com.mycompany.juegoespacial;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CampoTexto extends JTextField {
    private JLabel lblInfo;
    private Color colorFoco = new Color(100, 100, 150);

    public CampoTexto(JLabel lblInfo) {
        super(15);
        this.lblInfo = lblInfo;
        setForeground(Color.WHITE);
        setBackground(new Color(50, 50, 80));
        setCaretColor(Color.CYAN);
        setFont(new Font("Arial", Font.PLAIN, 14));

        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarInfo(); }
            public void removeUpdate(DocumentEvent e) { actualizarInfo(); }
            public void changedUpdate(DocumentEvent e) { actualizarInfo(); }
        });
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(colorFoco);
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBackground(new Color(50, 50, 80));
            }
        });
    }
    
    private void actualizarInfo() {
        lblInfo.setText("Caracteres: " + getText().length());
    }
}