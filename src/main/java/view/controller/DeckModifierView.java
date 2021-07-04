package view.controller;

import animatefx.animation.*;
import controller.menues.menuhandlers.menucontrollers.DeckMenuController;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.cards.CardHouse;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.Origin;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

// ** a little buggy! **

public class DeckModifierView {

    public HBox hBoxCollection;
    public ScrollBar scrollBar;
    public AnchorPane anchorPane;
    public AnchorPane anchorPaneOverHBox;
    public AnchorPane sideDeckPane;
    public AnchorPane mainDeckPane;
    public Label message;
    public Rectangle nextMainDeck;
    public Rectangle previousMainDeck;
    public Rectangle nextSideDeck;
    public Rectangle previousSideDeck;
    public Label previousMainDeckButtonLabel;
    public Label nextMainDeckButtonLabel;
    public Label nextSideDeckButtonLabel;
    public Label previousSideDeckButtonLabel;
    public Pane messageContainer;

    private ArrayList<Pane> slidesOfMainDeck;
    private ArrayList<Pane> slidesOfSideDeck;
    private ArrayList<Card> collection;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;

    private Pane shownOnStageMainDeck;
    private Pane shownOnStageSideDeck;
    private DeckMenuController controller;
    private User loggedInUser;
    private Deck toShowDeck;
    private TreeMap<String, ImageView> cardPictures;
    private int currSlideNumberMainDeck;
    private int currSlideNumberSideDeck;

    {
        controller = DeckMenuController.getInstance();
        loggedInUser = LoginUser.getUser();
        toShowDeck = loggedInUser.getActiveDeck();
        mainDeck = toShowDeck.getMainDeck();
        sideDeck = toShowDeck.getSideDeck();
        collection = loggedInUser.getUserCardCollection();
    }

    public void initialize() {
        cardPictures = new TreeMap<>();
        hBoxCollection.getChildren().removeAll();

        initializeRoot();

        initializeSlides();
        mainDeckPane.getChildren().add(slidesOfMainDeck.get(0));
        sideDeckPane.getChildren().add(slidesOfSideDeck.get(0));
        shownOnStageMainDeck = slidesOfMainDeck.get(0);
        shownOnStageSideDeck = slidesOfSideDeck.get(0);

        initializeScrollBar();

        //  initializeMainDeck();

//        ArrayList<Card> cards = (ArrayList<Card>) Card.getCards();
//
//        for (int i = 0; i < cards.size(); i++) {
//            Card card = cards.get(i);
//            String name = card.getName();
//            String nameWithoutSpace = name.replaceAll("\\s+", "");
//            Image image = null;
//            FileInputStream fileInputStream;
//            try {
//                if (card instanceof MonsterCard) {
//                    fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpace + ".jpg");
//                    image = new Image(fileInputStream);
//                } else if (card instanceof MagicCard) {
//                    fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
//                    image = new Image(fileInputStream);
//                }
//            } catch (FileNotFoundException e) {
//                System.out.println("File not found!");
//                continue;
//            }
//
//            ImageView imageView = new ImageView(image);
//
//            imageView.setFitWidth(107.5);
//            imageView.setFitHeight(153.5);
//           // imageView.setLayoutX(20 + (i * 108));
//
//            HBoxAllCards.getChildren().add(imageView);
//            cardPictures.put(name, imageView);
//        }
//
////        for(Map.Entry<String, ImageView> entry : cardPictures.entrySet()) {
////            HBoxAllCards.getChildren().add(entry.getValue());
////        }

        scrollBar.valueProperty().addListener((ov, old_val, new_val) -> hBoxCollection.setLayoutX(-new_val.doubleValue()));
    }

    private void initializeSlides() {
        slidesOfMainDeck = new ArrayList<>();
        slidesOfSideDeck = new ArrayList<>();

        int numberOfMainDeckSlides = 6;
        int numberOfSideDeckSlides = 3;

        for (int i = 0; i < numberOfMainDeckSlides; i++) {
            slidesOfMainDeck.add(new Pane());
        }

        for (int i = 0; i < numberOfSideDeckSlides; i++) {
            slidesOfSideDeck.add(new Pane());
        }

        initializeMainDeck();
        initializeSideDeck();
    }

