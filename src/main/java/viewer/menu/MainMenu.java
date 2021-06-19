package viewer.menu;

import com.sanityinc.jargs.CmdLineParser;
import model.enums.MenusMassages.Main;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.MainMenuController;
import model.userProp.LoginUser;
import viewer.Regex;
import viewer.menudisplay.MainMenuDisplay;

import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenu {
    private static MainMenu mainMenu;

    public static MainMenu getInstance() {
        if (mainMenu == null) {
            mainMenu = new MainMenu();
        }
        return mainMenu;
    }

    private static void recognizeCommand(String command) throws CmdLineParser.OptionException, IOException {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(command, Regex.menuEnter)).matches()) {
            MainMenuController.enterMenu(matcher);
        } else if (command.equals("menu show-current")) {
            MainMenuController.showCurrentMenu();
        } else {
            MainMenuController.invalidCommand();
        }
    }

    public void run() throws CmdLineParser.OptionException, IOException {
        String command;
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("menu exit")) {
                MainMenuDisplay.display(Main.SUCCESSFULLY_EXIT_MENU);
                break;
            } else if (command.equals("user logout")) {
                LoginUser.setUser(null);
                MainMenuDisplay.display(Main.SUCCESSFULLY_LOGOUT);
                break;
            }
            recognizeCommand(command);
        }

    }
}
