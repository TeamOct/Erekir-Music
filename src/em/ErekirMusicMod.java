package em;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.Planets;
import mindustry.mod.Mod;

import static em.content.EMusic.*;
import static mindustry.game.EventType.*;

public class ErekirMusicMod extends Mod {
    /** List of ambient music. */
    public Seq<Music> modAmbient = new Seq<>();
    /** List of "dark" music, usually played during conflicts and dire situations. */
    public Seq<Music> modDark = new Seq<>();
    /** List of boss music. Currently empty. */
    public Seq<Music> modBoss = new Seq<>();

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

        // Initiate music lists here.
        control = Vars.control.sound;

        modAmbient.addAll(dosimeter, coast);
        modDark.addAll(infernalTrain, wotu, toxicLakes);
        modBoss.addAll(crossedWings);

        Events.on(MusicRegisterEvent.class, e -> {
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
            } else {
                // Reset music in non-Erekir maps.
                control.ambientMusic = vAmbient;
                control.darkMusic = vDark;
                control.bossMusic = vBoss;
            }
        });
    }
}
