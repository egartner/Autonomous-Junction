package fr.utbm.tr54.server;

import java.sql.Time;

public class VirtualRobot {
	
	private int ID;
	private float physicalPosition;
	private float speed;
	private long lastTimeStamp;
	
	
	/**
	 * @param physicalPosition  distance in centimeter 
	 * @param ID robot's ID
	 * @param speed value in float 
	 * @param timeStamp  time to synchronise
	 */
	public VirtualRobot(float physicalPosition, int ID, float speed, long timeStamp) {

		this.physicalPosition = physicalPosition;
		this.ID = ID;
		this.speed = speed;
		this.lastTimeStamp =timeStamp;
	}
	
	
	
	//GET and SET
	/**
	 * @return robot's ID
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD robot's ID
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the robot's speed
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * @param speed value in float
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	/**
	 * @return the last timestamp
	 */
	public long getLastTimeStamp() {
		return lastTimeStamp;
	}
	/**
	 * @param lastTimeStamp last synchronized time
	 */
	public void setLastTimeStamp(long lastTimeStamp) {

		this.lastTimeStamp = lastTimeStamp;
	}

	/**
	 * @return the distance
	 */
	public float getPhysicalPosition() {
		return physicalPosition;
	}

	/**
	 * @param physicalPosition physical position 
	 */
	public void setPhysicalPosition(float physicalPosition) {
		this.physicalPosition = physicalPosition;
	}
	

}
