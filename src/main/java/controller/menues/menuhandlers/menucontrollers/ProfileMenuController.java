package controller.menues.menuhandlers.menucontrollers;

import model.enums.Error;
import model.enums.MenusMassages.Profile;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;
import viewer.menudisplay.ProfileMenuDisplay;

public class ProfileMenuController {
    public static void showCurrentMenu() {
        ProfileMenuDisplay.display(Profile.CURRENT_MENU);
    }

    public static void invalidCommand() {
        ProfileMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void changeNickname(String newNickname) {
        User user = User.getUserByUserInfo(newNickname, UserInfoType.NICKNAME);
        if (user != null) {
            ProfileMenuDisplay.display(Error.INVALID_NICKNAME, newNickname);
        } else {
            user = LoginUser.getUser();
            user.setNickname(newNickname);
            ProfileMenuDisplay.display(Profile.SUCCESSFULLY_CHANGE_NICKNAME);
        }
    }

    public static void changePassword(String currentPassword, String newPassword) {
        User user = LoginUser.getUser();
        if (!user.isPasswordMatch(currentPassword)) {
            ProfileMenuDisplay.display(Error.INVALID_PASSWORD);
        } else if (currentPassword.equals(newPassword)) {
            ProfileMenuDisplay.display(Error.INVALID_NEW_PASSWORD);
        } else {
            user.setPassword(newPassword);
            ProfileMenuDisplay.display(Profile.SUCCESSFULLY_CHANGE_PASSWORD);
        }
    }
}
