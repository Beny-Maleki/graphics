package model.gameprop.BoardProp;

import exceptions.CardNotFoundException;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.userProp.Deck;

import java.util.ArrayList;

public class PlayerBoard {
    MagicHouse[] magicHouse;
    MonsterHouse[] monsterHouse;
    MagicHouse field;
    ArrayList<Card> playerHand;
    GraveYard graveYard;

    {
        initializeBoardHouses();
        playerHand = new ArrayList<>();
        graveYard = new GraveYard();
    }

    public MagicHouse[] getMagicHouse() {
        return magicHouse;
    }

    public MonsterHouse[] getMonsterHouse() {
        return monsterHouse;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    private void initializeBoardHouses() {
        monsterHouse = new MonsterHouse[5];
        for (int i = 0; i < monsterHouse.length; i++) {
            monsterHouse[i] = new MonsterHouse();
        }
        magicHouse = new MagicHouse[5];
        for (int i = 0; i < magicHouse.length; i++) {
            magicHouse[i] = new MagicHouse();
        }
        field = new MagicHouse();
    }

    public MagicHouse getFieldHouse() {
        return field;
    }

    public Card getCard(Integer address, CardLocation location) {
        switch (location) {
            case PLAYER_HAND:
                return playerHand.get(address);
            case FIELD_ZONE:
                return field.getMagicCard();
            case SPELL_ZONE:
                return magicHouse[address].getMagicCard();
            case MONSTER_ZONE:
                return monsterHouse[address].getMonsterCard();
            default:
                return null;
        }
    }

    public void moveCardToGraveYard( int address , CardLocation location){
        if (location.equals(CardLocation.MONSTER_ZONE)){
            Card card =  monsterHouse[address].getMonsterCard();
            monsterHouse[address].setMonsterCard(null);
            graveYard.addCardToGraveYard(card);
        } else if (location.equals(CardLocation.SPELL_ZONE)) {
            Card card = magicHouse[address].getMagicCard();
            magicHouse[address].setMagicCard(null);
            graveYard.addCardToGraveYard(card);
        }
    }

    public void moveCardToGraveYard(Card card) {
        if (card instanceof MonsterCard) {
            MonsterHouse monsterHouse = MonsterHouse.getMonsterHouseByMonsterCard((MonsterCard) card);
            monsterHouse.setMonsterCard(null);
            graveYard.addCardToGraveYard(card);
        } else if (card instanceof MagicCard) {
            MagicHouse magicHouse = MagicHouse.getMagicHouseByMagicCard((MagicCard) card);
            magicHouse.setMagicCard(null);
            graveYard.addCardToGraveYard(card);
        }
    }

    public int numberOfFullHouse(String typeOfHouse){
        int counter = 0;
        if (typeOfHouse.equals("monster")){
            for (MonsterHouse house : monsterHouse) {
                if (!house.getState().equals(MonsterHouseVisibilityState.E)){
                    counter++;
                }
            }
        }else{
            for (MagicHouse house : magicHouse) {
                if (!house.getState().equals(MagicHouseVisibilityState.E)){
                    counter++;
                }
            }
        }
        return counter;
    }

    public void summonMonster(MonsterCard monsterCard) {
        for (MonsterHouse house : monsterHouse) {
            if (house.getMonsterCard() == null) {
                house.setMonsterCard(monsterCard);
                house.setState(MonsterHouseVisibilityState.OO);
                return;
            }
        }
    }

    public MagicCard getMagicCardByName(String name) throws CardNotFoundException {
        for (MagicHouse house : magicHouse) {
            if (house.getMagicCard().getName().equals(name)) {
                return house.getMagicCard();
            }
        }
        throw new CardNotFoundException("magic card not found!");
    }

    public MonsterCard getMonsterCardByName(String name) throws CardNotFoundException {
        for (MonsterHouse house : monsterHouse) {
            if (house.getMonsterCard().getName().equals(name)) {
                return house.getMonsterCard();
            }
        }
        throw new CardNotFoundException("monster card not found!");
    }
}
