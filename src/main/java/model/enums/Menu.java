package model.enums;

public enum Menu {
    WELCOME_MENU("WelcomePage.fxml"),
    MAIN_MENU("MainPage.fxml"),
    REGISTER_MENU("RegisterPage.fxml"),
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
