package controller.gamecontrollers.gamestagecontroller.handlers.attackmonster;

import controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.processors.AttackProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.attackmonster.processors.SelectedCardProcessor;
import model.cards.cardsProp.MonsterCard;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class AttackMonsterChain {
    AttackMonsterProcessor processor;

    public AttackMonsterChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectedCardProcessor(new AttackProcessor(null));
    }

    public String request(SelectedCardProp cardProp, MonsterHouse monsterHouse, Game game) {
        MonsterCard attacker = null;
        MonsterCard defence = null;
        if (cardProp != null && monsterHouse != null) {
            attacker = (MonsterCard) cardProp.getCard();
            defence = monsterHouse.getMonsterCard();
        }
        String message = processor.process(cardProp, monsterHouse, game).toString();

        if (attacker != null && defence != null) {
            message = modifier(message, defence, attacker);
        }
        return message;
    }

    private String modifier(String message, MonsterCard defence, MonsterCard attacker) {
        int damage;
        if (message.contains("OO_DEFEAT")) {
            damage = defence.getAttack() - attacker.getAttack();
            message = message.replace("OO_DEFEAT",
                    String.valueOf(damage));
        } else if (message.contains("D_DEFEAT")) {
            damage = defence.getDefence() - attacker.getAttack();
            message = message.replace("D_DEFEAT", String.valueOf(damage));
        } else if (message.contains("OO_WIN")) {
            damage = attacker.getAttack() - defence.getAttack();
            message = message.replace("OO_WIN", String.valueOf(damage));
        }
        return message;
    }

}
