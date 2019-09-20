package criancas;

import javax.swing.*;
import java.awt.*;

/**
 * @Equipe: Bianca, Emiliane, Luzyane, Matheus.
 */
/**
 * Cria as crianças
 */
public class Criancas extends Thread {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new MainFrame("Crianças brincando");
                frame.setSize(new Dimension(900, 300));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }

}
