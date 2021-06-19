package model.cards.cardsEnum.Monster;


public enum MonsterAttribute {
    DARK,
    EARTH,
    FIRE,
    LIGHT,
    WATER,
    WIND;

    public static MonsterAttribute assignAttribute(String attribute) {
        switch (attribute) {
            case "DARK": {
                return DARK;
            }
            case "EARTH": {
                return EARTH;
            }
            case "FIRE": {
                return FIRE;
            }
            case "LIGHT": {
                return LIGHT;
            }
            case "WATER": {
                return WATER;
            }
            case "WIND": {
                return WIND;
            }
            default: {
                return null;
            }
        }
    }
}


