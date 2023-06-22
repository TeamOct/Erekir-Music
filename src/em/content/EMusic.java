package em.content;

import arc.audio.Music;
import mindustry.Vars;

public class EMusic {
    public static Music dosimeter, infernalTrain, wotu, coast;

    public static void load() {
        dosimeter = loadMusic("dosimeter");
        infernalTrain = loadMusic("infernal_train");
        wotu = loadMusic("waltz_of_the_underworld");
        coast = loadMusic("the_coast");
    }

    private static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
