package net.tyan.battleroyal.stats;

import net.tyan.battleroyal.BattleRoyal;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * by Kevin on 23.06.2015.
 */

public class User {

    private int kills;
    private int deaths;

    private int points;

    private int playedRounds;
    private int roundsWon;
    private int chestOpened;

    private String achievements;

    private UUID uuid;

    public User(Player player) {
        if (UserFetcher.playerExists(player.getUniqueId())) {
            setUUID(player.getUniqueId());
            loadUser();
        } else {
            setKills(0);
            setDeaths(0);
            setPoints(100);
            setPlayedRounds(0);
            setRoundsWon(0);
            setChestOpened(0);
            setAchievements("");
            setUUID(player.getUniqueId());
            UserFetcher.createUser(this);
        }
    }

    public void update() {
        try {
            PreparedStatement statement = BattleRoyal.getMySQL().prepare("UPDATE users SET Kills = ?, Deaths = ?, Points = ?, PlayedRounds = ?, ChestOpened = ?, Achievements = ? WHERE UUID = ?;");
            statement.setInt(1, getKills());
            statement.setInt(2, getDeaths());
            statement.setInt(3, getPoints());
            statement.setInt(4, getPlayedRounds());
            statement.setInt(5, getChestOpened());
            statement.setString(6, getAchievements());
            statement.setString(7, getCompactUUID());

            BattleRoyal.getMySQL().update(statement);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadUser() {
        try {
            PreparedStatement statement = BattleRoyal.getMySQL().prepare("SELECT * FROM users WHERE UUID = ?;");
            statement.setString(1, getCompactUUID());

            BattleRoyal.getMySQL().query(statement, new Consumer<ResultSet>() {
                @Override
                public void accept(ResultSet resultSet) {
                    try {
                        if (resultSet.next()) {
                            BattleRoyal.activeUsers.get(getUUID()).setKills(resultSet.getInt("Kills"));
                            BattleRoyal.activeUsers.get(getUUID()).setDeaths(resultSet.getInt("Deaths"));
                            BattleRoyal.activeUsers.get(getUUID()).setPoints(resultSet.getInt("Points"));
                            BattleRoyal.activeUsers.get(getUUID()).setPlayedRounds(resultSet.getInt("PlayedRounds"));
                            BattleRoyal.activeUsers.get(getUUID()).setRoundsWon(resultSet.getInt("RoundsWon"));
                            BattleRoyal.activeUsers.get(getUUID()).setChestOpened(resultSet.getInt("ChestOpened"));
                            BattleRoyal.activeUsers.get(getUUID()).setAchievements(resultSet.getString("Achievements"));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getCompactUUID() {
        return getUUID().toString().replaceAll("-", "");
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getPoints() {
        return points;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPlayedRounds() {
        return playedRounds;
    }

    public void setPlayedRounds(int playedRounds) {
        this.playedRounds = playedRounds;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public int getChestOpened() {
        return chestOpened;
    }

    public void setChestOpened(int chestOpened) {
        this.chestOpened = chestOpened;
    }
}
