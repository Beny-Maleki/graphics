package view.controller;

import animatefx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.cards.CardHouse;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShopView {

    public AnchorPane gridPaneBGPane;
    public Label numberOfSlide;
    public Label message;
    public ImageView selectedCardImageView;
    public Label cardDescription;

    private ArrayList<GridPane> slidesOfShopCards;
    private int currentSlideNum;
    private GridPane shownOnStage;
    private Card selectedCard;

    public void initialize() {
        drawSlides();
        try {
            selectedCardImageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));
        } catch (FileNotFoundException ignored) {
        }
        numberOfSlide.setText("1");
        shownOnStage = slidesOfShopCards.get(0);
        gridPaneBGPane.getChildren().add(shownOnStage);
    }

    public void drawSlides() {
        slidesOfShopCards = new ArrayList<>();

        ArrayList<Card> cards = new ArrayList<>(Card.getCards());
        int numOfSlides = (cards.size() / 15);
        if (cards.size() % 15 != 0) numOfSlides++; // for now because of additional not used cards there is one empty page left empty!

        for (int i = 0; i < numOfSlides; i++) {
            slidesOfShopCards.add(new GridPane());
        }

        for (int i = 0; i < numOfSlides; i++) {
            GridPane current = slidesOfShopCards.get(i);
            current.setPrefWidth(521);
            current.setPrefHeight(438);

            outer:
            for (int j = 0; j < 3; j++) { // this 3*5 loop is for one page (3 rows & 5 columns) -> j:row & k:column
               for (int k = 0; k < 5; k++) {
                   if((15 * i) + (j * 5) + k == cards.size()) break outer; // configuring last slide!

                   Card card = cards.get((15 * i) + (j * 5) + k);

                   Image image;
                   try {
                       image = getImageOfCard(card);
                   } catch (FileNotFoundException ignored) {
                       cards.remove(card);
                       k--;
                       continue;
                   }


                   ImageView imageView = new ImageView(image);
                   CardHouse cardHouse = new CardHouse(card, imageView, image);// creating a wrapper for pic and related card!

                   imageView.setFitWidth(98);
                   imageView.setFitHeight(140);
                   imageView.setX((imageView.getFitWidth() + 5) * k + 5);
                   imageView.setY((imageView.getFitHeight() + 4.5) * k + 4.5);
                   imageView.setOnMouseClicked(mouseEvent -> {
                       selectedCardImageView.setImage(cardHouse.getImage());
                       selectedCard = cardHouse.getCard();
                       cardDescription.setText(selectedCard.getCardDetail());
                   });
                   slidesOfShopCards.get(i).add(imageView, k, j);
               }
            }
        }
    }

    private Image getImageOfCard(Card card) throws FileNotFoundException {
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
            throw new FileNotFoundException();
        }
        return image;
    }

    public void nextSlide() {

        if (currentSlideNum == slidesOfShopCards.size() - 1) message.setText("This is the last slide!");
        else {
            new FadeOutDown(message);
            message.setText("");

            gridPaneBGPane.getChildren().remove(shownOnStage);
            new FadeOutLeft(gridPaneBGPane).play();

            shownOnStage = slidesOfShopCards.get(currentSlideNum + 1);
            gridPaneBGPane.getChildren().add(shownOnStage);
            new FadeInLeft(gridPaneBGPane).play();

            currentSlideNum++;
            numberOfSlide.setText(String.valueOf(currentSlideNum + 1));

            new SlideOutUp(numberOfSlide).play();
            new SlideInUp(numberOfSlide).play();
        }
    }

    public void previousSlide() {
        if (currentSlideNum == 0) message.setText("This is the first slide!");
        else {
            new FadeOutDown(message);
            message.setText("");

            gridPaneBGPane.getChildren().remove(shownOnStage);
            new FadeOutRight(gridPaneBGPane).play();

            shownOnStage = slidesOfShopCards.get(currentSlideNum - 1);
            gridPaneBGPane.getChildren().add(shownOnStage);
            new FadeInRight(gridPaneBGPane).play();

            currentSlideNum--;
            numberOfSlide.setText(String.valueOf(currentSlideNum + 1));

            new SlideOutDown(numberOfSlide).play();
            new SlideInDown(numberOfSlide).play();
        }
    }
}
