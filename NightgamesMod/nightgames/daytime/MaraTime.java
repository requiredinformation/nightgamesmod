package nightgames.daytime;

import java.util.ArrayList;
import java.util.Optional;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Trait;
import nightgames.characters.body.BasicCockPart;
import nightgames.characters.body.BodyPart;
import nightgames.characters.body.CockMod;
import nightgames.characters.body.CockPart;
import nightgames.characters.body.ModdedCockPart;
import nightgames.characters.body.PussyPart;
import nightgames.global.Global;
import nightgames.items.Item;
import nightgames.requirements.BodyPartRequirement;
import nightgames.requirements.NotRequirement;
import nightgames.requirements.RequirementShortcuts;
import nightgames.status.addiction.Addiction;
import nightgames.status.addiction.AddictionType;

public class MaraTime extends BaseNPCTime {
    public MaraTime(Character player) {
        super(player, Global.getNPC("Mara"));
        knownFlag = "MaraKnown";
        giftedString = "\"Awww thanks!\"";
        giftString = "\"A present? You shouldn't have!\"";
        transformationOptionString = "Modifications";
        advTrait = Trait.madscientist;
        transformationIntro =
                        "[Placeholder]<br/>Mara explains that she may be able to modify your body with her new equipment.";
        loveIntro = "Mara's computer lab has once again become a clusterfuck of electronics equipment. Not just electronics either. The back corner houses a "
                        + "small chemical lab, and there are welding and metal cutting tools probably borrowed from the mechanical engineering building. You can only identify "
                        + "about 20%% of the junk in here. It looks like some mad scientist's laboratory.<br/><br/>Mara notices your presence and jumps up eagerly from her workstation to greet you. <i>\"Are you here to have some fun? I've "
                        + "got a couple of toys I've been working on that I'd like your help to test before tonight's match. My classwork has gotten really easy, so I've had a lot "
                        + "of free time to develop prototype tools and traps. Well, technically the classwork hasn't actually changed very much, I guess I just never realized how... "
                        + "basic it all was.\"</i> She can't seem to sit still while she's talking. She keeps fiddling with half completed electronics devices. She doesn't seem sleep "
                        + "deprived so much as manic. Has she taken something? <i>\"I came up with a new energy drink; perfectly safe and legal. Simple formula too, I have no idea why "
                        + "it isn't being produced already.\"</i> You hug Mara close to stop her from moving around. Didn't she promise to reduce her workload and pay attention to her "
                        + "health? She's dived deeper into her work than ever and is keeping herself awake with experimental chemistry. If she keeps this up, she's going to have a "
                        + "heart attack before her 20th birthday. <i>\"I..\"</i> She goes quiet and looks down guiltily. Eventually she wraps her arms around you and buries her face in your "
                        + "chest. <i>\"I guess I'm addicted to the challenge. I didn't mean to worry you.\"</i> She raises her head and kisses you softly. <i>\"I can forget all my work when we're "
                        + "together and I always sleep well after we make love. I'm counting on you to save me from myself.\"</i>";
        transformationFlag = "";
    }

    @Override
    public void buildTransformationPool() {
        options = new ArrayList<>();
        {
            TransformationOption growCock = new TransformationOption();
            growCock.ingredients.put(Item.Dildo, 1);
            growCock.requirements.add(RequirementShortcuts.rev(new NotRequirement(new BodyPartRequirement("cock"))));
            growCock.additionalRequirements = "";
            growCock.option = "Mara: Install a cock";
            growCock.scene = "[Placeholder]<br/>Mara makes some modifications to the dildo and manages to attach it to her own body through methods unknown to you.";
            growCock.effect = (c, self, other) -> {
                other.body.add(new ModdedCockPart(BasicCockPart.big, CockMod.bionic));
                return true;
            };
            options.add(growCock);
        }
        {
            TransformationOption removeCock = new TransformationOption();
            removeCock.ingredients.put(Item.DisSol, 3);
            removeCock.requirements.add(RequirementShortcuts.rev(new BodyPartRequirement("cock")));
            removeCock.additionalRequirements = "";
            removeCock.option = "Mara: Remove her cock";
            removeCock.scene = "[Placeholder]<br/>Mara uses the solvent to dissolve the bond from her modified cock, making her wholly female again.";
            removeCock.effect = (c, self, other) -> {
                other.body.removeAll("cock");
                return true;
            };
            options.add(removeCock);
        }
        TransformationOption bionicCock = new TransformationOption();
        bionicCock.ingredients.put(Item.PriapusDraft, 10);
        bionicCock.ingredients.put(Item.TinkersMix, 20);
        bionicCock.ingredients.put(Item.Lubricant, 5);
        bionicCock.ingredients.put(Item.Spring, 5);
        bionicCock.ingredients.put(Item.Dildo, 1);
        bionicCock.requirements.add(new BodyPartRequirement("cock"));
        bionicCock.requirements.add((c, self, other) -> {
            return self.body.get("cock")
                            .stream()
                            .anyMatch(cock -> ((CockPart) cock).isGeneric(self));
        });
        bionicCock.additionalRequirements = "A normal cock";
        bionicCock.option = "Bionic Cock";
        bionicCock.scene = "[Placeholder]<br/>Mara installs a bionic cock on you";
        bionicCock.effect = (c, self, other) -> {
            Optional<BodyPart> optPart = self.body.get("cock")
                                                  .stream()
                                                  .filter(cock -> ((CockPart) cock).isGeneric(self))
                                                  .findAny();
            BasicCockPart target = (BasicCockPart) optPart.get();
            self.body.remove(target);
            self.body.add(new ModdedCockPart(target, CockMod.bionic));
            return true;
        };
        options.add(bionicCock);
        TransformationOption cyberneticPussy = new TransformationOption();
        cyberneticPussy.ingredients.put(Item.TinkersMix, 20);
        cyberneticPussy.ingredients.put(Item.Lubricant, 5);
        cyberneticPussy.ingredients.put(Item.Spring, 5);
        cyberneticPussy.ingredients.put(Item.Onahole, 1);
        cyberneticPussy.requirements.add(new BodyPartRequirement("pussy"));
        cyberneticPussy.requirements.add((c, self, other) -> {
            return self.body.get("pussy")
                            .stream()
                            .anyMatch(part -> part == PussyPart.normal);
        });
        cyberneticPussy.additionalRequirements = "A normal pussy";
        cyberneticPussy.option = "Cybernetic Pussy";
        cyberneticPussy.scene = "[Placeholder]<br/>Mara installs a cybernetic pussy on you";
        cyberneticPussy.effect = (c, self, other) -> {
            self.body.addReplace(PussyPart.cybernetic, 1);
            return true;
        };
        options.add(cyberneticPussy);
    }

