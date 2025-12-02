
package controller;

import java.io.IOException;
import java.util.ResourceBundle;

import data.RepositorioCadastro;
import model.Administrador;
import model.Cadastro;
import model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import data.*;

public class TelaLoginController {
    
    RepositorioCadastro repositorioCadastro = new RepositorioCadastro();


    @FXML
    private Button buttonAcessar;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldSenha;
    @FXML
    private Label labelAcessoInvalido;

    @FXML
    void handleAcessarApp(ActionEvent event) {
            
        String email = textFieldEmail.getText();
        String senha = textFieldSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            labelAcessoInvalido.setText("Preencha todos os campos");
            textFieldEmail.requestFocus();
            return;
        }

        Cadastro cadastro = repositorioCadastro.readCadastro(email, senha);

        if (cadastro == null) {
            labelAcessoInvalido.setText("Email ou senha incorretos");
            return;
        }
        else {
            try {
            if (cadastro instanceof Administrador) {
                abrirTela("/View/TelaAdministrador.fxml");
            } else if (cadastro instanceof Cliente) {
                abrirTela("/View/TelaCliente.fxml");
            }

        } catch (IOException ex) {
            System.err.println("Erro ao fazer login: " + ex.getMessage());
            ex.printStackTrace();
        }
        }

    }

    @FXML
    void handleDigitarEmail(ActionEvent event) {

    }

    @FXML
    void handleDigitarSenha(ActionEvent event) {

    }



    private void abrirTela(String caminhoFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();

        Stage atual = (Stage) buttonAcessar.getScene().getWindow();
        atual.close();
    }

    @FXML
    void handleVoltar(ActionEvent event) throws Exception{
        try{
                abrirTela( "/view/TelaInicialSGVI.fxml");
        } catch(IOException ex){
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
                ex.printStackTrace();
        }
    }

}
