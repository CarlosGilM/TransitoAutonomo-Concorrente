/* ***************************************************************
* Autor............: Carlos Gil Martins da Silva
* Matricula........: 202110261
* Inicio...........: 01/06/2023
* Ultima alteracao.: 05/06/2023
* Nome.............: Controlador do Transito Autonomo
* Funcao...........: Controlador que controla tudo que tem no programa,
cria todas as threads com seus respectivos ID'S, starta todas elas
e controla toda parte Gráfica, gerencia os Semaforos para não
ocorrer batidas e outros problemas no percurso dos carros, faz o controle
dos Percursos de Cada Carro, Controla os sliders de velocidade, 
pausa as threads e reinicia a execucao
****************************************************************/

package control;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import thread.*;

public class controllerGeral implements Initializable {

  @FXML private ImageView background;

  @FXML private ImageView carroVermelhoIMG;
  @FXML private ImageView carroAmareloIMG;
  @FXML private ImageView carroVerdeIMG;
  @FXML private ImageView carroCianoIMG;
  @FXML private ImageView carroLaranjaIMG;
  @FXML private ImageView carroAzulIMG;
  @FXML private ImageView carroRoxoIMG;
  @FXML private ImageView carroCinzaIMG;

  @FXML private Slider sliderAmarelo;
  @FXML private Slider sliderCiano;
  @FXML private Slider sliderCinza;
  @FXML private Slider sliderLaranja;
  @FXML private Slider sliderRoxo;
  @FXML private Slider sliderVerde;
  @FXML private Slider sliderVermelho;
  @FXML private Slider sliderAzul;

  @FXML private Button pausaAmarelo;
  @FXML private Button pausaAzul;
  @FXML private Button pausaCiano;
  @FXML private Button pausaCinza;
  @FXML private Button pausaLaranja;
  @FXML private Button pausaRoxo;
  @FXML private Button pausaVerde;
  @FXML private Button pausaVermelho;

  // Criação dos Semaforos VERMELHO / AMARELO
  private Semaphore[] SemVA = new Semaphore[6]; 

  // Criacao dos Semaforos VERMELHO / VERDE
  private Semaphore[] SemVVe = new Semaphore[3];

  // Criacao dos Semaforos VERMELHO / CIANO
  private Semaphore[] SemVC = new Semaphore[2];

  // Criacao dos Semaforos VERMELHO / LARANJA
  private Semaphore[] SemVL = new Semaphore[6];

  // Criacao dos Semaforos VERMELHO / AZUL
  private Semaphore[] SemVAz = new Semaphore[9];

  // Criacao dos Semaforos VERMELHO / ROXO
  private Semaphore[] SemVR = new Semaphore[8];

  // Criacao dos Semaforos VERMELHO / CINZA
  private Semaphore[] SemVCz = new Semaphore[5];

  // Criacao dos Semaforos AMARELO / VERDE
  private Semaphore[] SemAV = new Semaphore[2];

  // Criacao dos Semaforos AMARELO / CIANO
  private Semaphore[] SemACi = new Semaphore[1];

  // Criacao dos Semaforos AMARELO / LARANJA
  private Semaphore[] SemALr = new Semaphore[3];

  // Criacao dos Semaforos AMARELO / AZUL
  private Semaphore[] SemAAz = new Semaphore[2];

  // Criacao dos Semaforos AMARELO / ROXO
  private Semaphore[] SemARx = new Semaphore[3];

  // Criacao dos Semaforos AMARELO / CINZA
  private Semaphore[] SemACz = new Semaphore[3];

  // Criacao dos Semaforos Verde / Ciano
  private Semaphore[] SemVeC = new Semaphore[1];

  // Criacao dos Semaforos Verde / Ciano
  private Semaphore[] SemVeL = new Semaphore[1];

  // Criacao dos Semaforos Verde / Azul
  private Semaphore[] SemVeA = new Semaphore[1];

  // Criacao dos Semaforos Verde / Cinza
  private Semaphore[] SemVeCz = new Semaphore[1]; // Iniciado com 0 (Cinza comeca na RC)

  // Criacao dos Semaforos Ciano / Cinza
  private Semaphore[] SemCcz = new Semaphore[1]; // Iniciado com 0 (Ciano comeca na RC)

  // Criacao dos Semaforos Laranja / Azul
  private Semaphore[] SemLA = new Semaphore[2]; 

  // Criacao dos Semaforos Laranja / Roxo
  private Semaphore[] SemLRx = new Semaphore[2];

  // Criacao dos Semaforos Laranja / Cinza
  private Semaphore[] SemLCz = new Semaphore[1];
  private Semaphore SemLCz1 = new Semaphore(0); // Semaforo Especial iniciado com 0

  // Criacao dos Semaforos Laranja / Roxo
  private Semaphore[] SemAzRx = new Semaphore[2];

  // Criacao dos Semaforos Laranja / Roxo
  private Semaphore[] SemAzCz = new Semaphore[2];

  // Criacao dos Semaforos Roxo e Cinza
  private Semaphore[] SemRCz = new Semaphore[3];

  // Instanciando Threads
  private carro carroVermelho;
  private carro carroRoxo;
  private carro carroAzul;
  private carro carroAmarelo;
  private carro carroCiano;
  private carro carroVerde;
  private carro carroCinza;
  private carro carroLaranja;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    iniciarSemaforos();
  iniciarThreads();
  } // Fim Initialize

  //Metodo de Reiniciar A Execucao das Threads, parando as threads
  //resetando todas as variavéis, e objetos para sua criação padrão
  @FXML
  void ClickbotaoReiniciar(MouseEvent event) {
    carroVermelho.stop();
    carroAmarelo.stop();
    carroVerde.stop();
    carroCiano.stop();
    carroLaranja.stop();
    carroAzul.stop();
    carroRoxo.stop();
    carroCinza.stop();

    iniciarSemaforos();

    Platform.runLater(() -> carroVermelhoIMG.setX(0));
    Platform.runLater(() -> carroVermelhoIMG.setY(0));
    Platform.runLater(() -> carroVermelhoIMG.setRotate(0));

    Platform.runLater(() -> carroAmareloIMG.setX(0));
    Platform.runLater(() -> carroAmareloIMG.setY(0));
    Platform.runLater(() -> carroAmareloIMG.setRotate(0));
    
    Platform.runLater(() -> carroVerdeIMG.setX(0));
    Platform.runLater(() -> carroVerdeIMG.setY(0));
    Platform.runLater(() -> carroVerdeIMG.setRotate(0));

    Platform.runLater(() -> carroCianoIMG.setX(0));
    Platform.runLater(() -> carroCianoIMG.setY(0));
    Platform.runLater(() -> carroCianoIMG.setRotate(0));

    Platform.runLater(() -> carroLaranjaIMG.setX(0));
    Platform.runLater(() -> carroLaranjaIMG.setY(0));
    Platform.runLater(() -> carroLaranjaIMG.setRotate(0));

    Platform.runLater(() -> carroAzulIMG.setX(0));
    Platform.runLater(() -> carroAzulIMG.setY(0));
    Platform.runLater(() -> carroAzulIMG.setRotate(0));

    Platform.runLater(() -> carroRoxoIMG.setX(0));
    Platform.runLater(() -> carroRoxoIMG.setY(0));
    Platform.runLater(() -> carroRoxoIMG.setRotate(0));

    Platform.runLater(() -> carroCinzaIMG.setX(0));
    Platform.runLater(() -> carroCinzaIMG.setY(0));
    Platform.runLater(() -> carroCinzaIMG.setRotate(0));

    pausaVermelho.setText("Pausar");
    pausaAmarelo.setText("Pausar");
    pausaVerde.setText("Pausar");
    pausaCiano.setText("Pausar");
    pausaLaranja.setText("Pausar");
    pausaAzul.setText("Pausar");
    pausaRoxo.setText("Pausar");
    pausaCinza.setText("Pausar");

    sliderVermelho.setValue(5);
    sliderAmarelo.setValue(5);
    sliderVerde.setValue(5);
    sliderCiano.setValue(5);
    sliderLaranja.setValue(5);
    sliderAzul.setValue(5);
    sliderRoxo.setValue(5);
    sliderCinza.setValue(5);
    iniciarThreads();
  } // Fim Reiniciar

  //Metodo de Iniciar as Threads
  public void iniciarThreads(){
    // Criando as Classes com os Ids e Passando as IMGS como Referencia
    this.carroVermelho = new carro(0, carroVermelhoIMG);
    this.carroAmarelo = new carro(1, carroAmareloIMG);
    this.carroVerde = new carro(2, carroVerdeIMG);
    this.carroCiano = new carro(3, carroCianoIMG);
    this.carroLaranja = new carro(4, carroLaranjaIMG);
    this.carroAzul = new carro(5, carroAzulIMG);
    this.carroRoxo = new carro(6, carroRoxoIMG);
    this.carroCinza = new carro(7, carroCinzaIMG);
    // Setando os Controladores
    carroVermelho.setControlador(this);
    carroAmarelo.setControlador(this);
    carroVerde.setControlador(this);
    carroCiano.setControlador(this);
    carroLaranja.setControlador(this);
    carroAzul.setControlador(this);
    carroRoxo.setControlador(this);
    carroCinza.setControlador(this);
    //Start Threads
    carroVermelho.start();
    carroAmarelo.start();
    carroVerde.start();
    carroCiano.start();
    carroLaranja.start();
    carroAzul.start();
    carroRoxo.start();
    carroCinza.start();
  } // Fim iniciar Threads

