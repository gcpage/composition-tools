package sco;

public enum GenRoutine {

	soundfile(1), sine(10), window(20);
	
	int id;
	
	private GenRoutine(int id) {
		this.id = id;
	}
}
