package controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.FlipSummonProcessor;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class FlipCardProcessor extends FlipSummonProcessor {

    public FlipCardProcessor(FlipSummonProcessor processor) {
        super(processor);
    }

    public MainPhase process(SelectedCardProp cardProp, Game game) {
        MonsterHouse flipCardHouse = (MonsterHouse) cardProp.getCardPlace();
        flipCardHouse.setState(MonsterHouseVisibilityState.OO);
        game.setHiredMonster(flipCardHouse);
        return MainPhase.CARD_FLIP_SUCCESSFULLY;
    }
}
