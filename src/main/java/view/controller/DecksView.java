package view.controller;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class DecksView {
    public Pane DeckOneHolder;
    public Pane DeckTwoHolder;
    public Pane DeckFourHolder;
    public Pane DescriptionArea;
    public Pane DeckThreeHolder;

    @FXML
    public void initialize() throws FileNotFoundException {
        ArrayList<Pane> deckHolders =
                new ArrayList<>(Arrays.asList(DeckOneHolder, DeckTwoHolder, DeckThreeHolder, DeckFourHolder));

        deckHolders.forEach(this::handleMouseEnteredEvent);
        deckHolders.forEach(this::handleMouseExitedEvent);

        User loginUser = LoginUser.getUser();
        int numberOfDeckHolder = loginUser.getNumberOfDeckHolder();
        for (int i = numberOfDeckHolder; i < 4; i++) {
            ImageView imageView = new ImageView();
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/GreyLockBG.jpg");
            Image image = new Image(fileInputStream);
            imageView.setImage(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setLayoutX(55);
            imageView.setLayoutY(90);
            deckHolders.get(i).setStyle("-fx-background-color : #a9a8a8; -fx-background-radius: 15");
            deckHolders.get(i).getChildren().add(imageView);
        }
    }

    private void handleMouseExitedEvent(Pane pane) {
        pane.setOnMouseExited(mouseEvent -> {
            pane.setScaleX(1);
            pane.setScaleY(1);

            pane.setEffect(null);
        });
    }

    private void handleMouseEnteredEvent(Pane pane) {
        pane.setOnMouseEntered(mouseEvent -> {
            pane.setScaleX(1.05);
            pane.setScaleY(1.105);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setWidth(pane.getWidth());
            dropShadow.setHeight(pane.getHeight());
            pane.setEffect(dropShadow);

            pane.toFront();

        });
    }

    public void run(MouseEvent event) {

    }
}
