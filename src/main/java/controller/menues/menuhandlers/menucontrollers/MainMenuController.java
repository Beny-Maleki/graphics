package controller.menues.menuhandlers.menucontrollers;

import com.sanityinc.jargs.CmdLineParser;
import model.enums.Error;
import model.enums.MenusMassages.Main;
import model.enums.Menu;
import controller.MenuHandler;
import viewer.menudisplay.MainMenuDisplay;

import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenuController {
    public static void showCurrentMenu() {
        MainMenuDisplay.display(Main.CURRENT_MENU);
    }

    public static void invalidCommand() {
        MainMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void enterMenu(Matcher matcher) throws CmdLineParser.OptionException, IOException {
        String menuName = matcher.group(1);
        if (menuName.equals("Duel menu")) {
            MenuHandler.changeMenu(Menu.START_DUEL);
        } else if (menuName.equals("Deck menu")) {
            MenuHandler.changeMenu(Menu.DECK_MENU);
        } else if (menuName.equals("Scoreboard menu")) {
            MenuHandler.changeMenu(Menu.SCORE_BOARD_MENU);
        } else if (menuName.equals("Profile menu")) {
            MenuHandler.changeMenu(Menu.USER_PROFILE_MENU);
        } else if (menuName.equals("Shop menu")) {
            MenuHandler.changeMenu(Menu.SHOP_MENU);
        } else if (MenuHandler.isMenuExist(menuName)) {
            MainMenuDisplay.display(Error.INVALID_NAVIGATION);
        } else {
            invalidCommand();
        }
    }
}
