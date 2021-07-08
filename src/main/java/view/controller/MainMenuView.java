package view.controller;

import controller.menues.menuhandlers.menucontrollers.MainMenuController;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;

import java.io.IOException;

public class MainMenuView {

    public Button duel;
    public Button decksConstruction;
    public Button shop;
    public Button setting;
    public Button logout;
    public Button profile;
    private final MainMenuController controller;

    {
        controller = new MainMenuController();
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == shop) {
            controller.moveToPage(shop, Menu.SHOP_MENU);
        } else if (event.getSource() == decksConstruction) {
            controller.moveToPage(decksConstruction, Menu.DECKS_VIEW);
        } else if (event.getSource() == logout) {
            controller.logout();
            controller.moveToPage(logout, Menu.WELCOME_MENU);
        } else if (event.getSource() == profile) {
            controller.moveToPage(profile, Menu.PROFILE_MENU);
        }
    }
}
