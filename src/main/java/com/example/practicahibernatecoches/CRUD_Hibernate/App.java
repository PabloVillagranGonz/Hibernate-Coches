package com.example.practicahibernatecoches.CRUD_Hibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jboss.jandex.Main;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/practicahibernatecoches/coches.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Coches!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}