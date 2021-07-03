package controller.menues.menuhandlers.menucontrollers;

import model.enums.Error;
import model.enums.MenusMassages.Main;
import controller.Controller;
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
}
