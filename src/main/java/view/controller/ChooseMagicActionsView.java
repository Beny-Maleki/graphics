package view.controller;

import animatefx.animation.Wobble;
import controller.menues.menuhandlers.menucontrollers.CardCreatorController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.cards.cardsActions.Action;
import model.cards.cardsEnum.Magic.MagicAttribute;
import model.cards.cardsEnum.Magic.MagicType;
import model.cards.cardsEnum.Magic.RestrictionTypeInAdding;
import model.cards.cardsProp.MagicCard;
import model.enums.Menu;
import model.events.Event;
import view.AudioPath;
import view.ClickButtonHandler;
import view.SoundEffectHandler;

import java.io.IOException;
import java.util.ArrayList;

public class ChooseMagicActionsView {
    public Button back;
    public Label price;
    public Button add;
    public Label message;
    public Label details;
    public Button remove;
    public Button previous;
    public Button next;
    public Label description;
    public Button create;
    private String name;
    private MagicType magicType;
    private MagicAttribute magicAttribute;
    private RestrictionTypeInAdding restrictionTypeInAdding;
    private ArrayList<Action> actions;
    private ArrayList<Event> triggers;
    private ArrayList<MagicCard> magicCards;
    private MagicCard currentMagicCard;
    private int index = 0;
    private CardCreatorController controller;
    private String descriptionFinal;

    {
        controller = new CardCreatorController();
        actions = new ArrayList<>();
        triggers = new ArrayList<>();
        descriptionFinal = "";
    }

    public void setDetails(String name, MagicType magicType, MagicAttribute magicAttribute, RestrictionTypeInAdding restrictionTypeInAdding) {
        this.name = name;
        this.magicAttribute = magicAttribute;
        this.magicType = magicType;
        this.restrictionTypeInAdding = restrictionTypeInAdding;
        setDetailOfCard(details);
        magicCards = MagicCard.getMagicCards();
        setFirstDescription();
        updatePriceHere();
    }

    private void updatePriceHere() {
        controller.updatePrice(actions, restrictionTypeInAdding, magicType, price);
    }

    private void setFirstDescription() {
        currentMagicCard = magicCards.get(index);
        description.setText(currentMagicCard.getDescriptionWithDifferentLine());
    }

    private void setDetailOfCard(Label details) {
        String detailOfCard = "Name: " + name +
                "\n" + magicType.toString() +
                "\nType: " + magicAttribute.toString() +
                "\nSpeed: " + restrictionTypeInAdding.toString();
        details.setText(detailOfCard);
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            controller.moveToPage(back, Menu.CARD_CREATOR_PAGE);
        } else if (event.getSource() == add) {
            descriptionFinal += currentMagicCard.getDescription() + "\n";
            controller.addActions(currentMagicCard, actions, triggers, message);
            updatePriceHere();
        } else if (event.getSource() == remove) {
            controller.removeActions(currentMagicCard, actions, triggers, message);
            updatePriceHere();
        } else if (event.getSource() == next) {
            index = (index + 1) % magicCards.size();
            currentMagicCard = magicCards.get(index);
            description.setText(currentMagicCard.getDescriptionWithDifferentLine());
        } else if (event.getSource() == previous) {
            if (index == 0) {
                index = magicCards.size() - 1;
            } else {
                index++;
            }
            currentMagicCard = magicCards.get(index);
            description.setText(currentMagicCard.getDescriptionWithDifferentLine());
        } else if (event.getSource() == create) {
            controller.createMagic(name, magicType.toString(), magicAttribute.toString(), descriptionFinal, restrictionTypeInAdding.toString(), price.getText(), actions, triggers, message);
//            if (magicAttribute.toString().equals("Field")) {
//                controller.moveToPage(create, Menu.CHOOSE_FIELD_CARDS_DETAILS);
//            } else {
//                controller.createMagic(name, magicType.toString(), magicAttribute.toString(), description.getText(), restrictionTypeInAdding.toString(), previous.getText(), actions, triggers, message);
//            }
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Wobble((Node) event.getSource()).play();
    }
}
