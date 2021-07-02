package view.controller;

import com.sanityinc.jargs.CmdLineParser;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.DuelMenuController;
import model.enums.Error;
import model.enums.MenusMassages.Duel;
import view.Regex;

public class DuelMenu {
    private static DuelMenu duelMenu;

    public static DuelMenu getInstance() {
        if (duelMenu == null) {
            duelMenu = new DuelMenu();
        }
        return duelMenu;
    }


    public void run() throws CmdLineParser.OptionException {
        System.out.println(Duel.SUCCESSFULLY_ENTER_MENU);
        String command;
        DuelMenuController controller = DuelMenuController.getController();
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("controller exit")) {
                System.out.println("controller exit successfully");
                break;
            }
            if (!invalidCommand(command)) {
                String outPut;
                if ((outPut = controller.run(command)) != null) {
                    System.out.println(outPut);
                } else {
                    break;
                }
            } else
                System.out.println(Error.INVALID_COMMAND);
        }
    }

    private boolean invalidCommand(String command) {
        for (String pattern : Regex.duelMenuCommands) {
            if (command.matches(pattern)) {
                return false;
            }
        }
        return true;
    }
}
