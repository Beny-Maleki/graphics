package controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.FlipSummonProcessor;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class SelectCardProcessor extends FlipSummonProcessor {
    public SelectCardProcessor(FlipSummonProcessor processor) {
        super(processor);
    }

    public MainPhase process(SelectedCardProp cardProp, Game game) {
        if (cardProp == null)
            return MainPhase.NO_CARD_SELECTED_YET;

        if (game.getHiredMonster() != null)
            return MainPhase.HIRE_MONSTER_BEFORE;

        if (!cardProp.getLocation().equals(CardLocation.MONSTER_ZONE))
            return MainPhase.WRONG_LOCATION_FOR_CHANGE;

        if (!cardProp.getCardPlace().getState().equals(MonsterHouseVisibilityState.DH))
            return MainPhase.CANT_FLIP_SUMMON_THIS_CARD;


        return super.process(cardProp, game);
    }
}
