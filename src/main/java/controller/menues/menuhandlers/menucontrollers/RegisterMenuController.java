package controller.menues.menuhandlers.menucontrollers;


import com.google.gson.Gson;
import com.sanityinc.jargs.CmdLineParser;
import controller.MenuHandler;
import model.enums.Error;
import model.enums.Menu;
import model.enums.MenusMassages.Register;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;

import java.io.FileWriter;
import java.io.IOException;

public class RegisterMenuController {


    private static RegisterMenuController controller;

    private RegisterMenuController() {
    }

    public static RegisterMenuController getInstance() {
        if (controller == null) controller = new RegisterMenuController();
        return controller;
    }

    public String login(String password, String username) {
        User user = User.getUserByUserInfo(username, UserInfoType.USERNAME);
        if (null != user) {
            if (user.isPasswordMatch(password)) {
                if (LoginUser.getUser() == null) {
                    LoginUser.setUser(user);
                    return Register.SUCCESSFULLY_LOGIN.getRegisterMessage();
                } else {
                    return Error.INVALID_LOGIN.toString();
                }
            } else {
                return Error.INVALID_USER_OR_PASS.toString();
            }
        }
        return Error.INVALID_USER_OR_PASS.toString();
    }

    public String enterMenu(String command) {
        if (command.contains("Main menu")) {
            if (null == LoginUser.getUser()) {
                return Error.INVALID_ENTER_MENU.toString();
            } else {
                try {
                    MenuHandler.changeMenu(Menu.MAIN_MENU);
                    return "enter Main menu successfully";
                } catch (CmdLineParser.OptionException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String createUser(String username, String nickname, String password) {
        if (null != User.getUserByUserInfo(username, UserInfoType.USERNAME)) {
            return processOutPut(Error.INVALID_USERNAME.toString(), username);
        }
        if (null != User.getUserByUserInfo(nickname, UserInfoType.NICKNAME)) {
            return processOutPut(Error.INVALID_NICKNAME.toString(), nickname);
        }
        new User(username, nickname, password);
        return Register.SUCCESSFULLY_USER_CREATE.getRegisterMessage();
    }

    public String logout() {
        if (LoginUser.getUser() == null) {
            return Error.INVALID_LOGOUT.toString();
        } else {
            LoginUser.setUser(null);
            return Register.SUCCESSFULLY_LOGOUT.getRegisterMessage();
        }
    }

    public String run(String command) throws CmdLineParser.OptionException {
        CmdLineParser parser = new CmdLineParser();
        if (command.startsWith("user create")) {
            CmdLineParser.Option<String> username = parser.addStringOption('u', "username");
            CmdLineParser.Option<String> nickname = parser.addStringOption('n', "nickname");
            CmdLineParser.Option<String> password = parser.addStringOption('p', "password");

            String[] splitCommand = command.split(" ");
            parser.parse(splitCommand);
            return createUser(parser.getOptionValue(username),
                    parser.getOptionValue(nickname),
                    parser.getOptionValue(password));
        } else if (command.startsWith("user login")) {
            CmdLineParser.Option<String> username = parser.addStringOption('u', "username");
            CmdLineParser.Option<String> password = parser.addStringOption('p', "password");
            String[] splitCommand = command.split(" ");
            parser.parse(splitCommand);
            return login(parser.getOptionValue(password),
                    parser.getOptionValue(username));
        } else if (command.startsWith("menu show")) {
            return Register.CURRENT_MENU.getRegisterMessage();
        } else if (command.startsWith("user logout")) {
            return logout();
        } else if (command.startsWith("menu enter")) {
            return enterMenu(command);
        }
        return null;
    }

    private String processOutPut(String error, String name) {
        if (error.contains("U_N")) {
            error = error.replace("U_N", name);
        } else if (error.contains("N_N")) {
            error = error.replace("N_N", name);
        }
        return error;
    }


    public void saveData() throws IOException {
        FileWriter writer = new FileWriter("jsonResources//Decks.Json");
        writer.write(new Gson().toJson(Deck.getAllDecks()));
        writer.close();
        writer = new FileWriter("jsonResources//Users.Json");
        writer.write(new Gson().toJson(User.getAllUsers()));
        writer.close();
    }
}
