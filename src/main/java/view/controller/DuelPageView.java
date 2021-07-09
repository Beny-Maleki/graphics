package view.controller;

import controller.menues.menuhandlers.menucontrollers.DuelMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.enums.Menu;

import java.io.IOException;

public class DuelPageView {

    public Button twoPlayerGame;
    public Button onePlayerGame;
    public Button back;
    public ChoiceBox roundNumber;
    public Pane gameSetUpPopUp;
    private DuelMenuController controller;

    {
        controller = new DuelMenuController();
    }

    @FXML
    public void initialize() {
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            controller.moveToPage(back, Menu.MAIN_MENU);
        } else if (event.getSource() == twoPlayerGame) {
            controller.moveToPage(twoPlayerGame, Menu.ROCK_PAPER_SCISSOR_PAGE);
        }
    }
}
