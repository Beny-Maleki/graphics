package model.userProp;

import model.cards.cardsProp.Card;
import model.cards.cardsProp.MonsterCard;

import java.util.Collections;
import java.util.List;

public class UserAI extends FatherUser {
    private DifficultyLevel AIDifficulty;
    private Deck playerDeck;

    {
        score = 0;
        nickname = "Robot";
    }

    public UserAI(DifficultyLevel AIDifficulty) {
        this.AIDifficulty = AIDifficulty;
        randomValidDeck();
    }

    private void randomValidDeck() {

        int weakMonsterNumber = 0;
        int normalMonsterNumber = 0;
        int strongMonsterNumber = 0;
        int weakCounter = 0;
        int normalCounter = 0;
        int strongCounter = 0;
        switch (AIDifficulty) {
            case EASY:
                weakMonsterNumber = 10;
                normalMonsterNumber = 10;
                strongMonsterNumber = 10;
                break;
            case NORMAL:
                weakMonsterNumber = 7;
                normalMonsterNumber = 12;
                strongMonsterNumber = 13;
                break;
            case HARD:
                weakMonsterNumber = 5;
                normalMonsterNumber = 15;
                strongMonsterNumber = 13;
                break;
        }

        List<Card> monsterCards = MonsterCard.getCards();
        List<Card> magicCards = MonsterCard.getCards();
        Collections.shuffle(monsterCards);
        Collections.shuffle(magicCards);
        for (Card card : monsterCards) {
            MonsterCard castToMonster = (MonsterCard) card;
            if (castToMonster.getLevel() < 5 && weakCounter != weakMonsterNumber) {
                activeDeck.addCardToMainDeck(castToMonster);
                weakCounter++;
            } else if (castToMonster.getLevel() < 7 && normalCounter != normalMonsterNumber) {
                activeDeck.addCardToMainDeck(castToMonster);
                normalCounter++;
            } else if (strongCounter != strongMonsterNumber) {
                activeDeck.addCardToMainDeck(castToMonster);
                strongCounter++;
            }
        }
        for (int i = 0; i < 10; i++) {
            activeDeck.addCardToMainDeck(magicCards.get(i));
        }
    }
}
