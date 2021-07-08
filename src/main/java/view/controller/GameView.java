package view.controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public void initialize() {
        LoginUser.setUser(User.getUserByUserInfo("Yaroo", UserInfoType.USERNAME));

        Player firstPlayer = new Player(User.getUserByUserInfo("Yaroo", UserInfoType.USERNAME), 0);
        Player secondPlayer = new Player(User.getUserByUserInfo("ali", UserInfoType.USERNAME), 0);

        GameInProcess.setGame(new Game(firstPlayer, secondPlayer, 1 ));

        User you = LoginUser.getUser();
        User opponent;
        if (GameInProcess.getGame().getFirstPlayer().getUser().equals(you)) opponent = (User) GameInProcess.getGame().getSecondPlayer().getUser();
        else opponent = (User) GameInProcess.getGame().getFirstPlayer().getUser();

        try {
            yourAvatar.setImage(new Image(new FileInputStream(you.getAvatarAddress())));
            opponentAvatar.setImage(new Image(new FileInputStream(opponent.getAvatarAddress())));
        } catch (FileNotFoundException e) {
            System.out.println("Aks ha inja naboodan ke :(");
        }

        yourUsername.setText(you.getUsername());
        yourNickname.setText(you.getNickname());

        opponentUsername.setText(opponent.getUsername());
        opponentNickname.setText(opponent.getNickname());

    }
}
