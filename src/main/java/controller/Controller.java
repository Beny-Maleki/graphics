package controller;

import animatefx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.enums.Menu;

import java.io.IOException;

public class Controller {
    Parent parent;

    public void moveToPage(Node node, Menu menu) throws IOException {
        Stage stage;
        Scene scene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(menu.getAddress()));
        parent = loader.load();
        stage = (Stage) node.getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        loadPane(menu);
        stage.show();
    }

    private void loadPane(Menu page) {

        switch (page) {
            case WELCOME_MENU: {
                new SlideInUp(parent).play();
                break;
            }
            case REGISTER_MENU: {
                new SlideInLeft(parent).play();
                break;
            }

            case LOGIN_MENU: {
                new SlideInDown(parent).play();
                break;
            }
            case MAIN_MENU: {
                new SlideInRight(parent).play();
                break;
            }
        }
    }

    public void displayMessage(Label message) {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3), (ActionEvent event) -> new FadeOut(message).play()
        ));

        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3.5), (ActionEvent event) -> {
            message.setText("");
            new FadeIn(message).play();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

}
