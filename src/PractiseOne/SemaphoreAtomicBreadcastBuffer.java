package PractiseOne;

import java.util.concurrent.Semaphore;

public class SemaphoreAtomicBreadcastBuffer<T> {
	private final int capacity;
	private final int consumers;
	private final Semaphore[] itemMutex; //mutex za uzimanje itema
	private final Semaphore[] consumerSems; //semafor za oznacavanje konsumerima da ima elemnata
	private final Semaphore producerSem; // semafor za profucera da moze da stavi elemnt
	// Protected by itemMutex.
	private final T[] buffer;
	private int consumerCount[];
	// Used by only one thread.
	private int producerProgress = 0;// do kog elemnta je dosao produces
	private int consumerProgress[]; //do kog elemnta je dosao consumer
	
	@SuppressWarnings("unchecked")
	public SemaphoreAtomicBreadcastBuffer(int capacity, int consumers) {
		this.capacity = capacity;
		this.consumers = consumers;
		producerSem = new Semaphore(capacity);
		itemMutex = new Semaphore[capacity];
		for (int i = 0; i < capacity; ++i) {
			itemMutex[i] = new Semaphore(1);
		}
		consumerSems = new Semaphore[consumers];
		for (int i = 0; i < consumers; ++i) {
			consumerSems[i] = new Semaphore(0);
		}
		consumerProgress = new int[consumers];
		consumerCount = new int[capacity];
		buffer = (T[]) new Object[capacity];
	}
	
	
	public void put(T data) {
		producerSem.acquireUninterruptibly(); //da li ima mesta za producera gde da stavi
		buffer[producerProgress] = data;
		consumerCount[producerProgress] = consumers; // broj consumera koji jos moraju da uzmu ovog itema
		producerProgress = (producerProgress + 1) % capacity;
		for (int i = 0; i < consumers; ++i) {
			consumerSems[i].release(); //obavestiti da ima itema za consumera
		}
	}

	
	public T get(int id) {
		consumerSems[id].acquireUninterruptibly(); //semafor da li ima itema za consumera
		int currentElement = consumerProgress[id]; //koji element uzima consumer
		consumerProgress[id] = (consumerProgress[id] + 1) % capacity; //povecaj koji element uzima consumer
		itemMutex[currentElement].acquireUninterruptibly(); //blokiraj taj element da moze samo consumer taj da ga uzima
		T item = buffer[currentElement]; //uzmi elemnt
		int currentConsumerCount = --consumerCount[currentElement]; // koliko jos consumera treba da uzmi ovaj item
		itemMutex[currentElement].release(); // otpusti taj elemnt 
		if (currentConsumerCount == 0) { // da li vise nijedan consumer treba da uzima ovaj element
			producerSem.release(); // dopusti produceru da pravi novi elemnt
		}
		return item;
	}

}