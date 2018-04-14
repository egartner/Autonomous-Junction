package fr.utbm.tr54.threads;

import java.io.IOException;
import java.net.SocketException;

import fr.utbm.tr54.message.RobotServerMes;
import lejos.network.BroadcastManager;

public class SendRobot2ServerThread  extends Thread{
	private RobotServerMes message;
	private int timeWaiting;
	private BroadcastManager broadcast;
	
	/**
	 * @param msg Message send by robots to server
	 * @param timeWaiting  the length of time to sleep
	 * @param broadcast   Broadcast Manager
	 * @throws IOException
	 */
	public SendRobot2ServerThread(RobotServerMes msg, int timeWaiting, BroadcastManager broadcast) throws IOException {
		
		this.message = msg;
		this.timeWaiting = timeWaiting;
		this.broadcast=broadcast;
		if(message.getRobotId() != 0) {				
			sendMessage();
		}
	}
	
	
	/**
	 * 
	 */
	public void run() {		

		try {
			Thread.sleep(timeWaiting*100);
		} catch (InterruptedException e) {}
			
	}
	
	/**
	 * @throws SocketException
	 */
	public void sendMessage() throws SocketException {
		broadcast.broadcast(message.getByteBufferMessage());
	
	}

}
