package model.cards.cardsProp;

import model.cards.cardsActions.Action;
import model.cards.cardsActions.magicActionChildren.*;
import model.cards.cardsEnum.Magic.MagicAttribute;
import model.cards.cardsEnum.Magic.MagicSpeed;
import model.cards.cardsEnum.Magic.MagicType;
import model.cards.cardsEnum.Monster.MonsterRace;
import model.enums.GameEnums.SideOfFeature;
import model.events.Event;
import model.events.eventChildren.ManuallyActivation;
import model.events.eventChildren.MonsterSummon;
import model.events.eventChildren.OpponentMonsterWantsToAttack;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;


public class MagicCard extends Card {
    private static ArrayList<MagicCard> magicCards;

    static {
        magicCards = new ArrayList<>();
    }

    private final ArrayList<Action> actionsOfMagic;
    private final ArrayList<Event> sideEvents;
    private final ArrayList<Event> triggers;
    private MagicSpeed magicSpeed;
    private MagicAttribute magicAttribute;
    private MagicType typeOfMagic;

    {
        actionsOfMagic = new ArrayList<>();
        sideEvents = new ArrayList<>();
        triggers = new ArrayList<>();
    }

    public MagicCard(String name, String typeOfMagic, String magicAttribute, String description, String speed, String price) {
        super(name, description, price);
        setTypeOfMagic(MagicType.setType(typeOfMagic));
        setMagicAttribute(MagicAttribute.setAttribute(magicAttribute));
        setMagicSpeed(MagicSpeed.setSpeed(speed));
        magicCards.add(this);
        setMagicEffect(name);
        setMagicEvents(name);
    }

    public MagicCard() {
        super();
    }

    public static void setMagicCards(ArrayList<MagicCard> magicCards) {
        MagicCard.magicCards = magicCards;
        Card.addMagicsToCards(magicCards);
    }

    public static MagicCard getMagicCardByName(String name) {
        for (MagicCard magicCard : magicCards) {
            if (magicCard.name.equals(name)) return magicCard;
        }
        return null;
    }

    private void setMagicEvents(String name) {
        if (name.equals("Monster Reborn")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Raigeki")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Harpie’s Feather Duster")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Dark Hole")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Mystical space typhoon")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Yami")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Forest")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Closed Forest")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Umiiruka")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Sword of Dark Destruction")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Black Pendant")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Mirror Force")) {
            triggers.add(OpponentMonsterWantsToAttack.getInstance());
        }
        if (name.equals("Mind Crush")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Torrential Tribute")) {
            triggers.add(MonsterSummon.getInstance());
        }
        if (name.equals("Call of the Haunted")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Change of Heart")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Time Seal")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Pot Of Greed")) {
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Terraforming")) {
            triggers.add(ManuallyActivation.getInstance());
        }
    }

    private void setMagicEffect(String name) {
        if (name.equals("Monster Reborn")) {
            actionsOfMagic.add(new SummonMonsterFromBothGraveYardsAction());
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Raigeki")) {
            actionsOfMagic.add(new DestroyAllOpponentMonsters());
        }
        if (name.equals("Harpie’s Feather Duster")) {
            actionsOfMagic.add(new DestroyAllOpponentMagics());
        }
        if (name.equals("Dark Hole")) {
            actionsOfMagic.add(new DestroyAllBoardMonsters());
        }
        if (name.equals("Mystical space typhoon")) {
            actionsOfMagic.add(new DestroyAMagicCard());
        }
        if (name.equals("Yami")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.FIEND.toString());
            types.add(MonsterRace.SPELLCASTER.toString());
            ArrayList<SideOfFeature> sideOfFeatures = new ArrayList<>();
            sideOfFeatures.add(SideOfFeature.CURRENT);
            sideOfFeatures.add(SideOfFeature.OPPONENT);
            actionsOfMagic.add(new ChangingMonsterAttackAction(200, types, 1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(200, types, 1, sideOfFeatures));
            types = new ArrayList<>();
            types.add(MonsterRace.FAIRY.toString());
            actionsOfMagic.add(new ChangingMonsterAttackAction(200, types, -1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(200, types, -1, sideOfFeatures));
        }
        if (name.equals("Forest")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.BEAST.toString());
            types.add(MonsterRace.INSECT.toString());
            types.add(MonsterRace.BEAST_WARRIOR.toString());
            ArrayList<SideOfFeature> sideOfFeatures = new ArrayList<>();
            sideOfFeatures.add(SideOfFeature.CURRENT);
            sideOfFeatures.add(SideOfFeature.OPPONENT);
            actionsOfMagic.add(new ChangingMonsterAttackAction(200, types, 1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(200, types, 1, sideOfFeatures));
        }
        if (name.equals("Closed Forest")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.BEAST.toString());
            types.add(MonsterRace.BEAST_WARRIOR.toString());
            ArrayList<SideOfFeature> sideOfFeaturesGraveyard = new ArrayList<>();
            sideOfFeaturesGraveyard.add(SideOfFeature.OPPONENT);
            sideOfFeaturesGraveyard.add(SideOfFeature.CURRENT);
            ArrayList<SideOfFeature> sideOfFeaturesChangeAttack = new ArrayList<>();
            sideOfFeaturesChangeAttack.add(SideOfFeature.CURRENT);
            actionsOfMagic.add(new ChangingMonsterAttackWithGraveyardsMonster(100, 1, types, sideOfFeaturesGraveyard, sideOfFeaturesChangeAttack));
        }
        if (name.equals("Umiiruka")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.AQUA.toString());
            ArrayList<SideOfFeature> sideOfFeatures = new ArrayList<>();
            sideOfFeatures.add(SideOfFeature.CURRENT);
            sideOfFeatures.add(SideOfFeature.OPPONENT);
            actionsOfMagic.add(new ChangingMonsterAttackAction(500, types, 1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(400, types, -1, sideOfFeatures));
        }
        if (name.equals("Sword of Dark Destruction")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.FIEND.toString());
            types.add(MonsterRace.SPELLCASTER.toString());
            actionsOfMagic.add(new ChangingSomeRaceEquipedMonsterAttack(400, 1, types));
            actionsOfMagic.add(new ChangingSomeRaceEquipedMonsterDefence(200, -1, types));
        }
        if (name.equals("Black Pendant")) {
            actionsOfMagic.add(new ChangingEquipedMonsterAttack(500, 1));
        }
        if (name.equals("Mirror Force")) {
            actionsOfMagic.add(new DestroyAllOpponentAttackingMonsters());
        }
        if (name.equals("Mind Crush")) {
            actionsOfMagic.add(new GuessingCardInOpponentHandAction());
        }
        if (name.equals("Torrential Tribute")) {
            actionsOfMagic.add(new DestroyAllBoardMonsters());
        }
        if (name.equals("Call of the Haunted")) {
            actionsOfMagic.add(new SummonMonsterFromOwnGraveYardAction());
        }
        if (name.equals("Change of Heart")) {
            actionsOfMagic.add(new ChangeTeamOfMonsterCard());
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Time Seal")) {
            actionsOfMagic.add(new AvoidOpponentsCardDraw(2));
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Pot Of Greed")) {
            actionsOfMagic.add(new DrawCardFromTopOfDeck(2));
            triggers.add(ManuallyActivation.getInstance());
        }
        if (name.equals("Terraforming")) {
            actionsOfMagic.add(new TerraformingAction());
            triggers.add(ManuallyActivation.getInstance());
        }
