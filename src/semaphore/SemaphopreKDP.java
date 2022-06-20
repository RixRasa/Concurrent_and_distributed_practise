package semaphore;

public class SemaphopreKDP {
	private int s = 0;
	
	public SemaphopreKDP(int init) {
		this.s = init;
	}
	
	public synchronized void semWait() {
		while(s == 0) {
			try {
				wait(); // n3 n5 cekaju blokirane
				// n4 n2 n1 cekaju da nastave sa radom
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		s--;
		//notifyAll(); ili notify(); BESPOTREBNO BUDJENJE
	}
	
	public synchronized void semSignal() {
		s++; // tri signala dolaze zaredom: s=1 => s=2 => s=3
		notify(); // odblokiraju se niti n4 => n2 => n1
		
		
		//if(s == 1) {notify();}
		
		//if(s == 1) {notifyAll();}
		
	}
}

