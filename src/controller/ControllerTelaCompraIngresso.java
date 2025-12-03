package controller;

import java.io.IOException;

import data.RepositorioFesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public void initialize(Festa festa) {
        this.festaSelecionada = festa;
        if (festa != null) {
            labelNomeFesta.setText("" + festa.getNome());
            labelValor.setText(""+ festa.getIngresso().getValor());
        }
    }

    //private Stage dialogStage;

    @FXML
    void irACompra(ActionEvent event) {
        if (!this.TFQuantidade.getText().isEmpty() && !TFTipo.getText().isEmpty()) {
            String novoTipo = this.TFTipo.getText().toUpperCase();
            int novaQuantidade = Integer.parseInt(this.TFQuantidade.getText());

            this.festaSelecionada.getIngresso().setTipo(novoTipo);
            this.festaSelecionada.getIngresso().setQuantidade(novaQuantidade);

            bancoDeDadosFestas.updateFesta(festaSelecionada);

            try{
                trocarTela(anchorPaneCompraIngresso, "/view/TelaFormaDePagamento.fxml");
            } catch(IOException ex){
                System.err.println("Erro ao tentar abrir a etapa de pagamento: " + ex.getMessage());
                ex.printStackTrace();
            }

            //this.dialogStage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Dados inválidos.");
                alert.setContentText("Por favor, inclua Tipo e Quantidade válidos.");
                alert.showAndWait();
        }
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
