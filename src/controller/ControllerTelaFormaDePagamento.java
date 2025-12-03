package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Festa;
import model.Inteira;
import model.Meia;

public class ControllerTelaFormaDePagamento {

    @FXML
    private AnchorPane anchorPaneTelaPagamento;

    @FXML
    private Button buttomFinalizar;

    @FXML
    private Button buttomVoltar;

    @FXML
    private Label labelBoleto;

    @FXML
    private Label labelOutro;

    @FXML
    private Label labelPix;

    @FXML
    private Label labelValorTotal;

    @FXML
    private AnchorPane anchorPaneMetodosPagamento;

    private Festa festa;

    @FXML
    void realizarCompra(ActionEvent event) {
        if (festa == null || festa.getIngresso() == null) {
            labelValorTotal.setText(" N/A");
            return;
        }

        if ("Meia".equals(festa.getIngresso().getTipo()) && festa.getIngresso() instanceof Meia) {
            Meia meiaIngresso = (Meia) festa.getIngresso();
            labelValorTotal.setText(" " + meiaIngresso.calcularTotal());
        } else if ("Inteira".equals(festa.getIngresso().getTipo()) && festa.getIngresso() instanceof Inteira) {
            Inteira inteiraIngresso = (Inteira) festa.getIngresso();
            labelValorTotal.setText(" " + inteiraIngresso.calcularTotal());
        } else {
            labelValorTotal.setText(" " + festa.getIngresso().getTipo());
        }

        if (labelPix != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Pagamento realizado!");
                alert.setContentText("Obrigado pelo Pix! :).");
                alert.showAndWait();
        }else if (labelBoleto !=null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Pagamento realizado!");
                alert.setContentText("Lá vai mais um boleto!");
                alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Nenhum método foi selecionado.");
                alert.setContentText("Por favor, selecione um método de pagamento para realizar compra.");
                alert.showAndWait();
        }
    }

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }

    @FXML
    void voltarInfoIngresso(ActionEvent event) {
        try{
                trocarTela(anchorPaneTelaPagamento, "/view/TelaCompraIngresso.fxml");
        } catch(IOException ex){
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
                ex.printStackTrace();
        }
    }

}
