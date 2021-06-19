package model.cards.cardsActions.magicActionChildren;


import controller.gamecontrollers.GetStringInputFromView;
import model.cards.cardsActions.Action;
import model.cards.cardsProp.Card;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
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
        for (Card card : opponentBoard.getPlayerHand()) {
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
            ArrayList<Card> opponentHand = opponentBoard.getPlayerHand();
            Iterator<Card> handIterator = opponentHand.iterator();
            while (handIterator.hasNext()) {
                Card card = handIterator.next();
                if (card.getName().equals(name)) {
                    opponentBoard.getGraveYard().addCardToGraveYard(card);
                    handIterator.remove();
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
            ArrayList<Card> hand = currentPlayerBoard.getPlayerHand();
            Collections.shuffle(hand);
            Card card = hand.get(0);
            currentPlayerBoard.getGraveYard().addCardToGraveYard(card);
            hand.remove(0);
        }
        isActivatedBefore = true;
    }
}
