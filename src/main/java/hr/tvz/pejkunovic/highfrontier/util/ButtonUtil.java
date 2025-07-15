package hr.tvz.pejkunovic.highfrontier.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ButtonUtil {

    public Map<Long, Button> addButtonsToMap(Map<Long, Button> buttonMap, Button... buttons) {
        for (int i = 0; i < buttons.length; i++) {
            buttonMap.put((long)(i + 1), buttons[i]);
        }
        return buttonMap;
    }
    public static List<Button> findAllButtons(Parent parent) {
        List<Button> buttons = new ArrayList<>();
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Button button) {
                buttons.add(button);
            } else if (node instanceof Parent child) {
                buttons.addAll(findAllButtons(child));
            }
        }
        return buttons;
    }

    public static void updateButtonsBasedOnTurn(Parent root, Integer turn, String playerName) {
        boolean enable;
        if (turn % 2 == 1 && playerName.equals("player1")) {
            enable = true;
        } else if (turn % 2 == 1 && playerName.equals("player2")) {
            enable = false;
        } else if (turn % 2 == 0 && playerName.equals("player1")) {
            enable = false;
        } else if (turn % 2 == 0 && playerName.equals("player2")) {
            enable = true;
        } else {
            enable = false;
        }

        if (enable) {
            setButtonsEnabled(root);
        } else {
            setButtonsDisabled(root);
        }
    }

    public static void setButtonsEnabled(Parent root) {
        Platform.runLater(() ->
            root.lookupAll(".button").forEach(node -> node.setDisable(false))
        );
    }

    public static void setButtonsDisabled(Parent root) {
        Platform.runLater(() ->
            root.lookupAll(".button").forEach(node -> node.setDisable(true))
        );
    }
}
