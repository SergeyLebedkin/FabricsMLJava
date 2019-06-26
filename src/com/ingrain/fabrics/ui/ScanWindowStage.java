package com.ingrain.fabrics.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// ScanWindowModel
public class ScanWindowStage extends Stage {
	// parent
	public Parent root = null;
	// scene
	public Scene scene = null;
	// controller
	public ScanWindowController controller = null;
	
	// StartupWindowModel
	public ScanWindowStage() throws IOException {
		super();
		// create loader and load root
		FXMLLoader loader = new FXMLLoader();
		root = loader.load(getClass().getResource("ScanWindow.fxml").openStream());
		// get controller
		controller = loader.getController();
		// create scene
		scene = new Scene(root);
		// create stage
		setTitle("FabricsML - Scan");
		setScene(scene);
	}
}