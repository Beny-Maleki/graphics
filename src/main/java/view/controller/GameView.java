package view.controller;

import animatefx.animation.FlipInX;
import animatefx.animation.FlipInY;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import model.gameprop.BoardProp.GameHouse;
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

    public void initialize() {
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


        initializeInfos(you, opponent);


        initializeHouses(playerYou, yourMonsterHousesGridPane, yourMagicHousesGridPane);
        initializeHouses(playerOpponent, opponentMagicHousesGridPane, opponentMonsterHousesGridPane);
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
            handleOnMouseEntered(monsterHouse);
        }

        MagicHouse[] magicHouses = player.getBoard().getMagicHouse();
        for (int i = 0; i < magicHouses.length; i++) {
            MagicHouse magicHouse = magicHouses[i];
            magicHousesGridPane.add(magicHouse, i, 0, 1, 1);

            Image image = null;
            try {
                image = new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg"));
            } catch (FileNotFoundException e) {
                System.out.println("Ey baba! Aks chi shod pas :(");
            }

            ImageView imageView = new ImageView();
            imageView.setFitWidth(47);
            imageView.setFitHeight(70);
            magicHouse.getChildren().add(imageView);
            magicHouse.setCardImage(image);
            imageView.setImage(image);
            magicHouse.setPrefSize(42, 70);

            handleOnMouseEntered(magicHouse);
        }
    }

    private void handleOnMouseEntered(GameHouse gameHouse) {
        gameHouse.setOnMouseEntered(e -> {
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
