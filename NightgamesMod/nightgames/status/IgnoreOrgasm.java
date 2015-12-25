package nightgames.status;

import org.json.simple.JSONObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.global.JSONUtils;

public class IgnoreOrgasm extends DurationStatus {
	public IgnoreOrgasm(Character affected, int duration) {
		super("Orgasm Ignored", affected, duration);
		flag(Stsflag.orgasmseal);
	}

	@Override
	public String initialMessage(Combat c, boolean replaced) {
		if (affected.getArousal().isFull()) {
			return affected.subjectAction("are", "is")
					+ " overpowering the urge to cum";
		}
		return "";
	}

	@Override
	public String describe(Combat c) {
		if (affected.getArousal().isFull()) {
			return affected.subjectAction("are", "is")
					+ " overpowering the urge to cum";
		}
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
	public double pleasure(Combat c, double x) {
		if (affected.getArousal().isFull()) {
			return -x * 9 / 10;
		}
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
	public String toString() {
		return "Orgasm Ignored";
	}

	@Override
	public int value() {
		return 0;
	}

	@Override
	public Status instance(Character newAffected, Character newOther) {
		return new IgnoreOrgasm(newAffected, getDuration());
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject saveToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", getClass().getSimpleName());
		obj.put("duration", getDuration());
		return obj;
	}

	@Override
	public Status loadFromJSON(JSONObject obj) {
		return new IgnoreOrgasm(null, JSONUtils.readInteger(obj, "duration"));
	}
}
