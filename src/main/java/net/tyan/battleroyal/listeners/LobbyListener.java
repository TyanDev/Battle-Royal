package net.tyan.battleroyal.listeners;


import net.tyan.battleroyal.BattleRoyal;
import net.tyan.battleroyal.game.GameState;
import net.tyan.battleroyal.utilities.TitleUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * by Kevin on 25.06.2015.
 */

public class LobbyListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getPlayer().getItemInHand().getType() == Material.EMPTY_MAP) {
                    ItemStack itemStack = event.getPlayer().getItemInHand();
                    event.setCancelled(true);
                    event.getPlayer().setItemInHand(itemStack);

                    TitleUtil.sendTitle(event.getPlayer(), "", "§3Achievements | WIP!", 10, 20, 10);
                }
            }
        }
    }

    /* Blocked events */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            if (!(event.getPlayer().hasPermission("battleroyal.admin") && event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            if (!(event.getPlayer().hasPermission("battleroyal.admin") && event.getPlayer().getGameMode() == GameMode.CREATIVE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            if (!(event.getPlayer().hasPermission("battleroyal.admin") && event.getPlayer().getGameMode() == GameMode.CREATIVE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            if (!(event.getPlayer().hasPermission("battleroyal.admin") && event.getPlayer().getGameMode() == GameMode.CREATIVE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (BattleRoyal.getGameState() == GameState.LOBBY) {
            Player player  = event.getPlayer();

            if (player.hasPermission("battleroyal.admin")) {
                event.setFormat("§3" + player.getDisplayName() + "§7: §c" + event.getMessage());
            } else {
                event.setFormat(player.getDisplayName() + "§7: §f" + event.getMessage());
            }
        }
    }
}
