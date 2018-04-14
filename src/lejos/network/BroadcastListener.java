package lejos.network;

import java.nio.ByteBuffer;

/**
 * Broadcast listener interface
 * @author Alexandre Lombard
 */
public interface BroadcastListener {
	/**
	 * Triggered on broadcast received
	 * @param data the raw message
	 */
	 
	public void onBroadcastReceived(ByteBuffer data);
}
