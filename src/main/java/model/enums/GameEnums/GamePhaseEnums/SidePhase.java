package model.enums.GameEnums.GamePhaseEnums;

public enum SidePhase {
    NOT_IN_SIDE_PHASE("this action can't done in your current location"),
    INVALID_TRIBUTE_COMMAND("invalid tribute command " +
            "\n( right format -> 1 card : T:<address> 2 cards : T1:<address> T2:<address>"),
    INVALID_ADDRESS("there is no monster on one of these addresses");
    String sidePhase;

    SidePhase(String sidePhase) {
        this.sidePhase = sidePhase;
    }

    @Override
    public String toString() {
        return sidePhase;
    }
}
