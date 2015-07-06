package net.tyan.battleroyal.stats;

import net.tyan.battleroyal.BattleRoyal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * by Kevin on 23.06.2015.
 */

public class UserFetcher {

    public static boolean playerExists(UUID uuid) {
        boolean exists = false;

        try {
            PreparedStatement statement = BattleRoyal.getMySQL().prepare("SELECT * FROM users WHERE UUID = ?;");
            statement.setString(1, uuid.toString().replaceAll("-", ""));

            ResultSet resultSet = BattleRoyal.getMySQL().getMySQL().query(statement);

            exists = resultSet.next();
            resultSet.close();
            statement.close();

            return exists;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return exists;
        }
    }


    public static void createUser(User user) {
        try {
            PreparedStatement statement = BattleRoyal.getMySQL().prepare("INSERT INTO users (UUID, Kills, Deaths, Points, PlayedRounds, RoundsWon, ChestOpened, Achievements) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, user.getCompactUUID());
            statement.setInt(2, user.getKills());
            statement.setInt(3, user.getDeaths());
            statement.setInt(4, user.getPoints());
            statement.setInt(5, user.getPlayedRounds());
            statement.setInt(6, user.getRoundsWon());
            statement.setInt(7, user.getChestOpened());
            statement.setString(8, user.getAchievements());

            BattleRoyal.getMySQL().update(statement);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
