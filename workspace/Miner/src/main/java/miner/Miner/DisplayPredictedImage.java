package miner.Miner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Rect;

class DisplayPredictedImage extends Thread{
	JFrame window;
	JLabel label;
	int frameCounter = 0;
	int prevFrameCounter = 0;

	public JFrame initializePredictedImageWindow() {
		window = new JFrame();
		window.setLayout(new FlowLayout());
		window.setSize(800, 600);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label = new JLabel();
		window.add(label);
		return window;
	}

	public void run(BufferedImage image, ArrayList<SpottedObject> predictedObjects) {
		writeToPredictedImageWindow(image, predictedObjects);
	}

	public void writeToPredictedImageWindow(BufferedImage image, ArrayList<SpottedObject> predictedObjects){
		Graphics2D newImage = image.createGraphics();
		for (SpottedObject spottedObject : predictedObjects) {
			//UNCOMMENT FOR PRINT
			//spottedObject.printAllInformation();
			Rect drawBox = spottedObject.getBox();
			newImage.setColor(spottedObject.color);

			newImage.setColor(spottedObject.color);
			newImage.setStroke(new BasicStroke(3));
			newImage.drawRect(drawBox.x, drawBox.y, drawBox.width, drawBox.height);
		}
		Font font = new Font("Serif", Font.PLAIN, 50);
		newImage.setFont(font);
		newImage.setColor(Color.RED);
		frameCounter++;
		String fps = Integer.toString(prevFrameCounter);
		newImage.drawString(fps, 1, 50);
		newImage.dispose();
		label.setIcon(new ImageIcon(image));
	}

	public void writeFpsInImageWindow() {		
		prevFrameCounter = frameCounter;
		frameCounter = 0;
		label.repaint();
	}
	
}
