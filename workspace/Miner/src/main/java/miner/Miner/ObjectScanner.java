package miner.Miner;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

public class ObjectScanner extends Thread{
	SavedModelBundle model;
	Robot robot;
	ArrayList<SpottedObject> spottedObjectList;

	public ObjectScanner() throws AWTException {
		final String TRAINED_MODEL_PATH = "/home/fredrik/programming/osrsMiningBot/workspace"
				+ "/Miner/src/main/java/miner/Miner/output_inference_graph_v1.pb/saved_model";
		this.model = SavedModelBundle.load(TRAINED_MODEL_PATH, "serve");
		this.robot = new Robot();
		this.spottedObjectList = new ArrayList<SpottedObject>();
	}

	public void run(BufferedImage gameWindow, double confidence) throws IOException {
		try {
			fetchAllObjectFromImage(gameWindow, confidence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fetchAllObjectFromImage(BufferedImage gameWindow, double confidence)
			throws IOException {
		List<Tensor<?>> predictedObjects = null;
		ArrayList<SpottedObject> reliableObjects = new ArrayList<SpottedObject>();
		try (Tensor<UInt8> input = makeImageTensor(gameWindow)) {
			predictedObjects = this.model.session().runner().feed("image_tensor", input).fetch("detection_scores")
					.fetch("detection_classes").fetch("detection_boxes").run();

			input.close();
		}
		Tensor<Float> scoreTensor = predictedObjects.get(0).expect(Float.class);
		Tensor<Float> classTensor = predictedObjects.get(1).expect(Float.class);
		Tensor<Float> boxTensor = predictedObjects.get(2).expect(Float.class);
		int maxObjects = (int) scoreTensor.shape()[1];
		float[] scores = scoreTensor.copyTo(new float[1][maxObjects])[0];
		float[] classes = classTensor.copyTo(new float[1][maxObjects])[0];
		float[][] boxes = boxTensor.copyTo(new float[1][maxObjects][4])[0];

		for (int prediction = 0; prediction < scores.length; prediction++) {
			if (scores[prediction] > confidence) {
				reliableObjects.add(new SpottedObject(scores[prediction], classes[prediction], boxes[prediction]));
			}
		}
		this.spottedObjectList = reliableObjects;
	}

	private static Tensor<UInt8> makeImageTensor(BufferedImage image) throws IOException {
		final int BATCH_SIZE = 1;
		final int CHANNELS = 3;	
		DataBuffer dataBuffer = image.getRaster().getDataBuffer();
		int[] pixelData = ((DataBufferInt) dataBuffer).getData();
		ByteBuffer byteBuffer = transformImageToBytes(pixelData);

		long[] shape = new long[] { BATCH_SIZE, image.getHeight(), image.getWidth(), CHANNELS };
		return Tensor.create(UInt8.class, shape, byteBuffer);
	}

	private static ByteBuffer transformImageToBytes(int[] buffer) throws IOException {
		byte[] byteArray = new byte[buffer.length*3];
		int temp = 0;
		for (int i = 0; i < buffer.length; i++) {
			Color c = new Color(buffer[i], true);
			byteArray[temp++] = (byte) c.getRed();
			byteArray[temp++] = (byte) c.getGreen();
			byteArray[temp++] = (byte) c.getBlue();
			
			
		}
		return ByteBuffer.wrap(byteArray);
	}

}