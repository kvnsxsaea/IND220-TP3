package com.kansysaea.ind220_tp3s.Controllers;

import com.kansysaea.ind220_tp3s.IPProtocols.IPProtocol;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class SocketUIController {
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem menuItemSettings, menuItemSmile;
    @FXML
    private TextArea msgBoard;
    @FXML
    private TextField msgField;
    @FXML
    private Button btnSend;

    private IPProtocol protocol;

    @FXML
    void initialize() {
        msgBoard.setEditable(false);
        msgField.setOnAction(event -> sendMsg()); // sendMsg() quand on appuie sur Enter
    }

    private String generateTimestamp() {
        java.util.Date date = new java.util.Date();
        return String.format("[%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS]", date);
    }

    // Quand on reçoit un message du serveur, on le publie au msgBoard
    public void receiveMessage(String message) {
        // On utilise Platform.runLater pour mettre à jour le msgBoard sur le thread JavaFX
        // Bref, d'la magie noire de thread
        Platform.runLater(() -> msgBoard.appendText(generateTimestamp() + " Server: " + message + "\n"));
    }

    // Publie le message en paramètre sur le msgBoard et l'envoie sur le socket
    public void postMessage(String data) {
        try {
            protocol.sendData(data);
        } catch (Exception e) {
            System.err.println("Error sending data: " + e.getMessage());
        }
    }

    public void printMsgBoard(String data) {
        msgBoard.appendText(generateTimestamp() + " Client: " + data + "\n");
    }

    public void setSocket(IPProtocol protocol) {
        this.protocol = protocol;
    }

    @FXML
    // Fonction d'envoi de message FXML
    private void sendMsg() {
        postMessage(msgField.getText());
        msgField.clear();
    }

    @FXML
    // Ouvre le menu de settings
    private void openSettings() throws IOException {

        // Load le Socket_Settings.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/kansysaea/ind220_tp3s/Socket_Settings.fxml"));
        Parent root = fxmlLoader.load();

        // Get le stage actuel et set la nouvelle scène (Settings)
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    // Ferme le app
    private void closeApp() {
        // Ferme l'application
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void EGG() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        switch(randomNum){
            case 1 -> msgBoard.appendText(easterEgg_1());
            case 2 -> msgBoard.appendText(easterEgg_2());
            case 3 -> msgBoard.appendText(easterEgg_3());
        }
    }

    private String easterEgg_1(){
        return "\n  /\\ o o o\\\n" +
                " /o \\ o o o\\_______\n" +
                "<    >------>   o /|\n" +
                " \\ o/  o   /_____/o|\n" +
                "  \\/______/     |oo|\n" +
                "        |   o   |o/\n" +
                "        |_______|/\nAllez voir Dés Joués! (10, 11, 12 Avril)\n"+
                "https://www.etsmtl.ca/evenement/des-joues-creation-theatrale-des-raconteurs-dangles\n";
    }

    private String easterEgg_2(){
        return "\n_|_|_|  _|      _|  _|_|_|      _|_|      _|_|      _|   \n" +
                "  _|    _|_|    _|  _|    _|  _|    _|  _|    _|  _|  _| \n" +
                "  _|    _|  _|  _|  _|    _|      _|        _|    _|  _| \n" +
                "  _|    _|    _|_|  _|    _|    _|        _|      _|  _| \n" +
                "_|_|_|  _|      _|  _|_|_|    _|_|_|_|  _|_|_|_|    _|  \n";
    }

    private String easterEgg_3(){
        return  "\n" +
                " ____ ____ ____ _________ ____ ____ ____ \n" +
                "||L |||A |||N |||       |||E |||T |||S ||\n" +
                "||__|||__|||__|||_______|||__|||__|||__||\n" +
                "|/__\\|/__\\|/__\\|/_______\\|/__\\|/__\\|/__\\|\n" +
                "\n";
    }
}
