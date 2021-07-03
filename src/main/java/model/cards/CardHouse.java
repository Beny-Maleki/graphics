package model.cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.cards.cardsProp.Card;

import java.util.ArrayList;

public class CardHouse {
    private static ArrayList<CardHouse> cardHouses;

    static {
        cardHouses = new ArrayList<>();
    }

    private Card card;
    private ImageView imageView;
    private Image image;

    public CardHouse(Card card, ImageView imageView, Image image) {
        this.card = card;
        this.imageView =imageView;
        this.image = image;
        cardHouses.add(this);
    }

    public Card getCard() {
        return card;
    }

    public Image getImage() {
        return image;
    }

    public static ArrayList<CardHouse> getCardHouses() {
        return cardHouses;
    }
}
