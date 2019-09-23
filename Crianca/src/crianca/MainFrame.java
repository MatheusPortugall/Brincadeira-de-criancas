package crianca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class MainFrame extends JFrame {
    
    public boolean seTemValoresIniciais = false;
    
    public MainFrame(String title){
        super(title);
        
        setLayout(new FlowLayout());
        
        JLabel labelNome = new JLabel("Nome");
        JLabel labelTb = new JLabel("Tempo de brincadeira");
        JLabel labelTq = new JLabel("Tempo quieta");
        JLabel labelCesto = new JLabel("Capacidade do cesto");
        
        JCheckBox comBola = new JCheckBox("Com Bola");
        JCheckBox semBola = new JCheckBox("Sem Bola");
        JTextField nome = new JTextField(10);
        JTextField Tb = new JTextField(10);
        JTextField Tq = new JTextField(10);
        JTextField cesto = new JTextField(10);
        JButton button = new JButton("Adicionar criança");
        
        Container c = getContentPane();
        c.add(labelNome);
        c.add(nome);
        c.add(comBola);
        c.add(semBola);
        c.add(labelTb);
        c.add(Tb);
        c.add(labelTq);
        c.add(Tq);
        c.add(labelCesto);
        c.add(cesto);
        c.add(button);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String tempoBrincando = Tb.getText();
                String tempoQuieto = Tq.getText();
                String nomeCrianca = nome.getText();
                String quantidadeCesto = cesto.getText();
                if(comBola.isSelected() == semBola.isSelected()){
                    JOptionPane.showMessageDialog(comBola, "Selecione apenas uma opção.");
                    return;
                }
                else if(!isNumeric(tempoBrincando) || !isNumeric(tempoQuieto)
                    || tempoBrincando.contains(".") || tempoBrincando.contains(",")
                    || tempoQuieto.contains(".") || tempoQuieto.contains(",")
                    || quantidadeCesto.contains(".") || quantidadeCesto.contains(",")){
                        JOptionPane.showMessageDialog(Tb, "Digite um valor válido!");
                        return;
                } else {
                    cesto.setEditable(false);
                    JTextArea novaCrianca = new JTextArea();
                    c.add(novaCrianca);
                    int valorTempoQuieto = Integer.parseInt(tempoQuieto);
                    int valorTempoBrincando = Integer.parseInt(tempoBrincando);
                    int valorQuantidadeCesto = Integer.parseInt(quantidadeCesto);
                    boolean statusValue = comBola.isSelected() ? true : false;
                    String status = comBola.isSelected() ? "Com bola" : "Sem bola";
                    Crianca crianca = new Crianca(valorQuantidadeCesto);
                    crianca.setTempoBrincando(valorTempoBrincando);
                    crianca.setTempoQuieta(valorTempoQuieto);
                    crianca.setNome(nomeCrianca);
                    crianca.setStatusBola(statusValue);
                    crianca.start();
                    Timer tempo = new Timer();
                    
                    String textoPadrao = 
                        "Nome: "+ crianca.getNome() + "\n" + "Tempo de brincadeira: " + crianca.getTempoBrincando() +
                         "Tempo quieta: "+ crianca.getTempoQuieta() + "\n"
                    ;
                    novaCrianca.append(textoPadrao);
                    TimerTask tarefa = new TimerTask(){

                        @Override
                        public void run(){
                            String textoStatus = textoPadrao + crianca.getStatusCrianca() + "\n";
                            novaCrianca.setText(textoStatus);
                            crianca.executaStatusCrianca();
                        }
                    };
                    tempo.scheduleAtFixedRate(tarefa, crianca.getTempo(), crianca.getTempo());
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
    
    public boolean getSeTemValoresIniciais(){
        return this.seTemValoresIniciais;
    }
    
    public void setSeTemValoresIniciais(boolean seTemValoresIniciais){
        this.seTemValoresIniciais = seTemValoresIniciais;
    }

}
