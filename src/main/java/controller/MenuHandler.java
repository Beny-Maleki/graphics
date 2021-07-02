package controller;

import animatefx.animation.SlideInRight;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.enums.Menu;

import java.io.IOException;

public class MenuHandler {
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
            case MAIN_MENU: {
                new SlideInRight(parent).play();
                break;
            }
        }
    }

}
