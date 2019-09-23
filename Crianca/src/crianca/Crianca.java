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
    private Semaphore capacidadeMaxima; //Quantos espaços disponiveis tem no cesto
    private Semaphore capacidadeAtual; //Quantas bolas tem no cesto
    private int tempo;
    
    public Crianca(int capacidadeCesto){
        capacidadeAtual  = new Semaphore(0);
        do{
            System.out.println("CAPACIDADE atual: " + capacidadeAtual.availablePermits());
        } while(false);
       
        capacidadeMaxima = new Semaphore(capacidadeCesto);
    }
    
    @Override
    public void run(){
        try {
            while(true) {
                /*
                capacidadeAtual.acquire();
                capacidadeMaxima.release();
                setStatusBola(true);
                criancaBrincando();
                busyWaitLoop(this.tempoBrincando);
                armazenaBola();
                criancaQuieta();
                busyWaitLoop(this.tempoQuieta);
                */
               // System.out.println("CAPACIDADE: " + capacidadeAtual.availablePermits());
                if(statusBola==true){
                    criancaBrincando();
                    busyWaitLoop(tempoBrincando);
                    armazenaBola();
                } else if(statusBola==false){
                    criancaQuieta();
                    busyWaitLoop(tempoQuieta);
                    pegarBola();
                }
               
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 
        finally {
            //System.out.println("Carregando próximo estado...");
        }
    }
    
    public void executaStatusCrianca(){
        if(statusBola==true){ // criança com a bola
            String status = getNome() + " está brincando.";
            System.out.println(status);
            setStatusCrianca(status);
           // System.out.println("Está brincando");
            setTempo(this.tempoBrincando);
            armazenaBola();
        } else if(statusBola==false) { // criança sem a bola
            String status = getNome() + " está quieta.";
            System.out.println(status);
            setStatusCrianca(status);
            //System.out.println("Está quieta");
            setTempo(this.tempoQuieta);
            pegarBola();
        }
    }
    
    public void criancaBrincando(){
        setStatusCrianca(getNome() + " está brincando."); 
        System.out.println(getNome() + " está brincando.");
        this.setTempo(1);
    }
    
    public void criancaQuieta(){
        setStatusCrianca(getNome() + " está quieta.");
        System.out.println(getNome() + " está quieta.");
        this.setTempo(1);
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

           if(capacidadeAtual.availablePermits() == 0){
                String status = getNome() + " aguardando bola.";
                System.out.println(getNome() + " aguardando bola.");
                System.out.println("Capacidade atual: " + getCapacidadeAtual());
                System.out.println("\nCapacidade maxima: " + getCapacidadeCesto());
                this.setStatusCrianca(status);
                this.setTempo(this.tempo);
            }
           capacidadeAtual.release();
           capacidadeMaxima.acquire();
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
            if(capacidadeAtual.availablePermits() == capacidadeMaxima.availablePermits()){
                String status = getNome() + " aguardando espaço no cesto.";
                System.out.println(status);
                this.setStatusCrianca(status);
                //this.setTempo(1);
            } 
            capacidadeAtual.acquire();
            capacidadeMaxima.release();
            setStatusBola(false);
            //this.setTempo(1);
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
    
    public int getCapacidadeCesto() {
        return capacidadeMaxima.availablePermits();
    }
	
    public int getCapacidadeAtual() {
        return capacidadeAtual.availablePermits();
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
    
     private void busyWaitLoop(int millis) throws InterruptedException {
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