    @Override
    public void subVisitIntro(String choice) {
        if (npc.getAffection(player) > 0) {
            Global.gui()
                  .message("You go to the computer lab to find Mara for some quality time. She immediately breaks into a smile when she sees you enter. You can tell "
                                  + "at a glance that she's better rested than the first time you came in here. It's not just her that's changed. The room is still a mess of electronics, but "
                                  + "a significant portion of the clutter is gone. You can reasonably walk across the room without getting tangled in anything. <i>\"I cut back on the number of "
                                  + "concurrent projects I'm working on,\"</i> she explains when she notices you looking around. <i>\"I've always had the habit of finding stuff to do in addition to "
                                  + "my classwork, but I guess I may have gotten a little over my head there.\"</i> She walks over to you and smiles up playfully. <i>\"I'm taking care of myself just "
                                  + "like you asked, now it's your turn to keep up your part of the deal. How are you going to entertain me today?\"</i>");
            Global.gui()
                  .choose(this, "Games");
            Global.gui()
                  .choose(this, "Sparring");
            Global.gui()
                  .choose(this, "Sex");
            if(player.getPure(Attribute.Arcane)>=3){
                Global.gui().choose(this,"Faerie play");
            }
            if (Global.getPlayer().checkAddiction(AddictionType.MIND_CONTROL)) {
                Global.gui().choose(this, "Confront about control");
            }
        } else if (Global.getPlayer().checkAddiction(AddictionType.MIND_CONTROL)) {
            Global.gui()
            .message("Mara low-affection addiction intro");
            if (npc.getAttraction(player) < 15) {
                npc.gainAttraction(player, 2); 
                player.gainAttraction(npc, 2);
            } else {
                npc.gainAffection(player, 1);
                player.gainAffection(npc, 1);
                Global.gui() .choose(this, "Games");
                Global.gui().choose(this, "Sparring");
                Global.gui().choose(this, "Sex");
            }
            Global.gui().choose(this, "Confront about control");
        } else if (npc.getAttraction(player) < 15) {
            Global.gui()
                  .message("You eventually find Mara in one of the computer labs, or at least a room labeled Computer Lab D. You typically think of a computer "
                                  + "lab as having rows of fully functional computers and enough clear floorspace to walk through. Most of the computers here are only half assembled and "
                                  + "every surface is covered with assorted electronics. Mara is the only one in the small room, unless someone is buried in the PLCs and cords. She's focused "
                                  + "intensely on her work and doesn't even notice you until you call out to her.<br/><br/><i>\"Oh, hi "
                                  + player.name() + ".\"</i> She glances up at you, but continues typing "
                                  + "on one of the keyboards in front of her. <i>\"How are you doing?\"</i> You ask her if she'd like to take a break and hang out for a while.<br/><i>\"Sounds fun, but I really "
                                  + "need to finish this tonight and it's not being cooperative.\"</i> She lets out a long yawn and picks up a nearby energy drink, but tosses it away when she realizes it's "
                                  + "empty. <i>\"I'm probably going to be stuck here right up to the start of tonight's match. If you could do me a favor and get me something with some caffeine, "
                                  + "I'll love you forever.\"</i><br/><br/>You find a vending machine not too far from the lab and buy an energy drink. When you return to Mara, she doesn't acknowledge your "
                                  + "presence. It doesn't seem like she's trying to be rude, but she's so consumed with her work that she's probably forgotten you were ever here. Deciding not to interrupt "
                                  + "her again, you set the drink near her. For the first time, you notice the dark bags under her eyes. You worry that she's pushing herself too hard.");
            npc.gainAttraction(player, 2);
            player.gainAttraction(npc, 2);
        } else {
            Global.gui()
                  .message("You head to the computer lab to see Mara. She jumps in surprise when the door opens, but relaxes when she sees who you are. <i>\"Hi "
                                  + player.name() + ". "
                                  + "I'm in the middle of something I can't really put down, but if you won't be bored, I'd enjoy some company.\"</i> Mara clears the junk off a chair behind her. You make "
                                  + "your way through the tangle of cords and sit down behind her. The two of you engage in some chat about nothing in particular, and you're impressed by her "
                                  + "ability to hold a conversation while writing code at an astonishing speed.<br/><br/>Eventually curiosity gets the better of you and you have to ask why she was so jumpy "
                                  + "when you came in. <i>\"I thought you were my advisor. He doesn't want me working in here for awhile.\"</i> According to the other CE majors, Mara is the only one who uses "
                                  + "this lab regularly. Why is her advisor trying to kick her out? <i>\"It's not like that. I kinda... nodded off in here the other day and I must have hit the corner of "
                                  + "one of the machines on the way down.\"</i> She touches a spot on her head, but you can't see if there's any mark there through her hair. <i>\"It was just a shallow cut, but "
                                  + "it must have looked pretty bad when he found me.\"</i><br/><br/>No wonder her advisor wants her to stop working. If she collapsed from exhaustion, that's a sign that she needs to "
                                  + "pay more attention to her health! <i>\"I'm committed to too many deadlines to slow down now. I've made it through worse schedules, I'll live.\"</i> You can't just let her continue "
                                  + "like this. She's taking her welfare way too lightly. You grab her hand to stop her from typing and demand that she take some time off or you're going to drag her out of here!<br/><br/>"
                                  + "She stares at you stunned for a few long seconds before looking away. <i>\"You're worried about me too?\"</i> she whispers while gently squeezing your hand. In the silence that follows, "
                                  + "she seems very small and fragile. Suddenly she smiles and meets your eyes as if nothing had happened. <i>\"Let's make a deal: I'll let you distract me from my projects. As long as "
                                  + "we're having fun, I won't think about anything but you.\"</i> Basically she'll only take a break while you're hanging out together. It's better than nothing.");
            npc.gainAffection(player, 1);
            player.gainAffection(npc, 1);
            Global.gui()
                  .choose(this, "Games");
            Global.gui()
                  .choose(this, "Sparring");
            Global.gui()
                  .choose(this, "Sex");
        }
        Global.gui()
              .choose(this, "Leave");
    }

