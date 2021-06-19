package model.cards.cardsEnum.Magic;

public enum MagicAttribute {
    RITUAL(),
    QUICK_PLAY(),
    FIELD(),
    EQUIP(),
    CONTINUOUS(),
    COUNTER(),
    ANTI_TRAP(),
    NORMAL();

    MagicAttribute() {
    }

    public static MagicAttribute setAttribute(String attribute) {
        switch (attribute) {
            case "Ritual": {
                return RITUAL;
            }
            case "Quick-Play": {
                return QUICK_PLAY;
            }
            case "Field": {
                return FIELD;
            }
            case "Continuous": {
                return CONTINUOUS;
            }
            case "Counter": {
                return COUNTER;
            }
            case "Anti-Trap": {
                return ANTI_TRAP;
            }
            case "Normal": {
                return NORMAL;
            }
            case "Equip": {
                return EQUIP;
            }
            default:
                return null;
        }
    }
}
