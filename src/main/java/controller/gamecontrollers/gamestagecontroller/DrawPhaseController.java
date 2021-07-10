package controller.gamecontrollers.gamestagecontroller;

import controller.gamecontrollers.GeneralController;
import model.cards.cardsProp.Card;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.HandHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.Deck;

import java.io.FileNotFoundException;

public class DrawPhaseController extends GeneralController {

    private static DrawPhaseController instance;

    public DrawPhaseController() {
    }

    public static DrawPhaseController getInstance() {
        if (instance == null) instance = new DrawPhaseController();
        return instance;
    }

    public String draw(boolean isCheating) throws FileNotFoundException {
        Game game = GameInProcess.getGame();
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        HandHouse[] hand = player.getBoard().getPlayerHand();
        if (!isCheating) {
            if (game.doesPlayerHavePermissionToDraw() && player.isAllowedToDraw && player.getBoard().numberOfFullHouse("hand") < 6) {
                game.setPlayerDrawInTurn();
                return chooseCardFromDeckAndPlaceToHand(player);
            }
        } else {
            return chooseCardFromDeckAndPlaceToHand(player);
        }
        return null;
    }

    private String chooseCardFromDeckAndPlaceToHand(Player player) throws FileNotFoundException {
        Deck playerDeck = player.getDeck();
        Card newCard = playerDeck.getMainDeck().get(0);
        playerDeck.removeCardFromMainDeck(newCard);
        try {
            player.getBoard().getFirstEmptyHouse().setCard(newCard);
        } catch (NullPointerException e) {
            return "";
        }

        return newCard.getName();
    }

    public String process(String message, String name) {
        if (message.contains("_CN_")) {
            message = message.replace("_CN_", name);
        }
        return message;
    }
}
