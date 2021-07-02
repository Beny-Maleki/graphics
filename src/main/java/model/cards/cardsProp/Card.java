package model.cards.cardsProp;

import model.events.Event;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Card {
    protected static List<Card> cards;
    protected static int numberOfCard;

    static {
        cards = new ArrayList<>();
        numberOfCard = 0;
    }

    protected int ID;
    protected String name;
    protected String number; // on card's picture
    protected int price;
    protected String description;


    public Card(String name, String description, String price) {
        setName(name);
        setDescription(description);
        setPrice(price);
        cards.add(this);
        ID = numberOfCard;
        numberOfCard++;
        System.out.println("ID of card : " + ID + "number of Cards : " + numberOfCard);
    }

    public Card() {
    }

    public static Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card;
            }
        }
        return null; // No such CArd notExists.
    }

    public static int getCardPriceByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card.price;
            }
        }
        return -1; // No such card notExists.
    }

    public static String getDescriptionByName(String name) {
        for (Card card : cards) {
            if (card.name.equals(name)) {
                return card.description;
            }
        }
        return null; // No such card notExists.
    }

    public static void addMagicsToCards(ArrayList<MagicCard> magicCards) {
        cards.addAll(magicCards);
    }

    public static void addMonstersToCards(ArrayList<MonsterCard> monsterCards) {
        cards.addAll(monsterCards);
    }

    public static List<Card> getCards() {
        return cards;
    }

    public abstract String getCardDetail();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void activeEffectsByEvent(Event event, Game game) {
    }

    public abstract Card getCopy(); // semi duplicate code in overrides; cause -> Card is abstract and not constructable!

    public static Card getCardById(int ID) {
        for (Card card : cards) {
            if (card.ID == ID) {
                return card;
            }
        }
        return null;
    }



    public int getID() {
        return ID;
    }

}
