package com.mycompany.juegoespacial;

import javax.swing.*;
import java.awt.*;

public class NaveEspacial extends JLabel {
    public NaveEspacial(int x, int y) {
        setBounds(x, y, 40, 40);
        setOpaque(false);
    }

    public void mover(int dx, int dy, int panelAncho, int panelAlto) {
        int nuevaX = getX() + dx;
        int nuevaY = getY() + dy;

        if (nuevaX >= 0 && nuevaX <= panelAncho - getWidth()) {
            setLocation(nuevaX, getY());
        }
        if (nuevaY >= 80 && nuevaY <= panelAlto - getHeight()) {
            setLocation(getX(), nuevaY);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        int[] xPoints = {20, 5, 35};
        int[] yPoints = {5, 35, 35};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}