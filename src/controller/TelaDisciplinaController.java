package controller;

import data.RepositorioIngresso;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import model.Ingresso;
import model.IIngresso;
import util.CaminhoArquivo;

public class TelaDisciplinaController implements Initializable {

    @FXML
    private ImageView imageCadastrarDisciplina;
    @FXML
    private ImageView imageEditarDisciplina;
    @FXML
    private ImageView imageDeletarDisciplina;
    @FXML
    private TableView<Disciplina> tableDisciplina;
    @FXML
    private TableColumn<?, ?> tableColumnIdDisciplina;
    @FXML
    private TableColumn<?, ?> tableColumnNomeDisciplina;
    @FXML
    private TableColumn<?, ?> tableColumnChDisciplina;

    private IDisciplina bancoDeDadosDisciplinas = new RepositorioDisciplina();
    @FXML
    private TextField textFieldPesquisarDisciplina;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableDisciplina.setPlaceholder(new Label("Nenhuma disciplina cadastrada."));
        carregarTabelaDisciplina();
    }

    private void carregarTabelaDisciplina() {
        //Liga as colunas da tabela (TableColumn) aos atributos (propriedades) dos objetos que estão sendo exibidos.
        tableColumnIdDisciplina.setCellValueFactory(new PropertyValueFactory("id")); //Liga a coluna tableColumnDisciplinaId ao método getId() do objeto Disciplina.
        tableColumnNomeDisciplina.setCellValueFactory(new PropertyValueFactory("nome")); //Liga a coluna tableColumnDisciplinaNome ao método getNome() do objeto Disciplina.
        tableColumnChDisciplina.setCellValueFactory(new PropertyValueFactory("cargaHoraria"));

        //carregar os dados (lista de disciplinas) na tabela da interface
        ArrayList<Disciplina> listaDisciplinas = bancoDeDadosDisciplinas.getAllDisciplinas();
        ObservableList obsListDisciplina = FXCollections.observableArrayList(listaDisciplinas);
        tableDisciplina.setItems(obsListDisciplina);
    }

    @FXML
    private void handleCadastrarDisciplina(MouseEvent event) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TelaCadastrarDisciplinaController.class.getResource(CaminhoArquivo.TELA_CADASTRAR_DISCIPLINA));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sistema Acadêmico - Disciplina");
            dialogStage.initModality(Modality.APPLICATION_MODAL); //enquanto essa janela estiver aberta, o usuário não pode interagir com a janela principal.
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            TelaCadastrarDisciplinaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            carregarTabelaDisciplina();
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela Cadastrar Disciplina: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleEditarDisciplina(MouseEvent event) {
        try {
            Disciplina disciplinaSelecionada = tableDisciplina.getSelectionModel().getSelectedItem();
            if (disciplinaSelecionada != null) {
                // Carrega o arquivo fxml e cria um novo stage para a janela popup.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TelaAtualizarDisciplinaController.class.getResource(CaminhoArquivo.TELA_ATUALIZAR_DISCIPLINA));
                AnchorPane page = (AnchorPane) loader.load();

                // Cria o palco dialogStage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Sistema Acadêmico - Atualizar Disciplina");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Define a disciplina no controller.
                TelaAtualizarDisciplinaController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setDisciplina(disciplinaSelecionada);

                // Mostra a janela e espera até o usuário fechar.
                dialogStage.showAndWait();
                this.bancoDeDadosDisciplinas.updateDisciplina(controller.getDisciplina());

                carregarTabelaDisciplina();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText("Nenhuma disciplina foi selecionada.");
                alert.setContentText("Por favor, selecione uma disciplina na tabela para poder editá-la.");
                alert.showAndWait();
            }
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a Tela Atualizar Disciplina: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleDeletarDisciplina(MouseEvent event) {
        Disciplina disciplinaSelecionada = tableDisciplina.getSelectionModel().getSelectedItem();

        if (disciplinaSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhuma disciplina foi selecionada.");
            alert.setContentText("Por favor, selecione uma disciplina na tabela para poder removê-la.");
            alert.show();
            return;
        }
        bancoDeDadosDisciplinas.deleteDisciplina(disciplinaSelecionada);
        carregarTabelaDisciplina();
    }

    @FXML
    private void handlePesquisarDisciplina(KeyEvent event) {
        ObservableList obsListDisciplina = FXCollections.observableArrayList();
        ArrayList<Disciplina> disciplinasCadastradas = bancoDeDadosDisciplinas.getAllDisciplinas();

        String nomeDigitado = this.textFieldPesquisarDisciplina.getText().toUpperCase();
        if (!nomeDigitado.isEmpty()) {
            for (Disciplina d : disciplinasCadastradas) {
                if (d.getNome().startsWith(nomeDigitado)) {
                    obsListDisciplina.add(d);
                }
            }
            tableDisciplina.setItems(obsListDisciplina);
        } else {
            carregarTabelaDisciplina();
        }
    }

}
