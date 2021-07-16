package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.userProp.ScoreboardItem;
import model.userProp.User;

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

    public void setScoreboardItems() {
        ArrayList<User> sortedUsers = User.getAllUsers();
        sortedUsers.sort(Comparator.comparing(User::getScore).thenComparing(User::getNickname).reversed());
        int rank = 1, counter = 1;
        User tempUser = null;
        for (User user : sortedUsers) {
            if (counter == 11) break;
            if (tempUser != null) {
                if (user.getScore() < tempUser.getScore()) {
                    rank = counter;
                }
                new ScoreboardItem(user.getUsername(), rank, user.getScore());
            } else {
                new ScoreboardItem(user.getUsername(), 1, user.getScore());
            }
            tempUser = user;
            counter++;
        }
    }
}
