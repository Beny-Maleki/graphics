package view.controller;

import animatefx.animation.FlipInX;
import animatefx.animation.Shake;
import controller.menues.menuhandlers.menucontrollers.ProfileMenuController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.enums.Menu;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.IOException;

public class ProfileView {
    private static ProfileView profileView;
    public Button ChangeNickname;
    public Button ChangePassword;
    public Button Exit;
    public Pane userInfo;
    public Label userName;
    public Label money;
    public Label score;
    public Label nickname;
    ProfileMenuController controller;
    private User user;

    {
        user = LoginUser.getUser();
        controller = ProfileMenuController.getInstance();
    }

    public static ProfileView getInstance() {
        if (profileView == null) {
            profileView = new ProfileView();
        }
        return profileView;
    }

    @FXML
    public void initialize() {
        userName.setText("Username : " + user.getUsername());
        nickname.setText("Nickname : " + user.getNickname());
        score.setText("Total Scores : " + user.getScore());
        money.setText("Balance : " + user.getBalance());
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Exit) {
            controller.moveToPage(Exit, Menu.MAIN_MENU);
        } else if (event.getSource() == ChangePassword) {
            controller.moveToPage(ChangePassword, Menu.PROFILE_CHANGE_PASSWORD);
        } else if (event.getSource() == ChangeNickname) {
            FlipInX flipInX = new FlipInX(nickname);
            
        }
    }

    public void hoverAnimation(MouseEvent event) {
        new Shake((Node) event.getSource()).play();
    }
}
