package hr.tvz.pejkunovic.highfrontier.util;

import javafx.scene.control.Button;

import java.util.Map;

public class ButtonUtil {

    public Map<Long, Button> addButtonsToMap(Map<Long, Button> buttonMap, Button... buttons) {
        for (int i = 0; i < buttons.length; i++) {
            buttonMap.put((long)(i + 1), buttons[i]);
        }
        return buttonMap;
    }
}
