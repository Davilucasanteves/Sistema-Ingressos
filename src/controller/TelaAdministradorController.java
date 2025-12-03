package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Festa;
import model.IFesta;
import data.*;

public class TelaAdministradorController {

    IFesta bancoDeDadosFesta = new RepositorioFesta();

    @FXML
    private ImageView imageCriarFesta;

    @FXML
    private ImageView imageDeletarFesta;

    @FXML
    private ImageView imageInformacoesFesta;

    @FXML
    private TableColumn<?, ?> tableColunmnIDFesta;

    @FXML
    private TableColumn<?, ?> tableColunmnNomeFesta;

    @FXML
    private TableView<Festa> tableFesta;

    @FXML
    public void initialize() {
        tableFesta.setPlaceholder(new Label("Nenhuma Festa cadastrada."));
        carregarTabelaFesta();
    }

    private void carregarTabelaFesta() {
        //Liga as colunas da tabela (TableColumn) aos atributos (propriedades) dos objetos que estão sendo exibidos.
        tableColunmnIDFesta.setCellValueFactory(new PropertyValueFactory("id")); 
        tableColunmnNomeFesta.setCellValueFactory(new PropertyValueFactory("nome"));

        //carregar os dados (lista de festas) na tabela da interface
        ArrayList<Festa> listaFestas = bancoDeDadosFesta.getAllFestas();
        ObservableList<Festa>  obsListFesta = FXCollections.observableArrayList(listaFestas);
        tableFesta.setItems(obsListFesta);
    }

    @FXML
    void handleCriarFesta(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaCadastrarFesta.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        stage.show();
        
        Stage atual = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        atual.close();
    }

    @FXML
    void handleDeletarFesta(MouseEvent event) {

    }

    @FXML
    void handleInformacoesFesta(KeyEvent event) {

    }
}
