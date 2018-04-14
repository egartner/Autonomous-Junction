package fr.utbm.tr54.tp1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import fr.utbm.tr54.robot.*;
import lejos.hardware.lcd.LCD;
import lejos.network.BroadcastListener;
import lejos.network.BroadcastManager;
import lejos.network.BroadcastReceiver;
import lejos.hardware.Button;

public class main {
	
	private static Robot robot;

	
	public static void main(String[] args) throws IOException{
		Random rnd = new Random();
		int id = rnd.nextInt(1000);
		robot = new Robot(id,0,360);
		
		robot.runOnTrace();
		


	}

}
