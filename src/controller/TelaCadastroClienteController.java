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

public class TelaCadastroClienteController {

    @FXML
    private Button buttonSalvarClienteCadastro;

    @FXML
    private TextField textFieldCPF;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldIdade;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldSenha;

    @FXML
    private TextField textFieldTelefone;

    @FXML
    private Label labelStatusCadastro;

    private Stage dialogStage;
    private RepositorioCadastro repositorioCadastro = new RepositorioCadastro(); 
    private RepositorioCliente repositorioCliente = new RepositorioCliente(); 
    
    public void initialize(URL url, ResourceBundle rb) {
        limparCampos();
    }    
    
    public void setDialogStage (Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    private void limparCampos(){
        textFieldNome.clear();
        textFieldCPF.clear();
        textFieldTelefone.clear();
        textFieldEmail.clear();
        textFieldSenha.clear();
        labelStatusCadastro.setText("");
        //textFielNomeDisciplina.requestFocus();
    }    
    @FXML
    void handleButtonSalvarClienteCadastro(ActionEvent event) throws Exception{
        if (!textFieldNome.getText().isEmpty() && !textFieldCPF.getText().isEmpty() &&!textFieldTelefone.getText().isEmpty() && !textFieldEmail.getText().isEmpty() && !textFieldSenha.getText().isEmpty()){
            if(repositorioCadastro.emailDisponivel(textFieldEmail.getText())){
                if(repositorioCliente.cpfDisponivel(textFieldCPF.getText())){
                    String nome = textFieldNome.getText().toUpperCase();
                    String cpf = textFieldCPF.getText().toUpperCase();
                    int telefone = Integer.parseInt(textFieldTelefone.getText());
                    String email = textFieldEmail.getText().toUpperCase();
                    String senha = textFieldSenha.getText().toUpperCase();
                    Cliente cliente = new Cliente(nome, cpf, telefone, email, senha);
                    repositorioCliente.createCliente(cliente);
                    repositorioCadastro.createCadastro(cliente);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaCliente.fxml"));
                    Parent root = loader.load();
                    Scene novaCena = new Scene(root);

                    Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stageAtual.setScene(novaCena);
                    stageAtual.show();
                }
                else {
                    labelStatusCadastro.setText("Vôce já possui um cadastro de cliente");
                }
            }
            else {
                labelStatusCadastro.setText("Já existe um cadastro com esse email");
            }
        }
        else {
                labelStatusCadastro.setText("Dados inválidos");
        }
    }
}