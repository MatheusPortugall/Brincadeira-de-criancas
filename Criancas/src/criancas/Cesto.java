package criancas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * Classe que representa uma caixa de mensagens, que interage com usuários e com
 * o pombo correio Possui os semaforos e bloqueia threads.
 *
 */
public class Cesto {
  /*private Semaphore mensagens;
  private Semaphore capacidade;

  private List<CestoListener> listeners = new ArrayList<>();

  public Cesto(int maximoMensagens) {
    bolas = new Semaphore(0);
    capacidade = new Semaphore(maximoMensagens);
  }

  /**
   * Pega <code>numeroMensagens</code> mensagens da caixa de mensagens. Bloqueia a
   * thread caso a caixa não tenha o número de mensagens requisitado no momento.
   *
   * @param numeroMensagens número de mensagens para serem retiradas da caixa
   * @throws InterruptedException caso a thread seja morta
   */
  /*public void pegarMensagens(int numeroMensagens) throws InterruptedException {
    bolas.acquire(numeroMensagens);
    capacidade.release(numeroMensagens);

    for (CestoListener listener : listeners) {
      listener.mensagensConsumidas(numeroMensagens);
    }
  }

  /**
   * Insere uma bola na caixa de cesto. Bloqueia a thread caso a caixa esteja
   * cheia.
   *
   * @throws InterruptedException caso a thread seja morta
   */
  /*public void inserirMensagem() throws InterruptedException {
    capacidade.acquire(1);
    bolas.release(1);

    for (CestoListener listener : listeners) {
      listener.mensagemInserida();
    }
  }

  public int getCapacidadeAtual() {
    return capacidade.availablePermits();
  }

  public int getNumeroMensagens() {
    return bolas.availablePermits();
  }

  public void addCestoListener(CestoListener listener) {
    this.listeners.add(listener);
  }*/
}