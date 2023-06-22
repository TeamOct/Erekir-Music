package em;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import em.content.EMusic;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.Planets;
import mindustry.mod.Mod;

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
        EMusic.load();

        // Initiate music lists here.
        control = Vars.control.sound;

        var universal = Seq.with(EMusic.dosimeter, EMusic.infernalTrain, EMusic.wotu);
        modAmbient.addAll(universal);
        modDark.addAll(universal);
        modBoss.addAll();

        Events.on(MusicRegisterEvent.class, e -> {
            vAmbient = control.ambientMusic.copy();
            vDark = control.darkMusic.copy();
            vBoss = control.bossMusic.copy();
        });

        Events.on(WorldLoadEvent.class, e -> {
            if (Vars.state.rules.planet == Planets.erekir) {
                // Inject custom music here.
                Log.info("The music should load now...");

                control.ambientMusic = modAmbient;
                control.darkMusic = modDark;
                //TODO: this will just erase all the boss music since the list is currently empty
                //control.bossMusic = modBoss;
            } else {
                // Reset music in non-Erekir maps.
                Log.info("Music reset. It should be like vanilla now.");
                control.ambientMusic = vAmbient;
                control.darkMusic = vDark;
                control.bossMusic = vBoss;
            }
        });
    }
}
