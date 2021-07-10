package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.gameprop.Selectable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HandHouse extends Pane implements Selectable {
    ImageView cardImage;
    Card card;

    public HandHouse() {
        cardImage = new ImageView();
        cardImage.setFitHeight(91);
        cardImage.setFitWidth(60);
    }

    @Override
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) throws FileNotFoundException {
        this.card = card;
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
            cardImage.setImage(image);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public void setImageOfCard() {
        this.getChildren().add(cardImage);
    }

    public void removeCard() {
        card = null;
        cardImage.setImage(null);
    }

    public boolean doesHaveImage() {
        return cardImage.getImage() == null;
    }

}
