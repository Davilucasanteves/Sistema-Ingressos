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

    private FestaCRUD crud = new FestaCRUD();

    
    private void alerta(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    
    private Integer getId(){
        try { return Integer.parseInt(txtIdFesta.getText()); }
        catch(Exception e){ alerta("ID inválido! Digite somente números."); return null; }
    }

    private Double getPreco(){
        try { return Double.parseDouble(txtPreco.getText()); }
        catch(Exception e){ alerta("Preço inválido! Digite somente números."); return null; }
    }

    
    private boolean camposVazios(){
        return txtLocal.getText().isBlank() || txtData.getText().isBlank() || txtPreco.getText().isBlank();
    }

    
    @FXML
    void cadastrarFesta(ActionEvent event){
        if(camposVazios()){ alerta("Preencha todos os campos!"); return; }

        Integer id = getId();
        Double preco = getPreco();
        if(id == null || preco == null) return;

       
        for(Festa f : crud.listarFesta()){
            if(f.getIdFesta() == id){
                alerta("Erro: Já existe uma festa com esse ID!");
                return;
            }
        }

        crud.cadastrarFesta(new Festa(id, txtLocal.getText(), txtData.getText(), preco));
        listarFestas();
        limparCampos();
        alerta("Festa cadastrada com sucesso!");
    }

    
    @FXML
    void editarFesta(ActionEvent event){
        Integer id = getId();
        Double preco = getPreco();
        if(id == null || preco == null) return;

        crud.atualizarFesta(id, new Festa(id, txtLocal.getText(), txtData.getText(), preco));
        listarFestas();
        limparCampos();
        alerta("Festa editada com sucesso!");
    }

    
    @FXML
    void excluirFesta(ActionEvent event){
        Integer id = getId();
        if(id == null) return;

        crud.deletarFesta(id);
        listarFestas();
        limparCampos();
        alerta("Festa removida com sucesso!");
    }

    
    @FXML
    void listarFestas(){
        txtLista.clear();
        for(Festa f : crud.listarFesta()){
            txtLista.appendText("ID: " + f.getIdFesta() +
                    " | Local: " + f.getLocal() +
                    " | Data: " + f.getData() +
                    " | Preço: R$" + f.getPreco() + "\n");
        }
    }

    
    void limparCampos(){
        txtIdFesta.clear();
        txtLocal.clear();
        txtData.clear();
        txtPreco.clear();
    }
}
