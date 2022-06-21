package Lab1Pokazni2021;

public interface MessageBox<T> {
	
	public void put(T msg, int prio, int timeToLiveMs);
	
	public T get(int timeToWait, Status status); //Boolean just makes new object, boolean is just common type, so we need "Status"
	
}
