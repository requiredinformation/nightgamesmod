package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.nskills.tags.SkillTag;
import nightgames.status.Alluring;
import nightgames.status.Stsflag;

public class StripTease extends Skill {
    public StripTease(Character self) {
        super("Strip Tease", self);
        addTag(SkillTag.undressing);
    }

    public StripTease(String string, Character self) {
        super(string, self);
    }

    public static boolean hasRequirements(Character user) {
        return user.get(Attribute.Seduction) >= 24 && !user.has(Trait.direct) && !user.has(Trait.shy)
                        && !user.has(Trait.temptress);
    }

    public static boolean isUsable(Combat c, Character self, Character target) {
        return self.stripDifficulty(target) == 0 && !self.has(Trait.strapped) && self.canAct() && c.getStance()
                                                                                                   .mobile(self)
                        && !self.mostlyNude() && !c.getStance()
                                                   .prone(self)
                        && c.getStance()
                            .front(self)
                        && (!self.breastsAvailable() || !self.crotchAvailable());
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return hasRequirements(user);
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return isUsable(c, getSelf(), target);
    }

    @Override
    public int getMojoBuilt(Combat c) {
        return 30;
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        if (getSelf().human()) {
            c.write(getSelf(), deal(c, 0, Result.normal, target));
        } else if (c.shouldPrintReceive(target, c)) {
            if (target.human() && target.is(Stsflag.blinded))
                printBlinded(c);
            else
                c.write(getSelf(), receive(c, 0, Result.normal, target));
        }
        if (!target.is(Stsflag.blinded)) {
            int m = 15 + Global.random(5);
            target.temptNoSource(c, getSelf(), m, this);
            getSelf().add(c, new Alluring(getSelf(), 5));
        }
        target.emote(Emotion.horny, 30);
        getSelf().undress(c);
        getSelf().emote(Emotion.confident, 15);
        getSelf().emote(Emotion.dominant, 15);
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new StripTease(user);
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.pleasure;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "During a brief respite in the fight as " + target.name()
                        + " is catching her breath, you make a show of seductively removing your clothes. "
                        + "By the time you finish, she's staring with undisguised arousal, pressing a hand unconsciously against her groin.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        return String.format("%s asks for a quick time out and starts sexily slipping %s own clothes off."
                        + " Although there are no time outs in the rules, %s can't help staring "
                        + "at the seductive display until %s finishes with a cute wiggle of %s naked ass.",
                        getSelf().subject(), getSelf().possessivePronoun(), target.subject(),
                        getSelf().pronoun(), getSelf().possessivePronoun());
    }

    @Override
    public String describe(Combat c) {
        return "Tempt opponent by removing your clothes";
    }

}
