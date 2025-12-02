package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Festa;
import model.IFesta;

public class TelaApagarFestaController {
    @FXML
    private AnchorPane anchorPaneCadastraFesta;

    @FXML
    private Button buttomVoltar;

    @FXML
    private Button buttonApagarFesta;

    @FXML
    private Label labelStatusApagarFesta;

    @FXML
    private TextField textFieldIdFesta;

    private IFesta bancoDeDadosFesta; 
    private Stage dialogStage;
    
    public void setBancoDeDadosFesta(IFesta bancoDeDadosFesta) {
        this.bancoDeDadosFesta = bancoDeDadosFesta;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    void handleButtonApagarFesta(ActionEvent event) {
        String textoId = textFieldIdFesta.getText();

        if (textoId.isEmpty()) {
            labelStatusApagarFesta.setText("Digite um ID.");
            return;
        }

        try {
            int id = Integer.parseInt(textoId);
            
            // Verificar se o bancoDeDadosFesta foi injetado
            if (bancoDeDadosFesta == null) {
                labelStatusApagarFesta.setText("Erro: Sistema não inicializado.");
                return;
            }
            
            Festa festa = bancoDeDadosFesta.readFestaPorId(id);

            if (festa == null) {
                labelStatusApagarFesta.setText("Festa não encontrada.");
                return;
            }

            bancoDeDadosFesta.deleteFesta(festa);
            labelStatusApagarFesta.setText("Festa removida com sucesso!");
            textFieldIdFesta.clear();
            if (dialogStage != null) {
            dialogStage.close();
            }

        } catch (NumberFormatException e) {
            labelStatusApagarFesta.setText("ID inválido.");
        }
    }
    
    @FXML
    private void voltarAdm(ActionEvent event) {
        dialogStage.close();
    }
}