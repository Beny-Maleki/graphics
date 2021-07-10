package controller.gamecontrollers;

import com.sanityinc.jargs.CmdLineParser;
import controller.gamecontrollers.gamestagecontroller.DrawPhaseController;
import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectChain;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GameError;
import model.enums.GameEnums.GamePhaseEnums.General;
import model.enums.GameEnums.PlayerTurn;
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
import model.gameprop.Selectable;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;
import view.game.UserInterface;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralController {

    private static GeneralController instance;

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

    public void selectCard(Game game, Selectable selectable) {
        boolean isCardBelongToCurrent = game.getPlayer(SideOfFeature.CURRENT).getBoard().doesBelong(selectable);
        SelectedCardProp<Selectable> selectedCard = new SelectedCardProp<>(isCardBelongToCurrent, selectable);
        game.setCardProp(selectedCard);
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
        String output;

        if (cardProp == null) output = General.NO_CARD_SELECTED.toString();
        else if (!cardProp.doesBelongToCurrent()) {
            if (cardProp.getLocation().equals(CardLocation.SPELL_ZONE)) {
                MagicHouse magicHouse = (MagicHouse) cardProp.getCardPlace();
                if (magicHouse.getState().equals(MagicHouseVisibilityState.H)) {
                    output = GameError.INVALID_SHOW_CARD_REQUEST.toString();
                } else {
                    output = cardProp.getCard().getCardDetail();
                }
            } else {
                MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                if (monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) {
                    output = GameError.INVALID_SHOW_CARD_REQUEST.toString();
                } else {
                    output = cardProp.getCard().getCardDetail();
                    if (cardProp.getCard() instanceof MonsterCard && cardProp.getLocation() == CardLocation.MONSTER_ZONE) {
                        monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                        int additionalAttack = monsterHouse.getAdditionalAttack();
                        int additionalDefence = monsterHouse.getAdditionalDefence();
                        return output + "\n additional Attack : "
                                + additionalAttack
                                + "\n additional Defence : " + additionalDefence;
                    }
                }
            }
        } else {
            output = cardProp.getCard().getCardDetail();
            if (cardProp.getCard() instanceof MonsterCard && cardProp.getLocation() == CardLocation.MONSTER_ZONE) {
                MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
                int additionalAttack = monsterHouse.getAdditionalAttack();
                int additionalDefence = monsterHouse.getAdditionalDefence();
                return output + "\n additional Attack : "
                        + additionalAttack
                        + "\n additional Defence : " + additionalDefence;
            }

        }
        return output;
    }

    public String nextPhase(Game game) throws FileNotFoundException {
        game.goToNextPhase();
        String output = game.getGameMainStage().getPhaseName();
        if (game.getGameMainStage().equals(GameMainStage.DRAW_PHASE)) {
            if (game.doesPlayerHavePermissionToDraw()) {
                new DrawPhaseController().draw(false);
            }
        }  // ActivationInOpponentTurn.getInstance().activeEffects(game);

        return output;
    }

    public String finishRound(Game game) throws FileNotFoundException {
        GameInProcess.getGame().finishRound(game.getTurnOfGame());
        return roundOrGameResults(game);
    }


    private String roundOrGameResults(Game game) {
        if (game.isGameFinished()) {
            Player winner = game.getWinner();
            return winner.getUser().getNickname() +
                    "  WIN !!!! game Points -> " + game.getWinner().getNumberOfWinningRound() + " : " + game.getLooser().getNumberOfWinningRound();
        } else {
            String gameScoreBoard = game.getPlayer(SideOfFeature.CURRENT).getUser().getNickname() + " : "
                    + game.getPlayer(SideOfFeature.CURRENT).getNumberOfWinningRound() + " | " +
                    game.getPlayer(SideOfFeature.OPPONENT).getUser().getNickname() + " : " +
                    game.getPlayer(SideOfFeature.OPPONENT).getNumberOfWinningRound();
            return gameScoreBoard + "\n" + drawSideDeck(game, game.getPlayer(SideOfFeature.CURRENT));
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

    public String run(String command) throws CmdLineParser.OptionException, FileNotFoundException {
        Game game = GameInProcess.getGame();
        String output = null;
        if (game.getGameSideStage().equals(GameSideStage.START_STAGE)) {
            if (command.equals("START")) {
                game.setGameSideStage(GameSideStage.NONE);
                output = DrawPhaseController.getInstance().draw(false) + " \n" + drawBoard(game);
            } else output = "invalid command to start game";
        } else if (game.getGameSideStage().equals(GameSideStage.NONE)) {
            if (command.startsWith("select -d")) {
                output = deSelectCard(game);
                // d selecting card
            } else if (command.startsWith("show graveyard")) {
                output = showGraveYard(game, command);
                // show grave yard (current / opponent)
            } else if (command.startsWith("card show")) {
                output = showSelectedCard(game);
                // show card detail
            } else if (command.equals("finishRound")) {
                // loose one round
                output = finishRound(game);
            } else if (command.equals("next phase")) {
                output = nextPhase(game);
            } else if (command.equals("draw board")) {
                output = drawBoard(game);
            } else if (command.equals("active effect")) {
                output = activeEffect(game);
            } else if (command.startsWith("cheat code: ")) {
                output = runCheatCode(command);
            }
        } else output = "back to game first";
        return processAnswer(game, output);
    }


    private String process(String generalMessage, String name) {
        if (generalMessage.contains("StAgE")) {
            generalMessage = generalMessage.replace("StAgE", name);
        }
        return generalMessage;
    }

    private String activeEffect(Game game) throws FileNotFoundException {
        ActiveEffectChain chain = new ActiveEffectChain();
        return chain.request(game);
    }

    private String runCheatCode(String cheatCode) throws FileNotFoundException {
        // increase money is implemented in main controller!
        if (cheatCode.contains("winner")) {
            Game game = GameInProcess.getGame();
            PlayerTurn playerTurn = game.getTurnOfGame();

            if (playerTurn.equals(PlayerTurn.PLAYER_ONE)) {
                game.finishRound(PlayerTurn.PLAYER_TWO);
            } else if (playerTurn.equals(PlayerTurn.PLAYER_TWO)) {
                game.finishRound(PlayerTurn.PLAYER_ONE);
            }

            return roundOrGameResults(game);
        } else if (cheatCode.contains("draw")) {
            return DrawPhaseController.getInstance().draw(true);
        } else if (cheatCode.contains("increase LP")) {
            String regex = "cheat code: increase LP -> (?<amount>\\d+)";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cheatCode);

            String output;
            if (matcher.matches()) {
                String amount = matcher.group("amount");
                int amountInt = Integer.parseInt(amount);

                Game game = GameInProcess.getGame();
                Player currPlayer = game.getPlayer(SideOfFeature.CURRENT);

                int LPAfterIncrease = currPlayer.getPlayerLifePoint() + amountInt;
                if (LPAfterIncrease <= 8000) {
                    currPlayer.setPlayerLifePoint(LPAfterIncrease);
                    output = "Your LP increased by " + amountInt + " and now is " + currPlayer.getPlayerLifePoint();
                } else {
                    output = "cheat denied! Don't be greedy :) Maximum possible LP is 8000!";
                }
            } else {
                output = "cheat denied! You were close :) but the format wasn't completely true!";
            }

            return output;
        } else {
            return "cheat denied! Cheat with such pattern doesn't exist!";
        }
    }

    protected String processAnswer(Game game, String rawOutPut) throws FileNotFoundException {
        game.checkRoundFinishCondition();
        if (game.isRoundFinish()) {
            return rawOutPut + "\n" + finishRound(game);
        }
        return rawOutPut;
    }
}
