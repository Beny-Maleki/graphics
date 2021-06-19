package model.gameprop.gamemodel;

import model.cards.cardsProp.Card;
import model.enums.GameEnums.PlayerTurn;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.TypeOfHire;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.enums.GameEnums.gamestage.GameSideStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;
import model.gameprop.existenceBasedObserver.ExistenceObserver;
import model.gameprop.turnBasedObserver.TurnObserver;
import model.userProp.Deck;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private final int numberOfGameRounds;
    private boolean isGameFinished;
    private boolean isMatchFinished;
    private int roundNumber;
    private Player firstPlayer;
    private Player secondPlayer;
    private Turn turn;
    private GameSideStage gameSideStage;
    private GameMainStage gameMainStage;

    {
        roundNumber = 1;
        isGameFinished = false;
        isMatchFinished = false;
        gameMainStage = GameMainStage.DRAW_PHASE;
        gameSideStage = GameSideStage.START_STAGE;
    }

    public Game(Player firstPlayer, Player secondPlayer, int numberOfRounds) {
        setFirstPlayer(firstPlayer);
        setSecondPlayer(secondPlayer);
        turn = new Turn(PlayerTurn.PLAYER_ONE, true);
        this.numberOfGameRounds = numberOfRounds;
    }

    public boolean isPlayerDrawInThisTurn() {
        return turn.isCardDraw();
    }

    public void setPlayerDrawInTurn() {
        turn.setCardDraw();
    }

    public Player getPlayer(SideOfFeature turn) {
        switch (turn) {
            case CURRENT: {
                if (this.turn.getPlayerWithTurn() == (PlayerTurn.PLAYER_ONE)) {
                    return firstPlayer;
                } else {
                    return secondPlayer;
                }
            }
            case OPPONENT: {
                if (this.turn.getPlayerWithTurn() == (PlayerTurn.PLAYER_TWO)) {
                    return firstPlayer;
                } else {
                    return secondPlayer;
                }
            }
            default:
                return null;
        }
    }

    public MonsterHouse getHiredMonster() {
        return turn.getMonsterHouseOfHiredMonster();
    }

    public void setHiredMonster(MonsterHouse monsterHouse) {
        turn.setMonsterHouseOfHiredMonster(monsterHouse);
    }

    private void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    private void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public GameMainStage getGameMainStage() {
        return gameMainStage;
    }

    public GameSideStage getGameSideStage() {
        return gameSideStage;
    }

    public void setGameSideStage(GameSideStage gameSideStage) {
        this.gameSideStage = gameSideStage;
    }

    public SelectedCardProp getCardProp() {
        return turn.getSelectedCardProp();
    }

    public void setCardProp(SelectedCardProp cardProp) {
        turn.selectedCardProp = cardProp;
    }

    public void goToNextPhase() {
        gameMainStage = GameMainStage.getNextPhase(gameMainStage.getPhaseNumber());
        if (gameMainStage.equals(GameMainStage.DRAW_PHASE))
            resetLastTurnData();
    }

    public PlayerTurn getTurnOfGame() {
        return turn.getPlayerWithTurn();
    }

    public boolean isMatchFinished() {
        return isMatchFinished;
    }

    public void finishMatch(PlayerTurn looserTurn) {
        switch (looserTurn) {
            case PLAYER_ONE: {
                secondPlayer.increaseWinningRound();
                turn = new Turn(PlayerTurn.PLAYER_TWO, false);
                break;
            }
            case PLAYER_TWO: {
                firstPlayer.increaseWinningRound();
                turn = new Turn(PlayerTurn.PLAYER_TWO, false);
                break;
            }
        }
        if (roundNumber == numberOfGameRounds) {
            finishGame(looserTurn);
        } else {
            firstPlayer = new Player(firstPlayer.getUser(), firstPlayer.getNumberOfWinningRound());
            secondPlayer = new Player(secondPlayer.getUser(), secondPlayer.getNumberOfWinningRound());
            gameMainStage = GameMainStage.DRAW_PHASE;
            gameSideStage = GameSideStage.EX_CHANGE_WITH_SIDE_DECK_FOR_PLAYER_ONE;
            TurnObserver.clearTurnObserver();
            ExistenceObserver.clearExistenceObserver();
        }
        roundNumber++;
    }

    public void finishGame(PlayerTurn looserTurn) {
        switch (looserTurn) {
            case PLAYER_ONE: {
                calculatePlayersBonus(secondPlayer, firstPlayer);
                break;
            }
            case PLAYER_TWO:
                calculatePlayersBonus(firstPlayer, secondPlayer);
        }

    }

    private void calculatePlayersBonus(Player winner, Player looser) {
        if (numberOfGameRounds == 1) {
            looser.getUser().changeBalance(100);
            winner.getUser().increaseScore(1000);
            winner.getUser().changeBalance(1000 + winner.playerLifePoint);
        } else {
            looser.getUser().changeBalance(300);
            winner.getUser().increaseScore(3000);
            winner.getUser().changeBalance(3000 + winner.playerLifePoint);
        }
        isGameFinished = true;
    }

    public Player getWinner() {
        if (firstPlayer.getNumberOfWinningRound() > secondPlayer.getNumberOfWinningRound())
            return firstPlayer;
        else
            return secondPlayer;
    }

    public Player getLooser() {
        if (firstPlayer.getNumberOfWinningRound() > secondPlayer.getNumberOfWinningRound())
            return secondPlayer;
        else
            return firstPlayer;
    }

    public int getTributeNumber() {
        return turn.getTributeNumber();
    }

    private void resetLastTurnData() {
        ArrayList<TurnObserver> turnObservers = TurnObserver.getTurnObservers();
        if (turnObservers != null) {
            for (TurnObserver turnObserver : turnObservers) {
                turnObserver.update();
            }
        }
        if (turn.getPlayerWithTurn() == PlayerTurn.PLAYER_ONE) {
            turn = new Turn(PlayerTurn.PLAYER_TWO, false);
        } else turn = new Turn(PlayerTurn.PLAYER_ONE, false);
    }

    public void setTributeSize(int numberOfCard) {
        turn.setTributeNumber(numberOfCard);
    }

    public TypeOfHire getTypeOfMonsterHire() {
        return turn.getTypeOfHighLevelMonsterHire();
    }

    public void setTypeOfMonsterHire(TypeOfHire typeOfMonsterHire) {
        turn.setTypeOfHighLevelMonsterHire(typeOfMonsterHire);
    }

    public void moveCardFromDeckToHand(Card card) {
        Player player = GameInProcess.getGame().getPlayer(SideOfFeature.CURRENT);
        Deck deck = player.getDeck();
        ArrayList<Card> hand = player.getBoard().getPlayerHand();

        deck.removeCardFromMainDeck(card);
        hand.add(card);

        Collections.shuffle(deck.getMainDeck());
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public boolean isFirstTurnOfTheGame() {
        return turn.isFirstTurn();
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
