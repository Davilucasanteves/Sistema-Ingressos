
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class TelaCriarCadastroController {

    @FXML
    private Button buttonCriarNovoAdministrador;

    @FXML
    private Button buttonCriarNovoCliente;

    @FXML

    void handleButtonCriarNovoCliente(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaCadastroCliente.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();
        
        Stage atual = (Stage) ((Button) event.getSource()).getScene().getWindow();
        atual.close();
    }
    @FXML
    void handleButtonCriarNovoAdministrador(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaCadastroAdministrador.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();
        
        Stage atual = (Stage) ((Button) event.getSource()).getScene().getWindow();
        atual.close();

    }

}
