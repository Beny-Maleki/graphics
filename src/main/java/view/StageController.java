package view;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.URLenums.AddressFXML;

import java.util.Objects;

public class StageController extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AddressFXML.REGISTER_MENU.value)));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        new FadeIn(root).play();

    }

    public static void run(String[] args) {
        launch(args);
    }
}
