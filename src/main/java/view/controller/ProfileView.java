package view.controller;

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

    public static ProfileView getInstance() {
        if (profileView == null) {
            profileView = new ProfileView();
        }
        return profileView;
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
        } else if (command.equals("controller show-current")) {
            ProfileMenuController.showCurrentMenu();
        } else {
            ProfileMenuController.invalidCommand();
        }
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Exit) {
            controller.moveToPage(Exit, Menu.MAIN_MENU);
        } else if (event.getSource() == ChangeNickname) {
            controller.moveToPage(ChangePassword, Menu.PROFILE_CHANGE_PASSWORD);
        } else if (event.getSource() == ChangePassword) {
            controller.moveToPage(ChangeNickname, Menu.PROFILE_CHANGE_NICKNAME);
        }
//        String command;
//        while (true) {
//            command = ImportScanner.getInput();
//            if (command.equals("controller exit")) {
//                break;
//            }
//            recognizeCommand(command);
//        }
//        ProfileMenuDisplay.display(Profile.SUCCESSFULLY_EXIT_MENU);
    }
}
