package controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.AttackMonsterProcessor;
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

    public BattlePhase process(SelectedCardProp offensive, MonsterHouse target, Game game) {
        if (offensive == null)
            return BattlePhase.NO_CARD_SELECTED_YET;
        if (!offensive.getLocation().equals(CardLocation.MONSTER_ZONE)) {
            return BattlePhase.CANT_ATTACK_WRONG_lOC;
        }
        if (!offensive.getCardPlace().getState().equals(MonsterHouseVisibilityState.OO)){
            return BattlePhase.CANT_ATTACK_IN_DEFENCE;
        }
        MonsterHouse offensiveCardPlace = (MonsterHouse) offensive.getCardPlace();
        if (offensiveCardPlace.isMonsterAttacked()) {
            return BattlePhase.ALREADY_ATTACK;
        }

        if (target.getState().equals(MonsterHouseVisibilityState.E))
            return BattlePhase.EMPTY_LOC_TO_ATTACK;

        return super.process(offensive, target, game);

    }
}
