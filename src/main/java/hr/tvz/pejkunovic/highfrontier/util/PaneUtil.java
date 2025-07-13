package hr.tvz.pejkunovic.highfrontier.util;

import javafx.scene.layout.Pane;

public class PaneUtil {
    public static void enablePane(Pane pane){
        pane.setDisable(false);
    }
    public static void disablePane(Pane pane){
        pane.setDisable(true);
    }
}
