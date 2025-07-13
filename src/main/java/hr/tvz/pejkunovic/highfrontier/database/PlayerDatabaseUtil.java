package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDatabaseUtil {

    private PlayerDatabaseUtil() {}

    public static List<Player> getAllPlayers() throws SQLException {
        List<Player> playerList = new ArrayList<>();

        String sqlQuery = "SELECT id, name, fuel, water, locationId FROM player";

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet playerResultSet = sqlStatement.executeQuery(sqlQuery)) {

            while (playerResultSet.next()) {
                Player newPlayer = getPlayerFromResultSet(playerResultSet);
                playerList.add(newPlayer);
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving all players.";
            System.out.println(ex.getErrorCode());
            throw new SQLException(message, ex);
        }

        return playerList;
    }

    public static Player getPlayerById(Long id) throws SQLException {
        Player player = new Player();

        String sqlQuery = "SELECT id, name, fuel, water, locationId FROM player WHERE id = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    player = getPlayerFromResultSet(resultSet);
                }
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving the player by ID.";
            throw new SQLException(message, ex);
        }

        return player;
    }

    private static Player getPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Integer fuel = resultSet.getInt("fuel");
        Integer water = resultSet.getInt("water");
        Long locationId = resultSet.getLong("locationId");

        return new Player(id, name, fuel, water, locationId, null, null);
    }

    public static void updatePlayerFuel(Long playerId, Integer newFuel) throws SQLException {
        String sql = "UPDATE player SET fuel = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newFuel);
            stmt.setLong(2, playerId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No player found with ID: " + playerId);
            }
        }
    }

    public static void updatePlayerWater(Long playerId, Integer newWater) throws SQLException {
        String sql = "UPDATE player SET water = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newWater);
            stmt.setLong(2, playerId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No player found with ID: " + playerId);
            }
        }
    }

}
