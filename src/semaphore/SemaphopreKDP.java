package semaphore;

public class SemaphopreKDP {
	private int s = 0;
	
	public SemaphopreKDP(int init) {
		this.s = init;
	}
	
	public synchronized void semWait() {
		while(s == 0) {
			try {
				wait(); // n3 n5 waiting to be unblocked
				// n4 n2 n1 waiting on their turn with 'this' key
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		s--;
		//notifyAll(); ili notify(); USELESS WAKING UP
	}
	
	public synchronized void semSignal() {
		s++; // three signals coming in a row: s=1 => s=2 => s=3
		notify(); // unblocking three threads n4 => n2 => n1
		
		
		//if(s == 1) {notify();}
		
		//if(s == 1) {notifyAll();}
		
	}
}

