package view.controller;

import controller.menues.menuhandlers.menucontrollers.DeckMenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

// ** a little buggy! **

public class DeckModifierView {

    public ImageView selectedCardImageView;
    public Label details;
    public HBox HBoxAllCards;
    public ScrollBar scrollBar;
    public Label cardName;
    public Label message;
    public AnchorPane anchorPane;
    public AnchorPane anchorPaneOverHBox;
    TreeMap<String, ImageView> cardPictures;

    private DeckMenuController controller;

    {
        controller = DeckMenuController.getInstance();
    }

    public void initialize() {
        anchorPane.setMaxWidth(1000);
        anchorPaneOverHBox.setMaxWidth(900);
        cardPictures = new TreeMap<>();
        HBoxAllCards.getChildren().removeAll();
        scrollBar.setMax(10450);
        scrollBar.setMin(0);
        scrollBar.setVisibleAmount(900);

        ArrayList<Card> cards = (ArrayList<Card>) Card.getCards();

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            String name = card.getName();
            String nameWithoutSpace = name.replaceAll("\\s+", "");
            Image image = null;
            FileInputStream fileInputStream;
            try {
                if (card instanceof MonsterCard) {
                    fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpace + ".jpg");
                    image = new Image(fileInputStream);
                } else if (card instanceof MagicCard) {
                    fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
                    image = new Image(fileInputStream);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
                continue;
            }

            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(107.5);
            imageView.setFitHeight(153.5);
           // imageView.setLayoutX(20 + (i * 108));

            HBoxAllCards.getChildren().add(imageView);
            cardPictures.put(name, imageView);
        }

//        for(Map.Entry<String, ImageView> entry : cardPictures.entrySet()) {
//            HBoxAllCards.getChildren().add(entry.getValue());
//        }

        scrollBar.valueProperty().addListener((ov, old_val, new_val) -> HBoxAllCards.setLayoutX(-new_val.doubleValue()));
    }

    public void run(MouseEvent mouseEvent) throws IOException {

    }
}