//        if (name.equals("Magic Jammer") || name.equals("Magic Cylinder"))
//            actionsOfMagic.add(StoppingActivationAction.getInstance());
//        if (name.equals("Advanced Ritual Art")) actionsOfMagic.add(RitualSummonAction.getInstance());
//        if (name.equals("Ring of Defense") || name.equals("Negate Attack"))
//            actionsOfMagic.add(MakeDamageZero.getInstance());
//        if (name.equals("Trap Hole") || name.equals("Twin Twisters"))
//            ;
//        if (name.equals("Magnum Shield") || name.equals("United We Stand"))
//            actionsOfMagic.add(ChangingMonsterAttackAction.getInstance());
//        if (name.equals("Spell Absorption")) actionsOfMagic.add(ChangingLifePointAction.getInstance());
//        if (name.equals("Magnum Shield") || name.equals("United We Stand") || name.equals("Sword of Dark Destruction") || name.equals("Umiiruka") || name.equals("Forest") || name.equals("Yami"))
//            actionsOfMagic.add(ChangeDefenceOfMonsterCard.getInstance());
//        if (name.equals("Negate Attack")) actionsOfMagic.add(EndBattlePhaseAction.getInstance());
    }

    @Override
    public void activeEffectsByEvent(Event event, Game game) {
        boolean shouldActiveEffects = false;
        for (Event trigger : triggers) {
            if (trigger.equals(event)) {
                shouldActiveEffects = true;
                break;
            }
        }
        System.out.println(shouldActiveEffects);
        if (shouldActiveEffects) {
            for (Action actionOfMagic : actionsOfMagic) {
                actionOfMagic.active(game);
            }
        }
    }

    @Override
    public String getCardDetail() {
        return
                "Name: " + name +
                        "\n" + typeOfMagic.toString() +
                        "\nType: " + magicAttribute +
                        "\nSpeed: " + magicSpeed.toString() +
                        "\nDescription: " + description;
    }

    public MagicAttribute getMagicAttribute() {
        return magicAttribute;
    }

    private void setMagicAttribute(MagicAttribute magicAttribute) {
        this.magicAttribute = magicAttribute;
    }

    public MagicType getTypeOfMagic() {
        return typeOfMagic;
    }

    private void setTypeOfMagic(MagicType typeOfMagic) {
        this.typeOfMagic = typeOfMagic;
    }

    public MagicSpeed getMagicSpeed() {
        return magicSpeed;
    }

    public void setMagicSpeed(MagicSpeed magicSpeed) {
        this.magicSpeed = magicSpeed;
    }

    public void setDetails(String name, String typeOfMagic, String magicAttribute, String description, String speed, String price) {
        setName(name);
        setTypeOfMagic(MagicType.setType(typeOfMagic));
        setMagicAttribute(MagicAttribute.setAttribute(magicAttribute));
        setMagicSpeed(MagicSpeed.setSpeed(speed));
        setDescription(description);
        setPrice(price);
        setMagicEffect(name);
//        setMagicEvents(name);
    }

    public ArrayList<Action> getActionsOfMagic() {
        return actionsOfMagic;
    }

    @Override
    public Card getCopy() {
        MagicCard copy = new MagicCard();

        // semi duplicate block:
        copy.name = this.name;
        copy.description = this.description;
        copy.price = this.price;
        copy.ID = this.ID;
        //

        copy.setMagicSpeed(this.magicSpeed);
        copy.setTypeOfMagic(this.typeOfMagic);
        copy.setMagicAttribute(this.magicAttribute);

        return copy;
    }
}
