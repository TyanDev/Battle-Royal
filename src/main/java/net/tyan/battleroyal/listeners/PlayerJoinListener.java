package net.tyan.battleroyal.listeners;

import net.tyan.battleroyal.BattleRoyal;
import net.tyan.battleroyal.reference.Reference;
import net.tyan.battleroyal.stats.User;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

/**
 * by Kevin on 24.06.2015.
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(Reference.PREFIX + "§3" + event.getPlayer().getName() + " §7spielt nun mit!");
        User user = new User(event.getPlayer());

        BattleRoyal.activeUsers.put(event.getPlayer().getUniqueId(), user);

        readyPlayer(event.getPlayer());
        giveItems(event.getPlayer());
    }

    private void readyPlayer(Player player) {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setFireTicks(0);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setGameMode(GameMode.ADVENTURE);
        player.setExp(0f);
        player.setLevel(0);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }

    private void giveItems(Player player) {
        ItemStack returnLobby = new ItemStack(Material.DEAD_BUSH);
        ItemStack voter = new ItemStack(Material.PAPER);
        ItemStack achievements = new ItemStack(Material.EMPTY_MAP);

        ItemMeta returnLobbyMeta = returnLobby.getItemMeta();
        returnLobbyMeta.setDisplayName("§cZur\u00fcck zur Lobby");

        ItemMeta voterMeta = voter.getItemMeta();
        voterMeta.setDisplayName("§6Vote");

        ItemMeta achievementsMeta = achievements.getItemMeta();
        achievementsMeta.setDisplayName("§6Achievements");

        returnLobby.setItemMeta(returnLobbyMeta);
        voter.setItemMeta(voterMeta);
        achievements.setItemMeta(achievementsMeta);

        player.getInventory().setItem(0, voter);
        player.getInventory().setItem(4, achievements);
        player.getInventory().setItem(8, returnLobby);
    }
}
