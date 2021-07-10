package view.controller;

import animatefx.animation.*;
import controller.gamecontrollers.GeneralController;
import controller.gamecontrollers.gamestagecontroller.BattlePhaseController;
import controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import controller.gamecontrollers.gamestagecontroller.MainPhaseController;
import controller.gamecontrollers.gamestagecontroller.StandByPhaseController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.TypeOfHire;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.BoardProp.GameHouse;
import model.gameprop.BoardProp.HandHouse;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.User;
import model.userProp.UserInfoType;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameView {

    public ImageView yourAvatar;
    public ImageView opponentAvatar;
    public Label opponentUsername;
    public Label opponentNickname;
    public Label yourUsername;
    public Label yourNickname;
    public Pane yourHousesContainer;
    public Pane opponentHousesContainer;
    public GridPane yourMonsterHousesGridPane;
    public GridPane yourMagicHousesGridPane;
    public GridPane opponentMagicHousesGridPane;
    public GridPane opponentMonsterHousesGridPane;
    public Pane opponentHandContainer;
    public Pane yourHandContainer;
    public Pane yourGraveyardPane;
    public Pane opponentGraveyardPane;
    public Pane yourFieldPane;
    public Pane opponentFieldPane;
    public Pane yourDeckPane;
    public Pane opponentDeckPane;
    public ImageView selectedCardImageView;
    public AnchorPane root;
    public Button nextPhase;
    public Label phaseName;
    public Pane drawPhaseBox;
    public Pane standByPhaseBox;
    public Pane firstMainPhaseBox;
    public Pane battlePhaseBox;
    public Pane secondMainPhaseBox;
    public Pane endPhaseBox;
    public AnchorPane cardDescriptionArea;
    public Button summonButton;
    public Button setMonsterButton;
    public Label currentDeckNumber;
    public Label opponentDeckNumber;
    private Player playerYou;
    private Player playerOpponent;
    private GeneralController controller;
    private DrawPhaseController drawPhaseController;
    private MainPhaseController mainPhaseController;
    private BattlePhaseController battlePhaseController;
    private StandByPhaseController standByPhaseController;
    private Game game;

    {
        controller = new GeneralController();
        drawPhaseController = new DrawPhaseController();
        mainPhaseController = new MainPhaseController();
        battlePhaseController = new BattlePhaseController();
        standByPhaseController = new StandByPhaseController();
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        game = new Game(new Player(User.getUserByUserInfo("sas", UserInfoType.NICKNAME), 0),
                new Player(User.getUserByUserInfo("KaftarBaz", UserInfoType.NICKNAME), 0), 1);
        GameInProcess.setGame(game);

        playerYou = GameInProcess.getGame().getFirstPlayer();
        playerOpponent = GameInProcess.getGame().getSecondPlayer();

        User you = (User) playerYou.getUser();
        User opponent = (User) playerOpponent.getUser();

        summonButton.setVisible(false);
        setMonsterButton.setVisible(false);
        assert opponent != null;

        setNumberOfDeckCards();
        initializeInfos(you, opponent);

        initializeShadowEffect();

        initializeHouses(playerYou, yourMonsterHousesGridPane, yourMagicHousesGridPane);
        initializeHouses(playerOpponent, opponentMonsterHousesGridPane, opponentMagicHousesGridPane);

        initializeHand(playerYou, yourHandContainer, true);
        initializeHand(playerOpponent, opponentHandContainer, false);
    }

    private void setNumberOfDeckCards() {
        currentDeckNumber.setText(String.valueOf(playerYou.getDeck().getMainDeck().size()));
        opponentDeckNumber.setText(String.valueOf(playerOpponent.getDeck().getMainDeck().size()));
    }

    private void initializeShadowEffect() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(selectedCardImageView.getFitWidth());
        dropShadow.setHeight(selectedCardImageView.getFitHeight());
        selectedCardImageView.setEffect(dropShadow);
    }

    private void initializeHand(Player player, Pane handContainer, boolean isOpponentSide) {
        HandHouse[] handHouses = player.getBoard().getPlayerHand();

        handContainer.setPadding(new Insets(20));

        for (int i = 0; i < handHouses.length; i++) {
            HandHouse handHouse = handHouses[i];
            handHouse.setLayoutX(i * (10 + 61));
            handHouse.setLayoutY(20);
            handHouse.setPrefSize(61, 90);

            setMouseEnterEventForHand(handHouse);

            setMouseExitEventForHand(handHouse);

            setMouseClickEventForHand(handHouse);
            handContainer.getChildren().add(i, handHouse);
            handHouse.setImageOfCard(isOpponentSide);
        }
    }

    private void setMouseClickEventForHand(HandHouse handHouse) {
        handHouse.setOnMouseClicked(e -> {
            controller.selectCard(game, handHouse);
            if (game.getCardProp().doesBelongToCurrent()) {
                selectedCardImageView.setImage(Card.getCardImage(game.getCardProp().getCard()));
                new FlipInX(selectedCardImageView).play();
                deActiveActions();
                showAvailableActions(handHouse);
            }
        });
    }

    private void deActiveActions() {
        summonButton.setVisible(false);
        setMonsterButton.setVisible(false);
    }

    private void showAvailableActions(HandHouse handHouse) {
        if (game.getGameMainStage() == GameMainStage.FIRST_MAIN_PHASE) {
            if (handHouse.getCard() instanceof MonsterCard && game.getHiredMonster() == null) {
                summonButton.setVisible(true);
                setMonsterButton.setVisible(true);
                new FadeIn(summonButton).play();
                new FadeIn(setMonsterButton).play();
            }
        }
    }

    private void reloadPlayerHand(Pane handContainer, boolean isOpponentSide, HandHouse[] handHouses) {
        reloadImages();
        handContainer.getChildren().clear();
        makeHandPane(handContainer, handHouses);
        makeHandPaneReloadAnimation(handContainer, isOpponentSide);
    }

    private void makeHandPaneReloadAnimation(Pane handContainer, boolean isOpponentSide) {
        if (isOpponentSide) {
            SlideInDown animation = new SlideInDown(handContainer);
            animation.setSpeed(2);
            animation.play();
        } else {
            SlideInUp animation = new SlideInUp(handContainer);
            animation.setSpeed(2);
            animation.play();
        }
    }

    private void makeHandPane(Pane handContainer, HandHouse[] handHouses) {
        for (int j = 0; j < handHouses.length; j++) {
            if (handHouses[j].getStyle() == null) {
                break;
            }
            handHouses[j].setLayoutX(j * (10 + 61));
            handHouses[j].setLayoutY(20);
            handHouses[j].setPrefSize(61, 90);
            handContainer.getChildren().add(j, handHouses[j]);
        }
    }

    private void reloadImages() {
        HandHouse[] handHouses = game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand();
        for (int j = 0; j < handHouses.length; j++) {
            for (int k = 0; k < handHouses.length - 1; k++) {
                if (handHouses[k].doesHaveImage()) {
                    HandHouse temp = handHouses[k];
                    handHouses[k] = handHouses[k + 1];
                    handHouses[k + 1] = temp;
                }
            }
        }
    }

    private void setMouseExitEventForHand(HandHouse handHouse) {
        handHouse.setOnMouseExited(e -> {
            handHouse.setLayoutY(20);
            handHouse.setScaleX(1);
            handHouse.setScaleY(1);

            handHouse.setEffect(null);
        });
    }

    private void setMouseEnterEventForHand(HandHouse handHouse) {
        handHouse.setOnMouseEntered(e -> {
            handHouse.setLayoutY(10);
            handHouse.setScaleX(1.2);
            handHouse.setScaleY(1.2);
            handHouse.toFront();

            DropShadow dropShadow = new DropShadow();
            handHouse.setEffect(dropShadow);
        });
    }

    private void initializeHouses(Player player, GridPane monsterHousesGridPane, GridPane magicHousesGridPane) {
        monsterHousesGridPane.setHgap(25);
        monsterHousesGridPane.setPadding(new Insets(10));
        magicHousesGridPane.setHgap(25);
        magicHousesGridPane.setPadding(new Insets(10));

        MonsterHouse[] monsterHouses = player.getBoard().getMonsterHouse();
        for (int i = 0; i < monsterHouses.length; i++) {
            MonsterHouse monsterHouse = monsterHouses[i];
            monsterHousesGridPane.add(monsterHouse, i, 0, 1, 1);

            monsterHouse.setPrefSize(63, 102);
            handleOnMouseClicked(monsterHouse);
        }

        MagicHouse[] magicHouses = player.getBoard().getMagicHouse();
        for (int i = 0; i < magicHouses.length; i++) {
            MagicHouse magicHouse = magicHouses[i];
            magicHousesGridPane.add(magicHouse, i, 0, 1, 1);
            magicHouse.setPrefSize(63, 102);

            handleOnMouseClicked(magicHouse);
        }
    }

    private void handleOnMouseClicked(GameHouse gameHouse) {
        gameHouse.setOnMouseClicked(e -> {
            if (gameHouse.getCardImage() != null) {
                controller.selectCard(game, gameHouse);
                if (gameHouse.getState().equals("hidden") || gameHouse.getState().equals("defenceHidden")) {
                    selectedCardImageView.setImage(GameHouse.getBackOfCardImage());
                } else {
                    selectedCardImageView.setImage(Card.getCardImage(game.getCardProp().getCard()));
                }
                new FlipInX(selectedCardImageView).play();
            }
            //TODO: make a field "selectedCard : Card" which holds the selected card!
        });
    }


    private void initializeInfos(User you, User opponent) {
        try {
            yourAvatar.setImage(new Image(new FileInputStream(you.getAvatarAddress())));
            opponentAvatar.setImage(new Image(new FileInputStream(opponent.getAvatarAddress())));
        } catch (FileNotFoundException e) {
            System.out.println("Aks ha inja naboodan ke :(");
        }

        yourUsername.setText("Username: " + you.getUsername());
        yourNickname.setText("Nickname: " + you.getNickname());

        opponentUsername.setText("Username: " + opponent.getUsername());
        opponentNickname.setText("Nickname: " + opponent.getNickname());
    }


    private void showGraveYard(Player player) {
        Pane graveYardPane = new Pane();
        int sizeOfFlowPane = player.getBoard().getGraveYard().getDestroyedCards().size();

        FlowPane graveYardFlowPane = makePopUpFlowPane(sizeOfFlowPane);


        ArrayList<Card> graveYardArrList = player.getBoard().getGraveYard().getDestroyedCards();
        for (Card card : graveYardArrList) {
            ImageView imageView;
            imageView = new ImageView(Card.getCardImage(card));

            setPopUpCardsEffects(imageView);

            graveYardFlowPane.getChildren().add(imageView);
        }

        addNodesToPopUpPage(graveYardPane, player, graveYardFlowPane);
    }

    @NotNull
    private ScrollPane makePopUpScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(700);
        scrollPane.setPrefHeight(190);
        scrollPane.setLayoutX(30);
        scrollPane.setLayoutY(60);
        scrollPane.setPrefViewportHeight(190);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.toFront();
        return scrollPane;
    }

    private void setPopUpCardsEffects(ImageView imageView) {
        imageView.setOnMouseEntered(e -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);

            DropShadow dropShadow = new DropShadow();
            imageView.setEffect(dropShadow);
        });

        imageView.setOnMouseExited(e -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);

            imageView.setEffect(null);

        });

        imageView.setOnMouseClicked(e -> {
            selectedCardImageView = imageView;
        });
    }

    @NotNull
    private FlowPane makePopUpFlowPane(int sizeOfFlowPane) {
        FlowPane graveYardFlowPane = new FlowPane();
        graveYardFlowPane.setHgap(14);
        graveYardFlowPane.setVgap(10);
        graveYardFlowPane.setPrefWrapLength(sizeOfFlowPane * (14.222 + 109) + 28.444);
        graveYardFlowPane.setPrefHeight(190);
        graveYardFlowPane.setPadding(new Insets(14));
        graveYardFlowPane.setStyle("-fx-background-image: url('/graphicprop/images/dirtyBoardBG.jpg'); -fx-background-size: cover");
        return graveYardFlowPane;
    }

    public void showTributeItems() {
        Pane tributePane = new Pane();
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        int sizeOfFlowPane = player.getBoard().numberOfFullHouse("monster");

        FlowPane tributeFlowPane = makePopUpFlowPane(sizeOfFlowPane);


        MonsterHouse[] monsterHouses = player.getBoard().getMonsterHouse();
        for (MonsterHouse house : monsterHouses) {
            if (house.getCard() != null) {
                ImageView imageView;
                imageView = new ImageView(Card.getCardImage(house.getCard()));
                setPopUpCardsEffects(imageView);
                tributeFlowPane.getChildren().add(imageView);
            }
        }

        addNodesToPopUpPage(tributePane, player, tributeFlowPane);
    }

    private void addNodesToPopUpPage(Pane tributePane, Player player, FlowPane tributeFlowPane) {
        Button closeButton = seCloseButtonPopUp(tributePane);


        Label title = setPopUpName(player);

        ScrollPane scrollPane = makePopUpScrollPane();

        tributeFlowPane.setMinWidth(700);
        scrollPane.setContent(tributeFlowPane);

        tributePane.toFront();
        tributePane.setPrefHeight(300);
        tributePane.setPrefWidth(760);
        tributePane.setStyle("-fx-background-image: url('/graphicprop/images/brickwall.jpg'); -fx-background-size: cover");
        tributePane.setLayoutX(150);
        tributePane.setLayoutY(200);

        tributePane.getChildren().addAll(closeButton, title, scrollPane);

        for (Node child : root.getChildren()) {
            child.setDisable(true);
            child.setOpacity(0.3);
        }
        root.getChildren().add(tributePane);
        new ZoomIn(tributePane).play();
    }

    @NotNull
    private Label setPopUpName(Player player) {
        Label title = new Label();
        title.setText(player.getUser().getNickname() + "'s graveyard:");
        title.setLayoutX(30);
        title.setLayoutY(30);
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-text-fill: white");
        return title;
    }

    @NotNull
    private Button seCloseButtonPopUp(Pane graveYardPane) {
        Button closeButton = new Button();
        closeButton.setText("X");
        closeButton.setOpacity(0.6);
        closeButton.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand");
        closeButton.setPrefHeight(10);
        closeButton.setPrefWidth(15);
        closeButton.setOnMouseEntered(e -> {
            closeButton.setOpacity(1);
        });
        closeButton.setOnMouseExited(e -> {
            closeButton.setOpacity(0.6);
        });
        closeButton.setOnMouseClicked(e -> {
            new ZoomOut(graveYardPane).play();
            root.getChildren().remove(graveYardPane);
            for (Node child : root.getChildren()) {
                child.setDisable(false);
                child.setOpacity(1);
            }
        });
        return closeButton;
    }

    public void run(MouseEvent mouseEvent) throws FileNotFoundException {
        if (mouseEvent.getSource() == yourGraveyardPane) {
            showGraveYard(playerYou);
        } else if (mouseEvent.getSource() == opponentGraveyardPane) {
            showGraveYard(playerOpponent);
        } else if (mouseEvent.getSource() == nextPhase) {
            phaseName.setText(controller.nextPhase(game));
            setNumberOfDeckCards();
            new BounceIn(nextPhase).play();
            if (phaseName.getText().equals("draw phase")) {
                swapColorForChangeTurn();
                turnPlayerHandCard();
            }
            setScaleForCurrentPhase(phaseName.getText());
        } else if (mouseEvent.getSource() == summonButton) {
            mainPhaseController.hireCard(game, TypeOfHire.SUMMON);
        } else if (mouseEvent.getSource() == setMonsterButton) {
            mainPhaseController.hireCard(game, TypeOfHire.SET);
        }
    }

    private void swapColorForChangeTurn() {
        if (endPhaseBox.getStyle().contains("red")) {
            swapPhaseNameButton("-fx-background-image: url('graphicprop/images/greenPhaseBg.png')");
        } else if (endPhaseBox.getStyle().contains("green")) {
            swapPhaseNameButton("-fx-background-image: url('graphicprop/images/redPhaseBg.png')");
        } else {
            swapPhaseNameButton("-fx-background-image: url('graphicprop/images/redPhaseBg.png')");
        }
        if (cardDescriptionArea.getStyle().contains("youUser")) {
            cardDescriptionArea.setStyle("-fx-background-image: url('graphicprop/images/opponentUserInfoBgjpeg.jpeg')");
        } else if (cardDescriptionArea.getStyle().contains("opponentUser")) {
            cardDescriptionArea.setStyle("-fx-background-image: url('graphicprop/images/youUserInfoBGjpeg.jpeg')");
        } else {
            cardDescriptionArea.setStyle("-fx-background-image: url('graphicprop/images/opponentUserInfoBgjpeg.jpeg')");
        }
        new FadeIn(cardDescriptionArea).play();
    }

    private void turnPlayerHandCard() {
        for (HandHouse house : game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand()) {
            house.setImageOfCard(true);
            FlipInX flipInX = new FlipInX(house);
            flipInX.setSpeed(0.5);
            flipInX.play();
        }
        for (HandHouse house : game.getPlayer(SideOfFeature.OPPONENT).getBoard().getPlayerHand()) {
            house.setImageOfCard(false);
            FlipInX flipInX = new FlipInX(house);
            flipInX.setSpeed(0.5);
            flipInX.play();
        }
    }

    private void swapPhaseNameButton(String s) {

        drawPhaseBox.setStyle(s);
        standByPhaseBox.setStyle(s);
        firstMainPhaseBox.setStyle(s);
        battlePhaseBox.setStyle(s);
        secondMainPhaseBox.setStyle(s);
        endPhaseBox.setStyle(s);

        new BounceIn(drawPhaseBox).play();
        new BounceIn(standByPhaseBox).play();
        new BounceIn(firstMainPhaseBox).play();
        new BounceIn(battlePhaseBox).play();
        new BounceIn(secondMainPhaseBox).play();
        new BounceIn(endPhaseBox).play();


    }

    private void setScaleForCurrentPhase(String currentPhase) {
        setNormalScale();
        switch (currentPhase) {
            case "draw phase": {
                drawPhaseBox.setScaleX(1.2);
                drawPhaseBox.setScaleY(1.2);
                break;
            }
            case "first main phase": {
                firstMainPhaseBox.setScaleX(1.2);
                firstMainPhaseBox.setScaleY(1.2);
                break;
            }
            case "second main phase": {
                secondMainPhaseBox.setScaleX(1.2);
                secondMainPhaseBox.setScaleY(1.2);
                break;
            }

            case "battle phase": {
                battlePhaseBox.setScaleX(1.2);
                battlePhaseBox.setScaleY(1.2);
                break;
            }

            case "stand by phase": {
                standByPhaseBox.setScaleX(1.2);
                standByPhaseBox.setScaleY(1.2);
                break;
            }

            case "end phase": {
                endPhaseBox.setScaleX(1.2);
                endPhaseBox.setScaleY(1.2);
                break;
            }
        }
    }

    private void setNormalScale() {
        drawPhaseBox.setScaleX(1.0);
        drawPhaseBox.setScaleY(1.0);

        firstMainPhaseBox.setScaleX(1.0);
        firstMainPhaseBox.setScaleY(1.0);


        secondMainPhaseBox.setScaleX(1.0);
        secondMainPhaseBox.setScaleY(1.0);


        battlePhaseBox.setScaleX(1.0);
        battlePhaseBox.setScaleY(1.0);


        standByPhaseBox.setScaleX(1.0);
        standByPhaseBox.setScaleY(1.0);

        endPhaseBox.setScaleX(1.0);
        endPhaseBox.setScaleY(1.0);

    }
}
