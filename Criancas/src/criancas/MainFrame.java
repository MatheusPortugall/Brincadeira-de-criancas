package criancas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super(title);
        
        setLayout(new FlowLayout());
        
        Random random = new Random();
        
        JLabel labelNome = new JLabel("Nome");
        JLabel labelBola = new JLabel("Bola");
        JLabel labelTb = new JLabel("Tempo de brincadeira");
        JLabel labelTq = new JLabel("Tempo quieta");
        
        JCheckBox comBola = new JCheckBox("Com Bola");
        JCheckBox semBola = new JCheckBox("Sem Bola");
        JTextField nome = new JTextField(10);
        JTextField Tb = new JTextField(10);
        JTextField Tq = new JTextField(10);
        JTextArea novaCrianca = new JTextArea();
        JButton button = new JButton("Adicionar criança");
        
        Container c = getContentPane();
        c.add(labelNome);
        c.add(nome);
        c.add(labelBola);
        c.add(comBola);
        c.add(semBola);
        c.add(labelTb);
        c.add(Tb);
        c.add(labelTq);
        c.add(Tq);
        c.add(button);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String tempoBrincando = Tb.getText();
                String tempoQuieto = Tq.getText();
                String nomeCrianca = nome.getText();
                if(comBola.isSelected() == semBola.isSelected()){
                    JOptionPane.showMessageDialog(comBola, "Selecione apenas uma opção.");
                }
                else if(!isNumeric(tempoBrincando) || !isNumeric(tempoQuieto)){
                    JOptionPane.showMessageDialog(Tb, "Digite apenas números");
                } else {
                    c.add(novaCrianca);
                    String status = comBola.isSelected() ? "Com bola" : "Sem bola";
                    novaCrianca.append("\n" + "Nome: " + nomeCrianca + "\n");
                    novaCrianca.append("Status: " + status + "\n");
                    novaCrianca.append("Tempo quieta: " + tempoQuieto + "\n");
                    novaCrianca.append("Tempo brincando: " + tempoBrincando);
                }
            }
        });
    }
    
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    
    public static void setWarningMsg(String text){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Valor inválido!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

}