//Metodo de Startar os Semaforos por meio de um For criando o novo Semaforo
  public void iniciarSemaforos() {
    for (int i = 0; i < SemVA.length; i++) {
      SemVA[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVVe.length; i++) {
      SemVVe[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVC.length; i++) {
      SemVC[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVL.length; i++) {
      SemVL[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVAz.length; i++) {
      SemVAz[i] = new Semaphore(1);
    }
    for (int i = 1; i < SemVR.length; i++) {
      SemVR[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVCz.length; i++) {
      SemVCz[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemAV.length; i++) {
      SemAV[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemACi.length; i++) {
      SemACi[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemALr.length; i++) {
      SemALr[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemAAz.length; i++) {
      SemAAz[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemARx.length; i++) {
      SemARx[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemACz.length; i++) {
      SemACz[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVeC.length; i++) {
      SemVeC[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVeL.length; i++) {
      SemVeL[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVeA.length; i++) {
      SemVeA[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemVeCz.length; i++) {
      SemVeCz[i] = new Semaphore(0); // Esse é ZERO
    }
    for (int i = 0; i < SemCcz.length; i++) {
      SemCcz[i] = new Semaphore(0); // Esse é ZERO
    }
    for (int i = 0; i < SemLA.length; i++) {
      SemLA[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemLRx.length; i++) {
      SemLRx[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemLCz.length; i++) {
      SemLCz[i] = new Semaphore(1);
      SemLCz1 = new Semaphore(0); // Esse é ZERO
    }
    for (int i = 0; i < SemAzRx.length; i++) {
      SemAzRx[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemAzCz.length; i++) {
      SemAzCz[i] = new Semaphore(1);
    }
    for (int i = 0; i < SemRCz.length; i++) {
      SemRCz[i] = new Semaphore(1);
    }
  } // Fim IniciarSemaforos

  public void percorrePercurso(int id) throws InterruptedException {
    switch (id) {
      case 0: // Carro Vermelho
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.moveTras(95); // Rua 21

        SemVAz[8].acquire(); // Semaforo Vermelho e Azul
        SemVA[4].acquire(); //Semaforo Vermelho e Amarelo
        SemVR[7].acquire(); // Semaforo Vermelho e Roxo
        carroVermelho.moveTras(42); // Cruzamento
        carroVermelho.rotate(-90);
        carroVermelho.moveBaixo(75); // Rua 55
        carroVermelho.moveBaixo(37); // Cruzamento
        carroVermelho.rotate(-180);
        carroVermelho.moveFrente(95); // Rua 26
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(95); // Rua 27
        SemVCz[2].acquire(); // Semaforo Vermelho e Cinza
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 28
        SemVA[4].release(); // Semaforo Vermelho e Amarelo
        SemVR[7].release(); // Semaforo Vermelho e Roxo
        carroVermelho.moveFrente(65); // Rua 28

        SemVL[4].acquire(); // Semaforo Vermelho e Laranja
        SemVA[5].acquire(); // Semaforo Vermelho e Amarelo
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + pedaco Rua 29
        SemVCz[2].release();
        carroVermelho.moveFrente(65); // Rua 29
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 30
        SemVAz[8].release(); // Semaforo Vermelho e Azul

        carroVermelho.moveFrente(65); // Rua 30
        carroVermelho.moveFrente(34); // Cruzamento
        carroVermelho.rotate(-270);
        carroVermelho.moveCima(75); // Rua 60
        carroVermelho.moveCima(37); // Cruzamento
        carroVermelho.moveCima(30); // Cruzamento + Pedaco Rua 54
        SemVA[5].release(); // Semaforo Vermelho e Amarelo

        carroVermelho.moveCima(45); // Rua 54

        SemVC[0].acquire(); // Semaforo Vermelho e Ciano
        SemVCz[3].acquire(); // Vermelho e Cinza
        SemVVe[1].acquire(); // Verde e Vermelho
        carroVermelho.moveCima(37); // Cruzamento
        carroVermelho.moveCima(75); // Rua 48

        SemVA[1].acquire(); // Semaforo Vermelho e Amarelo
        SemVR[2].acquire(); // Semaforo Vermelho e Roxo
        carroVermelho.moveCima(37); // Cruzamento
        carroVermelho.rotate(0);
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 15
        SemVL[4].release(); // Semaforo Vermelho e Laranja
        SemVVe[1].release(); // Semaforo Verde e Vermelho

        carroVermelho.moveTras(65); // Rua 15

        SemVAz[4].acquire(); // Semaforo Vermelho e Azul
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 14
        SemVAz[4].release(); // Semaforo Vermelho e Azul
        SemVR[2].release(); // Semaforo Vermelho e Roxo

        carroVermelho.moveTras(65); // Rua 14
        SemVL[1].acquire(); // Semaforo Vermelho e Laranja
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 13
        SemVCz[3].release(); // Vermelho e Cinza
        SemVL[1].release(); // Semaforo Vermelho e Laranja
        SemVA[1].release(); // Semaforo Vermelho e Amarelo

        carroVermelho.moveTras(65); // Rua 13

        SemVR[3].acquire(); // Semaforo Vermelho e Roxo // Vermelho e Cinza
        SemVAz[3].acquire(); // Semaforo Vermelho e Azul
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 12
        SemVAz[3].release(); // Semaforo Vermelho e Azul
        carroVermelho.moveTras(65); // Rua 12
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.rotate(90);
        carroVermelho.moveCima(30); // Cruzamento + Pedaço Rua 38
        SemVC[0].release(); // Semaforo Vermelho e Ciano
        SemVR[3].release();  // Semaforo Vermelho e Roxo // Vermelho e Cinza

        carroVermelho.moveCima(45); // Rua 38

        SemVVe[0].acquire(); // Semaforo Verde e Vermelho
        carroVermelho.moveCima(36); // Cruzamento
        carroVermelho.rotate(180);
        carroVermelho.moveFrente(95); // Rua 7

        SemVR[1].acquire(); // Semaforo Vermelho e Roxo
        SemVAz[1].acquire(); // Semaforo Vermelho e Azul // Vermelho e Cinza
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 8
        SemVAz[1].release(); // Semaforo Vermelho e Azul // Vermelho e Cinza

        carroVermelho.moveFrente(65); // Rua 8

        SemVL[0].acquire(); // Semaforo Vermelho e Laranja // Vermelho e Cinza
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 9
        SemVL[0].release(); // Semaforo Vermelho e Laranja // Vermelho e Cinza
        SemVR[1].release(); // Semaforo Vermelho e Roxo

        carroVermelho.moveFrente(65); // Rua 9

        SemVAz[2].acquire(); // Semaforo Vermelho e Azul
        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 10
        SemVAz[2].release(); // Semaforo Vermelho e Azul

        carroVermelho.moveFrente(65); // Rua 10

        SemVA[0].acquire(); // Semaforo Vermelho e Amarelo
        SemVC[1].acquire(); // Semaforo Vermelho e Ciano
        SemVL[5].acquire(); // Semaforo Vermelho e Laranja // Vermelho e Roxo

        carroVermelho.moveFrente(36); // Cruzamento
        carroVermelho.rotate(90);
        carroVermelho.moveCima(30); // Cruzamento + Pedaco Rua 36
        SemVVe[0].release(); //Semaforo Verde e Vermelho
        carroVermelho.moveCima(45); // Rua 36
        carroVermelho.moveCima(37); // Cruzamento
        carroVermelho.rotate(0);
        carroVermelho.moveTras(90); // Rua 05

        SemVAz[0].acquire(); // Semaforo Vermelho e Azul
        carroVermelho.moveTras(39); // Cruzamento
        carroVermelho.moveTras(90); // Rua 04

        SemVCz[0].acquire(); // Semaforo Vermelho e Cinza
        carroVermelho.moveTras(39); // Cruzamento
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 03
        SemVL[5].release(); // Semaforo Vermelho e Laranja // Vermelho e Roxo

        carroVermelho.moveTras(60); // Rua 03
        carroVermelho.moveTras(39); // Cruzamento
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 02
        SemVAz[0].release(); // Semaforo Vermelho e Azul
        SemVCz[0].release(); // Semaforo Vermelho e Cinza

        carroVermelho.moveTras(60); // Rua 02
        carroVermelho.moveTras(39); // Cruzamento
        carroVermelho.moveTras(90); // Rua 01
        carroVermelho.moveTras(45); // Cruzamento
        carroVermelho.rotate(-90);
        carroVermelho.moveBaixo(74); // Rua 31

        SemVVe[2].acquire(); //Semaforo Verde e Vermelho
        carroVermelho.moveBaixo(37); // Cruzamento
        carroVermelho.moveBaixo(74); // Rua 37

        SemVCz[1].acquire(); // Semaforo Vermelho e Cinza
        carroVermelho.moveBaixo(37); // Cruzamento
        carroVermelho.moveBaixo(30); // Cruzamento + Rua 43
        SemVC[1].release(); // Semaforo Vermelho e Ciano
        carroVermelho.moveBaixo(44); // Rua 43

        SemVR[5].acquire(); // Semaforo Vermelho e Roxo
        carroVermelho.moveBaixo(37); // Cruzamento
        carroVermelho.rotate(-180);
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 16
        SemVA[0].release(); // Semaforo Vermelho e Amarelo

        carroVermelho.moveFrente(62); // Rua 16
        carroVermelho.moveFrente(39); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 17
        SemVR[5].release(); // Semaforo Vermelho e Roxo

        carroVermelho.moveFrente(62); // Rua 17

        SemVAz[5].acquire(); // Semaforo Vermelho e Azul
        carroVermelho.moveFrente(39); // Cruzamento
        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 18
        SemVAz[5].release(); // Semaforo Vermelho e Azul
        SemVCz[1].release(); // Semaforo Vermelho e Cinza

        carroVermelho.moveFrente(62); // Rua 18

        SemVA[2].acquire(); // Semaforo Vermelho e Amarelo
        SemVR[4].acquire(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde
        SemVL[2].acquire(); //Semaforo Vermelho e Laranja
        carroVermelho.moveFrente(39); // Cruzamento

        carroVermelho.moveFrente(30); // Cruzamento + Pedaco Rua 19
        SemVL[2].release();

        carroVermelho.moveFrente(62); // Rua 19

        SemVAz[6].acquire(); // Semaforo Vermelho e azul
        carroVermelho.moveFrente(39); // Cruzamento
        carroVermelho.rotate(270);
        carroVermelho.moveBaixo(30); // Cruzamento + Pedaco Rua 53
        SemVVe[2].release(); //Semaforo Verde e Vermelho
        SemVR[4].release(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde

        carroVermelho.moveBaixo(45); // Rua 53
        carroVermelho.moveBaixo(37); // Cruzamento
        carroVermelho.rotate(0);
        carroVermelho.moveTras(30); // Cruzamento + Pedaço Rua 24
        SemVA[2].release(); // Semaforo Vermelho e Amarelo
        SemVAz[6].release(); // Semaforo Vermelho e Azul

        carroVermelho.moveTras(65); // Rua 24

        SemVA[3].acquire(); // Semaforo Vermelho e Amarelo // Vermelho e Roxo
        SemVL[3].acquire(); // Semaforo Vermelho e Laranja // Vermelho e Cinza
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.moveTras(30); // Cruzamento + Pedaco Rua 23
        SemVL[3].release();  // Semaforo Vermelho e Laranja // Vermelho e Cinza
        carroVermelho.moveTras(65); // Rua 23
        SemVAz[7].acquire(); // Semaforo Vermelho e Azul // Vermelho e Roxo
        carroVermelho.moveTras(36); // Cruzamento
        carroVermelho.moveTras(30); // Cruazmento + Pedaco Rua 22
        SemVA[3].release(); // Semaforo Vermelho e Amarelo // Vermelho e Roxo
        SemVAz[7].release(); // Semaforo Vermelho e Azul // Vermelho e Roxo

        carroVermelho.moveTras(65); // Rua 22
        break; // Fim Carro Vermelho

      case 1: // Carro Amarelo
        carroAmarelo.rotate(180);

        SemALr[2].acquire(); // Semaforo Amarelo e Laranja
        SemVA[5].acquire(); // Semaforo Vermelho e Amarelo
        carroAmarelo.moveFrente(36); // Cruzamento
        carroAmarelo.rotate(-90);
        carroAmarelo.moveBaixo(75); // Rua 60
        carroAmarelo.moveBaixo(36); // Cruzamento
        carroAmarelo.rotate(0);
        carroAmarelo.moveTras(90); // Rua 30
        SemAAz[0].acquire(); // Semaforo Amarelo e Azul
        carroAmarelo.moveTras(39); // Cruzamento
        carroAmarelo.moveTras(90); // Rua 29

        SemACz[0].acquire(); // Semaforo Amarelo e Cinza
        carroAmarelo.moveTras(39); // Cruzamento
        carroAmarelo.rotate(90);
        carroAmarelo.moveCima(30); // Cruzamento + Pedaco Rua 58
        SemAAz[0].release(); // Semaforo Amarelo e Azul
        SemVA[5].release(); // Semaforo Vermelho e Amarelo

        carroAmarelo.moveCima(45); // Rua 58

        SemARx[1].acquire(); // Semaforo Amarelo e Roxo
        SemVA[3].acquire(); // Semaforo Vermelho e Amarelo
        carroAmarelo.moveCima(36); // Cruzamento
        carroAmarelo.rotate(0);
        carroAmarelo.moveTras(30); // Cruzamento + Pedaco Rua 23
        SemALr[2].release(); // Semaforo Amarelo e Laranja
        SemACz[0].release(); //Semaforo Amarelo e Cinza

        carroAmarelo.moveTras(65); // Rua 23
        SemVA[4].acquire(); // Semaforo Vermelho e Amarelo

        SemAAz[1].acquire(); // Semaforo Amarelo e Azul
        carroAmarelo.moveTras(37); // Cruzamento
        carroAmarelo.rotate(-90);
        carroAmarelo.moveBaixo(30); // Cruzamento + Pedaco Rua 57
        SemVA[3].release(); // Semaforo Vermelho e Amarelo

        carroAmarelo.moveBaixo(45); // Rua 57

        carroAmarelo.moveBaixo(36); // Cruzamento
        carroAmarelo.rotate(0);
        carroAmarelo.moveTras(30); // Cruzamento + Pedaco Rua 27
        SemAAz[1].release();

        carroAmarelo.moveTras(63); // Rua 27
        carroAmarelo.moveTras(37); // Cruzamento

        SemARx[2].acquire(); // Semaforo Amarelo e Roxo
        SemARx[1].release(); // Semaforo Amarelo e Roxo

        carroAmarelo.moveTras(93); // Rua 26
        carroAmarelo.moveTras(37); // Cruzamento
        carroAmarelo.rotate(90);
        carroAmarelo.moveCima(75); // Rua 55
        carroAmarelo.moveCima(36); // Cruzamento
        carroAmarelo.moveCima(30); // Cruzamento + Pedaco Rua 49
        SemVA[4].release(); // Semaforo Vermelho e Amarelo

        carroAmarelo.moveCima(45); // Rua 49

        SemVA[0].acquire(); // Semaforo Vermelho e Amarelo
        SemAV[0].acquire(); //Semaforo Amarelo e Verde
        SemACz[1].acquire(); // Semaforo Amarelo e Cinza
        carroAmarelo.moveCima(36); // Cruzamento
        carroAmarelo.moveCima(30); // Cruzamento + Pedaco Rua 43
        SemARx[2].release(); // Semaforo Amarelo e Roxo

        carroAmarelo.moveCima(45); // Rua 43

        SemACi[0].acquire(); //Semaforo Amarelo e Ciano
        carroAmarelo.moveCima(36); // Cruzamento
        carroAmarelo.moveCima(30); // Cruzamento + Pedaco Rua 37
        SemACz[1].release(); // Semaforo Amarelo e Cinza

        carroAmarelo.moveCima(45); // Rua 37
        carroAmarelo.moveCima(37); // Cruzamento
        carroAmarelo.moveCima(30); // Cruzamento + Pedaco Rua 31
        SemAV[0].release(); // Semaforo Amarelo e Verde

        carroAmarelo.moveCima(45); // Rua 31
        carroAmarelo.moveCima(37); // Cruzamento
        carroAmarelo.rotate(-180);
        carroAmarelo.moveFrente(95); // Rua 1
        carroAmarelo.moveFrente(36); // Cruzamento
        carroAmarelo.moveFrente(95); // Rua 2

        SemVAz[0].acquire(); // Semaforo Vermelho e Azul // Amarelo e Azul
        SemACz[2].acquire(); //Semaforo Amarelo e Cinza
        carroAmarelo.moveFrente(36); // Cruzamento
        carroAmarelo.moveFrente(95); // Rua 3

        SemARx[0].acquire(); // Semaforo amarelo e roxo
        SemALr[0].acquire(); // Semaforo Amarelo e Laranja
        carroAmarelo.moveFrente(36); // Cruzamento
        carroAmarelo.moveFrente(30); // Cruzamento + Pedaco Rua 4
        SemACz[2].release(); //Semaforo Amarelo e Cinza

        carroAmarelo.moveFrente(65); // Rua 4
        carroAmarelo.moveFrente(35); // Cruzamento
        carroAmarelo.moveFrente(30); // Cruzamento + Pedaco Rua 5
        SemVAz[0].release(); // Semaforo Vermelho e Azul // Amarelo e Azul

        carroAmarelo.moveFrente(65); // Rua 5
        carroAmarelo.moveFrente(35); // Cruzamento
        carroAmarelo.rotate(-90);
        carroAmarelo.moveBaixo(75); // Rua 36

        SemAV[1].acquire(); // Semaforo Amarelo e Verde

        carroAmarelo.moveBaixo(36); // Cruzamento
        carroAmarelo.moveBaixo(20); // Cruzamento + Pedaco Rua 42
        SemVA[0].release(); // Semaforo Vermelho e Amarelo

        carroAmarelo.moveBaixo(55); // Rua 42

        SemVA[1].acquire(); // Semaforo Vermelho e Amarelo
        carroAmarelo.moveBaixo(36); // Cruzamento
        carroAmarelo.rotate(0);
        carroAmarelo.moveTras(30); // Cruzamento + Pedaco Rua 15
        SemAV[1].release(); // Semaforo Amarelo e Verde
        SemALr[0].release(); // Semaforo Amarelo e Laranja

        carroAmarelo.moveTras(63); // Rua 15

        SemVAz[4].acquire(); // Semaforo Vermelho e Azul // Amarelo e Azul
        carroAmarelo.moveTras(37); // Cruzamento
        carroAmarelo.moveTras(30); // Cruzamento + Pedaco Rua 14
        SemARx[0].release(); // Semaforo amarelo e roxo
        SemVAz[4].release(); // Semaforo Vermelho e Azul // Amarelo e Azul

        carroAmarelo.moveTras(63); // Rua 14

        SemALr[1].acquire(); // Semaforo Amarelo e Laranja
        carroAmarelo.moveTras(37); // Cruzamento
        
        carroAmarelo.rotate(-90);
        carroAmarelo.moveBaixo(30); // Cruzamento + Pedaco Rua 46
        SemVA[1].release(); // Semaforo Vermelho e Amarelo
        SemACi[0].release(); // Semaforo Amarelo e Ciano

        carroAmarelo.moveBaixo(45); // Rua 46

        SemVA[2].acquire(); // Semaforo Vermelho e Amarelo
        SemVR[4].acquire(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Amarelo e Roxo // Amarelo e Cinza
        carroAmarelo.moveBaixo(36); // Cruzamento
        carroAmarelo.rotate(-180);
        carroAmarelo.moveFrente(30); // Cruzamento + Pedaco Rua 19
        SemALr[1].release(); //Semaforo Amarelo e Laranja

        carroAmarelo.moveFrente(65); // Rua 19
        
        SemVAz[6].acquire(); // Semaforo Vermelho e Azul // Amarelo e Azul
        carroAmarelo.moveFrente(36); // Cruzamento
        carroAmarelo.rotate(-90);
        carroAmarelo.moveBaixo(30); // Cruzamento + Pedaco Rua 53
        SemVR[4].release(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Amarelo e Roxo //Amarelo e Cinza

        carroAmarelo.moveBaixo(45); // Rua 53
        carroAmarelo.moveBaixo(36); // Cruzamento
        carroAmarelo.rotate(-180);
        carroAmarelo.moveFrente(30); // Cruzamento + Pedaco Rua 25
        SemVAz[6].release(); // Semaforo Vermelho e Azul // Amarelo e Azul
        SemVA[2].release(); // Semaforo Vermelho e Amarelo

        carroAmarelo.moveFrente(63); // Rua 25
        break; // Fim Carro Amarelo

      case 2: // Carro Verde
        carroVerde.rotate(180);

        SemVVe[0].acquire(); // Semaforo Vermelho e Verde
        carroVerde.moveFrente(39); // Cruzamento
        carroVerde.moveFrente(90); // Rua 7

        SemVR[1].acquire(); // Semaforo Vermelho e Roxo // Verde e Roxo
        SemVAz[1].acquire(); //Semaforo Vermelho e Azul // Verde e Azul // Verde e Cinza
        carroVerde.moveFrente(39); // Cruzamento
        carroVerde.moveFrente(30); // Cruzamento + Pedaco Rua 8
        SemVAz[1].release(); //Semaforo Vermelho e Azul // Verde e Azul // Verde e Cinza

        carroVerde.moveFrente(60); // Rua 8

        SemVL[0].acquire(); // Semarofo Vermelho e Laranja // Verde e Laranja // Verde e Cinza
        carroVerde.moveFrente(39); // Cruzamento
        carroVerde.moveFrente(30); // Cruzamento + Pedaco Rua 9
        SemVL[0].release(); // Semarofo Vermelho e Laranja // Verde e Laranja // Verde e Cinza

        SemVR[1].release(); // Semaforo Vermelho e Roxo // Verde e Roxo 

        carroVerde.moveFrente(60); // Rua 9

        SemVAz[2].acquire(); //Semaforo Vermelho e Azul // Verde e Azul
        carroVerde.moveFrente(39); // Cruzamento
        carroVerde.moveFrente(30); // Cruzamento + Pedaco Rua 10
        SemVAz[2].release(); //Semaforo Vermelho e Azul // Verde e Azul

        carroVerde.moveFrente(60); // Rua 10

        SemVeCz[0].acquire();// Semaforo Verde e Cinza
        SemVeL[0].acquire(); // Semaforo Verde e Laranja
        SemAV[1].acquire(); // Semaforo Amarelo e Verde // Verde e Ciano // Verde e Roxo
        carroVerde.moveFrente(46); // Cruzamento
        carroVerde.rotate(-90);
        carroVerde.moveBaixo(30); // Cruzamento + Pedaco Rua 42
        SemVVe[0].release(); // Semaforo Vermelho e Verde

        carroVerde.moveBaixo(44); // Rua 42

        SemVVe[1].acquire(); // Semaforo Vermelho e verde
        carroVerde.moveBaixo(37); // Cruzamento
        carroVerde.moveBaixo(30); // Cruzamento + Pedaco Rua 48
        SemAV[1].release(); // Semaforo Amarelo e Verde // Verde e Ciano // Verde e Roxo

        carroVerde.moveBaixo(44); // Rua 48
        carroVerde.moveBaixo(37); // Cruzamento
        carroVerde.rotate(0);
        carroVerde.moveTras(30); // Cruzamento + Pedaco Rua 20
        SemVVe[1].release(); // Semaforo Vermelho e Verde
        SemVeL[0].release(); // Semaforo Verde e Laranja

        carroVerde.moveTras(60); // Rua 20

        SemVVe[2].acquire(); // Semaforo Vermelho e Verde
        SemVR[4].acquire(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Verde e Roxo
        SemVeA[0].acquire(); // Semaforo Verde e Azul
        carroVerde.moveTras(39); // Cruzamento
        carroVerde.moveTras(30); // Cruzamento + Pedaco Rua 19
        SemVeA[0].release(); // Semaforo Verde e Azul

        carroVerde.moveTras(60); // Rua 19
        SemVL[2].acquire(); // Semaforo Vermelho e Laranja // Semaforo Verde e Laranja
        carroVerde.moveTras(39); // Cruzamento
        carroVerde.moveTras(30); // Cruzamento + Pedaco Rua 18
        SemVeCz[0].release();// Semaforo Verde e Cinza

        SemVL[2].release(); // Semaforo Vermelho e Laranja // Semaforo Verde e Laranja
        SemVR[4].release(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Verde e Roxo

        carroVerde.moveTras(60); // Rua 18

        SemVCz[1].acquire(); // Semaforo Vermelho e Cinza // Verde e Cinza
        SemVAz[5].acquire(); //Semaforo Vermelho e Azul // Verde e Azul
        carroVerde.moveTras(39); // Cruzamento
        carroVerde.moveTras(30); //Cruzamento + Pedaco Rua 17
        SemVAz[5].release(); //Semaforo Vermelho e Azul // Verde e Azul

        carroVerde.moveTras(60); // Rua 17

        SemVR[5].acquire(); // Semaforo Vermelho e Roxo // Verde e Roxo
        carroVerde.moveTras(39); // Cruzamento
        carroVerde.moveTras(90); // Rua 16

        SemAV[0].acquire(); // Semaforo Amarelo e Verde
        carroVerde.moveTras(45); // Cruzamento
        carroVerde.rotate(90);
        carroVerde.moveCima(30); // Cruzamento + Pedaco Rua 43
        SemVR[5].release(); // Semaforo Vermelho e Roxo // Verde e Roxo

        carroVerde.moveCima(44); // Rua 43

        SemVeC[0].acquire(); // Semaforo Verde e Ciano
        carroVerde.moveCima(37); // Cruzamento
        carroVerde.moveCima(30); // Cruzamento + Pedaco Rua 37
        SemVCz[1].release(); // Semaforo Vermelho e Cinza // Verde e Cinza

        carroVerde.moveCima(44); // Rua 37
        carroVerde.moveCima(37); // Cruzamento
        carroVerde.rotate(180);
        carroVerde.moveFrente(30); // Cruzamento + Pedaco Rua 6
        SemVeC[0].release(); // Semaforo Verde e Ciano
        SemVVe[2].release(); // Semaforo Vermelho e Verde
        SemAV[0].release(); // Semaforo Amarelo e Verde

        carroVerde.moveFrente(60); // Rua 6
        break; // Fim Carro Verde

      case 3: // Carro Ciano
        SemVC[0].acquire(); // Semaforo Vermelho e Ciano
        SemVR[3].acquire(); // Semaforo Vermelho e Roxo // Ciano e Roxo
        carroCiano.moveFrente(39); // Cruzamento
        carroCiano.moveFrente(90); // Rua 12

        SemVAz[3].acquire(); // Semaforo Vermelho e Azul // Ciano e Azul
        carroCiano.moveFrente(39); // Cruzamento
        carroCiano.moveFrente(30); // Cruzamento + Pedaco Rua 13
        SemVAz[3].release(); // Semaforo Vermelho e Azul // Ciano e Azul
        SemVR[3].release(); // Semaforo Vermelho e Roxo // Ciano e Roxo
        SemCcz[0].release(); // Semaforo Especial Ciano e Cinza

        carroCiano.moveFrente(60); // Rua 13

        SemACi[0].acquire(); // Semaforo Amarelo e Ciano
        SemVCz[3].acquire(); // Semaforo Vermelho e Cinza
        SemVL[1].acquire(); // Semáforo Vermelho e Laranja // Carro Ciano // Laranja
        carroCiano.moveFrente(39); // Cruzamento
        carroCiano.moveFrente(30); // Cruzamento + Pedaco Rua 14
        SemVL[1].release(); // Semáforo Vermelho e Laranja // Carro Ciano // Laranja

        carroCiano.moveFrente(60); // Rua 14

        SemARx[0].acquire(); // Semaforo Amarelo e Roxo // Ciano e Roxo
        SemVAz[4].acquire(); // Semaforo Vermelho e Azul // Ciano e Azul
        carroCiano.moveFrente(39); // Cruzamento
        carroCiano.moveFrente(30); // Cruzamento + Pedaco Rua 15
        SemVAz[4].release(); // Semaforo Vermelho e Azul // Ciano e Azul

        carroCiano.moveFrente(60); // Rua 15
        SemALr[0].acquire(); // Semaforo Amarelo e Laranja // Ciano e Laranja
        SemAV[1].acquire(); // Semaforo Verde e Ciano // Semaforo Amarelo e Verde
        carroCiano.moveFrente(45); // Cruzamento
        carroCiano.rotate(-90);
        carroCiano.moveCima(30); // Cruzamento + Pedaco Rua 42
        SemVCz[3].release(); // Semaforo Vermelho e Cinza
        SemVC[0].release(); // Semaforo Vermelho e Ciano

        carroCiano.moveCima(44); // Rua 42

        SemVC[1].acquire(); // Semaforo Vermelho e Ciano
        carroCiano.moveCima(37); // Cruzamento
        carroCiano.moveCima(30); // Cruzamento + Pedaco Rua 36
        SemAV[1].release(); // Semaforo Verde e Ciano // Semaforo Amarelo e Verde

        carroCiano.moveCima(44); // Rua 36
        carroCiano.moveCima(37); // Cruzamento
        carroCiano.rotate(-180);
        carroCiano.moveTras(90); // Rua 5

        SemVAz[0].acquire(); // Semaforo Vermelho e Azul // Semaforo Ciano e Azul
        carroCiano.moveTras(39); // Cruzamento
        carroCiano.moveTras(90); // Rua 4

        SemVCz[0].acquire(); // Semaforo Vermelho e Cinza // Ciano e Cinza
        carroCiano.moveTras(39); // Cruzamento
        carroCiano.moveTras(30); // Cruzamento + Pedaco Rua 3
        SemARx[0].release(); // Semaforo Amarelo e Roxo // Ciano e Roxo
        SemALr[0].release(); // Semaforo Amarelo e Laranja // Ciano e Laranja

        carroCiano.moveTras(60); // Rua 3
        carroCiano.moveTras(39); // Cruzamento
        carroCiano.moveTras(30); // Cruzamento + Pedaco Rua 2
        SemVCz[0].release(); // Semaforo Vermelho e Cinza // Ciano e Cinza
        SemVAz[0].release(); // Semaforo Vermelho e Azul // Semaforo Ciano e Azul

        carroCiano.moveTras(60); // Rua 2
        carroCiano.moveTras(39); // Cruzamento
        carroCiano.moveTras(90); // Rua 1
        carroCiano.moveTras(46); // Cruzamento
        carroCiano.rotate(90);
        carroCiano.moveBaixo(74); // Rua 31
        SemVeC[0].acquire(); // Semaforo Verde e Ciano
        carroCiano.moveBaixo(37); // Cruzamento
        carroCiano.moveBaixo(74); // Rua 37

        SemCcz[0].acquire(); // Semaforo Especial comecado com 0
        carroCiano.moveBaixo(37); // Cruzamento
        carroCiano.rotate(0);
        carroCiano.moveFrente(30); // Cruzamento + Pedaco Rua 11
        SemVC[1].release(); // Semaforo Vermelho e Ciano
        SemACi[0].release(); // Semaforo Amarelo e Ciano
        SemVeC[0].release(); // Semaforo Verde e Ciano
        carroCiano.moveFrente(60); // Rua 11
        break; // Fim carro Ciano

      case 4: // Carro Laranja
        SemALr[1].acquire(); //Semaforo Amarelo e Laranja
        SemVL[1].acquire(); // Semaforo Vermelho e Laranja
        carroLaranja.moveBaixo(37); // Cruzamento
        carroLaranja.moveBaixo(30); // Cruzamento + Pedaço Rua 46
        SemLCz1.release(); // Semaforo Laranja e Cinza ESPECIAL COMECANDO COM 0
        SemVL[1].release(); // Semaforo Vermelho e Laranja

        carroLaranja.moveBaixo(44); // Rua 46
        SemALr[2].acquire(); // Semaforo Amarelo e Laranja
        SemLCz[0].acquire(); //Semaforo Laranja e Cinza
        SemLRx[1].acquire(); // Semaforo Laranja / Roxo
        SemLA[1].acquire(); // Laranja e Azul
        SemVL[2].acquire(); // Semaforo Vermelho e Laranja
        carroLaranja.moveBaixo(37); // Cruzamento
        carroLaranja.moveBaixo(30); // Cruzamento + Perdaco Rua 52
        SemALr[1].release(); // semaforo Amarelo e Laranja
        SemVL[2].release(); // Semaforo Vermelho e Laranja

        carroLaranja.moveBaixo(44); // Rua 52

        SemVL[3].acquire(); // Semaforo Vermelho e Laranja
        
        carroLaranja.moveBaixo(37); // Cruzamento
        carroLaranja.moveBaixo(30); // Cruzamento + Pedaco Rua 58
        SemLRx[1].release(); // Semaforo Laranja / Roxo
        SemVL[3].release(); // Semaforo Vermelho e Laranja

        carroLaranja.moveBaixo(44); // Rua 58

        SemVL[4].acquire(); // Semaforo Vermelho e Laranja
        SemAAz[0].acquire(); // Semaforo Amarelo e Azul // Azul e Laranja
        carroLaranja.moveBaixo(37); // Cruzamento
        carroLaranja.rotate(-90);
        carroLaranja.moveFrente(30); // Cruzamento + Pedaco Rua 29
        SemLCz[0].release(); // Semaforo Laranja e Cinza

        carroLaranja.moveFrente(65); // Rua 29
        carroLaranja.moveFrente(36); // Cruzamento
        carroLaranja.moveFrente(30); // Cruzamento + Pedaco Rua 30
        SemAAz[0].release(); // Semaforo Amarelo e Azul // Azul e Laranja
        SemLA[1].release(); // Laranja e Azul

        carroLaranja.moveFrente(65); // Rua 30
        carroLaranja.moveFrente(36); // Cruzamento
        carroLaranja.rotate(-180);
        carroLaranja.moveCima(74); // Rua 60
        carroLaranja.moveCima(37); // Cruzamento
        carroLaranja.moveCima(30); // Cruzamento + Pedaco Rua 54
        SemALr[2].release(); // Semaforo Amarelo e Laranja

        carroLaranja.moveCima(44); // Rua 54
        SemVeL[0].acquire(); // Semaforo Verde e Laranja
        SemVVe[1].acquire(); // Semaforo Vermelho e Verde // Laranja e Cinza
        carroLaranja.moveCima(37); // Cruzamento
        carroLaranja.moveCima(74); // Rua 48

        SemLRx[0].acquire(); // Semaforo Laranja e Roxo
        SemALr[0].acquire(); // Semaforo Amarelo e Laranja
        carroLaranja.moveCima(37); // Cruzamento
        carroLaranja.moveCima(30); // Cruzamento + Pedaco Rua 42
        SemVVe[1].release(); // Semaforo Vermelho e Verde // Laranja e Cinza
        SemVL[4].release(); // Semaforo Vermelho e Laranja
 
        carroLaranja.moveCima(44); // Rua 42

        SemVL[5].acquire(); // Semaforo Vermelho e Laranja
        carroLaranja.moveCima(37); // Cruzamento
        carroLaranja.moveCima(30); // Cruzamento + Pedaco Rua 36
        SemVeL[0].release(); // Semaforo Verde e Laranja

        carroLaranja.moveCima(44); // Rua 36
        SemLCz1.acquire(); // Semaforo Laranja e Cinza ESPECIAL COMECANDO COM 0
        carroLaranja.moveCima(37); // Cruzamento
        carroLaranja.rotate(90);
        carroLaranja.moveTras(95); // Rua 5

        SemLA[0].acquire(); // Semaforo Laranja e Azul
        carroLaranja.moveTras(36); // Cruzamento
        carroLaranja.moveTras(95); // Rua 4

        carroLaranja.moveTras(36); // Cruzamento
        carroLaranja.rotate(0);
        carroLaranja.moveBaixo(30); // Cruzamento + Pedaco Rua 34
        SemLA[0].release(); // Semaforo Laranja e Azul
        SemVL[5].release(); // Semaforo Vermelho e Laranja
        SemALr[0].release(); //Semaforo Amarelo e Laranja

        carroLaranja.moveBaixo(44); // Rua 34

        SemVL[0].acquire(); // Semaforo Vermelho e Laranja
        carroLaranja.moveBaixo(37); // Cruzamento
        carroLaranja.moveBaixo(30); // Cruzamento + Pedaco Rua 40
        SemLRx[0].release(); // Semaforo Laranja e Roxo
        SemVL[0].release(); // Semaforo Vermelho e Laranja

        carroLaranja.moveBaixo(44); // Rua 40
        break; // Fim Carro Laranja

      case 5: // Carro Azul
        SemAzCz[0].acquire(); // Semaforo Azul e Cinza
        SemAzRx[0].acquire(); // Semaforo Azul e Roxo
        SemVAz[3].acquire(); // Semaforo Vermelho / Azul
        carroAzul.moveCima(37); // Cruzamento
        carroAzul.moveCima(30); // Cruzamento + Pedaco Rua 39
        SemVAz[3].release(); // Semaforo Vermelho / Azul

        carroAzul.moveCima(44); // Rua 39

        SemVAz[1].acquire(); // Semaforo Vermelho / Azul
        carroAzul.moveCima(37); // Cruzamento
        carroAzul.moveCima(30); // Cruzamento + Pedaco Rua 33
        SemAzRx[0].release(); // Semaforo Azul e Roxo
        SemVAz[1].release(); // Semaforo Vermelho / Azul

        carroAzul.moveCima(44); // Rua 33
        
        SemVAz[0].acquire(); // Semaforo Vermelho / Azul // Amarelo e Azul // Ciano e Azul
        carroAzul.moveCima(37); // Cruzamento
        carroAzul.rotate(90);
        carroAzul.moveFrente(95); // Rua 3

        SemLA[0].acquire(); // Semaforo Laranja e Azul
        carroAzul.moveFrente(36); // Cruzamento
        carroAzul.moveFrente(30); // Cruzamento + Pedaco Rua 4
        SemAzCz[0].release(); // Semaforo Azul e Cinza

        carroAzul.moveFrente(65); // Rua 4
        carroAzul.moveFrente(36); // Cruzamento
        carroAzul.rotate(180);
        carroAzul.moveBaixo(30); // Cruzamento  + Pedaco Rua 35
        SemLA[0].release(); // Semaforo Laranja e Azul
        SemVAz[0].release(); // Semaforo Vermelho / Azul // Amarelo e Azul // Ciano e Azul

        carroAzul.moveBaixo(44); // Rua 35

        SemVAz[2].acquire(); // Semaforo Vermelho / Azul // Amarelo e Azul
        carroAzul.moveBaixo(37); // Cruzamento
        carroAzul.moveBaixo(30); // Cruzamento + Pedaco Rua 41
        SemVAz[2].release(); // Semaforo Vermelho / Azul // Amarelo e Azul

        carroAzul.moveBaixo(44); // Rua 41

        SemAzRx[1].acquire(); // Semaforo Azul e Roxo
        SemVAz[4].acquire(); // Semaforo Vermelho / Azul // Ciano e AZUL
        carroAzul.moveBaixo(37); // Cruzamento
        carroAzul.moveBaixo(30); // Cruzamento + Pedaco Rua 47
        SemVAz[4].release(); // Semaforo Vermelho / Azul // Ciano e Azul

        carroAzul.moveBaixo(44); // Rua 47

        SemVAz[6].acquire(); // Semaforo Vermelho / Azul // Amarelo e Azul
        SemVeA[0].acquire(); // Semaforo Verde e Azul
        carroAzul.moveBaixo(37); // Cruzamento
        carroAzul.moveBaixo(30); // Cruzamento + Pedaco Rua 53
        SemAzRx[1].release(); // Semaforo Azul e Roxo
        SemVeA[0].release(); // Semaforo Verde e Azul
        
        carroAzul.moveBaixo(44); // Rua 53
        carroAzul.moveBaixo(37); // Cruzamento
        carroAzul.moveBaixo(30); // Cruzamento + Pedaco Rua 59
        SemVAz[6].release(); // Semaforo Vermelho / Azul // Amarelo e Azul

        carroAzul.moveBaixo(44); // Rua 59

        SemVAz[8].acquire(); // Semaforo Vermelho / Azul
        SemLA[1].acquire(); // Laranja e Azul
        SemAAz[0].acquire(); // Semaforo Amarelo e Azul
        carroAzul.moveBaixo(37); // Cruzamento
        carroAzul.rotate(-90);
        carroAzul.moveTras(95); // Rua 29

        SemAzCz[1].acquire(); // Semaforo Azul e Cinza
        carroAzul.moveTras(36); // Cruzamento
        carroAzul.moveTras(30); // Cruzamento + Pedaco Rua 28
        SemAAz[0].release(); // Amarelo e Azul
        SemLA[1].release(); // Laranja e Azul
        carroAzul.moveTras(65); // Rua 28

        SemAAz[1].acquire(); // Semaforo Amarelo e Azul
        carroAzul.moveTras(36); // Cruzamento
        carroAzul.rotate(0);
        carroAzul.moveCima(30); // Cruzamento + Pedaco Rua 57
        SemVAz[8].release(); // Semaforo Vermelho / Azul

        carroAzul.moveCima(44); // Rua 57

        SemVAz[7].acquire();
        carroAzul.moveCima(37); // Cruzamento
        carroAzul.moveCima(30); // Cruzamento + Pedaco Rua 51
        SemVAz[7].release();

        SemAAz[1].release(); //Semaforo Amarelo e Azul

        carroAzul.moveCima(44); // Rua 51

        SemVAz[5].acquire();
        carroAzul.moveCima(37); // Cruzamento
        carroAzul.moveCima(30); // Cruzamento + Pedaco Rua 45
        SemAzCz[1].release(); // Semaforo Azul e Cinza
        SemVAz[5].release();

        carroAzul.moveCima(44); // Rua 45
        break; // Fim Carro Azul

      case 6: // Carro Roxo
        SemRCz[1].acquire(); // Semaforo Roxo e Cinza
        SemVR[3].acquire(); // Semaforo Vermelho e Roxo
        carroRoxo.moveCima(37); // Cruzamento
        carroRoxo.rotate(90);
        carroRoxo.moveFrente(95); // Rua 12

        SemAzRx[0].acquire(); // Semaforo Azul e Roxo
        carroRoxo.moveFrente(36); // Cruzamento
        carroRoxo.rotate(0);
        carroRoxo.moveCima(34); // Cruzamento + Pedaco Rua 39
        SemVR[3].release(); // Semaforo Vermelho e Roxo

        carroRoxo.moveCima(44); // Rua 39

        SemVR[1].acquire(); // Semaforo Vermelho e Roxo
        carroRoxo.moveCima(37); // Cruzamento
        carroRoxo.rotate(90);
        carroRoxo.moveFrente(30); // Cruzamento + Pedaco Rua 8
        SemRCz[1].release(); // Semaforo Roxo e Cinza
        SemAzRx[0].release(); // Semaforo Azul e Roxo

        carroRoxo.moveFrente(65); // Rua 8
        SemLRx[0].acquire(); //Semaforo laranja e Roxo
        SemRCz[0].acquire(); // Semaforo Roxo e Cinza
        carroRoxo.moveFrente(36); // Cruzamento
        carroRoxo.rotate(0);
        carroRoxo.moveCima(30); // Cruzamento + Pedaco Rua 34
        SemVR[1].release(); // Semaforo Vermelho e Roxo

        carroRoxo.moveCima(44); // Rua 34

        SemARx[0].acquire(); // Semaforo Amarelo e Roxo
        SemVL[5].acquire(); // Semaforo Vermelho e Laranja // Vermelho e Roxo
        SemLA[0].acquire(); // Semaforo Laranja e Azul // Azul e Roxo
        carroRoxo.moveCima(37); // Cruzamento
        carroRoxo.rotate(90);
        carroRoxo.moveFrente(30); //Cruzamento + Pedaco Rua 4
        SemRCz[0].release(); // Semaforo Roxo e Cinza

        carroRoxo.moveFrente(65); // Rua 4
        carroRoxo.moveFrente(36); // Cruzamento
        carroRoxo.moveFrente(30); // Cruzamento + Pedaco Rua 5
        SemLA[0].release(); // Semaforo Laranja e Azul // Azul e Roxo

        carroRoxo.moveFrente(65); // Rua 5
        carroRoxo.moveFrente(36); // Cruzamento
        carroRoxo.rotate(180);
        carroRoxo.moveBaixo(74); // Rua 36

        SemAV[1].acquire(); // Semaforo Amarelo e Verde // Semaforo Verde e Roxo
        carroRoxo.moveBaixo(37); // Cruzamento
        carroRoxo.moveBaixo(30); // Cruzamento + Pedaco Rua 42
        SemVL[5].release(); // Semaforo Vermelho e Laranja // Vermelho e Roxo

        carroRoxo.moveBaixo(44); // Rua 42

        SemVR[2].acquire(); // Semaforo Vermelho e Roxo
        carroRoxo.moveBaixo(37); // Cruzamento
        carroRoxo.rotate(-90);
        carroRoxo.moveTras(30); // Cruzamento + Pedaco Rua 15
        SemLRx[0].release(); //Semaforo laranja e Roxo
        SemAV[1].release(); // Semaforo Amarelo e Verde // Semaforo Verde e Roxo

        carroRoxo.moveTras(65); // Rua 15

        SemAzRx[1].acquire(); // Semaforo Azul e Roxo
        carroRoxo.moveTras(36); // Cruzamento
        carroRoxo.rotate(180);
        carroRoxo.moveBaixo(30); // Cruzamento + Pedaco Rua 47
        SemVR[2].release(); // Semaforo Vermelho e Roxo
        SemARx[0].release(); // Semaforo Amarelo e Roxo

        carroRoxo.moveBaixo(44); // Rua 47

        SemRCz[2].acquire(); // Semaforo Roxo e Cinza
        SemVR[4].acquire();  // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Amarelo e Roxo // Verde e Roxo
        carroRoxo.moveBaixo(37); // Cruzamento
        carroRoxo.rotate(-90);
        carroRoxo.moveTras(30); // Cruzamento + Pedaco rua 19
        SemAzRx[1].release(); // Semaforo Azul e Roxo

        carroRoxo.moveTras(65); // Rua 19

        SemLRx[1].acquire(); //Semaforo laranja e Roxo
        carroRoxo.moveTras(36); // Cruzamento
        carroRoxo.rotate(180);
        carroRoxo.moveBaixo(30); // Cruzamento + Pedaco Rua 52
        SemVR[4].release();  // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Amarelo e Roxo // Verde e Roxo

        carroRoxo.moveBaixo(44); // Rua 52

        SemARx[1].acquire(); // Semaforo Amarelo e Roxo
        SemVA[3].acquire(); // Semaforo Vermelho e Amarelo // Vermelho e Roxo
        
        carroRoxo.moveBaixo(37); // Cruzamento
        carroRoxo.rotate(-90);
        carroRoxo.moveTras(30); // Cruzamento + Pedaco Rua 23
        SemRCz[2].release(); // Semaforo Roxo e Cinza
        SemLRx[1].release(); //Semaforo laranja e Roxo

        carroRoxo.moveTras(65); // Rua 23
        SemAAz[1].acquire(); // Semaforo Amarelo e Azul // Azul e Roxo
        carroRoxo.moveTras(36); // Cruzamento
        carroRoxo.rotate(180);
        carroRoxo.moveBaixo(30); // Cruzamento + Pedaco Rua 57
        SemVA[3].release(); // Semaforo Vermelho e Amarelo // Vermelho e Roxo

        carroRoxo.moveBaixo(44); // Rua 57

        SemVR[7].acquire(); // Semaforo Vermelho e Roxo
        carroRoxo.moveBaixo(37); // Cruzamento
        carroRoxo.rotate(-90);
        carroRoxo.moveTras(30); // Cruzamento + Pedaco Rua 27
        SemAAz[1].release(); // Semaforo Amarelo e Azul // Azul e Roxo

        carroRoxo.moveTras(65); // Rua 27

        SemARx[2].acquire(); // Semaforo Amarelo e Roxo
        SemARx[1].release(); // Semaforo Amarelo e Roxo
        
        carroRoxo.moveTras(36); // Cruzamento
        carroRoxo.moveTras(95); // Rua 26
        carroRoxo.moveTras(36); // Cruzamento
        carroRoxo.rotate(0);
        carroRoxo.moveCima(74); // Rua 55
        carroRoxo.moveCima(37); // Cruzamento
        carroRoxo.moveCima(30); // Cruzamento + Pedaco Rua 49
        SemVR[7].release(); // Semaforo Vermelho e Roxo

        carroRoxo.moveCima(44); // Rua 49

        SemVR[5].acquire(); // Semaforo Vermelho e Roxo
        carroRoxo.moveCima(37); // Cruzamento
        carroRoxo.rotate(90);
        carroRoxo.moveFrente(30); // Cruzamento + Pedaco Rua 16
        SemARx[2].release(); // Semaforo Amarelo e Roxo

        carroRoxo.moveFrente(65); // Rua 16
        carroRoxo.moveFrente(36); // Cruzamento
        carroRoxo.rotate(0);
        carroRoxo.moveCima(30); // Cruzamento + Pedaco Rua 44
        SemVR[5].release(); // Semaforo Vermelho e Roxo

        carroRoxo.moveCima(44); // Rua 44
        break; // Fim Carro Roxo

      case 7: // Carro Cinza
      carroCinza.rotate(180);
        SemVCz[3].acquire(); // Semaforo Vermelho e Cinza
        SemVVe[1].acquire(); // Semaforo Vermelho e Verde // Laranja e Cinza
        carroCinza.moveFrente(36); // Cruzamento
        carroCinza.rotate(90);
        carroCinza.moveCima(74); // Rua 48

        SemVA[1].acquire();
        SemVR[2].acquire(); // Vermelho e Roxo // Cinza e Roxo
        carroCinza.moveCima(37); // Cruzamento
        carroCinza.rotate(0);
        carroCinza.moveTras(30); // Cruzamento + Pedaco Rua 15
        SemVVe[1].release(); // Semaforo Vermelho e Verde // Laranja e Cinza
        SemVeCz[0].release(); // Semaforo Especial INICIADO COM 0

        carroCinza.moveTras(65); // Rua 15

        SemVAz[4].acquire(); // Semaforo Vermelho e Azul // Cinza e Azul
        carroCinza.moveTras(36); // Cruzamento
        carroCinza.moveTras(30); // Cruzamento + Pedaco Rua 14
        SemVR[2].release(); // Vermelho e Roxo // Cinza e Roxo
        SemVAz[4].release(); // Semaforo Vermelho e Azul // Cinza e Azul

        carroCinza.moveTras(65); // Rua 14

        SemAzCz[0].acquire(); // Semaforo Azul e Cinza
        SemACz[2].acquire(); // Semaforo Amarelo e Cinza
        SemLCz1.acquire(); // Semaforo Especial 0 // Laranja e Cinza
        
        
        carroCinza.moveTras(36); // Cruzamento
        carroCinza.rotate(90);
        carroCinza.moveCima(30); // Cruzamento + Pedaco Rua 40
        SemVCz[3].release(); // Semaforo Vermelho e Cinza
        SemVA[1].release();
        
        carroCinza.moveCima(44); // Rua 40
        
        SemRCz[0].acquire(); // Semaforo Roxo e Cinza
        SemVL[0].acquire(); // Semaforo Vermelho e Laranja // Vermelho e Cinza // Verde e Cinza
        carroCinza.moveCima(37); // Cruzamento
        carroCinza.moveCima(30); // Cruzamento + Pedaco da Rua 34
        SemVL[0].release(); // Semaforo Vermelho e Laranja // Vermelho e Cinza // Verde e Cinza
        
        carroCinza.moveCima(44); // Rua 34
        SemVCz[0].acquire(); // Semaforo Vermelho e Cinza // Amarelo e Cinza
        carroCinza.moveCima(37); // Cruzamento
        carroCinza.rotate(0);
        carroCinza.moveTras(30); // Cruzamento + Pedaco Rua 3
        SemRCz[0].release(); // Semaforo Roxo e Cinza
        SemLCz1.release(); // Semaforo Especial 0 // Laranja e Cinza

        carroCinza.moveTras(65); // Rua 3
        carroCinza.moveTras(36); // Cruzamento
        carroCinza.rotate(-90);
        carroCinza.moveBaixo(30); // Cruzamento + Pedaco Rua 33
        SemVCz[0].release(); // Semaforo Vermelho e Cinza // Amarelo e Cinza
        SemACz[2].release(); // Semaforo Amarelo e Cinza
        carroCinza.moveBaixo(44); // Rua 33

        SemRCz[1].acquire(); // Semaforo Roxo e Cinza
        SemVAz[1].acquire(); // Semaforo Vermelho e Azul // Vermelho e Cinza
        carroCinza.moveBaixo(37); // Cruzamento
        carroCinza.moveBaixo(30); // Cruzamento + Pedaco Rua 39
        SemVAz[1].release(); // Semaforo Vermelho e Azul // Vermelho e Cinza

        carroCinza.moveBaixo(44); // Rua 39
        SemVCz[1].acquire(); // Semaforo Vermelho e Cinza
        SemACz[1].acquire(); // Semaforo Amarelo e Cinza
        SemCcz[0].acquire(); // Semaforo Especial iniciado com 0 // Ciano e Cinza
        SemVR[3].acquire(); // Semaforo Vermelho e Roxo // Vermelho e Cinza
        carroCinza.moveBaixo(37); // Cruzamento
        carroCinza.rotate(0);
        carroCinza.moveTras(30); // Cruzamento + Pedaco Rua 12
        SemAzCz[0].release(); // Semaforo Azul e Cinza

        carroCinza.moveTras(65); // Rua 12
        carroCinza.moveTras(36); // Cruzamento
        carroCinza.moveTras(30); // Cruzamento + Pedaco Rua 11
        SemRCz[1].release(); // Semaforo Roxo e Cinza
        SemVR[3].release(); // Semaforo Vermelho e Roxo // Vermelho e Cinza

        carroCinza.moveTras(65); // Rua 11


        carroCinza.moveTras(36); // Cruzamento
        carroCinza.rotate(-90);
        carroCinza.moveBaixo(30); // Cruzamento + Pedaco Rua 43
        SemCcz[0].release(); // Semaforo Especial iniciado com 0 // Ciano e Cinza
        
        carroCinza.moveBaixo(44); // Rua 43
        SemVR[5].acquire(); // Semaforo Vermelho e Roxo // Roxo e Cinza
        carroCinza.moveBaixo(37); // Cruzamento
        carroCinza.rotate(180);
        carroCinza.moveFrente(30); // Cruzamento + Pedaco Rua 16
        SemACz[1].release();

        carroCinza.moveFrente(65); // Rua 16
        carroCinza.moveFrente(36); // Cruzamento
        carroCinza.moveFrente(30); // Cruzamento + Pedaco Rua 17
        SemVR[5].release(); // Semaforo Vermelho e Roxo // Roxo e Cinza

        carroCinza.moveFrente(65); // Rua 17
        SemVCz[2].acquire(); // Semaforo Vermelho e Cinza
        SemAzCz[1].acquire(); // Semaforo Azul e Cinza
        carroCinza.moveFrente(36); // Cruzamento
        carroCinza.rotate(-90);
        carroCinza.moveBaixo(30); // Cruzamento + Pedaco Rua 51
        SemVCz[1].release(); // Semaforo Vermelho e Cinza

        carroCinza.moveBaixo(44); // Rua 51

        SemLCz[0].acquire(); // Semaforo Laranja e Cinza
        SemACz[0].acquire(); // Semaforo Amarelo e Cinza
        SemAAz[1].acquire(); // Semaforo Amarelo e Azul // Amarelo e Cinza
        SemVAz[7].acquire(); // Semaforo Vermelho e Azul // Vermelho e Roxo
        carroCinza.moveBaixo(37); // Cruzamento
        carroCinza.moveBaixo(30); // Cruzamento + Pedaco Rua 57
        SemVAz[7].release(); // Semaforo Vermelho e Azul // Vermelho e Roxo

        carroCinza.moveBaixo(44); // Rua 57

        carroCinza.moveBaixo(37); // Cruzamento
        carroCinza.rotate(180);
        carroCinza.moveFrente(30); // Cruzamento + Pedaco Rua 28
        SemAAz[1].release(); // Semaforo Amarelo e Azul // Amarelo e Cinza

        carroCinza.moveFrente(65); // Rua 28

        carroCinza.moveFrente(36); // Cruzamento
        carroCinza.rotate(90);
        carroCinza.moveCima(30); // Cruzamento + Pedaco Rua 58
        SemAzCz[1].release(); // Semaforo Azul e Cinza
        SemVCz[2].release(); // Semaforo Vermelho e Cinza

        carroCinza.moveCima(44); // Rua 58

        SemRCz[2].acquire(); // Semaforo Roxo e Cinza
        SemVL[3].acquire(); // Semaforo Vermelho e Laranja // Vermelho e Cinza
        carroCinza.moveCima(37); // Cruzamento
        carroCinza.moveCima(30); // Cruzamento + Pedaco Rua 52
        SemVL[3].release(); // Semaforo Vermelho e Laranja // Vermelho e Cinza
        SemACz[0].release(); // Semaforo Amarelo e Cinza

        carroCinza.moveCima(44); // Rua 52

        SemVeCz[0].acquire(); // Semaforo Especial INICIADO COM 0
        SemVR[4].acquire(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Amarelo e Cinza
        carroCinza.moveCima(37); // Cruzamento
        carroCinza.rotate(180);
        carroCinza.moveFrente(30); // Cruzamento + Pedaco Rua 19
        SemLCz[0].release(); // Semaforo Laranja e Cinza

        carroCinza.moveFrente(65); // Rua 19
        SemVeA[0].acquire(); //Semaforo Verde e Amarelo // Azul e Cinza
        carroCinza.moveFrente(36); // Cruzamento
        carroCinza.moveFrente(30); // Cruzamento + Pedaco da Rua 20
        SemRCz[2].release(); // Semaforo Roxo e Cinza
        SemVeA[0].release(); //Semaforo Verde e Amarelo // Azul e Cinza
        SemVR[4].release(); // Semaforo Vermelho e Roxo // Vermelho e Cinza  // Amarelo e Verde // Amarelo e Cinza
        

        carroCinza.moveFrente(65); // Rua 20
        break; // Fim Carro Cinza
      default:
        break;
    } // Fim Switch
  } // Fim Percorre Percurso

    //Metodo de pegar as velocidades dos Sliders retornando 
    //o valor Inteiro para colocar no sleep das Threads
  public int getVelocidade(int id) {
    switch (id) {
      case 0:
        double aux = sliderVermelho.getValue();
        int retorno = (int) aux;
        return retorno;
      case 1:
        double aux1 = sliderAmarelo.getValue();
        int retorno1 = (int) aux1;
        return retorno1;
      case 2:
        double aux2 = sliderVerde.getValue();
        int retorno2 = (int) aux2;
        return retorno2;
      case 3:
        double aux3 = sliderCiano.getValue();
        int retorno3 = (int) aux3;
        return retorno3;
      case 4:
        double aux4 = sliderLaranja.getValue();
        int retorno4 = (int) aux4;
        return retorno4;
      case 5:
        double aux5 = sliderAzul.getValue();
        int retorno5 = (int) aux5;
        return retorno5;
      case 6:
        double aux6 = sliderRoxo.getValue();
        int retorno6 = (int) aux6;
        return retorno6;
      case 7:
        double aux7 = sliderCinza.getValue();
        int retorno7 = (int) aux7;
        return retorno7;
      default:
        return 0;
    } // Fim Switch
  } // Fim getVelocidade


    //Metodos de Pausar as Threads
  @FXML
  void clickPausaVermelho(MouseEvent event) {
    if (pausaVermelho.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroVermelho.suspend(); // Suspende
      pausaVermelho.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroVermelho.resume(); // Retorna a Thread
      pausaVermelho.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaAmarelo(MouseEvent event) {
    if (pausaAmarelo.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroAmarelo.suspend(); // Suspende
      pausaAmarelo.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroAmarelo.resume(); // Retorna a Thread
      pausaAmarelo.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaAzul(MouseEvent event) {
    if (pausaAzul.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroAzul.suspend(); // Suspende
      pausaAzul.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroAzul.resume(); // Retorna a Thread
      pausaAzul.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaCiano(MouseEvent event) {
    if (pausaCiano.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroCiano.suspend(); // Suspende
      pausaCiano.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroCiano.resume(); // Retorna a Thread
      pausaCiano.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaCinza(MouseEvent event) {
    if (pausaCinza.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroCinza.suspend(); // Suspende
      pausaCinza.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroCinza.resume(); // Retorna a Thread
      pausaCinza.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaLaranja(MouseEvent event) {
    if (pausaLaranja.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroLaranja.suspend(); // Suspende
      pausaLaranja.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroLaranja.resume(); // Retorna a Thread
      pausaLaranja.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaRoxo(MouseEvent event) {
    if (pausaRoxo.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroRoxo.suspend(); // Suspende
      pausaRoxo.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroRoxo.resume(); // Retorna a Thread
      pausaRoxo.setText("Pausar"); // Altera o nome do Botão
    }
  }

  @FXML
  void clickPausaVerde(MouseEvent event) {
    if (pausaVerde.getText().equals("Pausar")) { // Verifica o Nome do Botão
      carroVerde.suspend(); // Suspende
      pausaVerde.setText("Retomar"); // Troca o Nome do Botão
    } else {
      carroVerde.resume(); // Retorna a Thread
      pausaVerde.setText("Pausar"); // Altera o nome do Botão
    }
  }

} // The End
