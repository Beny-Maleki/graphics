package view.controller;

import controller.menues.menuhandlers.menucontrollers.ProfileMenuController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.enums.Error;
import model.enums.Menu;

import java.io.IOException;

public class ProfileChangePasswordView {
    public Label currentPassword;
    public Label newPassword;
    public Label repeatPassword;
    public Button change;
    public Label result;
    public Button back;
    ProfileMenuController controller;

    {
        controller = ProfileMenuController.getInstance();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == change) {
            String resultString = changePassword();
            result.setText(resultString);
        } else if (event.getSource() == back) {
            controller.moveToPage(back, Menu.PROFILE_MENU);
        }
    }

    private String changePassword() {
        String currentPass = currentPassword.getText(), newPass = newPassword.getText(), repeatPass = repeatPassword.getText();
        return controller.changePassword(currentPass, newPass, repeatPass);
    }
}
