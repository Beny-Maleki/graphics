package viewer.menu;


import controller.ImportScanner;
import model.enums.MenusMassages.DeckMessages;
import controller.menues.menuhandlers.menucontrollers.DeckMenuController;
import viewer.Regex;
import viewer.menudisplay.DeckMenuDisplay;

import java.util.regex.Matcher;

public class DeckMenu {
    private static DeckMenu deckMenu;

    private DeckMenu() {
    }

    public static DeckMenu getInstance() {
        if (deckMenu == null) {
            deckMenu = new DeckMenu();
        }
        return deckMenu;
    }

    private static void recognizeCommand(String command) {
        command = command.trim();
        boolean haveRecognizedCommand = false;
        Matcher matcher;
        if (command.equals("menu show-current")) {
            haveRecognizedCommand = true;
            DeckMenuController.showCurrent();
        } else if ((matcher = Regex.getMatcher(command, Regex.showCard)).matches()) {
            haveRecognizedCommand = true;
            DeckMenuController.showCard(matcher.group("cardName"));
        } else if ((matcher = Regex.getMatcher(command, Regex.createDeck)).matches()) {
            haveRecognizedCommand = true;
            DeckMenuController.createDeck(matcher.group("deckName"));
        } else if ((matcher = Regex.getMatcher(command, Regex.deleteDeck)).matches()) {
            haveRecognizedCommand = true;
            DeckMenuController.deleteDeck(matcher.group("deckName"));
        } else if ((matcher = Regex.getMatcher(command, Regex.activateDeck)).matches()) {
            haveRecognizedCommand = true;
            DeckMenuController.activateDeck(matcher.group("deckName"));
        } else if (command.equals("deck show --all")) {
            haveRecognizedCommand = true;
            DeckMenuController.showAllDecks();
        } else if (command.equals("deck show --cards")) {
            haveRecognizedCommand = true;
            DeckMenuController.showAllCardsOfUser();
        } else {
            for (int i = 0; i < 2; i++) {
                if ((matcher = Regex.getMatcher(command, Regex.showOneSideDeck[i])).matches()) {
                    haveRecognizedCommand = true;
                    String deckName = matcher.group("deckName");
                    DeckMenuController.showOneSideDeck(deckName);
                    break;
                }
            }
        }
        if (!haveRecognizedCommand) {
            if ((matcher = Regex.getMatcher(command, Regex.showOneMainDeck)).matches()) {
                haveRecognizedCommand = true;
                String deckName = matcher.group("deckName");
                DeckMenuController.showOneMainDeck(deckName);
            }
        }
        if (!haveRecognizedCommand) {
            for (int i = 0; i < 6; i++) {
                if ((matcher = Regex.getMatcher(command, Regex.addCardToSideDeck[i])).matches()) {
                    haveRecognizedCommand = true;
                    String cardName = matcher.group("cardName");
                    String deckName = matcher.group("deckName");
                    DeckMenuController.addCardToSideDeck(cardName, deckName);
                    break;
                }
            }
        }
        if (!haveRecognizedCommand) {
            for (int i = 0; i < 2; i++) {
                if ((matcher = Regex.getMatcher(command, Regex.addCardToMainDeck[i])).matches()) {
                    haveRecognizedCommand = true;
                    String cardName = matcher.group("cardName");
                    String deckName = matcher.group("deckName");
                    DeckMenuController.addCardToMainDeck(cardName, deckName);
                    break;
                }
            }
        }
        if (!haveRecognizedCommand) {
            for (int i = 0; i < 6; i++) {
                if ((matcher = Regex.getMatcher(command, Regex.removeCardFromSideDeck[i])).matches()) {
                    haveRecognizedCommand = true;
                    String cardName = matcher.group("cardName");
                    String deckName = matcher.group("deckName");
                    DeckMenuController.removeCardFromSideDeck(cardName, deckName);
                    break;
                }
            }
        }
        if (!haveRecognizedCommand) {
            for (int i = 0; i < 2; i++) {
                if ((matcher = Regex.getMatcher(command, Regex.removeCardFromMainDeck[i])).matches()) {
                    haveRecognizedCommand = true;
                    String cardName = matcher.group("cardName");
                    String deckName = matcher.group("deckName");
                    DeckMenuController.removeCardFromMainDeck(cardName, deckName);
                    break;
                }
            }
        }
        if (!haveRecognizedCommand) {
            DeckMenuController.invalidCommand();
        }


    }

    public void run() {
        String command;
        while (true) {
            command = ImportScanner.getInput();
            if (command.equals("menu exit")) {
                break;
            }
            recognizeCommand(command);
        }
        DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_EXIT_MENU);
    }
}
