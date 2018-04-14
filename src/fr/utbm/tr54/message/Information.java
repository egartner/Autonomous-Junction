package fr.utbm.tr54.message;

public class Information {
	
	private int IdRobot,orderList;
	private float newSpeed;

	/**
	 * Constructor of class Information
	 * 
	 * @param IdRobot 	ID of the robot
	 * @param orderList	order in the list
	 * @param newSpeed 	new speed of the robot
	 */
	public Information(int IdRobot, int orderList, float newSpeed) {
		this.IdRobot = IdRobot;
		this.orderList = orderList;
		this.newSpeed = newSpeed;
	}
	
	/**
	 * @return IdRobot	the ID of the robot
	 */
	public int getRobotId(){
		return this.IdRobot;
	}
	
	/**
	 * @return orderList	the order of the robot in the list
	 */
	public int getListOrder() {
		return this.orderList;
	}
	
	/**
	 * @return newSpeed		the new speed of the robot
	 */
	public float getNewSpeed() {
		return this.newSpeed;
	}
	
	/** 
	 * @return A string containing the Id of the robot, its order in the list and its new speed
	 */
	@Override
	public String toString() {
		return IdRobot + ";" + orderList + ";" + newSpeed;
	}
}
