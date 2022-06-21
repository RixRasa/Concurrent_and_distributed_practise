package PractiseSecond;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferLock<T> {
	final Lock lock =  new ReentrantLock();
	final Condition notFullCondition = lock.newCondition();
	final Condition notEmptyCondition = lock.newCondition();
	public List<T> bufferList;
	public int cap;
	
	public BoundedBufferLock(int capacity) {
		bufferList = new ArrayList<T>(capacity);
		cap = capacity;
	}
	
	public void put(T data) {
		lock.lock();
		while(bufferList.size() == cap) {
			try {
				notFullCondition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bufferList.add(data);
		notEmptyCondition.signal();
		lock.unlock();
	}
	
	public T get() {
		lock.lock();
		while(bufferList.size() == 0) {
			try {
				notEmptyCondition.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T dataT = bufferList.remove(0);
		notFullCondition.signal();
		lock.unlock();
		return dataT;
	}

}
