package ConsumerProducer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//interface Lock and Condition
//Lock : lock, unlock ...
//Condition : await, signal, signalAll ...


//Can also use ArrayBlockinQueue or
// LinkedBlockingQueue interfaces
// using put(E e) and take() methods
public class BoundedBufferWithPackages<T> {
	
	final Lock lock = new ReentrantLock(true); //ReentrantLock - inherit key for further locks; "true" - fair
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();
	
	public static final int N = 100;
	final T[] items	= (T[]) new Object[N];
	int putptr, takeptr, count;
	
	public void put(T x) throws InterruptedException{
		lock.lock();
		
		try {
			while(count == items.length)
				notFull.await();
			items[putptr] = x;
			putptr = (putptr + 1) % items.length;
			count++;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
		
	}
	
	
	public T get() throws InterruptedException{
		lock.lock();
		try {
			while(count == 0)
				notEmpty.await();
			T reT = items[takeptr];
			takeptr = (takeptr + 1) % items.length;
			count--;
			notFull.signal();
			return reT;
		}finally {
			lock.unlock();
		}
	}
}
