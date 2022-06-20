package ReadersAndWriters;

import java.util.concurrent.locks.StampedLock;

public class RWStapedLock { //TYPO should "RWStampedLock"
	private final StampedLock sl;
	
	public RWStapedLock() {
		sl = new StampedLock();
	}
	
	public long startRead() {
		return sl.readLock();
	}
	
	public void endRead(long st) {
		sl.unlockRead(st);
	}
	
	public long startWrite() {
		return sl.writeLock();
	}
	
	public void endWrite(long st) {
		sl.unlockWrite(st);
	}
	
	
	
}
