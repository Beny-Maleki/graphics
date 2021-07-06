package model.cards.cardsEnum.Monster;

public enum MonsterType {
    NORMAL("Normal"),
    EFFECT("Effect"),
    RITUAL("RITUAL");

    String enumToString;

    MonsterType(String enumToString) {
        this.enumToString = enumToString;
    }

    public static MonsterType assignType(String type) {
        switch (type) {
            case "Normal": {
                return NORMAL;
            }
            case "Effect": {
                return EFFECT;
            }
            case "RITUAL": {
                return RITUAL;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return enumToString;
    }
}
