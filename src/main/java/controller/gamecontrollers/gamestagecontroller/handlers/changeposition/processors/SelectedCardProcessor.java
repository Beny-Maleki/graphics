package controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosProcessor;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.WantedPos;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;

public class SelectedCardProcessor extends ChangePosProcessor {

    public SelectedCardProcessor(ChangePosProcessor processor) {
        super(processor);
    }

    public MainPhase process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse monsterHouse) {
        if (cardProp == null) return MainPhase.NO_CARD_SELECTED_YET;
        if (!cardProp.getLocation().equals(CardLocation.MONSTER_ZONE))
            return MainPhase.WRONG_LOCATION_FOR_CHANGE;

        return super.process(cardProp, pos, monsterHouse);
    }
}
