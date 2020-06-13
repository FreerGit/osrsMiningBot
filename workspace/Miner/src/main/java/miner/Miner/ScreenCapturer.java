package miner.Miner;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ScreenCapturer {

	public static BufferedImage captureGameScreen() throws AWTException {
		//TODO offsets and rectangle size is probably wrong, let it be for now.
		Rectangle rectangle = new Rectangle(Consts.X_OFFSET, Consts.Y_OFFSET, Consts.WINDOW_WIDTH,
				Consts.WINDOW_HEIGHT);
		return new Robot().createScreenCapture(rectangle);
	}
}