package view.controller;

import animatefx.animation.Tada;
import controller.menues.menuhandlers.menucontrollers.LoginPageController;
import controller.menues.menuhandlers.menucontrollers.WelcomePageController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;

import java.io.IOException;


public class WelcomeView {
    public Button Register;
    public Button Login;
    public Button Exit;
    public Button Logout;
    public Label Message;

    //TODO: delete this after Main Menu got connected!
    public Button Shop;

    WelcomePageController controller;

    {
        controller = WelcomePageController.getInstance();
    }


    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Exit) {
            controller.exit();
        } else if (event.getSource() == Register) {
            controller.moveToPage(Register, Menu.REGISTER_MENU);
        } else if (event.getSource() == Login) {
            controller.moveToPage(Login, Menu.LOGIN_MENU);
        } else if (event.getSource() == Logout) {
            controller.logout(Message);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        new Tada((Node) event.getSource()).play();
    }

}
