/* ***************************************************************
* Autor............: Carlos Gil Martins da Silva
* Matricula........: 202110261
* Inicio...........: 01/06/2023
* Ultima alteracao.: 05/06/2023
* Nome.............: Thread Carro
* Funcao...........: Thread Carro controla a animacao dos Carros que
são diferenciados pelos seus IDs, chama o metodo Principal de percorrer
o percurso, e controla as movimentações pra todas Direcoes
****************************************************************/

package thread;

import control.controllerGeral;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class carro extends Thread {

  private final int id; // Id do Carro
  public double x = 0; // Posicao Inicial X
  public double y = 0; // Posicao Inicial Y
  public ImageView car; // Imagem do Carro

  public carro(int id, ImageView carro) { // Construtor da Classe com Identificador
    this.id = id;
    this.car = carro;
  }

  controllerGeral cG = new controllerGeral(); // Instanciando e Criando o Controller
  // Metodo Utilizado para Setar um Controlador em Comum em Todas Thread

  public void setControlador(controllerGeral controle) {
    this.cG = controle;
  }

  @Override
  public void run() { // Start RUN
    while (true) { // Repetir o Processo inumeras vezes
      try {
        cG.percorrePercurso(id);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      resetPosition();
      System.out.println("Fim Carro ID: "+ id);
    } // Fim While
  } // Fim Run

  // Metodo Rotaciona o Carro para o angulo passado na chamada
  public void rotate(int angle) {
    Platform.runLater(() -> car.setRotate(angle));
  }// fim do metodo rotate()

  // Reseta o Carro Para Posicao Inicial
  public void resetPosition() {
    x= 0;
    y= 0;
    Platform.runLater(() -> car.setRotate(0));
    Platform.runLater(() -> car.setX(0));
    Platform.runLater(() -> car.setX(0));
  }// fim do metodo reset

  // Metodo que move o Carro Para Frente Setando sua pocisao
  // nova com o setX e Dando Sleep com a Velocidade
  public void moveFrente(int quantidade) {
    for (int i = 0; i < quantidade; i++) {
      x += 1;
      Double finalX = x;
      Platform.runLater(() -> car.setX(finalX));
      try {
        Thread.sleep(cG.getVelocidade(id));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  // Metodo que move o Carro Para baixo Setando sua pocisao
  // nova com o setX e Dando Sleep com a Velocidade
  public void moveBaixo(int quantidade) {
    for (int i = 0; i < quantidade; i++) {
      y += 1;
      Double finalY = y;
      Platform.runLater(() -> car.setY(finalY));
      try {
        Thread.sleep(cG.getVelocidade(id));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

    // Metodo que move o Carro Para Tras Setando sua pocisao
  // nova com o setX e Dando Sleep com a Velocidade
  public void moveTras(int quantidade) {
    for (int i = 0; i < quantidade; i++) {
      x -= 1;
      double finalX = x;
      Platform.runLater(() -> car.setX(finalX));
      try {
        Thread.sleep(cG.getVelocidade(id));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

    // Metodo que move o Carro Para Cima Setando sua pocisao
  // nova com o setX e Dando Sleep com a Velocidade
  public void moveCima(int quantidade) {
    for (int i = 0; i < quantidade; i++) {
      y -= 1;
      Double finalY = y;
      Platform.runLater(() -> car.setY(finalY));
      try {
        Thread.sleep(cG.getVelocidade(id));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
