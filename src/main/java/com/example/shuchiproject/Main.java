package com.example.shuchiproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/main-view.fxml"));

        Region region = loader.load();

        double origW = 600;
        double origH = 400;

        Group group = new Group(region);
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add( group );

        Scene scene = new Scene(rootPane, origW, origH);

        group.scaleXProperty().bind(scene.widthProperty().divide(origW));
        group.scaleYProperty().bind(scene.heightProperty().divide(origH));

        stage.setScene(scene);
        stage.show();
    }
}
