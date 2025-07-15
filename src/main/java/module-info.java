module hr.tvz.pejkunovic.highfrontier {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.h2database;
    requires java.rmi;

    opens hr.tvz.pejkunovic.highfrontier to javafx.fxml;
    opens hr.tvz.pejkunovic.highfrontier.model to javafx.base;

    exports hr.tvz.pejkunovic.highfrontier;
    exports hr.tvz.pejkunovic.highfrontier.model;
    exports hr.tvz.pejkunovic.highfrontier.model.cardmodels;
    exports hr.tvz.pejkunovic.highfrontier.chat to java.rmi;
    opens hr.tvz.pejkunovic.highfrontier.model.cardmodels to javafx.base;
}
