package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import model.enums.Error;
import model.enums.MenusMassages.DeckMessages;
import model.enums.MenusMassages.ShopMessages;
import model.cards.cardsProp.Card;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;
import view.menudisplay.DeckMenuDisplay;
import view.menudisplay.ShopMenuDisplay;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShopMenuController extends Controller {
    private static ShopMenuController instance;

    private ShopMenuController() {
    }

    public static ShopMenuController getInstance() {
        if (instance == null) instance = new ShopMenuController();
        return instance;
    }

    public static void showCurrent() {
        DeckMenuDisplay.display(DeckMessages.CURRENT_MENU);
    }

    public static void buyCard(String cardName) {
        User user = LoginUser.getUser();
        Card card = Card.getCardByName(cardName);

        if (card != null) {
            user.addCard(card.getID());
            user.changeBalance(-card.getPrice());
        }
    }

    public static void showAllCards() {
        List<Card> cards = Card.getCards();
        Card[] sortedCards = ShopMenuController.cardNameAlphabetSorter(cards);
        ShopMenuDisplay.printAllCards(sortedCards);
    }

    public static Card[] cardNameAlphabetSorter(List<Card> cards) {
        Card[] sortedCards = cards.toArray(new Card[0]);
        Comparator<Card> cardNameSorter = Comparator.comparing(Card::getName);

        Arrays.sort(sortedCards, cardNameSorter);
        return sortedCards;
    }

    public static void showCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            ShopMenuDisplay.display(Error.INVALID_CARD_NAME);
        } else {
            ShopMenuDisplay.printCardDetail(card);
        }
    }

    public static int countCardInUserProperties(User loggedInUser, int count, Card selectedCard) {
        for (Card c : loggedInUser.getUserCardCollection()) {
            if (c.getName().equals(selectedCard.getName())) {
                count++;
            }
        }
        for (Deck deck : loggedInUser.getAllUserDecksId()) {
            for (Card c : deck.getMainDeck()) {
                if (c.getName().equals(selectedCard.getName())) {
                    count++;
                }
            }
            for (Card c : deck.getSideDeck()) {
                if (c.getName().equals(selectedCard.getName())) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void invalidCommand() {
        ShopMenuDisplay.display(Error.INVALID_COMMAND);
    }
}
