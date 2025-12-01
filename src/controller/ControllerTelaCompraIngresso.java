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
    private TextField TFTipo;

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
        if ((!this.TFQuantidade.getText().isEmpty() && !TFTipo.getText().isEmpty()) && (TFTipo.getText().equalsIgnoreCase("MEIA") || TFTipo.getText().equalsIgnoreCase("INTEIRA"))) {
            String novoTipo = this.TFTipo.getText().toUpperCase();
            int novaQuantidade = Integer.parseInt(this.TFQuantidade.getText());

            this.festaSelecionada.getIngresso().setTipo(novoTipo);
            this.festaSelecionada.getIngresso().setQuantidade(novaQuantidade);

            bancoDeDadosFestas.updateFesta(festaSelecionada);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaFormaDePagamento.fxml"));
            Parent root = loader.load(); 

            ControllerTelaFormaDePagamento controllerPagamento = loader.getController();

            controllerPagamento.setFesta(festaSelecionada);

            trocarConteudo(anchorPaneCompraIngresso, root);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Dados inválidos.");
                alert.setContentText("Por favor, inclua Tipo(MEIA ou INTEIRA) e Quantidade(menor ou igual aos disponíveis) válidos.");
                alert.showAndWait();
        }
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
