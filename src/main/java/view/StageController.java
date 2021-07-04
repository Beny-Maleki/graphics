package view;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.enums.Menu;
import model.userProp.LoginUser;
import model.userProp.User;

import java.util.Objects;

public class StageController extends Application {

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YuGiOh!");
        LoginUser.setUser(new User("ali", "ali", "ali"));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Menu.DECKS_VIEW.getAddress())));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        new FadeIn(root).play();

    }
}
