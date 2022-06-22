package PractiseSecond;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarrierLock {
	public int count;
	public int countArr = 0;
	public boolean passed = false;
	Lock lock = new ReentrantLock();
	Condition notArrivedCondition = lock.newCondition();
	
	
	public BarrierLock(int count ) {
		count = count;
	}
	
	public void arrived() {
		if(passed == true) return;
		countArr++;
		if(count ==  countArr) {
			passed = true;
			notArrivedCondition.signalAll();
		}
		else {
			while(!passed) {
				try {
					notArrivedCondition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void await() {
		if(passed == true) return;
		else {
			while(!passed) {
				try {
					notArrivedCondition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
