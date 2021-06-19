package controller.menues.menuhandlers.menucontrollers;

import model.enums.Error;
import model.enums.MenusMassages.DeckMessages;
import model.enums.MenusMassages.ShopMessages;
import model.cards.cardsProp.Card;
import model.userProp.LoginUser;
import model.userProp.User;
import viewer.menudisplay.DeckMenuDisplay;
import viewer.menudisplay.ShopMenuDisplay;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShopMenuController {
    public static void showCurrent() {
        DeckMenuDisplay.display(DeckMessages.CURRENT_MENU);
    }

    public static void buyCard(String cardName) {
        User user = LoginUser.getUser();
        Card card = Card.getCardByName(cardName);
        int userBalance = user.getBalance();

        if (card == null) {
            ShopMenuDisplay.display(Error.INVALID_CARD_NAME_IN_SHOP, cardName);
        } else if (userBalance < card.getPrice()) {
            ShopMenuDisplay.display(Error.NOT_ENOUGH_MONEY);
        } else {
            user.addCard(card.getID());
            user.changeBalance(-card.getPrice());
            ShopMenuDisplay.display(ShopMessages.SUCCESSFULLY_BOUGHT_A_CARD, "" + user.getBalance());
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

    public static void invalidCommand() {
        ShopMenuDisplay.display(Error.INVALID_COMMAND);
    }
}
