package miner.Miner;

import org.opencv.core.Core;

public class main {

	public static void main(String[] args) throws Exception {
		System.out.println("Initializing bot, fetching libraries!");
		
		System.load("/usr/local/cuda-9.0/lib64/libcublas.so.9.0");
		System.load("/usr/local/cuda-9.0/lib64/libcudart.so.9.0");
		System.load("/usr/local/cuda-9.0/lib64/libcusolver.so.9.0");
		System.load("/usr/local/cuda-9.0/lib64/libcufft.so.9.0");
		System.load("/usr/local/cuda-9.0/lib64/libcurand.so.9.0");
		System.load("/home/fredrik/programming/osrsMiningBot/addons/opencv-3.4.10/build/lib/libopencv_java3410.so");
		
		System.out.println("Loading done, starting bot");
		Bot bot = new Bot();
		bot.run();

		System.out.println("Done");
	}

}
