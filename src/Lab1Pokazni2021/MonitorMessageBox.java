package Lab1Pokazni2021;


import java.util.LinkedList;
import java.util.List;

public class MonitorMessageBox<T> implements MessageBox<T> {

	private List<T> buffer; // size --> buffer.size()
	private int cap = 0;
	//private int writeI = 0;
	//private int readI = 0;
	
	
	public MonitorMessageBox(int cap) {
		super();
		this.cap = cap;
		buffer = new LinkedList<>();
	}

	@Override
	public synchronized void put(T msg, int prio, int timeToLiveMs) {
		long start = System.currentTimeMillis(); 
		
		while( cap == buffer.size()) {
			try {
				wait();
			} catch (InterruptedException e) {
				long end = System.currentTimeMillis();
				if(end - start >= timeToLiveMs) return;
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		if(end - start >= timeToLiveMs) return;
		
		cap++;
		buffer.add(msg);// without taking care of priority
		notify();
	}

	@Override
	public synchronized T get(int timeToWait, Status status) {
		long start = System.currentTimeMillis(); 
		
		while(cap == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				long end = System.currentTimeMillis();
				if(end - start >= timeToWait) {
					status.setStatus(false);
					return null;
				}
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		if(end - start >= timeToWait) {
			status.setStatus(false);
			return null;
		}
		
		cap--;
		notifyAll();
		T item = buffer.remove(0);//prvi element
		status.setStatus(true);
		
		return item;
	}

}
