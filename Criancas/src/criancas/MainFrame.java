package criancas;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super(title);
        
        setLayout(new FlowLayout());
        
        JLabel labelId = new JLabel("Nome");
        JLabel labelBola = new JLabel("Bola");
        JLabel labelTb = new JLabel("Tempo de brincadeira");
        JLabel labelTq = new JLabel("Tempo quieta");
        
        JTextField id = new JTextField("", 10);
        JTextField bola = new JTextField("", 10);
        JTextField Tb = new JTextField("", 10);
        JTextField Tq = new JTextField("", 10);
        JButton button = new JButton("Adicionar criança");
        
        Container c = getContentPane();
        c.add(labelId);
        c.add(id);
        c.add(labelBola);
        c.add(bola);
        c.add(labelTb);
        c.add(Tb);
        c.add(labelTq);
        c.add(Tq);
        c.add(button);
        
    }
}
