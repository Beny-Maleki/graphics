package controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.MonsterProcessor;
import model.cards.cardsEnum.Monster.MonsterType;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.TypeOfHire;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.enums.GameEnums.gamestage.GameSideStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class HireMonsterProcessor extends MonsterProcessor {

    public HireMonsterProcessor(MonsterProcessor processor) {
        super(processor);
    }

    public String process(Game game, TypeOfHire type) {
        MonsterCard monsterCard = (MonsterCard) game.getCardProp().getCard();

        if (monsterCard.getType() == MonsterType.RITUAL) {
            PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
            if (!board.doesRitualCardAvailable()) {
                return MainPhase.CANT_FIND_ADVANCED_ART_FOR_HIRE_RITUAL_MONSTER.toString();
            }
            game.setHiredMonsterRitual(true);
        }
        if (monsterCard.getLevel() < 5) {
            for (MonsterHouse monsterHouse : game.getPlayer(SideOfFeature.CURRENT).getBoard().getMonsterHouse()) {
                if (monsterHouse.getMonsterCard() == null) {
                    monsterHouse.setMonsterCard(monsterCard);
                    if (type.equals(TypeOfHire.SUMMON)) monsterHouse.setState(MonsterHouseVisibilityState.OO);
                    else monsterHouse.setState(MonsterHouseVisibilityState.DH);
                    game.setHiredMonster(monsterHouse);
                    game.setCardProp(null);
                    game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand().remove(monsterCard);
                    if (type.equals(TypeOfHire.SUMMON)) return MainPhase.SUMMONED_SUCCESSFULLY.toString();
                    else return MainPhase.SET_SUCCESSFULLY.toString();
                }
            }
        } else {
            game.setTypeOfMonsterHire(type);
            if (monsterCard.getLevel() < 7) {
                if (game.getPlayer(SideOfFeature.CURRENT).getBoard().numberOfFullHouse("monster") < 1)
                    return MainPhase.TRIBUTE_NOT_POSSIBLE.toString();
                game.setTributeSize(1);
                game.setGameSideStage(GameSideStage.TRIBUTE);
                return MainPhase.ONE_TRIBUTE_NEED.toString();
            } else {
                if (game.getPlayer(SideOfFeature.CURRENT).getBoard().numberOfFullHouse("monster") < 2)
                    return MainPhase.TRIBUTE_NOT_POSSIBLE.toString();
                game.setTributeSize(2);
                game.setGameSideStage(GameSideStage.TRIBUTE);
                return MainPhase.TW0_TRIBUTE_NEED.toString();
            }
        }
        return super.process(game, type);
    }
}
