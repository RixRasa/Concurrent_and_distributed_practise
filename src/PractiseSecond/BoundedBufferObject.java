package PractiseSecond;

import java.util.ArrayList;
import java.util.List;

public class BoundedBufferObject<T> {
	private List<T> buffList;
	private int cap;
	
	
	public BoundedBufferObject(int capacicty) {
		buffList = new ArrayList<T>(capacicty);
		cap = capacicty;
	}
	
	
	public void put(T data) {
		synchronized (this) {
			while(buffList.size() == cap) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			buffList.add(data);
			notifyAll();
		}
	}
	
	public T get() {
		synchronized (this) {
			while(buffList.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			T data = buffList.remove(0);
			notifyAll();
			return data;
		}
	}
}
