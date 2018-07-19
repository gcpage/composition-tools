package sco;

import java.io.Serializable;

public class FTable implements Serializable {

	private static final long serialVersionUID = 2581117148676222588L;

	public static final int IFN_DEFAULT = 0; // placeholder - set by engine if remains at 0

	public int ifn;
	public int itime;
	public int isize;
	public GenRoutine igen;

	public FTable(int ifn, GenRoutine igen) {
		this.ifn = ifn;
		itime = 0;
		isize = 0;
		this.igen = igen;
	}

	public String read() {
		return "f " + ifn + " " + itime + " " + isize + " " + igen.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((FTable) obj).igen == this.igen;
	}
}