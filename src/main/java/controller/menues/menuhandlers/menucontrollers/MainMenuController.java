package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import javafx.scene.control.Label;
import model.enums.Error;
import model.enums.MenusMassages.Main;
import model.userProp.LoginUser;
import view.menudisplay.MainMenuDisplay;

import java.util.regex.Matcher;

public class MainMenuController extends Controller {
    public static void showCurrentMenu() {
        MainMenuDisplay.display(Main.CURRENT_MENU);
    }

    public static void invalidCommand() {
        MainMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void enterMenu(Matcher matcher) {
    }

    public void logout() {
        LoginUser.setUser(null);
    }

}
