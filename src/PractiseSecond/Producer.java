package PractiseSecond;

import java.io.BufferedReader;
import java.io.FileReader;

public class Producer extends Thread{
	private BoundedBufferMonitor<String> lines; 
	
	public Producer(BoundedBufferMonitor<String> lists) {
		super("Producer");
		lines = lists;
	}
	
	
	@Override
	public void run() {  //CITANJE
		try(BufferedReader reader = new BufferedReader(new FileReader("title_basics.tsv"))) {
			String lineString = reader.readLine();
			while((lineString = reader.readLine()) != null) {
				lines.put(lineString);
			}
			lines.put(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
