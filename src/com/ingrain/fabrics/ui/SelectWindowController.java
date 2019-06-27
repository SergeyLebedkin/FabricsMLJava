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
	private float scale = 1.0f;

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
		this.imageInfo.loadImageDataFile(file);
		this.drawImageInfo();
	}

	@FXML
	void buttonScaleUpClick(MouseEvent event) {
		this.canvasMain.setWidth(canvasMain.getWidth() * 2);
		this.canvasMain.setHeight(canvasMain.getHeight() * 2);
		this.drawImageInfo();
	}

	@FXML
	void buttonSelectImageClick(MouseEvent event) {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tiff file", "*.tiff;*.tif"));
		File file = fileChooser.showOpenDialog(null);
		this.imageInfo.loadFromFile(file);
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
			this.canvasMain.setWidth(imageInfo.canvasImage.getWidth() * this.scale);
			this.canvasMain.setHeight(imageInfo.canvasImage.getHeight() * this.scale);
			// get dimantions
			double sw = this.imageInfo.canvasImage.getWidth();
			double sh = this.imageInfo.canvasImage.getHeight();
			double dw = this.canvasMain.getWidth();
			double dh = this.canvasMain.getHeight();

			// create proxy image and get context
			WritableImage writableImage = new WritableImage((int) sw, (int) sh);
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