package PractiseSecond;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Thread{
	public BoundedBufferMonitor<String> lines;
	public int id;
	public BarrierMonitor consumerCombinerBarrier;
	public AtomicInteger totalFilms;
	public int K;
	
	public int maksimumWriters = 0;
	public ArrayList<Film> list;
	
	public BoundedBufferMonitor<Integer> integeri;
	public BoundedBufferMonitor<ArrayList<Film>> filmovi;
	
	
	
	public Consumer(BoundedBufferMonitor<String> line , int id, BarrierMonitor b, int K, BoundedBufferMonitor<Integer> inte,
			BoundedBufferMonitor<ArrayList<Film>> movie) {
		super("Consumer" + id);
		lines = line;
		this.id = id;
		consumerCombinerBarrier = b;
		this.K = K;
		list = new ArrayList<Film>();
		integeri = inte;
		filmovi = movie;
		
	}
	
	@Override
	public void run() {
		String line;
		int k = 0;
		while((line = lines.get()) != null) {
			Film film = new Film(line);
			if (film.NumWritets > maksimumWriters) {
				list.clear();
				list.add(film);
				maksimumWriters = film.NumWritets;
			}
			else if(film.NumWritets == maksimumWriters) {
				list.add(film);
			}
			else if(film.NumWritets < maksimumWriters) {}
			k++;
			if(k == K) {
				totalFilms.addAndGet(k);
				k=0;
			}
		}
		integeri.put(maksimumWriters);
		totalFilms.addAndGet(k);
		consumerCombinerBarrier.await();
		int max = integeri.get();
		if(max == maksimumWriters) {
			filmovi.put(list);
		}
		
		
		
		
	}
	
}
