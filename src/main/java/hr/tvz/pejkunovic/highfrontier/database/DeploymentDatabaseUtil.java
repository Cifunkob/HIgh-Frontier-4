package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.Deployment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeploymentDatabaseUtil {

    public static void createDeployment(Deployment deployment) throws SQLException {
        String sql = "INSERT INTO deployed_outposts (player_id, rover_id, space_location_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, deployment.getPlayerId());
            stmt.setLong(2, deployment.getRoverCardId());
            stmt.setLong(3, deployment.getSpaceLocationId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            String message = "Failed to create deployment.";
            throw new SQLException(message, ex);
        }
    }

    public static List<Deployment> getAllDeployments() throws SQLException {
        String sql = "SELECT player_id, rover_id, space_location_id FROM deployed_outposts";
        List<Deployment> deployments = new ArrayList<>();

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                deployments.add(getDeploymentFromResultSet(rs));
            }

        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve all deployments.", ex);
        }

        return deployments;
    }

    public static List<Deployment> getDeploymentsByPlayerId(Long playerId) throws SQLException {
        return getDeploymentsByAttribute("player_id", playerId);
    }

    public static List<Deployment> getDeploymentsByRoverCardId(Long roverCardId) throws SQLException {
        return getDeploymentsByAttribute("rover_id", roverCardId);
    }

    public static List<Deployment> getDeploymentsBySpaceLocationId(Long spaceLocationId) throws SQLException {
        return getDeploymentsByAttribute("space_location_id", spaceLocationId);
    }

    private static List<Deployment> getDeploymentsByAttribute(String column, Long value) throws SQLException {
        String sql = "SELECT player_id, rover_id, space_location_id FROM deployed_outposts WHERE " + column + " = ?";
        List<Deployment> deployments = new ArrayList<>();

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    deployments.add(getDeploymentFromResultSet(rs));
                }
            }

        } catch (SQLException ex) {
            throw new SQLException("Failed to retrieve deployments by " + column + ".", ex);
        }

        return deployments;
    }

    private static Deployment getDeploymentFromResultSet(ResultSet rs) throws SQLException {
        Long playerId = rs.getLong("player_id");
        Long roverCardId = rs.getLong("rover_id");
        Long spaceLocationId = rs.getLong("space_location_id");
        return new Deployment(playerId, roverCardId, spaceLocationId);
    }

    public boolean hasPlayerDeployedOnPlanet(long playerId, long spaceLocationId) throws SQLException {
        String sql = "SELECT 1 FROM deployed_outposts WHERE player_id = ? AND space_location_id = ? LIMIT 1";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, playerId);
            stmt.setLong(2, spaceLocationId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new SQLException("Failed to check if player has deployed on this planet.", ex);
        }
    }
}

