package viewer.menu;

import model.enums.MenusMassages.Scoreboard;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.ScoreboardMenuController;
import viewer.menudisplay.ScoreboardMenuDisplay;

public class ScoreboardMenu {
    private static ScoreboardMenu scoreboardMenu;

    public void run() {
        String command;
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("menu exit")) {
                break;
            }
            recognizeCommand(command);
        }
        ScoreboardMenuDisplay.display(Scoreboard.SUCCESSFULLY_EXIT_MENU);
    }

    public static ScoreboardMenu getInstance() {
        if (scoreboardMenu == null) {
            scoreboardMenu = new ScoreboardMenu();
        }
        return scoreboardMenu;
    }

    private static void recognizeCommand(String command) {
        if (command.equals("menu show-current")) {
            ScoreboardMenuController.showCurrentMenu();
        } else if (command.equals("scoreboard show")) {
            ScoreboardMenuController.showScoreboard();
        } else {
            ScoreboardMenuController.invalidCommand();
        }
    }
}
