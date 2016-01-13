package nightgames.daytime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nightgames.characters.custom.effect.CustomEffect;
import nightgames.characters.custom.requirement.CustomRequirement;
import nightgames.items.Item;

public class TransformationOption {
    String option;
    Map<Item, Integer> ingredients;
    List<CustomRequirement> requirements;
    String scene;
    String additionalRequirements;
    CustomEffect effect;

    public TransformationOption() {
        option = "";
        ingredients = new HashMap<Item, Integer>();
        scene = "";
        additionalRequirements = "";
        requirements = new ArrayList<>();
        effect = (c, self, other) -> {
            return true;
        };
    }
}
