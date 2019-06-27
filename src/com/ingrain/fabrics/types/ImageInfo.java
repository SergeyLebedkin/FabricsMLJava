package com.ingrain.fabrics.types;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	// high resolution image data
	public List<PixelLocationOnOverview> highResolutionImageData = new ArrayList<PixelLocationOnOverview>();
	public List<ImageInfo> highResolutionImageInfos = new ArrayList<ImageInfo>();
	// high resolution image suggestions settings
	public int HRWidth = 30;
	public int HRHeight = 23;
	// intensity
	public int[] intensity = new int[256];
	public int intensityLow = 90;
	public int intensityMedium = 150;
	public int intensityHigh = 250;
	// image resolution
	public float imageResolution = 0.00024f;

	// loadFromFile
	public void loadFromFile(File file) {
		// check for null
		if (file != null) {
			// store file reference
			this.fileRef = file;
			// read image file
			try {
				this.canvasImage = ImageIO.read(this.fileRef);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// update canvases
			this.updateAllCanvases();
		}
	}

	// loadImageDataFile
	public void loadImageDataFile(File file) throws NumberFormatException {
		// check for null
		if (file != null) {
			// store data file ref
			this.dataFileRef = file;
			try {
				// read xml from file
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document xml = dBuilder.parse(this.dataFileRef);
				xml.getDocumentElement().normalize();

				// get high resolution images width and height
				Node HRElement = xml.getElementsByTagName("HighREsolutionImageSuggestionsSettings").item(0);
				this.HRWidth = Integer.parseInt(HRElement.getAttributes().getNamedItem("HRWidth").getTextContent());
				this.HRHeight = Integer.parseInt(HRElement.getAttributes().getNamedItem("HRHeight").getTextContent());

				// get pixel locations
				NodeList pixelLocationOnOverviews = xml.getElementsByTagName("PixelLocationOnOverview");
				for (int i = 0; i < pixelLocationOnOverviews.getLength(); i++) {
					String xAttr = pixelLocationOnOverviews.item(i).getAttributes().getNamedItem("x").getTextContent();
					String yAttr = pixelLocationOnOverviews.item(i).getAttributes().getNamedItem("y").getTextContent();
					float x = Float.parseFloat(xAttr);
					float y = Float.parseFloat(yAttr);
					PixelLocationOnOverview pixelLocationOnOverview = new PixelLocationOnOverview(x, y);
					this.addPixelLocationOnOverview(pixelLocationOnOverview);
					System.out.println(x);
					System.out.println(y);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// updateAllCanvases
	private void updateAllCanvases() {
		// check for null
		if (canvasImage != null) {
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
		int[] canvasImageData = canvasImage.getRaster().getPixels(0, 0, w, h, (int[]) null);
		int[] canvasMaskData = canvasMask.getRaster().getPixels(0, 0, w, h, (int[]) null);
		// update intensity
		Arrays.fill(this.intensity, 0);
		for (int i = 0; i < canvasImageData.length; i++) {
			if (canvasMaskData[i * 3] > 0) {
				this.intensity[canvasImageData[i]]++;
			}
		}
	}

	// addPixelLocationOnOverview
	public void addPixelLocationOnOverview(PixelLocationOnOverview pixelLocation) {
		// add pixel location on overview
		this.highResolutionImageData.add(pixelLocation);
		// update High Res Area Canvas
		this.updateHighResAreaCanvas();
	}

	// updateHighResAreaCanvas
	public void updateHighResAreaCanvas() {
		// get rect dimentions
		int rectWidth = (int) (this.HRWidth / (this.imageResolution * 1000));
		int rectHeight = (int) (this.HRHeight / (this.imageResolution * 1000));
		// get context
		Graphics2D canvasHighResAreaCtx = (Graphics2D) this.canvasHighResArea.getGraphics();
		Graphics2D canvasHighResMaskCtx = (Graphics2D) this.canvasHighResMask.getGraphics();
		// clear areas
		canvasHighResAreaCtx.setColor(new Color(0, 0, 0, 0));
		canvasHighResAreaCtx.fillRect(0, 0, this.canvasHighResArea.getWidth(), this.canvasHighResArea.getHeight());
		canvasHighResMaskCtx.setColor(new Color(0, 0, 0, 0));
		canvasHighResMaskCtx.fillRect(0, 0, this.canvasHighResMask.getWidth(), this.canvasHighResArea.getHeight());
		// draw rects
		for (int i = 0; i < this.highResolutionImageData.size(); i++) {
			PixelLocationOnOverview pixelLocation = this.highResolutionImageData.get(i);
			canvasHighResAreaCtx.setStroke(new BasicStroke(4));
			canvasHighResAreaCtx.setColor(pixelLocation.inBlackList ? Color.LIGHT_GRAY : new Color(255, 129, 0, 255));
			canvasHighResAreaCtx.drawRect((int) pixelLocation.x, (int) pixelLocation.y, rectWidth, rectHeight);
		};
	}
}
