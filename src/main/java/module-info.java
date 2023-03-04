module ro.tucn.assignment4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens ro.tucn.assignment4 to javafx.fxml;
    exports ro.tucn.assignment4;
    exports ro.tucn.assignment4.Controller;
    opens ro.tucn.assignment4.Controller to javafx.fxml;
    exports ro.tucn.assignment4.Business;
    opens ro.tucn.assignment4.Business to javafx.fxml;
}