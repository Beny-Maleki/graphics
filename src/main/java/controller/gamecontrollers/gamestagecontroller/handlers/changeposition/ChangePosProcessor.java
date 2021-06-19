package controller.gamecontrollers.gamestagecontroller.handlers.changeposition;

import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.WantedPos;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;

public abstract class ChangePosProcessor {
    ChangePosProcessor processor;

    protected ChangePosProcessor(ChangePosProcessor processor) {
        this.processor = processor;
    }

    protected MainPhase process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse monsterHouse) {
        if (processor != null) {
            return processor.process(cardProp, pos, monsterHouse);
        } else {
            return null;
        }
    }
}
