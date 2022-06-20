package ReadersAndWriters;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RW {
	ReadWriteLock rw;
	Lock readLock, writeLock;
	
	public RW() {
		rw = new ReentrantReadWriteLock();
		readLock = rw.readLock();
		writeLock = rw.writeLock();
	}
	
	public void starRead() {
		readLock.lock();
	}
	
	public void starWrite() {
		writeLock.lock();
	}
	
	public void endRead() {
		readLock.unlock();
	}
	
	public void endWrite() {
		writeLock.unlock();
	}

}
