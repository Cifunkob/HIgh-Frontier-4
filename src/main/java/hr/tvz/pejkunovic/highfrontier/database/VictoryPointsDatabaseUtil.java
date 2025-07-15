package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VictoryPointsDatabaseUtil {

    private VictoryPointsDatabaseUtil() {}

    public static void updateVictoryPointsByPlayerId(long playerId, double victoryPointsToAdd) throws SQLException {
        String sqlQuery = "UPDATE VICTORY_POINTS_PLAYER SET victoryPoints = victoryPoints + ? WHERE playerId = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setDouble(1, victoryPointsToAdd);
            preparedStatement.setLong(2, playerId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No player found with ID: " + playerId);
            }
        } catch (SQLException ex) {
            String message = "An error occurred while adding victory points.";
            throw new SQLException(message, ex);
        }
    }

    public static List<VictoryPointsPlayer> getAllVictoryPointsPlayers() throws SQLException {
        List<VictoryPointsPlayer> players = new ArrayList<>();
        String sqlQuery = "SELECT playerId, victoryPoints FROM VICTORY_POINTS_PLAYER";

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                long playerId = resultSet.getLong("playerId");
                double victoryPoints = resultSet.getDouble("victoryPoints");

                VictoryPointsPlayer player = new VictoryPointsPlayer();
                player.setPlayerId(playerId);
                player.setVictoryPoints(victoryPoints);

                players.add(player);
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving all victory points players.";
            throw new SQLException(message, ex);
        }

        return players;
    }

    public static VictoryPointsPlayer getVictoryPointsPlayerById(long playerId) throws SQLException {
        String sqlQuery = "SELECT playerId, victoryPoints FROM VICTORY_POINTS_PLAYER WHERE playerId = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, playerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    VictoryPointsPlayer player = new VictoryPointsPlayer();
                    player.setPlayerId(resultSet.getLong("playerId"));
                    player.setVictoryPoints(resultSet.getDouble("victoryPoints"));
                    return player;
                } else {
                    return null;
                }
            }

        } catch (SQLException ex) {
            String message = "An error occurred while retrieving victory points for player ID: " + playerId;
            throw new SQLException(message, ex);
        }
    }
}
