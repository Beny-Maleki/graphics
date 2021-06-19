package model.cards.cardsActions.magicActionChildren;

import controller.gamecontrollers.GeneralController;
import controller.gamecontrollers.GetStringInputFromView;
import exceptions.CardNotFoundException;
import model.cards.cardsActions.Action;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.GameInProcess;
import model.gameprop.gamemodel.Game;

public class SummonMonsterFromOwnGraveYardAction extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard currentPlayerboard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        GeneralController.getInstance().showGraveYard(GameInProcess.getGame(), "--current");
        String cardToSummon = GetStringInputFromView.getInputFromView(RequestingInput.FROM_GRAVEYARD);
        MonsterCard summonedMonster;
        try {
            summonedMonster = currentPlayerboard.getGraveYard().getMonsterCardFromGraveyardByName(cardToSummon);
            currentPlayerboard.summonMonster(summonedMonster);
            currentPlayerboard.getGraveYard().removeCardFromGraveYard(summonedMonster);
        } catch (CardNotFoundException e) {
            e.printStackTrace();
            active(game);
        }
        isActivatedBefore = true;
    }
}
