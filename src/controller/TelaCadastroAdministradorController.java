package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.RepositorioAdministrador;
import data.RepositorioCadastro;
import data.RepositorioCliente;
import model.Administrador;
import model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import data.*;
import model.*;

public class TelaCadastroAdministradorController {

    @FXML
    private Button buttonSalvarAdministradorCadastro;

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
    private RepositorioAdministrador repositorioAdministrador = new RepositorioAdministrador(); 
    
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
        textFieldIdade.clear();
        textFieldEmail.clear();
        textFieldSenha.clear();
        labelStatusCadastro.setText("");
    }    

    @FXML
    void handleButtonSalvarAdministradorCadastro(ActionEvent event) throws Exception{
        if (!textFieldNome.getText().isEmpty() && !textFieldCPF.getText().isEmpty() &&!textFieldTelefone.getText().isEmpty() && !textFieldEmail.getText().isEmpty() && !textFieldSenha.getText().isEmpty()){
            if(repositorioCadastro.emailDisponivel(textFieldEmail.getText())){
                if(repositorioAdministrador.cpfDisponivel(textFieldCPF.getText())){
                    String nome = textFieldNome.getText().toUpperCase();
                    String cpf = textFieldCPF.getText().toUpperCase();
                    int telefone = Integer.parseInt(textFieldTelefone.getText());
                    String email = textFieldEmail.getText().toUpperCase();
                    String senha = textFieldSenha.getText().toUpperCase();
                    Administrador administrador = new Administrador(nome, cpf, telefone, email, senha);
                    repositorioAdministrador.createAdministrador(administrador);
                    repositorioCadastro.createCadastro(administrador);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaAdministrador.fxml"));
                    Parent root = loader.load();
                    Scene novaCena = new Scene(root);

                    Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stageAtual.setScene(novaCena);
                    stageAtual.show();
                }
                else {
                    labelStatusCadastro.setText("Vôce já possui um cadastro de administrador");
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
    
    private void abrirTela(String caminhoFXML, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();

        Stage atual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        atual.close();
    }

    @FXML
    void handleVoltar(ActionEvent event) {
        try {
            abrirTela("/view/TelaCriarCadastro.fxml", event);
        } catch (IOException ex) {
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}