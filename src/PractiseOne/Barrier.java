package PractiseOne;

public interface Barrier {
	public void arrived();
	public boolean await(long timeout);
	public void reset();
}
