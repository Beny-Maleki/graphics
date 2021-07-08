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

public class ProfileChangePasswordView {
    public TextField currentPassword;
    public TextField newPassword;
    public TextField repeatPassword;
    public Button Change;
    public Label result;
    public Button Back;
    ProfileMenuController controller;

    {
        controller = ProfileMenuController.getInstance();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Change) {
            String resultString = changePassword();
            result.setText(resultString);
        } else if (event.getSource() == Back) {
            controller.moveToPage(Back, Menu.PROFILE_MENU);
        }
    }

    private String changePassword() {
        String currentPass = currentPassword.getText(), newPass = newPassword.getText(), repeatPass = repeatPassword.getText();
        return controller.changePassword(currentPass, newPass, repeatPass);
    }

    public void hoverAnimation(MouseEvent event) {
        new Tada((Node) event.getSource()).play();
    }
}
