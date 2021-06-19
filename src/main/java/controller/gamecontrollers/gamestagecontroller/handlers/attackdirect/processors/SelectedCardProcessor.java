package controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.AttackDirectProcessor;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;

public class SelectedCardProcessor extends AttackDirectProcessor {
    public SelectedCardProcessor(AttackDirectProcessor processor) {
        super(processor);
    }

    public String process(SelectedCardProp cardProp, Player target, boolean firstTurnOfTheGame, GameMainStage stage) {
        if (cardProp == null) return BattlePhase.NO_CARD_SELECTED_YET.toString();

        if (!cardProp.getLocation().equals(CardLocation.MONSTER_ZONE)) {
            return BattlePhase.CANT_ATTACK_WRONG_lOC.toString();
        }
        if (!cardProp.getCardPlace().getState().equals(MonsterHouseVisibilityState.OO)) {
            return BattlePhase.CANT_ATTACK_IN_DEFENCE.toString();
        }
        MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
        if (monsterHouse.isMonsterAttacked()) return BattlePhase.ALREADY_ATTACK.toString();

        return super.process(cardProp, target, firstTurnOfTheGame , stage);
    }
}
