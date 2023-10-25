package default1;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        nivel1 nivelUno = new nivel1();
        setContentPane(nivelUno);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
        });
    }
}
