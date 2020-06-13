package miner.Miner;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Bot {
	Robot robot;
	ObjectScanner scanner;
	DisplayPredictedImage outputWindow;
	ConcurrentLinkedQueue<ArrayList<SpottedObject>> predictedObjectsJobs;
	ConcurrentLinkedQueue<BufferedImage> drawImageJobs;
	// ConcurrentLinkedQueue<>

	public Bot() throws AWTException {
		this.robot = new Robot();
		this.scanner = new ObjectScanner();
		this.outputWindow = new DisplayPredictedImage();
		this.predictedObjectsJobs = new ConcurrentLinkedQueue<ArrayList<SpottedObject>>();
		this.drawImageJobs = new ConcurrentLinkedQueue<BufferedImage>();

	}

	public void run() throws AWTException, IOException, InterruptedException {
		//Not easy to read! Basically one thread will capture a new image and do a prediction on that picture
		//When done, add that to a queue. Another thread will then grab the predictions and the image that was
		//used for the prediction and add the boxes to that picture and display it. This will allow for MUCH MUCH
		//better frame rate rather then doing all of that in a sequence.
		outputWindow.initializePredictedImageWindow();
		outputWindow.start();
		scanner.start();
		//Every second call writeFpsInImageWindow function.
		Runnable helloRunnable = () -> { outputWindow.writeFpsInImageWindow(); };
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
		
		while (true) {
			BufferedImage gameWindow = ScreenCapturer.captureGameScreen();
			//Same as this but we call it from run, since its a thread! Very redundant, however it will be fine for now.
			//scanner.fetchAllObjectFromImage(gameWindow, 0.6);
			scanner.run(gameWindow, 0.6);
			predictedObjectsJobs.add(scanner.spottedObjectList);
			drawImageJobs.add(gameWindow);
			//Same as this but we call it from run, since its a thread! Very redundant, however it will be fine for now.
			//outputWindow.writeToPredictedImageWindow(gameWindow, predictedObjects);
			outputWindow.run(drawImageJobs.poll(), predictedObjectsJobs.poll());
		}
	}
}
