package model.userProp;

import model.cards.cardsProp.Card;

import java.util.ArrayList;

public class User extends FatherUser{

    private static ArrayList<User> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    private final ArrayList<String> allUserDecksId;
    private final ArrayList<Integer> userCardCollection;
    private String username;
    private String password;

    {
        allUserDecksId = new ArrayList<>();
        userCardCollection = new ArrayList<>();
        activeDeck = null;
    }

    {
        balance = 10000000;
        score = 0;
    }

    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        allUsers.add(this);
    }

    public static User getUserByUserInfo(String info, UserInfoType userInfoType) {
        switch (userInfoType) {
            case USERNAME: {
                for (User user : allUsers) {
                    if (user.getUsername().equals(info)) {
                        return user;
                    }
                }
                break;
            }
            case NICKNAME: {
                for (User user : allUsers) {
                    if (user.getNickname().equals(info))
                        return user;
                }
                break;
            }
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> users) {
        allUsers = users;
    }

    public Deck getDeckByName(String name) {

        for (String ID : allUserDecksId) {
            Deck deck = Deck.getDeckById(ID);
            assert deck != null;
            if (deck.getName().equals(name)) {
                return deck;
            }
        }
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordMatch(String password) {
        return password.equals(this.password);
    }

    public void setActiveDeck(Deck activeDeck) {
        if (this.activeDeck != null) this.activeDeck.setDeckActivated(false);
        this.activeDeck = activeDeck;
        if (activeDeck != null) activeDeck.setDeckActivated(true);
    }

    public ArrayList<Deck> getAllUserDecksId() {
        ArrayList<Deck> allUserDecks = new ArrayList<>();
        for (String deckId : allUserDecksId) {
            allUserDecks.add(Deck.getDeckById(deckId));
        }
        return allUserDecks;
    }

    public ArrayList<Card> getUserCardCollection() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Integer ID : userCardCollection) {
            cards.add(Card.getCardById(ID));
        }
        return cards;
    }

    public void addCard(Integer ID) {
        userCardCollection.add(ID);
    }

    public void removeCard(int ID) {
        userCardCollection.remove(ID);
    }


    public boolean isCardInUserCardCollection(Card card) {
        return !this.getUserCardCollection().contains(card);
    }

    public void addDeckId(String ID) {
        allUserDecksId.add(ID);
    }


}

