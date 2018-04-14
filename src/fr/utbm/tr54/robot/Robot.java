package fr.utbm.tr54.robot;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import fr.utbm.tr54.message.*;
import fr.utbm.tr54.threads.SendRobot2ServerThread;
import fr.utbm.tr54.tp1.Clock;
import lejos.hardware.lcd.LCD;
import lejos.network.*;
import lejos.robotics.Color;

public class Robot {
	private static final int SAFE_ZONE_DISTANCE = 100;
	private final int id;
	private float physicalPosition;
	private float speed;
	private MotorManager motor;
	private SensorManager sensor;
	private boolean zoneConflict = false;
	private BroadcastManager broadcast;
	private BroadcastReceiver receiver;
	private ServerRobotMes mesReceive;
	private RobotServerMes newMes;
	private Clock clock;
	
	/**
	 * Constructor of Robot
	 * 
	 * @param id				the ID of the robot
	 * @param physicalPosition	the position of the robot
	 * @param speed				the speed of the robot
	 */
	public Robot(int id, int physicalPosition, float speed) {
		this.id = id;
		System.out.println(Integer.toString(id));
		this.setPhysicalPosition(physicalPosition);
		this.setSpeed(speed);
		motor = new MotorManager();
		sensor = new SensorManager();
		clock = new Clock();
		mesReceive = null;
	}

	
	/**
	 * Main loop of the robot
	 * 
	 * @throws IOException
	 */
	public void runOnTrace() throws IOException {		
		boolean asDoneIt =false;
		//sender
		broadcast = BroadcastManager.getInstance(9999);
		RobotServerMes newMes = new RobotServerMes(-1,this.id,this.speed,this.clock.getTime());
		SendRobot2ServerThread communicationThread = new SendRobot2ServerThread(newMes, 0, broadcast);

		//listener
		receiver = BroadcastReceiver.getInstance(8888);		
		receiver.addListener(new BroadcastListener() {

			@Override
			public void onBroadcastReceived(ByteBuffer message) {
				mesReceive = new ServerRobotMes(message);
				clock.syncTime(mesReceive.getTimeStamp());
				if(isZoneConflict()) {
					for(int i =0; i< mesReceive.getRobotsInfo().size();i++) {
						if(mesReceive.getRobotsInfo().get(i).getRobotId()== id) {							
							speed = mesReceive.getRobotsInfo().get(i).getNewSpeed();
						}
					}
				}
			}
		});
		
		while (true) {
			
			if (isZoneConflict()){
				motor.setSpeed((int)speed);
				setPhysicalPosition(motor.CalculateDistance()/10);
			}
			else {
				motor.setSpeed(motor.getMaxSpeed());
			}
			System.out.println(sensor.distance());
			if((int)sensor.distance() < 30) {
				while((int)sensor.distance() < 30)
					motor.stop();
			}
			else {
				switch (sensor.captCouleur()) {	
					case Color.WHITE:
						motor.forwardRight();
						break;
						
					case Color.BLACK:
						motor.forwardLeft();
						break;	
						
					case Color.RED:
						//Zone Conflict
						setZoneConflict(true);
						//reset the tachometer
						motor.resetTachometer();
						setPhysicalPosition(motor.CalculateDistance()/10);
						lejos.hardware.Sound.beepSequenceUp();
						break;
					case Color.BLUE:
						//color blue 
						motor.forward();
						break;
						
					default:
						motor.forward();
				}
			}
			if(getPhysicalPosition() >= 30 && getPhysicalPosition() <= 40 && asDoneIt == false) {
				setSpeed(0);
				asDoneIt = true;
				lejos.hardware.Sound.beepSequenceUp();
				
			}
			if (getPhysicalPosition() >= SAFE_ZONE_DISTANCE && isZoneConflict()){
				setZoneConflict(false);
				asDoneIt = false;
				//send message to server to say I am outside now
				setPhysicalPosition(-1);
			}
			if(! communicationThread.isAlive()) {
				newMes = new RobotServerMes(this.physicalPosition,this.id,this.speed,this.clock.getTime());
				communicationThread =new SendRobot2ServerThread(newMes,1,broadcast);
				communicationThread.start();
			}	
		}
	}
	
	/**
	 * @return id	the ID of the robot
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return physicalPosition	the position of the robot
	 */
	public float getPhysicalPosition() {
		return physicalPosition;
	}

	/**
	 * @param physicalPosition	the position of the robot
	 */
	public void setPhysicalPosition(float physicalPosition) {
		this.physicalPosition = physicalPosition;
	}

	/**
	 * @return speed	the speed of the robot
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed		the speed of the robot
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * @return zoneConflict	1: the robot is in the conflict zone
	 * 						0: the robot is not in the conflict zone
	 */
	public boolean isZoneConflict() {
		return zoneConflict;
	}

	/**
	 * @param zoneConflict	1: the robot is in the conflict zone
	 * 						0: the robot is not in the conflict zone
	 */
	public void setZoneConflict(boolean zoneConflict) {
		this.zoneConflict = zoneConflict;
	}
	
}
