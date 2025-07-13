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

    @Override
    public void run() {
        System.out.println("I ENTERED THE SERVER THREAD FOR PLAYER TWO!");
        acceptRequestsFromPlayerOne();
    }

    private static void acceptRequestsFromPlayerOne() {
        try (ServerSocket serverSocket = new ServerSocket(PLAYER_TWO_SERVER_PORT)){
            System.err.println("Player two server listening on port: " + serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Player one client connected from port: " + clientSocket.getPort());
                new Thread(() ->  processSerializablePlayerOneClient(clientSocket)).start();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processSerializablePlayerOneClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             )
        {
            GameState receivedGameState = (GameState) ois.readObject();
            UniverseMapController.refreshGameState(receivedGameState,1L);
            System.out.println("Current game state received!");
            System.out.println(receivedGameState.toString());
            oos.writeObject(Boolean.TRUE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

//MORAS IMPLEMENTIRATI REFRESH GAMESTATE

