package com.ingrain.fabrics.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

// StartupWindowController
public class StartupWindowController {
	@FXML
	private Button buttonScan;

	@FXML
	private Button buttonSelect;
	
	@FXML
	void initialize() {
	}
	
	// events
	
	@FXML
	void buttonScanOnClick(MouseEvent e) {
		// scanWindow show
		if (scanWindowStage != null) {
			scanWindowStage.show();
		}
		// startupWindowModel show
		if (startupWindowStage != null) {
			startupWindowStage.close();
		}
	}
	
	@FXML
	void buttonSelectOnClick(MouseEvent e) {
		// scanWindow show
		if (selectWindowStage != null) {
			selectWindowStage.show();
		}
		// startupWindowModel show
		if (startupWindowStage != null) {
			startupWindowStage.close();
		}
	}
	
	// external elements
	private ScanWindowStage scanWindowStage = null; 
	private SelectWindowStage selectWindowStage = null;
	private StartupWindowStage startupWindowStage = null;
	
	// setScanWindowModel
	public void setScanWindowStage(ScanWindowStage scanWindowStage) {
		this.scanWindowStage = scanWindowStage;
	}
	
	// setSelectWindowStage
	public void setSelectWindowStage(SelectWindowStage selectWindowStage) {
		this.selectWindowStage = selectWindowStage;
	}
	
	// setStage
	public void setStage(StartupWindowStage startupWindowStage) {
		this.startupWindowStage = startupWindowStage;
	}
}