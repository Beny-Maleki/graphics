package model.gameprop;

import model.cards.cardsProp.Card;
import model.gameprop.BoardProp.PlayerBoard;
import model.userProp.Deck;
import model.userProp.User;

public class Player {
    public  boolean isAllowedToDraw;
    public int playerLifePoint;
    User user;
    PlayerBoard board;
    Deck deck;
    int numberOfWinningRound;

    {
        isAllowedToDraw = true;
        playerLifePoint = 8000;
    }

    public Player(User user, int numberOfWinningRound) {
        setUser(user);
        board = new PlayerBoard();
        deck = user.getActiveDeck().getCopy();
        gameSetUp();
        this.numberOfWinningRound = numberOfWinningRound;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public int getPlayerLifePoint() {
        return this.playerLifePoint;
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayerBoard getBoard() {
        return board;
    }

    public void changePlayerLifePoint(int amount) {
        playerLifePoint -= amount;
        if (playerLifePoint < 0)
            playerLifePoint = 0;
    }

    private void gameSetUp() {
        //Collections.shuffle(deck.getMainDeck());
        for (int i = 0; i < 5; i++) {
            board.getPlayerHand().add(deck.getMainDeck().get(i));
        }

        for (int i = 0 ; i < 5 ; i++){
            deck.removeCardFromMainDeck(deck.getMainDeck().get(i));
        }

    }

    public int getNumberOfWinningRound() {
        return numberOfWinningRound;
    }

    public void increaseWinningRound() {
        numberOfWinningRound++;
    }
}
