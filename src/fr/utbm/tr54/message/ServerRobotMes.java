package fr.utbm.tr54.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerRobotMes implements Message {
	
	private long timeStamp;
	private LinkedList<Information> robotsInfo;
	
	/**
	 * Constructor of class ServerRobotMes
	 * 
	 * @param timeStamp 	the time stamp
	 * @param robotsInfo	the list containing the data to be transmitted to the robots	
	 */
	public ServerRobotMes(long timeStamp, LinkedList<Information> robotsInfo){

		this.timeStamp = timeStamp;
		this.robotsInfo = robotsInfo;
	}
	
	/**
	 * Constructor of class ServerRobotMes 
	 * 
	 * @param mes	message of type ByteBuffer
	 */
	public ServerRobotMes(ByteBuffer mes) {
		generateFromByteBufferMessage(mes);
	}
	
	/**
	 * @return timeStamp	the time at which the message has been sent
	 */
	public long getTimeStamp(){
		return this.timeStamp;
	}

	/**
	 * @return robotsInfo	the list containing the data to be transmitted to the robots
	 */
	public LinkedList<Information> getRobotsInfo(){
		return this.robotsInfo;
	}
	
	/**
	 * @return str	a string containing all the data to be transmitted to the robots
	 */
	@Override
	public String toString() {
		String str = timeStamp + ":";
		for (Information information : robotsInfo) {
			str += information.toString() + "#"; 
		}
		str = str.substring(0,str.length()-1);
		return str;
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.tr54.message.Message#getByteMessage()
	 */
	@Override
	public byte[] getByteMessage() {
		return this.toString().getBytes();
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.tr54.message.Message#generateFromByteMessage(byte[])
	 */
	@Override
	public void generateFromByteMessage(byte[] mes) {
		//getClass().ByteBuffer b = ByteBuffer.wrap(mes);
		String str = new String(mes);
		
		double timeStamp = Double.parseDouble(str.split(":")[0]);
		str = str.split(":")[1];
		LinkedList<Information> robotsInfo = new LinkedList<Information>();
		for (String string : str.split("#")) {
			robotsInfo.add( new Information( Integer.parseInt(string.split(";")[0]), Integer.parseInt(string.split(";")[1]), Float.parseFloat(string.split(";")[2]) ));
		}
		this.timeStamp = (long)timeStamp;
		this.robotsInfo = robotsInfo;
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
	 * @see fr.utbm.tr54.message.Message#generateFromByteBufferMessage(java.nio.ByteBuffer)
	 */
	@Override
	public void generateFromByteBufferMessage(ByteBuffer mes) {
		byte[] data = mes.array();
		this.generateFromByteMessage(data);
	}
}
