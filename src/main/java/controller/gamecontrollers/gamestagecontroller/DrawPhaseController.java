package controller.gamecontrollers.gamestagecontroller;

import controller.gamecontrollers.GeneralController;
import model.cards.cardsProp.Card;
import model.enums.GameEnums.GamePhaseEnums.DrawPhase;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.gamemodel.Game;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.userProp.Deck;

import java.util.ArrayList;

public class DrawPhaseController extends GeneralController {

   private static DrawPhaseController instance ;

   private DrawPhaseController(){
   }

    public static DrawPhaseController getInstance() {
        if (instance == null) instance = new DrawPhaseController();
        return instance;
    }

    public String draw() {
        Game game = GameInProcess.getGame();
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        ArrayList<Card> hand = player.getBoard().getPlayerHand();
        if (!game.isPlayerDrawInThisTurn() && player.isAllowedToDraw) {
            if (hand.size() < 6) {
                Deck playerDeck = player.getDeck();
                Card newCard = playerDeck.getMainDeck().get(0);
                playerDeck.removeCardFromMainDeck(newCard);
                game.setPlayerDrawInTurn();
               player.getBoard().getPlayerHand().add(newCard);
               return process(DrawPhase.ADD_NEW_CARD.toString(), newCard.getName());
            }
        }
        return null;
    }

    public String process(String message , String name){
        if (message.contains("_CN_")){
            message = message.replace("_CN_", name);
        }
        return message;
    }
}
