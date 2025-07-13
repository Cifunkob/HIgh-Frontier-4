package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.DeploymentDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.SpaceLocationDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;


public class DeploymentController {
    @FXML
    TableView<Deployment> tableView;
    @FXML
    TableColumn<Deployment,String> locationColumn;
    @FXML
    TableColumn<Deployment,String> roverColumn;
    private Player player;
    public void setUpInitial(){
        try {
            List<Deployment> deployments= DeploymentDatabaseUtil.getDeploymentsByPlayerId(player.getId());
            locationColumn.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(
                            SpaceLocationDatabaseUtil.getSpaceLocationById(celldata.getValue().getSpaceLocationId()).getName());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            roverColumn.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(
                            RoverCardsDatabaseUtil.getRoverCardById(celldata.getValue().getRoverCardId()).getName());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            tableView.setItems(FXCollections.observableList(deployments));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setUp(Player player){
        this.player=player;
        setUpInitial();
    }
}
