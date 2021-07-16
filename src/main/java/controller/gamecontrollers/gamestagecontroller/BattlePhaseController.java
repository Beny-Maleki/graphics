package controller.gamecontrollers.gamestagecontroller;

import controller.gamecontrollers.GeneralController;
import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.AttackDirectChain;
import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.AttackMonsterChain;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class BattlePhaseController extends GeneralController {
    private static BattlePhaseController instance;

    public BattlePhaseController() {
    }

    public static BattlePhaseController getInstance() {
        if (instance == null) instance = new BattlePhaseController();
        return instance;
    }

    public String run(String command) throws FileNotFoundException {
        Game game = GameInProcess.getGame();
        String output = null;
        if (command.equals("attack direct")) {
            output = attackDirect(game);
        } else if (command.startsWith("attack")) {
            int address = Character.getNumericValue(command.charAt(7));
            output = attackMonsterHouse(game,
                    game.getPlayer(SideOfFeature.OPPONENT).getBoard().getMonsterHouse()[address - 1]);
        }
        return processAnswer(game, output);
    }


    public String attackMonsterHouse(Game game, MonsterHouse target) {
        AttackMonsterChain chain = new AttackMonsterChain();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(chain.request(target, game));
        game.setCardProp(null);
        return stringBuilder.toString();
    }

    public String attackDirect(Game game) {
        AttackDirectChain chain = new AttackDirectChain();
        return chain.request(game);
    }
}
