package em;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import em.content.EMusic;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.Planets;
import mindustry.game.EventType.*;
import mindustry.mod.Mod;

public class ErekirMusicMod extends Mod {
    /** List of ambient music. */
    public Seq<Music> modAmbient = new Seq<>();
    /** List of "dark" music, usually played during conflicts and dire situations. */
    public Seq<Music> modDark = new Seq<>();
    /** List of boss music. Currently empty. */
    public Seq<Music> modBoss = new Seq<>();

    /** List of <i>vanilla</i> ambient music. */
    public Seq<Music> vAmbient = new Seq<>();
    /** List of <i>vanilla</i> dark music. */
    public Seq<Music> vDark = new Seq<>();
    /** List of <i>vanilla</i> boss music. */
    public Seq<Music> vBoss = new Seq<>();

    /** A reference to SoundControl instance for easier access. */
    public SoundControl control = Vars.control.sound;

    public ErekirMusicMod() {
        // Initiate music lists here.
        Events.on(ClientLoadEvent.class, e -> {
            var universal = Seq.with(EMusic.dosimeter, EMusic.infernalTrain, EMusic.wotu);
            modAmbient.addAll(universal);
            modDark.addAll(universal);
            modBoss.addAll();

            vAmbient = control.ambientMusic;
            vDark = control.darkMusic;
            vBoss = control.bossMusic;
        });

        // Inject custom music here.
        Events.on(WorldLoadEvent.class, e -> {
            if (Vars.state.rules.planet != Planets.erekir) return;

            control.ambientMusic = modAmbient;
            control.darkMusic = modDark;
            //TODO: this will just erase all the boss music since the list is currently empty
            //control.bossMusic = modBoss;
        });

        // Reset music here.
        Events.on(StateChangeEvent.class, e -> {
            if (Vars.state.rules.planet != Planets.erekir) return;

            control.ambientMusic = vAmbient;
            control.darkMusic = vDark;
            control.bossMusic = vBoss;
        });
    }

    @Override
    public void loadContent(){
        EMusic.load();
    }
}
