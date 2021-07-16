package controller.menues.menuhandlers.menucontrollers;

import com.sanityinc.jargs.CmdLineParser;
import controller.Controller;
import controller.menues.menuhandlers.duelmenuhandler.DuelChain;
import model.enums.GameEnums.PlayerTurn;
import model.enums.MenusMassages.Duel;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.LoginUser;
import model.userProp.User;
import model.userProp.UserInfoType;
import view.RockPaperScissorGame;

import java.io.FileNotFoundException;
import java.util.Objects;

public class DuelPageController extends Controller {

    public String run(String command) throws CmdLineParser.OptionException, FileNotFoundException {
        if (command.startsWith("controller show")) {
            return showCurrentMenu();
        } else if (command.startsWith("duel --new")) {
            CmdLineParser parser = new CmdLineParser();
            CmdLineParser.Option<Boolean> newOpt = parser.addBooleanOption("new");
            CmdLineParser.Option<String> secondPlayerName = parser.addStringOption('s', "second-player");
            CmdLineParser.Option<String> roundNumber = parser.addStringOption('r', "rounds");
            String[] splitCommand = command.split(" ");

            parser.parse(splitCommand);
            if (parser.getOptionValue(newOpt)) {
                return makeNewDuel(parser.getOptionValue(roundNumber),
                        parser.getOptionValue(secondPlayerName));
            }
        }
        return null;
    }

    private String showCurrentMenu() {
        return Duel.CURRENT_MENU.toString();
    }

    private String makeNewDuel(String rounds, String secondPlayer) throws CmdLineParser.OptionException, FileNotFoundException {
        DuelChain chain = new DuelChain();
        String[] data = new String[]{LoginUser.getUser().getUsername(), secondPlayer, rounds};
        Duel error;
        if ((error = chain.request(data)) != null) {
            return processAnswer(error, data);
        }
        Player loggedInPlayer = new Player(LoginUser.getUser(), 0);
        ;
        Player opponentPlayer = new Player(User.getUserByUserInfo(secondPlayer, UserInfoType.USERNAME), 0);
        Game game = null;
        PlayerTurn firstPlayer = RockPaperScissorGame.run(LoginUser.getUser().getNickname(),
                Objects.requireNonNull(User.getUserByUserInfo(secondPlayer, UserInfoType.USERNAME)).getNickname());
        switch (firstPlayer) {
            case PLAYER_ONE: {
                game = new Game(loggedInPlayer, opponentPlayer, Integer.parseInt(rounds));
                break;
            }
            case PLAYER_TWO: {
                game = new Game(opponentPlayer, loggedInPlayer, Integer.parseInt(rounds));
            }
        }
        GameInProcess.setGame(game);
             return null;
    }

    public User findOpponent(String name) {
        for (User user : User.getAllUsers()) {
            if (user.getNickname().equals(name)) {
                return user;
            }
        }
        return null;
    }

    private String processAnswer(Duel duel, String[] data) {
        String error = duel.toString();
        if (error.contains("U1")) {
            error = error.replace("U1", data[0]);
        } else if (error.contains("U2")) {
            error = error.replace("U2", data[1]);
        }
        return error;
    }
}
