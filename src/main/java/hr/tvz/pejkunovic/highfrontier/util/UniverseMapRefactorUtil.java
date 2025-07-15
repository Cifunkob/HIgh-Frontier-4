package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.HelloApplication;
import hr.tvz.pejkunovic.highfrontier.UniverseMapController;
import hr.tvz.pejkunovic.highfrontier.database.DeploymentDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.exception.UniverseException;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerOneClientThread;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerOneServerThread;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerTwoClientThread;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerTwoServerThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UniverseMapRefactorUtil {

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private UniverseMapRefactorUtil() {
        throw new IllegalStateException("Utility class");
    }
    private static volatile boolean alertShown = false;
    public static final String PLAYER1="player1";

    public static void startNetworking(UniverseMapController universeMapController){
        if(HelloApplication.getPlayerName().equals("player2")) {
            PlayerTwoServerThread playerTwoServerThread = new PlayerTwoServerThread(universeMapController);
            Thread thread = new Thread(playerTwoServerThread);
            thread.start();
        }
        else if(HelloApplication.getPlayerName().equals(PLAYER1)) {
            PlayerOneServerThread playerOneServerThread = new PlayerOneServerThread(universeMapController);
            Thread thread = new Thread(playerOneServerThread);
            thread.start();
        }
    }

    public static void handleEndTurn(
            UniverseMapController controller,
            AnchorPane anchorPane,
            Label turnLabel,
            TableView<VictoryPointsPlayer> tableView,
            List<VictoryPointsPlayer> victoryPointsPlayers,
            Player player
    ) {
        try {
            controller.setTurn(controller.getTurn()+1);
            controller.setTurnToDisplay(controller.getTurnToDisplay()+1);

            if (controller.getTurnToDisplay()== 6) {
                turnLabel.setText("Finished");
            } else {
                turnLabel.setText(String.valueOf(controller.getTurnToDisplay()));
            }

            List<Deployment> deployments = DeploymentDatabaseUtil.getDeploymentsByPlayerId(player.getId());
            ButtonUtil.updateButtonsBasedOnTurn(anchorPane, controller.getTurn(), HelloApplication.getPlayerName());

            for (Deployment deployment : deployments) {
                VictoryPointsUtil.updateVictoryPoints(victoryPointsPlayers, deployment);
                controller.initialize();
            }

            Runnable threadTask;
            if (HelloApplication.getPlayerName().equals(PLAYER1)) {
                threadTask = new PlayerOneClientThread(deployments, victoryPointsPlayers, controller.getTurn());
            } else {
                threadTask = new PlayerTwoClientThread(deployments, victoryPointsPlayers, controller.getTurn());
            }

            new Thread(threadTask).start();
            tableView.setItems(FXCollections.observableList(victoryPointsPlayers));

        } catch (SQLException e) {
            throw new UniverseException(e);
        }
    }

    public static void changeButtonStyleNeighbour(Map<Long, Button> buttonMap, List<Long> spaceLocationNeighbours, Player player){
        buttonMap.forEach((id, button) -> {
            if (spaceLocationNeighbours.contains(id)) {
                button.setStyle("-fx-background-color: green;");
            } else if (id.equals(player.getLocationId())) {
                button.setStyle("-fx-background-color: blue;");
            } else {
                button.setStyle("-fx-background-color: red;");
            }
        });
    }

    public static Player getPlayerByCurrentName() {
        try {
            String playerName = HelloApplication.getPlayerName();
            if (PLAYER1.equals(playerName)) {
                return PlayerDatabaseUtil.getPlayerById(1L);
            } else if ("player2".equals(playerName)) {
                return PlayerDatabaseUtil.getPlayerById(2L);
            } else {
                throw new UniverseException("Unknown player name: " + playerName);
            }
        } catch (SQLException e) {
            throw new UniverseException(e);
        }
    }

    public static void startGameFinishCheckerThread(UniverseMapController controller) {
        scheduler.scheduleAtFixedRate(() -> {
            if (alertShown) {
                scheduler.shutdown();
                return;
            }

            int currentTurn = controller.getTurn();
            if (currentTurn >= 11) {
                alertShown = true;

                Platform.runLater(() -> {
                    String winnerName = getWinnerName(controller.getVictoryPointsPlayers());
                    checkTurnAndShowWinner(currentTurn, winnerName);
                });

                scheduler.shutdown();
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public static void checkTurnAndShowWinner(int turn, String winnerName) {
        if (turn == 11) {
            Platform.runLater(() -> {
                if(!winnerName.equals("Draw")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText("Winner Declared");
                    alert.setContentText("Congratulations " + winnerName + "! You have won the game.");
                    alert.showAndWait();
                    Platform.exit();
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText("No winner");
                    alert.setContentText("The game ended in a draw. No one won the game.");
                    alert.showAndWait();
                    Platform.exit();
                }
            });
        }
    }

    public static String getWinnerName(List<VictoryPointsPlayer> victoryPointsPlayers) {

        VictoryPointsPlayer p1 = victoryPointsPlayers.get(0);
        VictoryPointsPlayer p2 = victoryPointsPlayers.get(1);

        if (p1.getVictoryPoints() .equals(p2.getVictoryPoints())) {
            return "Draw";
        }
        VictoryPointsPlayer winner = p1.getVictoryPoints() > p2.getVictoryPoints() ? p1 : p2;

        try {
            return PlayerDatabaseUtil.getPlayerById(winner.getPlayerId()).getName();
        } catch (SQLException e) {
            throw new UniverseException("Failed to retrieve winner's name", e);
        }
    }
}

