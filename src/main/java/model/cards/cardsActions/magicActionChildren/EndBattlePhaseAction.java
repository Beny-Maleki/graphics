package model.cards.cardsActions.magicActionChildren;

import controller.gamecontrollers.GeneralController;
import model.cards.cardsActions.Action;
import model.gameprop.gamemodel.Game;

public class EndBattlePhaseAction extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        GeneralController.getInstance().nextPhase(game);
        isActivatedBefore = true;
    }
}
