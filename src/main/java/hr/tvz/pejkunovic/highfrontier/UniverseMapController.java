package hr.tvz.pejkunovic.highfrontier;
import hr.tvz.pejkunovic.highfrontier.chat.ChatRemoteService;
import hr.tvz.pejkunovic.highfrontier.database.*;
import hr.tvz.pejkunovic.highfrontier.exception.UniverseException;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.GameState;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;
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
import lombok.Data;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.*;

@Data
public class UniverseMapController {
    @FXML private Button mercuryButton;
    @FXML private Button laGrangeM2VButton;
    @FXML private Button venusButton;
    @FXML private Button zoozveButton;
    @FXML private Button laGrangeV2EButton;
    @FXML private Button earthButton;
    @FXML private Button lunaButton;
    @FXML private Button laGrangeE2MButton;
    @FXML private Button marsButton;
    @FXML private Button fobosButton;
    @FXML private Button deimosButton;
    @FXML private Button laGrangeM2JButton;
    @FXML private Button jupiterButton;
    @FXML private Button ortozijaButton;
    @FXML private Button haldenaButton;
    @FXML private Button tebaButton;
    @FXML private Button hermipaButton;
    @FXML private Button europaButton;
    @FXML private Button karmaButton;
    @FXML private AnchorPane anchorPane;
    @FXML private TableView<VictoryPointsPlayer> tableView;
    @FXML private TableColumn<VictoryPointsPlayer, String> nameColumn;
    @FXML private TableColumn<VictoryPointsPlayer, String> pointsColumn;
    @FXML private Label turnLabel;
    @FXML private TextField chatMessageTextField;
    @FXML private TextArea chatMessageTextArea;
    private ControllerOpenUtil controllerOpenUtil=new ControllerOpenUtil();
    private ButtonUtil buttonUtil=new ButtonUtil();
    private Player player;
    protected Map<Long, Button> buttonMap= new HashMap<>();
    protected List<Long> spaceLocationNeighbours=new ArrayList<>();
    private Integer turn=1;
    private Integer turnToDisplay=1;
    private Player opponent;
    private List<Deployment> opponentDeployments= new ArrayList<>();
    private List<VictoryPointsPlayer> victoryPointsPlayers=new ArrayList<>();
    private ChatRemoteService chatRemoteService;
    private VictoryPointsPlayer victoryPointsPlayer1=new VictoryPointsPlayer(1L,0.0);
    private VictoryPointsPlayer victoryPointsPlayer2=new VictoryPointsPlayer(2L,0.0);

    public void initialize() {
        UniverseMapRefactorUtil.startNetworking(this);

        setupButton();
        setupPlayer();
        try {
            spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(player.getLocationId());
        } catch (SQLException e) {
            throw new UniverseException(e);
        }
        if(victoryPointsPlayers.isEmpty()) {
            changeButtonStyleNeighbour();
            victoryPointsPlayers.add(victoryPointsPlayer1);
            victoryPointsPlayers.add(victoryPointsPlayer2);
            turnLabel.setText(turnToDisplay.toString());
        }
            nameColumn.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(PlayerDatabaseUtil.getPlayerById(celldata.getValue().getPlayerId()).getName());
                } catch (SQLException e) {
                    throw new UniverseException(e);
                }
            });
            pointsColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getVictoryPoints().toString()));
            tableView.setItems(FXCollections.observableList(victoryPointsPlayers));
            startButtonToggleThread(anchorPane);
            UniverseMapRefactorUtil.startGameFinishCheckerThread(this);

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1098);
            chatRemoteService = (ChatRemoteService) registry.lookup(ChatRemoteService.REMOTE_OBJECT_NAME);
        } catch (RemoteException | NotBoundException e) {
            throw new UniverseException(e);
        }

        Timeline chatMessagesTimeline = ChatUtil.ChatUtils.getChatTimeline(chatRemoteService, chatMessageTextArea);
        chatMessagesTimeline.play();
    }
    public void openSpaceObjectMenu(ActionEvent event){controllerOpenUtil.openSpaceObjectMenu(event,player,this);}

    public void openRoverShop(){controllerOpenUtil.openRoverShop(player);}

    public void openEngineShop(){controllerOpenUtil.openEngineShop(player);}

    public void openPlayerResources(){controllerOpenUtil.openResourceInformation(player);}

    public void openDeployment(){controllerOpenUtil.openDeployment(player);}

    public void openOpponentResources(){controllerOpenUtil.openOpponentResourcesInformation(opponent);}

    public void setupPlayer(){
        player= UniverseMapRefactorUtil.getPlayerByCurrentName();
    }

    public void setupButton(){
        buttonUtil.addButtonsToMap(buttonMap, mercuryButton, venusButton, earthButton, marsButton, jupiterButton, lunaButton, fobosButton, deimosButton, europaButton, tebaButton, haldenaButton, hermipaButton, karmaButton, ortozijaButton, zoozveButton, laGrangeM2VButton, laGrangeV2EButton, laGrangeE2MButton, laGrangeM2JButton
        );
    }
    public void changeButtonStyleNeighbour(){
        UniverseMapRefactorUtil.changeButtonStyleNeighbour(buttonMap, spaceLocationNeighbours, player);
    }

    public void updatePlayerLocation(Long newLocationId) {
        player.setLocationId(newLocationId);
        try {
            PlayerDatabaseUtil.updatePlayerLocation(player.getId(), player.getLocationId());
            spaceLocationNeighbours = ConnectionSpaceLocationUtil.getConnectedLocationIds(newLocationId);
            changeButtonStyleNeighbour();
        } catch (SQLException e) {
            throw new UniverseException(e);
        }
    }

    public void endTurn(){
        UniverseMapRefactorUtil.handleEndTurn(this, anchorPane, turnLabel, tableView, victoryPointsPlayers, player);
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setTurnToDisplay(int turnToDisplay) {
        this.turnToDisplay = turnToDisplay;
    }

    public void refreshGameState(GameState gameState, Long playerId){
        try {
            setTurn(gameState.getTurnNumber());
            turnLabel.setText(turnToDisplay.toString());
            opponent=PlayerDatabaseUtil.getPlayerById(playerId);
            victoryPointsPlayers=gameState.getVictoryPointsPlayers();
            tableView.setItems(FXCollections.observableList(victoryPointsPlayers));
        } catch (SQLException e) {
            throw new UniverseException(e);
        }
        for(Deployment deployment : gameState.getDeployments()){
            if(Objects.equals(deployment.getPlayerId(), playerId)){
                opponentDeployments.add(deployment);
            }
        }
    }
    public void startButtonToggleThread(Parent root) {
        Thread.startVirtualThread(() -> {
            while (true) {
                try {
                    ButtonUtil.updateButtonsBasedOnTurn(root, turn, HelloApplication.getPlayerName());
                    Thread.sleep(500);
                } catch (InterruptedException _) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
    public void sendChatMessage() {
        ChatUtil.ChatUtils.sendChatMessage(chatMessageTextField.getText(), chatRemoteService);
    }
    public void generateDocumentation(){DocumentationUtil.generateDocumentation();}
}