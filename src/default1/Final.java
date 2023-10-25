package default1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Final extends JPanel {
    private BufferedImage imagen;

    public Final() {
        setLayout(new BorderLayout());

        // Cargar la imagen
        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\usuario\\Downloads\\final.jpeg"); // Ruta a tu imagen
            Image img = icon.getImage();
            int width = 600;
            int height = 700;
            imagen = scaleImage(img, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Agregar componente al panel aquí
        JLabel label = new JLabel("BIEN HECHO, LOGRASTE SUPERAR TODOS LOS NIVELES");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centra horizontalmente
        label.setVerticalAlignment(SwingConstants.CENTER); // Centra verticalmente

        // Panel para el mensaje "BIEN HECHO..."
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setOpaque(false);
        messagePanel.add(label, BorderLayout.NORTH);

        // Botón "SALIR"
        JButton btnNewButton = new JButton("SALIR");
        btnNewButton.setForeground(Color.ORANGE); // Color del texto del botón
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra la aplicación cuando se hace clic en el botón
            }
        });
        // Establecer el tamaño preferido del botón
        btnNewButton.setPreferredSize(new Dimension(100, 50));

        // Agregar el botón al panel y centrarlo
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnNewButton);
        messagePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Establecer el layout del panel principal como BorderLayout
        setLayout(new BorderLayout());

        // Agregar el panel del mensaje encima de la imagen al centro
        add(messagePanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen de fondo
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, this);
        }
    }

    private BufferedImage scaleImage(Image img, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Final");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 700);
            Final panel = new Final();
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
