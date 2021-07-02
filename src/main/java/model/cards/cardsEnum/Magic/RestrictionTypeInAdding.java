package model.cards.cardsEnum.Magic;

public enum RestrictionTypeInAdding {
    UNLIMITED,
    LIMITED;

    public static RestrictionTypeInAdding setSpeed(String speed) {
        switch (speed) {
            case "Unlimited": {
                return UNLIMITED;
            }
            case "Limited": {
                return LIMITED;
            }
            default:
                return null;
        }
    }
}
