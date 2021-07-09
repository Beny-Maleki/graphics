package model.cards.cardsActions.magicActionChildren;


import controller.gamecontrollers.GetStringInputFromView;
import model.cards.cardsActions.Action;
import model.cards.cardsProp.Card;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.HandHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;
import model.userProp.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GuessingCardInOpponentHandAction extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        String name = GetStringInputFromView.getInputFromView(RequestingInput.GUESS_CARD);
        boolean isCardInOpponentHand = false;
        PlayerBoard opponentBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        for (HandHouse house  : opponentBoard.getPlayerHand()) {
            Card card = house.getCard();
            if (name.equals(card.getName())) {
                isCardInOpponentHand = true;
                break;
            }
        }
        if (isCardInOpponentHand) {
            Deck deck = game.getPlayer(SideOfFeature.OPPONENT).getDeck();
            ArrayList<Card> mainDeck = deck.getMainDeck();
            Iterator<Card> mainDeckIterator = mainDeck.iterator();
            while (mainDeckIterator.hasNext()) {
                Card card = mainDeckIterator.next();
                if (card.getName().equals(name)) {
                    opponentBoard.getGraveYard().addCardToGraveYard(card);
                    mainDeckIterator.remove();
                }
            }
            HandHouse[] opponentHand = opponentBoard.getPlayerHand();
            for (HandHouse handHouse : opponentHand) {
                Card card = handHouse.getCard();
                if (card.getName().equals(name)) {
                    opponentBoard.getGraveYard().addCardToGraveYard(card);
                    handHouse.removeCard();
                }
            }
            MonsterHouse[] opponentMonsterHouse = opponentBoard.getMonsterHouse();
            for (MonsterHouse monsterHouse : opponentMonsterHouse) {
                if (monsterHouse.getMonsterCard().getName().equals(name)) {
                    opponentBoard.getGraveYard().addCardToGraveYard(monsterHouse.getMonsterCard());
                    monsterHouse.setMonsterCard(null);
                }
            }
        }
        else {
            PlayerBoard currentPlayerBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
            HandHouse[] hand = currentPlayerBoard.getPlayerHand();
            //Collections.shuffle(hand);TODO fix the bug
            Card card = hand[0].getCard();
            currentPlayerBoard.getGraveYard().addCardToGraveYard(card);
            hand[0].removeCard();
        }
        isActivatedBefore = true;
    }
}
