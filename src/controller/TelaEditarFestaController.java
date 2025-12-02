package controller;

import data.RepositorioFesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
public class TelaEditarFestaController {

    @FXML
    private AnchorPane anchorPaneCadastraFesta;

    @FXML
    private Button buttomVoltar;

    @FXML
    private Button buttonSalvarFesta;

    @FXML
    private Label labelStatusCadastro;

    @FXML
    private TextField textFieldData;

    @FXML
    private TextField textFieldDescricao;

    @FXML
    private TextField textFieldIdFesta;

    @FXML
    private TextField textFieldNomeFesta;

    private Stage dialogStage;
    private Festa festa;
    private RepositorioFesta bancoDeDadosFesta = new RepositorioFesta();

    @FXML
    private void handleButtonSalvarFesta(ActionEvent event) {
        if (!textFieldNomeFesta.getText().isEmpty()
                && !textFieldDescricao.getText().isEmpty()
                && !textFieldData.getText().isEmpty()) {

            String novoNome = textFieldNomeFesta.getText().toUpperCase();
            String novaDescricao = textFieldDescricao.getText();
            String novaData = textFieldData.getText();

            // Atualiza os dados da festa
            festa.setNome(novoNome);
            festa.setDescricao(novaDescricao);
            festa.setData(novaData);

            bancoDeDadosFesta.updateFesta(festa);

            dialogStage.close();

        } else {
            labelStatusCadastro.setText("Dados inválidos.");
            textFieldNomeFesta.requestFocus();
        }
    }

    @FXML
    private void voltarAdm(ActionEvent event) {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setFesta(Festa festa) {
        this.festa = festa;

        textFieldIdFesta.setText(String.valueOf(festa.getId()));
        textFieldNomeFesta.setText(festa.getNome());
        textFieldDescricao.setText(festa.getDescricao());
        textFieldData.setText(festa.getData());
    }

    public Festa getFesta() {
        return festa;
    }
}

