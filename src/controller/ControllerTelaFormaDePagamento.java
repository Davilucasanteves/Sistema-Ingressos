package controller;

import java.io.IOException;

import data.RepositorioFesta;
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
import model.IFesta;
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
    private int quantidadeComprando = 0;  // Quantidade de ingressos que o usuário deseja comprar
    private IFesta bancoDeDadosFestas = new RepositorioFesta();

    public void setFesta(Festa festa, int quantidadeComprando) {
        this.festa = festa;
        this.quantidadeComprando = quantidadeComprando;
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
            valorCalculado = meiaIngresso.getValor() * this.quantidadeComprando;
        } else if ("INTEIRA".equalsIgnoreCase(tipo) && festa.getIngresso() instanceof Inteira) {
            Inteira inteiraIngresso = (Inteira) festa.getIngresso();
            valorCalculado = inteiraIngresso.getValor() * this.quantidadeComprando;
        } else {
            valorCalculado = festa.getIngresso().getValor() * this.quantidadeComprando; 
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
        
        if (festa.getQuantidade()>= quantidadeComprando) {
            int novaQuantidadeDisponivel = festa.getQuantidade() - quantidadeComprando;
            festa.getIngresso().setQuantidade(novaQuantidadeDisponivel);
            
            bancoDeDadosFestas.updateFesta(festa);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Você quis ingressos demais!");
            alert.setHeaderText("Pagamento cancelado!");
        }
        
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Pagamento realizado!");
        
        if ("Pix".equalsIgnoreCase(metodo)) {
            alert.setContentText("Obrigado pelo Pix! :).\nVocê comprou " + quantidadeComprando + " ingresso(s).");
        } else if ("Boleto".equalsIgnoreCase(metodo)) {
            alert.setContentText("Lá vai mais um boleto!\nVocê comprou " + quantidadeComprando + " ingresso(s).");
        } else {
            alert.setContentText("Pagamento processado com sucesso via " + metodo + ".\nVocê comprou " + quantidadeComprando + " ingresso(s).");
        }
        alert.showAndWait();

        try {
            trocarTela(anchorPaneTelaPagamento, "/view/TelaIngressosDisponiveis.fxml");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a Tela IngressosDisponiveis: " + e.getMessage());
            e.printStackTrace();
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
