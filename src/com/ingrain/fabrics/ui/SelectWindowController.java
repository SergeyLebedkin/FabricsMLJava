package com.ingrain.fabrics.ui;

import java.io.File;

import com.ingrain.fabrics.types.ImageInfo;
import com.ingrain.fabrics.ui.controls.ResizableCanvas;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.stage.FileChooser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SelectWindowController {

    @FXML
    private Button buttonLoadImage;

    @FXML
    private Button buttonLoadImageData;

    @FXML
    private ComboBox<?> comboBoxImages;

    @FXML
    private TextField textFieldImageDimsXPx;

    @FXML
    private TextField textFieldImageDimsYPx;

    @FXML
    private TextField textFieldImageDimsXMm;

    @FXML
    private TextField textFieldImageDimsYMm;

    @FXML
    private TextField textFieldImageResolution;

    @FXML
    private ComboBox<?> comboBoxAreaIndex;

    @FXML
    private Button buttonAddToBlackList;

    @FXML
    private Button buttonRemoveFromBlackList;

    @FXML
    private VBox panelHighRes;

    @FXML
    private VBox panelMain;

	// main canvas
	private ResizableCanvas canvasMain;

	// image info
	private ImageInfo imageInfo = new ImageInfo();
	private float scale = 1.0f;

	@FXML
	void initialize() {
		buttonLoadImage.setOnMouseClicked(event -> buttonLoadImageOnMouseClicked(event));
		buttonLoadImageData.setOnMouseClicked(event -> buttonLoadImageDataOnMouseClicked(event));

		canvasMain = new ResizableCanvas();
		canvasMain.setWidth(1024);
		canvasMain.setHeight(1024);
		canvasMain.setOnScroll(event -> this.canvasMainOnScroll(event));
		panelMain.getChildren().add(canvasMain);
	}

	// buttonLoadImageOnMouseClicked
	void buttonLoadImageOnMouseClicked(MouseEvent event) {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tiff file", "*.tiff;*.tif"));
		File file = fileChooser.showOpenDialog(null);
		this.imageInfo.loadFromFile(file);
		this.drawImageInfo();
	}

	// buttonLoadImageDataOnMouseClicked
	void buttonLoadImageDataOnMouseClicked(MouseEvent event) {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML file", "*.xml"));
		File file = fileChooser.showOpenDialog(null);
		this.imageInfo.loadImageDataFile(file);
		this.drawImageInfo();
	}

	// canvasMainOnScroll
	void canvasMainOnScroll(ScrollEvent event) {
		this.scale *= (float) Math.pow(1.1, event.getDeltaY() / 40.0f);
		this.drawImageInfo();
	};

	void drawImageInfo() {
		// copy image to canvas
		if (imageInfo != null) {
			// get dimantions
			double sw = this.imageInfo.canvasImage.getWidth();
			double sh = this.imageInfo.canvasImage.getHeight();
			double dw = sw * this.scale;
			double dh = sh * this.scale;
			// change canvas dimentions
			this.canvasMain.setWidth(dw);
			this.canvasMain.setHeight(dh);
			// create proxy image and get context
			WritableImage writableImage = null;
			GraphicsContext graphicsContext = this.canvasMain.getGraphicsContext2D();
			graphicsContext.setGlobalAlpha(1.0);
			// draw image and all other layers
			writableImage = SwingFXUtils.toFXImage(this.imageInfo.canvasImage, null);
			graphicsContext.drawImage(writableImage, 0, 0, sw, sh, 0, 0, dw, dh);
			writableImage = SwingFXUtils.toFXImage(this.imageInfo.canvasHighResArea, null);
			graphicsContext.drawImage(writableImage, 0, 0, sw, sh, 0, 0, dw, dh);
		}
	}
}