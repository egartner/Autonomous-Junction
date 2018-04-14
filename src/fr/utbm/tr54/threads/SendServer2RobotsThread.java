package fr.utbm.tr54.threads;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

import fr.utbm.tr54.message.ServerRobotMes;
import lejos.network.BroadcastManager;


public class SendServer2RobotsThread  extends Thread{
	private ServerRobotMes message;
	private int timeWaiting;
	private BroadcastManager broadcast;
	
	/**
	 * Thread to send 
	 * @param msg Message send by server to robots
	 * @param timeWaiting   the length of time to sleep
	 * @param broadcast  Broadcast Manager
	 * @throws IOException exeption 
	 */
	public SendServer2RobotsThread(ServerRobotMes msg, int timeWaiting, BroadcastManager broadcast) throws IOException {

		this.message = msg;
		this.timeWaiting = timeWaiting;
		this.broadcast=broadcast;
		if(!message.getRobotsInfo().isEmpty()) {				
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
