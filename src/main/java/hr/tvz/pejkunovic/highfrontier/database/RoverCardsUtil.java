package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoverCardsUtil {

    public RoverCardsUtil() {}

    public static List<RoverCard> getAllRoverCards() throws SQLException {
        List<RoverCard> roverCardList = new ArrayList<>();

        String sqlQuery = "SELECT id, name, mass, cost, efficiency FROM rover_cards";

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                RoverCard roverCard = getRoverCardFromResultSet(resultSet);
                roverCardList.add(roverCard);
            }

        } catch (SQLException ex) {
            String message = "An error occurred while retrieving all rover cards.";
            throw new SQLException(message, ex);
        }

        return roverCardList;
    }

    public static RoverCard getRoverCardById(Long id) throws SQLException {
        RoverCard roverCard = null;

        String sqlQuery = "SELECT id, name, mass, cost, efficiency FROM rover_cards WHERE id = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roverCard = getRoverCardFromResultSet(resultSet);
                }
            }

        } catch (SQLException ex) {
            String message = "An error occurred while retrieving the rover card by ID.";
            throw new SQLException(message, ex);
        }

        return roverCard;
    }

    private static RoverCard getRoverCardFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int mass = resultSet.getInt("mass");
        int cost = resultSet.getInt("cost");
        int efficiency = resultSet.getInt("efficiency");

        return new RoverCard(id, name, mass, cost, efficiency);
    }
}