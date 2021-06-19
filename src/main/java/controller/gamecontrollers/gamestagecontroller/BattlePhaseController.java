package controller.gamecontrollers.gamestagecontroller;

import controller.gamecontrollers.GeneralController;
import controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.AttackDirectChain;
import controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.AttackMonsterChain;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.gamemodel.Game;

public class BattlePhaseController extends GeneralController {
    private static BattlePhaseController instance;

    private BattlePhaseController() {
    }

    public static BattlePhaseController getInstance() {
        if (instance == null) instance = new BattlePhaseController();
        return instance;
    }

    public String run(String command) {
        Game game = GameInProcess.getGame();
        if (command.equals("attack direct")) {
            return attackDirect(game);
        } else if (command.startsWith("attack")) {
            int address = Character.getNumericValue(command.charAt(7));
            return attackMonsterHouse(game,
                    game.getPlayer(SideOfFeature.OPPONENT).getBoard().getMonsterHouse()[address - 1]);
        }
        return null;
    }


    private String attackMonsterHouse(Game game, MonsterHouse target) {
        AttackMonsterChain chain = new AttackMonsterChain();
        StringBuilder stringBuilder = new StringBuilder();
        if (target.getState().equals(MonsterHouseVisibilityState.DH)) {
            stringBuilder.append("the hidden defence revealed : ").append(target.getMonsterCard().getName()).append("\n");
        }
        stringBuilder.append(chain.request(game.getCardProp(), target, game));
        game.setCardProp(null);
        return stringBuilder.toString();
    }

    private String attackDirect(Game game) {
        AttackDirectChain chain = new AttackDirectChain();
        return chain.request(game.getCardProp(), game.getPlayer(SideOfFeature.OPPONENT), game.isFirstTurnOfTheGame(), game.getGameMainStage());
    }
}
