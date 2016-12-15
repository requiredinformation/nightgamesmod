package nightgames.status;

import com.google.gson.JsonObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;
import nightgames.global.Global;

public class Channeling extends DurationStatus {
    public Channeling(Character affected) {
        super("Channeling", affected, 1);
        flag(Stsflag.channeling);
        flag(Stsflag.purgable);
    }

    public Channeling(Character affected, int duration) {
        this(affected);
        super.setDuration(duration);
    }

    @Override
    public String initialMessage(Combat c, boolean replaced) {
        if (affected.human()) {
            return "You channel your energy into your next spell.";
        } else {
            return Global.format("{self:NAME} " +
                                 "{self:SUBJECT-ACTION:channel|channels} " +
                                 "{self:possesive} energy into " +
                                 "{self:possesive} next spell.",
                                 affected, c.getOpponent(affected)); 
        }
    }

    @Override
    public String describe(Combat c) {
        return "";
    }

    @Override
    public float fitnessModifier() {
        return 0;
    }

    @Override
    public int mod(Attribute a) {
        return 0;
    }

    @Override
    public int damage(Combat c, int x) {
        return 0;
    }

    @Override
    public double pleasure(Combat c, BodyPart withPart, BodyPart targetPart, double x) {
        return 0;
    }

    @Override
    public int weakened(int x) {
        return 0;
    }

    @Override
    public int tempted(int x) {
        return 0;
    }

    @Override
    public int evade() {
        return 0;
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
        return 0;
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Status instance(Character newAffected, Character newOther) {
        return new Channeling(newAffected);
    }

    @Override  public JsonObject saveToJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", getClass().getSimpleName());
        return obj;
    }

    @Override public Status loadFromJson(JsonObject obj) {
        return new Channeling(null);
   }

}
