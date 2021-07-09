package view.controller;

import animatefx.animation.*;
import controller.gamecontrollers.GeneralController;
import controller.gamecontrollers.gamestagecontroller.BattlePhaseController;
import controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import controller.gamecontrollers.gamestagecontroller.MainPhaseController;
import controller.gamecontrollers.gamestagecontroller.StandByPhaseController;
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
import model.cards.CardHouse;
import model.cards.cardsProp.Card;
import model.enums.Origin;
import model.gameprop.BoardProp.GameHouse;
import model.gameprop.BoardProp.HandHouse;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;

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

    public void initialize() throws FileNotFoundException {
        LoginUser.setUser(User.getUserByUserInfo("Yaroo", UserInfoType.USERNAME));

        User you = LoginUser.getUser();

        //TODO: Replace this  instantiation with a more general one!
        User opponent = User.getUserByUserInfo("ali", UserInfoType.USERNAME);
        //

        playerYou = new Player(you, 0);
        playerOpponent = new Player(opponent, 0);

        //TODO: replace this block with the result of Rock/paper/scissors
        Player firstPlayer = playerYou;
        Player secondPlayer = playerOpponent;
        //

        GameInProcess.setGame(game = new Game(firstPlayer, secondPlayer, 1));


        assert opponent != null;
        initializeInfos(you, opponent);


        initializeHouses(playerYou, yourMonsterHousesGridPane, yourMagicHousesGridPane);
        initializeHouses(playerOpponent, opponentMagicHousesGridPane, opponentMonsterHousesGridPane);

        initializeHand(playerYou, yourHandContainer, true);
        initializeHand(playerOpponent, opponentHandContainer, false);
    }

    private void initializeHand(Player player, Pane handContainer, boolean isOpponentSide) {
        HandHouse[] handHouses = player.getBoard().getPlayerHand();

        handContainer.setPadding(new Insets(20));

        for (int i = 0; i < handHouses.length; i++) {
            HandHouse handHouse = handHouses[i];
            handHouse.setImageOfCard();
            handHouse.setLayoutX(i * (10 + 61));
            handHouse.setLayoutY(20);
            handHouse.setPrefSize(61, 90);

            setMouseEnterEventForHand(handHouse);

            setMouseExitEventForHand(handHouse);

            setMouseClickEventForHand(handContainer, isOpponentSide, handHouses, handHouse);
            handContainer.getChildren().add(i, handHouse);
        }
    }

    private void setMouseClickEventForHand(Pane handContainer, boolean isOpponentSide, HandHouse[] handHouses, HandHouse handHouse) {
        handHouse.setOnMouseClicked(e -> {
            reloadImages(handHouses);
            handContainer.getChildren().clear();
            makeHandPane(handContainer, handHouses);
            makeHandPaneReloadAnimation(handContainer, isOpponentSide);
        });
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

    private void reloadImages(HandHouse[] handHouses) {
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
            handHouse.setScaleX(1.1);
            handHouse.setScaleY(1.1);
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

            monsterHouse.setPrefSize(42, 70);
            handleOnMouseClicked(monsterHouse);
        }

        MagicHouse[] magicHouses = player.getBoard().getMagicHouse();
        for (int i = 0; i < magicHouses.length; i++) {
            MagicHouse magicHouse = magicHouses[i];
            magicHousesGridPane.add(magicHouse, i, 0, 1, 1);
            magicHouse.setPrefSize(42, 70);

            handleOnMouseClicked(magicHouse);
        }
    }

    private void handleOnMouseClicked(GameHouse gameHouse) {
        gameHouse.setOnMouseClicked(e -> {
            if (gameHouse.getCardImage() != null) {
                selectedCardImageView.setImage(gameHouse.getCardImage());
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


        FlowPane graveYardFlowPane = new FlowPane();
        graveYardFlowPane.setHgap(14);
        graveYardFlowPane.setVgap(10);
        graveYardFlowPane.setPrefWrapLength((player.getBoard().getGraveYard().getDestroyedCards().size()) * (14.222 + 109) + 28.444);
        graveYardFlowPane.setPrefHeight(190);
        graveYardFlowPane.setPadding(new Insets(14));
        graveYardFlowPane.setStyle("-fx-background-image: url('/graphicprop/images/dirtyBoardBG.jpg'); -fx-background-size: cover");


        ArrayList<Card> graveYardArrList = player.getBoard().getGraveYard().getDestroyedCards();
        for (Card card : graveYardArrList) {
            String nameWithoutSpaces = card.getName();
            nameWithoutSpaces.replaceAll("\\s+", "");


            FileInputStream fileInputStream;
            Image image;
            CardHouse cardHouse;
            try {
                ImageView imageView;
                fileInputStream = new FileInputStream("/src/main/resources/graphicprop/images/Cards/Monsters/" + nameWithoutSpaces + ".jpg");
                image = new Image(fileInputStream);
                imageView = new ImageView(image);


                imageView.setOnMouseEntered(e -> {
                    imageView.setScaleX(1.1);
                    imageView.setScaleY(1.1);

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

                cardHouse = new CardHouse(card, imageView, image, Origin.GAME);
                graveYardFlowPane.getChildren().add(imageView);
            } catch (FileNotFoundException e) {
                System.out.println("Ey baba :\"( aksa koshan pass!");
            }
        }

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


        Label title = new Label();
        title.setText(player.getUser().getNickname() + "'s graveyard:");
        title.setLayoutX(30);
        title.setLayoutY(30);
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-text-fill: white");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(700);
        scrollPane.setPrefHeight(190);
        scrollPane.setLayoutX(30);
        scrollPane.setLayoutY(60);
        scrollPane.setPrefViewportHeight(190);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.toFront();

        graveYardFlowPane.setMinWidth(700);
        scrollPane.setContent(graveYardFlowPane);

        graveYardPane.toFront();
        graveYardPane.setPrefHeight(300);
        graveYardPane.setPrefWidth(760);
        graveYardPane.setStyle("-fx-background-image: url('/graphicprop/images/brickwall.jpg'); -fx-background-size: cover");
        graveYardPane.setLayoutX(150);
        graveYardPane.setLayoutY(200);

        graveYardPane.getChildren().addAll(closeButton, title, scrollPane);

        for (Node child : root.getChildren()) {
            child.setDisable(true);
            child.setOpacity(0.3);
        }
        root.getChildren().add(graveYardPane);
        new ZoomIn(graveYardPane).play();
    }

    public void run(MouseEvent mouseEvent) throws FileNotFoundException {
        if (mouseEvent.getSource() == yourGraveyardPane) {
            showGraveYard(playerYou);
        } else if (mouseEvent.getSource() == opponentGraveyardPane) {
            showGraveYard(playerOpponent);
        } else if (mouseEvent.getSource() == nextPhase) {
            phaseName.setText(controller.nextPhase(game));
            new BounceIn(nextPhase).play();
        }
    }
}
