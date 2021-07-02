package model.events.eventChildren;

import model.events.Event;

public class GotAttacked extends Event {
    private static GotAttacked instance;

    public static GotAttacked getInstance() {
        if (instance == null) {
            instance = new GotAttacked();
        }
        return instance;
    }
}
