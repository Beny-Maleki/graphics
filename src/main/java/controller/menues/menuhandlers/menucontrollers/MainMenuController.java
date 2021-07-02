package controller.menues.menuhandlers.menucontrollers;

import com.sanityinc.jargs.CmdLineParser;
import model.enums.Error;
import model.enums.MenusMassages.Main;
import model.enums.Menu;
import controller.MenuHandler;
import view.menudisplay.MainMenuDisplay;

import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenuController extends MenuHandler{
    public static void showCurrentMenu() {
        MainMenuDisplay.display(Main.CURRENT_MENU);
    }

    public static void invalidCommand() {
        MainMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void enterMenu(Matcher matcher) {
    }
}
