package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.ConnectionSpaceLocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectionSpaceLocationUtil {
    private ConnectionSpaceLocationUtil() {}

    public static List<ConnectionSpaceLocation> getAllConnections() throws SQLException {
        List<ConnectionSpaceLocation> connectionList = new ArrayList<>();

        String sqlQuery = "SELECT id, sourceId, destinationId, thrustCost FROM Connection_Space_Location";

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet connectionResultSet = sqlStatement.executeQuery(sqlQuery)) {

            while (connectionResultSet.next()) {
                ConnectionSpaceLocation newConnection = getConnectionFromResultSet(connectionResultSet);
                connectionList.add(newConnection);
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving all connections.";
            throw new SQLException(message, ex);
        }

        return connectionList;
    }

    public static List<Long> getConnectedLocationIds(Long spaceLocationId) throws SQLException {
        List<Long> connectedLocationIds = new ArrayList<>();

        String sqlQuery = "SELECT sourceId, destinationId " +
                "FROM Connection_Space_Location " +
                "WHERE sourceId = ? OR destinationId = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, spaceLocationId);
            preparedStatement.setLong(2, spaceLocationId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long sourceId = resultSet.getLong("sourceId");
                    Long destinationId = resultSet.getLong("destinationId");

                    if (sourceId.equals(spaceLocationId)) {
                        connectedLocationIds.add(destinationId);
                    } else {
                        connectedLocationIds.add(sourceId);
                    }
                }
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving connected location IDs for SpaceLocation ID: " + spaceLocationId;
            throw new SQLException(message, ex);
        }

        return connectedLocationIds;
    }

    private static ConnectionSpaceLocation getConnectionFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long sourceId = resultSet.getLong("sourceId");
        Long destinationId = resultSet.getLong("destinationId");
        Integer thrustCost = resultSet.getInt("thrustCost");

        return new ConnectionSpaceLocation(id, sourceId, destinationId, thrustCost);
    }

    public static Optional<ConnectionSpaceLocation> getConnectionByLocationIds(Long sourceId, Long destinationId) throws SQLException {
        String sqlQuery = "SELECT id, sourceId, destinationId, thrustCost " +
                "FROM Connection_Space_Location " +
                "WHERE (sourceId = ? AND destinationId = ?) OR (sourceId = ? AND destinationId = ?)";
        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, sourceId);
            preparedStatement.setLong(2, destinationId);
            preparedStatement.setLong(3, destinationId);
            preparedStatement.setLong(4, sourceId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getConnectionFromResultSet(resultSet));
                }
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving the connection between locations: " +
                    sourceId + " and " + destinationId;
            throw new SQLException(message, ex);
        }
        return Optional.empty();
    }




}
