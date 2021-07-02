package model.enums;

public enum Menu {
    MAIN_MENU("MainMenu.fxml"),
    PROFILE_CHANGE_PASSWORD("ProfileChangePassword.fxml"),
    PROFILE_CHANGE_NICKNAME("ProfileChangeNickname.fxml");

    String address;

    Menu(String address) {
        this.address = address;
    }

    public String getAddress() {
        return "/graphicprop/fxml/" + address;
    }
}
