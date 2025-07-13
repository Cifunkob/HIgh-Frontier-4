package hr.tvz.pejkunovic.highfrontier.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardUtil {

    public void addMotorToPlayer(long playerId, long motorId) throws SQLException {
        String sqlInsert = "INSERT INTO player_motor (player_id, motor_id) VALUES (?, ?)";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {

            preparedStatement.setLong(1, playerId);
            preparedStatement.setLong(2, motorId);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String message = "Failed to add motor card to player.";
            throw new SQLException(message, ex);
        }
    }

    public boolean playerHasMotor(long playerId) throws SQLException {
        String sqlQuery = "SELECT 1 FROM player_motor WHERE player_id = ? LIMIT 1";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, playerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // true if exists, false otherwise
            }

        } catch (SQLException ex) {
            String message = "Failed to check if player has motor.";
            throw new SQLException(message, ex);
        }
    }

    public void addRoverToPlayer(long playerId, long roverId) throws SQLException {
        String sqlInsert = "INSERT INTO player_rovers (player_id, rover_id) VALUES (?, ?)";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {

            preparedStatement.setLong(1, playerId);
            preparedStatement.setLong(2, roverId);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String message = "Failed to add rover card to player.";
            throw new SQLException(message, ex);
        }
    }

    public Long getMotorIdByPlayerId(Long playerId) throws SQLException {
        String sqlQuery = "SELECT motor_id FROM player_motor WHERE player_id = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, playerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("motor_id");
                } else {
                    return null;
                }
            }

        } catch (SQLException ex) {
            String message = "Failed to retrieve motor ID for player.";
            throw new SQLException(message, ex);
        }
    }

    public Optional<List<Long>> getRoversByPlayerId(long playerId) {
        String sql = "SELECT rover_id FROM player_rovers WHERE player_id = ?";
        List<Long> roverIds = new ArrayList<>();

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    roverIds.add(resultSet.getLong("rover_id"));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }

        return roverIds.isEmpty() ? Optional.empty() : Optional.of(roverIds);
    }

    public boolean removeRoverFromPlayer(long playerId, long roverId) {
        String sql = "DELETE FROM player_rovers WHERE player_id = ? AND rover_id = ? LIMIT 1";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playerId);
            statement.setLong(2, roverId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
