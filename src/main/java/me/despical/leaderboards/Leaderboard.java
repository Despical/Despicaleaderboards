package me.despical.leaderboards;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

/**
 * @author Despical
 * <p>
 * Created at 26.06.2023
 */
public abstract class Leaderboard extends PlaceholderExpansion {

    protected final Main plugin;
    protected final String id, emptyMessage, unknownPlayerMessage;

    public Leaderboard(Main plugin, String id) {
        this.plugin = plugin;
        this.id = id;
        this.emptyMessage = plugin.getConfig().getString("Empty-Value");
        this.unknownPlayerMessage= plugin.getConfig().getString("Unknown-Player");
        this.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return id;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Despical";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }
}