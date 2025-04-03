package com.kansysaea.ind220_tp3s.Controllers;

import com.kansysaea.ind220_tp3s.IPProtocols.IPProtocol;
import com.kansysaea.ind220_tp3s.IPProtocols.TCPProtocol;
import com.kansysaea.ind220_tp3s.IPProtocols.UDPProtocol;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private TextField tfIPAddr, tfPort;
    @FXML
    private RadioButton rbTCP, rbUDP;
    @FXML
    private Button btnConnect;

    private IPProtocol socket;
    private String connectionType;

    public void initialize() {
    }

    @FXML
    private void onRadioToggle() {
        if (rbTCP.isSelected()) {
            rbUDP.setSelected(false);
            connectionType = "TCP";
        } else if (rbUDP.isSelected()) {
            rbTCP.setSelected(false);
            connectionType = "UDP";
        }
    }

    @FXML
    private void onConnectClick() throws IOException {
        // Si le socket est déjà ouvert, le fermer
        if (socket != null) {
            socket.close();
        }

        // Load le Socket_ClientUI.fxml & on get le controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/kansysaea/ind220_tp3s/Socket_ClientUI.fxml"));
        Parent root = fxmlLoader.load();
        SocketUIController controller = fxmlLoader.getController();

        // Selon le type de connexion sélectionné, crée le socket approprié
        switch (connectionType) {
            case "TCP":
                socket = new TCPProtocol(tfIPAddr.getText(), Integer.parseInt(tfPort.getText()), controller);
                break;
            case "UDP":
                socket = new UDPProtocol(tfIPAddr.getText(), Integer.parseInt(tfPort.getText()), controller);
                break;
        }

        // On passe le socket au controller
        controller.setSocket(socket);

        // On ouvre la nouvelle fenêtre UI Client
        Stage stage = (Stage) btnConnect.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Socket Client UI");
        stage.show();
    }
}
