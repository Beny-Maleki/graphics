package controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.AttackDirectProcessor;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class AttackProcessor extends AttackDirectProcessor {
    public AttackProcessor(AttackDirectProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        Player target = game.getPlayer(SideOfFeature.OPPONENT);
        boolean isFirstTurnOfTheGame = game.isFirstTurnOfTheGame();
        if (isFirstTurnOfTheGame) {
            return BattlePhase.CANT_ATTACK_ON_FIRST_TURN.toString();
        }
        if (target.getBoard().numberOfFullHouse("monster") != 0) {
            return BattlePhase.CANT_ATTACK_DIRECT.toString();
        }
        MonsterCard monsterCard = (MonsterCard) cardProp.getCard();
        target.changePlayerLifePoint(monsterCard.getAttack());
        MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();

        monsterHouse.setMonsterAttacked();
        int practicalAttack = ((MonsterHouse) cardProp.getCardPlace()).getAdditionalAttack() + monsterCard.getAttack();
        return "you opponent receives " + practicalAttack + " battle damage";
    }
}
