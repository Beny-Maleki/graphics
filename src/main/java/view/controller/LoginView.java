package view.controller;

import controller.menues.menuhandlers.menucontrollers.LoginPageController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;
import view.AudioPath;
import view.ClickButtonHandler;
import view.SoundEffectHandler;

import java.io.IOException;

public class LoginView {

    public TextField UserName;
    public TextField PassWord;
    public Button Login;
    public Button Back;
    public Label Message;
    private final LoginPageController controller;

    {
        controller = new LoginPageController();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Login) {
            controller.login(UserName.getText(), PassWord.getText(), Message);
        } else if (event.getSource() == Back) {
            controller.moveToPage(Back, Menu.WELCOME_MENU);
        }
    }

    public void soundEffect(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
    }

}
