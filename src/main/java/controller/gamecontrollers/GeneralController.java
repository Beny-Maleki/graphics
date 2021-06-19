package controller.gamecontrollers;

import com.sanityinc.jargs.CmdLineParser;
import controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectChain;
import model.cards.cardsProp.Card;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GameError;
import model.enums.GameEnums.GamePhaseEnums.General;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.enums.GameEnums.gamestage.GameSideStage;
import model.gameprop.BoardProp.GraveYard;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;
import viewer.game.UserInterface;

public class GeneralController {

    private static GeneralController instance;

    protected GeneralController() {
    }

    public static GeneralController getInstance() {
        if (instance == null) instance = new GeneralController();
        return instance;
    }

    public String showGraveYard(Game game, String command) {
        Player player;
        if (command.contains("--opponent")) {
            player = game.getPlayer(SideOfFeature.OPPONENT);
        } else {
            player = game.getPlayer(SideOfFeature.CURRENT);
        }

        StringBuilder graveYardDisplay = new StringBuilder();

        GraveYard graveYard = player.getBoard().getGraveYard();

        if (graveYard.getDestroyedCards().size() == 0) {
            graveYardDisplay.append("graveyard empty");
        } else {
            for (Card destroyedCard : graveYard.getDestroyedCards()) {
                graveYardDisplay.append(destroyedCard.getName()).append(":").
                        append(destroyedCard.getDescription()).append("\n");
            }
            graveYardDisplay.deleteCharAt(graveYardDisplay.length() - 1);
        }
        return graveYardDisplay.toString();
    }

    private String selectCard(Game game, String command) throws CmdLineParser.OptionException {
        if (game.getCardProp() != null) {
            return GameError.CARD_SELECTED_BEFORE.toString();
        }
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option<Boolean> isOpponent = parser.addBooleanOption('o', "opponent");
        CmdLineParser.Option<Boolean> field = parser.addBooleanOption('f', "field");
        CmdLineParser.Option<Integer> monster = parser.addIntegerOption('m', "monster");
        CmdLineParser.Option<Integer> spell = parser.addIntegerOption('s', "spell");
        CmdLineParser.Option<Integer> hand = parser.addIntegerOption('h', "hand");
        String[] splitCommand = command.split(" ");
        parser.parse(splitCommand);


        boolean opponentSide = parser.getOptionValue(isOpponent, false);
        SideOfFeature side = SideOfFeature.CURRENT;
        Player player = game.getPlayer(SideOfFeature.CURRENT);
        if (opponentSide) {
            side = SideOfFeature.OPPONENT;
            player = game.getPlayer(SideOfFeature.OPPONENT);
        }

        Integer cardAddress;
        CardLocation location;

        if ((cardAddress = parser.getOptionValue(monster)) != null) {
            if (cardAddress > 5 || cardAddress < 1) {
                return GameError.INVALID_SELECTION.toString();
            }
            location = CardLocation.MONSTER_ZONE;
        } else if ((cardAddress = parser.getOptionValue(spell)) != null) {
            if (cardAddress > 5 || cardAddress < 1) {
                return GameError.INVALID_SELECTION.toString();
            }
            location = CardLocation.SPELL_ZONE;
        } else if (parser.getOptionValue(field, false)) {
            location = CardLocation.FIELD_ZONE;
            cardAddress = 0;
        } else {
            cardAddress = parser.getOptionValue(hand);
            if (cardAddress > player.getBoard().getPlayerHand().size()) {
                return GameError.INVALID_SELECTION.toString();
            }
            location = CardLocation.PLAYER_HAND;
        }

        if (player.getBoard().getCard(cardAddress - 1, location) != null) {
            SelectedCardProp selectedCardProp = new SelectedCardProp(cardAddress - 1, location, side);
            game.setCardProp(selectedCardProp);
            return General.CARD_SELECTED_SUCCESSFULLY.toString();
        } else {
            return GameError.EMPTY_SELECTION.toString();
        }

    }

    private String deSelectCard(Game game) {
        if (game.getCardProp() != null) {
            game.setCardProp(null);
            return General.CARD_DESELECT_SUCCESSFULLY.toString();
        } else {
            return GameError.INVALID_DESELECT_CARD_REQUEST.toString();
        }
    }

