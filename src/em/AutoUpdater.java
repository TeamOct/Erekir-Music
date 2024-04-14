package em;

import arc.Core;
import arc.util.*;
import arc.util.serialization.Jval;
import mindustry.mod.Mods;

import static mindustry.Vars.*;

// stolen from ApsZoldat/MindustryMappingUtilities, thanks
public class AutoUpdater {
    public AutoUpdater() {
        Mods.LoadedMod mod = mods.getMod(ErekirMusicMod.class);
        Http.get(ghApi + "/repos/" + mod.getRepo() + "/releases", result -> {
            var json = Jval.read(result.getResultAsString());
            Jval.JsonArray releases = json.asArray();

            if (releases.size == 0) return;

            String latest = releases.first().getString("tag_name");
            String current = mod.meta.version;
            float latestFloat = Float.parseFloat(latest.replace("v", "").replaceFirst("[.]", ""));
            float currentFloat = Float.parseFloat(current.replace("v", "").replaceFirst("[.]", ""));

            Log.info("[EM] Current version: @, latest version: @", currentFloat, latestFloat);

            if (currentFloat >= latestFloat) {
                Log.info("[EM] Mod is on the latest version.");
                return;
            }

            ui.showConfirm("@em.updateavailable.title", Core.bundle.format("em.updateavailable.description", latest), () -> {
                ui.mods.githubImportMod(mod.getRepo(), true);
            });
        }, this::error);
    }

    void error(Throwable e) {
        Log.err("[EM] Failed to check for updates!\nCause", e);
    }
}
