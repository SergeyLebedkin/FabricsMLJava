package com.ingrain.fabrics.types;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

// ImageInfo
public class ImageInfo {
	// file reference
	public File fileRef = null;
	public File dataFileRef = null;
	// buffered images
	public BufferedImage canvasImage = null;
	public BufferedImage canvasMask = null;
	public BufferedImage canvasHiLight = null;
	public BufferedImage canvasBorders = null;
	public BufferedImage canvasHighResArea = null;
	public BufferedImage canvasHighResMask = null;
    // high resolution image suggestions settings
    public int HRWidth = 30;
    public int HRHeight = 23;
    // intensity
    public int[] intensity = new int[256];
    public int intensityLow = 90;
    public int intensityMedium = 150;
    public int intensityHigh = 250;
    // image resolution
    public float imageResolution = 1.0f;
	
	// loadFromFile
	public void loadFromFile(File file) throws IOException {
		// check for null
		if (file == null) return;
		// store file reference
		this.fileRef = file;
		// read image file
		this.canvasImage = ImageIO.read(this.fileRef);
		// update canvases
		this.updateAllCanvases();
	}
	
	// updateAllCanvases
	private void updateAllCanvases() {
		// check for null
		if (canvasImage == null) return;
		// update canvases
		int w = canvasImage.getWidth();
		int h = canvasImage.getHeight();
		this.canvasMask = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.canvasHiLight = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.canvasBorders = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.canvasHighResArea = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.canvasHighResMask = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		// update data
		this.updateHilightCanvas();
		this.updateBordersCanvas();
		this.updateIntensity();
		this.updateHighResAreaCanvas();
	}
	
	// updateHilightCanvas
	public void updateHilightCanvas() {
		return;
	}
	
	// updateBordersCanvas
	public void updateBordersCanvas() {
		return;
	}
	
	// updateIntensity
	public void updateIntensity() {
		// get data arrays
		int w = canvasImage.getWidth();
		int h = canvasImage.getHeight();
		int[] canvasImageData = canvasImage.getRaster().getPixels(0, 0, w, h, (int[])null);
		int[] canvasMaskData = canvasMask.getRaster().getPixels(0, 0, w, h, (int[])null);
		
		// update intensity
		Arrays.fill(this.intensity, 0);
		for (int i = 0; i < canvasImageData.length; i++) {
			if (canvasMaskData[i*3] > 0) {
				this.intensity[canvasImageData[i]]++;
			}
		}
	}
	
	// updateHighResAreaCanvas
	public void updateHighResAreaCanvas() {
		return;
	}
}
