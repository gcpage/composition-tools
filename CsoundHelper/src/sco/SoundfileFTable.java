package sco;

public class SoundfileFTable extends FTable {

	private static final long serialVersionUID = -2596051195054624160L;

	String Sfilnam;
	float iskip;
	int iformat;
	int ichn;

	public SoundfileFTable(String filename, int ifn) {
		super(ifn, GenRoutine.soundfile);
		this.Sfilnam = "samples/" + filename;
		this.iskip = 0; // Start at beginning
		this.iformat = 0; // Defer
		this.ichn = 0; // Defer
	}

	public SoundfileFTable(String filename) {
		this(filename, IFN_DEFAULT);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((SoundfileFTable) obj).Sfilnam.equals(this.Sfilnam);
	}

	@Override
	public String read() {
		return super.read() + " " + "\"" + Sfilnam + "\" " + iskip + " " + iformat + " " + ichn;
	}
}
