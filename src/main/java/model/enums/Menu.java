package model.enums;

public enum Menu {
    WELCOME_MENU("WelcomePage"),
    MAIN_MENU("MainPage"),
    REGISTER_MENU("RegisterPage"),
    LOGIN_MENU("LoginPage"),
    PROFILE_CHANGE_PASSWORD("ProfileChangePassword"),
    PROFILE_CHANGE_NICKNAME("ProfileChangeNickname"),
    SHOP_MENU("ShopMenu"),
    DECKS_VIEW("DecksView"),
    SCOREBOARD_MENU("ScoreBoardPage"),
    PROFILE_MENU("ProfilePage"),
    DECK_MODIFIER("DeckModifier"),
    DUEL_MENU("DuelPage"),
    ROCK_PAPER_SCISSOR_PAGE("RockPaperScissor"),
    GAME_PAGE("GamePage");

    String address;

    Menu(String address) {
        this.address = address;
    }

    public String getAddress() {
        return "/graphicprop/fxml/" + address + ".fxml";
    }
}
