package view.controller;

import controller.menues.menuhandlers.menucontrollers.MainMenuController;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;

import java.io.IOException;

public class MainMenuView {

    public Button Duel;
    public Button DecksConstruction;
    public Button Shop;
    public Button Setting;
    public Button Logout;
    private MainMenuController controller;

    {
        controller = new MainMenuController() ;
    }
    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Shop) {
            controller.moveToPage(Shop , Menu.SHOP_MENU);
        }else if (event.getSource() == DecksConstruction){
            controller.moveToPage(DecksConstruction , Menu.DECKS_VIEW);
        }
    }
}
