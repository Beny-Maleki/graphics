package view.controller;

import controller.menues.menuhandlers.menucontrollers.RegisterPageController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;

import java.io.IOException;

public class RegisterView {
    public TextField Username;
    public TextField Password;
    public TextField Nickname;
    public Button Register;
    public Button Back;
    public Label Message;

    private final RegisterPageController controller;

    {
        controller = new RegisterPageController();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Register) {
            controller.createUser(Username.getText(), Password.getText(), Nickname.getText(), Message);
        } else if (event.getSource() == Back) {
            controller.moveToPage(Back, Menu.WELCOME_MENU);
        }
    }

}
