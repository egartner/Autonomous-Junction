package fr.utbm.tr54.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import fr.utbm.tr54.message.*;
import fr.utbm.tr54.tp1.Clock;
import lejos.network.*;
import fr.utbm.tr54.threads.SendServer2RobotsThread;

public class Server {
	
	private final static int TIME_BETWEEN_MESSAGE = 1;
	private final static int MAX_SPEED_ROBOT = 360;  // centimetre par seconde
	private static boolean asMessages = false;
	private static RobotServerMes mesReceive;

	public static void main(String[] args) throws IOException{
		
		Clock clock = new Clock();
		BroadcastManager broadcast = BroadcastManager.getInstance(8888);
		BroadcastReceiver receiver = BroadcastReceiver.getInstance(9999);

			
		LinkedList<VirtualRobot> listRobots = new LinkedList<>();	
		LinkedList<Information> listInformations = new LinkedList<>();
		

		SendServer2RobotsThread  communicationThread =new SendServer2RobotsThread(new ServerRobotMes(clock.getTime(), listInformations),0,broadcast);			
		communicationThread.start();
		
		receiver.addListener(new BroadcastListener() {

			@Override
			public void onBroadcastReceived(ByteBuffer message) {
				asMessages = true;
				mesReceive = new RobotServerMes(message);
			}
		});
		
		
	
		while(true) {			
			
			//look if got message
			if(asMessages==true) {
				
				//read the message
				if (mesReceive != null){
					VirtualRobot robotFromMes = new VirtualRobot(mesReceive.getPhysicalPosition(),mesReceive.getRobotId(),  mesReceive.getSpeed(), mesReceive.getTimestamp());
				
					int indexList;
					//-1 means that it's not there
					if((indexList = isRobotInList(listRobots, robotFromMes)) == -1) {
						listRobots.addLast(robotFromMes);
					}
					else {
						listRobots.get(indexList).setLastTimeStamp(mesReceive.getTimestamp());
						listRobots.get(indexList).setPhysicalPosition(mesReceive.getPhysicalPosition());
						listRobots.get(indexList).setSpeed(mesReceive.getSpeed());
					}
				}
				asMessages=false;
	
			}

			//Trie la listRobots par la position physique des robots.
			Collections.sort(listRobots, new Comparator<VirtualRobot>() { 
				@Override 
				public int compare(VirtualRobot r1, VirtualRobot r2) { 
					return (int)(r2.getPhysicalPosition() - r1.getPhysicalPosition());
				} 
			});			
			
			int newSpeed;
			listInformations.clear();
			for(int i=0;i<listRobots.size();i++) {
				newSpeed =0;
				VirtualRobot thisRobot = listRobots.get(i);
				
				newSpeed = (int) thisRobot.getSpeed();
				
				if (thisRobot.getPhysicalPosition() >=30 && thisRobot.getPhysicalPosition() <=40) {
					newSpeed =0;
				}
				if(i ==0) {
					newSpeed =MAX_SPEED_ROBOT;
				}
				listInformations.add(new Information(thisRobot.getID(), i, newSpeed));				
			}
			
						
			if(! communicationThread.isAlive()) {
				System.out.println(listRobots.toString());
				ServerRobotMes newMes = new ServerRobotMes(clock.getTime(), listInformations);
				communicationThread = new SendServer2RobotsThread(newMes,TIME_BETWEEN_MESSAGE,broadcast);
				communicationThread.start();

			}	
			
		}
		
	}
	
	
	
	private static int isRobotInList(LinkedList<VirtualRobot> listRobots, VirtualRobot robotFromMes) {
		for(int i=0;i<listRobots.size();i++) {
			if (listRobots.get(i).getID() == robotFromMes.getID()){
				return i;
			}
		}
		return -1;
	}
	
}
