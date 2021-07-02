package model.events.eventChildren;

import model.events.Event;

public class SummonMonster extends Event {
    private static SummonMonster instance;

    public static SummonMonster getInstance() {
        if (instance == null) {
            instance = new SummonMonster();
        }
        return instance;
    }
}
