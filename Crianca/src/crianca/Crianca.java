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
    private Semaphore capacidadeMaxima;
    private Semaphore capacidadeAtual;
    private Semaphore regiaoCritica;
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
               // System.out.println("CAPACIDADE: " + capacidadeAtual.availablePermits());
                executaStatusCrianca();
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 
        finally {
            System.out.println("Carregando próximo estado...");
        }
    }
    
    public void executaStatusCrianca(){
        if(statusBola==true){ // criança com a bola
            String status = getNome() + " está brincando.";
            setStatusCrianca(status);
           // System.out.println("Está brincando");
            setTempo(this.tempoBrincando);
            armazenaBola();
            
        } else if(statusBola==false) { // criança sem a bola
            String status = getNome() + " está quieta.";
            setStatusCrianca(status);
            //System.out.println("Está quieta");
            setTempo(this.tempoQuieta);
            pegarBola();
        }
    }
    
    /**
    * Pega uma bola do cesto. Bloqueia a thread 
    * caso o cesto esteja vazio.
    * 
    * @throws InterruptedException caso a thread seja morta
    */
    
    public void pegarBola() {
        try {
            while(capacidadeAtual.availablePermits() == 0){
                String status = getNome() + " aguardando bola.";
                this.setStatusCrianca(status);
                this.setTempo(5);
            }
            capacidadeAtual.acquire();
            setStatusBola(true);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Carregando próximo estado...");
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
            while(capacidadeAtual.availablePermits() == capacidadeMaxima.availablePermits()){
                String status = getNome() + " aguardando espaço no cesto.";
                this.setStatusCrianca(status);
                this.setTempo(5);
            } 
            capacidadeAtual.release();
            setStatusBola(false);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Carregando próximo estado...");
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
