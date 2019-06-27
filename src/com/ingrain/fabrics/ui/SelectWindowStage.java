package com.ingrain.fabrics.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// SelectWindowStage
public class SelectWindowStage extends Stage {
	// parent
	public Parent root = null;
	// scene
	public Scene scene = null;
	// controller
	public SelectWindowController controller = null;
	
	// StartupWindowModel
	public SelectWindowStage() throws IOException {
		super();
		// create loader and load root
		FXMLLoader loader = new FXMLLoader();
		root = loader.load(getClass().getResource("SelectWindow.fxml").openStream());
		// get controller
		controller = loader.getController();
		// create scene
		scene = new Scene(root);
		// create stage
		setTitle("FabricsML - Select");
		setWidth(800);
		setHeight(600);
		setScene(scene);
	}
}