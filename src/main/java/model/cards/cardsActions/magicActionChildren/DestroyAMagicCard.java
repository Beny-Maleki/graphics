package model.cards.cardsActions.magicActionChildren;

import controller.gamecontrollers.GetStringInputFromView;
import exceptions.CardNotFoundException;
import model.cards.cardsActions.Action;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class DestroyAMagicCard extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard opponentBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        PlayerBoard currentBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        String magicToDestroy = GetStringInputFromView.getInputFromView(RequestingInput.MAGIC_CARD_TO_DESTROY);
        try {
            MagicCard cardToDestroy = opponentBoard.getMagicCardByName(magicToDestroy);
            opponentBoard.getGraveYard().addCardToGraveYard(cardToDestroy);
            for (MagicHouse magicHouse : opponentBoard.getMagicHouse()) {
                if (magicHouse.getMagicCard().getName().equals(magicToDestroy)) {
                    magicHouse.setMagicCard(null);
                }
            }
        }
        catch (CardNotFoundException e) {
            try {
                MagicCard cardToDestroy = currentBoard.getMagicCardByName(magicToDestroy);
                currentBoard.getGraveYard().addCardToGraveYard(cardToDestroy);
                for (MagicHouse magicHouse : currentBoard.getMagicHouse()) {
                    if (magicHouse.getMagicCard().getName().equals(magicToDestroy)) {
                        magicHouse.setMagicCard(null);
                    }
                }
            } catch (CardNotFoundException cardNotFoundException) {
                cardNotFoundException.printStackTrace();
                active(game);
            }
        }
        isActivatedBefore = true;
    }
}
