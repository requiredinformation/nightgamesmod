package nightgames.trap;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.global.Global;
import nightgames.items.Item;

public class Alarm extends Trap {
    
    public Alarm() {
        this(null);
    }
    
    public Alarm(Character owner) {
        super("Alarm", owner);
    }

    @Override
    public void trigger(Character target) {
        if (target.human()) {
            Global.gui().message(
                            "You're walking through the eerily quiet campus, when a loud beeping almost makes you jump out of your skin. You realize the beeping is "
                                            + "coming from a cell phone on the floor. You shut it off as quickly as you can, but it's likely everyone nearby heard it already.");
        } else if (target.location().humanPresent()) {
            Global.gui().message(target.name() + " Sets off your alarm, giving away her presence.");
        }
        target.location().alarm = true;
        target.location().remove(this);
    }

    @Override
    public boolean decoy() {
        return true;
    }

    @Override
    public boolean recipe(Character user) {
        return user.has(Item.Tripwire) && user.has(Item.Phone);
    }

    @Override
    public String setup(Character user) {
        owner = user;
        owner.consume(Item.Tripwire, 1);
        owner.consume(Item.Phone, 1);
        if (user.human()) {
            return "You rig up a disposable phone to a tripwire. When someone trips the wire, it should set of the phone's alarm.";
        } else {
            return "";
        }
    }

    @Override
    public boolean requirements(Character owner) {
        return owner.get(Attribute.Cunning) >= 6;
    }

}
