package nightgames.status;

import com.google.gson.JsonObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;

/**
 * Kind of like a mini-winded.
 */
public class Stunned extends DurationStatus {
    public Stunned(Character affected) {
        this(affected, 1);
    }

    public Stunned(Character affected, int duration) {
        super("Stunned", affected, duration);
        flag(Stsflag.stunned);
        flag(Stsflag.debuff);
        flag(Stsflag.purgable);
        flag(Stsflag.disabling);
    }

    @Override
    public String describe(Combat c) {
        if (affected.human()) {
            return "You were knocked off your feet!";
        } else {
            return affected.name() + " was knocked off " + affected.possessivePronoun() + " feet!";
        }
    }

    @Override
    public String initialMessage(Combat c, boolean replaced) {
        return String.format("%s now stunned.\n", affected.subjectAction("are", "is"));
    }

    @Override
    public float fitnessModifier() {
        return -.8f;
    }

    @Override
    public int mod(Attribute a) {
        if (a == Attribute.Power || a == Attribute.Speed) {
            return -2;
        } else {
            return 0;
        }
    }

    @Override
    public void onRemove(Combat c, Character other) {
        if (affected.get(Attribute.Divinity) > 0) {
            affected.addlist.add(new BastionOfFaith(affected, 3));
        } else {
            affected.addlist.add(new Braced(affected, 2));
        }
        affected.addlist.add(new Wary(affected, 2));
        affected.heal(c, affected.getStamina().max() / 3, " (Recovered)");
    }

    @Override
    public int regen(Combat c) {
        super.regen(c);
        affected.emote(Emotion.nervous, 15);
        affected.emote(Emotion.angry, 10);
        return 0;
    }

    @Override
    public int damage(Combat c, int x) {
        return -x;
    }

    @Override
    public double pleasure(Combat c, BodyPart withPart, BodyPart targetPart, double x) {
        return -x / 2;
    }

    @Override
    public int weakened(int x) {
        return -x;
    }

    @Override
    public int tempted(int x) {
        return -x;
    }

    @Override
    public int evade() {
        return -10;
    }

    @Override
    public int escape() {
        return 0;
    }

    @Override
    public int gainmojo(int x) {
        return 0;
    }

    @Override
    public int spendmojo(int x) {
        return 0;
    }

    @Override
    public int counter() {
        return -10;
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Status instance(Character newAffected, Character newOther) {
        return new Stunned(newAffected);
    }

    @Override public JsonObject saveToJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", getClass().getSimpleName());
        return obj;
    }

    @Override public Status loadFromJson(JsonObject obj) {
        //Winded constructor can't handle nulls
        throw new UnsupportedOperationException();
        //return new Winded(null);
    }
}
