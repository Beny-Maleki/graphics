package model.enums;

public enum Menu {
    MAIN_MENU("MainMenu.fxml"),
    SHOP_MENU("ShopMenu.fxml"),
    DECK_MENU("DeckMenu.fxml");

    String address;

    Menu(String address) {
        this.address = address;
    }

    public String getAddress() {
        return "/graphicprop/fxml/" + address;
    }
}
