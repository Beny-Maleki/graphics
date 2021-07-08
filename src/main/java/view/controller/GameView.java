package view.controller;

import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.User;
import model.userProp.UserInfoType;

public class GameView {

    public void initialize() {
        GameInProcess.setGame(new Game(new Player(User.getUserByUserInfo("Yaroo", UserInfoType.USERNAME), 1), new Player(User.getUserByUserInfo("ali", UserInfoType.USERNAME), 1), 3 ));

    }
}
