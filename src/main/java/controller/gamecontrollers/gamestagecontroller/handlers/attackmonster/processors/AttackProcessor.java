package controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.AttackMonsterProcessor;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class AttackProcessor extends AttackMonsterProcessor {
    public AttackProcessor(AttackMonsterProcessor processor) {
        super(processor);
    }

    public BattlePhase process(SelectedCardProp offensive, MonsterHouse target, Game game) {
        MonsterCard offensiveCard = (MonsterCard) offensive.getCard();
        MonsterCard targetCard = target.getMonsterCard();
        MonsterHouse offensiveCardPlace = (MonsterHouse) offensive.getCardPlace();
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        Player current = game.getPlayer(SideOfFeature.CURRENT);

        if (game.getGameMainStage() != GameMainStage.BATTLE_PHASE) return BattlePhase.ATTACK_NOT_IN_BATTLE_PHASE;

        offensiveCardPlace.setMonsterAttacked();

        if (target.getState().equals(MonsterHouseVisibilityState.OO)) {
            int attackDifference = offensiveCard.getAttack() - targetCard.getAttack();
            if (attackDifference > 0) {
                successfulAttackToOffensiveMonster(target, targetCard, opponent, attackDifference);
                return BattlePhase.SUCCESSFUL_ATTACK_OFFENSIVE_TARGET;
            } else if (attackDifference == 0) {
                drawAttackToOffensiveMonster(target, offensiveCard, targetCard, offensiveCardPlace, opponent, current);
                return BattlePhase.BOTH_CARD_ELIMINATE;
            } else {
                defeatAttackToOffensiveMonster(offensiveCard, offensiveCardPlace, opponent, current, attackDifference);
                return BattlePhase.DEFEAT_ATTACK_OO_TARGET;
            }
        } else {
            int damageAmount = offensiveCard.getAttack() - targetCard.getDefence();
            if (damageAmount > 0) {
                target.setMonsterCard(null);
                opponent.getBoard().getGraveYard().addCardToGraveYard(offensiveCard);
                return BattlePhase.SUCCESSFUL_ATTACK_DEFENCE_KNOWN_TARGET;
            } else if (damageAmount == 0) {
                target.setState(MonsterHouseVisibilityState.DO);
                return BattlePhase.NO_CARD_ELIMINATE;
            }
            else {
                current.changePlayerLifePoint(damageAmount * -1);
                return BattlePhase.DEFEAT_ATTACK_ON_DEFENCE_UNKNOWN;
            }
        }
    }

    private void defeatAttackToOffensiveMonster(MonsterCard offensiveCard, MonsterHouse offensiveCardPlace,
                                                Player opponent, Player current, int damage) {
        opponent.getBoard().getGraveYard().addCardToGraveYard(offensiveCard);
        offensiveCardPlace.setMonsterCard(null);
        current.changePlayerLifePoint(damage * -1);
    }

    private void drawAttackToOffensiveMonster(MonsterHouse target, MonsterCard offensiveCard, MonsterCard targetCard, MonsterHouse offensiveCardPlace, Player opponent, Player current) {
        offensiveCardPlace.setMonsterCard(null);
        target.setMonsterCard(null);
        opponent.getBoard().getGraveYard().addCardToGraveYard(offensiveCard);
        current.getBoard().getGraveYard().addCardToGraveYard(targetCard);
    }

    private void successfulAttackToOffensiveMonster(MonsterHouse target, MonsterCard targetCard, Player opponent, int attackDifference) {
        opponent.getBoard().getGraveYard().addCardToGraveYard(targetCard);
        target.setMonsterCard(null);
        opponent.changePlayerLifePoint(attackDifference);
    }
}
