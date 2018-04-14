package fr.utbm.tr54.tp1;

public class Clock {
	private static long initialTime;
	
	public Clock(){
		initialTime = System.currentTimeMillis();
	}
	
	/**
	 * @return The actual time of this clock
	 */
	public long getTime() {
		return System.currentTimeMillis() - initialTime;
	}
	
	/**
	 * Synchronime the time of this clock with the new masterTime
	 * 
	 * @param masterTime 
	 */
	public void syncTime(long masterTime) {
		initialTime = - masterTime +System.currentTimeMillis();
	}
}
