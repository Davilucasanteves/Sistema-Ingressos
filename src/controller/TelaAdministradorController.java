package controller;

import java.io.IOException;
import java.util.ArrayList;

import data.RepositorioFesta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Festa;
import model.IFesta;

public class TelaAdministradorController {

    IFesta bancoDeDadosFesta = new RepositorioFesta();

    @FXML
    private ImageView imageCriarFesta;

    @FXML
    private ImageView imageDeletarFesta;

    @FXML
    private ImageView imageInformacoesFesta;

    @FXML
    private Button buttomSair;

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
        
        // Filtrar apenas festas com quantidade > 0
        ArrayList<Festa> festaDisponivel = new ArrayList<>();
        for (Festa festa : listaFestas) {
            if (festa.getQuantidade() > 0) {
                festaDisponivel.add(festa);
            }
        }
        
        ObservableList<Festa>  obsListFesta = FXCollections.observableArrayList(festaDisponivel);
        tableFesta.setItems(obsListFesta);
    }

    @FXML
    void handleCriarFesta(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaCadastrarFesta.fxml"));
        AnchorPane page = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Sistema");
        stage.setScene(new Scene(page));
        Image imagemIconeDaJanela = new Image("/icons/icone_logo.png");
        stage.getIcons().add(imagemIconeDaJanela);
        stage.show();
        
        Stage atual = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        atual.close();
    }

    @FXML
    void handleDeletarFesta(MouseEvent event) {
        Festa festaSelecionada = tableFesta.getSelectionModel().getSelectedItem();
        
        if (festaSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhuma festa foi selecionada.");
            alert.setContentText("Por favor, selecione uma festa na tabela para deletá-la.");
            alert.showAndWait();
            return;
        }
        
        // Confirma a deleção
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Deleção");
        confirmacao.setHeaderText("Tem certeza que deseja deletar essa festa?");
        confirmacao.setContentText("Festa: " + festaSelecionada.getNome() + "\nEsta ação não pode ser desfeita.");
        
        if (confirmacao.showAndWait().isPresent() && confirmacao.getResult().getText().equalsIgnoreCase("OK")) {
            // Deleta a festa do repositório
            bancoDeDadosFesta.deleteFesta(festaSelecionada);
            
            // Atualiza a tabela
            carregarTabelaFesta();
            
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("Festa deletada com sucesso!");
            sucesso.setContentText("A festa foi removida do sistema.");
            sucesso.showAndWait();
        }
    }

    @FXML
    void handleInformacoesFesta(MouseEvent event) {
        Festa festaSelecionada = tableFesta.getSelectionModel().getSelectedItem();
        
        if (festaSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhuma festa foi selecionada.");
            alert.setContentText("Por favor, selecione uma festa na tabela para editá-la.");
            alert.showAndWait();
            return;
        }
        
        abrirTelaEditar(festaSelecionada);
    }
    
    private void abrirTelaEditar(Festa festa) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaEditarFesta.fxml"));
            Parent root = loader.load();
            
            TelaEditarFestaController controller = loader.getController();
            controller.setFestaEmEdicao(festa);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Festa");
            stage.setScene(new Scene(root));
            Image imagemIconeDaJanela = new Image("/icons/icone_logo.png");
            stage.getIcons().add(imagemIconeDaJanela);
            stage.setOnHidden(e -> carregarTabelaFesta()); // Recarrega tabela quando fechar
            stage.show();
            
        } catch (IOException ex) {
            System.err.println("Erro ao abrir tela de edição: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    void handleSair(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaInicialSGVI.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Sistema de Ingressos");
        stage.setScene(new Scene(root));
        Image imagemIconeDaJanela = new Image("/icons/icone_logo.png");
        stage.getIcons().add(imagemIconeDaJanela);
        stage.show();
        
        // Fecha a tela de administrador
        Stage atual = (Stage) buttomSair.getScene().getWindow();
        atual.close();
    }
}
