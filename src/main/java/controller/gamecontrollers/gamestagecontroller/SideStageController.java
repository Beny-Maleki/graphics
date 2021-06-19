package controller.gamecontrollers.gamestagecontroller;

import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.GamePhaseEnums.SidePhase;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.TypeOfHire;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.enums.GameEnums.gamestage.GameSideStage;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.GameInProcess;
import model.gameprop.gamemodel.Game;
import viewer.Regex;
import viewer.game.UserInterface;

public class SideStageController {

    private static SideStageController instance;

    private SideStageController() {
    }

    public static SideStageController getInstance() {
        if (instance == null) instance = new SideStageController();
        return instance;
    }

    public String run(String command) {
        Game game = GameInProcess.getGame();
        switch (game.getGameSideStage()) {
            case NONE:
                return SidePhase.NOT_IN_SIDE_PHASE.toString();
            case TRIBUTE:
                PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
                if (game.getTributeNumber() == 1) {
                    if (command.matches(Regex.sideStageCommand[0])) {
                        int cardAddress = command.charAt(2) - 1;
                        MonsterCard card = (MonsterCard) board.getCard(cardAddress, CardLocation.MONSTER_ZONE);
                        if (card == null) {
                            return SidePhase.INVALID_ADDRESS.toString();
                        } else {
                            board.moveCardToGraveYard(cardAddress, CardLocation.MONSTER_ZONE);
                        }
                    } else
                        return SidePhase.INVALID_TRIBUTE_COMMAND.toString();
                } else {
                    if (command.matches(Regex.sideStageCommand[1])) {
                        int cardOneAddress = Character.getNumericValue(command.charAt(3)) - 1;
                        int cardTwoAddress = Character.getNumericValue(command.charAt(8)) - 1;
                        MonsterCard cardOne = (MonsterCard) board.getCard(cardOneAddress, CardLocation.MONSTER_ZONE);
                        MonsterCard cardTwo = (MonsterCard) board.getCard(cardTwoAddress, CardLocation.MONSTER_ZONE);
                        if (cardOne == null || cardTwo == null) {
                            return SidePhase.INVALID_ADDRESS.toString();
                        } else {
                            board.moveCardToGraveYard(cardOneAddress, CardLocation.MONSTER_ZONE);
                            board.moveCardToGraveYard(cardTwoAddress, CardLocation.MONSTER_ZONE);
                        }
                    } else
                        return SidePhase.INVALID_TRIBUTE_COMMAND.toString();
                }
                TypeOfHire type = game.getTypeOfMonsterHire();
                for (MonsterHouse monsterHouse : game.getPlayer(SideOfFeature.CURRENT).getBoard().getMonsterHouse()) {
                    if (monsterHouse.getMonsterCard() == null) {
                        monsterHouse.setMonsterCard((MonsterCard) game.getCardProp().getCard());
                        if (type == TypeOfHire.SUMMON) monsterHouse.setState(MonsterHouseVisibilityState.OO);
                        else monsterHouse.setState(MonsterHouseVisibilityState.DH);
                        game.setHiredMonster(monsterHouse);
                        game.setCardProp(null);
                        if (type == TypeOfHire.SUMMON) {
                            game.setGameSideStage(GameSideStage.NONE);
                            return MainPhase.SUMMONED_SUCCESSFULLY.toString();
                        } else {
                            game.setGameSideStage(GameSideStage.NONE);
                            return MainPhase.SET_SUCCESSFULLY.toString();
                        }
                    }
                }
            case EX_CHANGE_WITH_SIDE_DECK_FOR_PLAYER_ONE:
                UserInterface userInterface = new UserInterface(game);
                if (command.equals("Finish")) {
                    game.setGameSideStage(GameSideStage.EX_CHANGE_WITH_SIDE_DECK_FOR_PLAYER_TWO);
                    return userInterface.showSideDeck(game.getPlayer(SideOfFeature.OPPONENT));
                } else if (command.equals("Show Main Deck")) {
                    return userInterface.showMainDeck(game.getPlayer(SideOfFeature.CURRENT));
                } else {

                }
        }
        return null;

    }
}
