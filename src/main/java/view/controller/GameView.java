package view.controller;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    public void initialize() {
        LoginUser.setUser(User.getUserByUserInfo("Yaroo", UserInfoType.USERNAME));

        User you = LoginUser.getUser();

        //TODO: Replace this  instantiation with a more general one!
        User opponent =User.getUserByUserInfo("ali", UserInfoType.USERNAME);
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
            monsterHousesGridPane.add(monsterHouses[i], i, 0, 1, 1);
            monsterHouses[i].setStyle("-fx-background-color: red");
            monsterHouses[i].setPrefSize(42, 70);
        }

        MagicHouse[] magicHouses = player.getBoard().getMagicHouse();
        for (int i = 0; i < magicHouses.length; i++) {
            magicHousesGridPane.add(magicHouses[i], i, 0, 1, 1);
            magicHouses[i].setStyle("-fx-background-color: yellow");
            magicHouses[i].setPrefSize(42, 70);
        }
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