    private String showSelectedCard(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        if (cardProp == null) return General.NO_CARD_SELECTED.toString();
        if (cardProp.getSide().equals(SideOfFeature.OPPONENT)) {
            if (cardProp.getLocation().equals(CardLocation.SPELL_ZONE)) {
                MagicHouse magicHouse = (MagicHouse) cardProp.getCardPlace();
                if (magicHouse.getState().equals(MagicHouseVisibilityState.H)) {
                    return GameError.INVALID_SHOW_CARD_REQUEST.toString();
                } else {
                    return cardProp.getCard().getCardDetail();
                }
            } else {
                MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                if (monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) {
                    return GameError.INVALID_SHOW_CARD_REQUEST.toString();
                } else {
                    return cardProp.getCard().getCardDetail();
                }
            }
        } else {
            return cardProp.getCard().getCardDetail();
        }
    }

    public String nextPhase(Game game) {
        DrawPhaseController drawController = DrawPhaseController.getInstance();
        game.goToNextPhase();
        String output = process(General.NEXT_PHASE_MESSAGE.toString(), game.getGameMainStage().getPhaseName());
        if (game.getGameMainStage().equals(GameMainStage.DRAW_PHASE)) {
            String draw;
            if (!game.isPlayerDrawInThisTurn()) {
                if ((draw = drawController.draw()) != null) {
                    return output + "\n" + draw;
                } else return output;
            }
        }
        return process(General.NEXT_PHASE_MESSAGE.toString(), game.getGameMainStage().getPhaseName()) + "\n" + drawBoard(game);
    }

    private String surrender(Game game) {
        GameInProcess.getGame().finishMatch(game.getTurnOfGame());
        if (game.isGameFinished()) {
            Player winner = game.getWinner();
            return winner.getUser().getNickname() +
                    "  WIN !!!! game Points -> " + game.getWinner().getNumberOfWinningRound() + " : " + game.getLooser().getNumberOfWinningRound();
        } else {
            return drawSideDeck(game, game.getPlayer(SideOfFeature.CURRENT));
        }
    }

    private String drawBoard(Game game) {
        UserInterface drawer = new UserInterface(game);
        return drawer.drawBoard();
    }

    private String drawSideDeck(Game game, Player player) {
        UserInterface drawer = new UserInterface(game);
        return drawer.showSideDeck(player);
    }

    public String run(String command) throws CmdLineParser.OptionException {
        Game game = GameInProcess.getGame();
        if (game.getGameSideStage().equals(GameSideStage.START_STAGE)) {
            if (command.equals("START")) {
                game.setGameSideStage(GameSideStage.NONE);
                return DrawPhaseController.getInstance().draw() + " \n" + drawBoard(game);
            }
            return "invalid command to start game";
        } else if (game.getGameSideStage().equals(GameSideStage.NONE)) {
            if (command.startsWith("select -d")) {
                return deSelectCard(game);
                // d selecting card
            } else if (command.startsWith("show graveyard")) {
                return showGraveYard(game, command);
                // show grave yard (current / opponent)
            } else if (command.startsWith("select")) {
                return selectCard(game, command);
                // select a card from (monster / spell / hand )
            } else if (command.startsWith("card show")) {
                return showSelectedCard(game);
                // show card detail
            } else if (command.equals("surrender")) {
                // loose one round
                return surrender(game);
            } else if (command.equals("next phase")) {
                return nextPhase(game);
            } else if (command.equals("draw board")) {
                return drawBoard(game);
            } else if (command.equals("active effect")) {
                return activeEffect(game);
            } else if (command.startsWith("cheat code : ")) {
                return runCheatCode(command);
            } else return null;
        } else return "back to game first";
    }


    private String process(String generalMessage, String name) {
        if (generalMessage.contains("StAgE")) {
            generalMessage = generalMessage.replace("StAgE", name);
        }
        return generalMessage;
    }

    private String activeEffect(Game game) {
        ActiveEffectChain chain = new ActiveEffectChain();
        return chain.request(game);
    }

    private String runCheatCode(String cheatCode) {
        if (cheatCode.contains("winner")) {
            return null;
        } else
            return null;
    }
}
