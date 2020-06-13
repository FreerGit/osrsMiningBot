package miner.Miner;

import java.awt.Color;

import org.opencv.core.Rect;

public class SpottedObject {
	// has a score, classId and matrix for the box
	float score;
	String classIdentifier;
	Rect box;
	Color color;

	public SpottedObject(float score, float classNum, float[] box) {
		this.score = score;
		this.classIdentifier = toName(classNum);
		this.box = createPredictionBox(box);
	}

	public String toName(float classNum) {
		String[] labels = { "fixForIndex", "ore", "depleted" };
		if ((int) classNum == 1) {
			color = Color.GREEN;
		}
		else if ((int) classNum == 2) {
			color = Color.BLACK;
		}
		else {
			color = Color.RED;
		}
		return labels[(int) classNum];
	}

	public Rect createPredictionBox(float[] box) {
		//translate back form 300x300 to the real image.
		int offset_y = (int) (box[0] * Consts.WINDOW_HEIGHT);
		int offset_x = (int) (box[1] * Consts.WINDOW_WIDTH);
		int width = (int) (box[2] * Consts.WINDOW_HEIGHT) - offset_y;
		int height = (int) (box[3] * Consts.WINDOW_WIDTH) - offset_x;
		return new Rect(offset_x, offset_y, width, height);

	}

	public String getClassIdentifier() {
		return classIdentifier;
	}

	public Rect getBox() {
		return box;
	}

	public void printAllInformation() {
		System.out.println(score);
		System.out.println(classIdentifier);
		System.out.println(box);	}
}