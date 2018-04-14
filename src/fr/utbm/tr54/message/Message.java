package fr.utbm.tr54.message;

import java.nio.ByteBuffer;

/**
 * Message Interface
 */
public interface Message {

	/**
	 * @return return the message converted into an array of bytes
	 */
	public byte[] getByteMessage();
	/**
	 * @return the message converted into a ByteBuffer
	 */
	public ByteBuffer getByteBufferMessage();
	/**
	 * Generate the data from an array of byte
	 * 
	 * @param mes message received converted into an array of bytes
	 */
	public void generateFromByteMessage(byte[] mes);
	/**
	 * Generate the data from the ByteBuffer received
	 * 
	 * @param mes message received converted into a ByteBuffer
	 */
	public void generateFromByteBufferMessage(ByteBuffer mes);
}
