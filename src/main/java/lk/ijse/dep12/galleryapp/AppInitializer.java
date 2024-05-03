package lk.ijse.dep12.galleryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.dep12.galleryapp.controller.MainViewController;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainView.fxml"))));
        primaryStage.setTitle("Gallery App");

        Image icon = new Image(getClass().getResourceAsStream("/logo/gallery.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
