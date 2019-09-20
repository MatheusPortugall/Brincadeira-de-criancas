package criancas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Equipe: Bianca, Emiliane, Luzyane, Matheus.
 */

/**
 * Criança
 *
 * Estado inicial: K = 10 Id = 1 Tb = 10s Tq = 5s
 */
public class Crianca extends Thread {

  /*public static void main(String[] args) {
      private int K;
      private int id;
      private int Tb;
      private int Tq;

      private List<CriancaListener> listeners = new ArrayList<>();;
      private EstadoCrianca estadoAtual;

      private void mudarEstado(EstadoCrianca novoEstado) {
        for(CriancaListener listener : listeners) {
          listener.mudancaEstado(this, novoEstado);
        }
        estadoAtual = novoEstado;
      }

      private void busyWaitLoop(int millis) throws InterruptedException {
        long current = System.currentTimeMillis();

        while(current + millis > System.currentTimeMillis()) {
          if(isInterrupted()) {
            throw new InterruptedException();
          }
        }
      }

      public void run() {
        try {
          while(true) {
            //O Pombo tenta pegar as mensagens da caixa de mensagens.
            //Se não houver mensagens suficientes, ele dorme.
            mudarEstado(EstadoCrianca.BLOQUEADO);
            Cesto.pegarBola();

            mudarEstado(EstadoCrianca.BRINCANDO);
            busyWaitLoop(Tb);

            mudarEstado(EstadoCrianca.QUIETA);
            busyWaitLoop(Tq);
          }
        }
        catch (InterruptedException ie) {
          ie.printStackTrace();
        }
      }
    }*/

}
