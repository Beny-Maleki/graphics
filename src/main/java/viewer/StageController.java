package viewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewer.URLenums.AddressFXML;

import java.util.Objects;

public class StageController extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("YuGiOh!");
        primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AddressFXML.REGISTER_MENU.value)));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void run(String[] args) {
        launch(args);
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
