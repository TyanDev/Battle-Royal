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

public class CheatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        User user = BattleRoyal.activeUsers.get(player.getUniqueId());


        if (args.length == 1) {
            int input = Integer.valueOf(args[0]);
            user.setKills(input);
            player.sendMessage("§6Du hast nun §3" + input + " §6Kills!");
        }

        return true;
    }
}
