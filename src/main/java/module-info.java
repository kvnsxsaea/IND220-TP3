module com.kansysaea.ind220_tp3s {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kansysaea.ind220_tp3s to javafx.fxml;
    exports com.kansysaea.ind220_tp3s;
    exports com.kansysaea.ind220_tp3s.Controllers;
    opens com.kansysaea.ind220_tp3s.Controllers to javafx.fxml;
    exports com.kansysaea.ind220_tp3s.Server;
    opens com.kansysaea.ind220_tp3s.Server to javafx.fxml;
}