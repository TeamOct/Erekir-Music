package em.content;

import arc.audio.Music;
import mindustry.Vars;

public class EMusic {
    public static Music
        // ambient
        dosimeter, coast,
        // dark
        infernalTrain, wotu, toxicLakes,
        // boss
        crossedWings
    ;

    public static void load() {
        dosimeter = loadMusic("dosimeter");
        coast = loadMusic("the_coast");

        infernalTrain = loadMusic("infernal_train");
        wotu = loadMusic("waltz_of_the_underworld");
        toxicLakes = loadMusic("Toxic_lakes");

        crossedWings = loadMusic("crossed_wings");
    }

    private static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
