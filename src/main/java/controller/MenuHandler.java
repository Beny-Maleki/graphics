package controller;

import com.sanityinc.jargs.CmdLineParser;
import model.enums.Menu;
import model.enums.MenusMassages.*;
import viewer.menudisplay.ShopMenuDisplay;
import viewer.menudisplay.DeckMenuDisplay;
import viewer.menudisplay.ProfileMenuDisplay;
import viewer.menudisplay.ScoreboardMenuDisplay;
import viewer.menu.DeckMenu;
import viewer.menu.*;

import java.io.IOException;
import java.util.ArrayList;

public class MenuHandler {
    private static final ArrayList<String> ALL_MENUS;

    static {
        ALL_MENUS = new ArrayList<>() {
            {
                add("Register menu");
                add("Main menu");
                add("Duel menu");
                add("Deck menu");
                add("Scoreboard menu");
                add("Profile menu");
                add("Shop menu");
            }
        };
    }

    public static void changeMenu(Menu menu) throws CmdLineParser.OptionException, IOException {
        if (menu == Menu.REGISTER_MENU) {
            RegisterMenu registerMenu = RegisterMenu.getInstance();
            System.out.println(Register.SUCCESSFULLY_ENTER_MENU.toString());
            registerMenu.run();
        }
        else if (menu == Menu.MAIN_MENU) {
            MainMenu mainMenu = MainMenu.getInstance();
            System.out.println(Main.SUCCESSFULLY_ENTER_MENU.getMainMessage());
            mainMenu.run();
        }
        else if (menu == Menu.SCORE_BOARD_MENU) {
            ScoreboardMenu scoreboardMenu = ScoreboardMenu.getInstance();
            ScoreboardMenuDisplay.display(Scoreboard.SUCCESSFULLY_ENTER_MENU);
            scoreboardMenu.run();
        }
        else if (menu == Menu.USER_PROFILE_MENU) {
            ProfileMenu profileMenu = ProfileMenu.getInstance();
            ProfileMenuDisplay.display(Profile.SUCCESSFULLY_ENTER_MENU);
            profileMenu.run();
        } else if (menu == Menu.DECK_MENU) {
            DeckMenu deckMenu = DeckMenu.getInstance();
            DeckMenuDisplay.display(DeckMessages.SUCCESSFULLY_ENTER_MENU);
            deckMenu.run();
        } else if (menu == Menu.SHOP_MENU) {
            ShopMenu shopMenu = ShopMenu.getInstance();
            ShopMenuDisplay.display(ShopMessages.SUCCESSFULLY_ENTER_MENU);
            shopMenu.run();
        } else if (menu == Menu.START_DUEL) {
            DuelMenu duelMenu = DuelMenu.getInstance();
            duelMenu.run();
        }
    }

    public static boolean isMenuExist(String menuName) {
        return ALL_MENUS.contains(menuName);
    }

}
