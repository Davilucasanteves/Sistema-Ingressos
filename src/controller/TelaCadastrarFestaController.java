package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import data.*;
import model.*;

public class TelaCadastrarFestaController {

    @FXML
    private Button buttonSalvarFesta;

    @FXML
    private TextField textFieldData;

    @FXML
    private TextField textFieldDescricao;

    @FXML
    private TextField textFieldQtdIngresso;

    @FXML
    private TextField textFieldNome;

    @FXML
    private Label labelStatusCadastro;

    private Stage dialogStage;
    private RepositorioFesta repositorioFesta = new RepositorioFesta(); 
    
    public void initialize(URL url, ResourceBundle rb) {
        limparCampos();
    }    
    
    public void setDialogStage (Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    private void limparCampos(){
        textFieldNome.clear();
        textFieldData.clear();
        textFieldDescricao.clear();
        textFieldQtdIngresso.clear();
        labelStatusCadastro.setText("");
    }    

    @FXML
    void handleButtonSalvarFesta(ActionEvent event) throws Exception{
        if (!textFieldNome.getText().isEmpty() && !textFieldData.getText().isEmpty() && !textFieldQtdIngresso.getText().isEmpty() && !textFieldDescricao.getText().isEmpty()){
                    String nome = textFieldNome.getText().toUpperCase();
                    String data = textFieldData.getText().toUpperCase();
                    int  qtdIngresso = Integer.parseInt(textFieldQtdIngresso.getText());
                    String descricao = textFieldDescricao.getText().toUpperCase();
                    repositorioFesta.createFesta(new Festa(nome, data, descricao, qtdIngresso));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaAdministrador.fxml"));
                    Parent root = loader.load();
                    Scene novaCena = new Scene(root);

                    Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stageAtual.setScene(novaCena);
                    stageAtual.show();
                }
        else {
                labelStatusCadastro.setText("Dados inválidos");
        }
    }
}