package controller;

import java.io.IOException;

import data.RepositorioFesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Festa;
import model.IFesta;


public class ControllerTelaCompraIngresso {

    @FXML
    private AnchorPane anchorPaneCompraIngresso;

    @FXML
    private TextField TFQuantidade;

    @FXML
    private RadioButton radioButtonMeia;

    @FXML
    private RadioButton radioButtonInteira;

    @FXML
    private Button buttomComprar;

    @FXML
    private Button buttomVoltar;

    @FXML
    private Label labelValor;

     @FXML
    private Label labelNomeFesta;

    private IFesta bancoDeDadosFestas = new RepositorioFesta();

    private Festa festaSelecionada;
    private int quantidadeComprando = 0;  // Armazena a quantidade que o usuário deseja comprar

    @FXML
    public void initialize() {//initialize não pode ter parâmetro
        
    }

    public void setFestaSelecionada(Festa festa) {
        this.festaSelecionada = festa;
        this.atualizarComponentesDeFesta(); 
    }

    private void atualizarComponentesDeFesta() {
        if (this.festaSelecionada != null) {
            
            labelNomeFesta.setText("" + this.festaSelecionada.getNome() + "");
            
            if (this.festaSelecionada.getIngresso() != null) {
                labelValor.setText("R$ " + this.festaSelecionada.getIngresso().getValor());
            } else {
                labelValor.setText("Valor não definido.");
            }
        }
}

    @FXML
    void irACompra(ActionEvent event) throws IOException{
        if ((!this.TFQuantidade.getText().isEmpty()) && (radioButtonMeia.isSelected() || radioButtonInteira.isSelected())) {
            String novoTipo = this.radioButtonMeia.getText().toUpperCase();
            int quantidadeDesejada = Integer.parseInt(this.TFQuantidade.getText());
            
            // Verifica se a quantidade desejada não excede a quantidade disponível
            if (quantidadeDesejada > this.festaSelecionada.getQuantidade()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Quantidade insuficiente.");
                alert.setContentText("Não há " + quantidadeDesejada + " ingressos disponíveis. Máximo disponível: " + this.festaSelecionada.getQuantidade());
                alert.showAndWait();
                return;
            }
            
            // Armazena a quantidade que será comprada
            this.quantidadeComprando = quantidadeDesejada;

            // Atualiza apenas o tipo do ingresso (não a quantidade total)
            this.festaSelecionada.getIngresso().setTipo(novoTipo);
            
            // NÃO atualizamos a quantidade total aqui, apenas passamos para o próximo controller

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaFormaDePagamento.fxml"));
            Parent root = loader.load(); 

            ControllerTelaFormaDePagamento controllerPagamento = loader.getController();

            String tipoIngressoSelecionado = getTipoIngressoSelecionado();

            controllerPagamento.setFesta(festaSelecionada, this.quantidadeComprando, tipoIngressoSelecionado);

            trocarConteudo(anchorPaneCompraIngresso, root);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Dados inválidos.");
                alert.setContentText("Por favor, inclua Tipo(MEIA ou INTEIRA) e Quantidade(menor ou igual aos disponíveis) válidos.");
                alert.showAndWait();
        }
    }

    public String getTipoIngressoSelecionado() {
        if (radioButtonMeia.isSelected()) {
            return "MEIA";
        } else if (radioButtonInteira.isSelected()) {
            return "INTEIRA";
        }
        return "padrao";
    }

    private void trocarConteudo(AnchorPane telaAtual, Parent novoConteudo) {
        telaAtual.getChildren().setAll(novoConteudo);
    }

    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }

    @FXML
    void voltarFestas(ActionEvent event) {
        try{
                trocarTela(anchorPaneCompraIngresso, "/view/TelaIngressosDisponiveis.fxml");
        } catch(IOException ex){
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
                ex.printStackTrace();
        }
    }

}
