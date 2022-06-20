package semaphore;

public class SemaphopreKDP {
	private int s = 0;
	
	public SemaphopreKDP(int init) {
		this.s = init;
	}
	
	public synchronized void semWait() {
		while(s == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		s--;
	}
	
	public synchronized void semSignal() {
		s++;
		notify();
	}
}

