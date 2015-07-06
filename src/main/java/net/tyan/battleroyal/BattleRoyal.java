package net.tyan.battleroyal;

import net.tyan.battleroyal.commands.CheatCommand;
import net.tyan.battleroyal.commands.StatsCommand;
import net.tyan.battleroyal.database.AsyncMySQL;
import net.tyan.battleroyal.game.GameState;
import net.tyan.battleroyal.listeners.LobbyListener;
import net.tyan.battleroyal.listeners.PlayerJoinListener;
import net.tyan.battleroyal.listeners.PlayerQuitListener;
import net.tyan.battleroyal.reference.Reference;
import net.tyan.battleroyal.scheduler.LobbyScheduler;
import net.tyan.battleroyal.stats.User;
import net.tyan.battleroyal.utilities.RegisterUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * by Kevin on 23.06.2015.
 */

public class BattleRoyal extends JavaPlugin {

    private static AsyncMySQL mySQL;
    public static Map<UUID, User> activeUsers;
    private static GameState gameState;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

        for (Map.Entry<UUID, User> entries : activeUsers.entrySet()) {
            User user = entries.getValue();
            user.update();
            activeUsers.remove(entries.getKey());
        }
    }

    private void init() {
        mySQL = new AsyncMySQL(this, Reference.HOST, Reference.PORT, Reference.USER, Reference.PASSWORD, Reference.DATABASE);
        mySQL.update("CREATE TABLE IF NOT EXISTS users (UUID char(32), Kills int, Deaths int, Points int, PlayedRounds int, RoundsWon int, ChestOpened int, Achievements text);");
        activeUsers = new HashMap<>();

        gameState = GameState.LOBBY;

        RegisterUtil<BattleRoyal> registerUtil = new RegisterUtil<>(this);
        registerUtil.registerEvents(PlayerJoinListener.class, PlayerQuitListener.class, LobbyListener.class);

        registerUtil.registerCommand("stats", "Stats Command", new StatsCommand(), "");
        registerUtil.registerCommand("cheat", "Cheat Command", new CheatCommand(), "battleroyal.admin");

        new LobbyScheduler(this);
    }

    public static AsyncMySQL getMySQL() {
        return mySQL;
    }

    public static GameState getGameState() {
        return gameState;
    }


}
