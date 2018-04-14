package fr.utbm.tr54.message;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Time;

public class RobotServerMes implements Message {
	
	private float physicalPosition;
	private int idRobot;
	private float speed;
	private long timeStamp;

	/**
	 * Constructor of the class RobotServerMes
	 * 
	 * @param physicalPosition 	position of the robot (distance to the conflict zone)
	 * @param idRobot			ID of the robot
	 * @param speed				speed of the robot
	 * @param timeStamp			timeStamp at which the message was sent
	 */
	public RobotServerMes(float physicalPosition, int idRobot, float speed, long timeStamp) {
		this.physicalPosition = physicalPosition;
		this.idRobot = idRobot;
		this.speed = speed;
		this.timeStamp = timeStamp;
	}
	/**
	 * Constructor of the class RobotServerMes
	 * 
	 * @param mes message received
	 */
	public RobotServerMes(ByteBuffer mes) {
		generateFromByteBufferMessage(mes);
	}
	
	/**
	 * @return physicalPosition position of the robot
	 */
	public float getPhysicalPosition() {
		return this.physicalPosition;
	}
	
	/**
	 * @return idRobot ID of the robot
	 */
	public int getRobotId(){
		return this.idRobot;
	}

	/**
	 * @return speed speed of the robot
	 */
	public float getSpeed(){
		return this.speed;
	}
	
	/**
	 * @return timeStamp timestamp of the message
	 */
	public long getTimestamp(){
		return this.timeStamp;
	}
	
	/** 
	 * @return string containing the timestamp (timeStamp), the ID (idRobot),
	 * the speed
	 */
	@Override
	public String toString() {
		return this.timeStamp + ";" + this.idRobot + ";" + this.speed + ";" + this.physicalPosition;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.tr54.message.Message#getByteMessage()
	 */
	@Override
	public byte[] getByteMessage() {
		return this.toString().getBytes();
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.tr54.message.Message#getByteBufferMessage()
	 */
	@Override
	public ByteBuffer getByteBufferMessage() {
		ByteBuffer buffer = ByteBuffer.wrap(this.getByteMessage());
		return buffer;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.tr54.message.Message#generateFromByteMessage(byte[])
	 */
	@Override
	public void generateFromByteMessage(byte[] mes) {
		ByteBuffer b = ByteBuffer.wrap(mes);
		String str = new String(b.array());
		this.timeStamp = (long)Long.parseLong((str.split(";")[0]));
		this.idRobot = Integer.parseInt(str.split(";")[1]);
		this.speed = Float.parseFloat(str.split(";")[2]);
		this.physicalPosition = Float.parseFloat(str.split(";")[3]);
	}

	/* (non-Javadoc)
	 * @see fr.utbm.tr54.message.Message#generateFromByteBufferMessage(java.nio.ByteBuffer)
	 */
	@Override
	public void generateFromByteBufferMessage(ByteBuffer mes) {
		byte[] data = mes.array();
		this.generateFromByteMessage(data);
	}
}
