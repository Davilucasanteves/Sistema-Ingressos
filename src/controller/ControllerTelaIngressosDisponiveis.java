package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.RepositorioIngresso;
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

    private IIngresso bancoDeDadosIngressos = new RepositorioIngresso();
    @FXML
    private TextField textFieldPesquisarIngresso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableIngresso.setPlaceholder(new Label("Nenhuma Ingresso cadastrado."));
        carregarTabelaIngresso();
    }

    private void carregarTabelaIngresso() {
        //Liga as colunas da tabela (TableColumn) aos atributos (propriedades) dos objetos que estão sendo exibidos.
        tableColumnIdIngresso.setCellValueFactory(new PropertyValueFactory("id")); //Liga a coluna tableColumnIngressoId ao método getId() do objeto Ingresso.
        tableColumnNomeIngresso.setCellValueFactory(new PropertyValueFactory("nome")); //Liga a coluna tableColumnIngressoNome ao método getNome() do objeto Ingresso.
        tableColumnChIngresso.setCellValueFactory(new PropertyValueFactory("cargaHoraria"));

        //carregar os dados (lista de Ingressos) na tabela da interface
        ArrayList<Ingresso> listaIngressos = bancoDeDadosIngressos.getAllIngressos();
        ObservableList obsListIngresso = FXCollections.observableArrayList(listaIngressos);
        tableIngresso.setItems(obsListIngresso);
    }

    @FXML
    private void handleCadastrarIngresso(MouseEvent event) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            //**loader.setLocation(ControllerTelaCadastrarIngresso.class.getResource(CaminhoArquivo.TELA_CADASTRAR_INGRESSO));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("SGVI - Ingressos");
            dialogStage.initModality(Modality.APPLICATION_MODAL); //enquanto essa janela estiver aberta, o usuário não pode interagir com a janela principal.
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //**ControllerTelaCadastrarIngresso controller = loader.getController();
            //**controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            carregarTabelaIngresso();
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela Cadastrar Ingresso: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleEditarIngresso(MouseEvent event) {
        try {
            Ingresso IngressoSelecionada = tableIngresso.getSelectionModel().getSelectedItem();
            if (IngressoSelecionada != null) {
                // Carrega o arquivo fxml e cria um novo stage para a janela popup.
                FXMLLoader loader = new FXMLLoader();
                //**loader.setLocation(ControllerTelaAtualizarIngresso.class.getResource(CaminhoArquivo.TELA_CADASTRAR_INGRESSO));
                AnchorPane page = (AnchorPane) loader.load();

                // Cria o palco dialogStage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Sistema Acadêmico - Atualizar Ingresso");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Define a Ingresso no controller.
                //**ControllerTelaAtualizarIngresso controller = loader.getController();
                //**controller.setDialogStage(dialogStage);
                //**controller.setIngresso(IngressoSelecionada);

                // Mostra a janela e espera até o usuário fechar.
                dialogStage.showAndWait();
                //**this.bancoDeDadosIngressos.updateIngresso(controller.getIngresso());

                carregarTabelaIngresso();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Nenhuma Ingresso foi selecionada.");
                alert.setContentText("Por favor, selecione uma Ingresso na tabela para poder editá-la.");
                alert.showAndWait();
            }
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela Atualizar Ingresso: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleDeletarIngresso(MouseEvent event) {
        Ingresso IngressoSelecionada = tableIngresso.getSelectionModel().getSelectedItem();

        if (IngressoSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhuma Ingresso foi selecionada.");
            alert.setContentText("Por favor, selecione uma Ingresso na tabela para poder removê-la.");
            alert.show();
            return;
        }
        bancoDeDadosIngressos.deleteIngresso(IngressoSelecionada);
        carregarTabelaIngresso();
    }

    @FXML
    private void handlePesquisarIngresso(KeyEvent event) {
        ObservableList obsListIngresso = FXCollections.observableArrayList();
        ArrayList<Ingresso> IngressosCadastradas = bancoDeDadosIngressos.getAllIngressos();

        String nomeDigitado = this.textFieldPesquisarIngresso.getText().toUpperCase();
        if (!nomeDigitado.isEmpty()) {
            for (Ingresso d : IngressosCadastradas) {
                if (d.getNomeDaFesta().startsWith(nomeDigitado)) {
                    obsListIngresso.add(d);
                }
            }
            tableIngresso.setItems(obsListIngresso);
        } else {
            carregarTabelaIngresso();
        }
    }

}
