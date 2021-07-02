package viewer.menu;

import com.sanityinc.jargs.CmdLineParser;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.RegisterMenuController;
import model.enums.Error;
import viewer.Regex;

import java.io.IOException;

public class RegisterMenu {
    private static RegisterMenu registerMenu;

    private RegisterMenu() {
    }

    public static RegisterMenu getInstance() {
        if (registerMenu == null)
            registerMenu = new RegisterMenu();

        return registerMenu;
    }

    public void run() throws CmdLineParser.OptionException, IOException {
        RegisterMenuController controller = RegisterMenuController.getInstance();
        String command;
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("menu exit")) {
                controller.saveData();
                break;
            }
            if (!isCommandInValid(command)) {
                String outPut;
                if ((outPut = controller.run(command)) != null)
                    System.out.println(outPut);
            } else {
                System.out.println(Error.INVALID_COMMAND);
            }
        }
    }

    public boolean isCommandInValid(String command) {
        for (String[] patterns : Regex.registerCommands) {
            for (String pattern : patterns) {
                if (command.matches(pattern))
                    return false;
            }
        }
        return true;
    }

}
