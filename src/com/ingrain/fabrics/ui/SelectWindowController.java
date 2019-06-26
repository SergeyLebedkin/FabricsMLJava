package com.ingrain.fabrics.ui;

import com.ingrain.fabrics.ui.controls.ResizableCanvas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

// SelectWindowController
public class SelectWindowController {
	@FXML
	private VBox mainPanel;

	// main canvas
	private ResizableCanvas canvasMain;
	private File file;
	BufferedImage bufferedImage;

	@FXML
	void initialize() {
		canvasMain = new ResizableCanvas();
		canvasMain.setWidth(100);
		canvasMain.setHeight(100);
		canvasMain.setOnScroll(event -> {
			if (event.getDeltaY() > 0) {
				canvasMain.setWidth(canvasMain.getWidth() / 1.3);
				canvasMain.setHeight(canvasMain.getHeight() / 1.3);
				this.draw();
			} else {
				canvasMain.setWidth(canvasMain.getWidth() * 1.3);
				canvasMain.setHeight(canvasMain.getHeight() * 1.3);
				this.draw();
			}
		});
		mainPanel.getChildren().add(canvasMain);
	}

	@FXML
	void buttonSelectImageClick(MouseEvent event) throws IOException {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tiff files", "*.tif;*.tiff;*.jpg"));
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			bufferedImage = ImageIO.read(file);
			draw();
		}
	}

	@FXML
	void buttonScaleDownClick(MouseEvent event) throws IOException {
		canvasMain.setWidth(canvasMain.getWidth() / 2);
		canvasMain.setHeight(canvasMain.getHeight() / 2);
		draw();
	}

	@FXML
	void buttonScaleUpClick(MouseEvent event) throws IOException {
		canvasMain.setWidth(canvasMain.getWidth() * 2);
		canvasMain.setHeight(canvasMain.getHeight() * 2);
		draw();
	}

	void draw() {
		// copy image to canvas
		if (bufferedImage != null) {
			WritableImage writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
			// draw original image
			canvasMain.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(bufferedImage, writableImage), 0, 0,
					bufferedImage.getWidth(), bufferedImage.getHeight(), 0, 0, canvasMain.getWidth(),
					canvasMain.getHeight());
		}
	}
}