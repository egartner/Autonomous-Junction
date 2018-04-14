package fr.utbm.tr54.robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class MotorManager {
	private int speed;
	private EV3LargeRegulatedMotor left;
	private EV3LargeRegulatedMotor right;
	private static final int DISTANCE_ROUE = 180;
	private static final int DIAMETRE_ROUE = 30;
	
	/**
	 * Constructor by default of class MotorManager
	 */
	public MotorManager(){
		left = new EV3LargeRegulatedMotor(MotorPort.B);
		right = new EV3LargeRegulatedMotor(MotorPort.C);
	}
	/**
	 * Constructor of class MotorManager
	 * 
	 * @param speed the speed of the robot
	 */
	public MotorManager(int  speed){
		left = new EV3LargeRegulatedMotor(MotorPort.B);
		right = new EV3LargeRegulatedMotor(MotorPort.C);
		left.setSpeed(speed);
		right.setSpeed(speed);
	}
	/**
	 * @return speed	the speed of the robot
	 */
	public int getSpeed() {
		return speed;
	}
	/**
	 * @param speed		the speed of the robot
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/**
	 * Move the robot forward in a straight line
	 */
	void forward() {
		left.setSpeed(speed);
		right.setSpeed(speed);
		left.forward();
		right.forward();
	}
	/**
	 * Move the robot forward while turning on the left
	 */
	void forwardLeft() {
		left.setSpeed(speed/10);
		right.setSpeed(speed);
		left.forward();
		right.forward();
	}
	/**
	 * Move the robot forward while turning on the right
	 */
	void forwardRight() {
		left.setSpeed(speed);
		right.setSpeed(speed/10);
		left.forward();
		right.forward();
	}
	/**
	 * Stop the robot
	 */
	void stop() {
		left.setSpeed(0);
		right.setSpeed(0);
		left.stop();
		right.stop();
	}
	/**
	 * Rotate the robot
	 * 
	 * @param angleRadian	the angle of rotation
	 */
	public void rotate(float angleRadian) {
		int angleDegre = (int) (angleRadian * DISTANCE_ROUE * 360 / (DIAMETRE_ROUE * Math.PI * 2));
		right.rotate(-angleDegre, true);
		left.rotate(angleDegre);
	}
	/**
	 * @return the distance detected by the sensor in front of the robot
	 */
	public float CalculateDistance()
	{
		return (float) (Math.PI*DIAMETRE_ROUE*(left.getTachoCount()/360));
	}
	/**
	 * Reset the tachometer
	 */
	public void resetTachometer()
	{
		left.resetTachoCount();
		right.resetTachoCount();
	}
	/**
	 * @return 360	the max speed of the robot
	 */
	public int getMaxSpeed() {
		return 360;
	}
}
