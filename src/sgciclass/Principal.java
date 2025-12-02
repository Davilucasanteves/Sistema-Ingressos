package sgciclass;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage stage) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaInicialSGVI.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaIngressosDisponiveis.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Image imagemIconeDaJanela = new Image("/icons/icone_logo.png");
            stage.getIcons().add(imagemIconeDaJanela);// define o icone da janela
            stage.setTitle("SGVI");
            stage.setResizable(true);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}