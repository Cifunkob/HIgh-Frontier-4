package hr.tvz.pejkunovic.highfrontier.threads;

import hr.tvz.pejkunovic.highfrontier.UniverseMapController;
import hr.tvz.pejkunovic.highfrontier.model.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerTwoServerThread implements Runnable{
    public static final Integer PLAYER_TWO_SERVER_PORT = 1101;
    public static final Integer PLAYER_ONE_SERVER_PORT = 1100;
    public static final String HOSTNAME = "localhost";
    private static boolean running=true;

    private final UniverseMapController universeMapController;

    public PlayerTwoServerThread(UniverseMapController universeMapController) {
        this.universeMapController = universeMapController;
    }

    @Override
    public void run() {
        acceptRequestsFromPlayerOne();
    }

    private void acceptRequestsFromPlayerOne() {
        try (ServerSocket serverSocket = new ServerSocket(PLAYER_TWO_SERVER_PORT)){

            while (running) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() ->  processSerializablePlayerOneClient(clientSocket)).start();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSerializablePlayerOneClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             )
        {
            GameState receivedGameState = (GameState) ois.readObject();
            universeMapController.refreshGameState(receivedGameState,1L);
            oos.writeObject(Boolean.TRUE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


