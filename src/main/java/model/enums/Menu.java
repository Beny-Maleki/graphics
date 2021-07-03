package model.enums;

public enum Menu {
    WELCOME_MENU("WelcomePage"),
    MAIN_MENU("MainPage"),
    REGISTER_MENU("RegisterPage"),
    LOGIN_MENU("LoginPage"),
    PROFILE_CHANGE_PASSWORD("ProfileChangePassword"),
    PROFILE_CHANGE_NICKNAME("ProfileChangeNickname"),
    SHOP_MENU("ShopMenu"),
    DECK_MENU("DeckMenu");

    String address;

    Menu(String address) {
        this.address = address;
    }

    public String getAddress() {
        return "/graphicprop/fxml/" + address + ".fxml";
    }
}
