package net.tyan.battleroyal.listeners;

import net.tyan.battleroyal.BattleRoyal;
import net.tyan.battleroyal.stats.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * by Kevin on 24.06.2015.
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        User user = BattleRoyal.activeUsers.get(event.getPlayer().getUniqueId());
        user.update();

        if (BattleRoyal.activeUsers.containsKey(event.getPlayer().getUniqueId()))
            BattleRoyal.activeUsers.remove(event.getPlayer().getUniqueId());

    }
}
