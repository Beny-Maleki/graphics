package model.events.eventChildren;

import model.events.Event;

public class ActivationInOpponentTurn extends Event {
    private static ActivationInOpponentTurn instance;

    private ActivationInOpponentTurn() {
    }

    public static ActivationInOpponentTurn getInstance() {
        if (instance == null) {
            instance = new ActivationInOpponentTurn();
        }
        return instance;
    }
}
