package PractiseSecond;

public class BarrierMonitor {
	public int count;
	private int currentCount = 0;
	public boolean passed = false;
	public int brojProbijanja = 0;//ovo se koristi kako bi mogli vise puta da koristimo barijeru
	
	public BarrierMonitor(int count) {
		count = count;
	}
	
	public synchronized void arrived() {
		currentCount++;
		if(count == currentCount) {
			currentCount = 0; //debatable za visetruko moraju da se dodaju dve pomenljive u toku i nije u toku
			brojProbijanja++;
			notifyAll();
		}
		else{
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	
	public synchronized void await() {
		if(brojProbijanja>0) {
			brojProbijanja--;
			return;
		}
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		brojProbijanja--;
	
	}
	
	public synchronized void reset() {
		
		
	}
	
	
}
