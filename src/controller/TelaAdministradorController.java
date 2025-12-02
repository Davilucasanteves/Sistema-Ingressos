package controller;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private TextField textFieldPesquisarFesta;

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
    void handleEditarFesta(MouseEvent event) throws Exception {
     try {
        Festa festaSelecionada = tableFesta.getSelectionModel().getSelectedItem();
        if (festaSelecionada != null) {

            // Carrega o arquivo FXML e cria uma janela popup
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TelaEditarFestaController.class.getResource("/View/TelaEditarFesta.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco da janela
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sistema - Editar Festa");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a festa no controller
            TelaEditarFestaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFesta(festaSelecionada);

            // Mostra a janela e espera o usuário fechar
            dialogStage.showAndWait();

            // Atualiza no banco de dados
            this.bancoDeDadosFesta.updateFesta(controller.getFesta());

            carregarTabelaFesta();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhuma festa foi selecionada.");
            alert.setContentText("Por favor, selecione uma festa na tabela para editá-la.");
            alert.showAndWait();
        }

    } catch (IOException ex) {
        System.err.println("Erro ao carregar a Tela Atualizar Festa: " + ex.getMessage());
        ex.printStackTrace();
    }
    }

    @FXML
     void handlePesquisarFesta(KeyEvent event) {
        ObservableList obsListFesta = FXCollections.observableArrayList();
        ArrayList<Festa> festasCadastradas = bancoDeDadosFesta.getAllFestas();

        String nomeDigitado = this.textFieldPesquisarFesta.getText().toUpperCase();

        if (!nomeDigitado.isEmpty()) {
            for (Festa f : festasCadastradas) {
                if (f.getNome().toUpperCase().startsWith(nomeDigitado)) {
                    obsListFesta.add(f);
                }
            }
            tableFesta.setItems(obsListFesta);
        } else {
            carregarTabelaFesta();
        }
    }
    @FXML
    private void handleDeletarFesta(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaApagarFesta.fxml"));
            AnchorPane page = loader.load();

            TelaApagarFestaController controller = loader.getController();
            
            controller.setBancoDeDadosFesta(this.bancoDeDadosFesta);
            
            Stage stage = new Stage();
            stage.setTitle("Apagar Festa");
            stage.setScene(new Scene(page));
            
            controller.setDialogStage(stage);
            stage.showAndWait(); 
            carregarTabelaFesta();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
