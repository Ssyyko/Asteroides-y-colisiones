package com.mycompany.juegoespacial;

import javax.swing.*;
import java.awt.*;

public class ObjetoEspacial extends JLabel {
    private int y;
    private final String tipo;
    private Timer temporizador;
    private boolean activo = true;

    public ObjetoEspacial(String tipo, Color color, int x, int y) {
        this.tipo = tipo;
        this.y = y;
        setBounds(x, y, 40, 40);
        setOpaque(false);
        setForeground(color);

        temporizador = new Timer(50, e -> mover());
        temporizador.start();
    }

    private void mover() {
        if (!activo) return;
        y += 2;
        setLocation(getX(), y);
        if (getParent() != null && y > getParent().getHeight()) {
            y = -40;
            setLocation((int)(Math.random() * (getParent().getWidth() - 40)), y);
        }
    }

    public void detener() {
        temporizador.stop();
    }

    public boolean estaActivo() {
        return activo;
    }

    public void desactivar() {
        this.activo = false;
        setVisible(false);
        detener();
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getForeground());
        if ("estrella".equals(tipo)) {
            g.fillOval(5, 5, 30, 30);
        } else if ("planeta".equals(tipo)) {
            g.fillRect(5, 5, 30, 30);
        } else {
            int[] xPoints = {20, 30, 35, 25, 15, 5, 10};
            int[] yPoints = {5, 15, 30, 35, 30, 20, 10};
            g.fillPolygon(xPoints, yPoints, 7);
        }
    }
}