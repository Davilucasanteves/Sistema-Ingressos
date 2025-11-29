package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.RepositorioFesta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.IIngresso;
import model.Ingresso;

public class ControllerTelaIngressosDisponiveis implements Initializable {

    @FXML
    private ImageView imageCadastrarIngresso;
    @FXML
    private ImageView imageEditarIngresso;
    @FXML
    private ImageView imageDeletarIngresso;
    @FXML
    private TableView<Ingresso> tableIngresso;
    @FXML
    private TableColumn<?, ?> tableColumnIdIngresso;
    @FXML
    private TableColumn<?, ?> tableColumnNomeIngresso;
    @FXML
    private TableColumn<?, ?> tableColumnChIngresso;

    private IIngresso bancoDeDadosIngressos = new RepositorioFesta();
    @FXML
    private TextField textFieldPesquisarIngresso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableIngresso.setPlaceholder(new Label("Nenhuma Ingresso cadastrado."));
    }

}
