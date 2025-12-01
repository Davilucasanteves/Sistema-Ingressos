package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.Festa;
import model.Inteira;
import model.Meia;

public class ControllerTelaFormaDePagamento {

    @FXML
    private AnchorPane anchorPaneTelaPagamento;

    @FXML
    private ToggleGroup metodoPagamentoGroup;

    @FXML
    private Button buttomFinalizar;

    @FXML
    private Button buttomVoltar;

    @FXML
    private RadioButton radioButtonBoleto;

    @FXML
    private RadioButton radioButtonOutro;

     @FXML
    private RadioButton radioButtonPix;

    @FXML
    private Label labelValorTotal;

    @FXML
    private AnchorPane anchorPaneMetodosPagamento;

    private Festa festa;

    public void setFesta(Festa festa) {
        this.festa = festa;
        this.atualizarValorTotal(); 
    }

    private void atualizarValorTotal() {
        if (festa == null || festa.getIngresso() == null) {
            labelValorTotal.setText("N/A");
            return;
        }

        String tipo= festa.getIngresso().getTipo();
        double valorCalculado= 0;
        
        
        if ("MEIA".equalsIgnoreCase(tipo) && festa.getIngresso() instanceof Meia) {
            Meia meiaIngresso = (Meia) festa.getIngresso();
            valorCalculado = meiaIngresso.calcularTotal();
        } else if ("INTEIRA".equalsIgnoreCase(tipo) && festa.getIngresso() instanceof Inteira) {
            Inteira inteiraIngresso = (Inteira) festa.getIngresso();
            valorCalculado = inteiraIngresso.calcularTotal();
        } else {
            valorCalculado = festa.getIngresso().getValor() * festa.getIngresso().getQuantidade(); 
        }
        
        labelValorTotal.setText(String.format("R$ %.2f", valorCalculado));
    }

    @FXML
    void realizarCompra(ActionEvent event) {
        
        if (metodoPagamentoGroup.getSelectedToggle() == null) {
            // Se nenhum RadioButton estiver selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum método foi selecionado.");
            alert.setContentText("Por favor, selecione um método de pagamento para realizar compra.");
            alert.showAndWait();
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) metodoPagamentoGroup.getSelectedToggle();
        String metodo = selectedRadioButton.getText(); 
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Pagamento realizado!");
        
        if ("Pix".equalsIgnoreCase(metodo)) {
            alert.setContentText("Obrigado pelo Pix! :).");
        } else if ("Boleto".equalsIgnoreCase(metodo)) {
            alert.setContentText("Lá vai mais um boleto!");
        } else {
            alert.setContentText("Pagamento processado com sucesso via " + metodo + ".");
        }
        alert.showAndWait();
        
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
