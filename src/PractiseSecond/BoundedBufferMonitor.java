package PractiseSecond;

import java.util.ArrayList;
import java.util.List;



public class BoundedBufferMonitor<T> {
	public List<T> bufferList;
	public int cap;
	
	public BoundedBufferMonitor(int capacity){
		bufferList = new ArrayList<>(capacity);
		cap = capacity;
	}
	
	public synchronized void put(T data) {
		while(bufferList.size() == cap) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bufferList.add(data);
		notifyAll();
	}
	
	
	public synchronized T get(){
		while(bufferList.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T data = bufferList.remove(0);
		notifyAll();
		return data;
	}
}
