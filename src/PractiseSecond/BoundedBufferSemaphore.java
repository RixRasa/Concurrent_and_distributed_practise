package PractiseSecond;

import java.security.DrbgParameters.Capability;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BoundedBufferSemaphore<T> {
	public List<T> bufferList;
	public int cap;
	public Semaphore fullSemaphore = new Semaphore(0);
	public Semaphore emptySemaphore = new Semaphore(1);
	public Semaphore mutexPSemaphore =  new Semaphore(1);
	public Semaphore mutexCSemaphore = new Semaphore(1);
	
	public BoundedBufferSemaphore(int capacity) {
		bufferList = new ArrayList<T>(cap);
		cap = capacity;
		fullSemaphore = new Semaphore(0);
		emptySemaphore = new Semaphore(cap);
		mutexPSemaphore =  new Semaphore(1);
		mutexCSemaphore = new Semaphore(1);
	}
	
	public void put(T data) {
		emptySemaphore.acquireUninterruptibly();
		mutexPSemaphore.acquireUninterruptibly();
		bufferList.add(data);
		mutexPSemaphore.release();
		fullSemaphore.release();
		
	}
	
	public T get() {
		fullSemaphore.acquireUninterruptibly();
		mutexCSemaphore.acquireUninterruptibly();
		T dataT = bufferList.remove(0);
		mutexCSemaphore.release();
		emptySemaphore.release();
		return dataT;
	}
	
}
