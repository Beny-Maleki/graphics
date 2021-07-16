package view.controller;

import animatefx.animation.Tada;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.enums.Menu;
import controller.menues.menuhandlers.menucontrollers.ScoreboardMenuController;
import model.userProp.ScoreboardItem;
import view.AudioPath;
import view.ClickButtonHandler;
import view.SoundEffectHandler;

import java.io.IOException;

public class ScoreboardView {
    private static ScoreboardView scoreboardView;
    public TableView scoreboard;
    public Button Back;
    public ScoreboardMenuController controller;
    public VBox scoreboardContainer;

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
        TableColumn<ScoreboardItem, String> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rankColumn.setId("rank-column");
        rankColumn.setPrefWidth(100);
        rankColumn.setResizable(false);

        TableColumn<ScoreboardItem, String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setId("score-column");
        scoreColumn.setPrefWidth(160);
        scoreColumn.setResizable(false);

        TableColumn<ScoreboardItem, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setId("username-column");
        usernameColumn.setPrefWidth(230);
        usernameColumn.setResizable(false);

        controller.setScoreboardItems();
        TableView<ScoreboardItem> tableView = new TableView<>();
        tableView.setId("scoreboard-table");
        tableView.getColumns().addAll(rankColumn, usernameColumn, scoreColumn);
        tableView.setItems(ScoreboardItem.getScoreboardItems());
        tableView.setEditable(true);

        scoreboardContainer.getChildren().add(tableView);
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Back) {
            controller.moveToPage(Back, Menu.WELCOME_MENU);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Tada((Node) event.getSource()).play();
    }
}
