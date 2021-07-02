package controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.AttackMonsterProcessor;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class SelectedCardProcessor extends AttackMonsterProcessor {
    public SelectedCardProcessor(AttackMonsterProcessor processor) {
        super(processor);
    }

    public String process(MonsterHouse target, Game game) {
        String output = null;
        SelectedCardProp offensive = game.getCardProp();
        if (offensive == null)
            output = BattlePhase.NO_CARD_SELECTED_YET.toString();
        output = checkSelectedCardCondition(output, offensive);

        if (target.getState().equals(MonsterHouseVisibilityState.E))
            output = BattlePhase.EMPTY_LOC_TO_ATTACK.toString();
        if (output != null) return output;
        return super.process(target, game);

    }

    public static String checkSelectedCardCondition(String output, SelectedCardProp offensive) {

        if (offensive == null)
           return BattlePhase.NO_CARD_SELECTED_YET.toString();

        if (!offensive.getLocation().equals(CardLocation.MONSTER_ZONE)) {
           return BattlePhase.CANT_ATTACK_WRONG_lOC.toString();
        }
        if (!offensive.getCardPlace().getState().equals(MonsterHouseVisibilityState.OO)) {
           return BattlePhase.CANT_ATTACK_IN_DEFENCE.toString();
        }
        MonsterHouse offensiveCardPlace = (MonsterHouse) offensive.getCardPlace();
        if (offensiveCardPlace.isMonsterAttacked()) {
         return BattlePhase.ALREADY_ATTACK.toString();
        }
        return output;
    }
}
