package controller.menues.menuhandlers.menucontrollers;

import model.cards.cardsProp.Card;
import model.enums.Error;
import model.enums.MenusMassages.DeckMessages;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;
import viewer.menudisplay.DeckMenuDisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class DeckMenuController {
    public static void showCurrent() {
        DeckMenuDisplay.display(DeckMessages.CURRENT_MENU);
    }

    public static void createDeck(String name) {
        if (LoginUser.getUser().getDeckByName(name) != null) {
            DeckMenuDisplay.display(Error.REPETITIOUS_DECK_NAME, name);
        } else {
            Deck deck = new Deck(name);
            LoginUser.getUser().addDeckId(deck.getID());
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_CREATE_DECK);
        }
    }

    public static void deleteDeck(String name) {
        if (LoginUser.getUser().getDeckByName(name) == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, name);
        } else {
            Deck selectedDeck = LoginUser.getUser().getDeckByName(name);
            selectedDeck.deleteDeckFromOwner();
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_DELETE_DECK);
        }
    }

    public static void activateDeck(String name) {
        if (LoginUser.getUser().getDeckByName(name) == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, name);
        } else {
            Deck selectedDeck = LoginUser.getUser().getDeckByName(name);
            LoginUser.getUser().setActiveDeck(selectedDeck);
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_ACTIVATE_DECK);
        }
    }

    public static void showAllDecks() {
        ArrayList<Deck> decks = LoginUser.getUser().getAllUserDecksId();
        Deck activeDeck = LoginUser.getUser().getActiveDeck();
        Deck[] sortedDecks = DeckMenuController.deckNameAlphabetSorter(decks);
        DeckMenuDisplay.showAllDecks(sortedDecks, activeDeck);
    }

    public static void showOneMainDeck(String deckName) {
        Deck deck = LoginUser.getUser().getDeckByName(deckName);
        if (deck == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
        } else {
            Card[] sortedMainDeck = DeckMenuController.cardNameAlphabetSorter(deck.getMainDeck());
            DeckMenuDisplay.showOneMainDeck(sortedMainDeck, deckName);
        }
    }

    public static void showOneSideDeck(String deckName) {
        Deck deck = LoginUser.getUser().getDeckByName(deckName);
        if (deck == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
        } else {
            Card[] sortedSideDeck = DeckMenuController.cardNameAlphabetSorter(deck.getSideDeck());
            DeckMenuDisplay.showOneSideDeck(sortedSideDeck, deckName);
        }
    }

    public static void showAllCardsOfUser() {
        User user = LoginUser.getUser();
        ArrayList<Card> unionOfDecksAndCollection;

        unionOfDecksAndCollection = new ArrayList<>(user.getUserCardCollection());
        for (Deck deck : user.getAllUserDecksId()) {
            unionOfDecksAndCollection.addAll(deck.getMainDeck());
            unionOfDecksAndCollection.addAll(deck.getSideDeck());
        }

        Card[] sortedCards = DeckMenuController.cardNameAlphabetSorter(unionOfDecksAndCollection);
        DeckMenuDisplay.printAllCards(sortedCards);
    }

    public static Deck[] deckNameAlphabetSorter(ArrayList<Deck> decks) {
        Deck[] sortedDecks = decks.toArray(new Deck[0]);
        Comparator<Deck> deckNameSorter = Comparator.comparing(Deck::getName);

        Arrays.sort(sortedDecks, deckNameSorter);
        return sortedDecks;
    }

    private static Card[] cardNameAlphabetSorter(ArrayList<Card> cards) {
        Card[] sortedCards = cards.toArray(new Card[0]);
        Comparator<Card> cardNameSorter = Comparator.comparing(Card::getName);

        Arrays.sort(sortedCards, cardNameSorter);
        return sortedCards;
    }

    public static void addCardToMainDeck(String cardName, String deckName) {
        User user = LoginUser.getUser();
        Deck selectedDeck = user.getDeckByName(deckName);
        Card selectedCard = Card.getCardByName(cardName);
        if (selectedCard == null || user.isCardInUserCardCollection(selectedCard)) {
            DeckMenuDisplay.display(Error.NOT_FOUND_CARD_NAME_IN_COLLECTION, cardName);
        } else if (selectedDeck == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
        } else if (selectedDeck.getMainDeck().size() > 60) {
            DeckMenuDisplay.display(Error.FULL_MAIN_DECK);
        } else if (selectedDeck.numOfCardOccurrence(cardName, "both decks") == 3) {
            DeckMenuDisplay.display(Error.NUMBER_LIMITATION_PASSED, cardName, deckName);
        } else {
            selectedDeck.addCardToMainDeck(selectedCard);
            user.removeCard(selectedCard.getID());
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_ADD_CARD_TO_DECK);
        }
    }

    public static void addCardToSideDeck(String cardName, String deckName) {
        User user = LoginUser.getUser();
        Deck selectedDeck = user.getDeckByName(deckName);
        Card selectedCard = Card.getCardByName(cardName);
        if (selectedCard == null || user.isCardInUserCardCollection(selectedCard)) {
            DeckMenuDisplay.display(Error.NOT_FOUND_CARD_NAME_IN_COLLECTION, cardName);
        } else if (selectedDeck == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
        } else if (selectedDeck.getSideDeck().size() > 15) {
            DeckMenuDisplay.display(Error.FULL_SIDE_DECK);
        } else if (selectedDeck.numOfCardOccurrence(cardName, "both decks") == 3) {
            DeckMenuDisplay.display(Error.NUMBER_LIMITATION_PASSED, cardName, deckName);
        } else {
            selectedDeck.addCardToSideDeck(selectedCard);
            user.removeCard(selectedCard.getID());
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_ADD_CARD_TO_DECK);
        }
    }

    public static void removeCardFromMainDeck(String cardName, String deckName) {
        Deck selectedDeck = LoginUser.getUser().getDeckByName(deckName);
        Card selectedCard = Card.getCardByName(cardName);
        if (selectedDeck == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
        } else if (selectedDeck.numOfCardOccurrence(cardName, "main deck") == 0) {
            DeckMenuDisplay.display(Error.NOT_FOUND_CARD_NAME_IN_MAIN_DECK, cardName, deckName);
        } else {
            assert selectedCard != null;
            selectedDeck.removeCardFromMainDeck(selectedCard);
            LoginUser.getUser().addCard(selectedCard.getID());
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_REMOVE_CARD_FROM_DECK);
        }
    }

    public static void removeCardFromSideDeck(String cardName, String deckName) {
        Deck selectedDeck = LoginUser.getUser().getDeckByName(deckName);
        Card selectedCard = Card.getCardByName(cardName);
        if (selectedDeck == null) {
            DeckMenuDisplay.display(Error.NOT_FOUND_DECK_NAME, deckName);
        } else if (selectedDeck.numOfCardOccurrence(cardName, "side deck") == 0) {
            DeckMenuDisplay.display(Error.NOT_FOUND_CARD_NAME_IN_SIDE_DECK, cardName, deckName);
        } else {
            assert selectedCard != null;
            selectedDeck.removeCardFromSideDeck(selectedCard);
            LoginUser.getUser().addCard(selectedCard.getID());
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_REMOVE_CARD_FROM_DECK);
        }
    }

    public static void showCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            DeckMenuDisplay.display(Error.INVALID_CARD_NAME);
        } else {
            DeckMenuDisplay.printCardDetail(card);
        }
    }

    public static void invalidCommand() {
        DeckMenuDisplay.display(Error.INVALID_COMMAND);
    }
}
