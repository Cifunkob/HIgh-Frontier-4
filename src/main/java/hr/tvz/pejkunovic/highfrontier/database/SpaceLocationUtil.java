package hr.tvz.pejkunovic.highfrontier.database;

import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.LocationType;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceLocationUtil {

    private SpaceLocationUtil() {}
    public static List<SpaceLocation> getAllSpaceLocations() throws SQLException {
        List<SpaceLocation> spaceLocationList = new ArrayList<>();

        String sqlQuery = "SELECT id, name, type, resource_richness FROM space_location";

        try (Connection connection = DatabaseManager.connectToDatabase();
             Statement sqlStatement = connection.createStatement();
             ResultSet spaceLocationResultSet = sqlStatement.executeQuery(sqlQuery)) {

            while (spaceLocationResultSet.next()) {
                SpaceLocation newLocation = getSpaceLocationFromResultSet(spaceLocationResultSet);
                spaceLocationList.add(newLocation);
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving all space locations.";
            System.out.println(ex.getErrorCode());
            throw new SQLException(message, ex);

        }

        return spaceLocationList;
    }

    public static SpaceLocation getSpaceLocationByName(String name) throws SQLException {
        SpaceLocation spaceLocation = new SpaceLocation();

        String sqlQuery = "SELECT id, name, type, resource_richness FROM SPACE_LOCATION WHERE name = ?";

        try (Connection connection = DatabaseManager.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spaceLocation = getSpaceLocationFromResultSet(resultSet);
                }
            }
        } catch (SQLException ex) {
            String message = "An error occurred while retrieving the space location by name.";
            throw new SQLException(message, ex);
        }

        return spaceLocation;
    }

    private static SpaceLocation getSpaceLocationFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String typeString = resultSet.getString("type");
        Integer resourceRichness = resultSet.getInt("resource_richness");

        LocationType type = LocationType.valueOf(typeString.toUpperCase());

        return new SpaceLocation(id, name, type, resourceRichness);
    }
}

