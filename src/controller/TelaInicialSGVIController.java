package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaInicialSGVIController{

    @FXML
    void handleEntrar(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaLogin.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();

        Stage atual = (Stage) ((Button) event.getSource()).getScene().getWindow();
        atual.close();
    }

    @FXML
    void handleCriarConta(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaCriarCadastro.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();
        
        Stage atual = (Stage) ((Button) event.getSource()).getScene().getWindow();
        atual.close();
    }

}
