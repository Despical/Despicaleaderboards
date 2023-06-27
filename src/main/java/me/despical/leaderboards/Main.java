package me.despical.leaderboards;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Despical
 * <p>
 * Created at 26.06.2023
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Class<?>[] leaderboards = {KOTLLeaderboards.class, OITCLeaderboards.class, TRLeaderboards.class, WMLeaderboards.class, MMLeaderboards.class};
        String[] dependencies = {"KOTL", "OITC", "TNTRun", "WhackMe", "MurderMystery"};
        List<String> foundDependencies = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            final String dependency = dependencies[i];
            if (getServer().getPluginManager().isPluginEnabled(dependency)) {
                try {
                    leaderboards[i].getConstructor(Main.class).newInstance(this);

                    foundDependencies.add(dependency);
                } catch (Exception e) {
                    getLogger().severe("Something went wrong while we were initializing our PAPI extension.");
                }
            }
        }

        saveDefaultConfig();

        getLogger().log(Level.INFO, "We initialized our found PAPI extensions for: {0}", String.join(", ", foundDependencies));
    }
}