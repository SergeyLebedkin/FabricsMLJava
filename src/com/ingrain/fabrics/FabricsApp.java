package com.ingrain.fabrics;

import com.ingrain.fabrics.ui.ScanWindowStage;
import com.ingrain.fabrics.ui.SelectWindowStage;
import com.ingrain.fabrics.ui.StartupWindowStage;

import javafx.application.Application;
import javafx.stage.Stage;

// FabricsApp
public class FabricsApp extends Application {
	// stages
	public ScanWindowStage scanWindowStage = null;
	public SelectWindowStage selectWindowStage = null;
	public StartupWindowStage startupWindowStage = null;
	
	// main
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// scanWindowStage
		scanWindowStage = new ScanWindowStage();
		selectWindowStage = new SelectWindowStage();
		
		// startupWindowModel
		startupWindowStage = new StartupWindowStage();
		startupWindowStage.setScanWindowStage(scanWindowStage);
		startupWindowStage.setSelectWindowStage(selectWindowStage);
		startupWindowStage.show();
	}
}
