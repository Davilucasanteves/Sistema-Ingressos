package controller;

import crud.FestaCRUD;
import model.Festa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FestaController {

    @FXML private TextField txtIdFesta;
    @FXML private TextField txtLocal;
    @FXML private TextField txtData;
    @FXML private TextField txtPreco;
    @FXML private TextArea txtLista;

    FestaCRUD crud = new FestaCRUD();

    @FXML
    void cadastrarFesta(ActionEvent event) {
        int id = Integer.parseInt(txtIdFesta.getText());
        String local = txtLocal.getText();
        String data = txtData.getText();
        double preco = Double.parseDouble(txtPreco.getText());

        Festa festa = new Festa(id, local, data, preco);
        crud.cadastrarFesta(festa);
        listarFestas();
        limparCampos();
    }

    @FXML
    void editarFesta(ActionEvent event) {
        int id = Integer.parseInt(txtIdFesta.getText());
        Festa festaAtualizada = new Festa(
                id,
                txtLocal.getText(),
                txtData.getText(),
                Double.parseDouble(txtPreco.getText())
        );

        crud.atualizarFesta(id, festaAtualizada);
        listarFestas();
        limparCampos();
    }

    @FXML
    void excluirFesta(ActionEvent event) {
        int id = Integer.parseInt(txtIdFesta.getText());
        crud.deletarFesta(id);
        listarFestas();
        limparCampos();
    }

    @FXML
    void listarFestas() {
        txtLista.clear();
        for (Festa f : crud.listarFesta()) {
            txtLista.appendText("ID: " + f.getIdFesta() +
                    " | Local: " + f.getLocal() +
                    " | Data: " + f.getData() +
                    " | Preço: R$" + f.getPreco() + "\n");
        }
    }

    void limparCampos() {
        txtIdFesta.clear();
        txtLocal.clear();
        txtData.clear();
        txtPreco.clear();
    }
}
