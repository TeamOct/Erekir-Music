package em;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.Planets;
import mindustry.core.GameState;
import mindustry.mod.Mod;

import static em.content.EMusic.*;
import static mindustry.game.EventType.*;

public class ErekirMusicMod extends Mod {
    /** List of <i>vanilla</i> ambient music. */
    public Seq<Music> vAmbient;
    /** List of <i>vanilla</i> dark music. */
    public Seq<Music> vDark;
    /** List of <i>vanilla</i> boss music. */
    public Seq<Music> vBoss;

    /** A reference to SoundControl instance for easier access. */
    public SoundControl control;

    public ErekirMusicMod() {}

    @Override
    public void init() {
        // First and foremost, load the music.
        load();

        control = Vars.control.sound;

        Events.on(MusicRegisterEvent.class, e -> {
            // Save copies of vanilla music lists.
            vAmbient = control.ambientMusic.copy();
            vDark = control.darkMusic.copy();
            vBoss = control.bossMusic.copy();
        });

        Events.on(WorldLoadEvent.class, e -> {
            if (Vars.state.rules.planet == Planets.erekir) {
                // Inject custom music here.
                control.ambientMusic = modAmbient;
                control.darkMusic = modDark;
                control.bossMusic = modBoss;
            }
        });

        Events.on(StateChangeEvent.class, e -> {
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                // Reset music upon going to main menu.
                control.ambientMusic = vAmbient;
                control.darkMusic = vDark;
                control.bossMusic = vBoss;
            }
        });
    }
}
