package model.enums;

public enum Menu {
    MAIN_MENU("MainMenu.fxml");

    String address;

    Menu(String address) {
        this.address = address;
    }

    public String getAddress() {
        return "/graphicprop/fxml/" + address;
    }
}
