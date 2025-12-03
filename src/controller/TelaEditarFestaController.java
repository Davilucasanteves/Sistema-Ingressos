package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.RepositorioFesta;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Festa;
import model.IFesta;

public class TelaEditarFestaController {

    @FXML
    private Button buttonSalvarAlteracoes;

    @FXML
    private TextField textFieldData;

    @FXML
    private TextField textFieldDescricao;

    @FXML
    private TextField textFieldQtdIngresso;

    @FXML
    private TextField textFieldNome;

    @FXML
    private Label labelStatusEdicao;

    @FXML
    private AnchorPane anchorPaneEditarFesta;

    @FXML
    private TextField textFieldValorDoIngresso;

    @FXML
    private Button buttomVoltar;

    private IFesta repositorioFesta = new RepositorioFesta();
    private Festa festaEmEdicao;

    public void initialize(URL url, ResourceBundle rb) {
        // Inicialização automática
    }

    public void setFestaEmEdicao(Festa festa) {
        this.festaEmEdicao = festa;
        preencherCamposComDadosFesta();
    }

    private void preencherCamposComDadosFesta() {
        if (festaEmEdicao != null) {
            textFieldNome.setText(festaEmEdicao.getNome());
            textFieldData.setText(festaEmEdicao.getData());
            textFieldDescricao.setText(festaEmEdicao.getDescricao());
            textFieldQtdIngresso.setText(String.valueOf(festaEmEdicao.getQuantidade()));
            if (festaEmEdicao.getIngresso() != null) {
                textFieldValorDoIngresso.setText(String.valueOf(festaEmEdicao.getIngresso().getValor()));
            }
        }
    }

    @FXML
    void handleButtonSalvarAlteracoes(ActionEvent event) throws Exception {
        if (!textFieldNome.getText().isEmpty() && !textFieldData.getText().isEmpty() 
            && !textFieldQtdIngresso.getText().isEmpty() && !textFieldDescricao.getText().isEmpty()
            && !textFieldValorDoIngresso.getText().isEmpty()) {
            
            try {
                String nome = textFieldNome.getText().toUpperCase();
                String data = textFieldData.getText();
                String descricao = textFieldDescricao.getText().toUpperCase();
                int quantidade = Integer.parseInt(textFieldQtdIngresso.getText());
                double valor = Double.parseDouble(textFieldValorDoIngresso.getText());

                // Atualiza os dados da festa
                festaEmEdicao.setNome(nome);
                festaEmEdicao.setData(data);
                festaEmEdicao.setDescricao(descricao);
                
                // Atualiza a quantidade e valor do ingresso
                festaEmEdicao.getIngresso().setQuantidade(quantidade);
                festaEmEdicao.getIngresso().setValor(valor);

                // Atualiza a festa no repositório
                repositorioFesta.updateFesta(festaEmEdicao);

                labelStatusEdicao.setText("Festa atualizada com sucesso!");
                
                // Fecha a janela após 1 segundo
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        Platform.runLater(() -> {
                            Stage stage = (Stage) anchorPaneEditarFesta.getScene().getWindow();
                            stage.close();
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (NumberFormatException ex) {
                labelStatusEdicao.setText("Quantidade e Valor devem ser números válidos!");
            }
        } else {
            labelStatusEdicao.setText("Todos os campos são obrigatórios!");
        }
    }

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }

    @FXML
    void voltarAdm(){
        try {
            trocarTela(anchorPaneEditarFesta, "/View/TelaAdministrador.fxml");
        } catch (IOException ex) {
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
