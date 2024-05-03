package lk.ijse.dep12.galleryapp.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import java.io.File;

public class MainViewController {


    public ImageView imgMode;
    public Button btnMode;
    public AnchorPane parent;
    public Button btnBrowse;
    public TilePane imageContainer;
    public ScrollPane imageContainerWrapper;
    public StackPane notFoundContainer;
    public TextField txtLocation;
    public StackPane previewContainer;
    public ImageView previewImageView;
    public Button btnClosePreview;

    // Initialize DirectoryChooser and boolean for dark mode
    private DirectoryChooser directoryChooser;
    private boolean isDarkMode = true;

    // Add DoubleProperties to hold the width and height of the previewContainer
    private DoubleProperty previewWidth = new SimpleDoubleProperty();
    private DoubleProperty previewHeight = new SimpleDoubleProperty();

    // Initialization method
    public void initialize() {
        directoryChooser = new DirectoryChooser();
        previewContainer.setVisible(false);

        // Bind the width and height of the previewImageView to the width and height of the previewContainer
        previewImageView.fitWidthProperty().bind(previewContainer.widthProperty());
        previewImageView.fitHeightProperty().bind(previewContainer.heightProperty());
    }

    // Event handler for browse button
    public void btnBrowseOnAction(ActionEvent actionEvent) {
        notFoundContainer.setVisible(true);
        imageContainerWrapper.setVisible(false);

        // Open directory chooser dialog
        File selectedDirectory = directoryChooser.showDialog(btnBrowse.getScene().getWindow());

        // If no directory selected, return
        if (selectedDirectory == null) {
            return;
        }

        // Set selected directory path to text field and load images from directory
        txtLocation.setText(selectedDirectory.getAbsolutePath());
        loadImages(selectedDirectory);
    }

    // Load images from a directory
    private void loadImages(File directory) {
        imageContainer.getChildren().clear();
        for (File file : directory.listFiles()) {
            if (!file.isFile()) continue;
            // Check file extensions to filter images
            if (file.getName().endsWith("jpeg") || file.getName().endsWith("jpg") ||
                    file.getName().endsWith("bmp") || file.getName().endsWith("png") ||
                    file.getName().endsWith("gif")) {
                ImageView imageView = new ImageView(file.toURI().toString());
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
                imageView.setOnMouseClicked(event -> showPreview(imageView.getImage()));
                imageContainer.getChildren().add(imageView);
                if (!imageContainerWrapper.isVisible()) {
                    imageContainerWrapper.setVisible(true);
                    notFoundContainer.setVisible(false);
                }
            }
        }
    }

    // Show preview of clicked image
    private void showPreview(Image image) {
        previewImageView.setImage(image);
        previewContainer.setVisible(true);

        // Set the width and height of the previewContainer to match the app size
        previewWidth.set(previewContainer.getWidth());
        previewHeight.set(previewContainer.getHeight());
    }

    // Close preview when close button clicked
    public void closePreview(ActionEvent actionEvent) {
        previewContainer.setVisible(false);
    }

    // Toggle between light and dark mode
    public void changeMode(ActionEvent event){
        isDarkMode = !isDarkMode;
        if (isDarkMode){
            setDarkMode();
        }else {
            setLightMode();
        }
    }

    // Set light mode styles
    private void setLightMode() {
        parent.getStylesheets().remove("/css/DarkMode.css");
        parent.getStylesheets().add("/css/LightMode.css");
        imageContainerWrapper.getStylesheets().remove("/css/DarkMode.css");
        imageContainerWrapper.getStylesheets().add("/css/LightMode.css");
        Image image = new Image("/logo/night-mode.png");
        imgMode.setImage(image);
    }

    // Set dark mode styles
    private void setDarkMode() {
        parent.getStylesheets().remove("/css/LightMode.css");
        parent.getStylesheets().add("/css/DarkMode.css");
        imageContainerWrapper.getStylesheets().remove("/css/LightMode.css");
        imageContainerWrapper.getStylesheets().add("/css/DarkMode.css");
        Image image = new Image("/logo/light-mode.png");
        imgMode.setImage(image);
    }
}
