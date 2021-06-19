package model.enums.MenusMassages;

public enum DeckMessages {
    CURRENT_MENU("Deck menu"),
    SUCCESSFULLY_ENTER_MENU("you entered Deck menu successfully"),
    SUCCESSFULLY_EXIT_MENU("Deck menu exited successfully"),
    SUCCESSFULLY_CREATE_DECK("deck created successfully!"),
    SUCCESSFULLY_DELETE_DECK("deck deleted successfully"),
    SUCCESSFULLY_ACTIVATE_DECK("deck activated successfully"),
    SUCCESSFULLY_ADD_CARD_TO_DECK("card added to deck successfully"),
    SUCCESSFULLY_REMOVE_CARD_FROM_DECK("card removed form deck successfully");

    private final String message;

    DeckMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}