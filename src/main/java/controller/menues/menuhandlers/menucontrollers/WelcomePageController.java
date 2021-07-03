package controller.menues.menuhandlers.menucontrollers;


import com.google.gson.Gson;
import controller.Controller;
import javafx.scene.control.Label;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.FileWriter;
import java.io.IOException;

public class WelcomePageController extends Controller {


    private static WelcomePageController controller;

    private WelcomePageController() {
    }

    public static WelcomePageController getInstance() {
        if (controller == null) controller = new WelcomePageController();
        return controller;
    }

    public void logout(Label message) {
        if (LoginUser.getUser() == null) {
            message.setText("No User Login yet !!!");
            message.setStyle("-fx-text-fill: red ; -fx-font-size: 20");
            displayMessage(message);
        } else {
            LoginUser.setUser(null);
            message.setText("User Logout successfully !!!");
            message.setStyle("-fx-text-fill: green ; -fx-font-size: 20");
            displayMessage(message);
        }
    }

    public void saveData() throws IOException {
        FileWriter writer = new FileWriter("jsonResources//Decks.Json");
        writer.write(new Gson().toJson(Deck.getAllDecks()));
        writer.close();
        writer = new FileWriter("jsonResources//Users.Json");
        writer.write(new Gson().toJson(User.getAllUsers()));
        writer.close();
    }

    public void exit() throws IOException {
        saveData();
        System.exit(0);
    }
}
