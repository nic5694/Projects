module snakegame.snakegame2d {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens snakegame.snakegame2d to javafx.fxml;
    exports snakegame.snakegame2d;
}