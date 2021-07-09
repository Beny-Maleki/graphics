package view.controller;

import animatefx.animation.FlipInX;
import animatefx.animation.SlideInUp;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    public void initialize() throws FileNotFoundException {
        LoginUser.setUser(User.getUserByUserInfo("Yaroo", UserInfoType.USERNAME));

        User you = LoginUser.getUser();

        //TODO: Replace this  instantiation with a more general one!
        User opponent = User.getUserByUserInfo("ali", UserInfoType.USERNAME);
        //

        Player playerYou = new Player(you, 0);
        Player playerOpponent = new Player(opponent, 0);

        //TODO: replace this block with the result of Rock/paper/scissors
        Player firstPlayer = playerYou;
        Player secondPlayer = playerOpponent;
        //

        GameInProcess.setGame(new Game(firstPlayer, secondPlayer, 1 ));


        assert opponent != null;
        initializeInfos(you, opponent);


        initializeHouses(playerYou, yourMonsterHousesGridPane, yourMagicHousesGridPane);
        initializeHouses(playerOpponent, opponentMagicHousesGridPane, opponentMonsterHousesGridPane);

        initializeHand(playerYou, yourHandContainer);
        initializeHand(playerOpponent, opponentHandContainer);
    }

    private void initializeHand(Player player, Pane handContainer) {
        HandHouse[] handHouses = player.getBoard().getPlayerHand();

        handContainer.setPadding(new Insets(20));

        for (int i = 0; i < handHouses.length; i++) {
            HandHouse handHouse = handHouses[i];
            handHouse.setLayoutX(i * (10 + 61));
            handHouse.setLayoutY(20);
            handHouse.setStyle("-fx-background-color: red");
            handHouse.setPrefSize(61, 90);

            handHouse.setOnMouseEntered(e -> {
                handHouse.setLayoutY(10);
                handHouse.setScaleX(1.1);
                handHouse.setScaleY(1.1);
                handHouse.toFront();

                DropShadow dropShadow = new DropShadow();
                handHouse.setEffect(dropShadow);
            });

            handHouse.setOnMouseExited(e -> {
                handHouse.setLayoutY(20);
                handHouse.setScaleX(1);
                handHouse.setScaleY(1);

                handHouse.setEffect(null);
            });

            handHouse.setOnMouseClicked(e -> {
                handHouse.setStyle(null);

                for (int j = 0; j < handHouses.length; j++) {
                    for (int k = 0; k < handHouses.length - 1; k++) {
                        if (handHouses[k].getStyle().equals(null) || handHouses[k].getStyle().equals("")) {
                            HandHouse temp = handHouses[k];
                            handHouses[k] = handHouses[k + 1];
                            handHouses[k + 1] = temp;
                        }
                    }
                }

                handContainer.getChildren().clear();
                for (int j = 0; j < handHouses.length; j++) {
                    if (handHouses[j].getStyle() == null) {
                        break;
                    }
                    handHouses[j].setLayoutX(j * (10 + 61));
                    handHouses[j].setLayoutY(20);
                    handHouses[j].setPrefSize(61, 90);
                    handContainer.getChildren().add(j, handHouses[j]);
                }
                new SlideInUp(handContainer).play();
            });

            handContainer.getChildren().add(i, handHouse);
        }
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

            Image image = null;
            try {
                 image = new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg"));
            } catch (FileNotFoundException e) {
                System.out.println("Ey baba! Aks chi shod pas :(");
            }

            ImageView imageView = new ImageView();
            imageView.setFitWidth(47);
            imageView.setFitHeight(70);
            monsterHouse.getChildren().add(imageView);
            monsterHouse.setCardImage(image);
            imageView.setImage(image);

            monsterHouse.setPrefSize(42, 70);
            handleOnMouseClicked(monsterHouse);
        }

        MagicHouse[] magicHouses = player.getBoard().getMagicHouse();
        for (int i = 0; i < magicHouses.length; i++) {
            MagicHouse magicHouse = magicHouses[i];
            magicHousesGridPane.add(magicHouse, i, 0, 1, 1);
            ImageView imageView = new ImageView();
            imageView.setFitWidth(47);
            imageView.setFitHeight(70);
            magicHouse.getChildren().add(imageView);
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
}
