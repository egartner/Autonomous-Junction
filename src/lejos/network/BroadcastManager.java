package lejos.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Singleton class used to send broadcast messages
 * @author Alexandre Lombard
 */
public class BroadcastManager implements AutoCloseable {
	
	private static BroadcastManager instance = null;
	
	/**
	 * Gets an instance of the broadcast manager 
	 * @return the broadcast manager
	 * @throws IOException 
	 */
	public static BroadcastManager getInstance(int port) throws IOException {
		if(instance == null) {
			instance = new BroadcastManager(port);
		}
		
		return instance;
	}
	
	private DatagramSocket socket;
	private DatagramChannel channel;
	private InetSocketAddress address;
	
	private BroadcastManager(int port) throws IOException {
		this.channel = DatagramChannel.open();
		this.socket = channel.socket();
		socket.setBroadcast(true);
		address = new InetSocketAddress(InetAddress.getByName("192.168.43.255"),port);
	}
	
	/**
	 * Close the broadcast manager
	 */
	public void close() {
		this.socket.close();
	}
	
	/**
	 * Broadcast a raw message
	 * @param message the message
	 * @throws IOException thrown if unable to send the packet
	 */
	public void broadcast(ByteBuffer message) throws SocketException {
		try {
			channel.send(message, address);	
		} catch (IOException e) {
			//
		}
	}
}
