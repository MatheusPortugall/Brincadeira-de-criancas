package crianca;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * @Equipe: Bianca, Emiliane, Luzyane, Matheus.
 */
/**
 * Cria a criança
 */
public class Crianca extends Thread {

    public String nome;
    public int tempoBrincando;
    public int tempoQuieta;
    public boolean statusBola;
    public String statusCrianca;
    private static Semaphore espacoDisponivel; //Quantos espaços disponiveis tem no cesto
    private static Semaphore espacoOcupado; //Quantas bolas tem no cesto
    private int tempo;
    
    public Crianca(int capacidadeCesto){
        espacoOcupado  = new Semaphore(0);
        espacoDisponivel = new Semaphore(capacidadeCesto);
    }
    
    public void run(){
        try {
            while(true) {
                    //setStatusBola(true);
                    criancaBrincando();
                    busyWaitLoop(this.tempoBrincando);
                    armazenaBola();
                    criancaQuieta();
                    busyWaitLoop(this.tempoQuieta);
                    pegarBola();
                /*
                    capacidadeAtual.acquire();
                    capacidadeMaxima.release();
                    setStatusBola(true);
                    criancaBrincando();
                    busyWaitLoop(this.tempoBrincando);
                    armazenaBola();
                    criancaQuieta();
                    busyWaitLoop(this.tempoQuieta);
               
                System.out.println("CAPACIDADE: " + capacidadeAtual.availablePermits());
                if(getStatusBola()==true){
                    criancaBrincando();
                    busyWaitLoop(tempoBrincando);
                    armazenaBola();
                    criancaQuieta();
                } else if(getStatusBola()==false){
                    criancaQuieta();
                    busyWaitLoop(tempoQuieta);
                    pegarBola();
                    criancaBrincando();
                } */
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 
        finally {
            //System.out.println("Carregando próximo estado...");
        }
    }
    
    public void criancaBrincando(){
        setStatusCrianca(getNome() + " está brincando."); 
        //System.out.println(getNome() + " está brincando.");
    }
    
    public void criancaQuieta(){
        setStatusCrianca(getNome() + " está quieta.");
        //System.out.println(getNome() + " está quieta.");
    }
    
    /**
    * Pega uma bola do cesto. Bloqueia a thread 
    * caso o cesto esteja vazio.
    * 
    * @throws InterruptedException caso a thread seja morta
    */
    
    public void pegarBola() {
        
        try {
            System.out.println("pegando bola");
           if(espacoOcupado.availablePermits() == 0){
                String status = getNome() + " aguardando bola.";
                System.out.println(getNome() + " aguardando bola.");
                System.out.println("Espaco OCUPADO no cesto: " + getEspacoOcupado());
                System.out.println("Espaco DISPONIVEL no cesto: " + getEspacoDisponivel());
                this.setStatusCrianca(status);
                this.setTempo(this.tempo);
            } 
            espacoOcupado.acquire();
            espacoDisponivel.release();
            setStatusBola(true);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        } finally {
           // System.out.println("Carregando próximo estado...");
        }
        
    }
    
    /**
    * Insere uma bola no cesto. Bloqueia a thread 
    * caso o cesto esteja cheio.
    * 
    * @throws InterruptedException caso a thread seja morta
    */
    
    public void armazenaBola() {
        try {
            System.out.println("guardando bola");
            if(espacoOcupado.availablePermits() == espacoDisponivel.availablePermits()){
                String status = getNome() + " aguardando espaço no cesto.";
                System.out.println(status);
                this.setStatusCrianca(status);
            }
            espacoOcupado.release();
            espacoDisponivel.acquire();
            setStatusBola(false);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        } finally {
           // System.out.println("Carregando próximo estado...");
        }
    }
    
    public int getTempo(){
        return this.tempo;
    }
    
    public String getStatusCrianca(){
        return this.statusCrianca;
    }
    
    public int getEspacoDisponivel() {
        return espacoDisponivel.availablePermits();
    }
	
    public int getEspacoOcupado() {
        return espacoOcupado.availablePermits();
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getTempoBrincando(){
        return this.tempoBrincando;
    }
    
    public int getTempoQuieta(){
        return this.tempoQuieta;
    }
    
    public boolean getStatusBola(){
        return this.statusBola;
    }
    
    public void setTempo(int tempo){
        this.tempo = tempo * 1000;
    }
    
    public void setStatusCrianca(String statusCrianca){
        this.statusCrianca = statusCrianca;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setTempoBrincando(int tempoBrincando){
        this.tempoBrincando = tempoBrincando;
    }
    
    public void setTempoQuieta(int tempoQuieta){
        this.tempoQuieta = tempoQuieta;
    }
    
    public void setStatusBola(boolean statusBola){
        this.statusBola = statusBola;
    }
    
     private void busyWaitLoop(int millis) throws InterruptedException { //tempo de processamento
        long current = System.currentTimeMillis();
        while(current + millis > System.currentTimeMillis()) {
            if(isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new MainFrame("Crianças brincando");
                frame.setSize(new Dimension(200, 800));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
