
package controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaClienteController {

    @FXML
    private ImageView imageDeletarFesta;
    
    @FXML
    private Button buttonSair;

    @FXML
    private ImageView imageInformacoesIngressos;

    @FXML
    private ImageView imageVerIngressosAVenda;

    @FXML
    private TableColumn<?, ?> tableColunmnIDFesta;

    @FXML
    private TableColumn<?, ?> tableColunmnNomeFesta;

    @FXML
    void handleCriarFesta(MouseEvent event) {

    }

    
    @FXML
    void handleVerIngressosAVenda(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaIngressosDisponiveis.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();

        // Fecha a janela atual
        Stage atual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        atual.close();
    }

    @FXML
    void handleimageDeletarFesta(MouseEvent event) {

    }

    @FXML
    void handleimageInformacoesIngressos(KeyEvent event) {

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
            abrirTela("/View/TelaInicialSGVI.fxml", event);
        } catch (IOException ex) {
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}