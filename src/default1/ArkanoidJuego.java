package default1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FondoPanel extends JPanel {
    private Image imagenFondo;

    public FondoPanel(Image imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}

public class ArkanoidJuego extends JFrame {

    public ArkanoidJuego() {
        setPreferredSize(new Dimension(700, 600));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cargar la imagen de fondo desde un archivo (ajusta la ruta de la imagen según su ubicación)
        ImageIcon imagenFondo = new ImageIcon("C:\\Users\\usuario\\Downloads\\fondo.jpeg");

        FondoPanel panelConFondo = new FondoPanel(imagenFondo.getImage());
        panelConFondo.setLayout(new GridBagLayout());

        // Crear los botones naranjas y establecer un tamaño personalizado
        JButton botonJugar = new JButton("JUGAR");
        JButton botonInformacion = new JButton("AYUDA");

        // Establecer el tamaño personalizado para los botones
        Dimension botonSize = new Dimension(150, 50); // Ancho: 150, Alto: 50
        botonJugar.setPreferredSize(botonSize);
        botonInformacion.setPreferredSize(botonSize);

        // Establecer el color de fondo de los botones en naranja
        botonJugar.setBackground(Color.ORANGE);
        botonInformacion.setBackground(Color.ORANGE);

        // Agregar ActionListener al botón JUGAR para abrir la ventana del nivel 1
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones cuando se hace clic en el botón JUGAR
                JFrame nivel1Frame = new JFrame("NIVEL 1");
                nivel1Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nivel1Frame.add(new nivel1()); // Crea una nueva instancia de nivel1
                nivel1Frame.pack();
                nivel1Frame.setResizable(false);
                nivel1Frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
                nivel1Frame.setVisible(true);

                // Cierra la ventana actual (Inicio)
                dispose();
            }
        });
        botonInformacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones cuando se hace clic en el botón INFORMACION
                String informacion = "<html>" +
                                     "<h1>Acerca del Juego</h1>" +
                                     "<p><b>Reglas del Juego:</b></p>" +
                                     "<ul style='list-style-type: none; padding: 0; text-align: left;'>" +
                                     "<li>Tienes <b>10 vidas</b> en cada nivel.</li>" +
                                     "<li>La cantidad de ladrillos por fila aumenta en cada nivel.</li>" +
                                     "</ul>" +
                                     "<p><b>Tiempo por nivel:</b></p>" +
                                     "<ul>" +
                                     "<li>Nivel 1: <b>60 segundos</b>.</li>" +
                                     "<li>Nivel 2: <b>80 segundos</b>.</li>" +
                                     "<li>Nivel 3: <b>100 segundos</b>.</li>" +
                                     "</ul>" +
                                     "<ul>" +
                                     "<li>Usa las teclas <b>izquierda</b> y <b>derecha</b> para mover la paleta.</li>" +
                                     "<li>Golpea la bola con la paleta para romper los ladrillos.</li>" +
                                     "<li>Evita que la bola toque el suelo para no perder vidas.</li>" +
                                     "<li>Gana puntos al romper los ladrillos.</li>" +
                                     "</ul>" +
                                     "<h1>¡Diviértete jugando!</h1></html>";

                // Crea un JLabel con el texto formateado en HTML
                JLabel label = new JLabel(informacion);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.TOP);
                label.setFont(new Font("Arial", Font.PLAIN, 16));

                // Crea un JPanel con el JLabel para personalizar el fondo y el borde
                JPanel panelFondo = new JPanel(new BorderLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        // Dibuja un fondo naranja
                        g.setColor(new Color(255, 165, 0)); // Color naranja (RGB: 255, 165, 0)
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };

                // Establece un borde para el panel
                panelFondo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                // Agrega el JLabel al panel con fondo
                panelFondo.add(label, BorderLayout.CENTER);

                // Muestra el panel con fondo en un cuadro de diálogo
                JOptionPane.showMessageDialog(ArkanoidJuego.this, panelFondo, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
            }
        });



        // Configurar GridBagConstraints para centrar los botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes

        // Agregar los botones al panel con fondo
        panelConFondo.add(botonJugar, gbc);
        gbc.gridy++;
        panelConFondo.add(botonInformacion, gbc);

        // Agregar el panel con fondo al centro del JFrame
        getContentPane().add(panelConFondo, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	ArkanoidJuego inicioFrame = new ArkanoidJuego();
            inicioFrame.setTitle("Inicio");
            inicioFrame.setVisible(true);
        });
    }
}
