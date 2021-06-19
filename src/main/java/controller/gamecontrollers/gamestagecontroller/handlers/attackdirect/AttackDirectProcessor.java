package controller.gamecontrollers.gamestagecontroller.handlers.attackdirect;

import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;

public abstract class AttackDirectProcessor {
    AttackDirectProcessor processor;

    public AttackDirectProcessor(AttackDirectProcessor processor) {
        this.processor = processor;
    }

    public String process(SelectedCardProp cardProp, Player target, boolean firstTurnOfTheGame, GameMainStage stage) {
        if (processor != null) {
            return processor.process(cardProp  ,target, firstTurnOfTheGame, stage);
        } else return null;
    }
}
