package controller.gamecontrollers.gamestagecontroller;

import com.sanityinc.jargs.CmdLineParser;
import controller.gamecontrollers.GeneralController;
import controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosChain;
import controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.FlipSummonChain;
import controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.HireMonsterChain;
import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.HireSpellChain;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.TypeOfHire;
import model.enums.GameEnums.WantedPos;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.enums.GameEnums.gamestage.GameSideStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class MainPhaseController extends GeneralController {
    private static MainPhaseController instance;

    private MainPhaseController() {
    }

    public static MainPhaseController getInstance() {
        if (instance == null) instance = new MainPhaseController();
        return instance;
    }

    public String run(String command) throws CmdLineParser.OptionException {
        Game game = GameInProcess.getGame();
        if (game.getGameSideStage().equals(GameSideStage.NONE)) {
            if (game.getGameMainStage().equals(GameMainStage.FIRST_MAIN_PHASE) ||
                    game.getGameMainStage().equals(GameMainStage.SECOND_MAIN_PHASE)) {
                if (command.equals("summon")) {
                    return hireCard(game, TypeOfHire.SUMMON);
                } else if (command.equals("set")) {
                    return hireCard(game, TypeOfHire.SET);
                } else if (command.startsWith("set --position")) {
                    MonsterHouse hiredMonsterHouse = game.getHiredMonster();
                    return changePosition(game.getCardProp(), command, hiredMonsterHouse);
                } else if (command.equals("flip-summon")) {
                    return flipSummon(game);
                }
            } else {
                return "you can’t do this action in this phase";
            }
        } else return "back to game first";
        return null;
    }

    private String hireCard(Game game, TypeOfHire type) {

        String answerToRequest;
        SelectedCardProp cardProp = game.getCardProp();
        if (cardProp.getCard() instanceof MonsterCard) {
            HireMonsterChain chain = new HireMonsterChain();
            if ((answerToRequest = chain.request(game, type)) != null) {
                return answerToRequest;
            }
        }else if (cardProp.getCard() instanceof MagicCard){
            if (type != TypeOfHire.SET) return MainPhase.CANT_SUMMON_MAGIC.toString();
            HireSpellChain chain = new HireSpellChain();
            if ((answerToRequest = chain.request(game))  != null){
                return answerToRequest;
            }
        }
        return null;
    }


    private String changePosition(SelectedCardProp cardProp, String command, MonsterHouse hiredMonsterHouse) {
        ChangePosChain chain = new ChangePosChain();
        if (command.contains("attack")) {
            return chain.request(cardProp, WantedPos.ATTACK, hiredMonsterHouse).toString();
        } else {
            return chain.request(cardProp, WantedPos.DEFENCE, hiredMonsterHouse).toString();
        }
    }

    private String flipSummon(Game game) {
        FlipSummonChain chain = new FlipSummonChain();
        return chain.request(game.getCardProp(), game).toString();
    }

}
