package view.controller;

import controller.menues.menuhandlers.menucontrollers.RegisterMenuController;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;

import java.io.IOException;


public class RegisterView {
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
        } else if (event.getSource() == Login) {
            controller.moveToPage(Login, Menu.SHOP_MENU);
        }
    }
}
