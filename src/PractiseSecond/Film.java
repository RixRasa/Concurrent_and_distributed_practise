package PractiseSecond;

public class Film {
	public String tconst;
	public String[] directors;
	public String[] writers;
	public int NumWritets = 0;
	
	public Film(String line) {
		String [] nizStrings = line.split("\t");
		tconst = nizStrings[0];
		String directorLineString = nizStrings[1];
		String writetString = nizStrings[2];
		
		directors = directorLineString.equals("\\N") ? new String[0] : directorLineString.split(",");
		writers = writetString.equals("\\N") ? new String[0] : writetString.split(",");
		for (String writer : writers) {
			if(writer.equals("\\N")) {continue;}
			else { NumWritets++;}
		}
	}
}
