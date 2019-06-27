package com.ingrain.fabrics.ui;

import java.io.File;

import com.ingrain.fabrics.types.ImageInfo;
import com.ingrain.fabrics.ui.controls.ResizableCanvas;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

// SelectWindowController
public class SelectWindowController {
	@FXML
	private VBox mainPanel;

	// main canvas
	private ResizableCanvas canvasMain;

	// image info
	private ImageInfo imageInfo = new ImageInfo();

	@FXML
	void initialize() {
		canvasMain = new ResizableCanvas();
		canvasMain.setWidth(1024);
		canvasMain.setHeight(1024);
		canvasMain.setOnScroll(event -> this.canvasMainOnScroll(event));
		mainPanel.getChildren().add(canvasMain);
	}

	@FXML
	void buttonScaleDownClick(MouseEvent event) {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML file", "*.xml"));
		File file = fileChooser.showOpenDialog(null);
		imageInfo.loadImageDataFile(file);
		this.drawImageInfo();
	}

	@FXML
	void buttonScaleUpClick(MouseEvent event) {
		canvasMain.setWidth(canvasMain.getWidth() * 2);
		canvasMain.setHeight(canvasMain.getHeight() * 2);
		this.drawImageInfo();
	}

	@FXML
	void buttonSelectImageClick(MouseEvent event) {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tiff file", "*.tiff;*.tif"));
		File file = fileChooser.showOpenDialog(null);
		imageInfo.loadFromFile(file);
		this.drawImageInfo();
	}

	// canvasMainOnScroll
	void canvasMainOnScroll(ScrollEvent event) {
		if (event.getDeltaY() < 0) {
			canvasMain.setWidth(canvasMain.getWidth() / 1.3);
			canvasMain.setHeight(canvasMain.getHeight() / 1.3);
		} else {
			canvasMain.setWidth(canvasMain.getWidth() * 1.3);
			canvasMain.setHeight(canvasMain.getHeight() * 1.3);
		}
		this.drawImageInfo();
	};

	void drawImageInfo() {
		// copy image to canvas
		if (imageInfo != null) {
			// get dimantions
			double sw = imageInfo.canvasImage.getWidth();
			double sh = imageInfo.canvasImage.getHeight();
			double dw = canvasMain.getWidth();
			double dh = canvasMain.getHeight();

			// create proxy image and get context
			WritableImage writableImage = new WritableImage((int) sw, (int) sh);
			GraphicsContext graphicsContext = canvasMain.getGraphicsContext2D();
			graphicsContext.setGlobalAlpha(1.0);

			// draw image
			writableImage = SwingFXUtils.toFXImage(imageInfo.canvasImage, null);
			graphicsContext.drawImage(writableImage, 0, 0, sw, sh, 0, 0, dw, dh);
			writableImage = SwingFXUtils.toFXImage(imageInfo.canvasHighResArea, null);
			graphicsContext.drawImage(writableImage, 0, 0, sw, sh, 0, 0, dw, dh);
		}
	}
}