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
    public static Semaphore espacoDisponivel; //Quantos espaços disponiveis tem no cesto
    public static Semaphore espacoOcupado; //Quantas bolas tem no cesto
    public boolean primeiraVez=true;
    
    public Crianca(int capacidadeCesto){
        espacoOcupado  = new Semaphore(0);
        espacoDisponivel = new Semaphore(10);
    }
    
    public void run(){
        try {
            if(this.primeiraVez){
                setStatusCrianca(getNome() + " está quieta.");
                busyWaitLoop(getTempoQuieta()*1000);
                this.primeiraVez = false;
            }
            while(true) {
                if(getStatusBola()==true){
                    criancaBrincando();
                    setStatusBola(false);
                } else if(getStatusBola()==false){
                    criancaQuieta();
                    setStatusBola(true);
                } 
            }
        }
        catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } 
        finally {
           //
        }
    }
    
    public void criancaBrincando() throws InterruptedException {
        /*System.out.println(getNome() + " está brincando.");
        System.out.println("CESTO: " + getEspacoOcupado());*/
        busyWaitLoop(getTempoBrincando()*1000);
        armazenaBola();
    }
    
    public void criancaQuieta() throws InterruptedException {
        /*System.out.println(getNome() + " está quieta.");
        System.out.println("CESTO: " + getEspacoOcupado());*/
        busyWaitLoop(getTempoQuieta()*1000);
        pegarBola();
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
             System.out.println(getNome() + " aguardando bola.");
             this.setStatusCrianca(status);
             System.out.println("Esperando..");
             System.out.println(getNome() + " CESTO: " + getEspacoOcupado());
             //esperaBolaNoCesto();
             System.out.println("..Parou de esperar");
         }
        espacoOcupado.acquire();
          espacoDisponivel.release(); 
         System.out.println(getNome() + " está brincando.");
         setStatusCrianca(getNome() + " está brincando.");
        System.out.println(getNome() + " CESTO: " + getEspacoOcupado());
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
        espacoDisponivel.acquire();
        espacoOcupado.release();
        System.out.println(getNome() + " está quieta.");
        setStatusCrianca(getNome() + " está quieta.");
        System.out.println(getNome() + " CESTO: " + getEspacoOcupado());
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
    
    public void busyWaitLoop(int millis) throws InterruptedException { //tempo de processamento
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
                frame.setSize(new Dimension(200, 800));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
