package me.despical.leaderboards;

import me.despical.commons.number.NumberUtils;
import me.despical.commons.string.StringFormatUtils;
import me.despical.tntrun.api.StatsStorage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * @author Despical
 * <p>
 * Created at 26.06.2023
 */
public class TRLeaderboards extends Leaderboard {

    public TRLeaderboards(Main plugin) {
        super(plugin, "trlb");
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String id) {
        id = id.toLowerCase();

        final int subId = NumberUtils.getInt(id.substring(id.lastIndexOf('_') + 1));

        if (id.contains("wins")) {
            final String request = handlePlaceholderRequest(id, "top_wins_name_", "top_wins_value_", subId, StatsStorage.StatisticType.WINS);

            if (request != null) {
                return request;
            }
        }

        if (id.contains("loses")) {
            final String request = handlePlaceholderRequest(id, "top_loses_name_", "top_loses_value_", subId, StatsStorage.StatisticType.LOSES);

            if (request != null) {
                return request;
            }
        }

        if (id.contains("games_player")) {
            final String request = handlePlaceholderRequest(id, "top_games_player_name_", "top_games_player_value_", subId, StatsStorage.StatisticType.GAMES_PLAYED);

            if (request != null) {
                return request;
            }
        }

        if (id.contains("coins")) {
            final String request = handlePlaceholderRequest(id, "top_coins_name_", "top_coins_value_", subId, StatsStorage.StatisticType.COINS);

            if (request != null) {
                return request;
            }
        }

        if (id.contains("longest_survive")) {
            return handlePlaceholderRequest(id, "top_longest_survive_name_", "top_longest_survive_value_", subId, StatsStorage.StatisticType.LONGEST_SURVIVE, true);
        }

        return null;
    }

    private String handlePlaceholderRequest(String id, String name, String value, int subId, StatsStorage.StatisticType statisticType) {
        return this.handlePlaceholderRequest(id, name, value, subId, statisticType, false);
    }

    private String handlePlaceholderRequest(String id, String name, String value, int subId, StatsStorage.StatisticType statisticType, boolean format) {
        final Map<UUID, Integer> stats = StatsStorage.getStats(statisticType);

        try {
            if (id.equals(name + subId)) {
                UUID current = (UUID) stats.keySet().toArray()[stats.keySet().toArray().length - subId];
                stats.remove(current);

                return plugin.getServer().getOfflinePlayer(current).getName();
            } else if (id.equals(value + subId)) {
                UUID current = (UUID) stats.keySet().toArray()[stats.keySet().toArray().length - subId];

                return format ? StringFormatUtils.formatIntoMMSS(stats.get(current)) : Integer.toString(stats.get(current));
            }

        } catch (IndexOutOfBoundsException exception) {
            return emptyMessage;
        } catch (NullPointerException exception) {
            return unknownPlayerMessage;
        }

        return null;
    }
}