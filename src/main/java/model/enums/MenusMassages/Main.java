package model.enums.MenusMassages;

public enum Main {
    CURRENT_MENU("Main menu"),
    SUCCESSFULLY_ENTER_MENU("you entered Main menu successfully"),
    SUCCESSFULLY_EXIT_MENU("Main menu exited successfully"),
    SUCCESSFULLY_LOGOUT("User logout successfully"),
    MONEY_CHEAT_ACCEPTED("Your money increased by %s and now is %s");

    private final String mainMessage;

    Main(String mainMessage) {
        this.mainMessage = mainMessage;
    }

    public String getMainMessage() {
        return mainMessage;
    }
}
