package nightgames.status;

import com.google.gson.JsonObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Player;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;
import nightgames.status.addiction.Addiction;
import nightgames.status.addiction.AddictionType;

public class CrisisOfFaith extends Status {

    public CrisisOfFaith(Character affected) {
        super("Crisis of Faith", affected);
        assert affected == null || affected.human();
        flag(Stsflag.debuff);
    }

    @Override
    public String initialMessage(Combat c, boolean replaced) {
        return ""; // explanation given in withdrawal message
    }

    @Override
    public boolean lingering() {
        return true;
    }
    
    @Override
    public String describe(Combat c) {
        return "You are deeply disturbed by the doubt in your heart, limiting mojo gain.";
    }

    @Override
    public int mod(Attribute a) {
        return 0;
    }

    @Override
    public int regen(Combat c) {
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
        return (int) (x * (1.0f - ((Player)affected).getAddiction(AddictionType.ZEAL).map(Addiction::getMagnitude)
                        .orElse(0f)));
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
        return -5;
    }

    @Override
    public Status instance(Character newAffected, Character newOther) {
        return new CrisisOfFaith(newAffected);
    }

     @Override public JsonObject saveToJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", getClass().getSimpleName());
        return obj;
    }

    @Override public Status loadFromJson(JsonObject obj) {
        return new CrisisOfFaith(null);
    }

}