    private void mainDeckCardSlotStyler(int j, int k, ImageView imageView) {
        deckCardSlotGeneralStyler(k, imageView);
        if ((j == 0)) {
            imageView.setLayoutY(0);
        } else {
            imageView.setLayoutY(120 + 10);
        }
    }

    private void deckCardSlotGeneralStyler(int k, ImageView imageView) {
        imageView.setStyle("-fx-cursor: hand");
        imageView.setFitWidth(82);
        imageView.setFitHeight(120);
        imageView.setLayoutX(10 + (k * (82 + 10)));
    }

    private void handleOnMouseEntered(ImageView imageView) {
        imageView.setOnMouseExited(mouseEvent -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            imageView.setEffect(null);
        });
    }

    private void handleOnMouseExited(ImageView imageView) {
        imageView.setOnMouseEntered(mouseEvent -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setWidth(imageView.getFitWidth());
            dropShadow.setHeight(imageView.getFitHeight());
            imageView.setEffect(dropShadow);

        });
    }

    private void initializeMainDeck() {
        int numberOfMainDeckSlides = 6;

        for (int i = 0; i < numberOfMainDeckSlides; i++) {
            slidesOfMainDeck.get(i).setLayoutX(15);
            slidesOfMainDeck.get(i).setLayoutY(10);

            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 5; k++) {
                    ImageView imageView = new ImageView();

                    mainDeckCardSlotStyler(j, k, imageView);

                    handleOnMouseEntered(imageView);
                    handleOnMouseExited(imageView);

                    Image image = null;
                    FileInputStream fileInputStream = null;
                    try {
                        if ((i * 10) + (5 * j) + k >= mainDeck.size()) {
                            // filling the remained card slots!
                            try {
                                imageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));
                                imageView.setDisable(true);
                            } catch (Exception ignored) {
                            }
                        } else {
                            Card card = mainDeck.get((i * 10) + (5 * j) + k);
                            makeCardHouseAndAssignImage(imageView, card);
                        }
                    } catch (FileNotFoundException ignored) {
                    }

                    slidesOfMainDeck.get(i).getChildren().add(imageView);
                }
            }
        }
    }

    private void initializeSideDeck() {
        int numberOfSideDeckSlides = 3;
        for (int i = 0; i < numberOfSideDeckSlides; i++) {
            slidesOfSideDeck.get(i).setLayoutX(15);
            slidesOfSideDeck.get(i).setLayoutY(10);

            for (int j = 0; j < 5; j++) {
                ImageView imageView = new ImageView();

                deckCardSlotGeneralStyler(j, imageView);

                handleOnMouseEntered(imageView);
                handleOnMouseExited(imageView);

                try {
                    if ((5 * i) + j >= sideDeck.size()) {
                        //filling the remained card slots!
                        try {
                            imageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));
                            imageView.setDisable(true);
                        } catch (FileNotFoundException ignored) {
                        }

                    } else {
                        Card card = sideDeck.get((5 * i) + j);
                        makeCardHouseAndAssignImage(imageView, card);
                    }
                } catch (FileNotFoundException ignored) {
                }

                slidesOfSideDeck.get(i).getChildren().add(imageView);
            }
        }
    }

    private void makeCardHouseAndAssignImage(ImageView imageView, Card card) throws FileNotFoundException {
        FileInputStream fileInputStream;
        Image image = null;

        String name = card.getName();
        String nameWithoutSpace = name.replaceAll("\\s+", "");

        if (card instanceof MonsterCard) {
            fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpace + ".jpg");
            image = new Image(fileInputStream);
        } else if (card instanceof MagicCard) {
            fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
            image = new Image(fileInputStream);
        }

        imageView.setImage(image);

        new CardHouse(card, imageView, image, Origin.DECK_MENU);
    }
    private void initializeRoot() {
        anchorPane.setMaxWidth(1000);
        anchorPaneOverHBox.setMaxWidth(900);
    }

    private void initializeScrollBar() {
        scrollBar.setMax(10450);
        scrollBar.setMin(0);
        scrollBar.setVisibleAmount(900);
    }

    private void previousSlide(AnchorPane containerPane, Pane shownPane, ArrayList<Pane> slides, boolean isMainDeck) {
        disappearMessageContainer();

        new BounceOutDown(message);
        message.setText("");

        containerPane.getChildren().remove(shownPane);
        new SlideOutRight(shownPane).play();

        int indexToShow;
        if (isMainDeck) indexToShow = currSlideNumberMainDeck - 1;
        else indexToShow = currSlideNumberSideDeck - 1;

        if (isMainDeck) {
            shownOnStageMainDeck = shownPane = slides.get(indexToShow);
        } else  {
            shownOnStageSideDeck = shownPane = slides.get(indexToShow);
        }
        containerPane.getChildren().add(shownPane);
        new SlideInRight(shownPane).play();

        if ((isMainDeck)) {
            currSlideNumberMainDeck--;
        } else {
            currSlideNumberSideDeck--;
        }

    }

    private void nextSlide(AnchorPane containerPane, Pane shownPane, ArrayList<Pane> slides, boolean isMainDeck) {
        disappearMessageContainer();

        new BounceOutDown(message);
        message.setText("");

        containerPane.getChildren().remove(shownPane);
        new SlideOutLeft(shownPane).play();

        int indexToShow;
        if (isMainDeck) indexToShow = currSlideNumberMainDeck + 1;
        else indexToShow = currSlideNumberSideDeck + 1;

        if (isMainDeck) {
            shownOnStageMainDeck = shownPane = slides.get(indexToShow);
        } else  {
            shownOnStageSideDeck = shownPane = slides.get(indexToShow);
        }
        containerPane.getChildren().add(shownPane);
        new SlideInLeft(shownPane).play();

        if ((isMainDeck)) {
            currSlideNumberMainDeck++;
        } else {
            currSlideNumberSideDeck++;
        }
    }

    private void previousSlideMainDeck() {
        if (currSlideNumberMainDeck == 0) {
            prepareMessageContainer();

            message.setText("This is the first slide of Main Deck!");
            new Shake(message).play();
        }
        else {
            previousSlide(mainDeckPane, shownOnStageMainDeck, slidesOfMainDeck, true);
        }

    }

    private void nextSlideMainDeck() {
        if (currSlideNumberMainDeck == slidesOfMainDeck.size() - 1) {
            prepareMessageContainer();

            message.setText("This is the last slide of Main Deck!");
            new Shake(message).play();
        }
        else {
            nextSlide(mainDeckPane, shownOnStageMainDeck, slidesOfMainDeck, true);
        }
    }

    private void previousSlideSideDeck() {
        if (currSlideNumberSideDeck == 0) {
            prepareMessageContainer();

            message.setText("This is the first slide of Slide Deck!");
            new Shake(message).play();
        }
        else {
            previousSlide(sideDeckPane, shownOnStageSideDeck, slidesOfSideDeck, false);
        }
    }

    private void nextSlideSideDeck() {
        if (currSlideNumberSideDeck == slidesOfSideDeck.size() - 1) {
            prepareMessageContainer();

            message.setText("This is the last slide of Side Deck!");
            new Shake(message).play();
        }
        else {
            nextSlide(sideDeckPane, shownOnStageSideDeck, slidesOfSideDeck, false);
        }
    }

    private void prepareMessageContainer() {
        messageContainer.setStyle("-fx-background-color: white; -fx-background-radius: 100; -fx-border-radius: 100");
        new ZoomInRight(messageContainer).play();
    }

    private void disappearMessageContainer() {
        if (!message.getText().equals("")) new FadeOutRight(messageContainer).play();
    }

    public void run(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == nextMainDeck || mouseEvent.getSource() == nextMainDeckButtonLabel) {
            nextSlideMainDeck();
        } else if (mouseEvent.getSource() == previousMainDeck || mouseEvent.getSource() == previousMainDeckButtonLabel) {
            previousSlideMainDeck();
        } else if (mouseEvent.getSource() == nextSideDeck || mouseEvent.getSource() == nextSideDeckButtonLabel) {
            nextSlideSideDeck();
        } else if (mouseEvent.getSource() == previousSideDeck || mouseEvent.getSource() == previousSideDeckButtonLabel) {
            previousSlideSideDeck();
        }
    }
}
