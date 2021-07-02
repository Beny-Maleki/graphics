package controller.gamecontrollers.gamestagecontroller;

import controller.gamecontrollers.GeneralController;
import model.cards.cardsProp.Card;
import model.enums.GameEnums.GamePhaseEnums.DrawPhase;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.Deck;

import java.util.ArrayList;

public class DrawPhaseController extends GeneralController {

    private static DrawPhaseController instance;

    private DrawPhaseController() {
    }

    public static DrawPhaseController getInstance() {
        if (instance == null) instance = new DrawPhaseController();
        return instance;
    }

    public String draw(boolean isCheating) {
        Game game = GameInProcess.getGame();
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        ArrayList<Card> hand = player.getBoard().getPlayerHand();
        if (!isCheating) {
            if (game.doesPlayerHavePermissionToDraw() && player.isAllowedToDraw && hand.size() < 6) {
                game.setPlayerDrawInTurn();
                return chooseCardFromDeckAndPlaceToHand(player);
            }
        } else {
            return chooseCardFromDeckAndPlaceToHand(player);
        }
        return null;
    }

    private String chooseCardFromDeckAndPlaceToHand(Player player) {
        Deck playerDeck = player.getDeck();
        Card newCard = playerDeck.getMainDeck().get(0);
        playerDeck.removeCardFromMainDeck(newCard);
        player.getBoard().getPlayerHand().add(newCard);
        return process(DrawPhase.ADD_NEW_CARD.toString(), newCard.getName());
    }

    public String process(String message, String name) {
        if (message.contains("_CN_")) {
            message = message.replace("_CN_", name);
        }
        return message;
    }
}
