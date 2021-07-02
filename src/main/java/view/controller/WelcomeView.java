package view.controller;

import animatefx.animation.Tada;
import controller.menues.menuhandlers.menucontrollers.RegisterMenuController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;

import java.io.IOException;


public class WelcomeView {
    public Button Register;
    public Button Login;
    public Button Exit;
    RegisterMenuController controller;

    {
        controller = RegisterMenuController.getInstance();
    }


    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Exit) {
            controller.exit();
        } else if (event.getSource() == Register) {
            controller.moveToPage(Login, Menu.REGISTER_MENU);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        new Tada((Node) event.getSource()).play();
    }
}
