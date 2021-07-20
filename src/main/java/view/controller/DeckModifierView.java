package view.controller;

import animatefx.animation.*;
import controller.menues.menuhandlers.menucontrollers.DeckModifierController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.cards.CardHouse;
import model.cards.cardsEnum.Magic.RestrictionTypeInAdding;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.Menu;
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

    public AnchorPane anchorPane;

    protected static final DataFormat buttonFormat = new DataFormat("MyButton");

    protected ImageView draggingImage;
    protected Card draggingCard;
    protected Image unknownCard;

    public ScrollPane scrollPane;
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
    public ImageView selectedCardImageView;
    public Label selectedCardDescriptionLabel;
    public Button moveToMainDeckButton;
    public Button moveToSideDeckButton;
    public Button moveToCollectionButton;
    public Button backButton;

    protected ArrayList<Pane> slidesOfMainDeck;
    protected ArrayList<Pane> slidesOfSideDeck;
    protected ArrayList<Card> collection;
    protected ArrayList<Card> mainDeck;
    protected ArrayList<Card> sideDeck;

    protected Pane shownOnStageMainDeck;
    protected Pane shownOnStageSideDeck;
    protected DeckModifierController controller;
    protected User loggedInUser;
    protected Deck toShowDeck;
    protected Card selectedCard;
    protected TreeMap<String, ImageView> cardPictures;
    protected int currSlideNumberMainDeck;
    protected int currSlideNumberSideDeck;
    protected double scrolled;

    {
        controller = DeckModifierController.getInstance();
        loggedInUser = LoginUser.getUser();
        toShowDeck = loggedInUser.getDeckOnModify();
        collection = loggedInUser.getCardCollection();
        try {
            unknownCard = new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        cardPictures = new TreeMap<>();
        initializeDeck();

        initializeRoot();

        initializeCollection();

        initializeSlides();

        makeIntendedTransferButtonsInvisible();
        makeIntendedTransferButtonsVisible();

        try {
            selectedCardImageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));
        } catch (FileNotFoundException ignored) {
        }

    }

    protected void initializeDeck() {
        mainDeck = toShowDeck.getMainDeck();
        sideDeck = toShowDeck.getSideDeck();
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
        } else if (mouseEvent.getSource() == moveToMainDeckButton) {
            moveToMainDeck();
            emptySelectedCard();
        } else if (mouseEvent.getSource() == moveToSideDeckButton) {
            moveToSideDeck();
            emptySelectedCard();
        } else if (mouseEvent.getSource() == moveToCollectionButton) {
            moveToCollection();
            emptySelectedCard();
        } else if (mouseEvent.getSource() == backButton) {
            back();
        }
    }

    protected void emptySelectedCard() throws FileNotFoundException {
        selectedCard = null;
        selectedCardDescriptionLabel.setText("");
        selectedCardImageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg")));

        new FlipInY(selectedCardImageView).play();
        new BounceOutUp(selectedCardDescriptionLabel).play();
    }

    protected void initializeRoot() {
        anchorPane.setMaxWidth(1000);
    }

    protected void initializeSlides() {
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

        mainDeckPane.getChildren().add(slidesOfMainDeck.get(0));
        sideDeckPane.getChildren().add(slidesOfSideDeck.get(0));
        shownOnStageMainDeck = slidesOfMainDeck.get(0);
        shownOnStageSideDeck = slidesOfSideDeck.get(0);

        initializeMainDeck();
        initializeSideDeck();
    }

    protected void mainDeckCardSlotStyler(int j, int k, ImageView imageView) {
        deckCardSlotGeneralStyler(k, imageView);
        if ((j == 0)) {
            imageView.setLayoutY(0);
        } else {
            imageView.setLayoutY(120 + 10);
        }
    }

    protected void deckCardSlotGeneralStyler(int k, ImageView imageView) {
        imageView.setStyle("-fx-cursor: hand");
        imageView.setFitWidth(82);
        imageView.setFitHeight(120);
        imageView.setLayoutX(10 + (k * (82 + 10)));
    }

    protected void handleOnMouseEntered(ImageView imageView) {
        imageView.setOnMouseEntered(mouseEvent -> {
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setWidth(imageView.getFitWidth());
            dropShadow.setHeight(imageView.getFitHeight());
            imageView.setEffect(dropShadow);

        });
    }

    protected void handleOnMouseExited(ImageView imageView) {
        imageView.setOnMouseExited(mouseEvent -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            imageView.setEffect(null);
        });
    }

    protected void handleOnMouseClick(ImageView imageView, CardHouse cardHouse) {
        imageView.setOnMouseClicked(mouseEvent -> {

            selectedCardImageView.setImage(cardHouse.getImage());
            new Tada(imageView).play();
            new FlipInX(selectedCardImageView).play();

            selectedCard = cardHouse.getCard();

            makeIntendedTransferButtonsInvisible();
            makeIntendedTransferButtonsVisible();

            selectedCardDescriptionLabel.setText(selectedCard.getCardDetailWithEnters());
            new BounceInUp(selectedCardDescriptionLabel).play();
        });
    }

    protected void makeIntendedTransferButtonsVisible() {
        if (selectedCard != null) {
            if (mainDeck.contains(selectedCard)) {
                moveToSideDeckButton.setVisible(true);
                moveToCollectionButton.setVisible(true);

                new FadeInDown(moveToCollectionButton).play();
                new FadeInDown(moveToSideDeckButton).play();
            } else if (sideDeck.contains(selectedCard)) {
                System.out.println("here 2");
                moveToMainDeckButton.setVisible(true);
                moveToCollectionButton.setVisible(true);

                new FadeInDown(moveToCollectionButton).play();
                new FadeInDown(moveToMainDeckButton).play();
            } else if (LoginUser.getUser().getCardCollection().contains(selectedCard)) {
                moveToSideDeckButton.setVisible(true);
                moveToMainDeckButton.setVisible(true);

                new FadeInDown(moveToMainDeckButton).play();
                new FadeInDown(moveToSideDeckButton).play();
            }
        }
    }

    protected void makeIntendedTransferButtonsInvisible() {
        if (selectedCard == null) {
            moveToCollectionButton.setVisible(false);
            moveToMainDeckButton.setVisible(false);
            moveToSideDeckButton.setVisible(false);

            new FadeOutDown(moveToCollectionButton).play();
            new FadeOutDown(moveToMainDeckButton).play();
            new FadeOutDown(moveToSideDeckButton).play();
        } else if (mainDeck.contains(selectedCard)) {
            moveToMainDeckButton.setVisible(false);

            new FadeOutDown(moveToMainDeckButton).play();
        } else if (sideDeck.contains(selectedCard)) {
            moveToSideDeckButton.setVisible(false);

            new FadeOutDown(moveToSideDeckButton).play();
        } else if (LoginUser.getUser().getCardCollection().contains(selectedCard)) {
            moveToCollectionButton.setVisible(false);

            new FadeOutDown(moveToCollectionButton).play();
        }

    }

    protected void initializeCollection() {
        initializeScrollBar();
    }

    protected void initializeScrollBar() {
        User user = LoginUser.getUser();

        ArrayList<Card> collection = DeckModifierController.cardNameAlphabetSorter(user.getCardCollection());
        // note this arrayList doesn't have the same refrence as the actual collection of user!

        FlowPane collectionFlowPane = new FlowPane();
        collectionFlowPaneStyler(collection, collectionFlowPane);

        for (int i = 0; i < collection.size(); i++) {
            Card card = collection.get(i);
            ImageView imageView = new ImageView();

            handleOnMouseEntered(imageView);
            handleOnMouseExited(imageView);

            collectionCardSlotStyler(i, imageView);

            CardHouse cardHouse = makeCardHouseAndAssignImage(imageView, card);

            handleOnMouseClick(imageView, cardHouse);

            collectionFlowPane.getChildren().add(imageView);
        }

        configureScrollPane(collectionFlowPane);
    }

    protected void configureScrollPane(FlowPane collectionFlowPane) {
        collectionFlowPane.setMinWidth(1000);
        scrollPane.setContent(collectionFlowPane);
        scrollPane.setPrefViewportHeight(190);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    }

    protected void collectionFlowPaneStyler(ArrayList<Card> collection, FlowPane collectionFlowPane) {
        collectionFlowPane.setHgap(14);
        collectionFlowPane.setVgap(10);
        collectionFlowPane.setPrefWrapLength((collection.size()) * (14.222 + 109) + 28.444);
        collectionFlowPane.setPrefHeight(190);
        collectionFlowPane.setPadding(new Insets(14));
        collectionFlowPane.setStyle("-fx-background-image: url('/graphicprop/images/dirtyBoardBG.jpg'); -fx-background-size: cover");
    }

    protected void collectionCardSlotStyler(int i, ImageView imageView) {
        imageView.setFitHeight(160);
        imageView.setFitWidth(109);
        imageView.setStyle("-fx-cursor: hand");
    }

    protected void initializeMainDeck() {
        int numberOfMainDeckSlides = 6;
        for (int i = 0; i < numberOfMainDeckSlides; i++) {
            slidesOfMainDeck.get(i).getChildren().clear();
            slidesOfMainDeck.get(i).setLayoutX(15);
            slidesOfMainDeck.get(i).setLayoutY(10);

            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 5; k++) {
                    ImageView imageView = new ImageView();
                    mainDeckCardSlotStyler(j, k, imageView);
                    handleOnMouseEntered(imageView);
                    handleOnMouseExited(imageView);

                    if ((i * 10) + (5 * j) + k >= mainDeck.size()) {
                        // filling the remained card slots!
                        imageView.setImage(unknownCard);
                        imageView.setDisable(true);
                    } else {
                        Card card = mainDeck.get((i * 10) + (5 * j) + k);
                        CardHouse cardHouse = makeCardHouseAndAssignImage(imageView, card);
                        handleOnMouseClick(imageView, cardHouse);
                        dragButton(imageView, cardHouse);
                    }
                    slidesOfMainDeck.get(i).getChildren().add(imageView);
                }
            }
        }
    }

    protected void dragButton(ImageView image, CardHouse cardHouse) {
        if (!image.getImage().equals(unknownCard)) {
            image.setOnDragDetected(e -> {
                Dragboard db = image.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(image.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();
                cc.put(buttonFormat, " ");
                db.setContent(cc);
                draggingImage = image;
                draggingCard = cardHouse.getCard();
                selectedCard = draggingCard;
            });
        }
    }

    protected void addDropHandling(Pane droppedPane) {
        droppedPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat) && draggingImage != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        droppedPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            if (db.hasContent(buttonFormat)) {
                e.setDropCompleted(true);
                if (droppedPane.equals(shownOnStageMainDeck)) {
                    moveToMainDeck();
                } else {
                    moveToSideDeck();
                }

                draggingImage = null;
                draggingCard = null;
                selectedCard = null;
                mainDeck = toShowDeck.getMainDeck();
                sideDeck = toShowDeck.getSideDeck();

                initializeMainDeck();
                initializeSideDeck();

                new SlideInLeft(mainDeckPane).play();
                new SlideInLeft(sideDeckPane).play();
            }
        });

    }

    protected void initializeSideDeck() {
        int numberOfSideDeckSlides = 3;
        for (int i = 0; i < numberOfSideDeckSlides; i++) {
            slidesOfSideDeck.get(i).getChildren().clear();
            slidesOfSideDeck.get(i).setLayoutX(15);
            slidesOfSideDeck.get(i).setLayoutY(10);

            for (int j = 0; j < 5; j++) {
                ImageView imageView = new ImageView();

                deckCardSlotGeneralStyler(j, imageView);

                handleOnMouseEntered(imageView);
                handleOnMouseExited(imageView);

                if ((5 * i) + j >= sideDeck.size()) {
                    //filling the remained card slots!
                    imageView.setImage(unknownCard);
                    imageView.setDisable(true);

                } else {
                    Card card = sideDeck.get((5 * i) + j);
                    CardHouse cardHouse = makeCardHouseAndAssignImage(imageView, card);

                    handleOnMouseClick(imageView, cardHouse);
                    dragButton(imageView, cardHouse);
                }

                slidesOfSideDeck.get(i).getChildren().add(imageView);
            }
        }
    }

    protected CardHouse makeCardHouseAndAssignImage(ImageView imageView, Card card) {
        FileInputStream fileInputStream;
        Image image = null;

        String name = card.getName();
        String nameWithoutSpace = name.replaceAll("\\s+", "");

        try {
            if (card instanceof MonsterCard) {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpace + ".jpg");
                image = new Image(fileInputStream);
            } else if (card instanceof MagicCard) {
                fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
                image = new Image(fileInputStream);
            }
        } catch (FileNotFoundException e) {
            try {
                if (card instanceof MonsterCard) {
                    fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/newMonster.jpg");
                }
                else {
                    fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/newMagic.jpg");
                }
                image = new Image(fileInputStream);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        imageView.setImage(image);

        return new CardHouse(card, imageView, image, Origin.DECK_MENU);
    }

    protected void previousSlide(AnchorPane containerPane, Pane shownPane, ArrayList<Pane> slides, boolean isMainDeck) {
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
        } else {
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

    protected void nextSlide(AnchorPane containerPane, Pane shownPane, ArrayList<Pane> slides, boolean isMainDeck) {
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
        } else {
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

    protected void previousSlideMainDeck() {
        if (currSlideNumberMainDeck == 0) {
            prepareMessageContainer();

            message.setText("This is the first slide of Main Deck!");
            new Shake(message).play();
        } else {
            previousSlide(mainDeckPane, shownOnStageMainDeck, slidesOfMainDeck, true);
        }

    }

    protected void nextSlideMainDeck() {
        if (currSlideNumberMainDeck == slidesOfMainDeck.size() - 1) {
            prepareMessageContainer();

            message.setText("This is the last slide of Main Deck!");
            new Shake(message).play();
        } else {
            nextSlide(mainDeckPane, shownOnStageMainDeck, slidesOfMainDeck, true);
        }
    }

    protected void previousSlideSideDeck() {
        if (currSlideNumberSideDeck == 0) {
            prepareMessageContainer();

            message.setText("This is the first slide of Slide Deck!");
            new Shake(message).play();
        } else {
            previousSlide(sideDeckPane, shownOnStageSideDeck, slidesOfSideDeck, false);
        }
    }

    protected void nextSlideSideDeck() {
        if (currSlideNumberSideDeck == slidesOfSideDeck.size() - 1) {
            prepareMessageContainer();

            message.setText("This is the last slide of Side Deck!");
            new Shake(message).play();
        } else {
            nextSlide(sideDeckPane, shownOnStageSideDeck, slidesOfSideDeck, false);
        }
    }

    protected void prepareMessageContainer() {
        messageContainer.setStyle("-fx-background-color: white; -fx-background-radius: 100; -fx-border-radius: 100");
        new ZoomInRight(messageContainer).play();
    }

    protected void disappearMessageContainer() {
        if (!message.getText().equals("")) {
            message.setText("");
            new FadeOutRight(messageContainer).play();
        }
    }

    protected void moveToCollection() {
        if (selectedCard == null) {
            prepareMessageContainer();

            message.setText("You don't have any card selected!");
            new Shake(message).play();
        } else {
            addCardToCollection();
            disappearMessageContainer();
        }

    }

    protected void addCardToCollection() {
        disappearMessageContainer();

        if (toShowDeck.getMainDeck().contains(selectedCard)) {
            DeckModifierController.removeCardFromMainDeck(selectedCard, toShowDeck.getName());
            initializeDeck();
        }
        if (toShowDeck.getSideDeck().contains(selectedCard)) {
            DeckModifierController.removeCardFromSideDeck(selectedCard, toShowDeck.getName());
            initializeDeck();
        }

        mainDeck = toShowDeck.getMainDeck();
        sideDeck = toShowDeck.getSideDeck();

        initializeCollection();
        initializeMainDeck();
        initializeSideDeck();

        new SlideInUp(scrollPane).play();
        new SlideInLeft(mainDeckPane).play();
        new SlideInLeft(sideDeckPane).play();

    }

    protected void moveToMainDeck() {
        if (selectedCard == null) {
            prepareMessageContainer();

            message.setText("You don't have any card selected!");
            new Shake(message).play();
        } else if (mainDeck.contains(selectedCard)) {
            prepareMessageContainer();

            message.setText("This card is already in your Main Deck");
            new Shake(message).play();
        } else if (toShowDeck.getMainDeck().size() == 60) {
            prepareMessageContainer();

            message.setText("Your main deck is full!");
            new Shake(message).play();
        } else if (selectedCard instanceof MonsterCard) {
            if ((toShowDeck.numOfCardOccurrence(selectedCard.getName(), "both decks") == 3)) {
                prepareMessageContainer();

                message.setText("You already have 3 of this card in this deck");
                new Shake(message).play();
            } else {
                addCardToMainDeck();
                disappearMessageContainer();
            }
        } else {
            if (((MagicCard) selectedCard).getMagicRestrictionType() == RestrictionTypeInAdding.LIMITED && (toShowDeck.numOfCardOccurrence(selectedCard.getName(), "both decks") == 1)) {
                prepareMessageContainer();

                message.setText("You already have 1 of this card in this deck");
                new Shake(message).play();
            } else if (((MagicCard) selectedCard).getMagicRestrictionType() == RestrictionTypeInAdding.UNLIMITED && (toShowDeck.numOfCardOccurrence(selectedCard.getName(), "both decks") == 3)) {
                prepareMessageContainer();

                message.setText("You already have 3 of this card in this deck");
                new Shake(message).play();
            } else {
                addCardToMainDeck();
                disappearMessageContainer();
            }
        }
    }

    protected void moveToSideDeck() {
        if (selectedCard == null) {
            prepareMessageContainer();

            message.setText("You don't have any card selected!");
            new Shake(message).play();
        } else if (sideDeck.contains(selectedCard)) {
            prepareMessageContainer();

            message.setText("This card is already in your Side Deck");
            new Shake(message).play();
        } else if (toShowDeck.getSideDeck().size() == 15) {
            prepareMessageContainer();

            message.setText("Your side deck is full!");
            new Shake(message).play();
        } else if (selectedCard instanceof MonsterCard) {
            if ((toShowDeck.numOfCardOccurrence(selectedCard.getName(), "both decks") == 3)) {
                prepareMessageContainer();

                message.setText("You already have 3 of this card in this deck");
                new Shake(message).play();
            } else {
                addCardToSideDeck();
                disappearMessageContainer();

                prepareMessageContainer();
                message.setText("move complete!");
                new Shake(message).play();
            }
        } else {
            if (((MagicCard) selectedCard).getMagicRestrictionType() == RestrictionTypeInAdding.LIMITED && (toShowDeck.numOfCardOccurrence(selectedCard.getName(), "both decks") == 1)) {
                prepareMessageContainer();

                message.setText("You already have 1 of this card in this deck");
                new Shake(message).play();
            } else if (((MagicCard) selectedCard).getMagicRestrictionType() == RestrictionTypeInAdding.UNLIMITED && (toShowDeck.numOfCardOccurrence(selectedCard.getName(), "both decks") == 3)) {
                prepareMessageContainer();

                message.setText("You already have 3 of this card in this deck");
                new Shake(message).play();
            } else {
                addCardToSideDeck();
                disappearMessageContainer();

                prepareMessageContainer();
                message.setText("move complete!");
                new Shake(message).play();
            }
        }
    }

    protected void addCardToSideDeck() {
        disappearMessageContainer();

        DeckModifierController.addCardToSideDeck(selectedCard, toShowDeck.getName());
        initializeDeck();
        initializeCollection();
        initializeMainDeck();
        initializeSideDeck();

        new SlideInUp(scrollPane).play();
        new SlideInLeft(mainDeckPane).play();
        new SlideInLeft(sideDeckPane).play();
    }

    protected void addCardToMainDeck() {
        disappearMessageContainer();

        DeckModifierController.addCardToMainDeck(selectedCard, toShowDeck.getName());
        initializeDeck();
        initializeCollection();
        initializeMainDeck();
        initializeSideDeck();

        new SlideInUp(scrollPane).play();
        new SlideInLeft(mainDeckPane).play();
        new SlideInLeft(sideDeckPane).play();
    }

    protected void back() throws IOException {
        controller.moveToPage(backButton, Menu.DECKS_VIEW);
    }
}
