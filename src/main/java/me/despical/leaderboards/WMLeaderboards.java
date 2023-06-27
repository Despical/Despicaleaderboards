package me.despical.leaderboards;

import me.despical.commons.number.NumberUtils;
import me.despical.whackme.api.StatsStorage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * @author Despical
 * <p>
 * Created at 26.06.2023
 */
public class WMLeaderboards extends Leaderboard {

    public WMLeaderboards(Main plugin) {
        super(plugin, "wmlb");
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String id) {
        id = id.toLowerCase();

        final int subId = NumberUtils.getInt(id.substring(id.lastIndexOf('_') + 1));

        if (id.contains("scorer")) {
            final String request = handlePlaceholderRequest(id, "top_scorer_name_", "top_scorer_value_", subId, StatsStorage.StatisticType.RECORD_SCORE);

            if (request != null) {
                return request;
            }
        }

        if (id.contains("games")) {
            return handlePlaceholderRequest(id, "top_games_name_", "top_games_value_", subId, StatsStorage.StatisticType.TOURS_PLAYED);
        }

        return null;
    }

    private String handlePlaceholderRequest(String id, String name, String value, int subId, StatsStorage.StatisticType statisticType) {
        final Map<UUID, Integer> stats = StatsStorage.getStats(statisticType);

        try {
            if (id.equals(name + subId)) {
                UUID current = (UUID) stats.keySet().toArray()[stats.keySet().toArray().length - subId];
                stats.remove(current);

                return plugin.getServer().getOfflinePlayer(current).getName();
            } else if (id.equals(value + subId)) {
                UUID current = (UUID) stats.keySet().toArray()[stats.keySet().toArray().length - subId];

                return Integer.toString(stats.get(current));
            }

        } catch (IndexOutOfBoundsException exception) {
            return emptyMessage;
        } catch (NullPointerException exception) {
            return unknownPlayerMessage;
        }

        return null;
    }
}