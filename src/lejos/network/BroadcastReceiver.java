package lejos.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;

public class BroadcastReceiver implements AutoCloseable {
	
	private static BroadcastReceiver instance = null;
	
	/**
	 * Gets an instance of the broadcast receiver 
	 * @return the broadcast receiver
	 * @throws IOException 
	 */
	public static BroadcastReceiver getInstance(int port) throws IOException {
		if(instance == null) {
			instance = new BroadcastReceiver(port);
		}
		
		return instance;
	}
	
	private DatagramSocket socket;
	private DatagramChannel channel;
	private InetSocketAddress address;
	private List<BroadcastListener> listeners = new ArrayList<>();
	
	private BroadcastReceiverRunnable runnable;
	
	private BroadcastReceiver(int port) throws IOException {
		this.channel = DatagramChannel.open();
		this.socket = channel.socket();
		address = new InetSocketAddress(port);
		this.socket.bind(address);
		this.runnable = new BroadcastReceiverRunnable(this);
		
		new Thread(this.runnable).start();
	}
	
	/**
	 * Close the broadcast receiver
	 */
	public void close() {
		this.runnable.stop();
		this.socket.close();
	}
	
	/**
	 * Add a listener to broadcast messages
	 * @param listener the listener to add
	 */
	public void addListener(BroadcastListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Remove a broadcast listener
	 * @param listener the listener to remove
	 */
	public void removeListener(BroadcastListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Fire the broadcast received event
	 * @param data the raw message received
	 */
	protected void fireBroadcastReceived(ByteBuffer data) {
		for(BroadcastListener listener : this.listeners) {
			listener.onBroadcastReceived(data);
		}
	}
	
	/**
	 * Gets the datagram socket
	 * @return the datagram socket
	 */
	protected DatagramSocket getSocket() {
		return this.socket;
	}
	
	protected DatagramChannel getChannel() {
		return this.channel;
	}
	
	private static class BroadcastReceiverRunnable implements Runnable {

		private boolean stop = false;
		private byte[] buffer = new byte[1024];
		
		private BroadcastReceiver broadcastReceiver;
		
		private BroadcastReceiverRunnable(BroadcastReceiver broadcastReceiver) {
			this.broadcastReceiver = broadcastReceiver;
		}
		
		@Override
		public void run() {
			while(!this.stop) {
				//final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				ByteBuffer data = ByteBuffer.allocate(8192);
				data.clear();
				try {
					this.broadcastReceiver.getChannel().receive(data);
					this.broadcastReceiver.fireBroadcastReceived(data);
				} catch (IOException e) {
					//
				}
			}
		}
		
		public void stop() {
			this.stop = true;
		}
	}
}
