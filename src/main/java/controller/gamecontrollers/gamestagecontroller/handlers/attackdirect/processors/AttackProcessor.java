package controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.AttackDirectProcessor;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;

public class AttackProcessor extends AttackDirectProcessor {
    public AttackProcessor(AttackDirectProcessor processor) {
        super(processor);
    }

    public String process(SelectedCardProp cardProp, Player target, boolean firstTurnOfTheGame, GameMainStage stage) {
        if (firstTurnOfTheGame) {
            return BattlePhase.CANT_ATTACK_ON_FIRST_TURN.toString();
        }
        if (target.getBoard().numberOfFullHouse("monster") != 0) {
            return BattlePhase.CANT_ATTACK_DIRECT.toString();
        }
        MonsterCard monsterCard = (MonsterCard) cardProp.getCard();
        target.changePlayerLifePoint(monsterCard.getAttack());
        MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();

        monsterHouse.setMonsterAttacked();
        return "you opponent receives " + monsterCard.getAttack() + " battle damage";
    }
}
