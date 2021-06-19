package model.cards.cardsEnum.Monster;

public enum MonsterType {
    NORMAL,
    EFFECT,
    RITUAL;

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
}
