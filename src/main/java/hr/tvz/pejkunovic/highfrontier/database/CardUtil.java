package hr.tvz.pejkunovic.highfrontier.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String sqlInsert = "INSERT INTO player_rover (player_id, rover_id) VALUES (?, ?)";

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
}
