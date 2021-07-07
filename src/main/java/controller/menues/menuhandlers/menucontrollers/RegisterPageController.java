package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import javafx.scene.control.Label;
import model.Exceptions.EmptyTextFieldException;
import model.enums.Error;
import model.enums.Menu;
import model.userProp.User;
import model.userProp.UserInfoType;

import java.io.IOException;

public class RegisterPageController extends Controller {


    public void createUser(String username, String nickname, String password, Label message) {
        try {
            if (username.equals("") || password.equals("") || nickname.equals("")) throw new EmptyTextFieldException();

            else if (null != User.getUserByUserInfo(username, UserInfoType.USERNAME)) {
                message.setText(processOutPut(Error.INVALID_USERNAME.toString(), username));
            } else if (null != User.getUserByUserInfo(nickname, UserInfoType.NICKNAME)) {
                message.setText(processOutPut(Error.INVALID_NICKNAME.toString(), nickname));
            } else {
                new User(username, password, nickname);
                moveToPage(message , Menu.LOGIN_MENU);
                return;
            }

        } catch (EmptyTextFieldException | IOException e) {
            message.setText(e.getMessage());
        }
        message.setStyle("-fx-text-fill: red ; -fx-font-size: 15");
        displayMessage(message);
    }


    private String processOutPut(String error, String name) {
        if (error.contains("U_N")) {
            error = error.replace("U_N", name);
        } else if (error.contains("N_N")) {
            error = error.replace("N_N", name);
        }
        return error;
    }
}
