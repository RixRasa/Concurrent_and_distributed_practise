package PractiseOne;

public class MonitorBarrier implements Barrier {
	private final int count;
	private int currentCount = 0;
	boolean passed = false;
	int round = 0;
	
	public MonitorBarrier(int count) {
		this.count = count;
	}
	
	@Override
	public synchronized void arrived() {
		int myRound = round;
		if(passed) {
			return;
		}
		if(++currentCount == count) {
			passed = true;
			notifyAll();
		}
		else {
			while(!passed && round ==  myRound) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized boolean await(long timeout) {
		int myRound = round;
		if (passed) {
			return true;
		}
		while(!passed && myRound == round) {
			try {
				long timeB = System.currentTimeMillis();
				wait(timeout);
				long timeA = System.currentTimeMillis();
				if(timeout != 0 && timeA - timeB >= timeout) {
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return currentCount == count;
	}

	@Override
	public synchronized void reset() {
		currentCount = 0;
		passed = false;
		round++;

	}

}
