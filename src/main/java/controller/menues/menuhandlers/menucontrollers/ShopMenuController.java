package controller.menues.menuhandlers.menucontrollers;

import controller.Controller;
import model.cards.cardsProp.Card;
import model.enums.Error;
import model.enums.MenusMassages.DeckMessages;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;
import view.menudisplay.DeckMenuDisplay;
import view.menudisplay.ShopMenuDisplay;

import java.util.ArrayList;
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

    public static void buyCard(Card card) {
        User user = LoginUser.getUser();
        Card newCard = card.getSimilarCard();
        user.addCard(newCard);
        user.changeBalance(-card.getPrice());
    }

    public static ArrayList<Card> cardNameAlphabetSorter(List<Card> cards) {
        Card[] sortedCards = cards.toArray(new Card[0]);
        Comparator<Card> cardNameSorter = Comparator.comparing(Card::getName);

        Arrays.sort(sortedCards, cardNameSorter);
        return new ArrayList<>(Arrays.asList(sortedCards));
    }

    public static int countCardInUserProperties(User loggedInUser, int count, Card selectedCard) {
        for (Card c : loggedInUser.getCardCollection()) {
            if (c.getName().equals(selectedCard.getName())) {
                count++;
            }
        }
        for (Deck deck : loggedInUser.getAllUserDecksId()) {
            if (deck != null) {
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
        }
        return count;
    }

    public static void invalidCommand() {
        ShopMenuDisplay.display(Error.INVALID_COMMAND);
    }
}
