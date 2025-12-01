package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.RepositorioFesta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Festa;
import model.IFesta;

public class ControllerTelaIngressosDisponiveis {

    @FXML
    private AnchorPane anchorPaneIngressos;

    @FXML
    private Button buttonVerIngresso;

    @FXML
    private Button buttonVoltar;

    @FXML
    private TableColumn<Festa, Integer> columnId;

    @FXML
    private TableColumn<Festa, String> columnNome;

    @FXML
    private TableColumn<Festa, Integer> columnQuantidade;

    @FXML
    private TableView<Festa> tabelaFestas;

    private IFesta bancoDeDadosFestas = new RepositorioFesta();

    //@Override
    public void initialize() {
        tabelaFestas.setPlaceholder(new Label("Nenhuma Festa cadastrada."));
        carregarTabelaFesta();
    }

    private void carregarTabelaFesta() {
        //Liga as colunas da tabela (TableColumn) aos atributos (propriedades) dos objetos que estão sendo exibidos.
        columnId.setCellValueFactory(new PropertyValueFactory<Festa, Integer>("id")); //Liga a coluna tableColumnFestaId ao método getId() do objeto Festa.
        columnNome.setCellValueFactory(new PropertyValueFactory<Festa, String>("nome")); //Liga a coluna tableColumnFestaNome ao método getNome() do objeto Festa.
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<Festa, Integer>("quantidade"));

        //carregar os dados (lista de Festas) na tabela da interface
        ArrayList<Festa> listaFestas = bancoDeDadosFestas.getAllFestas();
        ObservableList<Festa> obsListFesta = FXCollections.observableArrayList(listaFestas);
        tabelaFestas.setItems(obsListFesta);
    }

    @FXML
    void verInfoIngresso(ActionEvent event) throws IOException {

        Festa festaSelecionada = tabelaFestas.getSelectionModel().getSelectedItem();
        
        // 1. VALIDAÇÃO: Se nenhuma festa foi selecionada, mostre o alerta e saia.
        if (festaSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhuma Festa foi selecionada.");
            alert.setContentText("Por favor, selecione uma Festa na tabela para poder ver seu ingresso.");
            alert.showAndWait();
            return; // Sai do método
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCompraIngresso.fxml"));
            Parent root = loader.load(); //root tem o conteúdo da nova tela
            
            //Obtendo controller e passando objeto
            ControllerTelaCompraIngresso controllerNovaTela = loader.getController();
            controllerNovaTela.setFestaSelecionada(festaSelecionada);
            
            trocarConteudo(anchorPaneIngressos, root);
            
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela CompraIngresso: " + ex.getMessage());
            ex.printStackTrace();
        }
    }   

    //método para trocar o conteúdo do AnchorPane, aceitando um parent
    private void trocarConteudo(AnchorPane telaAtual, Parent novoConteudo) {
        telaAtual.getChildren().setAll(novoConteudo);
    }


    private void trocarTela(AnchorPane telaAtual, String caminhoNovaTelaFXML) throws IOException {
        AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource(caminhoNovaTelaFXML));
        telaAtual.getChildren().setAll(novaTela);
    }

    @FXML
    void voltarCliente(ActionEvent event) {
        try{
                trocarTela(anchorPaneIngressos, "/view/TelaCliente.fxml");
        } catch(IOException ex){
            System.err.println("Erro ao tentar voltar: " + ex.getMessage());
                ex.printStackTrace();
        }
    }
}
