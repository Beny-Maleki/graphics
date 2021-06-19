package controller.menues.menuhandlers.menucontrollers;

import model.enums.Error;
import model.enums.MenusMassages.Scoreboard;
import model.userProp.User;
import viewer.menudisplay.ScoreboardMenuDisplay;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardMenuController {
    public static void invalidCommand() {
        ScoreboardMenuDisplay.display(Error.INVALID_COMMAND);
    }

    public static void showCurrentMenu() {
        ScoreboardMenuDisplay.display(Scoreboard.CURRENT_MENU);
    }

    public static void showScoreboard() {
        ArrayList<User> sortedUsers = User.getAllUsers();
        sortedUsers.sort(Comparator.comparing(User::getScore).thenComparing(User::getNickname));
        int rank = 1, counter = 1;
        User tempUser = null;
        for (User user : sortedUsers) {
            if (tempUser != null) {
                if (user.getScore() > tempUser.getScore()) {
                    rank = counter;
                }
                ScoreboardMenuDisplay.toString(user.getNickname(), user.getScore(), rank);
            } else {
                ScoreboardMenuDisplay.toString(user.getNickname(), user.getScore(), 1);
            }
            tempUser = user;
            counter++;
        }
    }
}
