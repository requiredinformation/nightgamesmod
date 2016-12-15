package nightgames.status.addiction;

import java.util.Optional;

import javax.naming.OperationNotSupportedException;

import com.google.gson.JsonObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Player;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;
import nightgames.global.Global;
import nightgames.stance.Behind;
import nightgames.stance.Mount;
import nightgames.stance.Position;
import nightgames.stance.Stance;
import nightgames.status.CrisisOfFaith;
import nightgames.status.DivineCharge;
import nightgames.status.Status;

public class ZealAddiction extends Addiction {

    private boolean shouldApplyDivineCharge;
    
    public ZealAddiction(Player affected, Character cause, float magnitude) {
        super(affected, "Zeal", cause, magnitude);
        shouldApplyDivineCharge = false;
    }

    public ZealAddiction(Player affected, Character cause) {
        this(affected, cause, .01f);
    }

    @Override
    protected Optional<Status> withdrawalEffects() {
        return Optional.of(new CrisisOfFaith(Global.getPlayer()));
    }

    @Override
    protected Optional<Status> addictionEffects() {
        return Optional.of(this);
    }
    
    @Override
    public Optional<Status> startNight() {
        Optional<Status> s = super.startNight();
        if (!inWithdrawal && isActive()) {
            shouldApplyDivineCharge = true;
        }
        return s;
    }
    
    @Override
    public void endNight() {
        super.endNight();
        shouldApplyDivineCharge = false;
    }
    
    @Override
    public Optional<Status> startCombat(Combat c, Character opp) {
        Optional<Status> s = super.startCombat(c, opp);
        if (shouldApplyDivineCharge && opp.equals(cause)) {
            int sev = getSeverity().ordinal();
            opp.status.add(new DivineCharge(opp, sev * .75f));
        }
        return s;
    }

    @Override
    public void tick(Combat c) {
        super.tick(c);
        if ((c.getStance().en == Stance.neutral || c.getStance().en == Stance.behind)
                        && Global.randomdouble() < Math.min(.5f, combatMagnitude / 2.0)) {
            c.write(Global.getPlayer(), "Overcome by your desire to serve " + cause.name() + ", you get on the ground "
                            + "and prostrate yourself in front of " + cause.directObject() + ".");
            boolean behindPossible = cause.hasDick();
            Position pos;
            if (!behindPossible || Global.random(2) == 0) {
                pos = new Mount(cause, affected);
                c.write(cause, String.format(
                                "%s tells you to roll over, and once you have done so %s sets"
                                                + " %s down on your stomach.",
                                cause.name(), cause.pronoun(), cause.reflectivePronoun()));
            } else {
                pos = new Behind(cause, affected);
                c.write(cause, String.format("%s motions for you to get up and then casually walks around you"
                                + ", grabbing you from behind.", cause.name()));
            }
            c.setStance(pos);
        }
    }

    @Override
    protected String describeIncrease() {
        switch (getSeverity()) {
            case HIGH:
                return cause.name()
                                + " demands worship! The holy aura only reinforces your faith and your desire to serve!";
            case LOW:
                return cause.name() + " shines brightly as you cum inside of " + cause.directObject() + ". "
                                + "Maybe there is something to this whole divinity spiel?";
            case MED:
                return "You are now convinced " + cause.name()
                                + " is a higher power, and you feel a need to serve " + cause.directObject() + " accordingly.";
            case NONE:
            default:
                return ""; // hide
        }
    }

    @Override
    protected String describeDecrease() {
        switch (getSeverity()) {
            case LOW:
                return "Your faith is shaking, is " + cause.name() + " really so divine?";
            case MED:
                return "Your faith in " + cause.name() + " has wavered a bit, but " + cause.pronoun() + "'s still"
                                + " your goddess! Right?";
            case NONE:
                return "You don't konw what possessed you before, but you no longer see " + cause.name()
                                + " as <i>actually</i> divine.";
            case HIGH:
            default:
                return ""; // hide
        }
    }