    @Override
    public void subVisit(String choice) {
        if (choice.equals("Confront about control")) {
            if (npc.getAffection(player) == 0) {
                Global.gui().message("Mara has some fun making you do embarassing things in public, and then takes"
                                + " you home so you can put on a good show of jacking off for her. At the end, "
                                + "she reinforces her control over you. This is a placeholder.");
            } else {
                Global.gui()
                      .message("<i>\"Haha, yes! Great isn't it? Hands up!\"</i> You try to resist, "
                                      + "but this close and this direct it's no use. You let her know you're not happy about all this. <i>\"He, "
                                      + "sorry, I got a little carried away. You can put them down.\"</i> You do so and open your mouth to speak"
                                      + " again. <i>\"Kneel! Now stand back up! Jump! Oh, god, this is just too funny! Sorry, sorry, I'll "
                                      + "actually stop this time. You were saying?\"</i> Slightly exasperatedly, you complain about how she"
                                      + " shouldn't be trying to control you outside of matches, and that it's really quite unfair to begin with."
                                      + " Mara ponders this for a while. <i>\"Hmmm. I get what you're saying, but it's not like I can just turn "
                                      + "it off. Besides, these contacts are still in a beta phase and I can't very well go fiddling with them. "
                                      + "Nope, you'll just have to deal with it.\"</i> Well, getting her to stop altogether was a bit too much "
                                      + "to hope, but at least she can leave you alone during the day, right? <i>\"Well no. You see, this stuff"
                                      + " wears off after a while, and I can't have that. So, I guess I'm just going to have to give you some "
                                      + "orgasms during the day as well. That's not too bad, is it?\"</i> Normally, it would not be. Mara is "
                                      + "usually pretty nice. This control thing is a bit weird, though, so you refuse. You'll just have to deal "
                                      + "with the strain for a while. <i>\"Sorry, I can't have that. I promise, I'll make it really good. If you "
                                      + "want me to stop afterwards, that's fine, but I'm getting this one try. Follow me! And keep quiet!\"</i>"
                                      + " With your enraged rant preempted, there is nothing you can do but ride along as your body involuntarily"
                                      + " strolls after Mara.\n\nAfter a short walk, you end up at Mara's apartment. Every inch of the walls is"
                                      + " covered in designs and electrical layouts. You recognize some sketches of the device she uses in the "
                                      + "matches, and one of them looks suspiciously like a contact lens, but most of it is pure gibberish to "
                                      + "you. <i>\"Okay, so, now... Just... Strip and sit in that chair over there.\"</i> Still mute, you do"
                                      + " as she says. Once you've seated yourself, Mara walks over and kneels down in front of you. She looks"
                                      + " at your limp cock, and a smirk forms on her face. <i>\"Let's see if this works: Get hard.\"</i> You"
                                      + " want to tell her that's not something " + Global.getPlayer().guyOrGirl() + "s can really control like that, but inexplicably you feel "
                                      + "the familiar sensation of blood rushing into your dick. Within seconds, it's as hard as it's ever been."
                                      + " <i>\"Oooh, that's going to be useful at night! Now, until I say otherwise, don't cum!\"</i> You do "
                                      + "<i>not</i> like the sound of that, but it's not as if you can actually do anything about it. You are "
                                      + "going to have some serious words with her once she lets you speak again. Mara walks away into another room"
                                      + " briefly and when she comes back, that strange device she uses at night is strapped to one arm, and she's"
                                      + " holding something else in the other. It looks suspiciously like a fleshlight, but it has a weird attachment"
                                      + " at one end. <i>\"I just figured, since I'm kinda raping you anyway, I might as well get a little testing in."
                                      + " This thing is not </i>entirely<i> legal, but you'll keep quiet, right? Oh, right. So just let me know how it "
                                      + "feels.\"</i> The weird attatchment seems to be a connector, as she plugs it into her device. Mara brandishes her"
                                      + " new toy proudly, then grabs a bottle of lube and applies it to the squishy-looking orifice. You must look like a"
                                      + " deer in the headlights faced with that thing, and Mara thrives on it. She slowly pushes it onto your cock. Oh, this"
                                      + " is going to be bad. The inside feels soft and warm and it's covered in ridges and filaments. When you bottom out"
                                      + " - it <i>coincidentally</i> is the perfect size for you - a suction cap at the end engages over your dickhead and"
                                      + " the rest of it contracts around your shaft. Even if you could move, you would have a hard time getting this thing"
                                      + " off, now. <i>\"Are you ready? Remember, you can't cum until I say you can!\"</i> She pushes a button on her"
                                      + " arm-device, and the fleshlight starts to slowly rotate. The bumps and ridges feel exquisite against your skin,"
                                      + " and it only gets better as the device slowly accelerates. When it has reached a moderate speed, Mara flicks another"
                                      + " switch, and the thing starts undulating around your cock. The movements are very much like it's trying to milk you,"
                                      + " and it probably is. You are trembling with pleasure, and Mara just keeps making small adjustments to guage your"
                                      + " response - more suction, tighter grip. You'd have cum in under a minute, but Mara's command keeps you on the edge"
                                      + " until she's finally finished her experiments after ten minutes. She disengages this modern torture device and your"
                                      + " dick comes back into view - red as a lobster. <i>\"Oh, no! Why didn't you say something? No, wait, sorry. If it's"
                                      + " any consolation, this is going to be one hell of an orgasm!\"</i> She kneels down and slowly licks your painfully"
                                      + " hard shaft. <i>\"It's okay. Just look at me. You can cum now.\"</i> She quickly latches her lips onto you, and not"
                                      + " a moment too soon. Your cum feels like it is rising up from your toes and fingers, and your entire body is convulsing"
                                      + " is ecstacy. Your eyes remain in place, though, locked onto hers as those patterns start flashing across them once"
                                      + " again. It feels as if with each powerful jet of cum, you are giving some more control to Mara, and nothing has ever"
                                      + " felt better. When you are finally done, Mara releases you from all commands. <i>\"I guess you hate me now, huh? I"
                                      + " know it was wrong, but I just had to try, to do something, I...\"</i> You cut her off by kissing her. Yeah, her"
                                      + " methods were pretty brutal, but that was the most intense thing you ever felt. If she can make you feel like that,"
                                      + " who cares about some hypnosis thing? <i>\"Oh, " + player.name()
                                      + "! You're not mad? Oh!\"</i> She wraps her arms "
                                      + "around you, and you continue hugging and making out for a while before you finally leave. Your "
                                      + "muscles feel a little sluggish, and it's only going to get worse with time and distance, but maybe"
                                      + " it's worth it?");
            }
            Global.gui().choose(this, "Leave");
            Global.getPlayer().addict(AddictionType.MIND_CONTROL, npc, Addiction.MED_INCREASE);
            Global.getPlayer().getAddiction(AddictionType.MIND_CONTROL).ifPresent(Addiction::flagDaytime);
        }
        if (choice.equals("Sex")) {
            if (npc.getAffection(player) >= 8 && (!player.has(Trait.ticklemonster) || Global.random(2) == 1)) {
                Global.gui()
                      .message("You invite Mara to your room some fun. As soon as you get there she walks up behind you, shoves her hand down the front of your pants, and grabs you penis. "
                                      + "You're taken by surprise, but it doesn't stop you from getting hard in her hand. <i>\"You said we're here to have fun and I've decided you're my toy today,\"</i> she whispers "
                                      + "in an unusually sultry voice. When you're fully erect, she withdraws her hand and orders you to strip. Once you're naked, she has you sit on the bed and begins to fondle "
                                      + "your balls. <i>\"" +Global.capitalizeFirstLetter(Global.getPlayer().boyOrGirl()) + "s get really nervous when I play with these, but it feels good, doesn't it?\"</i> She's mostly back to her typical, mischievous self, but you can see a "
                                      + "dominant gleam in her eye. She takes her time undressing, turning it into a full stripshow, then she sits on your lap facing you. Her pussy is close enough to your straining "
                                      + "dick that you can feel her heat. <i>\"I think it would be a lot of fun to train you,\"</i> she says in her sweetest, innocent voice. <i>\"I'll train you so no other girl can get you "
                                      + "hard, but you'll come instantly at my touch. Then you'll win against everyone except me. Doesn't that sound advantageous for both of us?\"</i> You can't tell if she's joking or "
                                      + "being serious, but instead of resisting, the thought just makes you hornier. She inches closer to you until she's pressing against your erection while she nibbles on your ear. "
                                      + "The dual stimulus causes you to let out a sigh of pleasure. <br/><br/>Mara suddenly yelps and jerks away from you. What happened? Mara blushes and tries to regain her composure. <i>\"N-Nothing. "
                                      + "Your breath just tickled my neck a bit.\"</i> She's sensitive enough that breathing on her tickled? She leans back in and begins licking your neck seductively, but now you feel a mischievous "
                                      + "impulse of your own to see just how ticklish she is. You move your hand to the nape of her neck and trail your fingers lightly down her back. She shivers and you can hear her try "
                                      + "to hide a small giggle. This could be fun. Now you move your fingers under her thighs and tickle behind her knees. She shrieks in surprise and scrambles off yout lap, suddenly realizing "
                                      + "she's the toy now. <i>\"Wait! We can be reasonable about this! I can do wonderful things to you.\"</i><br/><br/>You tackle Mara to the bed and wiggle your fingers under her arms. She flails "
                                      + "desperately in an attempt to escape while laughing uncontrollably. You move your hands to her small breasts to tickle and tease her nipples. Her voice catches in her throat at the "
                                      + "sensation and she manages to scramble out of your reach, covering her upper body protectively. The pose leaves her legs open and you can see her wetness flowing freely. She follows "
                                      + "your gaze and her eyes widen. <i>\"Don't you dare!\"</i> You slip your hand between her legs before she can close them and lightly tease her love button. Her whole body tenses up as "
                                      + "this last touch sets off her climax. Even as parts of her are still trembling with pleasure, she covers her girl parts with her hands to avoid being tickled to a second orgasm. "
                                      + "<br/><br/>This just makes you want to tease her more. Her legs, and more specifically her feet, and still on your reach. She tries to stifle a squeal as you tickle the soles of her feet and "
                                      + "slowly work your way up her legs. Her knees are a gold mine, and she's so desperate to free them that she almost kicks you in the face. You linger here for a while, building her up "
                                      + "by touching around her kneecaps and the sides of the joint, then digging your fingers into the back of her knees to finish her off. <i>\"Cumming again!\"</i> she yells. If she can cum from "
                                      + "her knees being tickled, how will she react to the inner thighs? You dance your fingers higher, narrowing in on the area you estimate to be her most ticklish spot. Before you can reach "
                                      + "it, her hands dart out to catch your wrists and keep your fingers at bay. This exposes her treasure trove, but you don't have a free hand to exploit it. Instead you dive in face first "
                                      + "and start to lick up her juices. She makes a noise between a whimper and a giggle. <i>\"Oh God! Even that tickles right now!\"</i> You skillfully eat her out, alternating between licking her "
                                      + "clit and pushing your tongue into her entrance. Her third orgasm is the biggest yet, and she goes completely limp afterwards.<br/><br/>You softly stroke her hair as she catches her breath and "
                                      + "glares at you. <i>\"You're such a sadist.\"</i> She looks down at your straining member, neglected all this time and starting to leak pre-cum. She kisses you firmly and rolls on top of you. <i>\"If "
                                      + "I wasn't so fond of you I'd totally leave you with blue balls.\"</i> She guides the tip of your cock to her entrance and lowers herself onto your shaft. Her insides are hot, wet, and slippery. "
                                      + "You're almost ready to come from just being inside her. She moves her hips, riding you for her own pleasure as much as yours. You've been hard for so long without relief that you know you "
                                      + "won't be able to last, so you massage and play with her breasts to accelerate her climax. When your endurance breaks and you shoot you seed in Mara, she shudders and you can feel her pussy "
                                      + "squeeze you. She collapses into your chest and mumbles, <i>\"Four orgasms. I think you're trying to train my body.\"</i>");
                if (!player.has(Trait.ticklemonster)) {
                    Global.gui()
                          .message("<br/><br/><b>You've gotten better at finding sensitive spots when tickling nude opponents.</b>");
                    player.add(Trait.ticklemonster);
                    npc.getGrowth().addTrait(0, Trait.ticklemonster);
                }
            } else {
                Global.gui()
                      .message("You suggest finding a bed for some private fun. Mara smiles at you mischievously. <i>\"We don't really need a bed for that\"</i> She clears some space "
                                      + "off a nearby table and starts to undress you. She wants to do it right here? The door doesn't lock. What's to stop someone from walking in on you? <i>\"Nobody is "
                                      + "going to catch us. You're the only one who ever visits me here.\"</i> She gives you a tender kiss while she slips off your boxers. She's still fully dressed, and "
                                      + "you start to remedy that, but she makes you sit down so she can kneel between your legs. She rubs your erection with teasingly light, slow strokes and peppers "
                                      + "your glans with light kisses, arousing you greatly, but leaving you craving more stimulation. As your precum leaks out she laps it up with her tongue, giving you "
                                      + "more pleasure, but not enough for you to cum. You give a low groan of desire and frustration. She keeps up her teasing movements and looks at you innocently. "
                                      + "You realize she's waiting for you to beg her to make you cum. You open your mouth and only get as far as 'please' before she catches you off guard by sucking your "
                                      + "sensitive cockhead and jerking you off much faster. The sudden pleasure overwhelms you and you immediately blow your load into her mouth.<br/><br/>She swallows your cum "
                                      + "and continues sucking and licking your penis clean even as it becomes oversensitive in the wake of your orgasm. You gently, but firmly make Mara sit on the table "
                                      + "to free your overstimulated dick from her mouth. She's had her fun, now it's time for you to play with her. You strip her naked, which is hindered by her squirming "
                                      + "playfully away from your hands. Once she's completely nude, you start to lick and suck her modest breasts and hard nipples. She lets out a stifled whimper of pleasure "
                                      + "and you can see her love juices flowing freely from her pussy. You kiss your way down her tummy, but she catches your head before you can reach your target. You give "
                                      + "her a confused look. Is she not interested in getting eaten out? She smiles playfully. <i>\"I'm just not going to make it easy for you.\"</i> You kiss her on the lips and "
                                      + "slide your hand between her legs, fingering her soaked womanhood. You break the kiss so you can watch her expression while she bites her lip and struggles to keep "
                                      + "from cumming.<br/><br/>You jerk in surprise as you feel her toes grip your renewed erection and scrotum. She takes advantage of your hesitation and rubs your dick between "
                                      + "the soles of her feet. <i>\"You left yourself wide open,\"</i> she giggles. Even in private, it seems Mara can't help getting competitive. Well, you're not just going to keep "
                                      + "letting her toy with you. It's time for the main event.<br/><br/>Mara yelps in surprise as you sit down and pull her light body onto your lap. Before she has a chance to react, you "
                                      + "shove your cock into her tight, dripping womanhood. She moans in pleasure and buries her face in your neck, but soon regains her senses and sits bolt upright. <i>\"That wasn't "
                                      + "an orgasm!\"</i> she declares frantically, making you laugh. <i>\"I'm serious! That felt really good, but I didn't quite cum!\"</i> You give her a tender kiss and remind her that no "
                                      + "one is keeping score. She pouts at you cutely, but lets out a little moan when you thrust upwards. She responds by moving her own hips, and soon she's riding you vigorously. "
                                      + "She's completely given up trying to hold back her voice and is crying out in rhythm with her movements. As she orgasms, her pussy squeezes out your second ejaculation. "
                                      + "She rests her head against your shoulder and whispers, <i>\"Now we should find a bed.\"</i> Does she want another round? You'll probably need a few minutes to recover. <i>\"I "
                                      + "said I'd get some rest if you kept me company. I thought we could take a nap together.\"</i> That sounds like a very good idea.");

            }
            Global.gui()
                  .choose(this, "Leave");
            Daytime.train(player, npc, Attribute.Seduction);
            npc.gainAffection(player, 1);
            player.gainAffection(npc, 1);
        } else if (choice.equals("Games")) {
            if (npc.getAffection(player) >= 16 && (!player.has(Trait.spider) || Global.random(2) == 1)) {
                Global.gui()
                      .message("Mara is too damn good at these games. She moves her third spider next to your queen, trapping it in place. You don't lose until she fills in all six spaces "
                                      + "adjacent to your queen, but even with just those three pieces, you don't have enough room to move out of there. For the rest of the game, you try to block off her access "
                                      + "to the remaining spaces, while rushing to try to surround her queen and put her on the defensive. It's no use. You never manage to immobilize her queen so she always manages "
                                      + "to slip away before you can surround her. Finally she hops a grasshopper over the blockade you made around the last free space next to your queen, winning the game.<br/><br/>You "
                                      + "slump down in your chair while Mara walks over and settles comfortably on your lap. It's probably a mistake to keep playing these abstract games against a genius like Mara. She's "
                                      + "just going to win every time. <i>\"You did pretty well, you just failed to take one basic principle into consideration. As long as I can move and you can't, I'm going to win.\"</i> "
                                      + "She kisses you softly on the lips and then grins wickedly. <i>\"I prepared a practical example on the bed before you arrived. Shall we?\"</i><br/><br/>Mara leads you to her bed, where you see "
                                      + "several ropes tied to the legs. She quickly undresses you and you let her tie you to the bed. She binds your limbs tightly enough to keep you from moving, but takes great care to "
                                      + "make sure you're comfortable. When she's done, you're naked and spread-eagle on the bed. She sits between your spread legs, unfortunately still fully dressed. <i>\"Do you want to see "
                                      + "me naked too?\"</i> She squirms a bit in indecision. <i>\"I really want to be nice to you and we always have so much fun when we're naked together... but I'm kinda in a dominant mood, so I "
                                      + "think you should be the only one naked. Sorry.\"</i><br/><br/>Mara teasingly runs a single finger up and down your already hard penis. <i>\"I'm not sure if I should milk you over and over until "
                                      + "you're dry, or whether I should keep you on the brink as long as I can until you eventually cum in a massive burst.\"</i> She gives you a slow lick from balls to glans. <i>\"Yeah, I've "
                                      + "decided. Let's see if you can hit the ceiling.\"</i> She starts to stroke and play with your dick while she peppers kisses on your stomach and thighs. Her hands are very skilled and "
                                      + "it soon seems that she's forgotten she was planning to tease you, because you're rapidly approaching orgasm and she shows no indication of stopping. You do your best to hold back "
                                      + "and try to warn her, but it's too late, you're past the point of no return. Just as your endurance gives way, Mara tugs sharply on your balls, interrupting your climax. She takes "
                                      + "her hands off your twitching dick and watches you shiver in frustration. <br/><br/><i>\"I probably can't repeat that trick too many times, can I?\"</i> she says as she plays with the precum that starts "
                                      + "dripping from your penis.<br/><br/>You lose track of how many times she teases you to near orgasm and denies you. Sometimes she pulls on your balls, sometimes she squeezes your urethra "
                                      + "closed, and sometimes she just takes her hands off you. You're way beyond frustrated. You reached the begging stage ten minutes ago. Your need to ejaculate is starting to drive "
                                      + "you mad. After edging you again, Mara crawls over you to bring her face close to yours, confident your arousal won't wane while she's neglecting your dick. <i>\"Are you ready to try "
                                      + "and hit the ceiling?\"</i> Oh God yes! You're so pent up at this point that her hyperbole actually seems possible. <i>\"I want to have a short one-on-one Night Game right here and now. I'm "
                                      + "going to try to make you cum, and if I win, you have to do whatever I ask. You're at a bit of a disadvantage, but I believe in you. Is this ok?\"</i> Anything is fine! You just "
                                      + "need to cum as soon as possible. <i>\"Ok, ready? Start!\"</i> She grabs your swollen cock and begins to jerk you off seriously. In moments, you're overwhelmed with the feeling of sweet "
                                      + "release and you shoot jets of spunk high into the air. You almost pass out from the sensation, but eventually Mara's voice brings you back to consciousness.<br/><br/><i>\"You lost, and you didn't "
                                      + "quite hit the ceiling, but you made a very cute face when you came, so that's ok. Still, that's probably the poorest you've ever done in a match. Perhaps you should try to get some last "
                                      + "minute training in before tonight.\"</i> Well, you may have agreed (under serious coercion) to call that a match, but it certainly wasn't fair one. It's not like you're going to let her "
                                      + "tie you up naked in a real match. She giggles a bit. <i>\"Don't be so sure. I've been working on a trap to replicate this exact situation. Since I'm such a good sport and a good friend, "
                                      + "I'll even show you how to make it.\"</i> That can wait, you're still recovering from your megagasm and from being tied up for so long. Besides, even if it wasn't a fair fight, Mara did "
                                      + "make you cum. Surely she has something she wants you to do. She lowers her eyes shyly and replies softly. <i>\"Yeah, I want you to fall in love with me.\"</i> She gives you a tender, lingering "
                                      + "kiss. <i>\"There's no time limit, just get started on that when you get a chance.\"</i>");
                if (!player.has(Trait.spider)) {
                    Global.gui()
                          .message("<br/><br/><b>Mara has taught you to make the brilliant and insanely complex Spiderweb Trap.</b>");
                    player.add(Trait.spider);
                    npc.getGrowth().addTrait(0, Trait.spider);
                }
            } else {
                Global.gui()
                      .message("You knew Mara was clever, but you didn't expect playing a board game against her to be this frustrating. Every move you make, she seems to have planned far "
                                      + "ahead. She doesn't even pretend to be trying very hard. While you're planning out your next move, she tends to get bored and cuddle with you on the couch. This has a pretty profound impact on "
                                      + "your ability to concentrate. Even simple calculations become a challenge when she's stroking your thigh or breathing on your neck. On your turn you place down two stones in formation to "
                                      + "use them next turn, but she immediately summons a Sylvan Princess and steals one of them. <i>\"That was a bit too obvious for me to let happen. Is your head completely in "
                                      + "this game " + player.name()
                                      + "?\"</i> She's doing it on purpose, you're certain. She's building up fearsome formations of units, but every time you try to work out a counter, your "
                                      + "attention is seized by the feeling of her small, but soft breasts against your arm; or the sweet scent of her shampoo; or the sensation of her fingers creeping toward the "
                                      + "zipper of your jeans... hey wait! <br/><br/><i>\"What?\"</i> she asks innocently, while unzipping your pants. <i>\"Your erection looked very uncomfortable in those restricting pants, so I "
                                      + "was going to let the little guy out for a bit.\"</i> She slips your boner out of your pants and teases the sensitive tip. <i>\"It's still your move, and I should warn you that you're "
                                      + "going to be in a lot of trouble if you don't think of something clever this turn.\"</i> She gives you a wicked smile while you squirm under her touch. You steel yourself and try to analyze "
                                      + "the board, but there's no way you can focus like this.<br/><br/>You need to distract Mara so much that she forgets to tease you and gives you enough time to think. Without warning you push Mara "
                                      + "onto the couch and kiss her passionately. Her surprised protest is stifled as you push your tongue into her mouth. At the same time, your thigh presses into her crotch. A soft, aroused "
                                      + "noise escapes her as she lays there, unresisting. As suddenly as you grabbed her, you release her and stand back up, leaving her a bit dazed. You finally feel like your head is clear. "
                                      + "Looking at the board, you realize you're trailing enough in units to use your flare card, giving you an extra action. Playing a gun tower disrupts Mara's formation and, by the end of your "
                                      + "turn, you're in a much better position. <br/><br/><i>\"That's not fair!\"</i> Mara protests as she comes to her senses. <i>\"You can't play with a girl's heart like that just to win a game.\"</i> It doesn't "
                                      + "really seem like you did anything worse than what she was doing just seconds ago. <i>\"It's completely different. If you tease a " + Global.getPlayer().boyOrGirl() + ", he just gets hard and then you have something else to "
                                      + "tease. But if you do that to a girl, she's going to fall in love! Look at my flushed cheeks. Feel my heartbeat. I'm madly in love right now and it's totally your fault!\"</i> She glares down "
                                      + "at the gameboard and looks through her cards. <i>\"I'm going to have to completely destroy you to avenge the wounded heart of a pure maiden.\"</i><br/><br/>In the end, Mara still ends up winning, but "
                                      + "it's far from completely destroying you. You managed to give her a pretty good run for her money for the second half. By the time the game is over, she's mostly forgotten why she's "
                                      + "pretending to be mad at you, but she does demand to be on top during your 'follow-up game' to address your combined sexual frustration.");
            }
            Global.gui()
                  .choose(this, "Leave");
            Daytime.train(player, npc, Attribute.Cunning);
            npc.gainAffection(player, 1);
            player.gainAffection(npc, 1);
        } else if (choice.equals("Sparring")) {
            if (npc.getAffection(player) >= 12 && (!player.has(Trait.heeldrop) || Global.random(2) == 1)) {
                Global.gui()
                      .message("You and Mara prepare for some sparring practice by getting undressed. She tosses her clothes aside and is so eager that she's actually bouncing noticeably, "
                                      + "unconcerned about her nudity. You finish stretching and square off with her, but she raises a hand to stop you. <i>\"Wait! Can I have a kiss before we start?\"</i> You hesitate "
                                      + "in surprise. Mara's always angling for an advantage, is she going to try a sneak attack when you approach to kiss her? She pouts and looks a little hurt. <i>\"I wouldn't "
                                      + "do something that cruel. We're both naked, you're very sexy, and we're about to spend some time grappling. I just want a kiss first.\"</i> You feel a little guilty about distrusting "
                                      + "her as you embrace the naked girl and kiss her tenderly. She smiles gently as you part and whispers, <i>\"Thank you.\"</i> You're a little aroused, but nothing that would give Mara "
                                      + "an advantage. Apparently she really didn't have an ulterior motive.<br/><br/>When you start sparring, she remains on the defensive and focuses on avoiding your attacks. Her flexibility "
                                      + "serves her well her, each time you think you have a decent grip on her, she manages to slip away. You rush her, hoping to use your size to overwhelm her, but she nimbly dodges "
                                      + "away from you. You follow up with a feint to the left, and when she dodges to the right you manage to grab her around the waist. Before you can secure your grip, she squirms out "
                                      + "of your arms and drops into a low crouch. You hastily cover your groin to guard against the inevitable counterattack, but instead, she hooks her leg around your knee and takes "
                                      + "your feet out from under you. You catch yourself with both arms as you fall to keep from getting the wind knocked out of you. Mara grabs your ankles, jumps into the air, and executes "
                                      + "a perfect leg drop on your unprotected groin.<br/><br/>The pain is unbearable. You can't think about continuing the match. You can't think about anything except curling into the fetal "
                                      + "position and groaning in pain. <i>\"Do you give up?\"</i> Mara ask, crouching beside you. You nod. She hesitates for a few seconds. <i>\"Are you mad?\"</i> You shake your head. Low blows were allowed, "
                                      + "you can't really get mad at her for doing it effectively. She sits down on the mat and moves your head to her lap. <i>\"Here you go, we'll stay like this while you recover.\"</i> Her warm, "
                                      + "bare thighs make a very good pillow. You'd probably be able to enjoy it more if you weren't distracted by the pain from your balls. <i>\"I wish I had a chance of winning without hurting "
                                      + "you so much,\"</i> Mara says while stroking your hair. <i>\"I'm not as strong as Jewel, or as sexy as Angel. What I am is clever. The only way I can win it by finding my opponent's weakness "
                                      + "and hitting it as hard as I can.\"</i> She leans down and softly kisses you on the lips. <i>\"Unfortunately, sometimes that means hurting a " + Global.getPlayer().boyOrGirl() + " I like a lot.\"</i><br/><br/>By now, the pain in your groin "
                                      + "has started to recede and you're starting to react to being held by a naked girl. Mara glances between your legs and smiles. <i>\"Looks like you're feeling better.\"</i> She gently strokes "
                                      + "your dick until it becomes completely hard. <i>\"Feeling well enough for a quick squirt?\"</i> Your face reddens a bit at how she phrased her offer, but you nod. She grasps your shaft more "
                                      + "firmly and pumps it steadily. Your sore nuts give a twinge of protest, but it's quickly drowned out by your increasing need to cum. Mara smiles as you squirm under her hand and speeds "
                                      + "up her strokes to finish you off. Spurts of semen shoot into the air as you cum. You've probably recovered enough to get up, but you decide to take advantage of her lap pillow for a little "
                                      + "while longer.");
                if (!player.has(Trait.heeldrop)) {
                    Global.gui()
                          .message("<br/><br/><b>You've experienced Mara's most painful technique and learned how to use it yourself.</b>");
                    player.add(Trait.heeldrop);
                    npc.getGrowth().addTrait(0, Trait.heeldrop);
                }
            } else {
                Global.gui()
                      .message("You suggest heading to the gym to do a bit of sparring, but she suggests her room would give you more privacy. This turns out to be important, because when "
                                      + "you arrive she starts undressing and invites you to do the same. Obviously she feels that wrestling naked will give her a natural advantage. <i>\"Of course it will,\"</i> she "
                                      + "smiles mischieviously. <i>\"But you're not really going to refuse are you?\"</i> You look over her naked body top to bottom. There's no way you're going to to "
                                      + "refuse. You hastily strip down while she starts stretching. She's remarkably flexible, and you're pretty sure some of the more provocative stretches she does are intended "
                                      + "to tempt you. It makes sense for her to grasp for any edge she can get. While you know she can be quite formidable from your night matches, you outmatch her in both height "
                                      + "and weight. In a pure physical contest without her tricks or seduction, she's at a substantial disadvantage.<br/><br/>The two of you square off in the middle of the room. Mara's "
                                      + "nude form is a feast for the eyes, but you force yourself to keep your head in the match. Mara seems to have no such restraint and her eyes hungrily take in every inch of your "
                                      + "body. You're about to take the offensive to punish her lack of focus, when she lunges at your legs without warning. It seems her mind was on wrestling after all. She was simply "
                                      + "trying to lower your guard. It doesn't make any difference though, you're ready for her and easily push her back.<br/><br/>With deception having failed, her next move will probably "
                                      + "be to try to exploit your nudity by aiming for your anatomical weakness. Sure enough, she launches a kick to your groin, which you easily catch before it reaches you. Before "
                                      + "she can regain her balance, you advance and knock her to the mat. As you move to pin her, you catch first her right hand, then her left, when she makes desperate grabs for your "
                                      + "dangling testicles. With both her hands in your control and your body pressing down on hers, she can't do anything except squirm against you.<br/><br/><i>\"Since when did you become psychic?\"</i> "
                                      + "she asks with a pout, though she must already know the answer. She makes a much better rogue than a fighter. In a wrestling match with sex off the table, she only has one or two "
                                      + "tricks, which makes her very predictable. She makes one last futile attempt to wriggle out from under you, at which point she notices your erection pressing against her thigh. "
                                      + "<i>\"Are you planning to claim my body as the spoils of victory like some brutish barbarian?\"</i> Of course, you reply as you rub your dick against her vulva, but she's not really "
                                      + "going to refuse is she? She sulks while maneuvering her hips to try to get some penetration. <i>\"Stop teasing me and just put it in already.\"</i><br/><br/>You release her hands so you can "
                                      + "line up your insertion properly. You're a little worried that she'll try some last minute mischief, but when your dick pierces her wet folds she just wraps her arms around your "
                                      + "neck to pull you into a kiss. You thrust into her tight pussy and play with her hard nipples. She lets out little whimpers of pleasure each time you bury yourself into her. "
                                      + "You feel yourself nearing climax and suck on her neck, giving her a hickey. She gasps and shudders as you fill her with your hot seed. You pull out of her, smearing a significant "
                                      + "amount of cum on her thighs in the process. You suggest getting cleaned up, but she murmurs, <i>\"Too sleepy.\"</i> With a bit of effort, you pick her up and carry her to the bed, lying "
                                      + "down next to her. She rests her head on your arm, and in a few minutes she's asleep.");

            }
            Global.gui()
                  .choose(this, "Leave");
            Daytime.train(player, npc, Attribute.Power);
            npc.gainAffection(player, 1);
            player.gainAffection(npc, 1);
        } else if(choice.equals("Faerie play")){
                    Global.gui().message("The two of you head to Mara's room, where apparently she has a request for you. A quick glance around her room doesn't reveal any obvious traps or preparation. Everything seems "
                            + "fairly normal: slightly messy and her laptop left unlocked with some 'adult entertainment' on the desktop. <i>\"Hey! Don't be nosy!\"</i> She closes her laptop, noticably blushing, and sits "
                            + "on her bed.<br/><br/>"
                            + "<i>\"So... you've been learning some actual magic, right?\"</i> She asks with thinly veiled excitement. <i>\"Can you summon those faeries anytime?\"</i> She's practically vibrating at "
                            + "the idea of playing with one of your pixie familiars. You've never really tried summoning them outside of a fight or Aisha's training room. The Games seem to have a magic of their own that "
                            + "make the supernatural seem perfectly normal. It's a little harder to focus on your mana here in the 'real world,' but you should be able to manage.<br/><br/>"
                            + "You begin the spell, and after a bit longer than usual, you feel a fae spirit answer you. You extend your hand and a male faerie winks into existance.<br/>"
                            + "<i>\"Ooh, a boy!\"</i> She giggles in amusement as she pokes and prods the fae. <i>\"He's got a tiny dick. It's so cute!\"</i> She seems to be enjoying "
                            + "herself. You feel bad for your familiar though, summoned just to be bullied.<br/><br/>"
                            + "<i>\"Oh, don't worry. He's not complaining. Are all faeries this sensitive?\"</i> They are significantly more sensitive than humans. Mara's delicate fingerwork does seem to be having an "
                            + "effect. If she's not careful, she's going to make her new toy cum. <i>\"I'm very careful. Too bad, little guy.\"</i> She releases the frustrated little faerie. <i>\"Just be patient "
                            + "and I'll finish you off later.\"</i> She looks at you again expectantly. <i>\"How about a girl faerie next?\"</i><br/>"
                            + "Two at once? You've never tried to split your mana between two familiars. It takes a lot of concentration just to maintain the one, and it's going to be hard to generate enough mana "
                            + "outside the heat of a sexfight.<br/><br/>"
                            + "Mara pounces on you without warning and kisses you deeply. She slips her tongue into your mouth while pressing her lithe body against you. She breaks the kiss to give you a seductive "
                            + "smile. <i>\"Does that help at all?\"</i> You can definitely feel a fire blazing inside you. Instead of answering, you easily summon a female faerie.<br/>"
                            + "<i>\"Another cutie. Very nice.\"</i> She licks her lips suggestively she starts undressing. <i>\"I could probably have some fun with her, but I think we should start the main event.\"</i> "
                            + "She presses herself against your body again and helps you out of your own clothing. If she just wanted to have sex, you didn't need to summon the second faerie.<br/><br/> "
                            + "Mara assumes a sexy pose on the bed and gives you a wink. <i>\"This way we have two little 'helpers.' One for each of us. Wouldn't a few extra hands, tongues, and sexy parts make things "
                            + "a bit more fun?\"</i> Speaking of your two familiars, they appear to be conversing in their own language as they watch Mara's seductive display. <i>\"Obviously, the girl will attend to "
                            + "you and the little gu-Ah!\"</i><br/><br/>"
                            + "Before either of you can react, the two fae pounce on Mara, teasing and caressing her naked body. <i>\"Ahh! HAHA~ Tickles! Why are they just -AH!- attacking me-EEK!?\"</i> That's a good "
                            + "question. Maybe they're just loyal to you, because you summoned them. Maybe they're getting revenge for her teasing the male. Either way, it's a pretty good show. You position yourself "
                            + "between her legs and hold them in place to control her squirming. The female faerie catches you intention and spreads Mara's pussy lips to reveal her dripping wet entrance. You lightly "
                            + "prod her nethers with your cock, gauging her response.<br/><br/>"
                            + "<i>\"God, yes! Fill me up!\"</i> That's as positive as reactions get. You're quite happy to comply. You thrust your hips, penetrating her with a single stroke. She squeals in delight and "
                            + "wraps her legs around your hips. You piston your hips, fucking her in earnest. At first, you're worried about accidently squashing your familiars, but they have enough battle experience "
                            + "to stay out of the way, darting in occassionally to tease and tickle sensitive areas. You kiss Mara passionately as you both approach climax. Both faeries target Mara's clit simultaneously, "
                            + "sending her into a fit of orgasmic shudders. Her pussy clenches, milking out your ejaculation.<br/><br/>"
                            + "You collapse onto the bed, pulling Mara into your embrace. She leans her head contentedly against your chest. The two faeries, apparently realizing the humans in the room are too "
                            + "tired to play with them and entertain themselves by starting an impromptu sex-fight. You and Mara watch the show and she giggles in delight as the female gains the upper hand, milking "
                            + "the boy dry. Mara finally gets a chance to play with the female, giving the victor an orgasm as reward.");
                    if(!player.has(Trait.faefriend)){
                        Global.gui().message("<br/><br/><b>You're finding it easier to call faeries. They seem to be more eager to respond to your summons.</b>");
                        player.add(Trait.faefriend);
                    }
                    Global.gui().choose(this,"Leave");
                    npc.gainAffection(player,1);
                    player.gainAffection(npc,1);
            } else if (choice.equals("Leave")) {
            done(true);
        }
    }
    
    @Override
    public Optional<String> getAddictionOption() {
        return Global.getPlayer().checkAddiction(AddictionType.MIND_CONTROL) ? 
                        Optional.of("Confront about control") : Optional.empty();
    }
}
