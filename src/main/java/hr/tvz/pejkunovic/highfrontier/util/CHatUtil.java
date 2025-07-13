package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.HelloApplication;
import hr.tvz.pejkunovic.highfrontier.UniverseMapController;
import hr.tvz.pejkunovic.highfrontier.chat.ChatRemoteService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.rmi.RemoteException;
import java.util.List;

public class CHatUtil {
    public class ChatUtils {

        private ChatUtils() {}

        public static void sendChatMessage(String chatMessage, ChatRemoteService chatRemoteService) {
            try {
                chatRemoteService.sendChatMessage(HelloApplication.playerName
                        + ": " + chatMessage);
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }

        public static Timeline getChatTimeline(ChatRemoteService chatRemoteService, TextArea chatTextArea) {
            Timeline chatMessagesTimeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
                try {
                    List<String> chatMessages = chatRemoteService.getAllChatMessages();
                    String chatMessagesString = String.join("\n", chatMessages);
                    chatTextArea.setText(chatMessagesString);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());                }
            }), new KeyFrame(Duration.seconds(1)));
            chatMessagesTimeline.setCycleCount(Animation.INDEFINITE);
            return chatMessagesTimeline;
        }
    }
}