    @Override
    protected String describeWithdrawal() {
        switch (getSeverity()) {
            case HIGH:
                return "<b>Your mind is completely preoccupied by " + cause.name() + ". You didn't worship today!"
                                + " Will " + cause.directObject() + " be angry? What will you do if " + cause.pronoun()
                                + " is? You aren't going to be able to focus on much else tonight.</b>";
            case LOW:
                return "<b>You didn't pay your respects to " + cause.name() + " today... Is that bad? Or isn't it?"
                                + " You are confused, and will have less mojo tonight.</b>";
            case MED:
                return "<b>You are terribly nervous at the thought of having to face " + cause.name()
                                + " tonight after failing to pray to " + cause.directObject() + " today. The rampaging"
                                + " thoughts are throwing you off your game.</b>";
            case NONE:
                throw new IllegalStateException("Tried to describe withdrawal for an inactive zeal addiction.");
            default:
                return ""; // hide
        }
    }

    @Override
    protected String describeCombatIncrease() {
        return "You feel an increasingly strong desire to do whatever " + cause.name()
                        + " wants. " + Global.capitalizeFirstLetter(cause.pronoun()) + "'s a goddess, after all!";
    }

    @Override
    protected String describeCombatDecrease() {
        return "Doing " + cause.name() + "'s bidding clears your mind a bit. Why are you really doing this?"
                        + " One look at her reaffirms " + cause.directObject() + " divinity in your mind, though.";
    }

    @Override
    public String describeMorning() {
        return "An image of " + cause.name() + " in " + cause.directObject() + " full angelic splendor is fixed in your"
                        + " mind as you get up. A growing part of you wants to pray to this new deity; to worship "
                        + cause.directObject() + " and support you in the day to come.";
    }

    @Override
    public AddictionType getType() {
        return AddictionType.ZEAL;
    }

    @Override
    public String initialMessage(Combat c, boolean replaced) {
        if (inWithdrawal) {
            return "You tremble as " + cause.name()
                            + " steps into view. Will " + cause.pronoun() + " punish you for not being pious enough?"
                            + " Perhaps you should beg forgiveness...";
        }
        return cause.pronoun() + "'s here! The holy glow reveals " + cause.directObject() + " presence even before you"
                        + " see " + cause.directObject() + ", and you nearly drop to your knees where you are.";
    }

    @Override
    public String describe(Combat c) {
        switch (getCombatSeverity()) {
            case HIGH:
                return "Your knees tremble with your desire to offer yourself to your goddess.";
            case LOW:
                return cause.name() + " divine presence makes you wonder whether you should really be fighting "
                        + cause.directObject() + ".";
            case MED:
                return "A part of you is screaming to kneel before " + cause.name()
                                + ". Perhaps it's better to just give in?";
            case NONE:
            default:
                return "";

        }
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
        return -3;
    }

    @Override
    public Status instance(Character newAffected, Character newOther) {
        return new ZealAddiction((Player)newAffected, newOther, magnitude);
    }

    @Override public Status loadFromJson(JsonObject obj) {
        throw new RuntimeException(new OperationNotSupportedException("not supported yet"));
    }

    @Override
    public String informantsOverview() {
        return "You WHAT?! You actually think " + cause.getName() + " is divine? As in god-like? Are you shitting me?"
                        + " Man, even I don't know where " + cause.pronoun() + " got that pussy from, but it's certainly done"
                        + " a number on you. Well, I suppose that if you're busy worshipping " + cause.directObject() + ", you'll"
                        + " be assuming some submissive positions in the process. That can't help your chances"
                        + " in a fight. And if you're really serious about this, the soul-searching required to"
                        + " get rid of it will probably throw you off your game for a while. Not good, man. I"
                        + " suppose you can draw some spiritual strength from your newfound faith, many people"
                        + " seem to do so, but is that worth it?";
    }

}
