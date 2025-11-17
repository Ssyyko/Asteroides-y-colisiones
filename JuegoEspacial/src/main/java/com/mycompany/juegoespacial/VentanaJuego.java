package com.mycompany.juegoespacial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VentanaJuego extends JFrame implements KeyListener {
    private JPanel panelMenu, panelJuego;
    private JLabel lblPuntos, lblTiempo, lblInfo;
    private CampoTexto campoNombre;
    private BotonSimple btnJugar;
    private NaveEspacial nave;
    private int puntos = 0;
    private int tiempoRestante = 30;
    private Timer timerJuego, timerAcciones;
    private List<ObjetoEspacial> objetos;
    private boolean juegoActivo = false;
    private final String ARCHIVO_PUNTUACIONES = "puntuaciones.txt";

    public VentanaJuego() {
        setTitle("Juego Espacial Por Gabriel Palomo");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(10, 10, 40));
        objetos = new ArrayList<>();

        crearMenu();
        crearPanelMenu();
        crearPanelJuego();

        addKeyListener(this);
        setFocusable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setForeground(Color.WHITE);
        
        ItemMenuPersonalizado itemInstrucciones = new ItemMenuPersonalizado("Instrucciones");
        itemInstrucciones.addActionListener(e -> mostrarInstrucciones());
        
        ItemMenuPersonalizado itemSalir = new ItemMenuPersonalizado("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        
        menuArchivo.add(itemInstrucciones);
        menuArchivo.add(itemSalir);
        
        JMenu menuPuntuaciones = new JMenu("Puntuaciones");
        menuPuntuaciones.setForeground(Color.WHITE);
        
        ItemMenuPersonalizado itemVerPuntuaciones = new ItemMenuPersonalizado("Ver Puntuaciones");
        itemVerPuntuaciones.addActionListener(e -> new VentanPuntuaciones(this, ARCHIVO_PUNTUACIONES));
        
        menuPuntuaciones.add(itemVerPuntuaciones);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuPuntuaciones);
        setJMenuBar(menuBar);
    }

    private void mostrarInstrucciones() {
        String instrucciones = "<html><h2>Instrucciones del Juego</h2>"
                + "<p><b>Objetivo:</b> Pilota tu nave espacial y sobrevive. "
                + "Captura estrellas y planetas para sumar puntos, pero ¡esquiva los asteroides!</p>"
                + "<p><b>Controles:</b><br>"
                + "- Flechas del Teclado (↑ ↓ ← →): Mueven tu nave.<br>"
                + "- ESC: Vuelve al menú principal.</p>"
                + "<p><b>Sistema de Puntos:</b><br>"
                + "- Estrella Amarilla: +10 puntos.<br>"
                + "- Planeta Azul: +20 puntos.<br>"
                + "- Asteroide Naranja: Destruye tu nave.</p></html>";
        
        JOptionPane.showMessageDialog(this, instrucciones, "Cómo Jugar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void crearPanelMenu() {
        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBounds(0, 0, 700, 500);
        panelMenu.setBackground(new Color(10, 10, 40));

        JLabel lblTitulo = new JLabel("ASTEROIDES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.CYAN);
        lblTitulo.setBounds(180, 100, 350, 50);
        panelMenu.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(200, 200, 100, 25);
        panelMenu.add(lblNombre);

        lblInfo = new JLabel("Caracteres: 0");
        lblInfo.setForeground(Color.YELLOW);
        lblInfo.setBounds(450, 230, 150, 20);
        panelMenu.add(lblInfo);

        campoNombre = new CampoTexto(lblInfo);
        campoNombre.setBounds(300, 200, 200, 30);
        panelMenu.add(campoNombre);

        btnJugar = new BotonSimple("JUGAR", new Color(0, 120, 0));
        btnJugar.setBounds(250, 280, 200, 40);
        btnJugar.addActionListener(e -> {
            if (campoNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe tu nombre");
            } else {
                mostrarPanelJuego();
            }
        });
        panelMenu.add(btnJugar);

        add(panelMenu);
    }

    private void crearPanelJuego() {
        panelJuego = new JPanel();
        panelJuego.setLayout(null);
        panelJuego.setBounds(0, 0, 700, 500);
        panelJuego.setBackground(new Color(10, 10, 40));

        JPanel panelInfo = new JPanel(new FlowLayout());
        panelInfo.setBounds(0, 0, 700, 60);
        panelInfo.setBackground(new Color(20, 20, 60));

        lblPuntos = new JLabel("Puntos: 0");
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 18));
        lblPuntos.setForeground(Color.YELLOW);
        panelInfo.add(lblPuntos);

        lblTiempo = new JLabel("Tiempo: 30s");
        lblTiempo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTiempo.setForeground(Color.RED);
        panelInfo.add(lblTiempo);
        
        panelJuego.add(panelInfo);
        add(panelJuego);
        panelJuego.setVisible(false);
    }

    private void mostrarPanelMenu() {
        panelJuego.setVisible(false);
        panelMenu.setVisible(true);
        detenerJuego();
    }

    private void mostrarPanelJuego() {
        panelMenu.setVisible(false);
        panelJuego.setVisible(true);
        iniciarJuego();
        requestFocusInWindow();
    }

    private void iniciarJuego() {
        if (juegoActivo) return;
        juegoActivo = true;
        puntos = 0;
        tiempoRestante = 30;
        lblPuntos.setText("Puntos: 0");
        lblTiempo.setText("Tiempo: 30s");
        
        limpiarObjetos();

        nave = new NaveEspacial(330, 400);
        panelJuego.add(nave);

        String[] tipos = {"estrella", "planeta", "asteroide"};
        Color[] colores = {Color.YELLOW, Color.BLUE, Color.ORANGE};
        for (int i = 0; i < 8; i++) {
            String tipo = tipos[i % 3];
            Color color = colores[i % 3];
            int x = (int) (Math.random() * 650);
            ObjetoEspacial obj = new ObjetoEspacial(tipo, color, x, -50 - (i * 50));
            objetos.add(obj);
            panelJuego.add(obj);
        }

        timerJuego = new Timer(1000, e -> {
            tiempoRestante--;
            lblTiempo.setText("Tiempo: " + tiempoRestante + "s");
            if (tiempoRestante <= 0) {
                finalizarJuego("¡Tiempo agotado!");
            }
        });
        timerJuego.start();

        timerAcciones = new Timer(50, e -> detectarColisiones());
        timerAcciones.start();
    }
    
    private void detectarColisiones() {
        Rectangle rectNave = nave.getBounds();

        for (ObjetoEspacial obj : objetos) {
            if (obj.estaActivo() && rectNave.intersects(obj.getBounds())) {
                String tipo = obj.getTipo();
                obj.desactivar();

                if ("asteroide".equals(tipo)) {
                    finalizarJuego("¡Tu nave ha sido destruida!");
                    return;
                } else {
                    if ("estrella".equals(tipo)) {
                        puntos += 10;
                    } else if ("planeta".equals(tipo)) {
                        puntos += 20;
                    }
                    lblPuntos.setText("Puntos: " + puntos);
                }
            }
        }
    }

    private void detenerJuego() {
        juegoActivo = false;
        if (timerJuego != null) timerJuego.stop();
        if (timerAcciones != null) timerAcciones.stop();
        limpiarObjetos();
    }

    private void finalizarJuego(String mensaje) {
        detenerJuego();
        guardarPuntuacion(campoNombre.getText(), puntos);
        JOptionPane.showMessageDialog(this, mensaje + "\n" + campoNombre.getText() + ", tu puntuación es: " + puntos);
        mostrarPanelMenu();
    }

    private void guardarPuntuacion(String nombre, int puntuacion) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PUNTUACIONES, true))) {
            writer.write(nombre + ": " + puntuacion);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar la puntuación: " + e.getMessage());
        }
    }

    private void limpiarObjetos() {
        for (ObjetoEspacial obj : objetos) {
            obj.detener();
            panelJuego.remove(obj);
        }
        objetos.clear();
        if (nave != null) {
            panelJuego.remove(nave);
            nave = null;
        }
        panelJuego.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!juegoActivo || nave == null) return;

        int velocidad = 15;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                nave.mover(-velocidad, 0, panelJuego.getWidth(), panelJuego.getHeight());
                break;
            case KeyEvent.VK_RIGHT:
                nave.mover(velocidad, 0, panelJuego.getWidth(), panelJuego.getHeight());
                break;
            case KeyEvent.VK_UP:
                nave.mover(0, -velocidad, panelJuego.getWidth(), panelJuego.getHeight());
                break;
            case KeyEvent.VK_DOWN:
                nave.mover(0, velocidad, panelJuego.getWidth(), panelJuego.getHeight());
                break;
            case KeyEvent.VK_ESCAPE:
                if (panelJuego.isVisible()) {
                    mostrarPanelMenu();
                }
                break;
        }
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}