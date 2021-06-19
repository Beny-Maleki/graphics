package viewer.menu;

import model.enums.MenusMassages.Profile;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.ProfileMenuController;
import viewer.Regex;
import viewer.menudisplay.ProfileMenuDisplay;

import java.util.regex.Matcher;

public class ProfileMenu {
    private static ProfileMenu profileMenu;

    public static ProfileMenu getInstance() {
        if (profileMenu == null) {
            profileMenu = new ProfileMenu();
        }
        return profileMenu;
    }

    private static void recognizeCommand(String command) {
        Matcher matcher;
        if (Regex.getMatcher(command, Regex.profileChange).matches()) {
            Matcher matcher1, matcher2;
            if ((matcher = Regex.getMatcher(command, Regex.changeNickname)).matches()) {
                String newNickname = matcher.group(1);
                ProfileMenuController.changeNickname(newNickname);
            } else if (Regex.getMatcher(command, Regex.changePassword).matches()
                    && (matcher1 = Regex.getMatcher(command, Regex.currentPassword)).matches() &&
                    (matcher2 = Regex.getMatcher(command, Regex.newPassword)).matches()) {
                String currentPassword = matcher1.group(1);
                String newPassword = matcher2.group(1);
                ProfileMenuController.changePassword(currentPassword, newPassword);
            } else {
                ProfileMenuController.invalidCommand();
            }
        } else if (command.equals("menu show-current")) {
            ProfileMenuController.showCurrentMenu();
        } else {
            ProfileMenuController.invalidCommand();
        }
    }

    public void run() {
        String command;
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("menu exit")) {
                break;
            }
            recognizeCommand(command);
        }
        ProfileMenuDisplay.display(Profile.SUCCESSFULLY_EXIT_MENU);
    }
}
