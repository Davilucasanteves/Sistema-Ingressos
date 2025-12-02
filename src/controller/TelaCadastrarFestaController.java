package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.RepositorioFesta;
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
import model.Festa;

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

    @FXML
    private AnchorPane anchorPaneCadastraFesta;

    @FXML
    private TextField textFieldValorDoIngresso;

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
        textFieldValorDoIngresso.clear();
        labelStatusCadastro.setText("");
    }    

    Festa festa = new Festa("0", "0", "0");

    @FXML
    void handleButtonSalvarFesta(ActionEvent event) throws Exception{
        if (!textFieldNome.getText().isEmpty() && !textFieldData.getText().isEmpty() && !textFieldQtdIngresso.getText().isEmpty() && !textFieldDescricao.getText().isEmpty()){
                    String nome = textFieldNome.getText().toUpperCase();
                    String data = textFieldData.getText().toUpperCase();
                    festa.setQuantidade(Integer.parseInt(textFieldQtdIngresso.getText()));
                    festa.setValor(Integer.parseInt(textFieldValorDoIngresso.getText()));
                    // int  qtdIngresso = Integer.parseInt(textFieldQtdIngresso.getText());
                    String descricao = textFieldDescricao.getText().toUpperCase();
                    festa.setNome(nome); festa.setData(data); festa.setDescricao(descricao);
                    repositorioFesta.createFesta(festa);
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

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }

    @FXML
    void voltarAdm(ActionEvent event) {
        try{
                trocarTela(anchorPaneCadastraFesta, "/View/TelaAdministrador.fxml");
        } catch(IOException ex){
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
                ex.printStackTrace();
        }
    }
}