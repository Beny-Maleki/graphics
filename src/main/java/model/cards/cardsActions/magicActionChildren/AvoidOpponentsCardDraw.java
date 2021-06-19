package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsActions.Action;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.gameprop.turnBasedObserver.AvoidDrawingForSomeTurnsObserver;

public class AvoidOpponentsCardDraw extends Action {
    private int numOfTurns;

    public AvoidOpponentsCardDraw(int numOfTurns) {
        this.setNumOfTurns(numOfTurns);
    }

    {
        name = this.getClass().getSimpleName();
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    @Override
    public void active(Game game) {
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);

        opponent.isAllowedToDraw = false;

        new AvoidDrawingForSomeTurnsObserver(numOfTurns, opponent); // let the game watch it!
    }
}
