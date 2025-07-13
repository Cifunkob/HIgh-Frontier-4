package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.chat.ChatRemoteService;
import hr.tvz.pejkunovic.highfrontier.database.*;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.GameState;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerOneClientThread;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerTwoClientThread;
import hr.tvz.pejkunovic.highfrontier.util.*;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.*;

public class UniverseMapController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button mercuryButton;
    @FXML
    private Button laGrangeM2VButton;
    @FXML
    private Button venusButton;
    @FXML
    private Button zoozveButton;
    @FXML
    private Button laGrangeV2EButton;
    @FXML
    private Button earthButton;
    @FXML
    private Button lunaButton;
    @FXML
    private Button laGrangeE2MButton;
    @FXML
    private Button marsButton;
    @FXML
    private Button fobosButton;
    @FXML
    private Button deimosButton;
    @FXML
    private Button laGrangeM2JButton;
    @FXML
    private Button jupiterButton;
    @FXML
    private Button ortozijaButton;
    @FXML
    private Button haldenaButton;
    @FXML
    private Button tebaButton;
    @FXML
    private Button hermipaButton;
    @FXML
    private Button europaButton;
    @FXML
    private Button karmaButton;
    @FXML
    private TableView<VictoryPointsPlayer> tableView;
    @FXML
    private TableColumn<VictoryPointsPlayer, String> nameColumn;
    @FXML
    private TableColumn<VictoryPointsPlayer, String> pointsColumn;
    @FXML
    private Label turnLabel;
    @FXML
    private TextField chatMessageTextField;
    @FXML
    private TextArea chatMessageTextArea;
    ControllerOpenUtil controllerOpenUtil=new ControllerOpenUtil();
    ButtonUtil buttonUtil=new ButtonUtil();
    private Player player;
    protected Map<Long, Button> buttonMap= new HashMap<>();
    protected List<Long> spaceLocationNeighbours=new ArrayList<>();
    private static Integer turn;
    private static Player opponent;
    private static List<Deployment> opponentDeployments= new ArrayList<>();
    private static VictoryPointsPlayer  opponentVictoryPoints;
    private static List<VictoryPointsPlayer> victoryPointsPlayers=new ArrayList<>();
    private ChatRemoteService chatRemoteService;
    private VictoryPointsPlayer victoryPointsPlayer1=new VictoryPointsPlayer(1L,0.0);
    private VictoryPointsPlayer victoryPointsPlayer2=new VictoryPointsPlayer(2L,0.0);

    public void initialize() {
        try {
            spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(3L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupButton();
        setupPlayer();
        changeButtonStyleNeighbour();
        if(victoryPointsPlayers.isEmpty() || victoryPointsPlayers==null) {
            victoryPointsPlayers.add(victoryPointsPlayer1);
            victoryPointsPlayers.add(victoryPointsPlayer2);
            turn=0;
            turnLabel.setText(turn.toString());
        }
       // try {
            //List<VictoryPointsPlayer> victoryPointsPlayer= VictoryPointsDatabaseUtil.getAllVictoryPointsPlayers();
            nameColumn.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(
                            PlayerDatabaseUtil.getPlayerById(celldata.getValue().getPlayerId()).getName());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pointsColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getVictoryPoints().toString()));
            tableView.setItems(FXCollections.observableList(victoryPointsPlayers));
            startButtonToggleThread(anchorPane);

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1098);
            chatRemoteService = (ChatRemoteService) registry.lookup(ChatRemoteService.REMOTE_OBJECT_NAME);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        Timeline chatMessagesTimeline = CHatUtil.ChatUtils.getChatTimeline(chatRemoteService, chatMessageTextArea);
        chatMessagesTimeline.play();
            /* } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

    }
    public void openSpaceObjectMenu(ActionEvent event){
        controllerOpenUtil.openSpaceObjectMenu(event,player,this);
    }

    public void openRoverShop(){
       controllerOpenUtil.openRoverShop(player);
    }

    public void openEngineShop(){
       controllerOpenUtil.openEngineShop(player);
    }

    public void openPlayerResources(){
        controllerOpenUtil.openResourceInformation(player);
    }

    public void openDeployment(){
        controllerOpenUtil.openDeployment(player);
    }

    public void openOpponentResources(){controllerOpenUtil.openOpponentResourcesInformation(opponent,opponentVictoryPoints,opponentDeployments);}

    public void setupPlayer(){
        try {
            if(HelloApplication.playerName.equals("player1")) {
                player = PlayerDatabaseUtil.getPlayerById(1L);
                System.out.println("Player1");
            } else if (HelloApplication.playerName.equals("player2")) {
                player = PlayerDatabaseUtil.getPlayerById(2L);
                System.out.println("Player2");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupButton(){
        buttonUtil.addButtonsToMap(buttonMap,
                mercuryButton, venusButton, earthButton, marsButton, jupiterButton, lunaButton, fobosButton, deimosButton, europaButton, tebaButton,
                haldenaButton, hermipaButton, karmaButton, ortozijaButton, zoozveButton, laGrangeM2VButton, laGrangeV2EButton, laGrangeE2MButton, laGrangeM2JButton
        );
    }
    public void changeButtonStyleNeighbour(){
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

    public void updatePlayerLocation(Long newLocationId) {
        player.setLocationId(newLocationId);
        try {
            spaceLocationNeighbours = ConnectionSpaceLocationUtil.getConnectedLocationIds(newLocationId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        changeButtonStyleNeighbour();
    }

    public void endTurn(){
        try {
            turnLabel.setText(turn.toString());
            turn++;
            List<Deployment> deployments= DeploymentDatabaseUtil.getDeploymentsByPlayerId(player.getId());
            ButtonUtil.updateButtonsBasedOnTurn(anchorPane, turn, HelloApplication.playerName);
            for(Deployment deployment : deployments){
                //VictoryPointsDatabaseUtil.updateVictoryPointsByPlayerId(player.getId(), PointsCalculationUtil.calculatePoints(deployment));
                VictoryPointsUtil.updateVictoryPoints(victoryPointsPlayers,deployment);
                initialize();
            }
            if (HelloApplication.playerName.equals("player1")) {
                PlayerOneClientThread playerOneClientThread = new PlayerOneClientThread(deployments,victoryPointsPlayers,turn);
                Thread thread = new Thread(playerOneClientThread);
                thread.start();
            } else if (HelloApplication.playerName.equals("player2")) {
                PlayerTwoClientThread playerTwoClientThread = new PlayerTwoClientThread(deployments,victoryPointsPlayers,turn);
                Thread thread = new Thread(playerTwoClientThread);
                thread.start();
            }
            tableView.setItems(FXCollections.observableList(victoryPointsPlayers));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void refreshGameState(GameState gameState, Long playerId){
        try {
            turn= gameState.getTurnNumber();
            opponent=PlayerDatabaseUtil.getPlayerById(playerId);
            opponentVictoryPoints= VictoryPointsDatabaseUtil.getVictoryPointsPlayerById(playerId);
            victoryPointsPlayers=gameState.getVictoryPointsPlayers();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Deployment deployment : gameState.getDeployments()){
            if(Objects.equals(deployment.getPlayerId(), playerId)){
                opponentDeployments.add(deployment);
            }
        }
    }
    public void startButtonToggleThread(Parent root) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    ButtonUtil.updateButtonsBasedOnTurn(root, turn, HelloApplication.playerName);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    public void sendChatMessage() {
        CHatUtil.ChatUtils.sendChatMessage(chatMessageTextField.getText(), chatRemoteService);
    }
}



/* Izbrisi nek stoji dok je ispod 200
public void updateColors(){
    try {
        spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(player.getLocationId());
        changeButtonStyleNeighbour();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
*/
