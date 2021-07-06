package view.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.enums.Menu;
import model.enums.MenusMassages.Scoreboard;
import controller.ImportScanner;
import controller.menues.menuhandlers.menucontrollers.ScoreboardMenuController;
import view.menudisplay.ScoreboardMenuDisplay;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class ScoreboardView {
    private static ScoreboardView scoreboardView;
    public TableView scoreboard;
    public Button Back;
    public ScoreboardMenuController controller;

    {
        controller = ScoreboardMenuController.getInstance();
    }

    public static ScoreboardView getInstance() {
        if (scoreboardView == null) {
            scoreboardView = new ScoreboardView();
        }
        return scoreboardView;
    }

    public void setDetails() {
        controller.showScoreboard(scoreboard);
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Back) {
            controller.moveToPage(Back, Menu.MAIN_MENU);
        }
    }
}
