package hr.tvz.pejkunovic.highfrontier.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

    public class ChatServer {

        private static final int RANDOM_PORT_HINT = 0;

        public static void main(String[] args) {
            try {
                Registry registry = LocateRegistry.createRegistry(1098);
                ChatRemoteService chatRemoteService = new ChatRemoteServiceImpl();
                ChatRemoteService skeleton = (ChatRemoteService) UnicastRemoteObject.exportObject(chatRemoteService,
                        RANDOM_PORT_HINT);
                registry.rebind(ChatRemoteService.REMOTE_OBJECT_NAME, skeleton);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }
