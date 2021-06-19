package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsActions.Action;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.gameprop.turnBasedObserver.ChangeTeamForOneTurn;
import viewer.game.GetStringInput;

public class ChangeTeamOfMonsterCard extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        Player currentPlayer = game.getPlayer(SideOfFeature.CURRENT);
        Player opponentPlayer = game.getPlayer(SideOfFeature.OPPONENT);

        PlayerBoard oppoBoard = opponentPlayer.getBoard();
        if (oppoBoard.numberOfFullHouse("monster") == 0) {

        } else {

            MonsterHouse[] oppoPlayerMonsterHouses = oppoBoard.getMonsterHouse();
            MonsterHouse[] currPlayerMonsterHouses = currentPlayer.getBoard().getMonsterHouse();
            String selectedCardName = "";

            while(selectedCardName.length() == 0) { // inputting selected card
                selectedCardName = GetStringInput.getInput(RequestingInput.CHOOSE_FROM_OPPO_MONSTER_HOUSES);
                // TODO: output properly!!!
            }

            for (MonsterHouse oppoPlayerMonsterHouse : oppoPlayerMonsterHouses) { // seeking for the selected card...
                MonsterCard card = oppoPlayerMonsterHouse.getMonsterCard();

                if (card.getName().equals(selectedCardName)) { // found the wanted Card!
                    oppoPlayerMonsterHouse.setMonsterCard(null); // emptying monster house
                    for (MonsterHouse monsterHouse : currPlayerMonsterHouses) { // setting the card at the first empty monsterHouse
                        if (monsterHouse.getMonsterCard() == null) {
                            monsterHouse.setMonsterCard(card);
                        }
                    }

                    //Now we should make an observer for this action!
                    new ChangeTeamForOneTurn(card, currentPlayer, opponentPlayer);
                    break;
                }
            }

        }


    }
}
