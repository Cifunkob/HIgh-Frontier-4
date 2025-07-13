package hr.tvz.pejkunovic.highfrontier.threads;

import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.GameState;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;
import hr.tvz.pejkunovic.highfrontier.util.GameStateUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class PlayerOneClientThread implements Runnable {

    public static final Integer PLAYER_TWO_SERVER_PORT = 1101;
    public static final Integer PLAYER_ONE_SERVER_PORT = 1100;
    public static final String HOSTNAME = "localhost";
    private List<Deployment> deployments;
    private List<VictoryPointsPlayer> victoryPointsPlayers;
    private Integer playerTurn;

    public PlayerOneClientThread(List<Deployment> deployments, List<VictoryPointsPlayer> victoryPointsPlayers, Integer playerTurn) {
        this.deployments = deployments;
        this.victoryPointsPlayers = victoryPointsPlayers;
        this.playerTurn = playerTurn;
    }

    @Override
    public void run() {
        sendRequest();
    }

    private void sendRequest() {
        // Closing socket will also close the socket's InputStream and OutputStream.
        try (Socket clientSocket = new Socket(HOSTNAME, PLAYER_TWO_SERVER_PORT)){
            System.err.println("Client is connecting to " + clientSocket.getInetAddress() + ":" +clientSocket.getPort());

            //sendPrimitiveRequest(clientSocket);
            sendSerializableRequest(clientSocket);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendSerializableRequest(Socket client) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        GameState currentGameState = GameStateUtils.createCurrentGameState(deployments, victoryPointsPlayers, playerTurn );
        oos.writeObject(currentGameState);
        System.out.println("Game state received confirmation: " + (Boolean) ois.readObject());
    }
}