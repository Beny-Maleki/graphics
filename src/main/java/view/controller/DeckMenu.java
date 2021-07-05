package view.controller;


import controller.ImportScanner;
import model.enums.MenusMassages.DeckMessages;
import controller.menues.menuhandlers.menucontrollers.DeckMenuController;
import view.Regex;
import view.menudisplay.DeckMenuDisplay;

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

}
