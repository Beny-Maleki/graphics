package view.controller;

import animatefx.animation.Tada;
import controller.menues.menuhandlers.menucontrollers.ProfileMenuController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;

import java.io.IOException;

public class ProfileChangeNicknameView {
    public Button change;
    public Button back;
    public TextField nickname;
    public Label result;
    ProfileMenuController controller;

    {
        controller = ProfileMenuController.getInstance();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            controller.moveToPage(back, Menu.PROFILE_MENU);
        } else if (event.getSource() == change) {
            String resultString = changeNickname();
            result.setText(resultString);
        }
    }

    private String changeNickname() {
        String nicknameNew = nickname.getText();
        return controller.changeNickname(nicknameNew);
    }

    public void hoverAnimation(MouseEvent event) {
        new Tada((Node) event.getSource()).play();
    }
}
