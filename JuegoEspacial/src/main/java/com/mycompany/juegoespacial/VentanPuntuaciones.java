package com.mycompany.juegoespacial;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VentanPuntuaciones extends JDialog {
    public VentanPuntuaciones(JFrame parent, String archivo) {
        super(parent, "Puntuaciones", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaTexto.setBackground(new Color(30, 30, 50));
        areaTexto.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            StringBuilder contenido = new StringBuilder();
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            areaTexto.setText(contenido.toString());
        } catch (IOException e) {
            areaTexto.setText("No hay puntuaciones guardadas aún.");
        }

        BotonSimple btnCerrar = new BotonSimple("CERRAR", new Color(120, 0, 0));
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);

        setVisible(true);
    }
}