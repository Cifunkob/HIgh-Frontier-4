package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.cardModels.MotorCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotorCardsDatabaseUtil {

    private MotorCardsDatabaseUtil() {}

    public static List<MotorCard> getAllMotorCards() throws SQLException {
        List<MotorCard> motorCardList = new ArrayList<>();

        String sqlQuery = "SELECT id, name, mass, cost, thrust, isp FROM motor_cards";

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                MotorCard motorCard = getMotorCardFromResultSet(resultSet);
                motorCardList.add(motorCard);
            }

        } catch (SQLException ex) {
            String message = "An error occurred while retrieving all motor cards.";
            throw new SQLException(message, ex);
        }

        return motorCardList;
    }

    public static MotorCard getMotorCardById(Long id) throws SQLException {
        MotorCard motorCard = null;

        String sqlQuery = "SELECT id, name, mass, cost, thrust, isp FROM motor_cards WHERE id = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    motorCard = getMotorCardFromResultSet(resultSet);
                }
            }

        } catch (SQLException ex) {
            String message = "An error occurred while retrieving the motor card by ID.";
            throw new SQLException(message, ex);
        }

        return motorCard;
    }

    private static MotorCard getMotorCardFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int mass = resultSet.getInt("mass");
        int cost = resultSet.getInt("cost");
        int thrust = resultSet.getInt("thrust");
        int isp = resultSet.getInt("isp");

        return new MotorCard(id, name, mass, cost, thrust, isp);
    }
}