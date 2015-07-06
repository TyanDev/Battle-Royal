package net.tyan.battleroyal.commands;

import net.tyan.battleroyal.BattleRoyal;
import net.tyan.battleroyal.stats.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * by Kevin on 24.06.2015.
 */

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        User user = BattleRoyal.activeUsers.get(player.getUniqueId());

        String prefix = "§7[§6Stats§7] ";

        String[] stats = new String[7];
        stats[0] = "§9Kills: §a" + user.getKills();
        stats[1] = "§9Tode: §a" + user.getDeaths();
        String kd = String.valueOf((double) user.getKills() / (double) user.getDeaths());
        if (kd.length() > 4)
            kd = kd.substring(0, 4);
        if (kd.contains("[^\\d-]"))
            kd = "0";

        stats[2] = "§9K/D: §a" + kd;
        stats[3] = "§9Punkte: §a" + user.getPoints();
        stats[4] = "§9Gespielte Runden: §a" + user.getPlayedRounds();
        stats[5] = "§9Gewonnene Runden: §a" + user.getRoundsWon();
        stats[6] = "§9Ge\u00f6ffnete Kisten: §a" + user.getChestOpened();

        for (int i = 0; i < 13; i++)
            player.sendMessage("");

        for (int i = 0; i < stats.length; i++) {
            player.sendMessage(prefix + stats[i]);
        }
        return true;
    }
}
