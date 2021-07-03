package view.controller;

import animatefx.animation.Tada;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;
import model.enums.MenusMassages.Profile;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.ProfileMenuController;
import view.Regex;
import view.menudisplay.ProfileMenuDisplay;

import java.io.IOException;
import java.util.regex.Matcher;

public class ProfileView {
    public Button ChangeNickname;
    public Button ChangePassword;
    public Button Exit;
    ProfileMenuController controller;
    private static ProfileView profileView;

    {
        controller = ProfileMenuController.getInstance();
    }

    public static ProfileView getInstance() {
        if (profileView == null) {
            profileView = new ProfileView();
        }
        return profileView;
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Exit) {
            controller.moveToPage(Exit, Menu.MAIN_MENU);
        } else if (event.getSource() == ChangePassword) {
            controller.moveToPage(ChangePassword, Menu.PROFILE_CHANGE_PASSWORD);
        } else if (event.getSource() == ChangeNickname) {
            controller.moveToPage(ChangeNickname, Menu.PROFILE_CHANGE_NICKNAME);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        new Tada((Node) event.getSource()).play();
    }
}
