package PractiseOne;

import java.util.ArrayList;
import java.util.List;

public class MonitorBoundedBuffer<T> implements BoundedBuffer<T> {
	private final int cap;
	private final List<T> buffer;
	
	public MonitorBoundedBuffer(int cap) {
		this.cap = cap;
		buffer = new ArrayList<T>(cap);
	}
	
	@Override
	public synchronized void put(T data) {
		while(buffer.size() == cap) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.add(data);
		notifyAll();
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized T get() {
		while(buffer.size() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T data = buffer.remove(0);
		notifyAll();
		return data;
	}

}
