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

    private String nome;
    private int tempoBrincando;
    private int tempoQuieta;
    private boolean statusBola;
    private String statusCrianca;
    public static Semaphore espacoDisponivel; //Quantos espaços disponiveis tem no cesto
    public static Semaphore espacoOcupado; //Quantas bolas tem no cesto
    public static boolean primeiraVez=true;
    
    public Crianca(int capacidadeCesto){
        if(this.primeiraVez){
            espacoOcupado  = new Semaphore(0);
            System.out.println(capacidadeCesto);
            espacoDisponivel = new Semaphore(capacidadeCesto);
            this.primeiraVez = false;
        }
        
    }
    
    public void run(){
        try {
            /*if(this.primeiraVez){
                this.setStatusCrianca(getNome() + " está quieta.");
                busyWaitLoop(getTempoQuieta()*1000);
                this.primeiraVez = false;
            }*/
            while(true) {
                if(this.getStatusBola()==true){
                    System.out.println(getNome() + " Esta com bola");
                    this.setStatusCrianca(getNome() + " está brincando.");
                    //System.out.println(getNome() + " CESTO: " + espacoOcupado.availablePermits());
                    busyWaitLoop(this.getTempoBrincando()*1000);
                    armazenaBola();
                    
                } else if(this.getStatusBola()==false){
                    System.out.println(getNome() + " Esta sem bola");

                    this.setStatusCrianca(getNome() + " está quieta.");
                    //System.out.println(getNome() + " CESTO: " + espacoOcupado.availablePermits());
                    //this.criancaQuieta();
                    busyWaitLoop(this.getTempoQuieta()*1000);
                    pegarBola();
                    
                } 
		System.out.println(this.getStatusBola());

            }
        }
        catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } 
        finally {
           //
        }
    }
    
    /**
    * Pega uma bola do cesto. Bloqueia a thread 
    * caso o cesto esteja vazio.
    * 
    * @throws InterruptedException caso a thread seja morta
    */
    
    public void pegarBola() throws InterruptedException {
        if(espacoOcupado.availablePermits() == 0){
            String status = getNome() + " aguardando bola.";
            //System.out.println(getNome() + " aguardando bola.");
            this.setStatusCrianca(status);
        }
        espacoOcupado.acquire();
        this.setStatusBola(true);
        espacoDisponivel.release(); 
        //System.out.println(getNome() + " está brincando.");
        setStatusCrianca(getNome() + " está brincando.");
    }
    
    /**
    * Insere uma bola no cesto. Bloqueia a thread 
    * caso o cesto esteja cheio.
    * 
    * @throws InterruptedException caso a thread seja morta
    */
    
    public void armazenaBola() throws InterruptedException {
        if(espacoDisponivel.availablePermits() == 0){
            String status = getNome() + " aguardando lugar vazio no cesto.";
            System.out.println(status);
            this.setStatusCrianca(status);
            //esperaEspacoNoCesto();
        }
        System.out.println("Armazena Bola - Espaco disponivel antes: " + espacoDisponivel.availablePermits() );
        //System.out.println("Espaco ocupado: " + espacoOcupado.availablePermits() );
        System.out.println("Armazena Bola - Espaco ocupado antes: " + espacoOcupado.availablePermits() );
        espacoDisponivel.acquire();
        this.setStatusBola(false);
        espacoOcupado.release();
        System.out.println("Armazena Bola - Espaco disponivel depois: " + espacoDisponivel.availablePermits() );
        System.out.println("Espaco ocupado depois: " + espacoOcupado.availablePermits() );
        System.out.println(getNome() + " está quieta.");
        setStatusCrianca(getNome() + " está quieta.");
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
     
    public void esperaEspacoNoCesto() throws InterruptedException {
        while(espacoDisponivel.availablePermits() == 0){
            if(isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }
    
    public void esperaBolaNoCesto() throws InterruptedException {
        while(espacoOcupado.availablePermits() == 0){
            if(isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new MainFrame("Crianças brincando");
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                frame.setUndecorated(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
