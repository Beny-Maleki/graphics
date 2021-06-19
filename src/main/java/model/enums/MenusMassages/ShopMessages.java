package model.enums.MenusMassages;

public enum ShopMessages {
    CURRENT_MENU("Shop menu"),
    SUCCESSFULLY_ENTER_MENU("you entered Shop menu successfully"),
    SUCCESSFULLY_EXIT_MENU("Shop menu exited successfully"),
    SUCCESSFULLY_BOUGHT_A_CARD("card bought successfully and have been added to your card collection!\n Your Balance: %s");

    private final String message;

    ShopMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
