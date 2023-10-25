package default1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class nivel3 extends JPanel implements ActionListener, KeyListener {
    private int ancho = 500;
    private int alto = 600;
    private int anchoPaleta = 100;
    private int altoPaleta = 20;
    private int tamañoBola = 20;
    private int velocidadPaleta = 15;
    private int velocidadBolaX = 10;
    private int velocidadBolaY = 10;
    private int posicionJugadorX = ancho / 2 - anchoPaleta / 2;
    private int posicionJugadorY = alto - altoPaleta - 20;
    private int posicionBolaX = ancho / 2 - tamañoBola / 2;
    private int posicionBolaY = alto / 2 - tamañoBola / 2;
    private int vidas = 10;
    private int puntuacion = 0;
    private int tiempo = 0; // Contador de tiempo en segundos
    private Timer timerTiempo; // Timer para el contador de tiempo
    private ArrayList<Brick> bricks;
    int totalBricksPerRow = 8;
    
    private ImageIcon backgroundImage; // Variable para la imagen de fondo
    public class Brick {
        private int x;
        private int y;
        private int width;
        private int height;
        private boolean isVisible;

        public Brick(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.isVisible = true;
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, width, height);
        }

        public void draw(Graphics g) {
            if (isVisible) {
                g.setColor(new Color(0, 255, 0)); // Establece el color a verde (RGB: 0, 255, 0)
                g.fillRect(x, y, width, height); // Dibuja un rectángulo verde en lugar de marrón
            }
        }

        public boolean isVisible() {
            return isVisible;
        }

        public void setVisible(boolean visible) {
            isVisible = visible;
        }
public static int getWidth() {
	// TODO Auto-generated method stub
	return 0;
}
public static int getHeight() {
	// TODO Auto-generated method stub
	return 0;
}

public int getY() {
	// TODO Auto-generated method stub
	return 0;
}
    }
    public nivel3() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(ancho, alto));
        setFocusable(true);
        addKeyListener(this);

        backgroundImage = new ImageIcon(getClass().getResource("nivel3.jpeg"));

        bricks = new ArrayList<>();
        initializeBricks();

        Timer timer = new Timer(30, this);
        timer.start();

        // Inicializa el Timer para el contador de tiempo
        timerTiempo = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempo++;
                repaint();
            }
        });
        timerTiempo.start();
    }

    private void initializeBricks() {
        int totalBricksPerRow = 8;
        int initialX = 15;
        int initialY = 50;
        int brickWidth =  50;
        int brickHeight = 15;
        
        for (int i = 0; i < totalBricksPerRow; i++) {
            for (int j = 0; j < 3; j++) {
              
				int x = initialX + i * (brickWidth + 10);
             
				int y = initialY + j * (brickHeight + 10);
                Brick brick = new Brick(x, y, brickWidth, brickHeight);
                bricks.add(brick);
            }
          
        }
      }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen de fondo que abarca todo el panel
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Dibujar los ladrillos
      
        for (Brick brick : bricks) {
            brick.draw(g);
        }

        // Dibujar la paleta y la bola
        g.setColor(Color.WHITE);
        g.fillRect(posicionJugadorX, posicionJugadorY, anchoPaleta, altoPaleta);
        g.setColor(Color.RED);
        g.fillOval(posicionBolaX, posicionBolaY, tamañoBola, tamañoBola);

        // Dibujar contador de tiempo en la esquina superior derecha
        g.setColor(Color.white);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Tiempo: " + tiempo + "s", 360, 30);

        // Dibujar texto de vidas y puntuación
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Vidas: " + vidas, 20, 30);
        g.drawString("Puntos: " + puntuacion,  200, 30);
    }


    public void actionPerformed(ActionEvent e) {
    	
    	
        // Actualiza la posición de la pelota
        posicionBolaX += velocidadBolaX;
        posicionBolaY += velocidadBolaY;

        // Rebote en los bordes laterales
        if (posicionBolaX <= 0 || posicionBolaX >= ancho - tamañoBola) {
            velocidadBolaX = -velocidadBolaX;
        }

        // Rebote en la parte superior
        if (posicionBolaY <= 40) {
            velocidadBolaY = -velocidadBolaY;
        }

        // Pierde una vida si toca el borde inferior
        if (posicionBolaY >= alto - tamañoBola) {
            perderVida();
        }

        // Colisión con la paleta del jugador
        if (posicionBolaY + tamañoBola >= posicionJugadorY &&
                posicionBolaX + tamañoBola >= posicionJugadorX &&
                posicionBolaX <= posicionJugadorX + anchoPaleta) {
            velocidadBolaY = -velocidadBolaY;
        }

        for (Brick brick : bricks) {
            if (brick.isVisible() && brick.getBounds().intersects(new Rectangle(posicionBolaX, posicionBolaY, tamañoBola, tamañoBola))) {
                brick.setVisible(false);
                velocidadBolaY = -velocidadBolaY;
                puntuacion++;

                // Verificar si el jugador ha ganado
                if (puntuacion >= totalBricksPerRow * 3) {
                    mostrarMensajeGanaste();
                    return;  // Termina el método actionPerformed si el jugador ha ganado
                }
            }
        }

        // Verificar si se cumplió el tiempo límite
        if (tiempo >= 100) {
            mostrarMensajePerdisteTiempo();
            return;
        }

        // Redibuja la escena
        repaint();
    }

    private void perderVida() {
        vidas--; // Pierde una vida cuando la bola toca el borde inferior
        if (vidas <= 0) {
            mostrarMensajePerdisteVidas();
        } else {
            // Reubicar la bola en una posición aleatoria en el panel
            reiniciarPosicionBola();
        }
    }

    private void mostrarMensajePerdisteVidas() {
        String mensaje = "¡Perdiste! Te quedaste sin vidas. ¿Quieres jugar de nuevo?";
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "¡Game Over!", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarJuego(); // Reinicia el juego si el jugador elige jugar de nuevo
        } else {
            System.exit(0); // Cierra el juego si el jugador elige salir
        }
    }
    private void mostrarMensajePerdisteTiempo() {
        mostrarMensajePerdiste(" ¿Quieres jugar de nuevo?");
    }

    private void mostrarMensajePerdiste(String mensaje) {
        String mensajeFinal = "";
        if (vidas <= 0 && tiempo >= 100) {
            mensajeFinal = "Te quedaste sin vidas y sin tiempo. Obtuviste " + puntuacion + " punto/s.";
        } else if (vidas <= 0) {
            mensajeFinal = "Te quedaste sin vidas y obtuviste " + puntuacion + " punto/s.";
        } else if (tiempo >= 100) {
            mensajeFinal = "Te quedaste sin tiempo y obtuviste " + puntuacion + " punto/s.";
        }
        int opcion = JOptionPane.showOptionDialog(this, mensajeFinal + " " + mensaje, "Perdiste", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else {
            System.exit(0);
        }
    }


    private void mostrarMensajeGanaste() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("¡Felicidades!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 700);
            Final panel = new Final();
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    private void reiniciarJuego() {
        puntuacion = 0;
        vidas = 10;
        tiempo = 0;
        reiniciarPosicionBola();
        inicializarLadrillos();
        timerTiempo.restart();
        repaint();
    }

    private void reiniciarPosicionBola() {
        posicionBolaX = (int) (Math.random() * (ancho - tamañoBola));
        posicionBolaY = alto / 2; // La bola aparece en la mitad vertical del panel

        // Verifica si la bola se superpone con algún ladrillo
        // Si es así, ajusta la posición para que esté debajo de la fila de ladrillos
        boolean colision = true;
        while (colision) {
            colision = false;
            for (Brick brick : bricks) {
                if (brick.isVisible() && brick.getBounds().intersects(new Rectangle(posicionBolaX, posicionBolaY, tamañoBola, tamañoBola))) {
                    posicionBolaY = brick.getY() + brick.getHeight() + 1; // Asegura que la bola esté debajo del ladrillo
                    colision = true;
                    break;
                }
            }
        }
    }


    private void inicializarLadrillos() {
        bricks.clear();
        int totalBricksPerRow = 8;
        int totalBrickRows = 3;
        int initialX = 15;
        int initialY = 50;
        int brickWidth = 50;
        int brickHeight = 15;

        for (int i = 0; i < totalBrickRows; i++) {
            for (int j = 0; j < totalBricksPerRow; j++) {
                int x = initialX + j * (brickWidth + 10);
                int y = initialY + i * (brickHeight + 10);
                Brick brick = new Brick(x, y, brickWidth, brickHeight);
                bricks.add(brick);
            }
        }

        // Ajusta la posición de la pelota debajo de las filas de ladrillos
        posicionBolaX = ancho / 2 - tamañoBola / 2;
        posicionBolaY = initialY + totalBrickRows * (brickHeight + 10) + 10;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && posicionJugadorX > 0) {
            posicionJugadorX -= velocidadPaleta;
        } else if (keyCode == KeyEvent.VK_RIGHT && posicionJugadorX < ancho - anchoPaleta) {
            posicionJugadorX += velocidadPaleta;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NIVEL 3");
            nivel3 game = new nivel3();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(game);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}