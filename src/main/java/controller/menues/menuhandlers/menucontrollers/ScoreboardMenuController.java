package controller.menues.menuhandlers.menucontrollers;

import com.sun.javafx.collections.MappingChange;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.enums.Error;
import model.enums.MenusMassages.Scoreboard;
import model.userProp.User;
import view.menudisplay.ScoreboardMenuDisplay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardMenuController extends Controller {
    private static ScoreboardMenuController instance;

    private ScoreboardMenuController() {}

    public static ScoreboardMenuController getInstance() {
        if (instance == null) {
            instance = new ScoreboardMenuController();
        }
        return instance;
    }

    public static void invalidCommand() {
        ScoreboardMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void showCurrentMenu() {
        ScoreboardMenuDisplay.display(Scoreboard.CURRENT_MENU);
    }

    public void showScoreboard(TableView tableView) {
        ObservableList<Map<Integer, User>> scoreBoardUsers =
                FXCollections.observableArrayList();
        Map<Integer, User> userMap = new HashMap<>();
        ArrayList<User> sortedUsers = User.getAllUsers();
        sortedUsers.sort(Comparator.comparing(User::getScore).thenComparing(User::getNickname));
        int rank = 1, counter = 1;
        User tempUser = null;
        for (User user : sortedUsers) {
            if (scoreBoardUsers.size() == 10) break;
            if (tempUser != null) {
                if (user.getScore() > tempUser.getScore()) {
                    rank = counter;
                }
                userMap.put(rank, user);
            } else {
                userMap.put(1, user);
            }
            tempUser = user;
            counter++;
        }
        scoreBoardUsers.add(userMap);
        tableView.getItems().add(scoreBoardUsers);
    }
}
