package com.ingrain.fabrics.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// StartupWindowModel
public class StartupWindowStage extends Stage {
	// parent
	private Parent root = null;
	// scene
	private Scene scene = null;
	// controller
	private StartupWindowController controller = null;
	
	// StartupWindowModel
	public StartupWindowStage() throws IOException {
		super();
		// create loader and load root
		FXMLLoader loader = new FXMLLoader();
		root = loader.load(getClass().getResource("StartupWindow.fxml").openStream());
		// get controller
		controller = loader.getController();
		controller.setStage(this);
		// create scene
		scene = new Scene(root);
		// create stage
		resizableProperty().setValue(false);
		setTitle("FabricsML");
		setScene(scene);
	}
	
	// setScanWindowModel
	public void setScanWindowStage(ScanWindowStage scanWindowStage) {
		controller.setScanWindowStage(scanWindowStage);
	}
	
	// setSelectWindowStage
	public void setSelectWindowStage(SelectWindowStage selectWindowStage) {
		controller.setSelectWindowStage(selectWindowStage);
	}

}
