package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import model.Exceptions.EmptyTextFieldException;
import model.enums.Error;
import model.enums.MenusMassages.Profile;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;
import view.menudisplay.ProfileMenuDisplay;

public class ProfilePageController extends Controller {


    public static void showCurrentMenu() {
        ProfileMenuDisplay.display(Profile.CURRENT_MENU);
    }

    public static void invalidCommand() {
        ProfileMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public String changeNickname(String newNickname) {
        if (newNickname.equals("")) {
            return "You must fill all the fields first";
        }
        User user = User.getUserByUserInfo(newNickname, UserInfoType.NICKNAME);
        if (user != null) {
            return Error.INVALID_NICKNAME.toString();
        } else {
            user = LoginUser.getUser();
            user.setNickname(newNickname);
            return Profile.SUCCESSFULLY_CHANGE_NICKNAME.getMessage();
        }
    }

    public String changePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals("") || newPassword.equals("")) {
            return "You must fill all the fields first";
        }
        User user = LoginUser.getUser();
        if (!user.isPasswordMatch(currentPassword)) {
            return Error.INVALID_PASSWORD.toString();
        } else if (currentPassword.equals(newPassword)) {
            return Error.INVALID_NEW_PASSWORD.toString();
        } else {
            user.setPassword(newPassword);
            return Profile.SUCCESSFULLY_CHANGE_PASSWORD.getMessage();
        }
    }
}
