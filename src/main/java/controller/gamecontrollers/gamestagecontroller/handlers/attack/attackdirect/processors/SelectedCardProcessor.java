package controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.AttackDirectProcessor;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class SelectedCardProcessor extends AttackDirectProcessor {
    public SelectedCardProcessor(AttackDirectProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        String output;
        SelectedCardProp cardProp = game.getCardProp();

        assert cardProp != null;
        output = controller.gamecontrollers.gamestagecontroller.handlers.attack.
                attackmonster.processors.SelectedCardProcessor.checkSelectedCardCondition(null, cardProp);
        if (output == null)
            return super.process(game);
        return output;
    }
}
