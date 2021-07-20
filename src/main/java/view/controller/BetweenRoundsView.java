package view.controller;

import animatefx.animation.*;
import controller.RockPaperScissorController;
import controller.menues.menuhandlers.menucontrollers.DeckModifierController;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import model.cards.CardHouse;
import model.enums.Menu;
import model.gameprop.Player;
import model.userProp.LoginUser;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;

public class BetweenRoundsView extends DeckModifierView {
    public Button enterGame;
    protected int mainDeckSizeAtFirst;
    protected int sideDeckSizeAtFirst;


    {
        controller = DeckModifierController.getInstance();
        loggedInUser = LoginUser.getUser();
        toShowDeck = loggedInUser.getActiveDeck();
    }

    @FXML
    public void initialize() {
        cardPictures = new TreeMap<>();
        initializeDeck();

        initializeRoot();

        initializeSlides();

        try {
            selectedCardImageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));
        } catch (FileNotFoundException ignored) {
        }
        mainDeckSizeAtFirst = mainDeck.size();
        sideDeckSizeAtFirst = sideDeck.size();
    }

    @Override
    protected void addCardToSideDeck() {
        disappearMessageContainer();

        DeckModifierController.addCardToSideDeck(selectedCard, toShowDeck.getName());
        initializeDeck();
        initializeMainDeck();
        initializeSideDeck();

        new SlideInLeft(mainDeckPane).play();
        new SlideInLeft(sideDeckPane).play();
    }

    @Override
    protected void addCardToMainDeck() {
        disappearMessageContainer();

        DeckModifierController.addCardToMainDeck(selectedCard, toShowDeck.getName());
        initializeDeck();
        initializeMainDeck();
        initializeSideDeck();

        new SlideInLeft(mainDeckPane).play();
        new SlideInLeft(sideDeckPane).play();
    }

    @Override
    protected void handleOnMouseClick(ImageView imageView, CardHouse cardHouse) {
        imageView.setOnMouseClicked(mouseEvent -> {

            selectedCardImageView.setImage(cardHouse.getImage());
            new Wobble(imageView).play();
            new FlipInX(selectedCardImageView).play();

            selectedCard = cardHouse.getCard();

            selectedCardDescriptionLabel.setText(selectedCard.getCardDetailWithEnters());
            new BounceInUp(selectedCardDescriptionLabel).play();
        });
    }

    @Override
    protected void initializeMainDeck() {
        super.initializeMainDeck();
        addDropHandling(shownOnStageMainDeck);
    }

    @Override
    protected void initializeSideDeck() {
        super.initializeSideDeck();
        addDropHandling(shownOnStageSideDeck);
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == enterGame) {
            if (mainDeck.size() == mainDeckSizeAtFirst && sideDeck.size() == sideDeckSizeAtFirst) {
                controller.moveToPage(enterGame, Menu.ROCK_PAPER_SCISSOR_PAGE);
                RockPaperScissorController.setNumOfRounds(1);
            } else {
                prepareMessageContainer();
                message.setText("you can't change your cards number is Decks");
            }
        }
    }
}
