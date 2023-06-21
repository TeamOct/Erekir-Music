package em.content;

import arc.audio.Music;
import mindustry.Vars;

public class EMusic {
    public static Music dosimeter, infernalTrain, wotu;

    public static void load() {
        dosimeter = loadMusic("dosimeter");
        infernalTrain = loadMusic("infernal_train");
        wotu = loadMusic("waltz_of_the_underworld");
    }

    private static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
}
