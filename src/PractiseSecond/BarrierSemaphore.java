package PractiseSecond;


import java.security.Identity;
import java.util.concurrent.Semaphore;

public class BarrierSemaphore {
	public Semaphore[] consumeriSemaphores;
	public Semaphore mutexSemaphore;
	public Semaphore awaitSemaphore;
	public int count;
	public int counterArrived = 0;
	
	public BarrierSemaphore(int c) {
		count = c;
		consumeriSemaphores = new Semaphore[count];
		for(int i=0; i<count ; i++) {
			consumeriSemaphores[i] = new Semaphore(0);
		}
		mutexSemaphore = new Semaphore(1);
		awaitSemaphore = new Semaphore(0);
	}
	
	
	public void arrived(int id) {
		mutexSemaphore.acquireUninterruptibly();
		counterArrived++;
		if(counterArrived ==  count) {
			for(int i = 0; i<count ; i++) {
				if(id != i) {
					consumeriSemaphores[i].release();
				}
			}
			counterArrived=0;
			awaitSemaphore.release();
			mutexSemaphore.release();
		}
		else {
			mutexSemaphore.release();
			consumeriSemaphores[id].acquireUninterruptibly();
		}
	}
	
	public void await() {
		awaitSemaphore.acquireUninterruptibly();
	}
	
	
}
