package com.ingrain.fabrics.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

// SelectWindowController
public class SelectWindowController {
	@FXML
	private Button buttonSelectImage;

	@FXML
	private Canvas canvasMain;

	@FXML
	void initialize() {
	}

	@FXML
	void buttonSelectImageClick(MouseEvent event) throws IOException {
		// create file chooser and load file
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tiff files", "*.tif;*.tiff"));
		File file = fileChooser.showOpenDialog(null);

		// copy image to canvas
		if (file != null) {
			BufferedImage bufferedImage = ImageIO.read(file);
			// create temporary writable image buffer for fast copy to canvas
			WritableImage writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
			// draw original image
			canvasMain.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(bufferedImage, writableImage), 0, 0);
			canvasMain.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(bufferedImage, writableImage), 100, 100);
		}
	}
}