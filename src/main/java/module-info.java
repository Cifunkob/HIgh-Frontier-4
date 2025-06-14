module hr.tvz.pejkunovic.highfrontier {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;

    opens hr.tvz.pejkunovic.highfrontier to javafx.fxml;
    opens hr.tvz.pejkunovic.highfrontier.model to javafx.base;

    exports hr.tvz.pejkunovic.highfrontier;
    exports hr.tvz.pejkunovic.highfrontier.model;
    exports hr.tvz.pejkunovic.highfrontier.model.cardModels;
    opens hr.tvz.pejkunovic.highfrontier.model.cardModels to javafx.base;
}
